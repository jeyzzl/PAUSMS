<%@ page import="aca.catalogo.spring.CatTipoCal"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Grabar() {
		if (document.frmtipocal.TipoCalId.value != "" && document.frmtipocal.NombreTipoCal != "") {
			document.frmtipocal.submit();
		} else {
			alert("<spring:message code="aca.JSCompletar"/> ");
		}
	}	
</script>
<%
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CatTipoCal tipo 	= (CatTipoCal) request.getAttribute("tipo");	
%>
<div class="container-fluid">
	<h1><spring:message code="catalogos.tipoCalif.Titulo2"/></h1>
	<form action="grabarTipo" method="post" name="frmtipocal" target="_self">	
	<div class="alert alert-info">
		<a href="tipocal" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>

	<div class="form-group">
		<label><strong><spring:message code="aca.Clave"/></strong></label>  <% if(tipo.getTipoCalId().equals("")){ %><i>(Type in the next key)</i><% } %>
		<input class="form-control" name="TipoCalId" type="text" id="TipoCalId" size="3" maxlength="1" value="<%=tipo.getTipoCalId()%>">
		<br>
		<label><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="form-control" name="NombreTipoCal" type="text" id="NombreTipoCal" value="<%=tipo.getNombreTipoCal()%>" size="40" maxlength="30">
		<br>
		<label><strong><spring:message code="aca.NombreCorto"/></strong></label>
		<input class="form-control" name="NombreCorto" type="text" id="NombreCorto" value="<%=tipo.getNombreCorto()%>" size="40" maxlength="10">
		<br>
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>
	</div>
	</form>
</div>