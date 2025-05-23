<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	String cargaId 		= (String) request.getAttribute("cargaId");
	String bloques 		= (String) session.getAttribute("bloques");
	
	List<CargaAlumno> listaCargaAcademica 			= (List<CargaAlumno>) request.getAttribute("listaCargaAcademica");
	HashMap<String, String> mapaNombreAlumno		= (HashMap<String, String>) request.getAttribute("mapaNombreAlumno");
	HashMap<String, String> mapaPlanes				= (HashMap<String, String>) request.getAttribute("mapaPlanes");
	HashMap<String,AlumAcademico> mapaEnCarga		= (HashMap<String, AlumAcademico>) request.getAttribute("mapaEnCarga");	
	HashMap<String,String> mapaCreditosConfirmadas	= (HashMap<String,String>) request.getAttribute("mapaCreditosConfirmadas");		
	HashMap<String,String> mapaPagares				= (HashMap<String,String>) request.getAttribute("mapaPagares");	
%>
<body>
<div class="container-fluid">
	<h2>Students in Subject Loading</h2>
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
		<th>Confirmed</th>	
		<th>Credits</th>	
<!-- 		<th>Pagares</th>			 -->
		<th>Location</th>		
	</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno carga : listaCargaAcademica){
		row++;
		
		String nombreAlumno = "-";
		if(mapaNombreAlumno.containsKey(carga.getCodigoPersonal())){
			nombreAlumno = mapaNombreAlumno.get(carga.getCodigoPersonal());
		}
		String nombrePlan = "-";
		if(mapaPlanes.containsKey(carga.getPlanId())){
			nombrePlan = mapaPlanes.get(carga.getPlanId());
		}
		String tipoResidencia 	= "-";
		String tipoRequerido 	= "";
		if(mapaEnCarga.containsKey(carga.getCodigoPersonal())){
			tipoResidencia 	= mapaEnCarga.get(carga.getCodigoPersonal()).getResidenciaId();
			tipoRequerido 	= mapaEnCarga.get(carga.getCodigoPersonal()).getRequerido();
			if(tipoResidencia.equals("I")){
				tipoRequerido = tipoRequerido.equals("S")?"(Permanente)":"(Temporal)";
			}else{
				tipoRequerido = "";
			}
		}
		
		String asistencia = "Online";
		if(carga.getModo().equals("P")){
			asistencia = "Face to Face";
		}

		String confirmadas = "<span class='badge bg-warning'>No</span>";
		if(carga.getMat().equals("S")){
			confirmadas = "<span class='badge bg-success'>Yes</span>";
		}
		
		String creditos = "0";
		if(mapaCreditosConfirmadas.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			creditos = mapaCreditosConfirmadas.get(carga.getCodigoPersonal()+carga.getBloqueId());
		}
		
		String pagares = "0";
		if(mapaPagares.containsKey(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId())){
			pagares = mapaPagares.get(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId());
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>			
			<td><%=carga.getPlanId()%></td>		
			<td><%=nombrePlan%></td>
			<td><%=tipoResidencia.equals("E")?"Day Student":"Boarding"%><!--<%=tipoRequerido%>  --></td>
			<td align="center"><%=confirmadas%></td>	
			<td align="center"><%=creditos%></td>	
<%-- 			<td align="center"><%=pagares%></td> --%>
			<td><%=asistencia%></td>
		</tr>
<% 		}%>	
	</tbody>
	</table>	
</div>
</body>
</html>
