$(document).ready(function() {
			
	//$('#code_client').bind("enterKey",function(e){
	
	
	
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
	
	}
	
	if(id_bl!=null){
		
		$('#print_content').html('<iframe id="frame" width="100%" height="828" onload="ifrhgh()" frameborder="0" src="'+t+'?id_bl='+id_bl+'"></iframe>');
		
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
	
	//----------------------------------------------------------------------
	
	$(".art").change(function(){
		
		var parrent = $(this).parent().parent().parent();
		
		//-------------------- get unite chargement by art -----------------
		
		parrent.find('#id_magasin').empty();
		$.ajaxSetup({async: false});
		$.ajax({
			url: 'ajax_get_magasin_by_art',
			//type: 'POST',
			dataType: 'json',
			data : {
				id_article	: $(this).val(),
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
		
		console.log("art val = "+$(this).val())
		
		console.log("art attr before= "+$(this).attr("name"))
		
		if($(this).val()!=""){
			
			$(this).attr("name","art");
			
		}
		else{
			
			$(this).attr("name","");
			
		}
		
		parrent.find("#qte").val(0);
		parrent.find("#code_art").val($('option:selected', this).attr("code"));
		parrent.find("#unite_mesure").val($('option:selected', this).attr("um"));
		parrent.find("#id_um").val($('option:selected', this).attr("id_um"));
		parrent.find("#prix_unitaire").val($('option:selected', this).attr("pu"));
		parrent.find("#tva_art").val($('option:selected', this).attr("tva"));
		
		var pu = parrent.find("#prix_unitaire").val();
		
		parrent.find("#montant_ht").val((pu*0).formatMoney(2, '.', ' '));
		
		var montant_ht = 0;
		
		var montant_tva = 0;
		
		$(".montant_ht").each(function(){
			
			if($(this).val()!=""){
				
				var v = $(this).val();
				
				v = v.replace(/ /g,"");
				
				montant_ht = montant_ht + parseFloat(v);
				
				var tva = $(this).parent().parent().find("#tva_art").val()/100;
				
				if($("#tv").val()!="0"){ //-------------------------- if RC not exnéré
					
					montant_tva = montant_tva + ( parseFloat(v) * tva );
					
				}
				
			}
			
		});
		
		var montant_ttc = montant_ht + montant_tva;
		
		$("#total_ht").val((montant_ht).formatMoney(2, '.', ' '));
		$("#total_tva").val((montant_tva).formatMoney(2, '.', ' '));
		$("#total_ttc").val((montant_ttc).formatMoney(2, '.', ' '));
		
	});
	
	//----------------------------------RADIO FUNCTIONS------------------------------------
	
	$("#radio_select").click(function () {
		
		$("#select_mat").attr("name","matricule");
		$("#select_mat").attr('disabled',false);
		$("#select_mat").selectpicker('refresh');
		
		$("#input_mat").attr("name","");
		$("#input_mat").attr('disabled',true);
		
	})
	
	$("#radio_input").click(function () {
		
		$("#select_mat").attr("name","");
		$("#select_mat").val("");
		$("#select_mat").attr('disabled',true);
		$("#select_mat").selectpicker('refresh');
		
		$("#input_mat").attr("name","matricule");
		$("#input_mat").attr('disabled',false);
		
	})
	
	//----------------------------------------------------------------------
	
	$("#rc").change(function(){
		  
		 $("#designation_rc").val($("#rc option:selected").attr("nom")+' '+$("#rc option:selected").attr("prenom"));
		 $("#adresse_rc").val($("#rc option:selected").attr("adresse"));
		 $("#solde_rc").val(parseFloat($("#rc option:selected").attr("sold")).formatMoney(2, '.', ' '));
		 $("#max_solde_rc").val(parseFloat($("#rc option:selected").attr("plafond")).formatMoney(2, '.', ' '));
		 $("#tv").val($("#rc option:selected").attr("tva"));
		 $("#mode_reg").val( $("#rc option:selected").attr("mode_pay") );
		 
		 var id_rc_clt = $(this).val();
		 
		 var exo = $("#rc option:selected").attr("tva");
		 
		 if(exo=="0"){
			 
			 $("#exo").attr("class","badge badge-warning");
			 $("#exo").text("oui");
			 
		 }
		 else{
			 
			 $("#exo").attr("class","badge badge-secondary");
			 $("#exo").text("non");
			 
		 }
		 
		//-------------------- Update articles -----------------
			
			$('.art').find('option:not(:first)').remove();
			$(".art").selectpicker('refresh');
			
			//$.ajaxSetup({async: false});
			$("#redux").empty();
			
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
								/*
								var cat = value.article.produit.sous_category_produit.category_produit.nom_category;
								var s_cat = value.article.produit.sous_category_produit.nom_sous_category.replace(cat,"");
								var prod_temp = value.article.produit.designation.replace(cat,"");
								var prod = prod_temp.replace(s_cat,"");
								var emb = value.article.emballage_produit.nom_emballage.replace(cat,"");
								var pes = value.article.pesage_produit.pesage+' '+value.article.pesage_produit.unite_pesage;
								'+cat+' '+s_cat+' '+prod+' '+emb+' '+pes+'
								*/			
								
								$(this).append('<option value="'+value.article.id+'" code="'+value.article.code+'" '+
											   'data-subtext="'+value.article.code+' ('+sub+')" um="'+value.article.unite_mesure_vente.nom_unite_mesure+'" '+
											   'pu="'+value.prix+'" tva="'+value.tva.taux_tva+'" id_um="'+value.article.unite_mesure_vente.id+'" '+
											   '>'+value.article.libelle+'</option>');
								
								$(this).selectpicker('refresh');
									
							});
							
						});
						
						$(".art").find("option").hide();
						
					}
					
				}
			});
			
			//-----------------------------------------------------------
			
			$(".qte").attr("readonly", false);
			$(".qte").val(0);
			$(".prix_unitaire").val(0);
			$(".tva").val(0);
			$(".montant_ht").val(0);
			$(".id_magasin").empty();
			$(".unite_mesure").val("");
			$(".nom_art").val("");
			
			$("#total_ht").val(0);
			$("#total_tva").val(0);
			$("#total_ttc").val(0);
			
	});
	
	//----------------------------------------------------------------------
	
	$(".qte").keyup(function(){
		
		var qte = $(this).val();
		
		var parent = $(this).parent().parent();
		
		var pu = parent.find("#prix_unitaire").val();
		
		var val_select_art = parent.find("#art").val();
		
		if(val_select_art!=""){
			
			parent.find("#montant_ht").val((pu*qte).formatMoney(2, '.', ' '));
			
		}
		else{
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").text("Veuillez selectionner un Article ");
			$("#error").modal('show');
			
			$(this).val(0);
		}
		
		var montant_ht = 0;
		
		var montant_tva = 0;
		
		$(".montant_ht").each(function(){
			
			if($(this).val()!=""){
				
				var v = $(this).val();
				
				v = v.replace(/ /g,"");
				
				montant_ht = montant_ht + parseFloat(v);
				
				var tva = $(this).parent().parent().find("#tva_art").val()/100;
				
				
				if($("#tv").val()!="0"){ //-------------------------- if RC not exnéré
				
					montant_tva = montant_tva + ( parseFloat(v) * tva )
				
				}
				
			}
			
		});
		
		var montant_ttc = montant_ht + montant_tva;
		
		$("#total_ht_hidden").val(montant_ht);
		$("#total_tva_hidden").val(montant_tva);
		
		$("#total_ht").val((montant_ht).formatMoney(2, '.', ' '));
		/*
		var p_redux = $("#pourc_redux").val();
		
		var montant_ht_redux = montant_ht - (montant_ht* (p_redux/100) );
		
		var montant_tva_redux = montant_tva - (montant_tva* (p_redux/100) );
		
		var montant_ttc_redux = montant_ht_redux + montant_tva_redux;
		*/
		
		$("#total_ht_redux").val( (montant_ht).formatMoney(2, '.', ' ') );
		$("#total_tva").val( (montant_tva).formatMoney(2, '.', ' ') );
		$("#total_ttc").val( (montant_ht+montant_tva).formatMoney(2, '.', ' ') );
		
	});
	
	//____________________________________________> CALCULE REMISE <_____________________________________//
	
	$("#pourc_redux").keyup(function() {
		
		var p_redux = parseFloat($(this).val());
		
		var montant_ht = $("#total_ht_hidden").val();
		
		var montant_tva = $("#total_tva_hidden").val();
		
		var montant_ht_redux = montant_ht - (montant_ht* (p_redux/100) );
		//-----------------------------------------------------------------------------> calcule b %
		var montant_tva_redux = montant_tva - (montant_tva* (p_redux/100) );
		
		/*
		var montant_ht_redux = montant_ht - p_redux;
		
		var montant_tva_redux = montant_tva - p_redux;
		*/
		var montant_ttc_redux = montant_ht_redux + montant_tva_redux;
		
		$("#total_ht").val( (montant_ht_redux).formatMoney(2, '.', ' ') );
		$("#total_tva").val( (montant_tva_redux).formatMoney(2, '.', ' ') );
		$("#total_ttc").val( (montant_ttc_redux).formatMoney(2, '.', ' ') );
		
	})
	
	$("#mnt_redux").keyup(function() {
		
		console.log("enter mnt")
		
		var p_redux = parseFloat($(this).val());
		
		var montant_ht = $("#total_ht_hidden").val();
		
		var montant_tva = $("#total_tva_hidden").val();
		
		
		var montant_ht_redux = montant_ht - p_redux;
		
		var tva_redux = $("#tva_redux").val();
		
		var montant_tva_redux = montant_ht_redux * (tva_redux/100);
		
		var montant_ttc_redux = montant_ht_redux + montant_tva_redux;
		
		//------------ ------------------- calcule poucentage --------------------
		
		var pourcentage = (p_redux * 100) /  montant_ht;
		
		$("#pourc_redux").val(pourcentage.formatMoney(2, '.', ' '));
		
		//------------ --------------------- -------------------- ----------------
		
		$("#total_ht").val( (montant_ht_redux).formatMoney(2, '.', ' ') );
		$("#total_tva").val( (montant_tva_redux).formatMoney(2, '.', ' ') );
		$("#total_ttc").val( (montant_ttc_redux).formatMoney(2, '.', ' ') );
		
	})
	
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
		
		console.log("select val -> "+$("#select_mat").val())
		
		var test = 0;
		
		var msg = "";
		
		if($("#code_client").val()==""){
			
			test++;
			msg = msg+"- Code client incorrect. <br>";
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
			msg = msg+"- Matricule Vide. <br>";
			$("#matricule").css("border-color","red");
			
		}
		
		if($("#rc").val()==""){
			
			test++;
			msg = msg+"- Selectionner un Registre de commerce. <br>";
			//$("#rc").css("border-color","red");
			$(".bs-placeholder").find('[data-id=rc]').css("border-color","red");
			
		}
		
		if($("#total_ttc").val()=="" || $("#total_ttc").val()=="0"){
			
			test++;
			msg = msg+"- Veuillez faire une commande. <br>";
			$("#total_ttc").css("border-color","red");
			
		}
		
		console.log("test->"+test)
		
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
		