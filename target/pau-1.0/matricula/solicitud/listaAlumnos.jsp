<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>
<html>
<%
	String cargaId 		= (String) request.getAttribute("cargaId");
	String bloques 		= (String) session.getAttribute("bloques");
	
	List<AlumnoCurso> lisMaterias 				= (List<AlumnoCurso>) request.getAttribute("lisMaterias");
	HashMap<String, String> mapaNombreAlumno	= (HashMap<String, String>) request.getAttribute("mapaNombreAlumno");
	HashMap<String, String> mapaPlanes			= (HashMap<String, String>) request.getAttribute("mapaPlanes");
	HashMap<String,AlumAcademico> mapaEnCarga	= (HashMap<String, AlumAcademico>) request.getAttribute("mapaEnCarga");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, CatFacultad> mapaFacultades = (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultades");
%>
<body>
<div class="container-fluid">
	<h2>Students in Practicum Subjects</h2>
	<form name="frmProceso" action="listado" method="post">	
		<div class="alert alert-info">
			<a href="listado?CargaId=<%=cargaId%>&Bloques=<%=bloques%>" class="btn btn-primary">Return</a>
		</div>	
	</form>
	<table style="width:70%" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th>#</th>
			<th>School</th>
			<th>Student ID</th>
			<th>Name</th>
			<th>Plan</th>
			<th>Plan Name</th>	
			<th>Residence</th>					
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	int totalCreditos =0;
	int totalHt	=0;
	int totalHp =0;
	String codigoOld = "0";
	for(AlumnoCurso alumno : lisMaterias){
		if (!codigoOld.equals(alumno.getCodigoPersonal())){
			codigoOld = alumno.getCodigoPersonal();
			row++;
			
			String nombreAlumno="";
			if (mapaNombreAlumno.containsKey(alumno.getCodigoPersonal())){
				nombreAlumno = mapaNombreAlumno.get(alumno.getCodigoPersonal());
			} 
			String nombrePlan="";
			if (mapaPlanes.containsKey(alumno.getPlanId())){
				nombrePlan = mapaPlanes.get(alumno.getPlanId());
			}
			
			String tipoResidencia 	= "-";
			String tipoRequerido 	= "";
			if(mapaEnCarga.containsKey(alumno.getCodigoPersonal())){
				tipoResidencia 	= mapaEnCarga.get(alumno.getCodigoPersonal()).getResidenciaId();
				tipoRequerido 	= mapaEnCarga.get(alumno.getCodigoPersonal()).getRequerido();
				if(tipoResidencia.equals("I")){
					tipoRequerido = tipoRequerido.equals("S")?"(Permanent)":"(Temp)";
				}else{
					tipoRequerido = "";
				}
			}
			
			String facultadId = "0";
			if(mapaCarreras.containsKey(alumno.getCarreraId())){
				facultadId = mapaCarreras.get(alumno.getCarreraId()).getFacultadId();
			}
			
			String nombreFacultad = "-";
			if(mapaFacultades.containsKey(facultadId)){
				nombreFacultad = mapaFacultades.get(facultadId).getNombreCorto();
			}
						
			int creditos = Integer.parseInt(alumno.getCreditos());
			int ht = Integer.parseInt(alumno.getHt());	
			int hp = Integer.parseInt(alumno.getHp());
		
			totalCreditos = totalCreditos + creditos;
			totalHt		  = totalHt + ht;
			totalHp		  = totalHp + hp;
%>
		<tr>
			<td><%=row%></td>
			<td><%=nombreFacultad %></td>
			<td><%=alumno.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>			
			<td><%=alumno.getPlanId()%></td>		
			<td><%=nombrePlan%></td>
			<td><%=tipoResidencia.equals("E")?"Day Student":"Boarding Student"%><%=tipoRequerido%></td>			
		</tr>
<% 		}
	}%>		
	</tbody>
	</table>	
</div>
</body>
</html>
