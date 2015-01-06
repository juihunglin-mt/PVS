var arr = new Array();
$(document).ready(function(){
	removeUseField();
	
	$("#sub").click(function(){
		var arr = getUseFieldIds();
		var check = arr.length -1;
		var useFieldIds = "";
		for(i = 0; i < arr.length; i++) {
			useFieldIds += arr[i];
			if(i < check) {
				useFieldIds += ",";
			}
		}
		$("#hiddenField").val(useFieldIds);
		$("#doupdate").submit();
	});
	
	$("#add").click(function() {
		$("#allField option:selected").appendTo($("#useField"));
		/*var addField = $("#allField").val();
		for(i = 0; i < addField.length; i++) {
			if(checkUseField(addField[i])) {
				var optionText = $("#allField option[value="+addField[i]+"]").text();
				$("#useField").append($("<option></option>").attr("value", addField[i]).text(optionText));
			}
		}*/
	});
	
	$("#remove").click(function(){
		$("#useField option:selected").appendTo($("#allField"));
		/*var removeField = $("#useField").val();
		for(i = 0; i < removeField.length; i++) {
			$("#useField option[value="+removeField[i]+"]").remove();
		}*/
	});
	
	$("#up").click(function(){
		moveUpItem();
	});

	$("#down").click(function(){
		moveDownItem();
	});
	
	$("#text_filter").keyup(function() {
		arr = new Array();
		var text_filter = $(this).val();
		if(text_filter != "") {
			$("#allField option").each(function() {
				var optionText = $(this).text();
				if(optionText.indexOf(text_filter) != -1) {
					arr.push($(this).val());
				} 
				$(this).prop("selected", false);
			});
			if(arr.length > 0) {
				$("#allField option[value="+arr[0]+"]").prop("selected", true);
				$("#allField option:selected").focus();
				$("#focus_field").html($("#allField option:selected").text());
			}
		} else {
			$("#allField option").each(function() { 
				$(this).prop("selected", false);
				$("#focus_field").html("");
			});
		}
		
	});
	
	$("#back").click(function(){
		if(arr.length > 0) {
			var key = $("#allField option:selected").val();
			if(arr.length > 1) {
				for(i = 0; i < arr.length; i++) {
					if(arr[i] == key) {
						if(i == 0) {
							key = arr[arr.length-1];
						} else {
							key = arr[i -1];
						}
						break;
					}
				}
			}
			$("#allField option").each(function() { 
				$(this).prop("selected", false);
			});
			$("#allField option[value="+key+"]").prop("selected", true);
			$("#allField option:selected").focus();
			$("#focus_field").html($("#allField option:selected").text());
		}
	});
	
	$("#next").click(function(){
		if(arr.length > 0) {
			var key = $("#allField option:selected").val();
			if(arr.length > 1) {
				for(i = 0; i < arr.length; i++) {
					if(arr[i] == key) {
						var max = arr.length -1;
						if(i == max) {
							key = arr[0];
						} else {
							key = arr[i + 1];
						}
						break;
					}
				}
			;}
			$("#allField option").each(function() { 
				$(this).prop("selected", false);
			});
			$("#allField option[value="+key+"]").prop("selected", true);
			$("#allField option:selected").focus();
			$("#focus_field").html("   <b>" + $("#allField option:selected").text() + "</b>");
		}
	});
});

function checkUseField(index) {
	if($("#useField option[value="+index+"]").length > 0){
		return false;
	} else {
		return true;
	}
}

function getUseFieldIds() {
	var arr = new Array();
	$("#useField option").each(function() {
		var val = $(this).val();
		arr.push(val);
	});
	return arr;
}

function moveUpItem(){
	$('#useField option:selected').each(function(){
		$(this).insertBefore($(this).prev());
	});
}

function moveDownItem(){
	$('#useField option:selected').each(function(){
		$(this).insertAfter($(this).next());
	});
}

function removeUseField() {
	$("#useField option").each(function() {
		var val = $(this).val();
		$("#allField option[value="+val+"]").remove();
	});
}