<%@page import="java.text.*" %>
<%@page import="java.util.List" %>
<%@page import="aca.alerta.spring.AlertaPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>


<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<html>
<body>
<%
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<AlertaPeriodo> lisPeriodos 	= (List<AlertaPeriodo>)request.getAttribute("lisPeriodos"); 
%>
<div class="container-fluid">
	<h2>Catálogo de periodos de alerta sanitaria</h2>
	<div class="alert alert-info">
		<a href="accion" class="btn btn-primary"><i class="icon-white fas fa-plus"></i> <spring:message code='aca.Añadir'/></a>
	</div>
<%	if(accion.equals("5")){ %>
	<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	} %>
	<table class="table table-bordered table-sm" style="width:100%">
	<thead class="table-info">
	<tr>
		<th width="5%">Op</th>
		<th width="5%"><spring:message code='aca.Clave'/></th>
		<th width="30%"><spring:message code='aca.PeriodoNombre'/></th>
		<th width="10%"><spring:message code='aca.FechaInicio'/></th>
		<th width="10%"><spring:message code='aca.FechaFinal'/></th>
		<th width="5%">Mod.</th>
		<th width="10%"><spring:message code="aca.Estado"/></th>
		<th width="25%">Excepto</th>
	</tr>
	</thead>
<%
	for(AlertaPeriodo periodos : lisPeriodos){
		String estado = "";
		if(periodos.getEstado().equals("A")) estado="Activo"; else estado = "Inactivo";
		String exepto = periodos.getExcepto().length()>70?periodos.getExcepto().substring(0, 70)+"...":periodos.getExcepto();
%>
	<tr>
		<td>
			<a href="accion?periodoId=<%=periodos.getPeriodoId()%>"><i class="fas fa-pen-square"></i> </a>
			<a href="accion?Accion=2&periodoId=<%=periodos.getPeriodoId()%>"><i class="fas fa-trash"></i> </a>
		</td>
		<td><%=periodos.getPeriodoId() %></td>
		<td><%=periodos.getPeriodoNombre() %></td>
		<td><%=periodos.getFechaIni() %></td>
		<td><%=periodos.getFechaFin() %></td>
		<td><%=periodos.getModalidades() %></td>
		<td><%=estado%></td>
		<td><%=exepto%></td>
	</tr>
<%	} %>			
	</table>		
</div>
</body>
</html>