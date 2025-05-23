<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@page import="aca.exa.spring.ExaEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	String matricula 	= "";
	String year			= "";
	boolean anioEgreso  = false;
	if(request.getParameter("codigoAlumno")!=null){
		year		= request.getParameter("Year");
		matricula 	= request.getParameter("codigoAlumno");
		session.setAttribute("codigoAlumno",matricula);
		anioEgreso	= true;
	}else{
		matricula 		= (String) session.getAttribute("codigoAlumno");
	}
	
	List<ExaEvento> lisEventos 	= (List<ExaEvento>) request.getAttribute("lisEventos");
%>
<div class="container-fluid">
	<h2>Lista de Eventos asistidos</h2>
	<div class="alert alert-info">	
<%	
	if(anioEgreso){%>
		<table class="goback"><tr><td><a class="btn btn-primary" href="../reportes/anioEgreso?Year=<%=year%>">&lsaquo;&lsaquo;<spring:message code="aca.Regresar"/></a></td></tr></table>
<%	}	%>
		<a class="btn btn-primary" href="datos"><i class="icon-arrow-left"></i><spring:message code='aca.Regresar'/></a>
	</div>	
	<table class="table table-bordered" align="center" style="margin-top:5px;">
	<tr>
       <td><b><spring:message code="aca.Nombre"/> del Evento</b></td>
        <td><b>Fecha del Evento</b></td>
       <td><b>Lugar del Evento</b></td>
	</tr>
	<%
		for(ExaEvento even: lisEventos){	
	%>
	<tr>
		<td><%= even.getNombre() %></td>
        <td><%=even.getFechaEvento()%></td>
        <td><%=even.getLugar() %></td>
	</tr>
	<% } %>
	</table>
</div>