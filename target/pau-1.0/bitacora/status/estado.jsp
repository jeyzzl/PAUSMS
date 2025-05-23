<%@ page import="aca.bitacora.spring.BitEstado"%>
<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	int cont = 1;
	List<BitEstado> lisEstados 	= (List<BitEstado>) request.getAttribute("lisEstados");	
%>
<script type="text/javascript"> -->
	function Borrar(EstadoId) {
 		if (confirm("Quieres eliminar este estado ?" ) == true) {
 			document.location = "borrar?EstadoId="+EstadoId;
 		}
 	}
</script>
<div class="container-fluid">
	<h2>Estado</h2>
	<div class="alert alert-info">
		<a href="editar" style="text-align:center;" class="btn btn-primary"><i class="fas fa-plus"></i> Nuevo status</a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Estado</th>
			<th>Catalogo Estado</th>
		</tr>
	</thead>	
<%
		for(BitEstado estado :lisEstados){	
%>		
		<tr>
			<td>
				<%=cont%>&nbsp;&nbsp;
				<a href="editar?EstadoId=<%=estado.getEstado()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
				&nbsp;
				<a href="javascript:Borrar('<%=estado.getEstado()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
			</td>
			<td><%=estado.getEstado()%></td>
			<td><%=estado.getEstadoNombre()%></td>
		</tr>
<%
			cont++;
		}
%>			
	</table>
</div>