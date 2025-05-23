<%@ page import="java.util.List"%>
<%@ page import="aca.catalogo.spring.CatCiudad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Borrar(PaisId, EstadoId, CiudadId) {
				if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
					document.location = "borrarCiudad?PaisId=" + PaisId
							+ "&EstadoId=" + EstadoId + "&CiudadId=" + CiudadId;
				}
			}
		</script>
	</head>
<%
	String paisId 	 = (String)request.getAttribute("paisId");
	String estadoId  = (String)request.getAttribute("estadoId");

	List<CatCiudad> lisCiudades 	= (List<CatCiudad>)request.getAttribute("lisCiudades");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.paises.Titulo5"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="estados?PaisId=<%=paisId%>"><i class="fas fa-arrow-left"></i></a>
		<a class="btn btn-primary" href="editarCiudad?PaisId=<%=paisId%>&EstadoId=<%=estadoId%>"><spring:message code="aca.Anadir"/></a>
	</div>
	<table class="table table-sm table-bordered">  
	<tr>
		<th width="17%"><spring:message code="aca.Operacion"/></th>
		<th width="9%"><spring:message code="aca.Numero"/></th>
		<th width="74%"><spring:message code="aca.Ciudad"/>/City</th>
	</tr>
<%	
	for (CatCiudad ciudad : lisCiudades){
%>
	<tr>
		<td style="text-align: center">
			<a href="editarCiudad?CiudadId=<%=ciudad.getCiudadId()%>&EstadoId=<%=ciudad.getEstadoId()%>&PaisId=<%=ciudad.getPaisId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
			&nbsp;
			<a href="javascript:Borrar ('<%=ciudad.getPaisId()%>','<%=ciudad.getEstadoId()%>','<%=ciudad.getCiudadId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
		</td>
		<td align="center"><%=ciudad.getCiudadId()%></td>
		<td><%=ciudad.getNombreCiudad()%></td>
	</tr>
<%
	}
%>
	</table>
</div>
</body>
</html>
