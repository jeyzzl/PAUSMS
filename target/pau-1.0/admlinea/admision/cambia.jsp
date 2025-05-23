<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AdmSolicitud" scope="page" class="aca.admision.AdmSolicitud" />
<head>
<script type="text/javascript">
	function verifica(){
		if(document.getElementById("clave1").value != ""){
			document.forma.submit();
		}else{
			alert("Introduzca la nueva clave");
		}
	}
</script>
</head>
<% 
	String mensaje 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	String folio	= request.getParameter("Folio");
	String nombre	= (String) request.getAttribute("nombre");
	String usuario	= (String) request.getAttribute("usuario");

%>
<div class="container-fluid">
	<h2>Update Applicant's Password <small>( <%=nombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="registro?Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if(!mensaje.equals("0")){%>
	<div class="alert alert-info" role="alert">
  		<%=mensaje %> 
	</div>
<%	} %>
<form id="forma" name="forma" action="cambiaClave?Accion=1&Folio=<%=folio%>" method="post">
	<table style=width:40%;align:center class="table table-condensed">
		<tr>
			<td><b><spring:message code='aca.Usuario'/></b></td>
			<td><%=usuario%></td>
		</tr>
		<tr>
			<td><b>New Password:</b></td>
			<td><input type="password" id="clave1" name="clave1" /></td>
		</tr>
		<tr>
			<td colspan="2"><input class="btn btn-primary" type="button" value="Save" onclick="verifica();" /></td>
		</tr>
	</table>
</form>
</div>