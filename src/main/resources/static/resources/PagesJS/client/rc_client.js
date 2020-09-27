$(document).ready(function() {

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
	
	var ret = getUrlParameter('ret');
	
	if(ret!=null){
		
		if(ret!="succes"){
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").text("Ce client est déja relier a ce RC !!! ");
			$("#error").modal('show');
			
		}
		else {
			
			$("#title").text("Information ");
			$("#icone").attr("class","far fa-info-circle");
			$("#text").text("Ajout effectuer avec succes :) ");
			$("#error").modal('show');
			
		}
		
	}

});	