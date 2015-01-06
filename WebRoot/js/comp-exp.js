$(document).ready(function(){
	$.getJSON("json-comp-exp-all", function( data ) {
	  	availableTags = data.jsonResult.keyValue;
	  	
	  	$(".auto").autocomplete({
		    source: availableTags
		});
	});
	
});