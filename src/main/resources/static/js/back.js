chartIt();
async function chartIt(){
    await getdata();
    const ctx = document.getElementById('myChart').getContext('2d');
    const xlabels= [];
    const myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: xlabels,
            datasets: [
                {
                    label: 'Global Average Temperature',
                    data: ytemps,
                    backgroundColor:'rgba(255, 99, 132, 0.2)',
                    borderColor:'rgba(255, 99, 132, 1)',
                    borderWidth: 1
                }
            ]
        }
    });
}

getdata();
async function getdata(){
   getdata();
   async function getdata(){
       const response = await fetch('http://localhost:9191/reading/get/period?start=2022-02-01&end=2022-02-28'
                                    ,{
   		headers:{Authorization:'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb290IiwiZXhwIjoxNjQ3MzM4OTk2fQ.RkLGH3J8hVUZSqHP2Blx71LRcOqPe0CV7TlpisBvjxk876y0k-58z5C7zh4ZD6vrv2Uh-2HnjRjDNpGY97faKg'}
       })
   	.then(res =>res.json()).then(data => console.log(data));
       const data = await response.text();
       const{ electric_current, volt }=data;

       document.getElementById('lat').textContent = first;
       document.getElementById('lon').textContent = second;

       const table = data.split('\n');
       table.forEach(row =>{
           const columns = row.split(',');
           const year =columns[0];

           const temp =columns[1];

           console.log(year,temp);
       });
   }
    });
    }

    /*

function sendJSON(){

            let result ;
            let name = document.querySelector('#name');
            let email = document.querySelector('#password');

            // Creating a XHR object
            let xhr = new XMLHttpRequest();
            let url = "login";

            // open a connection
            xhr.open("POST", url, true);

            // Set the request header i.e. which type of content you are sending
            xhr.setRequestHeader("Content-Type", "application/json");

            // Create a state change callback
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {

                    // Print received data from server
                    result.innerHTML = this.responseText;

                }
            };

            // Converting JSON data to string
            var data = JSON.stringify({ "name": name.value, "password": password.value });

            // Sending data with the request
            xhr.send(data);
        }
        */
            