<%@page import="aca.residencia.spring.ResPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<%	
	ResPeriodo periodo = (ResPeriodo)request.getAttribute("periodo");	
 	String mensaje = (String)request.getAttribute("mensaje");	
%>
<body class="container-fluid">

	<h1>Cycle</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
  		Saved
	</div>
<%  }%>
	<form class="form-group" action="grabar" name="form" method="post">
		<input type="hidden" name="PeriodoId" value="<%=periodo.getPeriodoId()%>">
		<div class="container-fluid">
		<div class="row">
			<div class="col-sm-4">
				<label>Cycle Name</label>
				<input class="form-control" type="text" name="Nombre" value="<%=periodo.getPeriodoNombre()%>">
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-4">
				<label>Start Date</label>
				<input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" class="form-control" type="text" data-date-format="dd/mm/yyyy" value="<%=periodo.getFechaIni()%>" maxlength="10"/>
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-4">
				<label>End Date</label>
				<input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" class="form-control" type="text" data-date-format="dd/mm/yyyy" value="<%=periodo.getFechaFin()%>" maxlength="10"/>
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-4">
				<label>Status</label>
				<select id="Estado" name="Estado" class="form-select" type="text" value"<%=periodo.getEstado()%>">
					<option value="A" <%if(periodo.getEstado().equals("A"))out.print("selected");%>>Active</option>
					<option value="I" <%if(periodo.getEstado().equals("I"))out.print("selected");%>>Inactive</option>
				</select>
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-12">
				<button class="btn btn-primary" type="submit">Save</button>
			</div>
		</div>
		</div>
	</form>
<%	
	
%>
</body>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>