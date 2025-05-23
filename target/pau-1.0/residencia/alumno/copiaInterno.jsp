<%@page import="java.util.List"%>

<%@page import="aca.residencia.spring.ResPeriodo"%>
<%@page import="aca.residencia.spring.ResAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<%
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	ResAlumno alumno 			= (ResAlumno)request.getAttribute("alumno");	
	String mensaje 				= (String)request.getAttribute("mensaje");
	String nombreAlumno			= (String)request.getAttribute("nombreAlumno");
	String folio				= (String)request.getAttribute("folio");
	String periodoId			= (String)request.getAttribute("periodoId");

	List<ResPeriodo> lisPeriodo = (List<ResPeriodo>)request.getAttribute("lisPeriodo");	
%>
<body>
<head>
	<script type="text/javascript">
		function cambioPeriodo(){
			var periodoId = document.getElementById("PeriodoId").value;
			var folio = document.getElementById("FolioOrigen").value;
			location.href = "copiaInterno?PeriodoId="+periodoId+"&Folio="+folio;
		}
	</script>
</head>
<div class="container-fluid">
	<h2>REgister as Dormitory Student <small class="text-muted fs-4"> ( <%=nombreAlumno%> ) </small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
  		Saved
	</div>
<%  }%>
	<form class="form-group" action="grabar" name="form" method="post">	
		<input type="hidden" name="ResidenciaId" value="I">	
		<input type="hidden" name="Matricula" value="<%=alumno.getMatricula()%>">
		<input type="hidden" name="Folio" value="<%=alumno.getFolio()%>">
		<input type="hidden" id="FolioOrigen" value="<%=folio%>">
		<div class="row" style="margin: 10px 0px 0 10px;">
			<div class="col-sm-4">
			<label>Cycle</label>
				<select class="form-control" id="PeriodoId" name="PeriodoId" onchange="javascript:cambioPeriodo();">
<% 				for(ResPeriodo periodo : lisPeriodo){%>
					<option <%=periodoId.equals(periodo.getPeriodoId()) ? "selected" : ""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>	
<% 				}%>
				</select>
			</div>
			<div class="col-sm-2">
				<label>Dormitory</label>
				<input class="form-control" type="text" name="Dormitorio" value="<%=alumno.getDormitorio()%>">
			</div>
			<div class="col-sm-2">
				<label>Type</label>
				<select class="form-control" name="ResidenciaId">					
					<option <%=alumno.getResidenciaId().equals("A") ? "selected":""%> value="A">AWAY</option>
					<option <%=alumno.getResidenciaId().equals("I") ? "selected":""%> value="I">PRESENT</option>
				</select>
			</div>
			<div class="col-sm-2">
				<label>Start date</label>
				<input class="form-control" data-format="hh:mm:ss" id="FechaInicio" name="FechaInicio" type="text" data-date-format="dd/mm/yyyy" value="<%=alumno.getFechaInicio()%>" maxlength="10"/>
			</div>
			<div class="col-sm-2">
				<label>End date</label>
				<input class="form-control" data-format="hh:mm:ss" id="FechaFinal" name="FechaFinal" type="text" data-date-format="dd/mm/yyyy" value="<%=alumno.getFechaFinal()%>" maxlength="10"/>
			</div>
		</div><br>
		<div class="alert alert-info">
			<button class="btn btn-primary" type="submit">Save</button>			
		</div>
	</form>
</div>
</body>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>