<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
<%	
	String cargaId 			= (String)request.getAttribute("cargaId");
	
	List<CargaAcademica> lisMaterias 			= (List<CargaAcademica>)request.getAttribute("lisMaterias");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaEsquemas			= (HashMap<String,String>)request.getAttribute("mapaEsquemas");
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
	<h2>Missing Subjects <small class="text-muted fs-4">(<%=cargaId%>)</small></h2>
	<div class="alert alert-info">
		<a href="cargas" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<input type="text" class="input-medium search-query" placeholder="Search" id="buscar">
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th >#</th>
			<th>Plan</th>
			<th>Career</th>
			<th>Subject</th>
			<th class="right">#Stud.</th>
			<th>Key</th>
			<th>Professor</th>
			<th>Block</th>
			<th>Group</th>
			<th class="right">Progress(%)</th>
		</tr>
	</thead>
	<tbody>
<%
	int row = 1;
	for(CargaAcademica carga : lisMaterias){
		
		String facultadId		= "0";
		String nombreCarrera 	= "-";		
		if(mapaCarreras.containsKey(carga.getCarreraId())){
			nombreCarrera = mapaCarreras.get(carga.getCarreraId()).getNombreCarrera();
			facultadId 		= mapaCarreras.get(carga.getCarreraId()).getFacultadId();
		}
		
		String facultadNombre = "-";		
		if(mapaFacultades.containsKey(facultadId)){
			facultadNombre = mapaFacultades.get(facultadId).getNombreCorto();
		}		
		
		String numAlumnos = "0";		
		if(mapaAlumnos.containsKey(carga.getCursoCargaId())){
			numAlumnos = mapaAlumnos.get(carga.getCursoCargaId());
		}		
		String suma = "0";		
		if(mapaEsquemas.containsKey(carga.getCursoCargaId())){
			suma = mapaEsquemas.get(carga.getCursoCargaId());
		}
%>
		<tr>
			<td><%=row %></td>
			<td><%=facultadNombre%></td>
			<td><%=nombreCarrera%></td>
			<td><%=carga.getNombreCurso() %></td>
			<td class="right"><%=numAlumnos.equals("0")?"<span class='badge bg-warning'>"+numAlumnos+"</span>":numAlumnos%></td>
			<td><%=carga.getCodigoPersonal() %></td>
			<td><%=carga.getNombre() %></td>
			<td><%=carga.getBloqueId()%></td>
			<td><%=carga.getGrupo()%></td>
			<td class="right"><%=suma.equals("0")?suma:"<span class='badge bg-success'>"+suma+"</span>"%></td>
		</tr>
<%
		row ++;
	} 
%>
	<tbody>			
	</table>
</div>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>
<script>
$('#buscar').search();
</script>