var categorySaleLimits = {};
var rowCategoryIndex = {};
var categorySalesLoadState = {};
var commandeBlockingState = {
	stock: {},
	limit: {},
	limitZero: {}
};

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
	
	
	$("#yesButtun").click(function() {
		submit();
		$("#transport_confirmation").modal('hide');
	});
	$("#noButtun").click(function() {
		
		$("#transport_confirmation").modal('hide');
		
	});
	
	
	
	
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
		var rowId = parrent.attr("id");
		setBlockingAlert('stock', rowId, false);
		setBlockingAlert('limit', rowId, false);
		setBlockingAlert('limitZero', rowId, false);
		if(rowId){
			registerRowCategory(rowId, null);
		}
		
		
		//-------------------- get unite chargement by art -----------------
	
		var selectedOption = $('option:selected', this);
		let id_art = selectedOption.val();
		parrent.data("selected-article", id_art);
		var venteBaseStock = selectedOption.attr('data-vente-stock') === 'true';
		var categoryId = selectedOption.attr('data-category-id');
		registerRowCategory(rowId, categoryId);
		var canEnforce = shouldEnforceLimit(venteBaseStock);
		console.log("[commande] conditions -> cat:", window.rcCategoryId || 'N/A', "| venteBaseStock:", venteBaseStock, "| enforce:", canEnforce);
		parrent.data("vente-base-stock", venteBaseStock ? 'true' : 'false');
		fetchStockJournalier(id_art, rowId);
		fetchCategorySoldQuantity(categoryId, venteBaseStock, rowId);
		
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
			registerRowCategory(rowId, null);
			parrent.removeData("selected-article");
			parrent.removeData("vente-base-stock");
			clearAlertsForRow(rowId);
			
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
		
		const selectedOption = $("#rc option:selected");
		const categoryId = selectedOption.attr("category_id");
		const categoryName = selectedOption.attr("category_nom");
		window.rcCategoryId = categoryId;
		console.log("[commande] Client category =>", categoryId || "N/A", categoryName || "N/A");
		
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
	
		$("#max_solde_rc").val(parseFloat($("#rc option:selected").attr("plafond")).formatMoney(2, '.', ' '));
		$("#tv").val($("#rc option:selected").attr("tva"));
		$("#mode_reg").val( $("#rc option:selected").attr("mode_pay") );
		 
		if( $("#rc option:selected").attr("is_blf")=="true" ){
			 
		 $("#frm").prop("action","new_commande_bl_fact_post");
		 
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
	     $("#radio_input").attr("checked","checked");
	     $("#radio_select").attr("disabled",true);
	     $("#radio_select_veh").attr("disabled",true);
	     
	     
	     
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
		 
		
		console.log("call ajax id_rc_clt>>",id_rc_clt)
		$("#solde_rc").val(0);

		$.ajaxSetup({async: false});
		$.ajax({
			url: 'ajax_get_solde_rc',
			type: 'GET',
			dataType: 'json',
			data : {
				id	: id_rc_clt
	        },
	        success : function(responseJson) {
	        	console.log("responseJson>> ",responseJson);
	        	$("#solde_rc").val(parseFloat(responseJson).formatMoney(2, '.', ' '));
	        	
			}
		});
		
		
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

						var category = value.article && value.article.produit
							? value.article.produit.sous_category_produit.category_produit : null;
						var venteBaseStockFlag = category ? category.venteBaseStock : false;
						var pourcentageCat = category ? category.pourcentageVente : 0;
						console.log("art option -> ", value.article.code+' | '+venteBaseStockFlag+' | '+pourcentageCat);
					
						options.push('<option value="'+value.article.id+'" code="'+value.article.code+'" '+
						   'data-subtext=" ('+sub+')" um="'+value.article.unite_mesure_vente.nom_unite_mesure+'" '+
						   'pu="'+value.prix+'" tva="'+value.tva.taux_tva+'" id_um="'+value.article.unite_mesure_vente.id+'" '+
						   'data-vente-stock="'+venteBaseStockFlag+'" data-category-id="'+(category ? category.id : '')+'" data-category-pourcentage="'+pourcentageCat+'">'+
						   value.article.code+' | '+value.article.libelle+'</option>');
						
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
		console.log("qte changed");
		enforceCategoryLimit($(this));
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
		if(hasBlockingAlerts()){
			showCommandeValidationBlockedAlert();
			return;
		}
		var matricule =  $("#select_veh option:selected").val() ;
		var hasTransport = $("#hasTransport").val() ;
		console.log("hasTransport : "+hasTransport);
		
		console.log("matricule vhicule >> ",matricule);
		if(hasTransport=='true' && (matricule== undefined || matricule==null || matricule=="")){
			$("#transport_confirmation").modal('show');
		}else{
			submit();
		}
		
	});
	
	
	$("#select_veh").on("change", function(){
		updateVehicules()
	});
	
});


