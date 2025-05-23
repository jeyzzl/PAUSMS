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
	String colorTablas = "#eeeeee";
%>
<div class="container-fluid">
	<h2>Required Credits</h2>
	<hr>
	<table class="table table-bordered table-striped table-sm">
	<thead class="table-info">
		<tr> 
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="10%"><spring:message code="aca.Corto"/> Name</th>
			<th width="55%"><spring:message code="aca.Facultad"/></th>
			<th width="10%"><spring:message code="aca.Clave"/></th>
			<th width="20%"><spring:message code="aca.Director"/></th>			
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