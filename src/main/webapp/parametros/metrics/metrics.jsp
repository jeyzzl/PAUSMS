<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<!-- bootstrap -->	
	<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="../../bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
	<script src='../../bootstrap/js/bootstrap.min.js' type='text/javascript'></script>	
	<script src="../../js/jquery-1.9.1.min.js"></script>	
	<style type="text/css">
		#content{
			margin: 20px 20px 0 20px;
		}	  	
	</style>
</head>
<%
	String codigo 				= (String)session.getAttribute("codigoPersonal");
%>
<div class="container-fluid">
	<h2>Metrics</h2>
	<div class="alert alert-info">
		&nbsp;
	</div>
	<div id="recursos">Consultando...</div>
	
	<script>		
		$( document ).ready(function() {			
			var resultado = "";
			var mostrar = "mem,httpsessions.active,datasource.primary.active,datasource.dsArchivo.active,datasource.dsAdmision.active,datasource.dsSunPlus.active,heap.init,heap.used,heap,"+
						"gc.concurrentmarksweep.count,gc.concurrentmarksweep.time,instance.uptime,";
			$.getJSON("../../monitoracademico/metrics", function (data) {
				resultado += "<table class='table table-condensed' style='width:100%'>";
				resultado += "<tr><th width='40%'>Propiedad</th><th width='10%'>Valor</th><th width='60%'>&nbsp;</th></tr>";
				$.each(data, function (llave, valor) {
					//if (mostrar.includes(llave+",")){
						if (llave=="mem") valor = valor /1024+" mg";
						resultado += "<tr><td>"+llave+"</td> <td>"+valor+"</td><td>&nbsp;</td></tr>";
					//}	
		        });
				resultado += "</table>";
				$("#recursos").html(resultado);
			})	
		});		
	</script>
</div>
</html>