//----------------------------------- Functions

function setBlockingAlert(alertType, rowId, value){
	if(!rowId || !commandeBlockingState[alertType]){
		return;
	}
	commandeBlockingState[alertType][rowId] = !!value;
}

function clearAlertsForRow(rowId){
	if(!rowId){
		return;
	}
	delete commandeBlockingState.stock[rowId];
	delete commandeBlockingState.limit[rowId];
	delete commandeBlockingState.limitZero[rowId];
}

function hasBlockingAlerts(){
	return Object.keys(commandeBlockingState.stock).some(function(key){ return commandeBlockingState.stock[key]; }) ||
		Object.keys(commandeBlockingState.limit).some(function(key){ return commandeBlockingState.limit[key]; }) ||
		Object.keys(commandeBlockingState.limitZero).some(function(key){ return commandeBlockingState.limitZero[key]; });
}

function fetchStockJournalier(idArticle, rowId){
	if(!idArticle || idArticle === "0"){
		if(rowId){
			registerRowCategory(rowId, null);
			clearAlertsForRow(rowId);
		}
		return;
	}
	if(!shouldEnforceLimit(isRowUnderVenteStock(rowId))){
		if(rowId){
			setBlockingAlert('stock', rowId, false);
			setBlockingAlert('limit', rowId, false);
			setBlockingAlert('limitZero', rowId, false);
		}
		return;
	}

	$.getJSON('ajax_get_stock_journalier', { id_article: idArticle })
		.done(function(response) {
			var qty = (response && response.quantite_stock !== undefined) ? parseFloat(response.quantite_stock) : 0;
			var date = (response && response.date) ? response.date : 'N/A';
			var pourc = (response && response.pourcentage_vente !== undefined) ? parseFloat(response.pourcentage_vente) : 0;
			var allowed = (isFinite(qty) && isFinite(pourc)) ? qty * (pourc / 100) : 0;
			allowed = (!isFinite(allowed) || allowed < 0) ? 0 : allowed;
			var categoryId = (response && response.category_id !== undefined && response.category_id !== null)
				? response.category_id.toString()
				: getRowCategoryId(rowId);
			if(rowId){
				registerRowCategory(rowId, categoryId);
			}
			if(categoryId){
				upsertCategorySaleLimit(categoryId, { allowed: allowed });
				refreshCategoryRows(categoryId);
				fetchCategorySoldQuantity(categoryId, isRowUnderVenteStock(rowId), rowId);
			}
			var needsStockAlert = rowId && isRowUnderVenteStock(rowId) && qty <= 0;
			if(rowId){ setBlockingAlert('stock', rowId, needsStockAlert); }
			if(needsStockAlert){
				showStockJournalierAlert(rowId);
			}
			console.log("[commande] Stock journalier => article:", idArticle, "| categorie:", categoryId || 'N/A', "| quantite:", qty, "| % vente:", pourc, "| date:", date);
			console.log("[commande] Seuil de vente autorisé => categorie:", categoryId || 'N/A', "| limite brute:", allowed);
		})
		.fail(function(){
			console.warn("[commande] Impossible de récupérer le stock journalier pour l'article", idArticle);
		});
}

