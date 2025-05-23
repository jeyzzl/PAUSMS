<%@page import="aca.exa.spring.ExaEvento"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file="../../idioma.jsp"%>

<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	function Nuevo() {
		document.frmEvento.EventoId.value 	= " ";
		document.frmEvento.Nombre.value 	= " ";
		document.frmEvento.Fecha.value	 	= " ";
		document.frmEvento.Lugar.value	 	= " ";		
		document.frmEvento.submit();
	}
		
	function Grabar() {
		if (document.frmEvento.EventoId.value != ""
			&& document.frmEvento.Nombre != "" && 
			document.frmEvento.Lugar != "") {			
			document.frmEvento.submit();
		} else {
			alert("Complete el formulario ..! ");
		}
	}	
</script>
</head>
<%
	// Declaracion de variables
	boolean existe 			= (boolean)request.getAttribute("existe");
	ExaEvento exaEvento 	= (ExaEvento)request.getAttribute("exaEvento");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
	<div class="container-fluid">
	<h2>Catálogo de Eventos</h2>
	<div class="alert alert-info">
		<a href="evento" class="btn btn-primary"><spring:message code="aca.Listado"/></a>
	</div>		
	<form name="frmEvento" action="grabar" method="post">
		<input type="hidden" name="Eliminado" value="<%=exaEvento.getEliminado()%>">
		<input type="hidden" name="IdEvento" value="<%=exaEvento.getIdEvento()%>">		
		<table style="width:35%" class="table table-condensed">
			<tr>
				<td><strong><spring:message code="aca.Clave"/>:</strong></td>
				<td>
					<input id="EventoId" name="EventoId" type="text" class="form-control" size="3" maxlength="8" value="<%=exaEvento.getEventoId() %>" readonly>
				</td>
			</tr>
			<tr>
				<td><strong><spring:message code="aca.Nombre"/>:</strong></td>
				<td>
					<input name="Nombre" type="text" id="Nombre" class="form-control" size="40" maxlength="150" value="<%= exaEvento.getNombre()%>">
				</td>
			</tr>
			<tr>
				<td><strong>Lugar:</strong></td>
				<td>
					<input name="Lugar" type="text" id="Lugar" class="form-control" size="40" maxlength="150" value="<%=exaEvento.getLugar() %> ">
				</td>
			</tr>
			<tr>
				<td>Fecha Evento:</td>
				<td> 
					<input name="FechaEvento" type="text" class="form-control" value="<%=exaEvento.getFechaEvento()%>" data-date-format="dd/mm/yyyy" id="FechaEvento" size="10">
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;"><%=mensaje%></td>
			</tr>
			<tr>
				<th colspan="2">
					<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a>&nbsp;
					<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>&nbsp;						
				</th>
			</tr>
		</table>
	</form>
	</div>
<script>
	(function($){
		$('#FechaEvento').datepicker();	
	})(jQuery);
</script>		
</body>