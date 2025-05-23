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
	HashMap<String,String> mapaPromedios			= (HashMap<String,String>) request.getAttribute("mapaPromedios");
	
	java.text.DecimalFormat getFormato 				= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
%>
<div class="container-fluid">
	<h2>Schools</h2>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr> 
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="10%">Short.</th>
			<th width="35%">Name</th>
			<th width="10%">User ID</th>
			<th width="20%"><spring:message code="aca.Director"/></th>
			<%-- <th width="20%" class="right">GPA</th> --%>
		</tr>
		</thead>
<%
	for (CatFacultad facultad : lisFacultades){
		
		String nombreEmpleado = "";
		if (mapaDirectores.containsKey(facultad.getCodigoPersonal())){
			nombreEmpleado = mapaDirectores.get(facultad.getCodigoPersonal());
		}
		
		double promedio = 0;
		if (mapaPromedios.containsKey(facultad.getFacultadId())){
			promedio = Double.valueOf(mapaPromedios.get(facultad.getFacultadId()));
		}		
%>
	  	<tr>
		    <td class="left"><%=facultad.getFacultadId()%></td>
		    <td class="left"><%=facultad.getNombreCorto()%></td>
		    <td class="left"><a href="listado?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></td>
		    <td class="left"><%=facultad.getCodigoPersonal()%></td>
		    <td class="left"><%=nombreEmpleado%></td>
		    <%-- <td class="right"><%=getFormato.format(promedio)%></td> --%>
		 </tr>
<%
	}
%> 
	</table>			
</div>