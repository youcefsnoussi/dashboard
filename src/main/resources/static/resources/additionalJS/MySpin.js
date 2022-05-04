/**
 * 
 */

function BeginSpin(){
	
	let element = "<div id='spin' style='position:fixed; "+
					"left:0; "+
					"top:0; "+
					"width:100%;"+
					"height: 100%; "+
					"background:black; "+
					"opacity: 0.7'> " +
					
				  "</div>";
	
	let element1 = "<div class='spinner-border spinner-border-xl text-light' role='status' " +
	  				"style='position:fixed;left:41vw;top:36vh;width:20rem;height:20rem;font-size:10rem;'> "+
	  			   "</div> ";
	  			   //"<h5 style='color:white;margin-left:44%;margin-top:30%;'>Chargement en cours ...</h5>";
	
	$("body").append(element);
	$("#spin").append(element1); 
	/*
	console.log($("#spin").css("display"))
	$("#spin").css("display","block")
	console.log($("#spin").css("display"))*/
}

function EndSpin(){
	
	$("#spin").remove();
	//$("#spin").css("display","none")
}