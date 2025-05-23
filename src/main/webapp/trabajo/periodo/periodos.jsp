<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>

<html>
<head>
	<script type="text/javascript"> 
		function Borrar(PeriodoId) {
			if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
				document.location = "borrarPeriodo?PeriodoId="+PeriodoId;
			}
		}
 	</script>
</head>
<%
	/* Lista de las categorias */
	List<TrabPeriodo> listaPeriodos 	 		= (List<TrabPeriodo>)request.getAttribute("listaPeriodos");
  	HashMap<String,String> mapaPorPeriodo		= (HashMap<String,String>)request.getAttribute("mapaPorPeriodo");
	String periodoId  							= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String mensaje 								= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h2>CTP Periods</h2>
	<div class="alert alert-info">
		<a href="editarPeriodo" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>	
	</div>	
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="3%">#</th>
		<th width="3%">Op.</th>
		<th width="3%">Key</th>
		<th width="20%">Period Name</th>
		<th width="7%">Start Date</th>
		<th width="7%">End Date</th>
		<th width="5%">Status</th>
<!-- 		<th width="5%">Tipo</th> -->
	</tr>
	</thead>
<%				
	int row = 0;
	for (TrabPeriodo periodo: listaPeriodos) {
		row++;	
%>
	<tr>
		<td><%=row%></td>
		<td>
			<a href="editarPeriodo?PeriodoId=<%=periodo.getPeriodoId()%>" title="Edit"><i class="fas fa-edit"></i></a>&nbsp;
			<% if(mapaPorPeriodo.get(periodo.getPeriodoId())== null){ %>
			<a href="javascript:Borrar('<%=periodo.getPeriodoId()%>')" title="Delete"><i class="fas fa-trash-alt"></i></a>
			<% } %>
		</td>				
		<td><%=periodo.getPeriodoId()%></td>
		<td><%=periodo.getNombrePeriodo()%></td>
		<td><%=periodo.getFechaIni()%></td>
		<td><%=periodo.getFechaFin()%></td>
		<td><span class="badge bg-primary"><%=periodo.getEstado().equals("A")?"Active":"Inactive" %></span></td>
<%-- 		<td><span class="badge bg-primary"><%=periodo.getTipo().equals("R")?"Regular":"Summer" %></span></td> --%>
	</tr>
<%	}  %>
	</table>
</div>
</body>
</html>
