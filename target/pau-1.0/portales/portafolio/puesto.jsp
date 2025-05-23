<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menuPortal.jsp"%>

<%
	String codigoId 		= (String) session.getAttribute("codigoId");
	String periodoId		= (String) session.getAttribute("porPeriodo");
%>

<div class="container-fluid">
	<h2>
		Puesto <small>(<%=periodoId%>)
	</h2>
	<hr />
	<div style="margin-left: 20px; float: left; background: white;"
		class="alert alert-info ">

		<table style="width:100%" class="table"
			style="width: 700px; border-bottom: 1px solid gray">
			<tr>
				<th colspan=4><font size="3">Descripción de Puesto</font></th>
			</tr>
			<tr>
				<td><b><spring:message code="aca.Actividades" /></b></td>
			</tr>
		</table>

	</div>
</div>
<script>
	jQuery('.puesto').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>