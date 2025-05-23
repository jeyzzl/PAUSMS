<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatRegion"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<head>
	<script type="text/javascript">
		function Borrar(regionId, culturalId) {
			if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
				document.location = "deleteRegion?CulturalId="+culturalId+"&RegionId="+regionId;
			}
		}
	</script>
</head>
<%
	String culturalId 			= (String)request.getAttribute("culturalId");
	List<CatRegion> lisRegiones = (List<CatRegion>)request.getAttribute("lisRegiones");
	
%>
<body>
<div class="container-fluid">
	<h2>Provincial Groups Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupos"><i class="fas fa-arrow-left"></i></a>
 		<a class="btn btn-primary" href="editarRegiones?CulturalId=<%=culturalId%>">Add</a>
	</div>
	<table class="table table-sm table-bordered">  
		<tr>
			<th width="5%"><spring:message code="aca.Operacion"/></th>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="40%">Provincial Group</th>		
		</tr>
<%
	for(CatRegion region : lisRegiones){
%>
		<tr>
			<td style="text-align: center">
				<a href="editarRegiones?RegionId=<%=region.getRegionId()%>&CulturalId=<%=culturalId%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
				&nbsp;
				<a href="javascript:Borrar('<%=region.getRegionId()%>','<%=region.getCulturalId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
			</td>
			<td align="center"><%=region.getRegionId()%></td>
			<td align="center"><%=region.getNombreRegion()%></td>
		</tr>
<%
	}
%>
	</table>
</div>
</body>