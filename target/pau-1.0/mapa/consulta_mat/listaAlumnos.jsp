<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String facultad		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
	String cursoId 		= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
	String cursoNombre	= (String)request.getAttribute("cursoNombre");

	List<AlumnoCurso> lisAlumnos 			= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaTipos		= (HashMap<String,String>)request.getAttribute("mapaTipos");
	HashMap<String,String> mapaMaestros		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
%>
<div class="container-fluid">
	<h3><strong>Students registered in the subject:</strong> <small class="text-muted fs-5"><%=cursoNombre%></small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?facultad=<%=facultad%>&planId=<%=planId%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
	<tr>
		<th>#</th>
		<th>ID</th>
		<th>Student</th>
		<th>Record</th>
		<th>Professor</th>
		<th class="text-end">Ordinary</th>
		<th>Date</th>
		<th class="text-end">Extra</th>
		<th>Date</th>
		<th>Type</th>
	</tr>
	</thead>
	<tbody>
<% 		
	int row = 0;
	for(AlumnoCurso alumno : lisAlumnos){
		
		row++;
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String tipoNombre = "-";
		if (mapaTipos.containsKey(alumno.getTipoCalId())){
			if (alumno.getTipoCalId().equals("2") || alumno.getTipoCalId().equals("4")){
				tipoNombre = "<span class='badge bg-danger rounded-pill'>"+mapaTipos.get(alumno.getTipoCalId())+"</span>";
			}else if (alumno.getTipoCalId().equals("I")){
				tipoNombre = "<span class='badge bg-info rounded-pill'>"+mapaTipos.get(alumno.getTipoCalId())+"</span>";
			}else if (alumno.getTipoCalId().equals("1")){
				tipoNombre = "<span class='badge bg-dark rounded-pill'>"+mapaTipos.get(alumno.getTipoCalId())+"</span>";
			}else if (alumno.getTipoCalId().equals("3") || alumno.getTipoCalId().equals("5") || alumno.getTipoCalId().equals("6")){
				tipoNombre = "<span class='badge bg-warning rounded-pill'>"+mapaTipos.get(alumno.getTipoCalId())+"</span>";
			}else{
				tipoNombre = "<span class='badge bg-secondary rounded-pill'>"+mapaTipos.get(alumno.getTipoCalId())+"</span>";
			}			
		}
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(alumno.getMaestro())){
			maestroNombre = mapaMaestros.get(alumno.getMaestro());
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=alumno.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>
			<td><%=alumno.getCursoCargaId()%></td>
			<td><%=maestroNombre%></td>
			<td class="text-end"><%=alumno.getNota()%></td>
			<td><%=alumno.getFEvaluacion()==null?"":alumno.getFEvaluacion()%></td>
			<td class="text-end"><%=alumno.getNotaExtra()%></td>
			<td><%=alumno.getNotaExtra().equals("0")?"":alumno.getFExtra()%></td>
			<td><%=tipoNombre%></td>
		</tr>	
<% }%>
	</tbody>
	</table>
</div>
