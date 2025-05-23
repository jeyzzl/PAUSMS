<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.MaestrosUtil"%>
<%  
	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");		
	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	boolean esSuper      	= (boolean)request.getAttribute("esSuper");
	
	List<CatFacultad> lisFacultades			= (List<CatFacultad>)request.getAttribute("lisFacultades");
	List<String> lisPermisos				= (List<String>)request.getAttribute("lisPermisos");
	HashMap<String,String> mapaMaestros		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="aca.ListaDeFacultades"/></h1>
	<div class="alert alert-info"></div>	
	<table style="width:70%" class="table table-sm table-striped">
	<tr> 
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="50%"><spring:message code="aca.Facultad"/></th>
		<th width="45%"><spring:message code="aca.Director"/></th>
	</tr>  
<%
	boolean permiso = false;
	for(CatFacultad fac : lisFacultades){
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(fac.getCodigoPersonal())){
			maestroNombre = mapaMaestros.get(fac.getCodigoPersonal());
		}
		
		if (lisPermisos.contains(fac.getFacultadId())) permiso=true; else permiso = false;
		if (permiso||esAdmin||esSuper){			
%>
		<tr>
			<td><%=fac.getFacultadId()%></td>
			<td><a href="maestro?FacultadId=<%=fac.getFacultadId()%>"><%=fac.getTitulo()%> de <%=fac.getNombreFacultad()%></a></td>
			<td><%=maestroNombre%></td>
		</tr>
<%
		}
	}
%>
	</table>
	<table style="width:57%">
	<tr> 
		<td class="end">¡ Choose a Faculty !</td>
	</tr>
	</table>
</div>
</body>