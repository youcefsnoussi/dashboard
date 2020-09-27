$(document).ready(function() {
				
		$(".edit").click(function(){
			
			$("#title").text("modification");
			
			$("#icone").attr("class","far fa-edit");
			
			var i = $(this);
			
			$("#id_type_reg").val(i.attr("id_type_reg"));
			$("#designation").val(i.attr("designation"));
			
			$("#add_edit_type_reg").modal('show');
			
		});
		
		$("#add_type_reg").click(function(){
			
			$("#title").text("Ajouter");
			
			$("#icone").attr("class","far fa-plus-square");
			
			var i = $(this);
			
			$("#id_type_reg").val(0);
			
			$(".clear").val("");
			
			$("#add_edit_type_reg").modal('show');
			
		});
					
});