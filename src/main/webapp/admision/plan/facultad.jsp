<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String areaId 				= (String)session.getAttribute("areaId");
	// Lista de facultades
	List<CatFacultad> lisFacultades			= (List<CatFacultad>) request.getAttribute("lisFacultades"); 
	HashMap<String,String> mapaDirectores	= (HashMap<String,String>) request.getAttribute("mapaDirectores");
%>
<div class="container-fluid">
	<h2><spring:message code="catalogos.area.Titulo3"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="planes"><spring:message code="aca.Regresar"/></a>
	</div>
	<table class="table table-sm table-bordered" style="width:60%">
	<thead class="table-info">
  	<tr> 	    
	    <th><spring:message code="aca.Numero"/></th>
	    <th><spring:message code="aca.Facultad"/></th>
	    <th><spring:message code="catalogos.area.Directores"/></th>
  	</tr>
  	</thead>
<%
  	for (CatFacultad facultad : lisFacultades){
  		
  		String director = "";
  		if (mapaDirectores.containsKey(facultad.getCodigoPersonal())){
  			director = mapaDirectores.get(facultad.getCodigoPersonal());
  		}
  		if(codigoPersonal.equals("9800946") || codigoPersonal.equals("9801137")){
			if(facultad.getFacultadId().equals("109")){
%>
  	<tr class="">    	
	    <td align="center"><%=facultad.getFacultadId()%></td>
	    <td>
	    	<a href="listado?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%>
	    	</a>
	    </td>
	    <td title="<%=facultad.getCodigoPersonal() %>"><%=director%></td>
  	</tr>
<%
			}
  		}else{
%>
	<tr class="tr2">    	
	    <td align="center"><%=facultad.getFacultadId()%></td>
	    <td>
	    	<a href="listado?facultad=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%>
	    	</a>
	    </td>
	    <td title="<%=facultad.getCodigoPersonal() %>"><%=director%></td>
  	</tr>
<%  			
  		}	
	}	
%>
	</table>
</div>