$(document).ready(function(){
   $("#startDate").datepicker({ 
		 dateFormat: 'yy/mm/dd',
		 changeMonth: true,
		 changeYear: true,
		 yearRange: "-20:+1",
		 numberOfMonths: 1
		});
    $("#endDate").datepicker({ 
		 dateFormat: 'yy/mm/dd',
		 changeMonth: true,
		 changeYear: true,
		 yearRange: "-20:+1",
		 numberOfMonths: 1
		});
		
	checkDt();
	
	function checkDt(){
		var id = $("#dt").val();
		if(id == 0){
			$("#feedTr").hide();
		} else {
			$("#feedTr").show();
		}
			
		if(id == 1 || id == 4){
			$("#s").html('Start Date');
			$("#end").show();
			if(id == 1){
				$("#snapshot").show();
				$("#offset").show();
				$("#time").show();
				checkSnap();
			} else {
				$("#offsetId").val('');
				$("#hour").val(0);
				$("#minute").val(0);			
				$("#snapshotId").val(0);
				
				$("#snapshot").hide();
				$("#offset").hide();
				$("#time").hide();
			}
		} else {
			$("#s").html('Evaluation Date');
			
			$("#endDate").val('');
			$("#end").hide();
			$("#offsetId").val('');
			
			$("#hour").val(0);
			$("#minute").val(0);			
			$("#snapshotId").val(0);
			
			$("#snapshot").hide();
			$("#offset").hide();
			$("#time").hide();
		}
		
	};

	function checkSnap(){
		var snapId = $("#snapshotId").val();
		if(snapId != 0){
			$("#time").hide();
			$("#offset").hide();
		} else {
			$("#time").show();
			$("#offset").show();
		}
	};

	$("#snapshotId").change(function(){
		checkSnap();
	});
	
	$("#dt").change(function(){
		checkDt();
	});
 }); 