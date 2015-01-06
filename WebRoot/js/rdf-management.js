$(document).ready(function(){
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
	
	$("#next").click(function(){
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
		$("#doupdate").attr('action', 'rf-sheet-rdf-sort-next');
		$("#doupdate").submit();
	});
	
	$("#up").click(function(){
		moveUpItem();
	});

	$("#down").click(function(){
		moveDownItem();
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
