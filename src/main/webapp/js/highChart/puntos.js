$.noConflict();
window.Puntos = function(title, subtitle, container, textoVertical, categoriasHorizontales, serie){
 	var chart;  	
	jQuery(document).ready(function() {	
	
		chart = new Highcharts.Chart({
            chart: {
                renderTo: container,
                type: 'line'
            },
            exporting: {url: 'http://export.highcharts.com/index-utf8-encode.php'},
            title: {
                text: title
            },
            subtitle: {
                text: subtitle
            },
            xAxis: {
                categories: categoriasHorizontales
            },
            yAxis: {
                title: {
                    text: textoVertical
                }
            },
            tooltip: {
                enabled: true,
                formatter: function() {
                	var text = '<b>'+  this.x +': '+ Highcharts.numberFormat(this.y, 0, ',') ;
                    return text;
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: true
                }
            },
            series: serie
        });
	});
};	