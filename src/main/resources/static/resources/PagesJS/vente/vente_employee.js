$(document).ready(function() {
			
	$("#employee").change(function() {
		
		 $("#nom").val($("#employee option:selected").attr("nom"));
		 $("#prenom").val($("#employee option:selected").attr("prenom"));
		
	})
	
});
		