<%@ page import="aca.catalogo.spring.ResRazones"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Razon" scope="page" class="aca.residencia.ResRazon" />

<html>
<head>
	<script type="text/javascript">		
		function Grabar() {
			if (document.frmrazon.Razon.value != ""
					&& document.frmrazon.Descripcion != "") {
				document.frmrazon.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}
	</script>
</head>
<%
	// Declaracion de variables	
	ResRazones resRazon = (ResRazones) request.getAttribute("resRazon");
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje"); 
%>
<body>
	<div class="container-fluid">
	<h1><spring:message code="catalogos.razones.Titulo"/></h1>
	<form action="grabarRazon" method="post" name="frmrazon">
	<div class="alert alert-info">
		<a href="razon" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>		
	<div class="form-group">
		<label><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="Razon" type="text" id="Razon" maxlength="3" value="<%=resRazon.getRazon()%>" readonly>
		<br>
		<label><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="input input-xlarge form-control" name="Descripcion" type="text" id="Descripcion" value="<%=resRazon.getDescripcion()%>" maxlength="40">
		<br>
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
	</div>		
		</form>
	</div>
</body>
</html>