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
	<h2>Requirements by Course</h2>
<div class="alert alert-info"></div>
 	<table class="table table-bordered table-sm">
		<thead>
			<tr class="table-info">
				<th><spring:message code="aca.Numero"/></th>
				<th><spring:message code="aca.Facultad"/></th>
				<th><spring:message code="aca.Coordinador"/></th>
			</tr>
		</thead>
<%
	for (CatFacultad facultad : lisFacultades){
						
		String nombreDirector = "-";
		if(mapaDirectores.containsKey(facultad.getCodigoPersonal())){
			nombreDirector = mapaDirectores.get(facultad.getCodigoPersonal());
		}
%>
		<tr>
			<td align="left"><font size="2"><%=facultad.getFacultadId()%></font></td>
			<td align="left"><a href="carrera?facultad=<%=facultad.getFacultadId()%>"><font size="2"><%=facultad.getNombreFacultad()%></font></a></td>
			<td align="left"><font size="1"><%=nombreDirector%></font></td>
		</tr>
<%
	}
%> 
	</table>
</div>
