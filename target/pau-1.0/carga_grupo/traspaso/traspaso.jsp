<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<head>
<script type="text/javascript">
	function Refrescar(){		
		document.form.submit();
	}
</script>
</head>
<%

	int row = 0;
	String cargaId 									= (String)request.getAttribute("cargaId");
	
	List<Carga> lisCargas							= (List<Carga>) request.getAttribute("lisCargas");
	List<AlumnoCurso> lisAlumnos					= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");
	HashMap<String, MapaCurso> mapaCursos			= (HashMap<String, MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String, AlumPersonal> mapaAlumnos		= (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String, AlumPlan> mapaPlan				= (HashMap<String, AlumPlan>)request.getAttribute("mapaPlan");
	HashMap<String, AlumEstado> mapaEstado			= (HashMap<String, AlumEstado>)request.getAttribute("mapaEstado");
	HashMap<String, CargaAlumno> mapaCargas			= (HashMap<String, CargaAlumno>)request.getAttribute("mapaCargas");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, String> mapTipoCal				= (HashMap<String, String>)request.getAttribute("mapTipoCal");
	HashMap<String, String> mapaNombrePlan			= (HashMap<String, String>)request.getAttribute("mapaNombrePlan");
			
%>

<div class="container-fluid">
	<h2>Subject Extraction</h2>
<!-- SELECT DE CARGAS -->
	<form name="form" action="traspaso" method="post">
		<div class="alert alert-info d-flex align-items-center">
			<spring:message code="aca.Carga"/>:&nbsp;
			<select name="CargaId" onchange='javascript:Refrescar()' class="form-select" style="width:350px">
<%
	for (Carga carga: lisCargas ){
%>
			<option <%=cargaId.equals(carga.getCargaId())?" Selected ":""%> value="<%=carga.getCargaId()%>"><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
<%
	}
%>
			</select>			
		</div>
	</form>
	
	<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<td>No.</td>
				<td>Student ID</td>
				<td>Name</td>
				<td>Last Name</td>
				<td>Program</td>
				<td>Course</td>
				<td>Year</td>
				<td>Status</td>
				<td>Subject ID</td>
				<td>Subject Key</td>
				<td>Subject Name</td>
			</tr>
		</thead>
		<tbody>
<%
		for(AlumnoCurso alumno : lisAlumnos) {
			row++;
			String nombre = "-";
			String apellido = "-";
			if (mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
				AlumPersonal personal = mapaAlumnos.get(alumno.getCodigoPersonal());
				nombre = personal.getNombre();
				apellido = personal.getApellidoPaterno()+" "+personal.getApellidoMaterno();
			}
	
			String facultadNombre	= "";
			String facultadId		= "";
			if (mapaCarreras.containsKey(alumno.getCarreraId())){
				facultadId = mapaCarreras.get(alumno.getCarreraId()).getFacultadId();
				 if (mapaFacultades.containsKey(facultadId)){
					 facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
				 }			   
			}
			
			String grado = "";
			if(mapaPlan.containsKey(alumno.getCodigoPersonal()+alumno.getPlanId())){
				grado = mapaPlan.get(alumno.getCodigoPersonal()+alumno.getPlanId()).getGrado();
			}
			
			String estado = "";
			if(mapaEstado.containsKey(alumno.getCodigoPersonal())){
				estado = mapaEstado.get(alumno.getCodigoPersonal()).getEstado();
			}
			
			String nombrePlan		= "";
			if(mapaNombrePlan.containsKey(alumno.getPlanId())){
				nombrePlan = mapaNombrePlan.get(alumno.getPlanId());
			}
			
			String claveMateria = "";
			if(mapaCursos.containsKey(alumno.getCursoId())){
				claveMateria = mapaCursos.get(alumno.getCursoId()).getCursoClave();
			}
			
%>
			<tr>
				<td><%=row %></td>
				<td><%=alumno.getCodigoPersonal() %></td>
				<td><%=nombre %></td>
				<td><%=apellido %></td>
				<td><%=facultadNombre%></td>
				<td><%=nombrePlan%></td>
				<td><%=grado%></td>
				<td><%=estado.equals("I")?"Enrolled":"Assigned"%></td>
				<td><%=alumno.getCursoId() %></td>
				<td><%=claveMateria%></td>
				<td><%=alumno.getNombreCurso() %></td>
			<tr>
<%
		}
%>
		</tbody>
	</table>
</div>