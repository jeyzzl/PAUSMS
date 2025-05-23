jQuery.noConflict();
window.columnBasic = function(title, subtitle,container, textoVertical, categoriasHorizontales, serie){	
    
	var chart;
    jQuery(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: container,
                type: 'column'
            },
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
                min: 0,
                title: {
                    text: textoVertical
                }
            },
            legend: {
            	backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                reversed: false,
                shadow: true
            },
            tooltip: {
                formatter: function() {
                	var text = '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ Highcharts.numberFormat(this.y, 0, ',');
                    return text;
                }
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            
            series: serie
        });
    });
    
};