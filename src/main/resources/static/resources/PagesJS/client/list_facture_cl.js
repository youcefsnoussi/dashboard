$(document).ready(function() {
	
	var h = window.innerHeight;
	
	var datatable_config = {
			"language": {
		            "url": "resources/Plugins/datatable/lang/French.json"
		    },
			"ordering": false,
	    	"bPaginate": false,
			"scrollY":"400px",
			"scrollX": "auto",
	        "deferRender": true,
	        initComplete: function () {
	            this.api().columns([0,3]).every( function () {
	                var column = this;
	                var select = $('<select class="form-control" ><option value="">Tout</option></select>')
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
			
    };
 		
 	var table = $('#table').DataTable(datatable_config);
$(".edit_end").click(function() {
		
 		//console.log("id=>"+$(this).attr("id_relation"))
 		var id_rel = $(this).attr("id_relation");
 		var end = $(this).parent().find(".end").val();
 		
 		$.ajax({
 			url : 'update_date_end_relation',
 			data : {
 				id_relation : id_rel,
 				end : end
 			},
 			success : function(responseJson) {
 				
 				alert("modification effectuée ")
 				
 			}
 		});
 		
	});
 	
});
 	