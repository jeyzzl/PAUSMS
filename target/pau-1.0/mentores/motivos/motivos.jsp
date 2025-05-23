<%@ page import="java.util.List"%>
<%@ page import="aca.mentores.spring.MentMotivo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">

	function Borrar(motivoId) {
		if (confirm("Are you sure you want to delete this record?")) {
			document.location="borrarMotivos?MotivoId="+motivoId;
			document.frmMotivos.submit();
		}
	}
</script>
<%
	List<MentMotivo> lisMenMotivo	= (List<MentMotivo>) request.getAttribute("lisAll");
%>
<div class="container-fluid">
	<h2>Reasons Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-success" href="editarMotivo">New</a>
	</div>
	<table class="table table-sm table-bordered">
		<thead class="table-info">
			<tr>
				<th width="10%">Operation</th>
				<th width="10%">ID</th>
				<th width="20%">Description</th>
				<th width="60%">Comment</th>
			</tr>
		</thead>
	<%
		for (MentMotivo motivo : lisMenMotivo) {
	%>
	<tr>
		<td align="center">
			<a href="editarMotivo?MotivoId=<%=motivo.getMotivoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-pencil-alt"></i></a>&nbsp;
			<a href="javascript:Borrar('<%=motivo.getMotivoId()%>')" title="Delete"><i class="fas fa-trash" ></i></a>
		</td>
		<td align="center"><%=motivo.getMotivoId()%></td>
		<td><%=motivo.getMotivoNombre()%></td>
		<td><%=motivo.getComentario()%></td>
	</tr>
	<%
		}
	%>
</table>
</div>