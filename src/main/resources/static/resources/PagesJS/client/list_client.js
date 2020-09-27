$(document).ready(function() {
				
	$('[data-toggle="tooltip"]').tooltip()
	
	var datatable_config = {
			"language": {
		            "url": "resources/Plugins/datatable/lang/French.json"
		    },
			"ordering": true,
	    	"bPaginate": false,
			"scrollY":"400px",
			"scrollX": "auto",
	        "deferRender": true,
	        initComplete: function () {
	            this.api().columns([2,5]).every( function () {
	                var column = this;
	                var select = $('<select class="form-control" ><option value=""></option></select>')
	                    .appendTo( $(column.footer()).empty() )
	                    .on( 'change', function () {
	                        var val = $.fn.dataTable.util.escapeRegex(
	                            $(this).val()
	                        );
	 
	                        column
	                            .search( val ? '^'+val+'$' : '', true, false )
	                            .draw();
	                    } );
	 
	                column.data().unique().sort().each( function ( d, j ) {
	                    select.append( '<option value="'+d+'">'+d+'</option>' )
	                } );
	            } );
	        },
			 drawCallback: function () {
				
			      var api = this.api();
			      
			      $( api.column( 3, {page:'current'} ).footer() ).html(
			        api.column( 3, {page:'current'} ).data().sum().formatMoney(2, ',', ' ')
			      );
			      
			 },
    };
 		
 	var table = $('#table').DataTable(datatable_config);
	
	$("#add_client").click(function(){
		
		$('#print_content').html('<iframe id="frame" width="100%" height="900" onload="ifrhgh()" frameborder="0" src="/add_client"></iframe>');
		
		$("#title").text("Nouveau client");
		
		$("#icone").attr("class","far fa-user-alt");
		
		$("#new_client").modal("show");
		
	});
	
	$(".edit").click(function(){
		
		var id_c = $(this).attr("id_client");
		
		$('#print_content').html('<iframe id="frame" width="100%" height="1071" onload="ifrhgh()" frameborder="0" src="/info_client?id_c='+id_c+' "></iframe>');
		
		$("#title").text("Détail client");
		
		$("#icone").attr("class","far fa-user-edit");
		
		$("#new_client").modal("show");
		
	});
	
});

$(function () {
    $('#new_client').on('shown.bs.modal', function () {
        //$('#print_content').html('<iframe id="frame" width="100%" height="100%" onload="ifrhgh()" frameborder="0" src="/add_client"></iframe>');
    });
});

function ifrhgh(){
    var iframehght =  $("#frame").contents().height();
    $("#frame").height(iframehght);
}