<%@ page import="aca.catalogo.spring.CatCiudad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Nuevo() {
				document.frmciudad.CiudadId.value = " ";
				document.frmciudad.NombreCiudad.value = " ";
				document.frmciudad.Accion.value = "1";
				document.frmciudad.submit();
			}
		
			function Grabar() {
				if (document.frmciudad.CiudadId.value != "" && document.frmciudad.NombreCiudad != "") {
					document.frmciudad.Accion.value = "2";
					document.frmciudad.submit();
				} else {
					alert("<spring:message code='aca.JSCompletar'/>");
				}
			}
		
			function Modificar() {
				document.frmciudad.Accion.value = "3";
				document.frmciudad.submit();
			}
		
			function Borrar() {
				if (document.frmciudad.CiudadId.value != "") {
					if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
						document.frmciudad.Accion.value = "4";
						document.frmciudad.submit();
					}
				} else {
					alert("<spring:message code="aca.JSEscribirClave"/>");
					document.frmciudad.CiudadId.focus();
				}
			}
		
			function Consultar() {
				document.frmciudad.Accion.value = "5";
				document.frmciudad.submit();
			}
		</script>
	</head>
<%
	String paisId 	 = (String)session.getAttribute("paisId");
	String estadoId  = (String)session.getAttribute("estadoId");
	CatCiudad ciudad = (CatCiudad)request.getAttribute("ciudad");
	
	String mensaje 	 = (String)request.getAttribute("mensaje");
	
	if(mensaje.equals("1")){
		mensaje = "Saved";
	}else if(mensaje.equals("2")){
		mensaje = "Updated";
	}

%>
<body>
	<div class="container-fluid">
	<h1><spring:message code="catalogos.paises.Ciudades"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="ciudades?PaisId=<%=paisId%>&EstadoId=<%=estadoId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(!mensaje.equals("0")){%>
	<div class="alert alert-success">
		<%=mensaje%>
	</div>
<% 	}%>
		<form action="grabarCiudad" method="post" name="frmciudad" target="_self">
			<input type="hidden" name="PaisId" value="<%=ciudad.getPaisId()%>">
			<input type="hidden" name="EstadoId" value="<%=ciudad.getEstadoId()%>">
			<label><spring:message code="aca.Clave"/>:</label>
			<input class="form-control" name="CiudadId" type="text" id="CiudadId" size="3" maxlength="3" value="<%=ciudad.getCiudadId()%>" readonly="readonly"><br>
			<label><spring:message code="aca.Nombre"/></label>
			<input class="form-control" name="NombreCiudad" type="text" id="NombreCiudad" value="<%=ciudad.getNombreCiudad()%>" size="40" maxlength="40"><br>
			<div class="alert alert-info">
				<input class="btn btn-info" type="submit" value="<spring:message code='aca.Guardar'/>">
			</div>
		</form>
	</div>
</body>
</html>