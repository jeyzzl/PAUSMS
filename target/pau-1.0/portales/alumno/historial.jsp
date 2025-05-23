<%@page import="aca.plan.MapaCurso"%>
<%@page import="java.util.List"%>
<%@page import="java.text.*" %>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.conva.spring.ConvHistorial"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="convE" scope="page" class="aca.conva.ConvEvento"/>
<jsp:useBean id="convEU" scope="page" class="aca.conva.ConvEventoUtil"/>
<jsp:useBean id="convH" scope="page" class="aca.conva.ConvHistorial"/>
<jsp:useBean id="convHU" scope="page" class="aca.conva.ConvHistorialUtil"/>
<jsp:useBean id="convM" scope="page" class="aca.conva.ConvMateria"/>
<jsp:useBean id="convMU" scope="page" class="aca.conva.ConvMateriaUtil"/>

<%@ include file= "menu.jsp" %>
<%-- <jsp:include page="../menu.jsp" /> --%>
<%	
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
	
	String codigoPersonal = (String) request.getAttribute("matricula");
	
	List<ConvEvento> lisConv = (List<ConvEvento>) request.getAttribute("lisConv");
	
	List<ConvHistorial> lisHistorial = (List<ConvHistorial>) request.getAttribute("lisHistorial");
	
%>
<html>
<head></head>
<body>
<%	
	if(lisConv.size() != 0){
		for(ConvEvento conv : lisConv){
			
%>
<div class="container-fluid">
	<h3><spring:message code="datosAlumno.portal.ConvTitulo"/> <%=conv.getPlanId() %></h3>
	<div class="alert alert-info">
		<a href="convalidacion" class="btn btn-primary">Regresar</a>
	</div>
	<table  width="50%"   align="center" class="table table-sm">
		<tr>
			<th ><font size="4"><spring:message code="aca.Estado"/></font></th>
			<th><font size="4"><spring:message code="aca.Fecha"/></font></th>
		</tr>
<%
			if(!conv.getEstado().equals("X")){
				for(ConvHistorial historial : lisHistorial){
					String estado = historial.getEstado();
%>
		<tr>
			<td><font size="3"> 
			<%if(estado.equalsIgnoreCase("S")){%>
				<spring:message code="datosAlumno.portal.ConvSolicitada"/>
			<%} else if(estado.equalsIgnoreCase("A")){%>
				<spring:message code="datosAlumno.portal.ConvAceptada"/>
			<%} else if(estado.equalsIgnoreCase("T")){%>
				<spring:message code="datosAlumno.portal.ConvTramite"/>
			<%} else if(estado.equalsIgnoreCase("X")){%>
				<spring:message code="datosAlumno.portal.ConvCancelada"/>
			<%} else if(estado.equalsIgnoreCase("R")){%>
				<spring:message code="datosAlumno.portal.ConvRegistrada"/>
			<%} else if(estado.equalsIgnoreCase("C")){%>
				<spring:message code="datosAlumno.portal.ConvConfirmada"/>
			<%} else if(estado.equalsIgnoreCase("P")){%>
				Predictamen	
			<%}else{ %>
				Otro:<%=estado%>
			<%} %>
			</font></td>
			<td style="text-align:left;"><font size="3"><%=historial.getFecha() %></font></td>
		</tr>
<%
				}
			}else{
%>
				<tr>
					<td align="center"><font size="3"><spring:message code="datosAlumno.portal.ConvCancelada"/></font></td>
					<td align="center"><font size="3"><%=conv.getFecha() %></font></td>
				</tr>
<%
			}
%>
		  </table>
<%
		}
	}else{
%>
		<table style="width:100%">
			<tr>
				<td align="center"><font size="3"><spring:message code="datosAlumno.portal.Nota17"/></font></td>
			</tr>
		</table>
<%
	}
%>
</div>