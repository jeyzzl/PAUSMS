<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>

<%
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");

	// Lista de cargas
	ArrayList<aca.carga.Carga> lisCargas 	= CargaU.getListAllActivas(conEnoc, " ORDER BY CARGA_ID");
%>
<div class="container-fluid">
	<h2>Loads</h2>
	<div class="alert alert-info">
		<a href="estado?Accion=0" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="documento" method="post" action="elige_bloque">
	<table class="table table-sm" style="width:100%">
	<tr>
		<td><h2>Select Load</h2></td>
	</tr>
	<tr>
		<td>
			<select name="CargaId" class="form-select" style="width:400px;">
		<%
			for (aca.carga.Carga carga : lisCargas){
		%>
				<option value="<%=carga.getCargaId()%>"><%=carga.getCargaId()%> - <%=aca.carga.CargaUtil.getNombreCarga(conEnoc, carga.getCargaId())%></option>
		<%	}%>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<br>
			<input type="submit" class="btn btn-primary" name="Aceptar" value="Accept">				
		</td>
	</tr>
	</table>
	</form>
</div>

<%@ include file="../../cierra_enoc.jsp"%>
