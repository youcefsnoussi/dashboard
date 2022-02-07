$(document).ready(function() {
		
	$(".zero").focus(function(){
		
		if($(this).val()==0){
			
			$(this).val("");
			
		}
		
	});
	
	$(".zero").blur(function(){
		
		if($(this).val()==""){
			
			$(this).val(0);
			
		}
		
	});
		
});