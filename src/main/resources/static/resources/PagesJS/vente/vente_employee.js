$(document).ready(function() {
	
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
	
	var id_bl = getUrlParameter('id_bl');
	
	var num_bl = getUrlParameter('num_bl');
	
	var type = getUrlParameter('type');
	
	var t = "print_ble";
	
	if(id_bl!=null){
		
		$('#print_content').html('<iframe id="frame" width="100%" height="828" frameborder="0" '+
				'src="'+t+'?id_bl='+id_bl+'"></iframe>');
		
		$("#title_bl").text("Détail BL N° "+num_bl);
		
		$("#icone_bl").attr("class","far fa-file-invoice");
		
		$("#bl_detail_modal").modal("show");
			
	}
	
	$("#employee").change(function() {
		
		 $("#nom").val($("#employee option:selected").attr("nom"));
		 $("#prenom").val($("#employee option:selected").attr("prenom"));
		
	});
	
	$(".art").on("change",function(){
		
		let tr = $(this).attr("id_tr");
		
		let parrent = $("#"+tr);
		
		//-------------------- get unite chargement by art -----------------
		
		let id_art = $('option:selected', this).val();
		
		parrent.find('#id_magasin').empty();
		
		$.ajaxSetup({async: false});
		$.ajax({
			url: 'ajax_get_magasin_by_art',
			//type: 'POST',
			dataType: 'json',
			data : {
				id_article	: id_art
	        },
	        success : function(responseJson) {
	        	
				$.each(responseJson, function(key, value) {
					
					parrent.find("#id_magasin").each(function(){
						
						$(this).append('<option value="'+value.id+'" > '+value.name+' </option>');
						
					});
					
				});
				
			}
		});
		
		//-----------------------------------------------------
		if($(this).val()!="0"){
			
			$(this).attr("name","art");
			parrent.find('#id_magasin').attr("name","id_magasin");
			
		}
		else{
			
			$(this).attr("name","");
			
			parrent.find(".prix_unitaire").val(0);
			
		}
		
		//$("#pourc_redux").val(0);
		//$("#mnt_redux").val(0);
		
		
		parrent.find("#code_art").val($('option:selected', this).attr("code"));
		parrent.find("#unite_mesure").val($('option:selected', this).attr("um"));
		parrent.find("#id_um").val($('option:selected', this).attr("id_um"));
		parrent.find("#prix_unitaire").val($('option:selected', this).attr("pu"));
		parrent.find("#tva_art").val($('option:selected', this).attr("tva"));
		parrent.find("#pesage_palette").val($('option:selected', this).attr("pp"));
		//parrent.find("#qte").val(0);
		
		CalculeHTArticle( $(this).parent().parent().parent().find("#qte") );
		//CalculeReductionArticle( $(this).parent().parent().parent().find("#redux_art_val"), "valeur" );
		CalculeTvaTtcArticle( $(this).parent().parent().parent().find("#tva_art") );
		calculeTotal ();
		
		
	});
	
	$(".qte").keyup(function() {
		
		CalculeHTArticle ($(this));
		CalculeTvaTtcArticle($(this).parent().parent().find("#tva_art"));
		calculeTotal ();
		
	});
	
	$(".zero").focus(function(){
		
		if($(this).val()==0){
			
			$(this).val("");
			
		}
		
	});
	
	$(".zero").blur(function(){
		
		if($(this).val()==""){
			
			$(this).val(0);
			
		}
		
	});
	
	$("#sub").click(function(){
		
		var art_selected = [];
		
		$(".art").each(function() {
			
			if($(this).val() == "" || $(this).val() == "0"){}
			else{
				
				art_selected.push($(this).val());
				
			}
			
		});
		
		var test = 0;
		
		var msg = "";
		
		if(if_duplicate_value(art_selected)==true){
			
			test++;
			msg = msg+"<b>- Article Dupliqué. </b><br>";
			$("#code_client").css("border-color","red");
			
		}
		
		console.log("->"+$("#employee").val())
		
		if($("#employee").val() == null){
			
			test++;
			msg = msg+"<b>- Selectionner un Employee. </b><br>";
			//$("#rc").css("border-color","red");
			$(".bs-placeholder").find('[data-id=rc]').css("border-color","red");
			
		}
		
		if($("#total_ttc").val()=="" || $("#total_ttc").val()=="0"){
			
			test++;
			msg = msg+"<b>- Veuillez faire une commande. </b><br>";
			$("#total_ttc").css("border-color","red");
			
		}
		
		if(test==0){
			
			$("#sub").prop("disabled","true");
			
			$("#frm").submit();
			
		}
		else{
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").html(msg);
			$("#error").modal('show');
			
		}
		
	});
	
});

function CalculeHTArticle (input){
	
	let tds = $(input).parent().parent();
	
	let quant = parseFloat( $(input).val() );
	
	let prixUHT = parseFloat( tds.find("#prix_unitaire").val() );
	
	tds.find("#montant_ht").val ( (quant * prixUHT).formatMoney(2, '.', ' ') );
	
	tds.find("#montant_net_ht").val ( (quant * prixUHT).formatMoney(2, '.', ' ') );
	
}

function CalculeTvaTtcArticle(input){
	
	let tds = $(input).parent().parent();
	
	let mntHTNet = parseFloat( tds.find("#montant_ht").val().replaceAll(" ","") );
	
	let tva = ($(input).val() !== undefined) ? parseFloat($(input).val()) : 0;
	
	let mntTVA = mntHTNet * (tva / 100);
	
	tds.find("#montant_tva").val ( (mntTVA).formatMoney(2, '.', ' ') );
	
	tds.find("#montant_ttc").val ( (mntHTNet + mntTVA).formatMoney(2, '.', ' ') );
	
	
	
}

function calculeTotal (){
	
	let totalHT = 0;
	
	let totalTVA = 0;
	
	let totalTTC = 0;
	
	$(".montant_ht").each(function() {
		totalHT += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$(".montant_tva").each(function() {
		totalTVA += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$(".montant_ttc").each(function() {
		totalTTC += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$("#total_ht").val( (totalHT).formatMoney(2, '.', ' ') );
	
	$("#total_tva").val( (totalTVA).formatMoney(2, '.', ' ') );
	
	$("#total_ttc").val( (totalTTC).formatMoney(2, '.', ' ') );
	
}


function if_duplicate_value (arr){
	
	for(i=0;i<arr.length-1;i++){
		
		for(j=i+1;j<arr.length;j++){
			
			if(arr[i]==arr[j]){
				
				return true;
				
			}
			
		}
		
	}
	
	return false;
	
}