function fetchCategorySoldQuantity(categoryId, venteBaseStockFlag, rowId){
	if(!categoryId){
		return;
	}
	var normalizedCategoryId = categoryId.toString();
	if(!shouldEnforceLimit(venteBaseStockFlag)){
		if(rowId){
			setBlockingAlert('limit', rowId, false);
			setBlockingAlert('limitZero', rowId, false);
		}
		return;
	}

	var today = new Date();
	var dateString = today.getFullYear() + '-' + String(today.getMonth()+1).padStart(2, '0') + '-' + String(today.getDate()).padStart(2, '0');
	var cacheKey = normalizedCategoryId + '|' + dateString;
	if(categorySalesLoadState[cacheKey] === 'loaded'){
		refreshCategoryRows(normalizedCategoryId);
		return;
	}
	if(categorySalesLoadState[cacheKey] === 'pending'){
		return;
	}
	categorySalesLoadState[cacheKey] = 'pending';

	$.getJSON('ajax_get_article_sold_quantity', { id_category: normalizedCategoryId, date: dateString })
		.done(function(response){
			var qty = (response && response.quantite_totale !== undefined) ? parseFloat(response.quantite_totale) : 0;
			var sold = (isFinite(qty) && qty > 0) ? qty : 0;
			var baselineQty = getCategoryBaselineQuantity(normalizedCategoryId);
			var adjustedSold = sold - baselineQty;
			adjustedSold = (!isFinite(adjustedSold) || adjustedSold < 0) ? 0 : adjustedSold;
			upsertCategorySaleLimit(normalizedCategoryId, { sold: adjustedSold });
			categorySalesLoadState[cacheKey] = 'loaded';
			refreshCategoryRows(normalizedCategoryId);
			console.log("[commande] Quantité vendue => categorie:", normalizedCategoryId, "| date:", (response && response.date) || dateString, "| quantite:", sold, "| baseline:", baselineQty, "| ajustee:", adjustedSold);
		})
		.fail(function(){
			delete categorySalesLoadState[cacheKey];
			console.warn("[commande] Impossible de récupérer la quantité vendue pour la catégorie", normalizedCategoryId);
		});
}
function getEditBaselineForRow(rowId){
	if(!rowId){
		return null;
	}
	var row = $("#"+rowId);
	if(!row.length || !row.data("is-edit-baseline")){
		return null;
	}
	var initialArticle = row.data("initial-article");
	var initialQuantity = row.data("initial-quantity");
	if(initialArticle === undefined || initialQuantity === undefined){
		return null;
	}
	var parsedQuantity = parseFloat(initialQuantity);
	if(!isFinite(parsedQuantity) || parsedQuantity <= 0){
		return null;
	}
	return {
		article: initialArticle.toString(),
		quantity: parsedQuantity
	};
}

function shouldEnforceLimit(venteBaseStockFlag){
	var cat = window.rcCategoryId;
	var allowedCategory = (cat === '1' || cat === '5');
	var allowed = allowedCategory && venteBaseStockFlag;
	console.log("[commande] contrôle limite => cat:", cat || 'N/A', "| allowedCat:", allowedCategory, "| venteBaseStock:", venteBaseStockFlag, "| enforce:", allowed);
	return allowed;
}

function isRowUnderVenteStock(rowId){
	if(!rowId){
		return false;
	}
	var flag = $("#"+rowId).data("vente-base-stock");
	return flag === 'true';
}

function upsertCategorySaleLimit(categoryId, updates){
	if(!categoryId){
		return;
	}
	var normalized = categoryId.toString();
	var entry = categorySaleLimits[normalized] || {};
	if(updates){
		if(updates.allowed !== undefined){
			var allowedValue = parseFloat(updates.allowed);
			entry.allowed = (isFinite(allowedValue) && allowedValue >= 0) ? allowedValue : 0;
		}
		if(updates.sold !== undefined){
			var soldValue = parseFloat(updates.sold);
			entry.sold = (isFinite(soldValue) && soldValue >= 0) ? soldValue : 0;
		}
	}
	var allowed = (entry.allowed !== undefined && isFinite(entry.allowed)) ? entry.allowed : 0;
	var sold = (entry.sold !== undefined && isFinite(entry.sold)) ? entry.sold : 0;
	entry.limit = Math.max(allowed - sold, 0);
	categorySaleLimits[normalized] = entry;
	console.log("[commande] Limite recalculée => catégorie:", normalized, "| autorisé:", allowed, "| vendu:", sold, "| reste:", entry.limit);
}

function updateLimitZeroState(rowId){
	if(!rowId){
		return;
	}
	var categoryId = getRowCategoryId(rowId);
	if(!categoryId){
		setBlockingAlert('limitZero', rowId, false);
		return;
	}
	var entry = categorySaleLimits[categoryId];
	if(!entry){
		setBlockingAlert('limitZero', rowId, false);
		return;
	}
	var limitValue = (entry.limit !== undefined) ? parseFloat(entry.limit) : NaN;
	var allowedCapacity = (entry.allowed !== undefined) ? parseFloat(entry.allowed) : NaN;
	var zeroCapacity = isRowUnderVenteStock(rowId) && ((isFinite(limitValue) && limitValue <= 0) || (!isFinite(allowedCapacity) || allowedCapacity <= 0));
	setBlockingAlert('limitZero', rowId, zeroCapacity);
}

