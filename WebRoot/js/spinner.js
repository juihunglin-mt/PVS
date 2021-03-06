$(document).ready(function() {
	// ==================== LOADING ====================== //

	var opts = {
	  lines: 15, // The number of lines to draw
	  length: 0, // The length of each line
	  width: 5, // The line thickness
	  radius: 25, // The radius of the inner circle
	  corners: 1, // Corner roundness (0..1)
	  rotate: 0, // The rotation offset
	  direction: 1, // 1: clockwise, -1: counterclockwise
	  color: '#000', // #rgb or #rrggbb or array of colors
	  speed: 1, // Rounds per second
	  trail: 100, // Afterglow percentage
	  shadow: false, // Whether to render a shadow
	  hwaccel: false, // Whether to use hardware acceleration
	  className: 'spinner', // The CSS class to assign to the spinner
	  zIndex: 2e9, // The z-index (defaults to 2000000000)
	  top: '50%', // Top position relative to parent
	  left: '50%' // Left position relative to parent
	};
	var target = document.getElementById('loading');
	var spinner = new Spinner(opts);
	var $loadingBg = $('#loading_wrapper');

	loading_hide();

	function loading_show(){
        spinner.spin(target);
    	$loadingBg.show();
	}

	function loading_hide(){
        spinner.spin();
    	$loadingBg.hide();
	}
	
	$(".submit").click(function(){
		loading_show();
	});
	
});