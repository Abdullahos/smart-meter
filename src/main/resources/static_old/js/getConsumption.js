fetch("http://localhost:9191/chart.html",{
         method:'GET',
    headers:{
           	headers:{Authorization:'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb290IiwiZXhwIjoxNjQ3MzM4OTk2fQ.RkLGH3J8hVUZSqHP2Blx71LRcOqPe0CV7TlpisBvjxk876y0k-58z5C7zh4ZD6vrv2Uh-2HnjRjDNpGY97faKg'}
        }
    })
    .then(function(response){
    return response.json()
    })