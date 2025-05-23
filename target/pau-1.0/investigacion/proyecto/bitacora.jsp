
<%@ page import="aca.investiga.spring.InvBitacoraDao"%>
<%@ page import="aca.investiga.spring.InvBitacora"%>

<%@ page import = "java.util.List" %>
<%@ page import = "java.util.HashMap" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");
	String nombreProyecto	= (String)request.getAttribute("nombreProyecto");
	
	List<InvBitacora> lisBitacora		= (List<InvBitacora>)request.getAttribute("lisBitacora");
	HashMap<String,String> mapUsuarios		= (HashMap<String,String>)request.getAttribute("mapUsuarios");
%>
<html>
<head>
<style>		
	body{
		background : white;
	}
</style>
<body>
	<div class="container-fluid">
		<h1>Bitácora</h1>
		<br />		
		<div class="alert alert-info">
			<a href="proyecto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		</div>
	
	<table class="table table-sm table-bordered">  
		<thead>
		<tr>
    		<td colspan="5" height="20" align="center"><strong><font size="2">Proyecto: [ <%=proyectoId%> ] &nbsp; <%=nombreProyecto%></font></strong></td>
  		</tr>	
		</thead>
		<thead class="table-info">
		<tr>
			<th width="3%">Referencia</th>
			<th width="10%"><spring:message code="aca.Nombre"/></th>
			<th width="7%"><spring:message code="aca.Fecha"/></th>
			<th width="7%">Estado Ant.</th>
			<th width="7%">Estado Actual</th>
			
		</tr>
		</thead>
<%
	String estadoOld = "";
	String estadoNew = "";

	for (InvBitacora bitacora : lisBitacora){
		if(bitacora.getEstadoOld().equals("0")) estadoOld = "Capturado"; ;
		if(bitacora.getEstadoOld().equals("1")) estadoOld = "Comité Inv.";
		if(bitacora.getEstadoOld().equals("2")) estadoOld = "Comité Ética";
		if(bitacora.getEstadoOld().equals("3")) estadoOld = "DPI";
		if(bitacora.getEstadoOld().equals("4")) estadoOld = "DPI Registrado";
		if(bitacora.getEstadoNew().equals("0")) estadoNew = "Capturado"; 
		if(bitacora.getEstadoNew().equals("1")) estadoNew = "Comité Inv.";
		if(bitacora.getEstadoNew().equals("2")) estadoNew = "Comité Ética";
		if(bitacora.getEstadoNew().equals("3")) estadoNew = "DPI";
		if(bitacora.getEstadoNew().equals("4")) estadoNew = "DPI-Registrado";
		
		String usuarioNombre = "-";
		if(mapUsuarios.containsKey(bitacora.getUsuario())){
		   usuarioNombre = mapUsuarios.get(bitacora.getUsuario());
		}
%>		
		<tr>
			<td><%= bitacora.getFolio() %></td>
			<td><%= usuarioNombre %></td>
			<td><%= bitacora.getFecha() %></td>
			<td><%= estadoOld %></td>
			<td><%= estadoNew %></td>
		</tr>
<%	} %>
	</table>
	</div>	
</body>
</html>
