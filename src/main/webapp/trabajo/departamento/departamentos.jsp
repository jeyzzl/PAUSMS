<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(DeptId) {
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			document.location.href = "borrarDepartamento?DeptId="+DeptId;
		} 
	}
</script>
<%
	List<TrabDepartamento> lisDepartamento	= (List<TrabDepartamento>) request.getAttribute("lisDepartamentos");
	HashMap<String,String> mapaCatPorDept 	= (HashMap<String,String>) request.getAttribute("mapaCatPorDept");
%>
<body>
<div class="container-fluid">
	<h2>CTP Departments Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarDepartamento"><spring:message code="aca.Anadir"/></a>
	</div>
	
	<table class="table table-sm table-bordered">  
	<thead class="table-info">	
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="20%">Department</th>
		<th width="30%">Details</th>
		<th width="10%">Status</th>
		<th width="10%">Director</th>
		<th width="10%">Categories</th>
	</thead>
<%
	for (TrabDepartamento departamento : lisDepartamento){
		String total = "0";
		if(mapaCatPorDept.containsKey(departamento.getDeptId())){
			total = mapaCatPorDept.get(departamento.getDeptId());
		}
		String estado = departamento.getEstado();
%>
	<tr>
		<td style="text-align: left">
			<a  href="editarDepartamento?DeptId=<%=departamento.getDeptId()%>" title="<spring:message code="aca.Editar"/>"><i class="fas fa-edit"></i></a>&nbsp;
<%	if(total.equals("0")){ %>
	    	<a href="javascript:Borrar('<%=departamento.getDeptId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<% }%>
		</td>
		<td align="left"><%=departamento.getDeptId()%></td>
		<td><%=departamento.getNombre() %></td>
		<td><%=departamento.getDetalles()%></td>
		<td><%=estado.equals("A")?"Active":"Inactive"%></td>
		<td><%=departamento.getCodigo_personal()%></td>
		<td align="center">
			<a href="categorias?DeptId=<%=departamento.getDeptId()%>"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></a>		
		</td>
	</tr>
<%
	}				
%>
	</table>
</div>
</body>