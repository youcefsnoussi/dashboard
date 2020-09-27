$(document).ready(function() {
			
		$(".edit").click(function(){
			
			$("#title").text("modification");
			
			$("#icone").attr("class","far fa-edit");
			
			var i = $(this);
			
			$("#id_tva").val(i.attr("id_tva"));
			$("#tva").val(i.attr("taux_tva"));
			
			$("#new_tva").modal('show');
			
		});
		
		$("#add_taux").click(function(){
			
			$("#title").text("Ajouter");
			
			$("#icone").attr("class","far fa-plus-square");
			
			var i = $(this);
			
			$("#id_tva").val(0);
			
			$(".clear").val("");
			
			$("#new_tva").modal('show');
			
		});
			
});