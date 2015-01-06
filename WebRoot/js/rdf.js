$(document).ready(function(){
	$(".update").click(function(){
		var val = $(this).parent().parent().parent().parent().find($(".value")).val();
		var rvId = $(this).parent().parent().parent().parent().find($(".rdfValueList")).val();
		
		$(this).parent().parent().parent().parent().find($(".val")).val(val);
		$(this).parent().parent().parent().parent().find($(".rvId")).val(rvId);

		var nVer = navigator.appName;
		if(nVer == 'Microsoft Internet Explorer'){
			$(this).closest('form').submit();
		} else {
			$(this).parent().parent().parent().parent().find($(".form")).submit();
		}
		
	});
	
	checkFiletype();
	checkSelection();

	$("#filetype").click(function(){
		checkFiletype();
	});
	
	$("#selection").change(function(){
		checkSelection();
	});

	
	function checkSelection(){
		if($("#selection").val() == 0){
			$("#rdfValueList").show();
			$("#value").hide();
			
			$(".saveRdfValueList").each(function(){
				$(this).show();
			});
			
			$(".saveValue").each(function(){
				$(this).hide();
			});
			
		} else {
			$("#rdfValueList").hide();
			$("#value").show();

			$(".saveRdfValueList").each(function(){
				$(this).hide();
			});
			
			$(".saveValue").each(function(){
				$(this).show();
			});
		}
	};
	
	$(".oldSelection").change(function(){
		var id = $(this).parent().parent().find($(".0"));
		id.empty();
		if($(this).val() == 0){
			$.ajax({
				url:"json-rdf-value-all",
				type:"post",
				dataType:"json",
				success: function(data){
					var keyValue = data.jsonResult.keyValue;
					var content = "";
					for(var i = 0; i < keyValue.length; i++) {
						var model = keyValue[i];
						content += '<option value="'+model[0]+'" >'+model[1]+'</option>';
					}
					id.append(
						'<select name="rdfValueId" class="rdfValueList">'+
						content +
						'</select>');
				}
			});
		} else {
			id.append('<input type="text" class="value auto" size="100" />');
			autocomplete();
		}  
	});

	function checkFiletype(){
		if($("#filetype").prop('checked') == true){
			$("#on").show();
			$("#off").hide();
		} else {
			$("#on").hide();
			$("#off").show();
		}
	};
	
	
	
	$("#save").click(function(){
		if($("#rdfValueList").val() == null){
		
			var value = $("#value").val();
			var url = "";
			
			if($("#chkbox").prop('checked') == false && $("#filetype").prop('checked') == false ){
				url="json-check-exp";
				$.ajax({
					url: url,
					type:"post",
					data: {
						value : value
					},
					dataType:"json",
					success: function(data){
						if(data.jsonResult.value == 0){
						    if (confirm("This expression is not exist. Create new one?") == true) {
								$("#dosave").submit();
						    } else {
						        return false;
						    }
						} else {
							$("#dosave").submit();
						}
					}
				});
			} else {
				$("#dosave").submit();
			}
			
			
		
		} else {
			$("#dosave").submit();
		}
	});
	
//	$(".update").click(function(){
//		var tag = $(this);
//		if($(this).parent().parent().find(".rdfValueList").val() == null){
//		
//			var value = $(this).parent().parent().find(".value").val();
//			var chkbox = $(this).parent().parent().find(".chkbox");
//			var url = "";
//			
//			if(chkbox.prop('checked') == false){
//				url="json-check-exp";
//				$.ajax({
//					url: url,
//					type:"post",
//					data: {
//						value : value
//					},
//					dataType:"json",
//					success: function(data){
//						if(data.jsonResult.value == 0){
//						    if (confirm("This expression is not exist. Create new one?") == true) {
//								tag.attr('type', 'submit');
//								tag.click();
//						    } else {
//						        return false;
//						    }
//						} else {
//							tag.attr('type', 'submit');
//							tag.click();
//						}
//					}
//				});
//			} else {
//				tag.attr('type', 'submit');
//				tag.click();
//			}
//			
//			
//		
//		} else {
//			tag.attr('type', 'submit');
//			tag.click();
//		}
//		
//	});
	
	function stopReloadKey(evt) {
      var evt = (evt) ? evt : ((event) ? event : null);
      var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
      if (evt.keyCode == 13)  {
          return false;
      }
    }

    document.onkeypress = stopReloadKey;
    
//    $(".rdfValueList").each(function(){
//		var originalValue = $(this).val();
//    	var tag= $(this);
//    	var rdfId = $(this).parent().parent().find(".rdfId").val();
//		$.ajax({
//			url:"json-rdf-value",
//			type:"post",
//			data: {
//				id:rdfId
//			},
//			dataType:"json",
//			success: function(data){
//				var keyValue = data.jsonResult.keyValue;
//				tag.empty();
//				for(var i = 0; i < keyValue.length; i++) {
//					var model = keyValue[i];
//					$option=$("<option/>");
//					$option.attr("value",model[0]);
//					$option.text(model[1]);
//					tag.append($option);
//					tag.val(originalValue);
//				}
//			}
//		});
//    });
    
    autocomplete();
    
    function autocomplete(){
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
    }

    
    
});