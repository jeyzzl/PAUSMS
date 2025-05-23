<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.vista.spring.Inscritos" %>
<%@ page import="aca.catalogo.spring.CatPais" %>
<%@ page import="aca.catalogo.spring.CatEstado" %>
<%@ page import="aca.catalogo.spring.CatFacultad" %>
<%@ page import="aca.catalogo.spring.CatCarrera" %>
<%@ page import="aca.catalogo.spring.CatModalidad" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	int numeroOrden		= request.getParameter("Ordenar")==null ? 0 : Integer.parseInt(request.getParameter("Ordenar"));

	List<Inscritos> lisAlumnos 	= (List<Inscritos>) request.getAttribute("lisAlumnos");
	
	HashMap<String, CatFacultad> mapaFacultades 	= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras 		= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, CatPais> mapaPaises 			= (HashMap<String, CatPais>)request.getAttribute("mapaPaises");
	HashMap<String, CatEstado> mapaEstados 			= (HashMap<String, CatEstado>)request.getAttribute("mapaEstados");
	HashMap<String, CatModalidad> mapaModalidades 	= (HashMap<String, CatModalidad>)request.getAttribute("mapaModalidades");	
	HashMap<String, String> mapaTipos 				= (HashMap<String, String>)request.getAttribute("mapaTipos");	
%>
<style>
	th{
		cursor:pointer;
	}
</style>
<body>
<div class="container-fluid">
	<h1>Reporte de Inscritos</h1>
	<div class="alert alert-info">
		<b>Nota:</b> Para ordenar la lista por matrícula, nombre, país, estado o modalidad haga clic sobre el título correspondiente
	</div>
	<table class="table table-bordered">
<%
	String facultadTmp = "";
	String carreraTmp = "";
	int cont = 1;
	for(Inscritos inscrito : lisAlumnos){
		
		String facultadId 		= "0";
		String facultadNombre 	= "-";
		String carreraNombre 	= "-";
		if (mapaCarreras.containsKey(inscrito.getCarreraId())){
			facultadId 		= mapaCarreras.get(inscrito.getCarreraId()).getFacultadId();
			carreraNombre 	= mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
		}
		if(!facultadId.equals(facultadTmp)){
%>		
	<tr class="alert alert-success">
		<td colspan="7"><h3><%=mapaFacultades.get( facultadId).getNombreFacultad()%></h3></td>
	</tr>	
		
<%		
			facultadTmp = facultadId;
		}
		if(!inscrito.getCarreraId().equals(carreraTmp)){
			cont=1;
%>
	<thead>
	<tr>
		<td colspan="7"><h4><%=carreraNombre%></h4></td>
	</tr>
	</thead>
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th onclick="location.href='tipo?Ordenar=1'"><spring:message code="aca.Matricula"/></th>
		<th onclick="location.href='tipo?Ordenar=2'"><spring:message code="aca.Nombre"/></th>
		<th onclick="location.href='tipo?Ordenar=3'"><spring:message code="aca.Pais"/></th>
		<th onclick="location.href='tipo?Ordenar=4'"><spring:message code="aca.Estado"/></th>
		<th onclick="location.href='tipo?Ordenar=5'"><spring:message code="aca.Modalidad"/></th>
		<th onclick="location.href='tipo?Ordenar=6'"><spring:message code="aca.Tipo"/></th>
	</tr>
	</thead>
<%
			carreraTmp = inscrito.getCarreraId();
		}
		
		String pais = "";
		if(mapaPaises.get(inscrito.getPaisId())!=null)pais= mapaPaises.get(inscrito.getPaisId()).getNombrePais();
		String estado = "";
		if(mapaEstados.containsKey(inscrito.getPaisId()+inscrito.getEstadoId())) estado = mapaEstados.get(inscrito.getPaisId()+inscrito.getEstadoId()).getNombreEstado();

		String tipo = "0";
		if(mapaTipos.containsKey(inscrito.getTipoAlumnoId())) tipo = mapaTipos.get(inscrito.getTipoAlumnoId());
%>	
	<tr>
		<td><%=cont %></td>
		<td><%=inscrito.getCodigoPersonal() %></td>
		<td><%=inscrito.getNombre() + " " + inscrito.getApellidoPaterno() + " " + inscrito.getApellidoMaterno() %></td>
		<td><%=pais %></td>
		<td><%=estado %></td>
		<td><%=mapaModalidades.get( inscrito.getModalidadId()).getNombreModalidad()  %></td>
		<td><%=tipo%></td>
	</tr>
<%
		cont++;
	}
%>
	</table>
</div>
</body>