function enforceCategoryLimit(input){
	var row = $(input).closest('tr');
	var rowId = row.attr('id');
	if(!shouldEnforceLimit(isRowUnderVenteStock(rowId))){
		setBlockingAlert('limit', rowId, false);
		setBlockingAlert('stock', rowId, false);
		setBlockingAlert('limitZero', rowId, false);
		return;
	}

	var categoryId = getRowCategoryId(rowId);
	if(!categoryId){
		setBlockingAlert('limit', rowId, false);
		setBlockingAlert('limitZero', rowId, false);
		return;
	}
	var entry = categorySaleLimits[categoryId];
	if(!entry){
		setBlockingAlert('limit', rowId, false);
		setBlockingAlert('limitZero', rowId, false);
		return;
	}
	var allowedStock = (entry.allowed !== undefined) ? parseFloat(entry.allowed) : NaN;
	var zeroStock = isRowUnderVenteStock(rowId) && (!isFinite(allowedStock) || allowedStock <= 0);
	setBlockingAlert('stock', rowId, zeroStock);
	if(zeroStock){
		showStockJournalierAlert(rowId);
		return;
	}
	var limitValue = (entry.limit !== undefined) ? parseFloat(entry.limit) : NaN;
	if(!isFinite(limitValue)){
		setBlockingAlert('limit', rowId, true);
		setBlockingAlert('limitZero', rowId, false);
		return;
	}
	if(limitValue <= 0){
		setBlockingAlert('limitZero', rowId, true);
		setBlockingAlert('limit', rowId, false);
		showLimitConsumedAlert(rowId);
		$(input).val(0);
		return;
	}
	setBlockingAlert('limitZero', rowId, false);
	var requested = computeCategoryRequestedQuantity(categoryId);
	if(requested > limitValue){
		setBlockingAlert('limit', rowId, true);
		showLimitAlert(limitValue);
	} else {
		setBlockingAlert('limit', rowId, false);
	}
}

function registerRowCategory(rowId, categoryId){
	if(!rowId){
		return;
	}
	var row = $("#"+rowId);
	if(categoryId === undefined || categoryId === null || categoryId === ''){
		delete rowCategoryIndex[rowId];
		if(row.length){
			row.removeData("category-id");
		}
		return;
	}
	var normalized = categoryId.toString();
	rowCategoryIndex[rowId] = normalized;
	if(row.length){
		row.data("category-id", normalized);
	}
}

function getRowCategoryId(rowId){
	if(!rowId){
		return null;
	}
	var row = $("#"+rowId);
	if(row.length){
		var stored = row.data("category-id");
		if(stored !== undefined && stored !== null && stored !== ''){
			return stored.toString();
		}
	}
	var fallback = rowCategoryIndex[rowId];
	return (fallback !== undefined && fallback !== null) ? fallback.toString() : null;
}

function refreshCategoryRows(categoryId){
	if(!categoryId){
		return;
	}
	var normalized = categoryId.toString();
	$(".art").each(function(){
		var rowId = $(this).attr("id_tr");
		if(!rowId){
			return;
		}
		if(getRowCategoryId(rowId) === normalized){
			updateLimitZeroState(rowId);
			var qteInput = $("#"+rowId).find('#qte');
			if(qteInput.length){
				enforceCategoryLimit(qteInput.get(0));
			}
		}
	});
}

function computeCategoryRequestedQuantity(categoryId){
	if(!categoryId){
		return 0;
	}
	var normalized = categoryId.toString();
	var total = 0;
	$(".art").each(function(){
		var rowId = $(this).attr("id_tr");
		if(!rowId){
			return;
		}
		if(getRowCategoryId(rowId) !== normalized){
			return;
		}
		var row = $("#"+rowId);
		var value = row.find('#qte').val();
		var numeric = parseFloat(String(value).replace(/\s+/g, ''));
		if(isFinite(numeric) && numeric > 0){
			total += numeric;
		}
	});
	return total;
}

function getCategoryBaselineQuantity(categoryId){
	if(!categoryId){
		return 0;
	}
	var normalized = categoryId.toString();
	var total = 0;
	$(".art").each(function(){
		var rowId = $(this).attr("id_tr");
		if(!rowId){
			return;
		}
		var row = $("#"+rowId);
		if(!row.length || !row.data("is-edit-baseline")){
			return;
		}
		var baselineCategory = row.data("initial-category");
		if(!baselineCategory){
			return;
		}
		if(baselineCategory.toString() !== normalized){
			return;
		}
		var qty = row.data("initial-quantity");
		var numeric = parseFloat(qty);
		if(isFinite(numeric) && numeric > 0){
			total += numeric;
		}
	});
	return total;
}

