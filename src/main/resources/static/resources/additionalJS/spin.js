/**
 * Copyright (c) 2011-2014 Felix Gnass
 * Licensed under the MIT license
 * http://spin.js.org/
 *
 * Example:
    var opts = {
      lines: 12             // The number of lines to draw
    , length: 7             // The length of each line
    , width: 5              // The line thickness
    , radius: 10            // The radius of the inner circle
    , scale: 1.0            // Scales overall size of the spinner
    , corners: 1            // Roundness (0..1)
    , color: '#000'         // #rgb or #rrggbb
    , opacity: 1/4          // Opacity of the lines
    , rotate: 0             // Rotation offset
    , direction: 1          // 1: clockwise, -1: counterclockwise
    , speed: 1              // Rounds per second
    , trail: 100            // Afterglow percentage
    , fps: 20               // Frames per second when using setTimeout()
    , zIndex: 2e9           // Use a high z-index by default
    , className: 'spinner'  // CSS class to assign to the element
    , top: '50%'            // center vertically
    , left: '50%'           // center horizontally
    , shadow: false         // Whether to render a shadow
    , hwaccel: false        // Whether to use hardware acceleration (might be buggy)
    , position: 'absolute'  // Element positioning
    }
    var target = document.getElementById('foo')
    var spinner = new Spinner(opts).spin(target)
 */

function spin_it(mode){
	
//	console.log('spin it');
	
	var opts = {
		      lines: 12             // The number of lines to draw
		    , length: 7             // The length of each line
		    , width: 5              // The line thickness
		    , radius: 10            // The radius of the inner circle
		    , scale: 3.0            // Scales overall size of the spinner
		    , corners: 1            // Roundness (0..1)
		    , color: '#000'         // #rgb or #rrggbb
		    , opacity: 1/4          // Opacity of the lines
		    , rotate: 0             // Rotation offset
		    , direction: 1          // 1: clockwise, -1: counterclockwise
		    , speed: 1              // Rounds per second
		    , trail: 100            // Afterglow percentage
		    , fps: 20               // Frames per second when using setTimeout()
		    , zIndex: 2e9           // Use a high z-index by default
		    , className: 'spinner'  // CSS class to assign to the element
		    , top: '50%'            // center vertically
		    , left: '50%'           // center horizontally
		    , shadow: false         // Whether to render a shadow
		    , hwaccel: false        // Whether to use hardware acceleration (might be buggy)
		    , position: 'absolute'  // Element positioning
		    }
	
	if(mode=="on"){
		var $div = $('<div />').appendTo('body');
		$div.attr('id', 'snowLoading');
		
		$div.css({
			position:'fixed',
			left:0,
			top:0,
			width:"100%",
			height: "100%",
			"z-index":999999999
		});
		
		
		var $black = $('<div />').appendTo($div);
		$black.attr('id', 'blackscreen');
		
		$black.css({
			position:'fixed',
			left:0,
			top:0,
			width:"100%",
			height: "100%",
			background:'black',
			opacity: "0.5"
		});
		
		
		var $spin = $('<div />').appendTo($div);
		$spin.attr('id', 'spinLoading');
		
		$spin.css({
			position:'fixed',
			left:0,
			top:0,
			width:"100%",
			height: "100%"		
		});
		
		var target = document.getElementById('spinLoading');
		var spinner = new Spinner(opts).spin(target);
		
	}
	
	else{
		
		$("#snowLoading").remove();
		
	}
		
	
	
	
}