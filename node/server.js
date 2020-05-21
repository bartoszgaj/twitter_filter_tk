const request = require('request');

var requestLoop = setInterval(function(){
  request({
      url: "http://app:8080/api/v1/tweets",
      method: "GET",
      timeout: 10000,
      followRedirect: true,
      maxRedirects: 10
  },function(error, response, body){
      if(!error && response.statusCode == 200){
          console.log(body);
      }else{
          console.log('error' + response.statusCode);
      }
  });
}, 60000);
