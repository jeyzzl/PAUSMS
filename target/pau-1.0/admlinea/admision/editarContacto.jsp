<%@page import="aca.admision.spring.AdmRecomienda"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
</head>
<%
	AdmRecomienda recomendacion = (AdmRecomienda) request.getAttribute("recomendacion");

	String nombre 	= (String) request.getAttribute("nombre");
	String mensaje 	= (String) request.getAttribute("mensaje");
	
%>
<body>
<div class="container-fluid">
	<h2>Referee <small>&nbsp;(<%=recomendacion.getFolio()%> - <%=nombre%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listadoEncuestas?Folio=<%=recomendacion.getFolio()%>">
			<spring:message code="aca.Regresar" />
		</a>
	</div>
<% 		if(mensaje.equals("1")){%>
	<div class="alert alert-success">Updated</div>
<% 		}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">Error updating</div>
<% 		}%>
	<form class="form-group" name="frmComentario" action="grabarContacto" method="post">
		<input type="hidden" name="Folio" value="<%=recomendacion.getFolio()%>">
		<input type="hidden" name="Id" value="<%=recomendacion.getRecomendacionId()%>">
		<h3>Name:</h3>
		<input class="form-control" type="text" name="Nombre" value="<%=recomendacion.getNombre()%>"><br>
		<h3>Occupation:</h3>
		<input class="form-control" type="text" name="Puesto" value="<%=recomendacion.getPuesto()%>"><br>
		<h3>Email:</h3>
		<input class="form-control" type="text" name="Email" value="<%=recomendacion.getEmail()%>"><br>
		<h3>Phone Number:</h3>
		<input class="form-control" type="text" name="Telefono" value="<%=recomendacion.getTelefono()%>"><br>
		<button type="submit" class="btn btn-primary">Update</button>
	</form>
</div>
</body>
</html>