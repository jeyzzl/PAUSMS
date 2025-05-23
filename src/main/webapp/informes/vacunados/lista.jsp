<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumUbicacion" %>
<%@ page import="aca.alumno.spring.AlumPersonal" %>
<%@ page import="aca.plan.spring.MapaPlan" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
 	List<AlumUbicacion> lisVacunados 			= (List<AlumUbicacion>)request.getAttribute("lisVacunados");
	HashMap<String,AlumPersonal> mapaAlumnos 	= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaPlanAlumno 		= (HashMap<String,String>)request.getAttribute("mapaPlanAlumno");
	HashMap<String,MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");
%>
<style>
	th{
		cursor:pointer;
	}
</style>
<body>
<div class="container-fluid">
	<h2>Lista de vacunados</h2>
	<hr>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
	<tr>
		<th>#</th>
		<th>Código</th>
		<th>Alumno</th>
		<th>Plan</th>
		<th>Carrera</th>
		<th>Vacunas</th>
	</tr>
	</thead>
	<tbody>
<%	int row =0;
	for (AlumUbicacion alumUbicacion :lisVacunados){
		row++;
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(alumUbicacion.getCodigoPersonal())){			
			alumnoNombre = mapaAlumnos.get(alumUbicacion.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(alumUbicacion.getCodigoPersonal()).getApellidoPaterno()+" "+ mapaAlumnos.get(alumUbicacion.getCodigoPersonal()).getApellidoMaterno();
		}
		
		String alumnoPlan = "-";
		if (mapaPlanAlumno.containsKey(alumUbicacion.getCodigoPersonal())){			
			alumnoPlan = mapaPlanAlumno.get(alumUbicacion.getCodigoPersonal());
		}
		
		String planNombre = "-";
		if (mapaPlanes.containsKey(alumnoPlan)){			
			planNombre = mapaPlanes.get(alumnoPlan).getCarreraSe();
		}
%>
	<tr>
		<td><%=row%></td>
		<td><%=alumUbicacion.getCodigoPersonal()%></td>
		<td><%=alumnoNombre%></td>
		<td><%=alumnoPlan%></td>
		<td><%=planNombre%></td>
		<td><%=alumUbicacion.getDescripcionVacuna()%></td>
	</tr>
<%	} %>		
	</tbody>	
	</table>
</div>
</body>