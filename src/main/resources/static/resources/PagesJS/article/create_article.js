/**
 * 
 */
$(document).ready(function(){
				
	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = window.location.search.substring(1),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
	        }
	    }
	};
	
	var ret = getUrlParameter('ret');
	
	if(ret!=null){
		
		if(ret=="succes"){
			
			$("#title").text("Information");
			$("#icone").attr("class","far fa-info-circle");
			
			if($("#article").val()==""){
				$("#text").text("Article Ajouter avec succes ");
			}
			else{
				$("#text").text("Article Modifier avec succes ");
			}
			
			$("#error").modal('show');
			
		}
		else if(ret=="code_existe"){
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").text("Code article existant ");
			$("#error").modal('show');
			
		}
		else{
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").text("Un article existe avec les memes criteres ");
			$("#error").modal('show');
			
		}
		
	}
	
	//--------------------- init sous cat select --------------------
	
	update_sous_cat();
		
	//---------------------------------------
		
	update_prod();	
	
	//--------------------- init sous cat select --------------------
	
	//console.log($("#subvension"));
	
	$("#subvension").change(function(){
		
		if($(this).prop("checked")){
			
			$("#subvention").val("on");
			
		}
		else{
		
			$("#subvention").val("off");
			
		}
		
	});
	
	if($("#article").val()!=""){
		
		$("#frm").attr("action","edit_article");
		$("#cat_prod").val($("#id_cat_prod").val());
		update_sous_cat();
		$("#sous_cat_prod").val($("#id_sous_cat_prod").val());
		update_prod();
		$("#prod").val($("#id_prod").val());
		$("#emb_prod").val($("#id_emb_prod").val());
		$("#pesage_prod").val($("#id_pes_prod").val());
		$("#magasin").val($("#id_magasin").val());
		
		$("#code_art").val($("#code").val());
		$("#code_art").prop("readonly",true);
		
		$("#code_comptable").val($("#code_compt").val());
		$("#code_comptable").prop("readonly",true);
		
		//$("#tva").val($("#id_tva").val());
		$("#unite_mesure_vente").val($("#id_unite_mesure_vente").val());
		
		$("#lib").val($("#libelle").val());
		
		var units = $("#id_magasin").val().split("/");
		
		units.splice(-1,1);
		
		$("#magasin").val(units);
		
		$("#magasin").selectpicker('refresh');
		
		console.log("sub val -> "+$("#sub").val());
		
		if($("#sub").val()=="true"){
			
			$("#subvension").prop("checked",true);
			$("#subvention").val("on");
			
		}
		
		console.log("subvension val -> "+$("#subvention").val());
		
	}
	
	$("#cat_prod").change(function(){
		
		update_sous_cat();
		
		update_prod();	
		
	});
	
	$("#sous_cat_prod").change(function(){
		
		update_prod();	
		
	});
	//------------------------------
});
			
function update_sous_cat(){
	
	var id_cat_prod = $("#cat_prod").val();
	
	$("#sous_cat_prod").empty();
	
	$.ajaxSetup({async: false});
	$.ajax({
		url : 'get_sous_cat_prod',
		data : {
			id_cat_prod : id_cat_prod
		},
		success : function(responseJson) {
			
			if (responseJson != "null") {

				$.each(responseJson, function(key, value) {

					$("#sous_cat_prod").append('<option value="' + value.id + '">' + value.nom_sous_category + '</option>');

				});

			}
		}
	});
	
}

function update_prod(){
	
	var id_sous_cat_prod = $("#sous_cat_prod").val();
	
	$("#prod").empty();
	
	$.ajaxSetup({async: false});
	$.ajax({
		url : 'get_prod_sousCat',
		data : {
			id_sous_cat_prod : id_sous_cat_prod
		},
		success : function(responseJson) {
			
			if (responseJson != "null") {

				$.each(responseJson, function(key, value) {

					$("#prod").append('<option value="' + value.id + '">' + value.designation + '</option>');

				});

			}
		}
	});
	
}