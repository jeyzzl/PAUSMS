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
%>
<div class="container-fluid">
	<h2><spring:message code="aca.ListaDeFacultades"/><small class="text-muted fs-4"> <!-- (<a href="planDatos"><spring:message code="aca.Alineacion"/> DGP-SE</a>) --> </small></h2>
	<hr>
 	<table class="table table-bordered table-sm">
	<thead class="table-info">
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Facultad"/></th>
			<th><spring:message code="aca.Director"/></th>
		</tr>
	</thead>
	<tbody>
<%
	for (CatFacultad facultad : lisFacultades){
						
		String nombreDirector = "-";
		if(mapaDirectores.containsKey(facultad.getCodigoPersonal())){
			nombreDirector = mapaDirectores.get(facultad.getCodigoPersonal());
		}
%>
		<tr>
			<td class="text-start"><%=facultad.getFacultadId()%></td>
			<td class="text-start"><a href="listado?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></td>
			<td class="text-start"><%=nombreDirector%></td>
		</tr>
<%	} %> 
	</tbody>
	</table>
</div>