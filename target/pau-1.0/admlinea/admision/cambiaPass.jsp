<%@page import="aca.admision.spring.AdmUsuario"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<script type="text/javascript">
	function verifica(){
		if(document.getElementById("Clave").value != ""){
			document.forma.submit();
		}else{
			alert("Introduzca la nueva clave");
		}
	}
</script>
</head>
<% 
	String usuarioId		= (String) request.getAttribute("usuarioId");
	AdmUsuario admUsuario	= (AdmUsuario) request.getAttribute("admUsuario");
	
	String mensaje   		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
%>
<div class="container-fluid">
	<h2>Cambiar clave del aspirante *<small class="text-muted fs-6">( <%=admUsuario.getNombre()%> <%=admUsuario.getApellidoMaterno()==null?"":admUsuario.getApellidoMaterno()%> <%=admUsuario.getApellidoPaterno()%> )</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="sinSolicitud"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if(!mensaje.equals("0")){%>
	<div class="alert alert-info" role="alert">
  		<%=mensaje %> 
	</div>
<%	} %>
	<form id="forma" name="forma" action="grabaClave?UsuarioId=<%=usuarioId%>" method="post">
	<table style=width:40%;align:center class="table table-condensed">
		<tr>
			<td><b><spring:message code='aca.Usuario'/></b></td>
			<td><%=admUsuario.getCuenta()%></td>
		</tr>
		<tr>
			<td><b><spring:message code="aca.Clave"/>:</b></td>
			<td><input type="password" id="Clave" name="Clave" /></td>
		</tr>
		<tr>
			<td colspan="2"><input class="btn btn-primary" type="button" value="Guardar" onclick="verifica();" /></td>
		</tr>
	</table>
</form>
</div>