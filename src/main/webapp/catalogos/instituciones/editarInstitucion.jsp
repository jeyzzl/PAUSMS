<%@ page import="aca.catalogo.spring.CatInstitucion"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Grabar() {
				if (document.frminstitucion.InstitucionId.value != ""
						&& document.frminstitucion.NombreInstitucion != "") {
					document.frminstitucion.submit();
				} else {
					alert("<spring:message code="aca.JSCompletar"/> ");
				}
			}
		</script>
	</head>
<%
	// Declaracion de variables	
	CatInstitucion institucion = (CatInstitucion) request.getAttribute("institucion");
String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h2>Edit Endorser</h2>
	<form action="grabarInstitucion" method="post" name="frminstitucion" target="_self">
	<div class="alert alert-info">
		<a href="institucion" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="form-group">
		<label for="Clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="InstitucionId" type="text" id="InstitucionId" maxlength="3" value="<%=institucion.getInstitucionId()%>" readonly>
		<br>
		<label for="Nombre"><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="input input-xlarge form-control" name="NombreInstitucion" type="text" id="NombreInstitucion" value="<%=institucion.getNombreInstitucion()%>" maxlength="50">
		<br>
	</div>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
<% if (!mensaje.equals("-")){ out.print(mensaje);}%>
	</div>
	</form>
</div>
</body>
</html>