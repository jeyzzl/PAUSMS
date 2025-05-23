
<%@ page import="aca.investiga.spring.InvComentario"%>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.HashMap" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");
	String nombreUsuario	= (String)request.getAttribute("nombreUsuario");	
	
	List<InvComentario> lisComentarios		= (List<InvComentario>)request.getAttribute("lisComentarios");
	HashMap<String,String> mapUsuarios		= (HashMap<String,String>)request.getAttribute("mapUsuarios");
%>

<html>
<head>
<body>
	<div class="container-fluid">
		<h2>Listado de Comentarios <small class="text-muted fs-4">Proyecto: <%=proyectoId%></small></h2>				
		<div class="alert alert-info">
			<a href="proyecto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		</div>	
		<table class="table table-sm table-bordered">  
			<thead>
			<tr>
	    		<td colspan="10" height="20" align="center"><strong><font size="2">Usuario: [ <%=codigo%> ] &nbsp; <%=nombreUsuario%></font></strong></td>
	  		</tr>	
			</thead>
			<thead class="table-info">
			<tr>
				<th width="5%">Referencia</th>
				<th width="15%"><spring:message code="aca.Nombre"/></th>
				<th width="10%"><spring:message code="aca.Fecha"/></th>
				<th width="70%"><spring:message code="aca.Comentario"/></th>
			</tr>
			</thead>
<%
	 	String nombreDepartamento = ""; 
		for(InvComentario comentarios : lisComentarios){
			
			String usuarioNombre = "-";
			if(mapUsuarios.containsKey(comentarios.getCodigoPersonal())){
			   usuarioNombre = mapUsuarios.get(comentarios.getCodigoPersonal());
			}
%>	
			<tr>
				<td><%= comentarios.getFolio() %></td>
				<td><%= usuarioNombre%></td>
				<td><%= comentarios.getFecha() %></td>
				<td><%= comentarios.getComentario() %></td>
			</tr>
<%	} %>
		</table>
	</div>	
</body>
</html>
