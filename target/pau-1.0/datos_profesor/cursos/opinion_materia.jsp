<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	
	String codigoPersonal			= (String) session.getAttribute("codigoEmpleado");
	String periodoId				= request.getParameter("periodoId");
	String cursoCargaId				= request.getParameter("cursoCargaId");
	
	String nombreCurso				= (String) request.getAttribute("nombreCurso");	
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
%>
<body>
<div class="container-fluid">
	<h3>Evaluaci&oacute;n Estudiantil <small class="text-muted fs-5">( <%=nombreMaestro%> -  <%=nombreCurso%> )</small></h3>
	<div class="alert alert-info">
		<a href="cursos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
<%
	if(lisPreg.size() > 0){ 
%>
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th>Item</th>			
			<th>Min</th>
			<th>Max</th>
			<th>Media</th>
		</tr>
	</thead>
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
		<tr>
			<td><%=row+1 %></td>
			<td><%=edoAlumnoPreg.getPregunta() %></td>			
			<td align="text-right"><%=mapaMinimo.get(edoAlumnoPreg.getPreguntaId()) %></td>
			<td class="text-right"><%=mapaMaximo.get(edoAlumnoPreg.getPreguntaId()) %></td>
			<td align="text-right"><%=promedioPregunta %></td>
		</tr>
<%
		row++;
		}
%>
		<tr class="th2">
			<th colspan="4" align="right"><spring:message code="aca.Total"/></th>			
			<th class="text-right"><%=formato.format(sumaPromedios/lisPreg.size()) %></th>
		</tr>		
	</table>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>N° Alumnos</th>
			<th>Participaron</th>
			<th>Faltaron</th>
		</tr>
	</thead>
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