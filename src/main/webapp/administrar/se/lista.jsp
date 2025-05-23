<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.sep.spring.SepAlumno"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	List<SepAlumno> lisAlumnos 	= (List<SepAlumno>) request.getAttribute("lisAlumnos");

	HashMap<String, CatPais> mapaPaises 		= (HashMap<String, CatPais>) request.getAttribute("mapaPaises");
	HashMap<String, CatEstado> mapaEstados 		= (HashMap<String, CatEstado>) request.getAttribute("mapaEstados");
%>
<div class="container-fluid">
	<h2>Listado de alumnos</h2>
	<div class="alert alert-info d-flex align-items-center">	
		<a href="estadistica" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-sm table-bordered">
	<thead>
	<tr>
		<th width="5%">No.</th>
		<th width="5%">Matrícula</th>
		<th width="15%">Nombre</th>
		<th width="10%">Ap. Paterno</th>
		<th width="10%">Ap. Materno</th>
		<th width="5%">Genero</th>
		<th width="10%">CURP</th>
		<th width="5%">Plantel</th>
		<th width="5%">PlanUM</th>
		<th width="5%">PlanSE</th>
		<th width="5%">Grado</th>
		<th width="5%">Ciclo</th>
		<th width="5%">Edad</th>
		<th width="5%">Pa&iacute;s</th>
		<th width="5%">Estado</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(SepAlumno alumno : lisAlumnos){
		row++;
		
		String nombrePais = "-";
		if(mapaPaises.containsKey(alumno.getPaisId())){
			nombrePais = mapaPaises.get(alumno.getPaisId()).getNombrePais();
		}
		
		String nombreEstado = "-";
		if(mapaEstados.containsKey(alumno.getEstadoId())){
			nombreEstado = mapaEstados.get(alumno.getEstadoId()).getNombreEstado();
		}
%>
	<tr>
		<td><%=row%></td>
		<td><%=alumno.getCodigoPersonal()%></td>
		<td><%=alumno.getNombre()%></td>
		<td><%=alumno.getPaterno()%></td>
		<td><%=alumno.getMaterno()%></td>
		<td><%=alumno.getGenero() %></td>
		<td><%=alumno.getCurp()%></td>
		<td><%=alumno.getPlantel()%></td>
		<td><%=alumno.getPlanUm()%></td>
		<td><%=alumno.getPlanSep()%></td>
		<td><%=alumno.getGrado()%></td>
		<td><%=alumno.getCiclo()%></td>
		<td><%=alumno.getEdad()%></td>
		<td><%=nombrePais%></td>
		<td><%=nombreEstado%></td>
	</tr>
<%	}%>
	</tbody>
	</table>
</div>