<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.*" %>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.conva.spring.ConvHistorial"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%		
	String colorPortal 				= session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");
	String codigoPersonal 			= (String) request.getAttribute("matricula");	
	
	List<ConvEvento> lisConvalidaciones 	= (List<ConvEvento>) request.getAttribute("lisConvalidaciones");	
	List<ConvMateria> lisMaterias 			= (List<ConvMateria>) request.getAttribute("lisMaterias");	
	HashMap<String, String> mapCursos		= (HashMap<String, String>) request.getAttribute("mapCursos");	
%>
<html>
<head></head>
<body>
<div class="container-fluid">
<%
	if(lisConvalidaciones.size() > 0){	
		for(ConvEvento convalidacion : lisConvalidaciones){
%>
	<h3><spring:message code="portal.alumno.convalidacion.TramitesDeConvalidación"/> <small class="text-muted fs-5">( <spring:message code="portal.alumno.convalidacion.ProgramaAcademico"/>: <%=convalidacion.getPlanId() %>)</small></h3>
	<hr>		
		<table class="table table-sm">
		<tr>
			<td colspan="3">
				<h5><a href="historial"><spring:message code="portal.alumno.convalidacion.HistorialDelTramite"/></a></h5>
			</td>		
		</tr>
		<tr>
			<th style="text-align: left;"><font size="4"><spring:message code="aca.Curso"/></font></th>
			<th style="text-align: left;"><font size="4"><spring:message code="aca.Tipo"/></font></th>
			<th style="text-align: left;"><font size="4"><spring:message code="aca.Estado"/></font></th>
		</tr>
<%
			String nombreMateria = "-";
			for(ConvMateria materia : lisMaterias){				
				if (convalidacion.getConvalidacionId().equals(materia.getConvalidacionId()))
					if(mapCursos.containsKey(materia.getCursoId())){
						nombreMateria = mapCursos.get(materia.getCursoId());
					}
%>
		<tr>
			<td><font size="3"><%=nombreMateria%></font</td>
			<td><font size="3">
				<% String tipo = materia.getTipo();
				if(tipo.equals("I")){%>
					<spring:message code="datosAlumno.portal.ConvInterna"/>
				<%}else if(tipo.equals("E")){ %>
					<spring:message code="datosAlumno.portal.ConvExterna"/>
				<%} %>
			</font></td>
			<td><font size="3">
	<% 	String estado = materia.getEstado(); 
		if(convalidacion.getEstado().equals("X")){%>
			<spring:message code="datosAlumno.portal.ConvCancelada"/>
	<% 	}else{
			if(estado.equals("-")) out.print("-");
			if(estado.equals("S")){
	%>
			<spring:message code="datosAlumno.portal.ConvAceptada"/>
	<%		}else if(estado.equals("N")){%>
				<spring:message code="datosAlumno.portal.ConvRechazada"/>
	<%		}else if(estado.equals("R")){%>
			<spring:message code="datosAlumno.portal.ConvRegistrada"/>
	<% 		}
		}			
	}
%>	 
			</font></td>
		</tr>
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
<%	} %>
</div>
</body>