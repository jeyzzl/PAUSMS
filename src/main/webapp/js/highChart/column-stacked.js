	jQuery.noConflict();
	window.columnStacked = function(title, subtitle,container, textoVertical, categoriasHorizontales, serie){
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
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
            	backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                reversed: false,
                shadow: true
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ Highcharts.numberFormat(this.y, 0, ',') +'<br/>'+
                        'Total: '+ Highcharts.numberFormat(this.point.stackTotal,0 ,',');
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: serie
        });
    });
	};