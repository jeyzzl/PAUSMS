// Cambia el signo "$" a "jQuery" para eliminar los conflictos con otras librerias js
 
$.noConflict();
window.Pie = function(titulo, objeto, serie){
	
 	var chart;  	
	jQuery(document).ready(function() {	
	
		// Grafica 
		chart = new Highcharts.Chart({
			chart: {
				renderTo: objeto,
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
            // exporting: {url: 'http://export.highcharts.com/index-utf8-encode.php'},
			title: {
				text: titulo
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
				}
			},
			
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: true,
						formatter: function() {
							return '<b>'+ this.y +'% </b>';
						}
					},
					showInLegend:{
						enabled:true,
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					} 					
				}
			},
			
		    series: [{
				type: 'pie',
				name: 'Serie',
				data: serie 
			}]
		});
});
};	