<%@ page import="aca.catalogo.spring.CatRecogida"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script type="text/javascript">		
		function Grabar() {
			if (document.frmRecogida.RecogidaId.value != "" && document.frmRecogida.Nombre != "" && document.frmRecogida.NombreCorto.value.length <= 6) {
				document.frmRecogida.submit();
			} else {
				alert("Fill out the entire form. Short name up to 6 characters.");
			}
		}	
	</script>
</head>
<%
	String recogidaId 			= request.getParameter("RecogidaId")==null?"0":request.getParameter("RecogidaId");
	String mensaje	 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CatRecogida catRecogida 	= (CatRecogida) request.getAttribute("catRecogida");	
%>
<body>
<div class="container-fluid">
	<h2>Pickup Location <small class="text-muted">(<%=catRecogida.getNombre()%>)</small></h2>
	<form name="frmRecogida" action="grabar" method="post">	
	<div class="alert alert-info">
		<a href="recogida" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<div class="form-group">
		<label for="clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input name="RecogidaId" type="text" id="RecogidaId" class="form-control" maxlength="3" required value="<%=catRecogida.getRecogidaId()%>" readonly>
	    <br>
	    <label for="nombre"><strong>Pickup Location</strong></label>
	    <input name="Nombre" type="text" id="Nombre" class="form-control" maxlength="40" value="<%=catRecogida.getNombre()%>">
	    <br>
	    <label for="Corto"><strong>Short name</label>
	    <input name="NombreCorto" type="text" id="NombreCorto" class="form-control" maxlength="40" value="<%=catRecogida.getNombreCorto()%>">
	    <br>
	</div>
	<div class="alert alert-info">			
		<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Guardar"/></a>
	</div>	
	</form>
</div>
</body>
</html>