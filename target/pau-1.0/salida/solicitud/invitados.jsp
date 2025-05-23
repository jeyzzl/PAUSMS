<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>

<script type="text/javascript">
	function Borrar(salidaId, folio) {
		if (confirm("Estas seguro de eliminar el registro: " + folio) == true) {
			document.location = "borrarInvitado?Salida="+ salidaId + "&Folio=" + folio;
		}
	}
</script>

<%
	String codigoEmpleado 	= (String)session.getAttribute("codigoEmpleado");
	String salidaId 		= (String)request.getAttribute("salidaId");
	
	List<aca.salida.spring.SalInvitado> lisInvitados	= (List<aca.salida.spring.SalInvitado>)request.getAttribute("lisInvitados");
	
%>
<div class="container-fluid">
	<h2>Listado de invitados</h2>
	<div class="alert alert-info">
		<form action="grabarInvitado">
			<a class="btn btn-primary" href="solicitud"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			<input type="hidden" name="Salida" value="<%=salidaId%>">
			<input type="text" name="Nombre" placeholder="Nombre" required="required">
			<select name="Tipo">
				<option value="I">Invitado</option>
				<option value="C">Consejero</option>
			</select>
			<input type="submit" value="Añadir">
		</form>
	</div>
	<table class="table table-sm" style="text-align: left"; style="width:50%; margin:0 auto;">
	<tr>
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th width="10%"><spring:message code="aca.Numero"/></th>
		<th width="35%">Nombre</th>
		<th width="30%">Tipo</th>
	</tr>
<%
	int con = 0;
	for(aca.salida.spring.SalInvitado invitado : lisInvitados) {
		con++;
%>
	<tr>
		<td>
			<a href="javascript:Borrar('<%=invitado.getSalidaId()%>','<%=invitado.getFolio()%>')" class="fas fa-trash-alt" title="Eliminar"></a>
		</td>
		<td align="center"><%=con%></td>
		<td align="center"><%=invitado.getNombre()%></td>
		<td align="center"><%=invitado.getTipo().equals("I")?"Invitado":"Consejero"%></td>
	</tr>
<%
	}
%>
	</table>
</div>
