<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	List<CatFacultad> lisFacultades 		= (List<CatFacultad>) request.getAttribute("lisFacultades");
	HashMap<String, String> mapaDirectores 	= (HashMap<String, String>) request.getAttribute("mapaDirectores");
	HashMap<String, String> mapaPlanes 		= (HashMap<String, String>) request.getAttribute("mapaPlanes");
%>
<div class="container-fluid">
	<h2>Majors and Minors Catalog<small class="text-muted fs-4"> (School List)</small></h2>
	<hr>
 	<table class="table table-bordered table-sm">
	<thead class="table-info">
		<tr>
			<th width="10%"><spring:message code="aca.Numero"/></th>
			<th width="35%"><spring:message code="aca.Facultad"/></th>
			<th width="20%"><spring:message code="aca.Director"/></th>
			<%-- <th width="10%" class="text-end">Admissible Plans</th>
			<th width="10%" class="text-end">Valid Plans</th>
			<th width="10%" class="text-end">Inactive Plans</th> --%>
			<th width="5%" class="text-end">Total</th>
		</tr>
	</thead>
<%
	for (CatFacultad facultad : lisFacultades){
						
		String nombreDirector = "-";
		if(mapaDirectores.containsKey(facultad.getCodigoPersonal())){
			nombreDirector = mapaDirectores.get(facultad.getCodigoPersonal());
		}
		String admision="0",vigente="0", inactivo="0";
		if ( mapaPlanes.containsKey(facultad.getFacultadId()+"A")) admision 	= mapaPlanes.get(facultad.getFacultadId()+"A"); 
		if ( mapaPlanes.containsKey(facultad.getFacultadId()+"V")) vigente 		= mapaPlanes.get(facultad.getFacultadId()+"V");
		if ( mapaPlanes.containsKey(facultad.getFacultadId()+"I")) inactivo 	= mapaPlanes.get(facultad.getFacultadId()+"I");
		int total = Integer.parseInt(admision)+Integer.parseInt(vigente)+Integer.parseInt(inactivo);
%>
		<tr>
			<td class="text-start"><%=facultad.getFacultadId()%></td>
			<td class="text-start"><a href="listado?Facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></td>
			<td class="text-start"><%=nombreDirector%></td>
			<%-- <td class="text-end"><%=admision%></td>
			<td class="text-end"><%=vigente%></td>
			<td class="text-end"><%=inactivo%></td> --%>
			<td class="text-end"><%=total%></td>
		</tr>
<%
	}
%> 
	</table>
</div>
