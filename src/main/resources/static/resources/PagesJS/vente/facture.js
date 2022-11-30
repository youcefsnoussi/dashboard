
$(document).ready(function() {
			
	//$('#code_client').bind("enterKey",function(e){
	//-------------------------- CHECK CONNECTION TO SERVER
	
	checkConnection("commande"); //-------> CheckConnectionServer
	
	//-------------------------------------------------------------------------------
//	var rmse = $('option:selected', this).attr("rmse")
//	console.log("Selected remise :   "+rmse);
//	if(rmse && rmse=='true'){
//		parrent.find("#redux_art_pourc").attr("readonly", false);
//		parrent.find("#redux_art_val").attr("readonly", false);
//	}else{
//		parrent.find("#redux_art_pourc").attr("readonly", true);
//		parrent.find("#redux_art_val").attr("readonly", true);
//	}
	
	
	
	
	
	
	
	var remise_fact = $("#remise_fact").val();
	console.log("remise_fact : "+ remise_fact);
	if(remise_fact=='true'){
		$(".redux_art_pourc").attr("readonly", false);
		$(".redux_art_val").attr("readonly", false);
		$("#pourc_redux").attr("readonly", false);
		$("#mnt_redux").attr("readonly", false);
	}
	else{
		$(".redux_art_pourc").attr("readonly", true);
		$(".redux_art_val").attr("readonly", true);
		$("#pourc_redux").attr("readonly", true);
		$("#mnt_redux").attr("readonly", true);
	}
	
	let parrent = $("#tr20");
//	parrent.find('#art').attr("disabled", true);
	
	
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
	
	$(".select_consign").change(function() {
		
		$(this).parent().parent().find("#art_consign").val($(this).val());
		
	})

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
		
		parrent.find("#select_consign").find('option').remove();
		parrent.find("#select_consign").selectpicker('refresh');
		
		let id_rc_clt = $("#rc").val();
		
		//$.ajaxSetup({async: false});
		$.ajax({
			url: 'ajax_get_art_consign',
			//type: 'POST',
			dataType: 'json',
			data : {
				id_article	: id_art,
				id_rc_clt : id_rc_clt
	        },
	        success : function(responseJson) {
	        	
	        	let id_art = "";
	        	
				$.each(responseJson, function(key, value) {
					
					parrent.find("#select_consign").each(function(){
						
						$(this).append('<option value="'+value.article_consignation.id+'" selected> '+
								value.article_consignation.libelle+' </option>');
						
						id_art = id_art+value.article_consignation.id+","
						
					});
					
					
					
				});
				
				parrent.find("#select_consign").selectpicker('refresh');
				parrent.find("#art_consign").val(id_art);
				
			}
		});
		
		//-----------------------------------------------------
		/*
		console.log("art val = "+$(this).val())
		
		console.log("art attr before= "+$(this).attr("name"))
		*/
		if($(this).val()!="0"){
			
//			$(this).attr("name","art");
//			parrent.find('#id_magasin').attr("name","id_magasin");
			
		}
		else{
			
//			$(this).attr("name","");
			
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
		$("#select_veh").attr("name","");
		$("#select_veh").val("");
		$("#select_veh").attr('disabled',true);
		$("#select_veh").selectpicker('refresh');
		
		$("#select_mat").attr("name","matricule");
		$("#select_mat").attr('disabled',false);
		$("#select_mat").selectpicker('refresh');
		
		$("#input_mat").attr("name","");
		$("#input_mat").attr('disabled',true);
		updateVehicules();
		
	});
	
	$("#radio_input").click(function () {
		
		$("#select_mat").attr("name","");
		$("#select_mat").val("");
		$("#select_mat").attr('disabled',true);
		$("#select_mat").selectpicker('refresh');
		
		$("#select_veh").attr("name","");
		$("#select_veh").val("");
		$("#select_veh").attr('disabled',true);
		$("#select_veh").selectpicker('refresh');
		
		$("#input_mat").attr("name","matricule");
		$("#input_mat").attr('disabled',false);
		updateVehicules();
		
	});
	$("#radio_select_veh").click(function () {
		
		$("#select_mat").attr("name","");
		$("#select_mat").val("");
		$("#select_mat").attr('disabled',true);
		$("#select_mat").selectpicker('refresh');
		
		$("#select_veh").attr("name","matricule");
		$("#select_veh").attr('disabled',false);
		$("#select_veh").selectpicker('refresh');
		
		$("#input_mat").attr("name","");
		$("#input_mat").attr('disabled',true);
		
	});
	
	//----------------------------------------------------------------------
	
	$("#rc").on("change", function(){
		
		$(".remise_zero").val(0);
		
		$('.art').find('option:not(:first)').remove();
		$(".art").selectpicker('refresh');
		
		$(".select_consign").find('option').remove();
		$(".select_consign").selectpicker('refresh');
		
		$(".qte").attr("readonly", false);
		let parrent = $("#tr20");
		parrent.find('#qte').attr("readonly", true);
//		$(".id_magasin").empty();
		$(".id_magasin").attr("name","id_magasin");
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
//		 $("#select_veh").val("");
		 
		
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
					
					let options = new Array();
					
					$.each(responseJson, function(key, value) {
						/*
						if(value.id==(-1)){
							
							$("#redux").append("<span class='badge badge-secondary' id='art'> "+value.article.produit.designation+" "+
									value.article.emballage_produit.nom_emballage+" "+value.article.pesage_produit.pesage+
									value.article.pesage_produit.unite_pesage+"</span>")
							
						}
						*/
						
						let sub = (value.article.subvension==true) ? "Subventionné" : "NON Subventionné";
						
						/*if(value.article.code=="B0001" || value.article.code=="B0002") {console.log("pu",value.prix);}*/
						
						options.push('<option value="'+value.article.id+'" code="'+value.article.code+'" '+
								   'data-subtext=" ('+sub+')" um="'+value.article.unite_mesure_vente.nom_unite_mesure+'" '+
								   'pu="'+value.prix+'" tva="'+value.tva.taux_tva+'" id_um="'+value.article.unite_mesure_vente.id+'" '+
								   '">'+value.article.code+' | '+value.article.libelle+'</option>');
						
					});
					
					$(".art").append(options);
					$(".art").selectpicker('refresh');
					
					$(".art").find("option").hide();
					
				}
				
			}
		});
		
		//-----------------------------------------------------------
		
		updateVehicules();
			
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
//		$(".id_magasin").attr("name","id_magasin");
		var test = 0;
		var msg = "";
		
		var art_selected = [];
		var i = 0;
		var j = 0;
		$(".art").each(function() {
//			let tr = $(this).attr("id_tr");
//			let parrent = $("#"+tr);
//			parrent.find('#id_magasin').attr("name","id_magasin");
			if($(this).val() == "" || $(this).val() == "0"){}
			else{
				j=i;
				console.log("article : "+$(this).val());
				
				art_selected.push($(this).val());
				
			}
			i++;
		});
		$(".qte").each(function() {
			if( $(this).val()!=="" && parseFloat($(this).val()) <0 ){
				test ++;
				msg = msg+"<b>- Veuillez Vérifier les quantités des articles. </b><br>";
				 $(this).css("border-color","red");
			}
			
			
		});
		console.log("j : "+ j);
		console.log("art_selected.length : "+ art_selected.length);
		if(art_selected.length==1 && j==39 ){
			test++;
			msg = msg+"<b>- Veuillez faire une commande. </b><br>";
		}
		console.log(art_selected)
		
		
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
		if($("#select_mat").val()=="" && $("#input_mat").val()=="" && $("#select_veh").val()==""){
			
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
		if( $("#total_ttc").val()=="" || parseFloat($("#total_ttc").val()) ===0 || parseFloat($("#total_ttc").val()) <0 || isNaN(parseFloat($("#total_ttc").val()))){
		
			test++;
			msg = msg+"<b>- Veuillez faire une commande. </b><br>";
			$("#total_ttc").css("border-color","red");
			
		}
		
		//console.log("test ----->"+test)
		
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
							/*
							console.log("plafond_palette->"+responseJson.plafond_palette)
							
							if(responseJson.plafond_palette!=0){
								
								palette_plafond++;
								
								msg1 = msg1+"- Plafond Palette dépasser <br>";
								
							}
							*/
					}
					
				}
			});
			
			//console.log("-------------------------")
			
			//console.log("p_clt->"+client_plafond+" // p_rc ->"+rc_plafond)
			
			if(client_plafond==0 && rc_plafond==0 /*&& palette_plafond==0*/){
				
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
	
	
	$("#select_veh").on("change", function(){
		updateVehicules()
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



function updateVehicules(){

	var idWilaya =  $("#rc option:selected").attr("wilaya") ;	
	var comune =  $("#rc option:selected").attr("comune") ;	
	var type =  $("#select_veh option:selected").attr("type") ;	
	
	$("#type_vehicule").val(type);
	
	console.log("updateVehicules wilaya = "+idWilaya+" type = "+type +" comune = "+comune);
	
	if(!type || type==null){
		console.log("type null: " +type);
		let parrent = $("#tr20");
		var artselect = parrent.find('#art');
		
//		artselect.attr("name","art");

		parrent.find("#unite_mesure").val(0);
		parrent.find("#id_um").val(0);
		parrent.find("#prix_unitaire").val(0);
		parrent.find("#tva_art").val(0);
		parrent.find("#pesage_palette").val(0);
		
		
		parrent.find('#qte').val(0);
		
		parrent.find("#remise_zero").val(0);
		
		parrent.find("#art").find('option:not(:first)').remove();
		parrent.find("#art").selectpicker('refresh');
		
		parrent.find("#select_consign").find('option').remove();
		parrent.find("#select_consign").selectpicker('refresh');
		
//		parrent.find("#id_magasin").find('option').remove();
		parrent.find("#id_magasin").empty();
		
		
		
		CalculeHTArticle(parrent.find('#qte') );
		CalculeReductionArticle(parrent.find('#redux_art_val'), "valeur" );
		CalculeTvaTtcArticle(parrent.find('#tva_art'));
		calculeTotal ();
		
	}
	
	if(idWilaya && type && idWilaya!=null && type!=null){
	$.ajax({
		url: 'ajax_get_price_by_type',
		dataType: 'json',
		data : {
			idWilaya : idWilaya,
			type : type,
			comune : comune
        },
        success : function(responseJson) {
        	console.log("select_veh reponse prix : " +responseJson);
        	
	if(responseJson>0){
		var id= $("#transport").val();
		var code= $("#transportcode").val();
		var um= $("#transportum").val();
		var tva= $("#transporttva").val();
		var id_um= $("#transportid_um").val();
		var libelle= $("#transportlibelle").val();//{};
		
//		var id  = $("#transport").val();

		console.log("trsp id : "+ id);
		
		console.log("trsp code : "+ code);
//		
		
		let parrent = $("#tr20");
		
		console.log("transport id : "+transport.id);
		console.log("transport libelle : "+transport.libelle);
		
		
		
		
//		parrent.find('#art').append('<option value="'+50+'" selected="">'+'Transport'+'</option>');
		parrent.find('#art').append('<option value="'+id+'" code="'+code+'" '+
				   'data-subtext=" ('+sub+')" um="'+um+'" '+
				   'pu="'+responseJson+'" tva="'+tva+'" id_um="'+id_um+'" '+
				   '" selected="">'+code+' | '+libelle+'</option>');
		parrent.find('#art').selectpicker("refresh");
		
	
		var artselect = parrent.find('#art');
		
//		artselect.attr("name","art");

		parrent.find("#unite_mesure").val($('option:selected', artselect).attr("um"));
		parrent.find("#id_um").val($('option:selected', artselect).attr("id_um"));
		parrent.find("#prix_unitaire").val($('option:selected', artselect).attr("pu"));
		parrent.find("#tva_art").val($('option:selected', artselect).attr("tva"));
		parrent.find("#pesage_palette").val($('option:selected', artselect).attr("pp"));
		
		parrent.find('#qte').val(1);
		
		
		CalculeHTArticle(parrent.find('#qte') );
		CalculeReductionArticle(parrent.find('#redux_art_val'), "valeur" );
		CalculeTvaTtcArticle(parrent.find('#tva_art'));
		calculeTotal ();
		
		$.ajax({
			url: 'ajax_get_magasin_by_art',
			//type: 'POST',
			dataType: 'json',
			data : {
				id_article	: id
	        },
	        success : function(responseJson) {
	        	
				$.each(responseJson, function(key, value) {
					
					parrent.find("#id_magasin").each(function(){
						
						$(this).append('<option value="'+value.id+'"  selected> '+value.name+' </option>');
						
					});
					parrent.find('#id_magasin').attr("name","id_magasin");
					
				});
				
			}
		});
	}
	else{
		var matricule =  $("#select_veh option:selected").val() ;	
		$("#title").text("Erreur !!!");
		$("#icone").attr("class","far fa-exclamation-triangle");
		$("#text").html("Veuillez Ajouter un prix de transport pour le véhicule avec le matricule "+ matricule);
		$("#error").modal('show');
		resetTransport();
	}
    
    		
			
		}
	});
	}

}
function resetTransport(){
	
	$("#select_veh").val("");
	$("#select_veh").selectpicker('refresh');
	


	let parrent = $("#tr20");
	var artselect = parrent.find('#art');
	
//	artselect.attr("name","art");

	parrent.find("#unite_mesure").val(0);
	parrent.find("#id_um").val(0);
	parrent.find("#prix_unitaire").val(0);
	parrent.find("#tva_art").val(0);
	parrent.find("#pesage_palette").val(0);
	
	
	parrent.find('#qte').val(0);
	
	parrent.find("#remise_zero").val(0);
	
	parrent.find("#art").find('option:not(:first)').remove();
	parrent.find("#art").selectpicker('refresh');
	
	parrent.find("#select_consign").find('option').remove();
	parrent.find("#select_consign").selectpicker('refresh');
	
//	parrent.find("#id_magasin").find('option').remove();
	parrent.find("#id_magasin").empty();
	
	
	
	CalculeHTArticle(parrent.find('#qte') );
	CalculeReductionArticle(parrent.find('#redux_art_val'), "valeur" );
	CalculeTvaTtcArticle(parrent.find('#tva_art'));
	calculeTotal ();
	

	
}
		