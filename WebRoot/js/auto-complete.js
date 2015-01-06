function autoComplete(url, target) {
	$.getJSON(url, function( data ) {
	  	var availableTags = data.jsonResult.keyValue;

	  	$(target).autocomplete({
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
}

function autoComplete1(url, target, id) {
	$.getJSON(url, {id:id}, function(data) {
		var availableTags = data.jsonResult.keyValue;
	  	
	  	$(target).autocomplete({
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
}

function autoComplete2(url, target, id, value) {
	$.getJSON(url, {id:id,value:value}, function( data ) {
		var availableTags = data.jsonResult.keyValue;
		
	  	$(target).autocomplete({
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
}