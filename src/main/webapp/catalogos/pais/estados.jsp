<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatEstado"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<script type="text/javascript">
		function Borrar(estadoId, paisId) {
			if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
				document.location = "borrarEstado?PaisId="+paisId+"&EstadoId="+estadoId;
			}
		}
	</script>
</head>
<%
	String paisId 	= (String)request.getAttribute("paisId");

	List<CatEstado> lisEstados 						= (List<CatEstado>)request.getAttribute("lisEstados");
	HashMap<String,String> mapaTotalCiudadPorEstado = (HashMap<String,String>)request.getAttribute("mapaTotalCiudadPorEstado");
	
	HashMap<String,String> mapaColores 			= new HashMap<String,String>();
	
	mapaColores.put("1", "#f70c0c");
	mapaColores.put("2", " #f79b0c");
	mapaColores.put("3", "#f7f00c");
	mapaColores.put("4", " #3ef70c ");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.paises.Titulo3"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="paises"><i class="fas fa-arrow-left"></i></a>
 		<a class="btn btn-primary" href="editarEstado?PaisId=<%=paisId%>">Add</a>
	</div>
	<table class="table table-sm table-bordered">  
		<tr>
			<th width="5%"><spring:message code="aca.Operacion"/></th>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="40%"><spring:message code="aca.Estado"/></th>
			<th width="30%"><spring:message code="aca.NombreCorto"/></th>	
			<th width="20%">Villages/Cities</th>		
		</tr>
<%	
	for (CatEstado estado : lisEstados){
		
		String color = "red";
		if (mapaColores.containsKey(estado.getSemaforo())){
			color = mapaColores.get(estado.getSemaforo());
		}
		String total = "0";
		if(mapaTotalCiudadPorEstado.containsKey(estado.getEstadoId())){
			total = mapaTotalCiudadPorEstado.get(estado.getEstadoId());
		}
%>
		<tr>
			<td style="text-align: center">
				<a href="editarEstado?EstadoId=<%=estado.getEstadoId()%>&PaisId=<%=paisId%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
				&nbsp;
				<%if(total.equals("0")){ %>
				<a href="javascript:Borrar('<%=estado.getEstadoId()%>','<%=estado.getPaisId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
				<%}%>
			</td>
			<td align="center"><%=estado.getEstadoId()%></td>
			<td><a href="ciudades?PaisId=<%=estado.getPaisId()%>&EstadoId=<%=estado.getEstadoId()%>">
			<%=estado.getNombreEstado()%></a>
			</td>
			<td align="center"><%=estado.getCorto()%></td>
			<td align="center">
			<%if(total.equals("0")){ %>
				<span class="badge bg-warning"><%=total%></span>
			<%}else{%>
				<span class="badge bg-dark"><%=total%></span>
			<%}%>
			</td>			
		</tr>
<%
	}			
%>
	</table>
</div>
</body>
</html>