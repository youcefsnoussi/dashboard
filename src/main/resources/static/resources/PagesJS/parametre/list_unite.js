$(document).ready(function() {
				
		$(".edit").click(function(){
			
			$("#title").text("modification");
			
			$("#icone").attr("class","far fa-edit");
			
			var i = $(this);
			
			$("#id_unite").val(i.attr("id_unite"));
			$("#designation").val(i.attr("nom_unite"));
			$("#identifiant").val(i.attr("identifiant"));
			
			$("#add_edit_unite").modal('show');
			
		});
		
		$("#add_unite").click(function(){
			
			$("#title").text("Ajouter");
			
			$("#icone").attr("class","far fa-plus-square");
			
			var i = $(this);
			
			$("#id_unite").val(0);
			
			$(".clear").val("");
			
			$("#add_edit_unite").modal('show');
			
		});
					
});