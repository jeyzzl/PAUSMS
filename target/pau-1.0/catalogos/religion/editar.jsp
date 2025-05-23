<%@ page import="aca.catalogo.spring.CatReligion"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script type="text/javascript">		
		function Grabar() {
			if (document.frmReligion.ReligionId.value != "" && document.frmReligion.NombreReligion != "") {
				document.frmReligion.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}	
	</script>
</head>
<%
	String religionId 			= request.getParameter("ReligionId")==null?"0":request.getParameter("ReligionId");
	String mensaje	 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CatReligion catReligion 	= (CatReligion) request.getAttribute("catReligion");	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="catalogos.religion.Titulo2"/></h2>
	<form name="frmReligion" action="grabar" method="post">	
	<div class="alert alert-info">
		<a href="religion" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<div class="form-group">
		<label for="clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input name="ReligionId" type="text" id="ReligionId" class="form-control" maxlength="3" required value="<%=catReligion.getReligionId()%>" readonly>
	    <br>
	    <label for="nombre"><strong><spring:message code="aca.Religion"/></strong></label>
	    <input name="NombreReligion" type="text" id="NombreReligion" class="form-control" maxlength="40" value="<%=catReligion.getNombreReligion()%>">
	    <br>
	    <label for="Corto"><strong>Short name</label>
	    <input name="NombreCorto" type="text" id="NombreCorto" class="form-control" maxlength="40" value="<%=catReligion.getNombreCorto()%>">
	    <br>
	</div>
	<div class="alert alert-info">			
		<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Guardar"/></a>
	</div>	
	</form>
</div>
</body>
</html>