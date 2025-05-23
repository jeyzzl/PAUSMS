<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumAtuendo"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
<script type="text/javascript">
	function borrar(atuendoId){
		if (confirm("Are you sure you want to delete the attire?")){
			document.location.href = "borrar?AtuendoId="+atuendoId;
		}
	}
</script>
</head>
<%
	List<AlumAtuendo> lisAtuendos = (List<AlumAtuendo>) request.getAttribute("listaAtuendos");
%>
<body>
<div class="container-fluid">
	<h2>Attire List</h2>
	<div class="alert alert-info">
		<a href="grabaAtuendo" class="btn btn-primary btn-sm">New</a>
	</div>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th width="3%">No.</th>
			<th>Op.</th>
			<th>Description</th>
			<th>Price</th>
		</tr>
	</thead>
	<tbody>	
<%
	int cont = 1;
	for(AlumAtuendo atuendo : lisAtuendos){
%>
		<tr>
			<td>
				<%=cont%>
			</td>
			<td>
				<a href="grabaAtuendo?AtuendoId=<%=atuendo.getAtuendoId()%>" class="fas fa-edit" title="<spring:message code="aca.Modificar"/>"></a>&nbsp;
				<a href="javascript:borrar('<%=atuendo.getAtuendoId()%>')" class="fas fa-trash-alt" title="<spring:message code="aca.Eliminar"/>"></a>
			</td>
			<td><%=atuendo.getDescripcion()%></td>
			<td><%=atuendo.getPrecio()%></td>
		</tr>
<%
		cont++;
	}
%>	
	</tbody>			
	</table>
</div>
</body>
</html>
