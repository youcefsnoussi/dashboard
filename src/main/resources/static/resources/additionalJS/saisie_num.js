$(document).ready(function() {
	
	//$(".saisie_0").prop("type","number");
	
	$(".saisie_0").val(0);
	
	$(".saisie_0").focus(function(){
		
		var val = $(this).val();
		
		$(this).prop("type","number");
		
		if(val==0){
			
			$(this).val("");
			
		}
		else{
			
			$(this).val(parseFloat(val));
			
		}
		
	});
	
	$(".saisie_0").blur(function(){
		
		if($(this).val() == ""){
			
			$(this).val(0);
			
		}
		else{
			
			var val = $(this).val().replace(',','.');
			
			$(this).prop("type","text");
			
			$(this).val((val));
			
		}
		
	});
	
	
});