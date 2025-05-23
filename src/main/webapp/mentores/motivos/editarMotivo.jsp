<%@ page import="aca.mentores.spring.MentMotivo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Grabar() {
		if (document.frmMotivos.MotivoNombre.value != ""
				&& document.frmMotivos.Comentario.value != "") {
			document.frmMotivos.submit();
		} else {
			alert("Fill out the enitre form!");
		}
	}
</script>
<%
	MentMotivo motivo	= (MentMotivo) request.getAttribute("motivo");
%>
<div class="container-fluid">
	<h1>Reasons Catalog</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="motivos"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form action="grabarMotivos" method="post" name="frmMotivos">
		ID:
		<input name="MotivoId" value="<%=motivo.getMotivoId()%>" type="text" class="form-control" id="MotivoId" size="2" maxlength="2" readonly>
		<br>
		Reason:
		<input name="MotivoNombre" type="text" class="form-control" id="MotivoNombre" size="40" maxlength="40" value="<%=motivo.getMotivoNombre()%>">
		<br>
		Comment:
		<textarea class="form-control" id="Comentario" name="Comentario" cols="50"><%=motivo.getComentario()%></textarea>
		<br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>
		</div>
	</form>
</div>