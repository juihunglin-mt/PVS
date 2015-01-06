$(document).ready(function(){
	$(".rdfValueList").each(function(){
		var originalValue = $(this).val();
		
    	var tag= $(this);
    	var rdfId = $(this).parent().find(".rdfId").val();
    	
		$.ajax({
			url:"json-rdf-value",
			type:"post",
			data: {
				id:rdfId
			},
			dataType:"json",
			success: function(data){
				var keyValue = data.jsonResult.keyValue;
				tag.empty();
				for(var i = 0; i < keyValue.length; i++) {
					var model = keyValue[i];
					$option=$("<option/>");
					$option.attr("value",model[0]);
					$option.text(model[1]);
					tag.append($option);
					tag.val(originalValue);
				}
			}
		});
    });
    
	$(".rdfValueList").change(function(){
    	var pendingRfOutputId = $(this).parent().find('.pendingRfOutputId').val();
    	var rdfValueId = $(this).val();
    	
    	$.ajax({
			url:"json-rf-output-update",
			type:"post",
			data: {
				pendingRfOutputId : pendingRfOutputId,
				rdfValueId : rdfValueId,
			},
			dataType:"json",
			success: function(data){
//				alert("Update success");
			}
		});
    });
	
	$(".fixedValue").change(function(){
    	var pendingRfOutputId = $(this).parent().find('.pendingRfOutputId').val();
    	var fixedValue = $(this).val();
    	
    	$.ajax({
			url:"json-rf-output-update",
			type:"post",
			data: {
				pendingRfOutputId : pendingRfOutputId,
				fixedValue : fixedValue,
			},
			dataType:"json",
			success: function(data){
//				alert("Update success");
			}
		});
    });
	
	$.getJSON("json-exp-all", function( data ) {
	  	availableTags = data.jsonResult.keyValue;
	  	
	  	$(".auto").autocomplete({
	  		source: function(req, responseFn) {
		        var re = $.ui.autocomplete.escapeRegex(req.term);
		        var matcher = new RegExp( "^" + re, "i" );
		        var a = $.grep( availableTags, function(item,index){
		            return matcher.test(item);
		        });
		        responseFn( a );
		    },
		    change: function() {
	            var pendingRfOutputId = $(this).parent().find('.pendingRfOutputId').val();
		      	var rfExpValue = $(this).val();

		      	$.ajax({
		  			url: "json-check-exp",
		  			type:"post",
		  			data: {
		  				value : rfExpValue
		  			},
		  			dataType:"json",
		  			success: function(data){
		  				if(data.jsonResult.value == 0){
		  				    if (confirm("This expression is not exist. Create new one?") == true) {
		  				    	
		  				    	$.ajax({
		  							url:"json-rf-output-update",
		  							type:"post",
		  							data: {
		  								pendingRfOutputId : pendingRfOutputId,
		  								rfExpValue : rfExpValue,
		  							},
		  							dataType:"json",
		  							success: function(data){
		  								$.getJSON("json-exp-all", function( data ) {
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
//		  								alert("Update success");
		  							}
		  						});
		  				    } else {
		  				        return false;
		  				    }
		  				} else {
		  					$.ajax({
		  						url:"json-rf-output-update",
		  						type:"post",
		  						data: {
		  							pendingRfOutputId : pendingRfOutputId,
		  							rfExpValue : rfExpValue,
		  						},
		  						dataType:"json",
		  						success: function(data){
//		  							alert("Update success");
		  						}
		  					});
		  				}
		  			}
		  		});
		      },
		});
	});
	
});