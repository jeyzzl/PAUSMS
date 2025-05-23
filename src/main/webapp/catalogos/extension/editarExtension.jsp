<%@ page import="aca.catalogo.spring.CatExtension"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">		
			function Grabar(){
				if(document.frmextension.ExtensionId.value!="" 
						&& document.frmextension.NombreExtension.value!="" ){			
					document.frmextension.submit();			
				}else{
					alert("<spring:message code='aca.JSCompletar'/>");
				}
			}
		</script>
	</head>
<%
	// Declaracion de variables	
	CatExtension extension  = (CatExtension) request.getAttribute("extension");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.extension.Titulo2"/></h1>
	<form action="grabarExtension" method="post" name="frmextension" target="_self">
	<div class="alert alert-info">
		<a href="extension" class="btn btn-primary" ><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>

	<div class="row container-fluid">
	
	<div class="span3 col">
		<label for="aca.Clave"><spring:message code="aca.Clave"/></label>
		<input name="ExtensionId" type="text" class="input input-mini form-control" id="ExtensionId" size="3" maxlength="3" required value="<%=extension.getExtensionId()%>" readonly>
		<br>
		<label for="aca.Nombre"><spring:message code="aca.Nombre"/></label>	
		<input name="NombreExtension" type="text" class="input input-xlarge form-control" id="NombreExtension" required value="<%=extension.getNombreExtension()%>" size="40" maxlength="50">
		<br>
		<label for="catalogos.extension.Referente"><spring:message code="catalogos.extension.Referente"/></label>			
		<input name="Referente" type="text" class="input input-xlarge form-control" id="Referente" required value="<%=extension.getReferente()%>" size="40" maxlength="60">
		<br>
	</div>
    <div class="span3 col">
		
		<label for="catalogos.extension.Direccion"><spring:message code="aca.Direccion"/></label>					
		<input name="Direccion" type="text" class="input input-xlarge form-control" id="Direccion" required value="<%=extension.getDireccion()%>" size="40" maxlength="80">
		<br>
		<label for="catalogos.extension.Colonia"><spring:message code="catalogos.extension.Colonia"/></label>
		<input name="Colonia" type="text" class="input input-xlarge form-control" id="Colonia" required value="<%=extension.getColonia()%>" size="40" maxlength="30">
		<br>
		<label for="catalogos.extension.CodigoPostal"><spring:message code="catalogos.extension.CodigoPostal"/></label>			
		<input name="CodPostal" type="text" class="input input-xlarge form-control" id="CodPostal" required value="<%=extension.getCodPostal()%>" size="40" maxlength="30">				
		<br>
	</div>
	
    <div class="span3 col">
		<label for="aca.Telefono"><spring:message code="aca.Telefono"/></label>			
		<input name="Telefono" type="text" class="input input-xlarge form-control" id="Telefono" required value="<%=extension.getTelefono()%>" size="40" maxlength="60">				
		<br>
		<label for="catalogos.extension.Fax"><spring:message code="catalogos.extension.Fax"/></label>				
		<input name="Fax" type="text" class="input input-xlarge form-control" id="Fax" required value="<%=extension.getFax()%>" size="40" maxlength="60">
		<br>
		<label for="aca.Email"><spring:message code="aca.Email"/></label>
		<input name="Email" type="text" class="input input-xlarge form-control" id="Email" required value="<%=extension.getEmail()%>" size="40" maxlength="60">
	</div>	
	<br>
	</div>
	<div class="alert alert-info">
		<a  href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a> &nbsp;
	</div>
	</form>
 </div>
</body>
</html>