<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function grabar(){
		if(document.frmPersonal.Usuario.value != "" && document.frmPersonal.Clave.value != ""){
			document.frmPersonal.submit();			
		}else{
			alert(" Complete los campos..¡¡");
		}
	}		
</script>	
<%	
	String codigoUsuario	= (String)request.getAttribute("codigoPersonal");	
	String nombreUsuario	= (String)request.getAttribute("nombre");	
	String msj 				= (String)request.getAttribute("msj");	
	
	Acceso acceso = (Acceso)request.getAttribute("acceso");	
%>
<div class="container-fluid">
	<h2>User Information<small class="text-muted fs-5"> ( <%=codigoUsuario%> - <%=nombreUsuario%> ) </small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary bt-sm" href="usuario?Codigo=<%=codigoUsuario%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmPersonal" action="grabar" method="post">
	<input name="Codigo" type="hidden" value="<%=codigoUsuario%>">
	<table class="table table-bordered table-sm" style="width:50%">	  	
	  	<tr> 
	    	<td width="20%"><strong><spring:message code="aca.Usuario"/>:</strong></td>
	    	<td width="80%"><input name="Usuario" type="text" class="form-control" id="Usuario" value="<%=acceso.getUsuario()%>" size="10" maxlength="15"></td>
	  	</tr>
	  	<tr> 
	    	<td><strong><spring:message code="aca.Clave"/>:</strong></td>
	    	<td><input name="Clave" type="password" id="Clave" class="form-control" value="<%=acceso.getClave()%>" size="10" maxlength="15"></td>
	  	</tr>
	  	<tr>
	    	<td height="20" colspan="2" align="center"><%=msj%></td>
	  	</tr>
	  	<tr>
	    	<td height="26" colspan="2" align="center"><a href="javascript:grabar()" class="btn btn-primary" style="float:left;"><spring:message code="aca.Grabar"/></a></td>
	  	</tr>
	</table>
	</form>
</div>
