<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>

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
%>
<body>
<div class="container-fluid">
	<h2>Listado de Alumnos en Practicas</h2>
	<form name="frmProceso" action="listado" method="post">	
		<div class="alert alert-info">
			<a href="listado?CargaId=<%=cargaId%>&Bloques=<%=bloques%>" class="btn btn-primary">Regresar</a>
		</div>	
	</form>
	<table style="width:70%" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th>#</th>
			<th>Matricula</th>
			<th>Nombre Alumno</th>
			<th>Plan</th>
			<th>Nombre Plan</th>	
			<th>Internado</th>	
			<th>Crd.</th>
			<th>HT</th>
			<th>HP</th>		
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	int totalCreditos =0;
	int totalHt	=0;
	int totalHp =0;
	
	for(AlumnoCurso alumno : lisMaterias){
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
				tipoRequerido = tipoRequerido.equals("S")?"(Permanente)":"(Temporal)";
			}else{
				tipoRequerido = "";
			}
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
			<td><%=alumno.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>			
			<td><%=alumno.getPlanId()%></td>		
			<td><%=nombrePlan%></td>
			<td><%=tipoResidencia.equals("E")?"Externo":"Interno"%><%=tipoRequerido%></td>
			<td><%=alumno.getCreditos() %></td>
			<td><%=alumno.getHt() %></td>
			<td><%=alumno.getHp() %></td>
		</tr>
<% 		}%>	

		<tr style="background-color: #E6E6E6; ">
			<td colspan="6" class="center" style="font-weight: bold;">Total</td>
			<td style="font-weight: bold;"><%=totalCreditos%></td>	
			<td style="font-weight: bold;"><%=totalHt%></td>
			<td style="font-weight: bold;"><%=totalHp %></td>
			
		</tr>
	</tbody>
	</table>	
</div>
</body>
</html>
