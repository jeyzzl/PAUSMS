<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatDescuento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(descuentoId){		
		if (confirm("¿Deseas borrar el registro?")){
			document.location.href="borrar?DescuentoId="+descuentoId;
		}
	}	
</script>
<%
	String mensaje		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<CatDescuento> lisDescuentos		= (List<CatDescuento>)request.getAttribute("lisDescuentos");	
	HashMap<String,String> mapaDescuentos	= (HashMap<String,String>)request.getAttribute("mapaDescuentos");
%>
<body>
<div class="container-fluid">
	<h2>Catálogo de Descuentos</h2>	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion"><i class="fas fa-plus"></i><spring:message code='aca.Añadir'/></a>
	</div>
<%	if (!mensaje.equals("-")){%>	
	<%=mensaje %>
<%	} %>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>Op</th>
			<th>Descuento Id</th>
			<th>Descuento Nombre</th>
			<th><spring:message code='aca.Usuario'/></th>
		</tr>
	</thead>
<% 
	for(CatDescuento descuento : lisDescuentos){
		String total = "0";
		if (mapaDescuentos.containsKey(descuento.getDescuentoId())){
			total = mapaDescuentos.get(descuento.getDescuentoId());
		}
%>
		<tr>
			<td>
				<a href="accion?DescuentoId=<%=descuento.getDescuentoId()%>"><i class="fas fa-edit"></i></a>
<% 	if (total.equals("0")){%>				
				<a href="javascript:Borrar('<%=descuento.getDescuentoId()%>')"><i class="fas fa-trash-alt"></i></a>
<%	} %>				
			</td>
			<td align="right"><%=descuento.getDescuentoId()%></td>
			<td><%=descuento.getDescuentoNombre()%></td>
			<td><%=descuento.getUsuarios()%></td>
		</tr>
<%}%>
	</table>
</div>
</body>