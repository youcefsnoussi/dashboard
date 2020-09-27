$(document).ready(function() {
			
			/***************************************** get returned param *****************************************************/
			
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
				
				if(ret=="exist"){
					
					$("#title").text("Erreur !!!");
					$("#icone").attr("class","far fa-exclamation-triangle");
					$("#text").text("Un RC existe avec les memes informations !!! ");
					$("#error").modal('show');
					
				}
				else {
					
					$("#title").text("Information ");
					$("#icone").attr("class","far fa-info-circle");
					$("#text").text("Ajout effectuer avec succes :) ");
					$("#error").modal('show');
					
				}
				
			}
			
			/***************************************** END get returned param *****************************************************/
			
			$(".n_nif").keyup(function(){
				
				var val = $(this).val();
				
				
				if(val.length>15){
					
					$(this).val(val.slice(0,-1));
					
				}
				
			});
		 	
		 	$(".n_art").keyup(function(){
				var val = $(this).val();
				
				
				if(val.length>11){
					
					$(this).val(val.slice(0,-1));
					
				}
				
			});
		 	
		 	$(".n_rc").keyup(function(){
				
				var val = $(this).val();
				
				if(val.length==2){
					
					$(this).val(val+"/")
					
				}
				
				if(val.length==5){
					
					$(this).val(val+"-")
					
				}
				
				if(val.length>16){
					
					$(this).val(val.slice(0,-1));
					
				}
				
			});
		 	
			
});	