/*
$(document).ready(function() {
	
	
	
});
*/
function checkConnection(url){
	
	setInterval(function () {
	    
		$.ajax({
			url: "TestCon",
			type : "GET",
	        type: "HEAD",
	        timeout:1000,
	        statusCode: {
	            200: function (response) {
	                //console.log('---> Working!');
	            },
	            400: function (response) {
	            	window.location.replace(url);   //------> commande
	            },
	            0: function (response) {
	            	window.location.replace(url);
	            }              
	        }
		});
		
	}, 5000);
	
}

