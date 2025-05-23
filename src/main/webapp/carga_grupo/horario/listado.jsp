<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatHorario"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<html>
<body>
<%	
	String empleado 	= (String) session.getAttribute("codigoPersonal");
	Acceso acceso 		= (Acceso) request.getAttribute("acceso");
	
	List<CatHorario> lisHorarios 				= (List<CatHorario>) request.getAttribute("lisHorarios");
	HashMap<String, CatFacultad> mapaFacultades	= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, String> mapaHorarios		= (HashMap<String, String>) request.getAttribute("mapaHorarios");
	HashMap<String,String> mapaUsers			= (HashMap<String,String>) request.getAttribute("mapaUsers");
%>
<div class="container-fluid">
	<h2>Timetables</h2>
	<form name="frmHorario" action="listado" method="post">	
	<div class="alert alert-info">
		<%
			if (empleado.equals("9800308")){
		%>
			<a href="editar" class="btn btn-primary" align="left"><i class="fas fa-plus"></i> <spring:message code='aca.Anadir'/> Timetable</a>
		<%	} %>
		
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Op.</th>
		<th>Timetable ID</th>
		<th>Description</th>
		<th><spring:message code="aca.Facultad"/></th>
		<th><spring:message code="aca.Status"/></th>
		<th>Users</th>
	</tr>
	</thead>	
<%	
	int row = 0;
	for(CatHorario horario: lisHorarios ){
		row++;
		String estado = "";
		if(horario.getEstado().equals("A")){ estado = "Active"; }else{ estado = "Inactive";	}
		
		String facultad = "-";
		if (mapaFacultades.containsKey(horario.getFacultadId())){
			facultad = mapaFacultades.get(horario.getFacultadId()).getNombreFacultad();
		}
		if(horario.getFacultadId().equals("000")){
			facultad = "All";
		}
		
		String totPeriodos = "0";
		if (mapaHorarios.containsKey(horario.getHorarioId())){
			totPeriodos = mapaHorarios.get(horario.getHorarioId());
		}
		String numEmpleados = "0";
		if(mapaUsers.containsKey(horario.getHorarioId())){
			numEmpleados = mapaUsers.get(horario.getHorarioId());
		}
%>
	<tr>
		<td><%=horario.getHorarioId()%></td>
		<td>
			<a href="editar?HorarioId=<%=horario.getHorarioId()%>"><i class="fas fa-edit"></i></a>
	<%	if (totPeriodos.equals("0")){	%>
			<a href="javascript:Borrar('<%=horario.getHorarioId()%>');"><i class="fas fa-trash-alt"></i></a>
	<%	} %>		
		</td>
		<td><%=horario.getHorarioId() %></td>
		<td><%=horario.getDescripcion()%></td>
		<td><a href="altaHorario?horarioId=<%=horario.getHorarioId()%>&facultadId=<%=horario.getFacultadId()%>"> <%=facultad%> </a></td>
		<td><%=estado %></td>
		<td><a href="nuevoAcceso?HorarioId=<%=horario.getHorarioId()%>" class="btn btn-primary"><%=numEmpleados%> &nbsp;<i class="fas fa-user"></i></a></td>
	</tr>
<%		
	}
%>
	</table>	
</div>
</body>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>
<script>
	function Borrar(horarioId, periodo) {
		if (confirm("Are you sure you want to eliminate this schedule?") == true) {
			document.location.href = "borrar?HorarioId="+horarioId;
		}
	}
	$('#buscar').search();
</script>
</html>