<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../css/style.css" />
<%
	String cursoId		= (String)request.getAttribute("cursoId");
	String facultad		= (String)request.getAttribute("facultad");
	String planId		= (String)request.getAttribute("planId");
	String cursoNombre	= (String)request.getAttribute("cursoNombre");

	List<AlumnoCurso> lisAlumnos 		= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaAlumnos	= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
	<h3><strong>Course: <%=cursoNombre%></strong></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?facultad=<%=facultad%>&planId=<%=planId%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
		<tr>
			<th class="text-center">#</th>
			<th>ID</th>
			<th>Name</th>
			<th class="text-end">Ord. Grade</th>			
			<th>Ord. Date</th>
			<th class="text-end">Extra Grade</th>
			<th>Extra Date</th>
		</tr>
	</thead>
	<tbody>	
<% 	
	int row=0;
	for(AlumnoCurso alumno : lisAlumnos){
		row++;
		
		String name = "-";
		if (mapaAlumnos.containsKey( alumno.getCodigoPersonal())){
			name = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String FExtra = "-";
		if(alumno.getFExtra() != null){
			FExtra = alumno.getFExtra();		
		}
%>
		<tr>
			<td class="text-center"><%=row%></td>
			<td><%=alumno.getCodigoPersonal()%></td>
			<td><%=name%></td>
			<td class = "text-end"><%=alumno.getNota()%></td>			
			<td><%=alumno.getFEvaluacion()%></td>			
			<td class = "text-end"><%=alumno.getNotaExtra()%></td>
			<td ><%=FExtra%></td>
		</tr>
<% }%>
	</tbody>
	</table>
</div>