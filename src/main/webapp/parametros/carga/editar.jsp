<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		function Grabar() {
			if (document.frmcarga.CargaId.value != ""
					&& document.frmcarga.Nombre != ""
					&& document.frmcarga.Periodo != ""
					&& document.frmcarga.Inicio != ""
					&& document.frmcarga.Fin != ""
					&& document.frmcarga.FechaExtra != ""					
					&& document.frmcarga.FinServicios != ""
					&& document.frmcarga.Estado != "") {
				document.frmcarga.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}
	</script>
</head>
<body>
<%
	// Declaracion de variables
	String cargaId 			= (String)request.getParameter("CargaId")==null?"":request.getParameter("CargaId");
	String finServicios 	= request.getParameter("FinServicios")==null?"":request.getParameter("FinServicios");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	Carga carga 		= (Carga)request.getAttribute("carga");
%>
<div class="container-fluid">
	<h2>Extensión de servicios</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="carga"><spring:message code="aca.Listado"/></a>
	</div>
	<form name="frmcarga" id="frmcarga" action="grabar" method="post">
	<table style="width:35%" align="center" class="table table-condensed">			
	<tr>
		<td>Id Carga</td>
		<td><input type="text" class="input input-small" name="CargaId" id="CargaId" readonly="readonly" value="<%= carga.getCargaId() %>"></td>
	</tr>
	<tr>
		<td><spring:message code="aca.Nombre"/></td>
		<td><input type="text" class="input" name="Nombre" id="Nombre" readonly="readonly" value="<%= carga.getNombreCarga() %>" style="width: 280px;"></td>
	</tr>
	<tr>
		<td>Período</td>
		<td><input type="text" class="input input-small" name="Periodo" id="Periodo" readonly="readonly" value="<%= carga.getPeriodo() %>"></td>
	</tr>
	<tr>
		<td>Inicio</td>
		<td><input type="text" class="input input-small" name="Inicio" id="Inicio" readonly="readonly" value="<%= carga.getFInicio() %>"></td>
	</tr>
	<tr>
		<td>Fin</td>
		<td><input type="text" class="input input-small" name="Fin" id="Fin" readonly="readonly" value="<%= carga.getFFinal() %>"></td>
	</tr>
	<tr>
		<td><spring:message code="aca.Extra"/></td>
		<td>
			<input type="text" class="input input-small" name="FechaExtra" id="FechaExtra" data-date-format="dd/mm/yyyy" readonly="readonly" value="<%= carga.getFExtra() %>">					
		</td>
	</tr>
	<tr>
		<td>Servicio</td>
		<td>
			<input type="text" class="input input-small" name="FinServicios" id="FinServicios"  value="<%= carga.getFinServicios() %>">
			<img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FinServicios'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/AAAA)
		</td>
	</tr>
	<tr>
		<td><spring:message code="aca.Estado"/></td>
		<td><input type="text" class="input input-small" name="Estado" id="Estado" readonly="readonly" value="<%= carga.getEstado() %>"></td>
	</tr>
	<tr> 
		<th colspan="2" align="center" style="text-align:center;">
			<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>
		</th>
	</tr>
	</table>
	</form>
</body>
</html>
<script>
	jQuery('#FechaExtra').datepicker();
</script>