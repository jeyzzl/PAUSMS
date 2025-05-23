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
		Asistencia <small>(<%=periodoId%>)
		</small>
	</h2>
	<hr />
	<div style="margin-left: 20px; float: left; background: white;"
		class="alert alert-info ">

		<table style="width:100%" class="table"
			style="width: 700px; border-bottom: 1px solid gray">
			<tr>
				<th colspan=4><font size="3">Registro de asistencia y
						puntualidad ( 10% )</font></th>
			</tr>
			<tr>
				<td><b>Mes:</b></td>
				<td><b>Asistencia</b></td>
				<td><b>Puntualidad</b></td>
			</tr>
		</table>

	</div>

</div>
<script>
	jQuery('.asistencia').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>