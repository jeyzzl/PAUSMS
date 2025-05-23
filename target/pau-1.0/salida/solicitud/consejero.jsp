<%@page import="java.util.List"%>
<%@page import="aca.salida.spring.SalConsejero"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(salidaId, folio) {
		if (confirm("¿Estás seguro de eliminar el registro: " + folio+"?") == true) {
			document.location.href = "borrarConsejero?&Salida="+ salidaId + "&Folio=" + folio;
		}
	}
</script>

<%
	String codigoEmpleado 	= (String)session.getAttribute("codigoEmpleado");
	String salidaId 		= request.getParameter("salida");
	String nombreUsuario	= (String)request.getAttribute("nombreUsuario");
	
	List<SalConsejero> lisConsejeros	= (List<SalConsejero>)request.getAttribute("lisConsejeros");		
	
%>
<div class="container-fluid">
	<h2>Listado de consejeros</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="solicitud"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			<input type="text" style="width:300px;" class="form-control" value="<%= nombreUsuario%>" readonly/>&nbsp;
		<a class="btn btn-primary" href="grabarConsejero?salida=<%=salidaId%>"><spring:message code='aca.Añadir'/></a>
	</div>
	<table class="table table-sm" style="text-align: left"; style="width:50%; margin:0 auto;">
	<tr>
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th width="10%"><spring:message code="aca.Numero"/></th>
		<th width="15%">Folio</th>
		<th width="10%"><spring:message code="aca.Clave"/></th>
		<th width="25%">Consejero</th>
		<th width="55%">Trabajo</th>
	</tr>
<%
	int cont=0; 
	for(SalConsejero consejero : lisConsejeros) {
		cont++;
%>
	<tr>
		<td>
			<a href="javascript:Borrar('<%=consejero.getSalidaId()%>','<%=consejero.getFolio()%>')" class="fas fa-trash-alt" title="Eliminar"></a>
		</td>
		<td><%=cont%></td>
		<td><%=consejero.getFolio()%></td>
		<td><%=consejero.getClave()%></td>
		<td><%=consejero.getConsejero()%></td>
		<td><%=consejero.getTrabajo()%></td>
	</tr>
<%
	}
%>
	</table>
</div>