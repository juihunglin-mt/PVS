$(document).ready(function(){
	$.getJSON("json-identifier-all", function( data ) {
	  	availableTags = data.jsonResult.keyValue;
	  	
	  	$(".auto").autocomplete({
	  		source: function(req, responseFn) {
		        var re = $.ui.autocomplete.escapeRegex(req.term);
		        var matcher = new RegExp( "^" + re, "i" );
		        var a = $.grep( availableTags, function(item,index){
		            return matcher.test(item);
		        });
		        responseFn( a );
		    }
		});
	});
});