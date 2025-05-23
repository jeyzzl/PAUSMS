<%@page import="aca.afis.AfisPeriodoUtil"%>
<%@page import="java.text.*" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp" %>

<%@ include file= "../../seguro.jsp" %>


<jsp:useBean id="PeriodosUtil" class="aca.afis.AfisPeriodoUtil" scope="page" />
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>


<html>
<style>
	body{
		background: white;
	} 
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
</style>
<body>

<%
	String accion 		= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(accion);
	int accionFmt 		= 0;
	ArrayList<aca.afis.AfisPeriodo> PeriodosL = PeriodosUtil.getAll(conEnoc, "ORDER BY PERIODO_ID");
%>

<div class="container-fluid">
	
		<h1>Periodos de Evaluación</h1>
		<div class="alert alert-info">
			<a href="accion." class="btn btn-primary"><i class="icon-white icon-plus"></i><spring:message code='aca.Añadir'/></a>
		</div>
		<%if(nAccion==5){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%}	%>
		<table class="table table-bordered table-striped">
			<tr>
				<th>Op</th>
				<th><spring:message code='aca.PeriodoId'/></th>
				<th><spring:message code='aca.PeriodoNombre'/></th>
				<th><spring:message code='aca.FechaInicio'/></th>
				<th><spring:message code='aca.FechaFinal'/></th>
				<th><spring:message code="aca.Estado"/></th>
				<th><spring:message code="aca.Acceso"/></th>
			</tr>
<%
		for(aca.afis.AfisPeriodo periodos : PeriodosL){				
%>
			<tr>
				<td>
					<a href="accion.?PeriodoId=<%=periodos.getPeriodoId()%>"><i class="fas fa-pencil-alt"></i></a>
					<a href="accion.?Accion=2&PeriodoId=<%=periodos.getPeriodoId()%>"><i class="fas fa-trash-alt"></i> </a>
				</td>
				<td><%=periodos.getPeriodoId() %></td>
				<td><%=periodos.getPeriodoNombre() %></td>
				<td><%=periodos.getFechaIni() %></td>
				<td><%=periodos.getFechaFin() %></td>
				<td><%=periodos.getEstado().equals("A")?"Activo":"Inactivo"%></td>
				<td><%=periodos.getFiltro()%></td>
			</tr>
<%		} %>			
		</table>
		</div>
	
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>