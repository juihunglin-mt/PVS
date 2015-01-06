$(function(){

	setTimeout(function() {getData();},200);
	
	setInterval(function() {
		$('.stats').children('div').remove();
		$('table').parent('div').remove();
		getData();
	}, 30000);

	function getData() {
    
		$.ajax({
		  url: "json-allready",
		  type: "POST",
		  dataType: "json",
		  success: function(JData) {

				var i = 0,
					j = 0,
					k = 0;

				$.each(JData.jsonResult.sizes, function() {

					var $totalName = JData.jsonResult.sizes[i].name,
						$totalNum = JData.jsonResult.sizes[i].size;
					
					if($totalNum != null){
				  		$('.stats').append('<div>'+
												'<h3>'+ $totalNum +'</h3>'+
												'<p>'+ $totalName +'</p>'+
											'</div>');
	
						if ($totalNum <= 10){$('.stats div:last-child').addClass('less')}
						else if ($totalNum > 10 && $totalNum <= 50){$('.stats div:last-child').addClass('medium')}
						else if ($totalNum > 50){$('.stats div:last-child').addClass('more')}
					}
					
					i++;
		  		});

				i = 0;

				$.each(JData.jsonResult.values, function() {

					var $dataName = JData.jsonResult.values[i].table,
						$dataLink = JData.jsonResult.values[i].link,
						$tableName = JData.jsonResult.values[i].tableName,
						$tableClient = JData.jsonResult.values[i].client;

					
					if(JData.jsonResult.values[i].list[j] != undefined){
						var link = '',
							client ='',
							status_error = false;
						
						if($dataName == 'download') {
							$.each(JData.jsonResult.values[i].list, function() {
								if(JData.jsonResult.values[i].list[k].status == 'Error' || JData.jsonResult.values[i].list[k].status == 'Download Failed') {
									status_error = true;
								}
								k++;
							});
						} else {
							link = '<a href="'+ $dataLink +'" class=" rounded-corners"><span>View more</span></a>';
						}

						if($dataName != 'download' && $dataName != 'cross' && 
								$dataName != 'instrument' && $dataName != 'instrumentRejected' && 
								$dataName != 'beta' && $dataName != 'betaRejected' ){
							client = '<span>'+ $tableClient +'</span>';
						}
					
						$('.content').append('<div class="column container">'+
												'<table id="data-'+ $dataName +'">'+
													'<caption>'+
														'<h2>'+ $tableName + client + '</h2>' + link +
													'</caption>'+
													'<tr>' +
									                  '<th>Date</th>' +
									                  '<th>Action Name</th>' +
									                  '<th>Name</th>' +
									                  '<th>Status</th>' +
								                  	'</tr>'+
												'</table>'+
											'</div>');
						if (i%2 == 0){$('#data-' + $dataName).parent('div').addClass('c-40')}
						else {$('#data-' + $dataName).parent('div').addClass('c-55')}
						
						$.each(JData.jsonResult.values[i].list, function() {
							
							var hid = '';
							if($dataName == 'download' && status_error == true) {
								if ($('#data-' + $dataName).find('th').length < 5){
									$('#data-' + $dataName).find('tr:first-child').append('<th></th>');
								}

								if(JData.jsonResult.values[i].list[j].status == 'Error') {
									hid = '<td><ul class="tool"><li><a href="download-hide?downloadId=' + 
												JData.jsonResult.values[i].list[j].id + 
												'" class="hide"><span>Hide<i></i></span></a></li></ul></td>';
								} else if(JData.jsonResult.values[i].list[j].status == 'Download Failed') {
									hid = '<td><ul class="tool"><li><a href="download-hide?downloadId=' + 
												JData.jsonResult.values[i].list[j].id + 
												'" class="export"><span>Re-download<i></i></span></a></li></ul></td>';
								} else {
									hid = '<td></td>';
								}
							} 
							
							$('#data-'+JData.jsonResult.values[i].table).append('<tr>' +
					                  '<td>' + JData.jsonResult.values[i].list[j].date    		+ '</td>' +
					                  '<td>' + JData.jsonResult.values[i].list[j].editTypeName 	+ '</td>' +
					                  '<td>' + JData.jsonResult.values[i].list[j].name 			+ '</td>' +
					                  '<td><span class="' +JData.jsonResult.values[i].list[j].cssClass + '">' + JData.jsonResult.values[i].list[j].status + '</span></td>' +
					                  hid + 
					                  '</tr>');
							j++;
						});
					}
					j=0; 
				  	i++;
				});

		  },
		  error: function() {
		    /*alert("ERROR!!!");*/
		  }
		});

	};
});