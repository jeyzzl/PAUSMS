<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%	
	String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String bloques 		= (String) session.getAttribute("bloques");
	
	List<CargaAlumno> listaConfirmar 			= (List<CargaAlumno>) request.getAttribute("listaConfirmar");
	HashMap<String, String> mapaNombreAlumno	= (HashMap<String, String>) request.getAttribute("mapaNombreAlumno");
	HashMap<String, String> mapaPlanes			= (HashMap<String, String>) request.getAttribute("mapaPlanes");
	HashMap<String,AlumAcademico> mapaEnCarga	= (HashMap<String, AlumAcademico>) request.getAttribute("mapaEnCarga");
%>
<body>
<div class="container-fluid">
	<h2>Students with Confirmed Loads</h2>
	<form name="frmProceso" action="listado" method="post">	
		<div class="alert alert-info">
			<a href="listado?CargaId=<%=cargaId%>&Bloques=<%=bloques%>" class="btn btn-primary">Return</a>
		</div>	
	</form>
	<table style="width:70%" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>
			<th>Plan</th>
			<th>Plan Name</th>
			<th>Boarding</th>	
			<th>Location</th>			
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno confirmar : listaConfirmar){
		row++;
		
		String nombreAlumno="";
		if (mapaNombreAlumno.containsKey(confirmar.getCodigoPersonal())){
			nombreAlumno = mapaNombreAlumno.get(confirmar.getCodigoPersonal());
		} 
		String nombrePlan="";
		if (mapaPlanes.containsKey(confirmar.getPlanId())){
			nombrePlan = mapaPlanes.get(confirmar.getPlanId());
		}
		String tipoResidencia 	= "-";
		String tipoRequerido 	= "";
		if(mapaEnCarga.containsKey(confirmar.getCodigoPersonal())){
			tipoResidencia 	= mapaEnCarga.get(confirmar.getCodigoPersonal()).getResidenciaId();
			tipoRequerido 	= mapaEnCarga.get(confirmar.getCodigoPersonal()).getRequerido();
			if(tipoResidencia.equals("I")){
				tipoRequerido = tipoRequerido.equals("S")?"(Permanente)":"(Temporal)";
			}else{
				tipoRequerido = "";
			}
		}	
		
		String asistencia = "Online";
		if(confirmar.getModo().equals("P")){
			asistencia = "Face to Face";
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=confirmar.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>			
			<td><%=confirmar.getPlanId()%></td>		
			<td><%=nombrePlan%></td>			
			<td><%=tipoResidencia.equals("E")?"Day Student":"Boarding"%><!-- <%=tipoRequerido%> --></td>
			<td><%=asistencia%></td>
		</tr>
<% 		}%>	
	</tbody>
	</table>	
</div>
</body>
</html>
