/**
 * 
 */

$(document).ready(function(){
				
		$('.sidenav').sidenav();
		$('.collapsible').collapsible({accordion: true});
		
		$(".li_drop").click(function(){
			
			$(".li_drop").css("background-color","transparent");
			
			$(this).css("background-color","#f5f5f5");	
			
		});
		
		
				
});