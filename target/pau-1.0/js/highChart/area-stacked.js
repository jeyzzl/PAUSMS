jQuery.noConflict();
window.areaStacked = function(title, subtitle,container, textoVertical, categoriasHorizontales, serie){
    var chart;
    jQuery(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: container,
                type: 'area'
            },
            title: {
                text: title
            },
            subtitle: {
                text: subtitle
            },
            xAxis: {
                categories: categoriasHorizontales,
                tickmarkPlacement: 'on',
                title: {
                    enabled: false
                }
            },
            yAxis: {
                title: {
                    text: textoVertical
                },
                labels: {
                    formatter: function() {
                        return this.value / 1000;
                    }
                }
            },
            tooltip: {
                formatter: function() {
                	var text = '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ Highcharts.numberFormat(this.y, 0, ',');
                    return text;
                }
            },
            plotOptions: {
                area: {
                    stacking: 'normal',
                    lineColor: '#666666',
                    lineWidth: 1,
                    marker: {
                        lineWidth: 1,
                        lineColor: '#666666'
                    }
                }
            },
            series: serie
        });
    });
    
};