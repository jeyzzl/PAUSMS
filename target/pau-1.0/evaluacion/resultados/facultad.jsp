<%@page import="java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="facUtil"  class="aca.catalogo.FacultadUtil" scope="page"/>

<%  
	List<CatFacultad>lisFacultades				= (List<CatFacultad>) request.getAttribute("lisFacultades");
	HashMap<String,String> mapaDirectores		= (HashMap<String,String>) request.getAttribute("mapaDirectores");
%>
<style type="text/css"></style>
<div class="container-fluid">
	<h1><spring:message code="aca.ListaDeFacultades"/></h1>
	<hr>
	<table class="table table-sm table-bordered"> 
  	<tr class="table-info"> 
    	<th width="5%" align="center"><spring:message code="aca.Numero"/></th>
    	<th width="50%" align="center"><spring:message code="aca.Facultad"/></th>
    	<th width="45%" align="center"><spring:message code="aca.Director"/></th>
  	</tr>  
<%
	int cont = 0;
	for (CatFacultad fac : lisFacultades){
		cont++;
		
		String nombreEmpleado = "";
		if (mapaDirectores.containsKey(fac.getCodigoPersonal())){
			nombreEmpleado = mapaDirectores.get(fac.getCodigoPersonal());
		}
%>
	<tr>
    	<td align="left"><font size="2"><%=fac.getFacultadId()%></font></td>
    	<td align="left"><a href="maestro?facultad=<%=fac.getFacultadId()%>"><font size="2"><%=fac.getTitulo()%> de <%=fac.getNombreFacultad()%></font></a></td>
    	<td align="left"><font size="1"><%=nombreEmpleado%></font></td>
  	</tr>
<%	} %>  
</table>
</div>