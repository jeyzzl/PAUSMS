<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmPago"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>


<html>
<head></head>
<%	
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String nombreAlumno 	= (String)request.getAttribute("nombre");	
	
	List<AdmPago> lisPago		= (List<AdmPago>)request.getAttribute("lisPagos");
	
%>
<body>
<div class="container-fluid">
	<h2>Payment Confirmation <small class="text-muted fs-5">( <%=folio%> - <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%">#</th>
		<th width="20%">Amount Paid</th>
		<th width="10%">Date</th>
		<th width="10%">No. Reciept</th>
		<th width="10%">Method</th>
		<th width="40%">Comment</th>
		<th width="10%">File</th>
	</tr>
	</thead>
<%	
	int row=0;
	for(AdmPago pago : lisPago){
		row++;
		String fecha = "-";
		String metodo = pago.getMetodo();
		if (pago.getFecha() != null){
			fecha = pago.getFecha();
		}
		
		
		if (metodo.equals("B")){
			metodo = "Bank Deposit";
		}else if (metodo.equals("T")){
			metodo = "Transfer";
		}else{
			metodo = "Cash Payment";
		}
		
		
%>	
	<tr>
		<td><%=row%></td>
		<td><%=pago.getCantidad()%></td>
		<td><%=fecha %></td>
		<td><%=pago.getRecibo()==null?"n/a":pago.getRecibo()%></td>
		<td><%=metodo%></td>
		<td><%=pago.getComentario()%></td>
		<td><a href="descargarPago?Folio=<%=folio%>" class="btn btn-success" target="_blank"><i class="fas fa-file-pdf"></i></a></td>
	</tr>
<% 	}%>
	</table>	
</div>
</body>
</html>