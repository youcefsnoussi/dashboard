$(document).ready(function() {
			
				var logo_path = $("#logo_preview").val();
				
				var id_banque = $("#id_banque").val();
				
				console.log(id_banque);
				
				if(id_banque!=""){
					
					$("#banque").val(id_banque);
					
				}
				
				$("#logo").fileinput({
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
				    defaultPreviewContent: '<img src="/display_img?link='+logo_path+'" >',
				    layoutTemplates: {main2: '{preview} {remove} {browse}'},
				    allowedFileExtensions: ["jpg", "png", "svg","jpeg"]
				});
				
			
});	