/** * */

$(document).ready(function(){
	
	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = window.location.search.substring(1),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
	        }
	    }
	};
	
	var status = getUrlParameter('status');
	
	var e_message = getUrlParameter('e_message');
	
	var icon = "", messageText = "", title = "";
	
	switch (status){
	
		case "exist" : { icon="info-square"; title = "Opération Impossible";} break;
		
		case "ok" : { icon="info-square"; title = "Opération Effectuer";} break;
		
		case "error" : { icon="exclamation-triangle"; title = "Veuillez contacté l'administrateur";} break;
		
	}
	
	messageText = (typeof(status) !== "undefined") ? 
			decodeURIComponent(e_message.replaceAll('+',' ').replaceAll('$','<br>')) : 
			"";//e_message.replaceAll('$','<br>');
	
	if(status != "" && typeof(status) !== "undefined"){
		
		$("#title").text(title);
		
		$("#icone").attr("class","far fa-"+icon);
		
		$("#text").html(messageText);
		
		$("#error").modal("show");
		
	}
	
});