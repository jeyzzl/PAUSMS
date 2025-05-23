<%@ page import="aca.catalogo.spring.CatPatrocinador"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Grabar() {
				if (document.frmpatrocinador.PatrocinadorId.value != ""
						&& document.frmpatrocinador.NombrePatrocinador != "" && document.frmpatrocinador.DetallesPatrocinador != "") {
					document.frmpatrocinador.submit();
				} else {
					alert("<spring:message code="aca.JSCompletar"/> ");
				}
			}
		</script>
	</head>
<%
	// Declaracion de variables	
	CatPatrocinador patrocinador = (CatPatrocinador) request.getAttribute("patrocinador");
String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.instituciones.Titulo2"/></h1>
	<form action="grabarPatrocinador" method="post" name="frmpatrocinador" target="_self">
	<div class="alert alert-info">
		<a href="patrocinador" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="form-group">
		<label for="Clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="PatrocinadorId" type="text" id="PatrocinadorId" maxlength="3" value="<%=patrocinador.getPatrocinadorId()%>" readonly>
		<br>
		<label for="Nombre"><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="input input-xlarge form-control" name="NombrePatrocinador" type="text" id="NombrePatrocinador" value="<%=patrocinador.getNombrePatrocinador()%>" maxlength="50">
		<br>
		<label for="Detalles"><strong>Details</strong></label>
		<input class="input input-xlarge form-control" name="DetallesPatrocinador" type="text" id="DetallesPatrocinador" value="<%=patrocinador.getDetalles()%>" maxlength="50">
		<br>
		<label for="Direccion"><strong>Address</strong></label>
		<input class="input input-xlarge form-control" name="DireccionPatrocinador" type="text" id="DireccionPatrocinador" value="<%=patrocinador.getDireccion()%>" maxlength="50">
		<br>
		<label for="Telefono"><strong>Phone Number</strong></label>
		<input class="input input-xlarge form-control" name="TelefonoPatrocinador" type="text" id="TelefonoPatrocinador" value="<%=patrocinador.getTelefono()%>" maxlength="50">
		<br>
		<label for="Email"><strong>Email</strong></label>
		<input class="input input-xlarge form-control" name="EmailPatrocinador" type="text" id="EmailPatrocinador" value="<%=patrocinador.getEmail()%>" maxlength="50">
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