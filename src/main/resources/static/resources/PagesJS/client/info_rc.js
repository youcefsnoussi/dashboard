$(document).ready(function() {
			
			$('[data-toggle="tooltip"]').tooltip();
			
			if($("#etat_blockage").val()=="active"){
				
				$("#active").css("border-width","4px");
				$("#active").css("border-color","grey");
			    
			}
			else if($("#etat_blockage").val()=="block"){
				
				$("#block").css("border-width","4px");
				$("#block").css("border-color","grey");
				
			}
			else{
				
				$("#blacklist").css("border-width","4px");
				$("#blacklist").css("border-color","grey");
				
			}
			
			$("#active").click(function(){
				
				$("#etat_blockage").val("active");
				
				$(this).css("border-width","4px");
				$(this).css("border-color","grey");
				
				$("#block").css("border-width","0px");
				$("#blacklist").css("border-width","0px");
				
			});
			
			$("#block").click(function(){
				
				$("#etat_blockage").val("block");
				
				$(this).css("border-width","4px");
				$(this).css("border-color","grey");
				
				$("#active").css("border-width","0px");
				$("#blacklist").css("border-width","0px");
				
			});
			
			$("#blacklist").click(function(){
				
				$("#etat_blockage").val("blacklist");
				
				$(this).css("border-width","4px");
				$(this).css("border-color","grey");
				
				$("#block").css("border-width","0px");
				$("#active").css("border-width","0px");
				
			});
			
				
});	