#include "json.h"
#include "plugin/threads.h"
#include <stdio.h>
#include "sqlite/sqlite3.h"
#include <string>
using namespace std;

int serve_request(soap*);

static int callback(void *data, int argc, char **argv, char **azColName){
   int i;
   fprintf(stderr, "%s: ", (const char*)data);
   
   for(i = 0; i<argc; i++){
      printf("%s = %s\n", azColName[i], argv[i] ? argv[i] : "NULL");
   }
   
   printf("\n");
   return 0;
}
int main()
{
  soap *ctx = soap_new1(SOAP_C_UTFSTRING);
  int port = 8080;

  if (!soap_valid_socket(soap_bind(ctx, NULL, port, 100)))
  {
    soap_print_fault(ctx, stderr);
    exit(1);
  }

  soap_set_mode(ctx, SOAP_IO_KEEPALIVE); // enable HTTP keep-alive, which is optional

  ctx->send_timeout = 1;
  ctx->recv_timeout = 1;
  ctx->transfer_timeout = 30;

  while (1)
  {
    if (!soap_valid_socket(soap_accept(ctx)))
      soap_print_fault(ctx, stderr);
      
    else
    {
      THREAD_TYPE tid;
      void *arg = (void*)soap_copy(ctx);
      // use updated THREAD_CREATE from plugin/threads.h https://www.genivia.com/files/threads.zip
      if (!arg)
        soap_closesock(ctx);
     
      else
          while (THREAD_CREATE(&tid, (void*(*)(void*))serve_request, arg))
          	sleep(1);
    }
  }
  soap_destroy(ctx);
  soap_end(ctx);
  soap_free(ctx);

  return 0;
}

int serve_request(soap* ctx)
{
  THREAD_DETACH(THREAD_ID);

  // HTTP keep-alive max number of iterations
  unsigned int k = ctx->max_keep_alive;
  value request(ctx);

  do
  {
    if (ctx->max_keep_alive > 0 && !--k)
      ctx->keep_alive = 0;

      value response(ctx);
      char *zErrMsg = 0;
      sqlite3 *db;
      sqlite3_open("meter_db",&db);
      const char* data = "Callback function called";
      string sql = "SELECT * FROM active_appliances LIMIT 1";
      sqlite3_stmt *res;
      
      sqlite3_stmt* stmt; // will point to prepared stamement object
      int rc = sqlite3_prepare_v2(
      db,            // the handle to your (opened and ready) database
      sql.c_str(),    // the sql statement, utf-8 encoded
      sql.length(),   // max length of sql statement
      &stmt,          // this is an "out" parameter, the compiled statement goes here
      nullptr);       // pointer to the tail end of sql statement (when there are 
                    // multiple statements inside the string; can be null)
      if (rc != SQLITE_OK) {
 	cout<<"error"<<endl;
      }	
      string appliance;
	sqlite3_step(stmt);
	appliance = (const char*)sqlite3_column_text(stmt,0);
	
      cout<<"amp "<<appliance;
	// Free the statement when done.
	sqlite3_finalize(stmt);
	
      response["applicance"] = appliance;
      ctx->http_content = "application/json; charset=utf-8";
      if (soap_response(ctx, SOAP_FILE)
       || json_send(ctx, response)
       || soap_end_send(ctx))
        soap_print_fault(ctx, stderr);
    //}
    // close (keep-alive may keep socket open when client supports it)
    soap_closesock(ctx);
  } while (ctx->keep_alive);

  int err = ctx->error;

  // clean up
  soap_destroy(ctx);
  soap_end(ctx);
  soap_free(ctx);

  return err;
}

/* Don't need a namespace table. We put an empty one here to avoid link errors */
struct Namespace namespaces[] = { {NULL, NULL} };
