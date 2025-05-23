<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmEvaluacion"%>
<%@page import="aca.admision.spring.AdmEvaluacionNota"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>


<html>
<head></head>
<%	
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String nombreAlumno 	= (String)request.getAttribute("nombre");	
	
	List<AdmEvaluacionNota> lisEvaluaciones 		= (List<AdmEvaluacionNota>)request.getAttribute("lisEvaluaciones");
	HashMap<String,AdmEvaluacion> mapaEvaluaciones	= (HashMap<String,AdmEvaluacion>)request.getAttribute("mapaEvaluaciones");
%>
<body>
<div class="container-fluid">
	<h2>Other registered evaluations <small class="text-muted fs-5">( <%=folio%> - <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%">#</th>
		<th width="20%">Evaluation</th>
		<th width="15%">Date</th>
		<th width="10%">Grade</th>
		<th width="50%">Comment</th>
	</tr>
	</thead>
<%	
	int row=0;
	for(AdmEvaluacionNota evaluacion : lisEvaluaciones){
		row++;
		
		String nombreEvaluacion = "";
		if (mapaEvaluaciones.containsKey(evaluacion.getEvaluacionId())) {
			nombreEvaluacion = mapaEvaluaciones.get(evaluacion.getEvaluacionId()).getEvaluacionNombre();
		}
%>	
	<tr>
		<td><%=row%></td>
		<td><%=nombreEvaluacion%></td>
		<td><%=evaluacion.getFecha() %></td>
		<td><%=evaluacion.getNota()%></td>
		<td><%=evaluacion.getComentario()%></td>
	</tr>
<% 	}%>
	</table>	
</div>
</body>
</html>