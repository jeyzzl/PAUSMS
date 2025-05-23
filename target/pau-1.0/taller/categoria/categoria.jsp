<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecCategoria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<script type="text/javascript"> 
		function Borrar(CategoriaId) {
			if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
				document.location = "borrar?CategoriaId="+CategoriaId;
			}
		}
 	</script>
</head>
<%
	/* Lista de las categorias */
	List<BecCategoria> lisCategoria 			= (List<BecCategoria>)request.getAttribute("lisCategoria");
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaCategorias 		= (HashMap<String,String>)request.getAttribute("mapaCategorias");
	HashMap<String,String> mapaTotalAlumnos 	= (HashMap<String,String>)request.getAttribute("mapaTotalAlumnos");
	
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h1>Categorías</h1>
	<div class="alert alert-info">
		<a href="editar" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>	
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info"> 
	<tr>
		<th><spring:message code="aca.Operacion"/></th>
		<th>Categoría Id</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th><spring:message code='aca.Usuario'/></th>
		<th>Estado</th>
		<th>PDF</th>
		<th class="right">#Puestos</th>
		<th class="right">#Alumnos</th>
	</tr>
	</thead>	
<%				
	int row = 0;
	for (BecCategoria categoria: lisCategoria) {
		row++;	
		String numCategorias = "0";
		if (mapaCategorias.containsKey(categoria.getCategoriaId())){
			numCategorias = mapaCategorias.get(categoria.getCategoriaId());
		}
		
		String numAlumnos = "0";
		if (mapaTotalAlumnos.containsKey(categoria.getCategoriaId())){
			numAlumnos = mapaTotalAlumnos.get(categoria.getCategoriaId());
		}
		
		String usuarioNombre = "-";
		if (mapaMaestros.containsKey(categoria.getUsuario())){
			usuarioNombre = mapaMaestros.get(categoria.getUsuario());
		}
		
		String pdf = "-";
		if(categoria.getEstado().equals("A")){
			pdf = categoria.getPdf();
		}
%>
	<tr>
		<td style="text-align: center">
			<a href="editar?CategoriaId=<%=categoria.getCategoriaId()%>" title="Modificar"><i class="fas fa-pencil-alt"></i></a>&nbsp;
<% 		if (numCategorias.equals("0")){%>
			<a href="javascript:Borrar('<%=categoria.getCategoriaId()%>')" title="Eliminar"><i class="fas fa-trash" ></i></a>
<% 		}%>	
		</td>				
		<td align="center"><%=categoria.getCategoriaId()  %></td>
		<td><%=categoria.getCategoriaNombre() %></td>
		<td><%=usuarioNombre%></td>
		<td><%=categoria.getEstado().equals("A")?"Activo":"Inactivo" %></td>
		<td><%=pdf%></td>
		<td class="right"><%=numCategorias%></td>
		<td class="right"><%=numAlumnos%></td>
	</tr>
<%	}  %>
	</table>
</div>
</body>
</html>
