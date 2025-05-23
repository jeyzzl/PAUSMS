<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.admision.spring.AdmEvento"%>
<html>
<head>	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>	
</head>
<%
	AdmEvento evento 	= (AdmEvento)request.getAttribute("evento");
	String mensaje		= (String)request.getAttribute("mensaje");
%>
<body>
	<div class="container-fluid">
		<h2>Exam Venues</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a href="lista" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
		</div>
<% 	if(mensaje.equals("1")){%>
		<div class="alert alert-success" role="alert">
			<spring:message code="aca.Guardado"/>
		</div>
<% 	}%>
		<form action="grabar" method="post">
			<input name="EventoId" type="hidden" value="<%=evento.getEventoId()%>">
			<div class="row">
				<div class="form-group col-md-6 mb-4">
					<label><b>Venue</b></label> 
					<input name="Sede" type="text" class="form-control" value="<%=evento.getEventoNombre()%>">
				</div>
				<div class="span-6 col-md-6">
					<label><b>Location</b></label> 
					<input name="Lugar" type="text" class="form-control" value="<%=evento.getLugar()%>">
				</div>
			</div>
			<div class="row">
				<div class= "span-6  col-md-6">
					<label><b><spring:message code="aca.Fecha"/></b></label> 
					<input name="Fecha" id="fecha" type="text" data-date-format="yyyy/mm/dd" maxlength="10" class="form-control" value="<%=evento.getFecha().equals("") ? evento.getFecha() : evento.getFecha().substring(0, 10)%>">
				</div>
				<div class="span-6 col-md-6">
					<label><b><spring:message code="aca.Status"/></b></label> 
					<select name="Estado" class="form-control">
						<option value="A" <%=evento.getEstado().equals("A") ? "selected" : ""%>>Active</option>
						<option value="I" <%=evento.getEstado().equals("I") ? "selected" : ""%>>Inactive</option>
					</select>
				</div>
			</div>
			<br><button type="submit" class="btn btn-primary"><spring:message code="aca.Guardar"/></button></br>
		</form>
	</div>
</body>
<script>
	jQuery('#fecha').datepicker();
</script>
</html>