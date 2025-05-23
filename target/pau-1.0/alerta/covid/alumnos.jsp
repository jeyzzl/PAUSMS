<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.covid.spring.Covid"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>


<body>
<%	
	List<Covid> lista 			= (List<Covid>) request.getAttribute("lista");

	HashMap<String, AlumPersonal> mapaAlumnos 		= (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaPlanesAlumnos 		= (HashMap<String, String>) request.getAttribute("mapaPlanesAlumnos");
	HashMap<String, MapaPlan> mapaPlanes 			= (HashMap<String, MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, CatFacultad> mapaFacultades 	= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras	 	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,AlumAcademico> mapaAcademico 	= (HashMap<String,AlumAcademico>) request.getAttribute("mapaAcademico");
	
	int cont = 1;
%>
<html>
<div class="container-fluid">
	<h2>Reporte alumnos</h2>
	<div class="alert alert-info">
		<a type="button" class="btn btn-primary" href="reporte">Regresar</a>
	</div>
	<table class="table">
	<thead>
	<tr>
		<th>#</th>
		<th>Matrícula</th>
		<th>Alumno</th>
		<th>Fecha</th>
		<th>Facultad</th>
		<th>Carrera</th>
		<th>Genero</th>
		<th>Edad</th>		
		<th>Residencia</th>		
		<th>Dormitorio</th>		
		<th>HTA</th>
		<th>DM</th>
		<th>Corazón</th>
		<th>Pulmón</th>
		<th>Cancer</th>
		<th>Obe.</th>
		<th>Estres</th>
		<th>Depre.</th>
		<th>IMSS</th>
		<th>PASE</th>
		<th>ISSSTE</th>
		<th>HLC</th>
		<th>Privado</th>
		<th>Otro</th>
		<th>Ninguno</th>
	</tr>
	</thead>
	<tbody>
<% 				
	for(Covid datos : lista){
		AlumPersonal alumno = new AlumPersonal();		
		if(mapaAlumnos.containsKey(datos.getCodigoPersonal())){
			alumno = mapaAlumnos.get(datos.getCodigoPersonal());
			
			String planId 			= "-";
			String carreraId		= "-";
			String facultadId		= "-";
			String carreraNombre	= "-";
			String facultadNombre	= "-";
			String interno			= "-";
			String dormitorio		= "-";
			if (mapaPlanesAlumnos.containsKey(datos.getCodigoPersonal())){
				planId = mapaPlanesAlumnos.get(datos.getCodigoPersonal());
				if (mapaPlanes.containsKey(planId)){
					carreraId = mapaPlanes.get(planId).getCarreraId();
					if (mapaCarreras.containsKey(carreraId)){
						facultadId = mapaCarreras.get(carreraId).getFacultadId();
						carreraNombre = mapaCarreras.get(carreraId).getNombreCarrera();
					}
					if (mapaFacultades.containsKey(facultadId)){
						facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad(); 
					}
				}
				
				if(mapaAcademico.containsKey(datos.getCodigoPersonal())){
					interno = mapaAcademico.get(datos.getCodigoPersonal()).getResidenciaId().equals("E") ? "Externo" : "Interno";
					if(interno.equals("Interno")){
						dormitorio = mapaAcademico.get(datos.getCodigoPersonal()).getDormitorio();
					}
				}
			}
%>
	<tr>
		<td><%=cont++%></td>
		<td><%=alumno.getCodigoPersonal()%></td>
		<td><%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+" "+alumno.getNombre()%></td>
		<td><%=datos.getFecha()%></td>
		<td><%=facultadNombre%></td>
		<td><%=carreraNombre%></td>
		<td><%=alumno.getSexo()%></td>
		<td><%=aca.util.Fecha.edad(alumno.getFNacimiento(), aca.util.Fecha.getHoy())%></td>		
		<td><%=interno%></td>
		<td><%=dormitorio%></td>
		<td><%=datos.getHipertension()%></td>
		<td><%=datos.getDiabetes()%></td>
		<td><%=datos.getCorazon()%></td>
		<td><%=datos.getPulmon()%></td>
		<td><%=datos.getCancer()%></td>
		<td><%=datos.getObesidad()%></td>
		<td><%=datos.getEstres()%></td>
		<td><%=datos.getDepresion()%></td>
		<td><%=datos.getImss()%></td>
		<td><%=datos.getPase()%></td>
		<td><%=datos.getIsste()%></td>
		<td><%=datos.getHlc()%></td>
		<td><%=datos.getPrivado()%></td>
		<td><%=datos.getOtro()%></td>
		<td><%=datos.getNinguno()%></td>
	</tr>
<% 			
		}
	}
%>
	</tbody>
	</table>
</div>
</html>
</body>