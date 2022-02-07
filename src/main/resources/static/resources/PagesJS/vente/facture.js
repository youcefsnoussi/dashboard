
$(document).ready(function() {
			
	//$('#code_client').bind("enterKey",function(e){
	//-------------------------- CHECK CONNECTION TO SERVER
	
	checkConnection("commande"); //-------> CheckConnectionServer
	
	//-------------------------------------------------------------------------------
	
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
	
	var t;
	
	//if(type=="bl"){ t="print_bl"; }else{ t="print_ble"; }
	
	switch (type){
	
		case "bl" : { t="print_bl"; } break;
		
		case "ble" : { t="print_ble"; } break;
		
		case "bti" : { t="print_bti"; } break;
		
		case "blf" : { t="print_blf"; } break;
		
		case "blq" : { t="print_blq"; } break;
		
	}
	
	if(id_bl!=null){
		
		$('#print_content').html('<iframe id="frame" width="100%" height="828" frameborder="0" '+
				'src="'+t+'?id_bl='+id_bl+'"></iframe>');
		
		$("#title_bl").text("Détail BL N° "+num_bl);
		
		$("#icone_bl").attr("class","far fa-file-invoice");
		
		$("#bl_detail_modal").modal("show");
			
	}
	
	//---------------------------------- GET Matricules ---------------------------------------------
	
	$.ajax({
		url: 'get_matricules',
		//type: 'POST',
		dataType: 'json',
		data : {
			
        },
        success : function(responseJson) {
			
			if (responseJson != "null") {

				$.each(responseJson, function(key, value) {
					
					$("#select_mat").append('<option value="'+value+'" >'+value+'</option>');

				});
				
				$("#select_mat").selectpicker('refresh');
				$("#loading").remove();
			}
			
		}
	});
	
	//-------------------------------------------------------------------------------
	
	$("#new_payment").click(function(){
		
		$('#print_content_pay').html('<iframe id="frame" width="100%" height="900" frameborder="0" src="new_payment"></iframe>');
		
		$("#title_pay").text("Nouveau paiement");
		
		$("#icone_pay").attr("class","far fa-cash-register");
		
		$("#new_payment_modal").modal("show");
		
	});
	
	//----------------------------------------------------------------------
	
	$(".close_pay").click(function() {
		
		window.location.reload();
		
	});
	
	//----------------------------------------------------------------------
	
	$(".art").on("change", function(){
		
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
		/*
		console.log("art val = "+$(this).val())
		
		console.log("art attr before= "+$(this).attr("name"))
		*/
		if($(this).val()!="0"){
			
			$(this).attr("name","art");
			parrent.find('#id_magasin').attr("name","id_magasin");
			
		}
		else{
			
			$(this).attr("name","");
			
			parrent.find(".prix_unitaire").val(0);
			
			//parrent.find('#id_magasin').attr("name","");
			
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
		CalculeReductionArticle( $(this).parent().parent().parent().find("#redux_art_val"), "valeur" );
		CalculeTvaTtcArticle( $(this).parent().parent().parent().find("#tva_art") );
		calculeTotal ();
		
		
	});
	
	//----------------------------------RADIO FUNCTIONS------------------------------------
	
	$("#radio_select").click(function () {
		
		$("#select_mat").attr("name","matricule");
		$("#select_mat").attr('disabled',false);
		$("#select_mat").selectpicker('refresh');
		
		$("#input_mat").attr("name","");
		$("#input_mat").attr('disabled',true);
		
	});
	
	$("#radio_input").click(function () {
		
		$("#select_mat").attr("name","");
		$("#select_mat").val("");
		$("#select_mat").attr('disabled',true);
		$("#select_mat").selectpicker('refresh');
		
		$("#input_mat").attr("name","matricule");
		$("#input_mat").attr('disabled',false);
		
	});
	
	//----------------------------------------------------------------------
	
	$("#rc").on("change", function(){
		
		$(".remise_zero").val(0);
		
		$('.art').find('option:not(:first)').remove();
		$(".art").selectpicker('refresh');
		
		$(".qte").attr("readonly", false);
		$(".id_magasin").empty();
		$(".unite_mesure").val("");
		$(".nom_art").val("");
		
		$("#designation_rc").val($("#rc option:selected").attr("nom")+' '+$("#rc option:selected").attr("prenom"));
		$("#adresse_rc").val($("#rc option:selected").attr("adresse"));
		$("#solde_rc").val(parseFloat($("#rc option:selected").attr("sold")).formatMoney(2, '.', ' '));
		$("#max_solde_rc").val(parseFloat($("#rc option:selected").attr("plafond")).formatMoney(2, '.', ' '));
		$("#tv").val($("#rc option:selected").attr("tva"));
		$("#mode_reg").val( $("#rc option:selected").attr("mode_pay") );
		 
		if( $("#rc option:selected").attr("is_blf")=="true" ){
			 
		 $("#frm").prop("action","new_commande_bl_fact_post");
		 
	 	 $("#select_mat").attr("name","");
		 $("#select_mat").val("");
		 $("#select_mat").attr('disabled',true);
		 $("#select_mat").selectpicker('refresh');
		 
		 $("#input_mat").attr("name","matricule");
		 $("#input_mat").attr('disabled',false);
	     $("#radio_input").attr("checked","checked");
	     $("#radio_select").attr("disabled",true);
	     
	     $("#chauffeur").css("display","block");
	     $("#chauffeur_inp").attr("display","true");
		}
		else{
			 
		 $("#frm").prop("action","new_commande_post");
		 
		 $("#radio_select").attr("disabled",false);
		 
		 $("#chauffeur").css("display","none");
		 $("#chauffeur_inp").attr("display","false");
			 
		}
		 
		var id_rc_clt = $(this).val();
		 
		var exo = $("#rc option:selected").attr("tva");
		 
		if(exo==0){
			 
		 $("#exo").attr("class","badge badge-warning");
		 $("#exo").text("oui");
			 
		}
		else{
			 
		 $("#exo").attr("class","badge badge-secondary");
		 $("#exo").text("non");
			 
		}
		 
		//-------------------- Update articles -----------------
			
		$("#redux").empty();
		
		$.ajaxSetup({async: false});
		$.ajax({
			url: 'ajax_get_art_by_rc_cat',
			//type: 'POST',
			dataType: 'json',
			data : {
				id_rc_clt	:id_rc_clt,
	        },
	        success : function(responseJson) {
				
				if (responseJson != "null") {
					
					$.each(responseJson, function(key, value) {
						
						if(value.id==(-1)){
							
							$("#redux").append("<span class='badge badge-secondary' id='art'> "+value.article.produit.designation+" "+
									value.article.emballage_produit.nom_emballage+" "+value.article.pesage_produit.pesage+
									value.article.pesage_produit.unite_pesage+"</span>")
							
						}
						
						$(".art").each(function(){
							
							var sub = "Subventionné";
							
							if(value.article.subvension==false){
								
								sub = "NON Subventionné";
								
							}		
							
							$(this).append('<option value="'+value.article.id+'" code="'+value.article.code+'" '+
										   'data-subtext="'+value.article.code+' ('+sub+')" um="'+value.article.unite_mesure_vente.nom_unite_mesure+'" '+
										   'pu="'+value.prix+'" tva="'+value.tva.taux_tva+'" id_um="'+value.article.unite_mesure_vente.id+'" '+
										   'pp="'+value.article.pesagePalette+'">'+value.article.libelle+'</option>');
							
							$(this).selectpicker('refresh');
								
						});
						
					});
					
					$(".art").find("option").hide();
					
				}
				
			}
		});
		
		//-----------------------------------------------------------
		
		
			
	});
	
	//----------------------------------------------------------------------
	
	$(".qte").keyup(function(){
		
		CalculeHTArticle( $(this) );
		CalculeTvaTtcArticle( $(this).parent().parent().find("#tva_art") );
		calculeTotal ();
		
	});
	
	//____________________________________________> CALCULE REMISE <_____________________________________//
	
	$("#pourc_redux").keyup(function() {
		
		remiseGlobal($(this), "pourcentage");
		
	})
	
	$("#mnt_redux").keyup(function() {
		
		remiseGlobal($(this), "montant");
		
	})
	
	$(".redux_art_val").keyup(function() {
		
		CalculeReductionArticle( $(this), "valeur" );
		CalculeTvaTtcArticle( $(this).parent().parent().parent().find("#tva_art") );
		calculeTotal ();
		
	});
	
	$(".redux_art_pourc").keyup(function() {
		
		CalculeReductionArticle( $(this), "pourcentage" );
		CalculeTvaTtcArticle( $(this).parent().parent().parent().find("#tva_art") );
		calculeTotal ();
		
	});
	
	//____________________________________________> CALCULE REMISE <_____________________________________//
	
	//-------------------------------- Zero Blur -------------------------------------
	
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
	
	$("#fermer").click(function(){
		
		$('#code_client').focus();
		//$('#code_client').val("");
		
	});
	
	//-------------------------------- Zero Blur -------------------------------------
	
	//
	
	/*
	console.log("mode_reg == "+$("#mode_reg").val());
	
	console.log("rc == "+$("#rc").val());
	
	console.log("art == "+$(".art").val());
	*/
	$("#sub").click(function(){
		
		//console.log("select val -> "+$("#select_mat").val())
		
		var art_selected = [];
		
		$(".art").each(function() {
			
			if($(this).val() == "" || $(this).val() == "0"){}
			else{
				
				art_selected.push($(this).val());
				
			}
			
		});
		
		console.log(art_selected)
		
		var test = 0;
		
		var msg = "";
		
		if(if_duplicate_value(art_selected)==true){
			
			test++;
			msg = msg+"<b>- Article Dupliqué. </b><br>";
			$("#code_client").css("border-color","red");
			
		}
		
		if($("#code_client").val()==""){
			
			test++;
			msg = msg+"<b>- Code client incorrect. </b><br>";
			$("#code_client").css("border-color","red");
			
		}
		/*
		if($("#mode_reg").val()==null){
			
			test++;
			msg = msg+"- Selectionner un mode de regelement. <br>";
			//$("#mode_reg").css("border-color","red");
			$(".bs-placeholder").find('[data-id=mode_reg]').css("border-color","red");
			
		}
		*/
		if($("#select_mat").val()=="" && $("#input_mat").val()=="" ){
			
			test++;
			msg = msg+"<b>- Matricule Vide. </b><br>";
			$("#matricule").css("border-color","red");
			
		}
		
		if($("#chauffeur_inp").attr("display")=="true" && $("#chauffeur_inp").val()=="" ){
			
			test++;
			msg = msg+"<b>- Chauffeur Vide. </b><br>";
			$("#matricule").css("border-color","red");
			
		}
		
		if($("#rc").val()==""){
			
			test++;
			msg = msg+"<b>- Selectionner un Registre de commerce. </b><br>";
			//$("#rc").css("border-color","red");
			$(".bs-placeholder").find('[data-id=rc]').css("border-color","red");
			
		}
		
		//if($("#total_ttc").val()=="" || $("#total_ttc").val()=="0"){
		if( $("#total_ttc").val()=="" || parseFloat($("#total_ttc").val()) ===0 || isNaN(parseFloat($("#total_ttc").val())) ){
		
			test++;
			msg = msg+"<b>- Veuillez faire une commande. </b><br>";
			$("#total_ttc").css("border-color","red");
			
		}
		
		console.log("test ----->"+test)
		
		if(test===0){
			
			var client_plafond = 0;
			
			var rc_plafond = 0;
			
			var palette_plafond = 0;
			
			var msg1 = "";
			
			var mnt_ttc = parseFloat( $("#total_ttc").val().replace(" ","") );
			
			$.ajaxSetup({async: false});
			$.ajax({
				url: 'ajax_test_plafond',
				//type: 'POST',
				dataType: 'json',
				data : {
					id_rc_clt : $("#rc").val(),
					montant_ttc : $("#total_ttc").val().replaceAll(' ',''),
					nbr_palette : calculeNbrPalette()
		        },
		        success : function(responseJson) {
					
					if (responseJson != "null") {
							
							console.log("plafond_client->"+responseJson.plafond_client)
						
							if(responseJson.plafond_client!=0){
								
								client_plafond++;
								
								msg1 = msg1+"- Plafond client dépasser <br>";
								
							}
							
							console.log("plafond_rc->"+responseJson.plafond_rc)
							
							if(responseJson.plafond_rc!=0){
								
								rc_plafond++;
								
								msg1 = msg1+"- Plafond RC dépasser <br>";
								
							}
							
							console.log("plafond_palette->"+responseJson.plafond_palette)
							
							if(responseJson.plafond_palette!=0){
								
								palette_plafond++;
								
								msg1 = msg1+"- Plafond Palette dépasser <br>";
								
							}
						
					}
					
				}
			});
			
			console.log("-------------------------")
			
			console.log("p_clt->"+client_plafond+" // p_rc ->"+rc_plafond)
			
			if(client_plafond==0 && rc_plafond==0 && palette_plafond==0){
				
				console.log("-----------------> SUBMIT")
				
				spin_it('on');
				
				$("#sub").prop("disabled","true");
				
				$("#frm").submit();
				
			}
			else{
				
				$("#title").text("Erreur !!!");
				$("#icone").attr("class","far fa-exclamation-triangle");
				$("#text").html(msg1);
				$("#error").modal('show');
				
			}
			
		}
		else{
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").html(msg);
			$("#error").modal('show');
			
		}
		
	});
	
});

//----------------------------------- Functions

function CalculeReductionArticle(input, type){
	
	let tds = $(input).parent().parent().parent();
	
	let val_intro = ($(input).val() !== undefined) ? parseFloat($(input).val()) : 0;
	
	let val = 0;
	
	let pourc = 0;
	
	let mntHT = parseFloat( tds.find("#montant_ht").val().replaceAll(" ","") );
	
	if(type === "pourcentage"){
		
		pourc = val_intro;
		
		val = mntHT * (pourc / 100);
		
		$(input).parent().find("#redux_art_val").val(val);
		
	}
	else{
		
		val = val_intro;
		
		pourc = (mntHT !== 0 ) ? (val * 100) / mntHT : 0;
		
		$(input).parent().find("#redux_art_pourc").val(pourc);
		
	}
	
	tds.find("#montant_net_ht").val( ( mntHT - val ).formatMoney(2, '.', ' ') );
}	

function CalculeHTArticle (input){
	
	let tds = $(input).parent().parent();
	
	let quant = parseFloat( $(input).val() );
	
	let prixUHT = parseFloat( tds.find("#prix_unitaire").val() );
	
	tds.find("#montant_ht").val ( (quant * prixUHT).formatMoney(2, '.', ' ') );
	
	tds.find("#montant_net_ht").val ( (quant * prixUHT).formatMoney(2, '.', ' ') );
	
}

function CalculeTvaTtcArticle(input){
	
	let tds = $(input).parent().parent();
	
	let mntHTNet = parseFloat( tds.find("#montant_net_ht").val().replaceAll(" ","") );
	
	let tva = ($(input).val() !== undefined) ? parseFloat($(input).val()) : 0;
	
	let mntTVA = mntHTNet * (tva / 100);
	
	tds.find("#montant_tva").val ( (mntTVA).formatMoney(2, '.', ' ') );
	
	tds.find("#montant_ttc").val ( (mntHTNet + mntTVA).formatMoney(2, '.', ' ') );
	
	
	
}

function calculeTotal (){
	
	let totalHT = 0;
	
	let totalHTNet = 0;
	
	let totalTVA = 0;
	
	let totalTTC = 0;
	
	let totalReduction = 0;
	
	$(".montant_ht").each(function() {
		totalHT += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$(".montant_net_ht").each(function() {
		totalHTNet += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$(".montant_tva").each(function() {
		totalTVA += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$(".montant_ttc").each(function() {
		totalTTC += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$(".redux_art_val").each(function() {
		totalReduction += parseFloat( $(this).val().replaceAll(" ","") );
	});
	
	$("#total_ht").val( (totalHT).formatMoney(2, '.', ' ') );
	
	$("#total_ht_net").val( (totalHTNet).formatMoney(2, '.', ' ') );
	
	$("#total_tva").val( (totalTVA).formatMoney(2, '.', ' ') );
	
	$("#total_ttc").val( (totalTTC).formatMoney(2, '.', ' ') );
	
	$("#mnt_redux").val( (totalReduction).formatMoney(2, '.', ' ') );
	
	$("#pourc_redux").val( ( (totalReduction*100)/totalHT ).formatMoney(8, '.', ' ') );
	
}

function remiseGlobal(input, type){
	
	let val_intro = ( $(input).val() !== undefined) ? parseFloat($(input).val()) : 0;
	
	let val = 0;
	
	let pourc = 0;
	
	let mntHT = parseFloat( $("#total_ht").val().replaceAll(" ","") );
	
	if(type === "pourcentage"){
		
		pourc = val_intro;
		
		val = mntHT * (pourc / 100);
		
		$("#mnt_redux").val( val.formatMoney(8, '.', ' ') );
		
	}
	else{
		
		val = val_intro;
		
		pourc = (val * 100) / mntHT;
		
		$("#pourc_redux").val( pourc.formatMoney(8, '.', ' ') );
		
		//console.log(" after attribution");
		
	}
	
	$("#total_ht_net").val( (mntHT - val).formatMoney(2, '.', ' ') );
	
	let pourcArt = (val/mntHT)*100;
	
	$(".redux_art_pourc").each(function() {
		
		$(this).val(pourcArt.formatMoney(8, '.', ' '));
		CalculeReductionArticle($(this), "pourcentage");
		CalculeTvaTtcArticle( $(this).parent().parent().parent().find("#tva_art") );
		
	});
	
	let total_tva = 0;
	
	$(".montant_tva").each(function() {
		
		total_tva += ( $(this).val() !== undefined) ? parseFloat( $(this).val().replaceAll(" ","") ) : 0;
		
	});
	
	$("#total_tva").val( total_tva.formatMoney(2, '.', ' ') );
	
	$("#total_ttc").val( ((mntHT - val) + total_tva).formatMoney(2, '.', ' ') );
	
}

//---------------------------------------------- Calcule

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

function calculeNbrPalette(){
	
	var nbrP = 0;
	
	$(".qte").each(function() {
		
		console.log("------>enter each<-----------")
		
		if($(this).val()!="0"){
			
			console.log("enter if and value ->"+parseFloat($(this).val()))
			
			nbrP += parseFloat($(this).val()) / parseFloat($(this).parent().find("#pesage_palette").val());
			
		}
		
	});
	
	let result =  ( Number.isFinite(nbrP) === false ) ? 0 : nbrP;
	
	console.log("-------------------------------> test c p ",result)
	
	return result;
	
}
		