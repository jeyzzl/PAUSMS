<%@ page import= "aca.rol.spring.Rol"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String rolId 	= request.getParameter("rolId")==null?"0":request.getParameter("rolId");
	String mensaje 	= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	Rol rol 		= (Rol)request.getAttribute("rol");
%>
<html>
<head>
<style>		
	body{
		background : white;
	}
</style>
<body>
<div class="container-fluid" >
	<h1>Add Roles</h1>		
	<div class="alert alert-info" align="left">
		<a href="roles" class="btn btn-info"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if(mensaje.equals("1")){%>
	<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
	}if(mensaje.equals("2")){%>
	<div class="alert alert-danger"></div><h3><spring:message code='aca.NoSePudoGuardar'/></h3></div>
<%	
	}if(mensaje.equals("3")){%>
	<div class="alert alert-info"><h3><spring:message code='aca.SeModifico'/></h3></div>
<%	
	}if(mensaje.equals("4")){%>
	<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoModificar'/></h3></div>
<% 	}%>	  
	<form name="forma" action="grabar" method="post">			
	<table style="margin: 0 auto;" class="table table-nohover">
	<tr>
		<td>Id*</td>
		<td>
			<input type="text" class="input-xlarge" name="rolId" id="rolId" value="<%=rol.getRolId()%>" width="50%" readonly/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="aca.Nombre"/>*</td>
		<td>
			<input type="text" class="input-xlarge" name="nombre" id="nombre" maxlength="100" value="<%=rol.getRolNombre()%>" width="50%"/>
		</td>
	</tr>
	</table>
	<table style="margin: 0 auto;" class="table table-nohover">
	<tr>
		<td>
			<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="fas fa-check"></i> Save</a>
		</td>
	</tr>
	</table>
	</form>
</div>		
</body>
</html>
<script>
	function Guardar() {		
		if (forma.nombre.value != "") {			
			document.forma.submit();
		}else{
			alert("¡Fill out all the fields Properly (*) !");
		}
	}
</script>