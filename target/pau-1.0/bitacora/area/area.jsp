<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bitacora.spring.BitArea"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	int cont = 1;
	List<BitArea> lisAreas = (List<BitArea>) request.getAttribute("lisAreas");
	
	HashMap <String, String> mapaMaestros 			= (HashMap <String, String>)request.getAttribute("mapaMaestros");
	HashMap <String, String> mapaUsuariosPorArea 	= (HashMap <String, String>)request.getAttribute("mapaUsuariosPorArea");
%>

<script type="text/javascript">
	function Borrar(AreaId) {
		if (confirm("Quieres eliminar esta area ?" ) == true) {
			document.location = "borrar?AreaId="+AreaId;
		}
	}
</script>

<div class="container-fluid">
	<h2>Cat&aacute;logo de &Aacute;reas</h2>
	<div class="alert alert-info">
		<a href="editar" style="text-align:center;" class="btn btn-primary"><i class="fas fa-plus"></i> Nueva &aacute;rea</a>
	</div>
	<br>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>&Aacute;rea</th>
			<th>Responsable</th>
			<th>Acceso</th>
		</tr>
	</thead>
	<tbody>
<%
	for(BitArea area : lisAreas){
		String acceso = "0";
		if (mapaUsuariosPorArea.containsKey(area.getAreaId())){
			acceso = mapaUsuariosPorArea.get(area.getAreaId());
		}
%>
		<tr>
			<td>
				<%=cont%>&nbsp;&nbsp;
				<a href="editar?AreaId=<%=area.getAreaId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
				<a href="javascript:Borrar('<%=area.getAreaId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
			</td>
			<td><%=area.getAreaNombre()%></td>
			<td><%=mapaMaestros.get(area.getResponsable())%></td>
			<td>
				<a href="acceso?AreaId=<%=area.getAreaId()%>"><i class="fas fa-user"></i></a>&nbsp;
				<a href="acceso?AreaId=<%=area.getAreaId()%>"><span class="badge bg-info"><%=acceso%></span></a>
			</td>
		</tr>
<%
		cont++;
	}
%>			
	</tbody>
	</table>
</div>