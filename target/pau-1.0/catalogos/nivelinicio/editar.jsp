<%@ page import="aca.catalogo.spring.CatNivelInicio"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script type="text/javascript">		
		function Grabar() {
			if (document.frmNivelInicio.NivelInicioId.value != "" && document.frmNivelInicio.NombreNivel != "") {
				document.frmNivelInicio.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}	
	</script>
</head>
<%
	String nivelInicioId 		    = request.getParameter("NivelInicioId")==null?"0":request.getParameter("NivelInicioId");
	String mensaje	 			    = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CatNivelInicio catNivelInicio 	= (CatNivelInicio) request.getAttribute("catNivelInicio");	
%>
<body>
<div class="container-fluid">
	<h2>Edit Entry Level</h2>
	<form name="frmNivelInicio" action="grabar" method="post">	
	<div class="alert alert-info">
		<a href="nivelinicio" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<div class="form-group">
		<label for="clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input name="NivelInicioId" type="text" id="NivelInicioId" class="form-control" maxlength="3" required value="<%=catNivelInicio.getNivelInicioId()%>" readonly>
	    <br>
	    <label for="nombre"><strong>Enrty Level</strong></label>
	    <input name="NombreNivel" type="text" id="NombreNivel" class="form-control" maxlength="40" value="<%=catNivelInicio.getNombreNivel()%>">
	    <br>
	    <label for="Corto"><strong>Short name</label>
	    <input name="NombreCorto" type="text" id="NombreCorto" class="form-control" maxlength="40" value="<%=catNivelInicio.getNombreCorto()%>">
	    <br>
	</div>
	<div class="alert alert-info">			
		<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Guardar"/></a>
	</div>	
	</form>
</div>
</body>
</html>