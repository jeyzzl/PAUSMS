<%@page import="aca.cita.spring.CitaEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal 	= (String)session.getAttribute("codigoPersonal");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	java.util.List<CitaEvento> lista = (java.util.List<CitaEvento>) request.getAttribute("lista");
%>
<div class="container-fluid">
	<h3>Lista de eventos<small class="text-muted fs-5">( <%=codigoPersonal%> - <%=nombreAlumno%>)</small></h3>
	<div class="alert alert-info">	
		<a href="resumen" class="btn btn-primary">Regresar</a>
	</div>		 
	<table class="table table-sm table-border">
	<tr>
		<th>#</th>	
		<th>Evento</th>
	</tr>
<%	for (CitaEvento evento : lista){ %>
	<tr>
		<td><%=evento.getEventoId()%></td>	
		<td>
			<a href="citaHora?EventoId=<%=evento.getEventoId()%>">
			<%=evento.getEventoNombre()%>
			</a>
		</td>
	</tr>	
<%	} %>
</div>