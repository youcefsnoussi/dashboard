$(document).ready(function() {
	
	$(".qte").keyup(function(){
		
		calculeDetail($(this));
		calculeTotal();
		
	});
	
	$("#sub").click(function(){
		
		var test = 0;
		
		$(".qte").each(function(){
			
			var current_val = parseFloat($(this).val());
			
			var old_val = parseFloat($(this).parent().find("#qte_init").val());
			
			if(current_val>old_val){
				
				test = 1;
				
				$(this).css("border-color","red");
				
			}
			
		});
		
		if( isNaN(parseFloat($("#total_ttc").val())) || parseFloat($("#total_ttc").val()) ===0 ){
			test = 2;
		}
		
		if(test===0){
			
			$("#frm").submit();
			
		}
		
		if(test===1){
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").html("La quantité saisie doit être inferieur a la quantité initial ");
			$("#error").modal('show');
			
		}
		
		if(test===2){
			
			$("#title").text("Erreur !!!");
			$("#icone").attr("class","far fa-exclamation-triangle");
			$("#text").html("Veuillez faire une facture d'avoir !! ");
			$("#error").modal('show');
			
		}
		
	});
	
});

function calculeDetail(input){
	
	let parrent = $(input).parent().parent();
	
	let quant = parseFloat($(input).val());
	let prixUHT = parseFloat(parrent.find("#prix_unitaire").val());
	let pRemise = parseFloat(parrent.find("#pourcentage_remise_art").val());
	let tva = parseFloat(parrent.find("#tva_art").val());
	let mntHt = quant * prixUHT;
	
	let mntHTNet = mntHt - ( mntHt * (pRemise / 100));
	
	let mntTVA = mntHTNet*(tva/100);
	
	parrent.find("#montant_ht_art").val( (mntHt).formatMoney(2, '.', ' ') );
	parrent.find("#montant_remise_art").val( ( mntHt * (pRemise / 100) ).formatMoney(2, '.', ' ') );
	parrent.find("#montant_ht_net_art").val( (mntHTNet).formatMoney(2, '.', ' ') );
	parrent.find("#montant_tva_art").val( (mntTVA).formatMoney(2, '.', ' ') );
	parrent.find("#montant_ttc_art").val( (mntHTNet + mntTVA).formatMoney(2, '.', ' ') );
	
}

function calculeTotal(){
	
	let total_ht = 0;
	let total_ht_net = 0;
	
	let total_remise = 0;
	
	let total_tva = 0;
	
	let total_ttc = 0;
	
	$(".montant_ht_art").each(function() {
		
		total_ht += parseFloat( $(this).val().replaceAll(" ","") );
		
	});
	
	$(".montant_remise_art").each(function() {
		
		total_remise += parseFloat( $(this).val().replaceAll(" ","") );
		
	});
	
	$(".montant_ht_net_art").each(function() {
		
		total_ht_net += parseFloat( $(this).val().replaceAll(" ","") );
		
	});
	
	$(".montant_tva_art").each(function() {
		
		 total_tva += parseFloat( $(this).val().replaceAll(" ","") );
		
	});
	
	$(".montant_ttc_art").each(function() {
		
		 total_ttc += parseFloat( $(this).val().replaceAll(" ","") );
		
	});
	
	$("#total_ht").val( total_ht.formatMoney(2, '.', ' ') );
	$("#montant_remise").val( total_remise.formatMoney(2, '.', ' ') );
	$("#pourc_remisee").val( (total_remise*100/total_ht).formatMoney(2, '.', ' ') );
	$("#total_ht_net").val( total_ht_net.formatMoney(2, '.', ' ') );
	$("#total_tva").val( total_tva.formatMoney(2, '.', ' ') );
	$("#total_ttc").val( total_ttc.formatMoney(2, '.', ' ') );
	
}