function showLimitAlert(limit){
	var existing = $("#limitAlertModal");
	if(!existing.length){
		$('body').append(
			'<div class="modal fade" id="limitAlertModal" tabindex="-1" role="dialog" aria-hidden="true">'+
			'  <div class="modal-dialog modal-dialog-centered" role="document">'+
			'    <div class="modal-content">'+
			'      <div class="modal-header bg-warning text-dark">'+
			'        <h5 class="modal-title"><i class="far fa-exclamation-triangle mr-2"></i>Limite atteinte</h5>'+ 
			'        <button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
			'          <span aria-hidden="true">&times;</span>'+ 
			'        </button>'+ 
			'      </div>'+ 
			'      <div class="modal-body">'+
			'        <p id="limitAlertText" class="mb-0"></p>'+ 
			'      </div>'+ 
			'      <div class="modal-footer">'+
			'        <button type="button" class="btn btn-warning" data-dismiss="modal">Compris</button>'+ 
			'      </div>'+ 
			'    </div>'+ 
			'  </div>'+ 
			'</div>'
		);
	}
	$("#limitAlertText").text("La quantité maximale autorisée pour cette catégorie aujourd'hui est de " + limit + ".");
	$("#limitAlertModal").modal('show');
}

function showStockJournalierAlert(rowId){
	setBlockingAlert('stock', rowId, true);
	var modal = $("#stockJournalierAlertModal");
	if(!modal.length){
		$('body').append(
			'<div class="modal fade" id="stockJournalierAlertModal" tabindex="-1" role="dialog" aria-hidden="true">'+
			'  <div class="modal-dialog modal-dialog-centered" role="document">'+
			'    <div class="modal-content">'+
			'      <div class="modal-header bg-danger text-white">'+
			'        <h5 class="modal-title"><i class="far fa-clipboard-list mr-2"></i>Stock journalier requis</h5>'+ 
			'        <button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">'+
			'          <span aria-hidden="true">&times;</span>'+ 
			'        </button>'+ 
			'      </div>'+ 
			'      <div class="modal-body">'+
			'        <p id="stockJournalierAlertText" class="mb-0"></p>'+ 
			'      </div>'+ 
			'      <div class="modal-footer">'+
			'        <button type="button" class="btn btn-danger" data-dismiss="modal">Compris</button>'+ 
			'      </div>'+ 
			'    </div>'+ 
			'  </div>'+ 
			'</div>'
		);
		modal = $("#stockJournalierAlertModal");
	}
	var alertMessage = "Veuillez renseigner le stock journalier de la catégorie sélectionnée avant d'effectuer cette vente.";
	modal.find('#stockJournalierAlertText').text(alertMessage);
	modal.modal('show');
}

function showLimitConsumedAlert(rowId){
	setBlockingAlert('limitZero', rowId, true);
	var modal = $("#limitConsumedAlertModal");
	if(!modal.length){
		$('body').append(
			'<div class="modal fade" id="limitConsumedAlertModal" tabindex="-1" role="dialog" aria-hidden="true">'+
			'  <div class="modal-dialog modal-dialog-centered" role="document">'+
			'    <div class="modal-content">'+
			'      <div class="modal-header bg-warning text-dark">'+
			'        <h5 class="modal-title"><i class="far fa-hourglass-end mr-2"></i>Quota journalier atteint</h5>'+ 
			'        <button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
			'          <span aria-hidden="true">&times;</span>'+ 
			'        </button>'+ 
			'      </div>'+ 
			'      <div class="modal-body">'+
			'        <p id="limitConsumedAlertText" class="mb-0"></p>'+ 
			'      </div>'+ 
			'      <div class="modal-footer">'+
			'        <button type="button" class="btn btn-warning" data-dismiss="modal">Compris</button>'+ 
			'      </div>'+ 
			'    </div>'+ 
			'  </div>'+ 
			'</div>'
		);
		modal = $("#limitConsumedAlertModal");
	}
	var alertMessage = "La limite quotidienne de la catégorie sélectionnée est atteinte.";
	modal.find('#limitConsumedAlertText').text(alertMessage);
	modal.modal('show');
}

