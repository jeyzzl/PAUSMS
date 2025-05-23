<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ page import="aca.bitacora.spring.BitRequisito"%>
<%@ page import="aca.bitacora.spring.BitTramite"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	int cont = 1;
	List<BitRequisito> lisRequisito 		= (List<BitRequisito>) request.getAttribute("lisRequisito");
	
	HashMap<String, String> mapaRequisitoAsignados 	= (HashMap<String, String>) request.getAttribute("mapaRequisitoAsignados");
%>

<script type="text/javascript">
	function Borrar(RequisitoId) {
		if (confirm("Quieres eliminar este requisito ?" ) == true) {
			document.location = "borrar?RequisitoId="+RequisitoId;
		}
	}
</script>

<div class="container-fluid">
	<h2>Catálogo Requisito</h2>
	<div class="alert alert-info">
		<a href="editar" style="text-align:center;" class="btn btn-primary"><i class="fas fa-plus"></i> Nuevo requisito</a>
	</div>
	<br>
	<table class="table table-sm table-bordered" style="width:100%">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Nombre</th>
			<th>Tipo</th>
			<th>Descripción</th>
		</tr>
	</thead>
	<tbody>	
<%
		for(BitRequisito requisito : lisRequisito){
			
			boolean requisitoAsignado = false;
			if (mapaRequisitoAsignados.containsKey(requisito.getRequisitoId())){
				requisitoAsignado = true;
			}
%>		
		<tr>
			<td>
				<%=cont++%>&nbsp;&nbsp;
				<a href="editar?RequisitoId=<%=requisito.getRequisitoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
				&nbsp;
<% 			if(!requisitoAsignado){%>
				<a href="javascript:Borrar('<%=requisito.getRequisitoId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
<%          }%>
			</td>
			<td><%=requisito.getNombre()%></td>
			<td><%=requisito.getTipo().equals("M")?"Manual":"Automatico"%></td>
			<td><%=requisito.getComentario()%></td>
		</tr>
<%
		}
%>			
	</tbody>
	</table>
</div>