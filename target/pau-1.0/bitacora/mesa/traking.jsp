<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<div class="container-fluid">
	<h2>Bitácora de Servicios Escolares / Mesa de entrada - Salida / Traking de solicitudes</h2>
	<div class="alert alert-info">
		<a href="entrada" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>&nbsp;
	</div>
	<br>
	<table class="table" style="width:100%">
		<tr>
			<th>NOMBRE DEL SOLICITANTE</th>
			<th>Folio: </th>
			<th>Trámite: </th>
			<th>STATUS: </th>
		</tr>
	</table><br><br>
	<table class="table" style="width:100%;">
		<tr>
			<th style="background-color:#A9D0F5;">Estadística:</th>
			<th>Tiempo minimo :</th>
			<th style="background-color:#A9D0F5;">1.5 meses </th>
			<th>Tiempo maximo :</th>
			<th style="background-color:#A9D0F5;">2.5 meses </th>
			<th>Tiempo promedio : </th>
			<th style="background-color:#A9D0F5;">2 meses </th>
		</tr>
	</table><br><br>
	<table class="table">
		<tr>
			<th width="15%" >Fecha</th>
			<th width="15%" >Hora</th>
			<th width="30%" >Atendido por</th>
			<th width="70%" >Detalle</th>
		</tr>
	</table>
</div>