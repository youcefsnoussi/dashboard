$(document).ready(function() {
	
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
	            	window.location.replace("commande");
	            },
	            0: function (response) {
	            	window.location.replace("commande");
	            }              
	        }
		});
		
	}, 5000);
	
});