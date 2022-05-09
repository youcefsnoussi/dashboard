$(document).ready(function() {
			
	
			$(".edit").click(function(){
				
				$("#title").text("modification");
				
				$("#icone").attr("class","far fa-edit");
				
				var i = $(this);
				
				$("#id_banque").val(i.attr("id_banque"));
				$("#nom_banque").val(i.attr("nom_banque"));
				$("#code").val(i.attr("code"));
				$("#num_compte").val(i.attr("numero_compte_entreprise"));
				
				let display = i.attr("display");
				
				if(display=="true"){
					$("#display_ui").prop("checked",true);
					$("#display").val("on");
				}
				else{
					$("#display_ui").prop("checked",false);
					$("#display").val("off");
				}
				
				$("#add_edit_banque").modal('show');
				
			});
			
			$("#display_ui").change(function(){
				
				if($(this).prop("checked")){
					
					$("#display").val("on");
					
				}
				else{
				
					$("#display").val("off");
					
				}
				
			});
			
			$("#add_banque").click(function(){
				
				$("#title").text("Ajouter");
				
				$("#icone").attr("class","far fa-plus-square");
				
				var i = $(this);
				
				$("#id_banque").val(0)
				
				$(".clear").val("");
				
				$("#add_edit_banque").modal('show');
				
			});
			
});	/**
 * 
 */