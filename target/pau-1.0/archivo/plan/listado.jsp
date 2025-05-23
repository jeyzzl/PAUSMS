<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchGrupo"%>
<%@ page import="aca.archivo.spring.ArchGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<ArchGrupo> lisArchGrupo = (List<ArchGrupo>) request.getAttribute("lisArchGrupo");
	
	HashMap<String,ArchGrupo> mapaArchGrupo 		= (HashMap<String,ArchGrupo>) request.getAttribute("mapaArchGrupo");
	HashMap<String,String> mapaPlanesPorGrupo 		= (HashMap<String,String>) request.getAttribute("mapaPlanesPorGrupo");
	HashMap<String,String> mapaDocumentoPorGrupo 	= (HashMap<String,String>) request.getAttribute("mapaDocumentoPorGrupo");

	String nombreGrupo 		= "";
	String totalPlanes 		= "0";
	String totalDocumentos 	= "0";
%>
<head>
</head>
<div class="container-fluid">
	<h2>Groups</h2>
	<table class="table table-sm table-bordered">
	<tr>
		<th width="10%">Id</th>
		<th width="70%">Group</th>
		<th width="10%" class="right">Documents</th>
		<th width="10%" class="right">Plans</th>
	</tr>
<%  for(ArchGrupo grupo : lisArchGrupo) {
		if(mapaArchGrupo.containsKey(grupo.getGrupoId())){
			nombreGrupo = mapaArchGrupo.get(grupo.getGrupoId()).getGrupoNombre();
		}
		if(mapaDocumentoPorGrupo.containsKey(grupo.getGrupoId())){
			totalDocumentos = mapaDocumentoPorGrupo.get(grupo.getGrupoId());
		}
		if(mapaPlanesPorGrupo.containsKey(grupo.getGrupoId())){
			totalPlanes = mapaPlanesPorGrupo.get(grupo.getGrupoId());
		}%>
	<tr>
 		<td><%=grupo.getGrupoId()%></td>
 		<td><a href="grupo?GrupoId=<%=grupo.getGrupoId()%>"><%=nombreGrupo%></a></td>
 		<td class="right"><%=totalDocumentos%></td>
 		<td class="right"><%=totalPlanes%></td>
	</tr>
<%		totalPlanes = "0";
	}%>
	</table>
</div>