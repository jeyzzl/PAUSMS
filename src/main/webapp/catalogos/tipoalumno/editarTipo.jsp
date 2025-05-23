<%@ page import="aca.catalogo.spring.CatTipoAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>


<script type="text/javascript">	

	function Grabar() {		
		if (document.frmtipoalumno.TipoId.value != ""
				&& document.frmtipoalumno.NombreTipo != "") {			
			document.frmtipoalumno.submit();
		} else {
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
</script>
<%
	CatTipoAlumno tipo 		= (CatTipoAlumno) request.getAttribute("tipo");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<div class="container-fluid">
	<h1><spring:message code="catalogos.tipoAlumno.Titulo2"/></h1>
	<form action="grabarTipo" method="post" name="frmtipoalumno" target="_self">
	<div class="alert alert-info">
		<a href="tipoalumno" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<div class="form-group">
		<label><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="TipoId" type="text" id="TipoId" maxlength="2" value="<%=tipo.getTipoId()%>" readonly>
		<br>
		<label><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="input input-xlarge form-control" name="NombreTipo" type="text" id="NombreTipo" maxlength="40" value="<%=tipo.getNombreTipo()%>">
		<br>
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
	</div>
	</form>
</div>