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
		if (confirm("¿Esta seguro que desea borrar el atuendo?")){
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
	<h2>Listado de atuendos <a href="grabaAtuendo" class="btn btn-primary btn-sm">Nuevo</a></h2>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th>Op.</th>
			<th>Descripción</th>
			<th>Precio</th>
		</tr>
	</thead>
	<tbody>	
<%
	int cont = 1;
	for(AlumAtuendo atuendo : lisAtuendos){
%>
		<tr>
			<td>
				<%=cont%>&nbsp;&nbsp;
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
