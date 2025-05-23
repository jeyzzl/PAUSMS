<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.hca.spring.HcaMaestro"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file="id.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String cargaId 			= request.getParameter("CargaId")==null?session.getAttribute("cargaId").toString():request.getParameter("CargaId");
	String facultadId		= request.getParameter("FacultadId")==null?"101":request.getParameter("FacultadId");
	String opcion 			= request.getParameter("FacultadId")==null?"1":request.getParameter("Opcion");
	String cargaNombre 		= (String)request.getAttribute("cargaNombre");
	
	// HashMap de los empleados	
 	HashMap<String, String> mapMaestros 			 = (HashMap<String, String>)request.getAttribute("mapMaestros");

	// Mapa para contar las materias de cada maestro	
 	HashMap<String, String> mapMaterias 			 = (HashMap<String, String>)request.getAttribute("mapMaterias");
	
	//Mapa para saber los maestros capturados
 	HashMap<String, String> mapMaestrosCapturados 	 = (HashMap<String, String>)request.getAttribute("mapMaestrosCapturados");
 	HashMap<String, String> mapMaestrosPermiso 		 = (HashMap<String, String>)request.getAttribute("mapMaestrosPermiso");
 	HashMap<String, HcaMaestro> mapBases			 = (HashMap<String, HcaMaestro>)request.getAttribute("mapBases");
 	HashMap<String, CatCarrera> mapCarreras			 = (HashMap<String, CatCarrera>)request.getAttribute("mapCarreras");

	// Lista de maestros	
    List<String>  lisMaestros						 = (List<String>) request.getAttribute("lisMaestros");	
%>
<div class="container-fluid">
	<h2>Listado de Maestros <small class="text-muted fs-5">( <%=cargaId%> - <%=cargaNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="elige" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Menú</a>
	</div>
	<br>
	<table class="table table-bordered table-sm">
		<tr>
			<th>#</th>
			<th>Permiso</th>			
			<th><spring:message code="aca.Clave"/></th>			
			<th><spring:message code="aca.Maestro"/></th>
			<th>Carrera de base</th>
			<th class="text-end"><spring:message code="aca.Materias"/></th>
		</tr>
<%
	int cont = 0;
	for(String maestro : lisMaestros){
		cont++;
		
		String maestroNombre = "-";
		if(mapMaestros.containsKey( maestro )){
			maestroNombre = mapMaestros.get( maestro );
		}
		
		String materias = "0";
		if(mapMaterias.containsKey( maestro )){
			materias = mapMaterias.get( maestro );
		}
		
		String permiso = "<span class='badge rounded-pill bg-warning'>NO</span>";
		if (mapMaestrosPermiso.containsKey(maestro)){
			permiso = "<span class='badge rounded-pill bg-dark'>SI</span>";
		}	
		
		String carrera 			= "-";
		String carreraNombre 	= "";
		if (mapBases.containsKey(maestro)){
			carrera = mapBases.get(maestro).getCarreraId();
			if (mapCarreras.containsKey(carrera)){
				carreraNombre = mapCarreras.get(carrera).getNombreCarrera();
			}
		}
%>
		<tr>
		<%if(mapMaestrosCapturados.containsKey(maestro)){ %>
			<td><span class="badge rounded-pill bg-dark"><%=cont%></span></td>
		<%}else{%>
			<td><span class="badge rounded-pill bg-warning"><%=cont%></span></td>
		<%} %>
			<td><%=permiso%></td>
			<td><%=maestro%></td>
			<td><%=maestroNombre%></td>
			<td><%=carrera%> - <%=carreraNombre%></td>
			<td class="text-end"><%=materias%></td>
		</tr>
<%	} %>		
	</table>
</div>
