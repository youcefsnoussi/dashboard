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
			            this.api().columns([]).every( function () {
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
					      
					      $( api.column( 2, {page:'current'} ).footer() ).html(
					        api.column( 2, {page:'current'} ).data().sum().formatMoney(2, ',', ' ')
					      );
					      
					      $( api.column( 3, {page:'current'} ).footer() ).html(
					        api.column( 3, {page:'current'} ).data().sum().formatMoney(2, ',', ' ')
					      );
					      
					 },
		    };
		 		
		 	var table = $('#table').DataTable(datatable_config);
			
			$("#add_rc").click(function(){
				
				$('#print_content').html('<iframe id="frame" width="100%" height="900" onload="ifrhgh()" frameborder="0" src="add_rc"></iframe>');
				
				$("#title").text("Nouveau RC");
				
				$("#icone").attr("class","far fa-file-invoice");
				
				$("#new_rc").modal("show");
				
			});
			
			$(".edit").click(function(){
				
				var id_rc = $(this).attr("id_rc");
				
				$('#print_content').html('<iframe id="frame" width="100%" height="800" onload="ifrhgh()" frameborder="0" src="info_rc?id_rc='+id_rc+' "></iframe>');
				
				$("#title").text("Détail RC");
				
				$("#icone").attr("class","far fa-file-edit");
				
				$("#new_rc").modal("show");
				
			});
			
			$("#rc_clt").click(function(){
				
				$('#print_content').html('<iframe id="frame" width="100%" height="700" onload="ifrhgh()" frameborder="0" src="clt_rc"></iframe>');
				
				$("#title").text("Relation registre commerce client");
				
				$("#icone").attr("class","far fa-repeat");
				
				$("#new_rc").modal("show");
				
			});
			
			$("#closee").click(function () {
				
				location.reload();
				
			})
			
});

$(function () {
    $('#new_rc').on('shown.bs.modal', function () {
        //$('#print_content').html('<iframe id="frame" width="100%" height="100%" onload="ifrhgh()" frameborder="0" src="/add_client"></iframe>');
    });
});

function ifrhgh(){
    var iframehght =  $("#frame").contents().height();
    $("#frame").height(iframehght);
}
/**
 * 
 */