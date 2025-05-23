<%@page import="java.util.List"%>
<%@page import="aca.exa.spring.ExaEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	List<ExaEvento> lisEventos 	= (List<ExaEvento>) request.getAttribute("lisEventos"); 
%>
<body>
<div class="container-fluid">
	<h2>Eventos</h2>
	<hr>
	<table  class="table table-bordered table-striped">
		<tr>
			<th style="text-align:center" width="5%"><spring:message code="aca.Numero"/></th>
			<th width="35%"><spring:message code='aca.Descripcion'/></th>
			<th width="45%">Lugar</th>
			<th width="10%"><spring:message code="aca.Fecha"/></th>
		</tr>
	<%
		for(int i=0; i<lisEventos.size(); i++){
			ExaEvento evento = (ExaEvento) lisEventos.get(i);
			%>
			<tr>
				<td align="center"><%= i+1%></td>
				<td><a href="exalumnos?EventoId=<%=evento.getEventoId() %>"><%= evento.getNombre() %></a></td>
				<td><%= evento.getLugar() %></td>
				<td><%= evento.getFechaEvento() %></td>
			</tr>
<%		} %>
	</table>
  </div>
</body>