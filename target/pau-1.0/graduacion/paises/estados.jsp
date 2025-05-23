<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatEstado"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String eventoId						= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");	
	String eventoNombre					= (String)request.getAttribute("eventoNombre");
	List<CatEstado> lisEstados 			= (List<CatEstado>) request.getAttribute("lisEstados");		
	HashMap<String,String> mapaTotales	= (HashMap<String,String>) request.getAttribute("mapaTotPorEstados");
%>
<div class="container-fluid">
	<h3>Graduandos por los estados de México<small class="text-muted fs-5">( <%=eventoNombre%> )</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="graduandos?EventoId=<%=eventoId%>"><spring:message code="aca.Regresar"/></a>
	</div>	
	<table style="width:50%" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th width="2%"><spring:message code="aca.Numero"/></th>
		<th width="15%">Estados</th>
		<th width="2%" class="text-end"><spring:message code='aca.Cantidad'/></th>
	</tr>
	</thead>
<%
	int cont = 1;
	int total = 0;
	for (CatEstado estado : lisEstados) {
		String cantidad = "0";
		if (mapaTotales.containsKey("91"+estado.getEstadoId())){
			cantidad = mapaTotales.get("91"+estado.getEstadoId());
		}
			
		total = total + Integer.parseInt(cantidad);
%>
	<tr>
		<td class="center"><%=cont%></td>
		<td ><%=estado.getNombreEstado()%></td>
		<td class="text-end"><%=cantidad%></td>
	</tr>
	<%
		cont++;
	}	
%>	
	<tfoot class="table-info">
	<tr>
		<th class="text-end" colspan="2"><b> Total:</b></th>
		<th class="text-end"><b><%=total%></b></th>
	</tr>
	</tfoot>
</table>
</div>