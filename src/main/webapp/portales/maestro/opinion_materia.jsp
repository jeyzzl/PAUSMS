<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	
	String codigoPersonal			= (String) session.getAttribute("codigoEmpleado");
	String periodoId				= request.getParameter("periodoId");
	String cursoCargaId				= request.getParameter("cursoCargaId");
	String nombreCurso				= request.getParameter("nombreCurso");
	
	String nombreMaestro			= (String) request.getAttribute("nombreMaestro");
	String participaron				= (String) request.getAttribute("participaron");
	String faltaron					= (String) request.getAttribute("faltaron");
	float sumaPromedios				= 0f;
	int numPreg 					= 0;
	
	List<EdoAlumnoPreg> lisPreg						= (List<EdoAlumnoPreg>) request.getAttribute("lisPreg");
	HashMap <String, String> mapaPromedioRespuestas = (HashMap <String, String>) request.getAttribute("mapaPromedioRespuestas");
	HashMap <String, String> mapaMinimo 			= (HashMap <String, String>) request.getAttribute("mapaMinimo");
	HashMap <String, String> mapaMaximo				= (HashMap <String, String>) request.getAttribute("mapaMaximo");
			
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
	String sColor 		= "bgcolor = '#eeeeee'";
%>
<body>
<div class="container-fluid">
	<h3><spring:message code="portal.maestro.opinionMateria.EvaluacionEstudiantil"/> <small class="text-muted">( <%=nombreMaestro%> -  <%=nombreCurso%> )</small></h3>
	<div class="alert alert-info">
		<a href="cursos" class="btn btn-primary"><spring:message code="aca.Regresar"/></a> 
	</div>	
	<table class="table table-sm" style="width:80%">
<%
	if(lisPreg.size() > 0){ 
%>
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="portal.maestro.opinionMateria.Item"/></th>			
			<th><spring:message code="portal.maestro.opinionMateria.Min"/></th>
			<th><spring:message code="portal.maestro.opinionMateria.Max"/></th>
			<th><spring:message code="portal.maestro.opinionMateria.Media"/></th>
		</tr>
<%	
		int row = 0;
		String promedioPregunta = "";		  
		for(EdoAlumnoPreg edoAlumnoPreg : lisPreg){			
			// Verifica si es pregunta de opcion única para calcular el promedio.
			if (edoAlumnoPreg.getTipo().equals("O")){
				++numPreg;			
				promedioPregunta = mapaPromedioRespuestas.get(edoAlumnoPreg.getPreguntaId());
				sumaPromedios += Float.parseFloat(promedioPregunta);
			}
%>
		<tr <%=(row%2)!=0?sColor:"" %>>
			<td><%=row+1 %></td>
			<td><%=edoAlumnoPreg.getPregunta() %></td>			
			<td align="center"><%=mapaMinimo.get(edoAlumnoPreg.getPreguntaId()) %></td>
			<td align="center"><%=mapaMaximo.get(edoAlumnoPreg.getPreguntaId()) %></td>
			<td align="right"><%=promedioPregunta %></td>
		</tr>
<%
		row++;
		}
%>
		<tr class="th2">
			<th colspan="2" align="right"><spring:message code="aca.Total"/></th>			
			<th colspan="2">&nbsp;</th>
			<th align="right"><%=formato.format(sumaPromedios/lisPreg.size()) %></th>
		</tr>
		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>
	</table>
	<table class="table table-sm" style="border: dotted 1px gray; width:80%">
		<tr>
			<th><spring:message code="portal.maestro.opinionMateria.NAlumnos"/></th>
			<th><spring:message code="portal.maestro.opinionMateria.Participaron"/></th>
			<th><spring:message code="portal.maestro.opinionMateria.Faltaron"/></th>
		</tr>
		<tr>
			<td align="center"><%=Integer.parseInt(participaron)+Integer.parseInt(faltaron) %></td>
			<td align="center"><%=participaron %></td>
			<td align="center"><%=faltaron %></td>
		</tr>
	</table>
<%
	}
%>
</div>
</body>