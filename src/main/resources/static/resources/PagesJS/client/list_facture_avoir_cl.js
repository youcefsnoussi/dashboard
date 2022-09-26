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
	            this.api().columns([0,2,3]).every( function () {
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
	        drawCallback: function () {
				
			      var api = this.api();
			      
			      $( api.column( 4, {page:'current'} ).footer() ).html(
			        api.column( 4, {page:'current'} ).data().sum().formatMoney(2, ',', ' ')
			      );
			      
			      $( api.column( 5, {page:'current'} ).footer() ).html(
			        api.column(5, {page:'current'} ).data().sum().formatMoney(2, ',', ' ')
			      );
			      
			      $( api.column( 6, {page:'current'} ).footer() ).html(
			        api.column( 6, {page:'current'} ).data().sum().formatMoney(2, ',', ' ')
			      );
			      $( api.column( 7, {page:'current'} ).footer() ).html(
			        api.column( 7, {page:'current'} ).data().sum().formatMoney(2, ',', ' ')
			      );
			      
			 },
			
    };
 		
 	var table = $('#table').DataTable(datatable_config);
 	var id_fact; var num_fact;
 	$(".fact").click(function(){
		
		id_fact = $(this).attr("id_fact");
		
		
		$("#id_facture").val(id_fact);
		
		num_fact = $(this).attr("num_fact");
		
		console.log("id=>"+num_fact)
		
		$('#print_content').html('<iframe id="frame" width="100%" height="828" frameborder="0" '+
				'src="info_fact?id_fact='+id_fact+'"></iframe>');
		
		$("#title_fact").text("Détail Facture avoir N° "+num_fact);
		
		$("#icone_fact").attr("class","far fa-file-invoice");
		
		$("#fact_detail_modal").modal("show");
		
	});
 	
});
 	
 	
//$(".edit_end").click(function() {
//		
// 		//console.log("id=>"+$(this).attr("id_relation"))
// 		var id_rel = $(this).attr("id_relation");
// 		var end = $(this).parent().find(".end").val();
// 		
// 		$.ajax({
// 			url : 'update_date_end_relation',
// 			data : {
// 				id_relation : id_rel,
// 				end : end
// 			},
// 			success : function(responseJson) {
// 				
// 				alert("modification effectuée ")
// 				
// 			}
// 		});
// 		
//	});
// 	
//});
 	
 	