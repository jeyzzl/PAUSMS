<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<script type="text/javascript">	
	
		function Grabar() {
			if (document.frmperiodo.PeriodoId.value != "" && document.frmperiodo.PeriodoNombre != "" && document.frmperiodo.FechaIni != "" && document.frmperiodo.FechaFin != "" && document.frmperiodo.Estado != "") {				
				document.frmperiodo.submit();
			} else {
				alert("Fill out the entire form");
			}
		}		
	</script>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<%
	String periodoId  			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");		
	TrabPeriodo trabPeriodo		= (TrabPeriodo)request.getAttribute("trabPeriodo"); 
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<style>
	body{
		background:white;
	}
</style>
<body>
<div class="container-fluid">
	<h2>CTP Periods</h2>
	<div class="alert alert-info">
		<a href="periodos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<form action="grabarPeriodo" method="post" name="frmperiodo">			
	<div class="row">
		<div class="col">
			<label for="PeriodoId">Key:</label>			
			<input name="PeriodoId" type="text" id="PeriodoId" class="form-control" size="10" maxlength="10" value="<%=trabPeriodo.getPeriodoId()%>" readonly>
			<br><br>
			<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>					
			<input name="Nombre" type="text" id="Nombre"  class="form-control" size="30" maxlength="30" value="<%=trabPeriodo.getNombrePeriodo()%>">
			<br><br>
			<label for="FechaIni">Start Date:</label>
			<input name="FechaIni" type="text" id="FechaIni"  class="form-control" data-date-format="dd/mm/yyyy" size="10" maxlength="10" value="<%=trabPeriodo.getFechaIni()%>">
			<br><br>
			<label for="FechaFin">End Date:</label>
			<input name="FechaFin" type="text" id="FechaFin"  class="form-control" data-date-format="dd/mm/yyyy" size="10" maxlength="10" value="<%=trabPeriodo.getFechaFin()%>">
			<br><br>
			<label for="Estado">Status:</label>
           	<select name="Estado" id="Estado"  class="form-control" >
				<option value="A" <% if(trabPeriodo.getEstado().equals("A"))out.print("selected");%>>Active</option>
				<option value="I" <% if(trabPeriodo.getEstado().equals("I"))out.print("selected");%>>Inactive</option>
			</select>
		</div>
	</div>
	<br><br>
	<div class="alert alert-info">			
		<a class="btn btn-primary" href="javascript:Grabar()">Save</a> &nbsp;			
	</div>
	</form>
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
 </script>
</html>