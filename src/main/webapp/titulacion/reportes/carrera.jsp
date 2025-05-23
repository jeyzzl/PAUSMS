<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitCarrera"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<%
	//String codigoAlumno 		= (String) request.getAttribute("codigoAlumno");
	
	List<TitCarrera> lista						= (List<TitCarrera>)request.getAttribute("lista");
	HashMap<String,String> mapaTitulados		= (HashMap<String, String>)request.getAttribute("mapaTitulados");
	HashMap<String,String> mapaMatriculas		= (HashMap<String, String>)request.getAttribute("mapaMatriculas");
	HashMap<String, String> mapaAutorizacion	= (HashMap<String, String>)request.getAttribute("mapaAutorizacion");
	HashMap<String,String> mapaPlanes			= (HashMap<String, String>)request.getAttribute("mapaPlanes");
	HashMap<String,MapaPlan> mapaPlanRvoe		= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanRvoe");
	HashMap<String,String> mapaTramites			= (HashMap<String, String>)request.getAttribute("mapaTramites");
%>
<body>
<div class="container-fluid">
	<h2>Carrera</h2>
	<div class="alert alert-info d-flex align-items-center">		
		<a class="btn btn-primary" href="menu">Regresar</a>&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar..." id="buscar" style= "width:150px;">
	</div>	

	<table class="table table-sm table-bordered" id="table">
	<thead class="table-info">
	<tr>
		<th width="5%">#</th>
		<th width="5%">Folio</th>
		<th width="5%">Matrícula</th>
		<th width="23%">Alumno</th>
		<th width="5%">Clave Carrera</th>		
		<th width="22%">Nombre Carrera</th>
		<th width="22%">Nombre Carrera2</th>
		<th width="5%">Plan</th>
		<th width="5%">Fecha Inicio</th>
		<th width="5%">Fecha terminación</th>
		<th width="10%">Autorización</th>
		<th width="5%">Número de RVOE</th>			
		<th width="5%">RVOE</th>
		<th width="5%">Trámite</th>			
	</tr>
	</thead>
	<tbody>
<%	
	int row=0;
	for (TitCarrera carrera : lista){
		row++;
		String matricula = "0";
		if (mapaMatriculas.containsKey(carrera.getFolio())){
			matricula = mapaMatriculas.get(carrera.getFolio());
		}
		
		String nombreAlumno = "0";
		if (mapaTitulados.containsKey(matricula)){
			nombreAlumno = mapaTitulados.get(matricula);
		}
		String nombreAutorizacion = "-";
		if (mapaAutorizacion.containsKey(carrera.getIdAutorizacion())) {
			nombreAutorizacion = mapaAutorizacion.get(carrera.getIdAutorizacion());
		}
		String planId = "-";
		if (mapaPlanes.containsKey(carrera.getFolio())){
			planId = mapaPlanes.get(carrera.getFolio());
		}
		
		String rvoe = "";
		
		if(mapaPlanRvoe.containsKey(planId)){
			rvoe = mapaPlanRvoe.get(planId).getRvoe();
		}
		
		boolean claveCarreraTmp 	= false;
		boolean nombreCarreraTmp 	= false;
		
		if(mapaPlanRvoe.containsKey(planId)){
			if(mapaPlanRvoe.get(planId).getClaveProfesiones().equals(carrera.getCveCarrera())){
				claveCarreraTmp = true;
			}
			if(mapaPlanRvoe.get(planId).getCarreraSe().equals(carrera.getNombreCarrera())){
				nombreCarreraTmp = true;
			}
		}
		
		String tramite = "<span class='badge bg-warning'>0</span>";
		if (mapaTramites.containsKey(carrera.getFolio())){
				tramite 	= "<span class='badge bg-dark'>"+mapaTramites.get(carrera.getFolio())+"</span>";
		}
%>	
	<tr>
		<td><%=row %></td>
		<td><%=carrera.getFolio() %></td>
		<td><%=matricula%></td>
		<td><%=nombreAlumno%></td>
		<td <%=claveCarreraTmp != true ? "style='color:red;'" : ""%>><%=carrera.getCveCarrera()%></td>
		<td <%=nombreCarreraTmp != true ? "style='color:red;'" : ""%>><%=carrera.getNombreCarrera()%></td>
		<td><%=nombreCarreraTmp == true ? "" : mapaPlanRvoe.get(planId).getCarreraSe()%></td>
		<td><%=planId%></td>
		<td><%=carrera.getFechaInicio() %></td>
		<td><%=carrera.getFechaTerminacion() %></td>
		<td><%=carrera.getAutorizacion() %></td>
		
<%
		if(rvoe.equals(carrera.getNumeroRvoe())){
%>
		<td><%=carrera.getNumeroRvoe() %></td>	
<%
		}else{
%>
		<td style="color: red"><%=carrera.getNumeroRvoe()%></td>	
<%
		}
%>

<%
		if(rvoe.equals(carrera.getNumeroRvoe())){			
%>
			<td><%="-"%>
<%
		}else{
%>
			<td><%=rvoe%></td>
<% 
		}
%>		

		<td><%=tramite%></td>
	</tr>
<%
	}
%>
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</body>
</html>