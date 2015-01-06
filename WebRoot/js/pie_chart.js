$(function () {
	
	
    var chart;
    
    $(document).ready(function () {
    	$.getJSON("json-chart-feed", function( data ) {
//    		var valueList = data.jsonResult.valueList;
    		var list = data.jsonResult.list;
    		
    		$('#usage-chart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                tooltip: {
                	formatter: function() {
                        return '<b>'+ this.point.name +'</b><br>Usage: '+ Highcharts.numberFormat(this.percentage, 2, '.') +' %<br>Amount: '+this.point.y;
                    }
                },
                credits: {
                  enabled: false
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{
                    type: 'pie',
                    name: 'Usage',
                    data: list
                }]
            });
    	});
    	
        
    	$.getJSON("json-chart-group", function( data ) {
//    		var valueList = data.jsonResult.valueList;
    		var list = data.jsonResult.list;
    		
    		$('#instrumentgroup-chart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                tooltip: {
                	formatter: function() {
                        return '<b>'+ this.point.name +'</b><br>Usage: '+ Highcharts.numberFormat(this.percentage, 2, '.') +' %<br>Amount: '+this.point.y;
                    }
                },
                credits: {
                  enabled: false
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{
                    type: 'pie',
                    name: 'Usage',
                    data: list
                }]
            });
        });
    });
        

    /**
     * Grid-light theme for Highcharts JS
     * @author Torstein Honsi
     */

    // Load the fonts
    Highcharts.createElement('link', {
       href: 'http://fonts.googleapis.com/css?family=Dosis:400,600',
       rel: 'stylesheet',
       type: 'text/css'
    }, null, document.getElementsByTagName('head')[0]);

    Highcharts.theme = {
       colors: ["#7cb5ec", "#f7a35c", "#90ee7e", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
          "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
       chart: {
          backgroundColor: null,
          style: {
             fontFamily: "Avenir Next,Helvetica Neue,Helvetica,Arial,微軟正黑體,sans-serif"
          }
       },
       title: {
          style: {
             fontSize: '16px',
             fontWeight: 'bold',
             textTransform: 'uppercase'
          }
       },
       tooltip: {
          borderWidth: 0,
          backgroundColor: 'rgba(219,219,216,0.8)',
          shadow: false
       },
       legend: {
          itemStyle: {
             fontWeight: 'bold',
             fontSize: '13px'
          }
       },
       xAxis: {
          gridLineWidth: 1,
          labels: {
             style: {
                fontSize: '12px'
             }
          }
       },
       yAxis: {
          minorTickInterval: 'auto',
          title: {
             style: {
                textTransform: 'uppercase'
             }
          },
          labels: {
             style: {
                fontSize: '12px'
             }
          }
       },
       plotOptions: {
          candlestick: {
             lineColor: '#404048'
          }
       },


       // General
       background2: '#F0F0EA'
       
    };

    // Apply the theme
    Highcharts.setOptions(Highcharts.theme);
    
});
