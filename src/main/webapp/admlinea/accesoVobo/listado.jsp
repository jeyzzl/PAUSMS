<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<html>
<%
	List<CatCarrera> listaCarreras 				= (List<CatCarrera>)request.getAttribute("listaCarreras");
	HashMap<String,CatFacultad> mapaFacultad 	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultad");	
	HashMap<String,String> mapAcceCarrera 		= (HashMap<String,String>)request.getAttribute("mapAcceCarrera");	

%>
<body>
<div class="container-fluid">
	<h1>Acceso para visto bueno en las carreras</h1>
	<div class="alert alert-info d-flex align-items-center"><input type="text" class="form-control" style="width:120px" placeholder="Buscar..." id="buscar"></div>
<%		
	int cont = 1;
	String facultadId 		= "";
	String nombreFacultad 	= "";
%>					
	<table class="table table-sm table-bordered" id="table">
<% 	
	for(CatCarrera carrera : listaCarreras){
		String accesos		 	= "0";
		if(mapAcceCarrera.containsKey(carrera.getCarreraId())){
			accesos = mapAcceCarrera.get(carrera.getCarreraId());
		}
		if(!facultadId.equals(carrera.getFacultadId())){
			facultadId = carrera.getFacultadId();
			if(mapaFacultad.containsKey(carrera.getFacultadId())){
				nombreFacultad = mapaFacultad.get(carrera.getFacultadId()).getNombreFacultad();
			}
%>	
	<thead>
	<tr>
		<th colspan="3">
			<div class="alert alert-info" role="alert">
					<h3><%=nombreFacultad%></h3>
			</div>
		</th>
	</tr>
	<tr>
		<th>#</th>
		<th>Clave</th>
		<th>Carrera</th>
		<th>Accesos</th>
	</tr>				
	</thead>
<% 	
		} 
%>					
	<tbody>
		<tr>
			<th><%=cont++%></th>
			<th><%=carrera.getCarreraId()%></th>
			<td><%=carrera.getNombreCarrera()%></td>
			<td><a class="btn btn-primary" href="accesosVobo?CarreraId=<%=carrera.getCarreraId()%>" title="Acceso"><%=accesos%></a></td>
		</tr>				
	</tbody>
<% 	
	} 
%>
	</table>	
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</html>