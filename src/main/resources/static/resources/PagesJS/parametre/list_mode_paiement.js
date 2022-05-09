$(document).ready(function() {
			
		$(".edit").click(function(){
			
			$("#title").text("modification");
			
			$("#icone").attr("class","far fa-edit");
			
			var i = $(this);
			
			$("#id_mode_payment").val(i.attr("id_payment"));
			$("#designation").val(i.attr("nom_payment"));
			
			let display = i.attr("display");
			
			if(display=="true"){
				$("#display_ui").prop("checked",true);
				$("#display").val("on");
			}
			else{
				$("#display_ui").prop("checked",false);
				$("#display").val("off");
			}
			
			$("#add_edit_mode_payment").modal('show');
			
		});
		
		$("#display_ui").change(function(){
			
			if($(this).prop("checked")){
				
				$("#display").val("on");
				
			}
			else{
			
				$("#display").val("off");
				
			}
			
		});
		
		$("#add_mode_payment").click(function(){
			
			$("#title").text("Ajouter");
			
			$("#icone").attr("class","far fa-plus-square");
			
			var i = $(this);
			
			$("#id_mode_payment").val(0);
			
			$(".clear").val("");
			
			$("#add_edit_mode_payment").modal('show');
			
		});
			
});