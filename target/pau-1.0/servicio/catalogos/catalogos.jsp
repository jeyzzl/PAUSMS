<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="aca.ssoc.spring.SsocRequisito"%>

<%
	List<SsocRequisito> lisRequisitos = (List<SsocRequisito>) request.getAttribute("lisRequisitos");
%>

<script>
	
	function Borrar(requisitoId){
		if (confirm("¿Esta seguro que desea borrar el registro?")){
			document.location.href='borrar?RequisitoId='+requisitoId;
		}
	}
	
</script>
<body>
<div class="container-fluid">
	<h1>Catálogo de Requisitos</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editar"><spring:message code="aca.Anadir"/></a>
	</div>
	<table class="table table-sm" width="40%">
	<tr>
		<th width="12%" class='text-center'><spring:message code="aca.Operacion"/></th>
		<th width="5%" class='text-center'><spring:message code="aca.Numero"/></th>
		<th width="77%" class='text-center'>Nombre</th>
		<th width="8%" class='text-center'>Orden</th>
	</tr>
<%
	int row = 0;
	for (SsocRequisito requisito : lisRequisitos){
		row++;
		
%>
	<tr >
		<td style="text-align: center">
		  	<a href="editar?RequisitoId=<%=requisito.getRequisitoId()%>"  title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
      		<a href="javascript:Borrar('<%=requisito.getRequisitoId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
		</td>
		<td class="text-center"><%=row%></td>
		<td><%=requisito.getRequisitoNombre() %></td>
		<td class="text-center"><%=requisito.getOrden()%></td>
	</tr>

<%
	}
%>
	</table>
</div>
</body>