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
	
	if(id_bl!=null){
		
		$('#print_content').html('<iframe id="frame" width="100%" height="828" onload="ifrhgh()" frameborder="0" src="print_bl?id_bl='+id_bl+'"></iframe>');
		
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
					
					$("#matricule").append('<option value="'+value+'" >'+value+'</option>');

				});
				
				$("#matricule").selectpicker('refresh');
				$("#loading").remove();
			}
			
		}
	});
	
	//-------------------------------------------------------------------------------
	
	$("#id_client").change(function(){
	
		$("#designation").val("");
		$("#adresse").val("");
		$("#solde").val("");
		$("#max_solde").val("");
		$("#matricule").val("");
		
		var recherche = $("#id_client option:selected").attr("code_clt");
		
		console.log("==>"+recherche);
		
		spin_it('on');
		$.ajax({
			url: 'ajax_get_client_by_code',
			//type: 'POST',
			dataType: 'json',
			data : {
				code	:recherche,
	        },
			success : function(data){
				//console.log(data);
				
					//$("#id_client").val(data.id)
					$("#designation").val(data.nom+' '+data.prenom)
					$("#adresse").val(data.adresse)
					$("#solde").val(parseFloat(data.sold_encours).formatMoney(2, '.', ' '))
					$("#anc_code_client").val(recherche);
					$("#mode_reg").val(data.type_regelement_client);
					$("#max_solde").val(parseFloat(data.plafond).formatMoney(2, '.', ' '));
					$("#mode_reg").val(data.type_reglement.id);
					$("#mode_reg").selectpicker("refresh");
					
					if(($("#c_type_cl").val()!=data.category.id)&&($("#c_type_cl").val()!="")) {
						
						$("#c_type_cl").val(data.category.id);
							
					}
					
					//-------------------- update select RC b client
					
					$('#rc').find('option:not(:first)').remove();
					$("#rc").selectpicker('refresh');
					$("#designation_rc").val("");
					$("#adresse_rc").val("");
					$("#solde_rc").val("");
					$("#max_solde_rc").val("");
					
					//$.ajaxSetup({async: false});
					$.ajax({
						url: 'ajax_get_rc_by_client_fact',
						//type: 'POST',
						dataType: 'json',
						data : {
							id_client	:data.id,
				        },
				        success : function(responseJson) {
							
							if (responseJson != "null") {

								$.each(responseJson, function(key, value) {
									
									console.log(value.id)
									
									$("#rc").append('<option value="'+value.id+'" rc="'+value.registre_commerce.numero_rc+'" '+
													'data-subtext="'+value.registre_commerce.numero_rc+' [ '+value.date_debut+' ➤ '+value.date_fin+' ]" '+
													'nif="'+value.registre_commerce.numero_nif+'" title="'+value.registre_commerce.numero_rc+'" '+
													'adresse="'+value.registre_commerce.adresse+'" art="'+value.registre_commerce.numero_art+'" '+
													'plafond="'+value.registre_commerce.plafond+'" sold="'+value.registre_commerce.sold_encours+'" '+
													'nom="'+value.registre_commerce.nom+'" prenom="'+value.registre_commerce.prenom+'" '+
													'tva="'+value.registre_commerce.tva+'">'+value.registre_commerce.nom+' '+value.registre_commerce.prenom+'</option>');

								});
								
								$("#rc").selectpicker('refresh');
							}
							
						}
					});
					
					//-------------------------------------------------
					
					//-------------------- Update articles -----------------
					
					$('.art').find('option:not(:first)').remove();
					$(".art").selectpicker('refresh');
					
					//$.ajaxSetup({async: false});
					$("#redux").empty();
					
					$.ajax({
						url: 'ajax_get_art_by_client_cat',
						//type: 'POST',
						dataType: 'json',
						data : {
							id_client	:data.id,
				        },
				        success : function(responseJson) {
							
							if (responseJson != "null") {
								
								$.each(responseJson, function(key, value) {
									console.log("=>"+value.id)
									if(value.id==(-1)){
										
										$("#redux").append("<span class='badge badge-secondary' id='art'> "+value.article.produit.designation+" "+value.article.emballage_produit.nom_emballage+" "+value.article.pesage_produit.pesage+value.article.pesage_produit.unite_pesage+"</span>")
										
									}
									
									$(".art").each(function(){
										
										var sub = "Subventionné";
										
										if(value.article.subvension==false){
											
											sub = "NON Subventionné";
											
										}
																				$(this).append('<option value="'+value.article.id+'" code="'+value.article.code+'" '+
													   'data-subtext="'+value.article.code+' ('+sub+')" um="'+value.article.unite_mesure_vente.nom_unite_mesure+'" '+
													   'pu="'+value.prix+'" tva="'+value.tva.taux_tva+'" id_um="'+value.article.unite_mesure_vente.id+'" '+
													   '>'+value.article.produit.designation+' '+value.article.emballage_produit.nom_emballage+' '
													   +value.article.pesage_produit.pesage+value.article.pesage_produit.unite_pesage+'</option>');																				$(this).selectpicker('refresh');
											
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
					
					
			},
			error: function (xhr, status) {
	        	
				$("#designation").val("");
				$("#adresse").val("");
				$("#solde").val("");
				$("#max_solde").val("");
				$("#matricule").val("");
				
				$('#rc').find('option:not(:first)').remove();
				$("#rc").selectpicker('refresh');
				$("#designation_rc").val("");
				$("#adresse_rc").val("");
				$("#solde_rc").val("");
				$("#max_solde_rc").val("");
				
				$('#code_client').focus();
				$('#code_client').val("");
				
	        	$("#title").text("Erreur !!!");
				$("#icone").attr("class","far fa-exclamation-triangle");
				$("#text").text("Code Client inexistant ");
				$("#error").modal('show');
				
	        },
		});
		spin_it('off');
		
	});
	
	
	$('#code_client').keyup(function(e){
	    if(e.keyCode == 13)
	    {
	        $(this).trigger("enterKey");
	    }
	    else{
	    	
	    	$(this).val($(this).val().toUpperCase());
	    	
	    }
	});
	
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
	
	//----------------------------------------------------------------------
	
	$("#rc").change(function(){
		  
		 $("#designation_rc").val($("#rc option:selected").attr("nom")+' '+$("#rc option:selected").attr("prenom"));
		 $("#adresse_rc").val($("#rc option:selected").attr("adresse"));
		 $("#solde_rc").val(parseFloat($("#rc option:selected").attr("sold")).formatMoney(2, '.', ' '));
		 $("#max_solde_rc").val(parseFloat($("#rc option:selected").attr("plafond")).formatMoney(2, '.', ' '));
		 $("#tv").val($("#rc option:selected").attr("tva"));
		 
		 var exo = $("#rc option:selected").attr("tva");
		 
		 if(exo=="0"){
			 
			 $("#exo").attr("class","badge badge-warning");
			 $("#exo").text("oui");
			 
		 }
		 else{
			 
			 $("#exo").attr("class","badge badge-secondary");
			 $("#exo").text("non");
			 
		 }
		 
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
		
		$("#total_ht").val((montant_ht).formatMoney(2, '.', ' '));
		$("#total_tva").val((montant_tva).formatMoney(2, '.', ' '));
		$("#total_ttc").val((montant_ttc).formatMoney(2, '.', ' '));
		
	});
	
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
	/*
	console.log("mode_reg == "+$("#mode_reg").val());
	
	console.log("rc == "+$("#rc").val());
	
	console.log("art == "+$(".art").val());
	*/
	$("#sub").click(function(){
		
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
		
		if($("#matricule").val()==""){
			
			test++;
			msg = msg+"- Matricule incorrect. <br>";
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
		
		if(test==0){
			
			var client_plafond = 0;
			
			var rc_plafond = 0;
			
			var msg1 = "";
			
			$.ajaxSetup({async: false});
			$.ajax({
				url: 'ajax_test_plafond',
				//type: 'POST',
				dataType: 'json',
				data : {
					id_client	: $("#id_client").val(),
					id_rc		: $("#rc").val(),
					montant_ttc : $("#total_ttc").val(),
		        },
		        success : function(responseJson) {
					
					if (responseJson != "null") {
						
							if(responseJson.plafond_client!=0){
								
								client_plafond++;
								
								msg1 = msg1+"- Plafond client dépasser <br>";
								
							}
							
							if(responseJson.plafond_rc!=0){
								
								rc_plafond++;
								
								msg1 = msg1+"- Plafond RC dépasser <br>";
								
							}
							
						
					}
					
				}
			});
			
			if(client_plafond==0 && rc_plafond==0){
				
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
		