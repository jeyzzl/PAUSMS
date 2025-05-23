<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%  
	List<CatFacultad> lisFac				= (List<CatFacultad>) request.getAttribute("lisFac");
	HashMap<String, String> mapaDirectores 	= (HashMap<String, String>) request.getAttribute("mapaDirectores");
	
	int cont		= 0;	
	String director = "";
%>
<div class="container-fluid">
	<h2>Planes <small class="text-muted fs-4">(Lista de facultades)</small></h2>
	<hr>
 	<table class="table table-bordered">
	<thead class="table-info">
		<tr> 
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Facultad"/></th>
			<th><spring:message code="aca.Director"/></th>
		</tr>
	</thead>
	<tbody>		
<%
		for (CatFacultad fac : lisFac){	
			cont++;		
			
			if(mapaDirectores.containsKey(fac.getCodigoPersonal())){
				director = mapaDirectores.get(fac.getCodigoPersonal());
			}
	%>
		<tr>
	   		<td class="fs-6"><%=fac.getFacultadId()%></td>
	   	 	<td class="fs-6"><a href="carrera_plan?facultad=<%=fac.getFacultadId()%>"><%=fac.getTitulo()%> <spring:message code="aca.De"/> <%=fac.getNombreFacultad()%></a></td>
	    	<td class="fs-6"><%=director%></td>
	   	</tr> 
	<%
		}	
		lisFac	= null;
	%>
	</tbody>		
	</table>
			
</div>