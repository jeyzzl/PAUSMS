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
	HashMap<String,String> mapaCiclosReg			= (HashMap<String,String>) request.getAttribute("mapaCiclosReg");
	HashMap<String,String> mapaCiclosPen			= (HashMap<String,String>) request.getAttribute("mapaCiclosPen");
%>

<div class="container-fluid">
	<h2>Cycles Data</h2>
	<hr>
	 <table class="table table-bordered table-stiped">
	<thead class="table-info">
	<tr> 
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="10%"><spring:message code="aca.Corto"/></th>
		<th width="35%"><spring:message code="aca.Facultad"/></th>
		<th width="10%"><spring:message code="aca.Clave"/></th>
		<th width="20%"><spring:message code="aca.Director"/></th>
		<th width="10%" class="text-end"><spring:message code="aca.Registrados"/></th>
		<th width="10%" class="text-end"><spring:message code="aca.Pendientes"/></th>
	</tr>
	</thead>
	<tbody>
<%
	for (CatFacultad facultad : lisFacultades){
		
		String nombreEmpleado = "";
		if (mapaDirectores.containsKey(facultad.getCodigoPersonal())){
			nombreEmpleado = mapaDirectores.get(facultad.getCodigoPersonal());
		}
		String registrados = "0";
		if (mapaCiclosReg.containsKey(facultad.getFacultadId())){
			registrados = mapaCiclosReg.get(facultad.getFacultadId());
		}
		
		String pendientes = "0";
		if (mapaCiclosPen.containsKey(facultad.getFacultadId())){
			pendientes = mapaCiclosPen.get(facultad.getFacultadId());
		}
%>
  	<tr>
	    <td align="left"><%=facultad.getFacultadId()%></td>
	    <td align="left"><%=facultad.getNombreCorto()%></td>
	    <td align="left"><a href="listado?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></td>
	    <td align="left"><%=facultad.getCodigoPersonal()%></td>
	    <td align="left"><%=nombreEmpleado%></td>
	   	<td class="text-end"><%=registrados%></td>
	    <td class="text-end"><%=pendientes%></td>
	 </tr>
<%	} %> 
	</tbody>
	</table>			
</div>