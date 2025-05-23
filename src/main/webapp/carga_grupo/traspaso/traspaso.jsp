<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatAcomodo"%>

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
	String planId 									= (String)request.getAttribute("planId");
	String ciclo 									= (String)request.getAttribute("ciclo");
	
	List<Carga> lisCargas							= (List<Carga>) request.getAttribute("lisCargas");
	List<MapaPlan> lisPlanes 						= (List<MapaPlan>) request.getAttribute("lisPlanes");
	List<AlumnoCurso> lisAlumnos					= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");
	HashMap<String, MapaCurso> mapaCursos			= (HashMap<String, MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String, AlumPersonal> mapaAlumnos		= (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String, AlumPlan> mapaPlan				= (HashMap<String, AlumPlan>)request.getAttribute("mapaPlan");
	HashMap<String, AlumEstado> mapaEstado			= (HashMap<String, AlumEstado>)request.getAttribute("mapaEstado");
	HashMap<String, AlumAcademico> mapaAcademico	= (HashMap<String, AlumAcademico>)request.getAttribute("mapaAcademico");
	HashMap<String, CargaAlumno> mapaCargas			= (HashMap<String, CargaAlumno>)request.getAttribute("mapaCargas");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, String> mapTipoCal				= (HashMap<String, String>)request.getAttribute("mapTipoCal");
	HashMap<String, String> mapaNombrePlan			= (HashMap<String, String>)request.getAttribute("mapaNombrePlan");
	HashMap<String, CatAcomodo> mapaAcomodo			= (HashMap<String, CatAcomodo>)request.getAttribute("mapaAcomodo");
			
%>

<div class="container-fluid">
	<h2>Subject Extraction</h2>
<!-- SELECT DE CARGAS -->
	<form name="form" action="traspaso" method="post">
		<div class="alert alert-info d-flex align-items-center">
			<spring:message code="aca.Carga"/>:&nbsp;
			<select name="CargaId" onchange='javascript:Refrescar()' class="form-select me-2" style="width:350px">
<%
	for (Carga carga: lisCargas ){
%>
			<option <%=cargaId.equals(carga.getCargaId())?" Selected ":""%> value="<%=carga.getCargaId()%>"><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
<%
	}
%>
			</select>
			<spring:message code="aca.Curso"/>:&nbsp;
			<select name="PlanId" onchange='javascript:Refrescar()' class="form-select me-2" style="width:30rem">
				<option <%=planId.equals("-")?" Selected ":""%> value="-">All</option>
<%
	for (MapaPlan plan: lisPlanes ){
%>
				<option <%=planId.equals(plan.getPlanId())?" Selected ":""%> value="<%=plan.getPlanId()%>"><%=plan.getPlanId()%>-<%=plan.getNombrePlan()%></option>
<%
	}
%>
			</select>	
			Year:&nbsp;
			<select name="Ciclo" onchange='javascript:Refrescar()' class="form-select me-2" style="width:10rem">
				<option <%=ciclo.equals("0")?" Selected ":""%> value="0">All</option>
				<option <%=ciclo.equals("1")?" Selected ":""%> value="1">1</option>
				<option <%=ciclo.equals("2")?" Selected ":""%> value="2">2</option>
				<option <%=ciclo.equals("3")?" Selected ":""%> value="3">3</option>
				<option <%=ciclo.equals("4")?" Selected ":""%> value="4">4</option>
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
				<td>Residence</td>
				<td>Accommodation</td>
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

			String residencia = "";
			AlumAcademico academico = new AlumAcademico();
			if(mapaAcademico.containsKey(alumno.getCodigoPersonal())){
				academico = mapaAcademico.get(alumno.getCodigoPersonal());
				residencia = academico.getResidenciaId();
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

			String acomodo = "";
			if(mapaAcomodo.containsKey(academico.getAcomodoId())){
				acomodo = mapaAcomodo.get(academico.getAcomodoId()).getNombreCorto();
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
				<td><%=residencia.equals("I")?"Boarding":"Day Student"%></td>
				<td><%=acomodo%></td>
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