<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%  
	List<CatFacultad> lisFacultades 				= (List<CatFacultad>) request.getAttribute("lisFacultades");	
	HashMap<String,String> mapaDirectores			= (HashMap<String,String>) request.getAttribute("mapaDirectores");
%>
<div class="container-fluid">
	<h1>Facultades</h1>
	 	<table class="table table-bordered">
		<thead class="table-info">
		<tr> 
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="10%">Corto</th>
			<th width="35%"><spring:message code="aca.Facultad"/></th>
			<th width="10%"><spring:message code="aca.Clave"/></th>
			<th width="20%"><spring:message code="aca.Director"/></th>
			<th width="20%">&nbsp;</th>
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
		    <td align="left"><a href="cohorte?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getTitulo()%> <spring:message code="aca.De"/> <%=facultad.getNombreFacultad()%></a></td>
		    <td align="left"><%=facultad.getCodigoPersonal()%></font></td>
		    <td align="left"><%=nombreEmpleado%></td>
		    <td align="left">&nbsp;</td>
		 </tr>
<%
	}
%> 
		</tbody>
	</table>			
</div>