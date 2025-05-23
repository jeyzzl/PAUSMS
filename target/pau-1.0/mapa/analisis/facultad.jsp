<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%  
	List<CatFacultad> lisFacultades 				= (List<CatFacultad>) request.getAttribute("lisFacultades");
	HashMap<String,String> mapaDirectores			= (HashMap<String,String>) request.getAttribute("mapaDirectores");
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Facultades"/></h2>
	<hr>
	<table class="table table-bordered table-stiped">
	<thead class="table-info">
		<tr> 
			<th class="table-info" width="5%"><spring:message code="aca.Numero"/></th>
			<th class="table-info" width="10%"><spring:message code="aca.Corto"/></th>
			<th class="table-info" width="35%"><spring:message code="aca.Facultad"/></th>
			<th class="table-info" width="10%"><spring:message code="aca.Clave"/></th>
			<th class="table-info" width="20%"><spring:message code="aca.Director"/></th>
		</tr>
	</thead>
	<tbody>
<%
	for (CatFacultad facultad : lisFacultades){
		
		String nombreEmpleado = "";
		if (mapaDirectores.containsKey(facultad.getCodigoPersonal())){
			nombreEmpleado = mapaDirectores.get(facultad.getCodigoPersonal());
		}
%>
	  	<tr>
		    <td align="left"><%=facultad.getFacultadId()%></font></td>
		    <td align="left"><%=facultad.getNombreCorto()%></font></td>
		    <td align="left"><a href="listado?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></td>
		    <td align="left"><%=facultad.getCodigoPersonal()%></font></td>
		    <td align="left"><%=nombreEmpleado%></td>
		 </tr>
<%	} %> 
		</tbody>
	</table>			
</div>