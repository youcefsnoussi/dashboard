$(document).ready(function() {
				
	$('[data-toggle="tooltip"]').tooltip();
	
	if($("#etat_blockage").val()=="false"){
		
		$("#no_block").css("border-width","4px");
		$("#no_block").css("border-color","grey");
	    
	}
	else{
		
		$("#block").css("border-width","4px");
		$("#block").css("border-color","grey");
		
	}
	
	$("#no_block").click(function(){
		
		$("#etat_blockage").val(false);
		
		$("#no_block").css("border-width","4px");
		$("#no_block").css("border-color","grey");
		
		$("#block").css("border-width","0px");
		
	});
	
	$("#block").click(function(){
		
		$("#etat_blockage").val(true);
		
		$("#block").css("border-width","4px");
		$("#block").css("border-color","grey");
		
		$("#no_block").css("border-width","0px");
		
	});
	
	var cat_client = $("#category_client").val();
	
	$("#cat_client").val(cat_client);
	
	var bank = $("#bank").val();
	
	$("#banque").val(bank);
	
	var type_reglement = $("#type_reg").val();
	
	$("#type_reglement").val(type_reglement);
	
	var img_client = $("#image_client").val();
	
	$("#img_client").fileinput({
		
	    overwriteInitial: true,
	    maxFileSize: 25000,
	    showClose: false,
	    showCaption: false,
	    browseLabel: '',
	    removeLabel: '',
	    browseIcon: '<i class="far fa-folder-open"></i>',
	    removeIcon: '<i class="far fa-trash-alt"></i>',
	    removeTitle: 'Cancel or reset changes',
	    elErrorContainer: '#kv-avatar-errors-1',
	    msgErrorClass: 'alert alert-block alert-danger',
	    defaultPreviewContent: '<img src="/display_img?link='+img_client+'" height="100%" width="100%" >',
	    layoutTemplates: {main2: '{preview} {remove} {browse}'},
	    allowedFileExtensions: ["jpg", "png", "svg","jpeg"]
	    
	});
			
		
});	