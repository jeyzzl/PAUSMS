<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpHoras"%>
<%@page import="aca.emp.spring.EmpConfirmar"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoEmpleado 		= (String)request.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String)request.getAttribute("nombreEmpleado");
	String yearActual			= aca.util.Fecha.getHoy().substring(6,10);
	String year 				= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
	
	List<EmpHoras>	lisHoras 						= (List<EmpHoras>)request.getAttribute("lisHoras");
	HashMap<String,EmpConfirmar> mapaConfirmados	= (HashMap<String,EmpConfirmar>)request.getAttribute("mapaConfirmados"); 
	HashMap<String,String> mapaCursos				= (HashMap<String,String>)request.getAttribute("mapaCursos");
	
	int row = 1;
%>
<head>	
</head>
<body>
<div class="container-fluid">
	<h2>Materias <small class="text-muted fs-5">(<%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="lista?Year=<%=year%>">Regresar</a>
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead>
		<tr class="table-info">
			<th>N°</th>
			<th>Contrato</th>
			<th>Materia</th>
			<th>Otra</th>
			<th>Inicio</th>
			<th>Final</th>
			<th>Estado</th>		
			<th>Confirmado</th>
		</tr>
	</thead>
	<tbody>
<%	
	for(EmpHoras curso : lisHoras){
		String estado = "Solicitud";
		if (curso.getEstado().equals("A")) estado = "Autorizado VRA";
		if (curso.getEstado().equals("N")) estado = "Nómina";		
		if (curso.getEstado().equals("F")) estado = "Firmado";
		if (curso.getEstado().equals("P")) estado = "Pagado";
		
		String confirmado = "NO";
		if (mapaConfirmados.containsKey(curso.getCodigoPersonal()+curso.getFolio())){
			confirmado = mapaConfirmados.get(curso.getCodigoPersonal()+curso.getFolio()).getUsuario()+" - "+mapaConfirmados.get(curso.getCodigoPersonal()+curso.getFolio()).getFecha();
		}
		String materia = curso.getCursoId();
		if (mapaCursos.containsKey(curso.getCursoId())){
			materia = mapaCursos.get(curso.getCursoId());
		}
%>
	<tr>
		<td><%=row++%></td>
		<td><%=curso.getContratoId().equals("0")?"<span class='badge bg-warning'>"+curso.getContratoId()+"</span>":"<span class='badge bg-dark'>"+curso.getContratoId()+"</span>"%></td>
		<td><%=materia%></td>
		<td><%=curso.getMateria()%></td>
		<td><%=curso.getFechaIni()%></td>		
		<td><%=curso.getFechaFin()%></td>
		<td><%=estado%></td>
		<td><%=confirmado%></td>
	</tr>
<%	}	%>
	</tbody>
	</table>
</div>
</body>