/**
 * 
 */

$(document).ready(function(){
				
	$('.sidenav').sidenav();
	$('.collapsible').collapsible({accordion: true});
	
	$(".li_drop").click(function(){
		
		$(".li_drop").css("background-color","transparent");
		
		$(this).css("background-color","#f5f5f5");	
		
	});
	
	var res = 0;
	
	$.ajaxSetup({async: false});
	$.ajax({
		url: 'get_pay_impaye',
        success : function(responseJson) {
        	
        	$.each(responseJson, function(key, value) {
				res = value;
        	});
		}
	});	
	
	$("#Operation").text(res); $("#listedespaiements").text(res);
	
});