function showCommandeValidationBlockedAlert(){
	var modal = $("#commandeBlockedAlertModal");
	if(!modal.length){
		$('body').append(
			'<div class="modal fade" id="commandeBlockedAlertModal" tabindex="-1" role="dialog" aria-hidden="true">'+
			'  <div class="modal-dialog modal-dialog-centered" role="document">'+
			'    <div class="modal-content">'+
			'      <div class="modal-header bg-danger text-white">'+
			'        <h5 class="modal-title"><i class="far fa-times-circle mr-2"></i>Impossible de valider</h5>'+ 
			'        <button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">'+
			'          <span aria-hidden="true">&times;</span>'+ 
			'        </button>'+ 
			'      </div>'+ 
			'      <div class="modal-body">'+
			'        <p class="mb-0">Vous devez d\'abord corriger les alertes de stock journalier ou de limite de vente avant de valider la commande.</p>'+ 
			'      </div>'+ 
			'      <div class="modal-footer">'+
			'        <button type="button" class="btn btn-danger" data-dismiss="modal">Compris</button>'+ 
			'      </div>'+ 
			'    </div>'+ 
			'  </div>'+ 
			'</div>'
		);
		modal = $("#commandeBlockedAlertModal");
	}
	modal.modal('show');
}

function resolveArticleLabel(rowId){
	if(!rowId){
		return '';
	}
	var option = $("#"+rowId).find('#art option:selected');
	if(!option.length){
		return '';
	}
	return option.text().trim();
}

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
function submit(){
	if(hasBlockingAlerts()){
		showCommandeValidationBlockedAlert();
		return;
	}
	
	spin_it('on');
	console.log("select val -> "+$("#select_mat").val())
	
	var test = 0;
	
	var msg = "";
	
	if($("#code_client").val()==""){
		
		test++;
		msg = msg+"- Code client incorrect. <br>";
		$("#code_client").css("border-color","red");
		
	}
	
	if($("#mode_reg").val()==null){
		
		test++;
		msg = msg+"- Selectionner un mode de regelement. <br>";
		//$("#mode_reg").css("border-color","red");
		$(".bs-placeholder").find('[data-id=mode_reg]').css("border-color","red");
		
	}
	
	if($("#select_mat").val()=="" && $("#input_mat").val()=="" && $("#select_veh").val()=="" ){
		
		test++;
		msg = msg+"- Matricule Vide. <br>";
		$("#matricule").css("border-color","red");
		
	}
	
	if($("#rc").val()==""){
		
		test++;
		msg = msg+"- Selectionner un Registre de commerce. <br>";
		//$("#rc").css("border-color","red");
		$(".bs-placeholder").find('[data-id=rc]').css("border-color","red");
		
	}
	
	if($("#total_ttc").val()=="" || parseFloat($("#total_ttc").val())==0){
		
		test++;
		msg = msg+"- Veuillez faire une commande. <br>";
		$("#total_ttc").css("border-color","red");
		
	}
	
	//check duplicated articles 
		var selectedValues = []; 
		var hasDuplicate = false; 
	
		$(".art option:selected").each(function() {
		    var value = $(this).val(); 
		    
		    if (value == "0") return;  		    
		    if (selectedValues.includes(value)) { 
		        hasDuplicate = true;  
		    } else {
		        selectedValues.push(value); 
		    }
		});
      if (hasDuplicate) {
    	  test++;
    	  msg = msg+"- Y'a des articles dupliqués, svp vérifier. <br>";
      }
      
      
	
	console.log("test->"+test+" "+msg)
	
	if(test==0){
		
		var client_plafond = 0;
		
		var rc_plafond = 0;
		
		var msg1 = "";
		
		var mnt_ttc = parseFloat( $("#total_ttc").val().replace(" ","") );
		
		$.ajaxSetup({async: false});
		$.ajax({
			url: 'ajax_test_plafond',
			//type: 'POST',
			dataType: 'json',
			data : {
				id_rc_clt : $("#rc").val(),
				montant_ttc : $("#total_ttc").val(),
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
						
					
				}
				
			}
		});
		
		console.log("-------------------------")
		
		console.log("p_clt->"+client_plafond+" // p_rc ->"+rc_plafond)
		
		if(client_plafond==0 && rc_plafond==0){
			
			console.log("-----------------> SUBMIT")
			
			$("#frm").submit();
			
		}
		else{
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").html(msg1);
			$("#error").modal('show');
			
			spin_it('off');
		}
		
	}
	else{
		
		$("#title").text("Erreur !!!");
		$("#icone").attr("class","far fa-exclamation-triangle");
		$("#text").html(msg);
		$("#error").modal('show');
		
		spin_it('off');
		
	}

}
