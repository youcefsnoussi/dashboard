$(document).ready(function() {
			
		$(".edit").click(function(){
			
			$("#title").text("modification");
			
			$("#icone").attr("class","far fa-edit");
			
			var i = $(this);
			
			$("#id_mode_payment").val(i.attr("id_payment"));
			$("#designation").val(i.attr("nom_payment"));
			
			$("#add_edit_mode_payment").modal('show');
			
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