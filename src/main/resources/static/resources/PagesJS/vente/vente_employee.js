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
		
		$('#print_content').html('<iframe id="frame" width="100%" height="828" onload="ifrhgh()" frameborder="0" src="print_bl?id_bl='+id_bl+'">'+
									'</iframe>');
		
		$("#title_bl").text("Détail BL N° "+num_bl);
		
		$("#icone_bl").attr("class","far fa-file-invoice");
		
		$("#bl_detail_modal").modal("show");
			
	}
	
	//---------------------------------- GET Matricules ---------------------------------------------
	/*
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
	*/
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
		
		var p_redux = $("#pourc_redux").val();
		
		var montant_ht_redux = montant_ht - (montant_ht* (p_redux/100) );
		
		var montant_tva_redux = montant_tva - (montant_tva* (p_redux/100) );
		
		var montant_ttc_redux = montant_ht_redux + montant_tva_redux;
		
		$("#total_ht_redux").val( (montant_ht_redux).formatMoney(2, '.', ' ') );
		$("#total_tva").val( (montant_tva_redux).formatMoney(2, '.', ' ') );
		$("#total_ttc").val( (montant_ttc_redux).formatMoney(2, '.', ' ') );
		
	});
	
	$("#pourc_redux").keyup(function() {
		
		var p_redux = parseFloat($(this).val());
		
		var montant_ht = $("#total_ht_hidden").val();
		
		var montant_tva = $("#total_tva_hidden").val();
		
		var montant_ht_redux = montant_ht - (montant_ht* (p_redux/100) );
		
		var montant_tva_redux = montant_tva - (montant_tva* (p_redux/100) );
		
		var montant_ttc_redux = montant_ht_redux + montant_tva_redux;
		
		$("#total_ht_redux").val( (montant_ht_redux).formatMoney(2, '.', ' ') );
		$("#total_tva").val( (montant_tva_redux).formatMoney(2, '.', ' ') );
		$("#total_ttc").val( (montant_ttc_redux).formatMoney(2, '.', ' ') );
		
	})
	
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
			$("#employee").css("border-color","red");
			
		}
		
		if($("#total_ttc").val()=="" || $("#total_ttc").val()=="0"){
			
			test++;
			msg = msg+"- Veuillez faire une commande. <br>";
			$("#total_ttc").css("border-color","red");
			
		}
		
		if(test==0){
			
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
		