<%@ page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<head>
	<script type="text/javascript">
		function Borrar(catId, deptId) {
			if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
				document.location = "borrarCategoria?DeptId="+deptId+"&CatId="+catId;
			}
		}
	</script>
</head>
<%

	String deptId = (String)request.getAttribute("deptId");
	List<TrabCategoria> lisCategorias = (List<TrabCategoria>)request.getAttribute("lisCategorias");
	
	HashMap<String,String> mapaPorCat		= (HashMap<String,String>)request.getAttribute("mapaPorCat");
%>
<body>
<div class="container-fluid">
	<h2>CTP Categories Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="departamentos"><i class="fas fa-arrow-left"></i></a>
 		<a class="btn btn-primary" href="editarCategoria?DeptId=<%=deptId%>">Add</a>
	</div>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th width="10%"><spring:message code="aca.Operacion"/></th>
			<th width="10%"><spring:message code="aca.Clave"/></th>
			<th width="70%"><spring:message code="aca.Nombre"/></th>
			<th width="10%"><spring:message code="aca.Status"/></th>
		</tr>
	</thead>
<%
	for(TrabCategoria categoria : lisCategorias){
		String estado = categoria.getEstado();
%>
		<tr>
			<td style="text-align: center">
				<a href="editarCategoria?CatId=<%=categoria.getCategoriaId()%>&DeptId=<%=deptId%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
				&nbsp;
				<% if (mapaPorCat.get(categoria.getCategoriaId()) == null){ %>
				<a href="javascript:Borrar('<%=categoria.getCategoriaId()%>','<%=categoria.getDeptId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
				<% } %>
			</td>
			<td align="center"><%=categoria.getCategoriaId() %></td>
			<td><%=categoria.getNombreCategoria() %></td>
			<td><%=estado.equals("A")?"Active":"Inactive" %></td>
		</tr>
<%
	}
%>
	</table>
</div>
</body>