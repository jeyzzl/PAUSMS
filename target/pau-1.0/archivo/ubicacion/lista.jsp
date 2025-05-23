<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchUbicacion"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	int cont = 1;
	List<ArchUbicacion> lisUbicacion 	= (List<ArchUbicacion>) request.getAttribute("lisUbicacion");
	HashMap<String,String> mapaUsados 	= (HashMap<String,String>) request.getAttribute("mapaUsados");
%>
<script type="text/javascript">
	function Borrar(UbicacionId) {
		if (confirm("You want to delete the location?" ) == true) {
			document.location = "borrar?UbicacionId="+UbicacionId;
		}
	}
</script>
<div class="container-fluid">
	<h2>Location Catalog</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="editar" style="text-align:center;" class="btn btn-primary"><i class="icon-white icon-plus"></i> New Location</a>
	</div>
	<br>
	<table class="table table-sm" width="100%">
	<thead class="rable-dark">
	<tr>
		<th width="10%">Op.</th>
		<th width="10%">#</th>
		<th width="80%">Location</th>
	</tr>
	</thead>
	<tbody>	
<%
	for(ArchUbicacion ubicacion : lisUbicacion){
		String total = "0";
		if ( mapaUsados.containsKey(ubicacion.getUbicacionId()) ){
			total = mapaUsados.get(ubicacion.getUbicacionId());
		}
%>		
	<tr>
		<td>				
			<a href="editar?UbicacionId=<%=ubicacion.getUbicacionId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
<%		if (total.equals("0")){%>			
			<a href="javascript:Borrar('<%=ubicacion.getUbicacionId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%		} %>			
		</td>
		<td>
			<%=cont%>
		</td>
		<td><%=ubicacion.getUbicacionNombre()%></td>
		<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
	</tr>
<%
			cont++;
		}
%>	
	</tbody>		
	</table>
</div>