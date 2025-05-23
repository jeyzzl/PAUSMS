<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<script type="text/javascript">	
	
		function Grabar() {
			if (document.frmperiodo.PeriodoId.value != "" && document.frmperiodo.PeriodoNombre != "" && document.frmperiodo.FechaIni != "" && document.frmperiodo.FechaFin != "" && document.frmperiodo.Estado != "" && document.frmperiodo.IdEjercicio != "" && document.frmperiodo.Tipo != "") {				
				document.frmperiodo.submit();
			} else {
				alert("Llene correctamente el formulario");
			}
		}		
	</script>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<%
	String periodoId  			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");		
	BecPeriodo becPeriodo		= (BecPeriodo)request.getAttribute("becPeriodo"); 
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<style>
	body{
		background:white;
	}
</style>
<body>
<div class="container-fluid">
	<h2>Categorias</h2>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<form action="grabar" method="post" name="frmperiodo">			
	<div class="row">
		<div class="col">
			<label for="PeriodoId">Periodo Id:</label>			
			<input name="PeriodoId" type="text" id="PeriodoId" class="form-control" size="10" maxlength="10" value="<%=becPeriodo.getPeriodoId()%>">
			<br><br>
			<label for="PeriodoNombre"><spring:message code="aca.Nombre"/>:</label>					
			<input name="PeriodoNombre" type="text" id="PeriodoNombre"  class="form-control" size="30" maxlength="30" value="<%=becPeriodo.getPeriodoNombre()%>">
			<br><br>
			<label for="FechaIni">Fecha Inicial:</label>
			<input name="FechaIni" type="text" id="FechaIni"  class="form-control" data-date-format="dd/mm/yyyy" size="15" maxlength="10" value="<%=becPeriodo.getFechaIni()%>">
			<br><br>
			<label for="FechaFin">Fecha Final:</label>
			<input name="FechaFin" type="text" id="FechaFin"  class="form-control" data-date-format="dd/mm/yyyy" size="15" maxlength="10" value="<%=becPeriodo.getFechaFin()%>">
			<br><br>
			<label for="Estado">Estado:</label>
           	<select name="Estado" id="Estado"  class="form-control" >
				<option value="A" <%if(becPeriodo.getEstado().equals("A"))out.print("selected");%>>Activo</option>
				<option value="I" <%if(becPeriodo.getEstado().equals("I"))out.print("selected");%>>Inactivo</option>
			</select>
			<br><br>
			<label for="IdEjercicio">Ejercicio Id:</label>
			<input name="IdEjercicio" type="text" id="IdEjercicio"  class="form-control" size="30" maxlength="20" value="<%=becPeriodo.getIdEjercicio()%>">
			<br><br>
		</div>
		<div class="col">
			<label for="Tipo">Tipo:</label>
           	<select name="Tipo" id="Tipo" class="form-select" >
				<option value="R" <%if(becPeriodo.getTipo().equals("R"))out.print("selected");%>>Regular</option>
				<option value="V" <%if(becPeriodo.getTipo().equals("V"))out.print("selected");%>>Verano</option>
			</select>	
			<br><br><br>
			<strong>*Nota: Separa los renglones con un guion</strong>
			<br><br>
			<label for="fechasUniversidad">Universidad:</label>
			<textarea name="FechasUniversidad" class="form-control" rows="" cols="120"><%=becPeriodo.getFechasUniversidad()%></textarea>
			<br><br>
			<label for="fechasPreparatoria">Preparatoria:</label>
			<textarea name="FechasPreparatoria" class="form-control" rows="" cols="120"><%=becPeriodo.getFechasPrepa()%></textarea>
			<br><br><br>
			<label for="fechasPeriodo">Periodo:</label>
			<textarea name="FechasPeriodo" class="form-control" rows="" cols="120"><%=becPeriodo.getFechasPeriodo()%></textarea>
			<br><br>
		</div>
	</div>
	<br>
	<div class="alert alert-info">			
		<a class="btn btn-primary" href="javascript:Grabar()">Guardar</a> &nbsp;			
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