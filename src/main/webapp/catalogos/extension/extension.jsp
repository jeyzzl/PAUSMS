<%@ page import="java.util.List"%>
<%@ page import="aca.catalogo.spring.CatExtension"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(ExtensionId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location.href = "borrarExtension?ExtensionId="+ExtensionId;
		}
	}
</script>
<%
	List<CatExtension> lisExtension 	= (List<CatExtension>) request.getAttribute("lisExtension");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.extension.Titulo"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarExtension"><spring:message code="aca.Anadir"/></a>
	</div>

	<table style="width:60%">
	<tr align="center">
		<td><font size="3"></font></td>
	</tr>
	<tr align="right">
		<td></td>
	</tr>
	</table>
	<table class="table table-condensed" width="60%">
	<tr>
		<th width="10%"><spring:message code="aca.Operacion"/></th>
		<th width="5%"><spring:message code="aca.Clave"/></th>
		<th width="30%"><spring:message code="catalogos.extension.Extension"/></th>
		<th width="30%"><spring:message code="aca.Direccion"/></th>
		<th width="50%"><spring:message code="aca.Telefono"/></th>
	</tr>
<%
		for (CatExtension extension : lisExtension){
%>
	<tr>
		<td style="text-align: left">
			<a href="editarExtension?ExtensionId=<%=extension.getExtensionId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
			<a href="javascript:Borrar('<%=extension.getExtensionId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
		</td>
		<td align="left"><%=extension.getExtensionId()%></td>
		<td><%=extension.getNombreExtension()%></td>
		<td><%=extension.getDireccion()%></td>
		<td><%=extension.getTelefono()%></td>
	</tr>
<%
	}
%>
	</table>
</div>
</body>