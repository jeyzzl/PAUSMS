<%@page import="java.util.List"%>
<%@page import="aca.residencia.spring.ResPeriodo"%>
<%@page import="aca.residencia.spring.ResRazon"%>
<%@page import="aca.residencia.spring.ResAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<%	
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");
	ResAlumno alumno 				= (ResAlumno)request.getAttribute("alumno");	
	String mensaje 					= (String)request.getAttribute("mensaje");
	String nombreAlumno				= (String)request.getAttribute("nombreAlumno");
	String folio					= (String)request.getAttribute("folio");
	String periodoId				= (String)request.getAttribute("periodoId");
	
	List<ResPeriodo> lisPeriodo 	= (List<ResPeriodo>)request.getAttribute("lisPeriodo");	
	List<ResRazon> lisRazon 		= (List<ResRazon>)request.getAttribute("lisRazon"); 	
	
%>
<body>
<head>
	<script type="text/javascript">
		function cambioPeriodo(){
			var periodoId = document.getElementById("PeriodoId").value;
			var folio = document.getElementById("FolioOrigen").value;
			location.href = "copiaExterno?PeriodoId="+periodoId+"&Folio="+folio;
		}
	</script>
</head>
<div class="container-fluid">
	<h2>Register as Off-campus <small class="text-muted fs-4">( <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
  		Saved
	</div>
<%  }%>
	<form class="form-group" action="grabar" name="form" method="post">
		<input type="hidden" name="ResidenciaId" value="E">
		<input type="hidden" name="Matricula" value="<%=alumno.getMatricula()%>">
		<input type="hidden" name="Folio" value="<%=alumno.getFolio()%>">
		<input type="hidden" id="FolioOrigen" value="<%=folio%>">
		<div class="row" style="margin: 10px 0px 0 10px;">
			<div class="col-sm-3">
				<label>Cycle</label>
				<select class="form-control" id="PeriodoId" name="PeriodoId" onchange="javascript:cambioPeriodo();">
<% 				for(ResPeriodo periodo : lisPeriodo){%>
					<option <%=periodoId.equals(periodo.getPeriodoId()) ? "selected" : ""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>	
<% 				}%>
				</select>
			</div>
			<div class="col-sm-3">
				<label>Street</label>
				<input class="form-control" type="text" name="Calle" value="<%=alumno.getCalle()%>">
			</div>
			<div class="col-sm-2">
				<label>Building Number</label>
				<input class="form-control" type="text" name="Numero" value="<%=alumno.getNumero()%>">
			</div>
			<div class="col-sm-2">
				<label>Neighborhood</label>
				<input class="form-control" type="text" name="Colonia" value="<%=alumno.getColonia()%>">
			</div>
			<div class="col-sm-2">
				<label>Town</label>
				<input class="form-control" type="text" name="Municipio" value="<%=alumno.getMunicipio()%>">
			</div>
		</div><br>
		<div class="row" style="margin: 10px 0px 0 10px;">
			<div class="col-sm-2">
				<label>Phone Area</label>
				<input class="form-control" type="text" name="TelArea" value="<%=alumno.getTelArea()%>">
			</div>
			<div class="col-sm-2">
				<label>Phone Number</label>
				<input class="form-control" type="text" name="TelNum" value="<%=alumno.getTelNum()%>">
			</div>
			<div class="col-sm-3">
				<label>Tutor name</label>
				<input class="form-control" type="text" name="TutNombre" value="<%=alumno.getTutNombre()%>">
			</div>
			<div class="col-sm-3">
				<label>Tutor Surname</label>
				<input class="form-control" type="text" name="TutApellidos" value="<%=alumno.getTutApellidos()%>">
			</div>
			<div class="col-sm-2">
				<label>User</label>
				<input class="form-control" type="text" name="Usuario" value="<%=codigoPersonal%>" readonly>
			</div>
		</div><br>
		<div class="row" style="margin: 10px 0px 0 10px;">
			<div class="col-sm-4">
				<label>Reason</label>
       	 		<select class="form-control" name="Razon">
<%				for(ResRazon razon : lisRazon){
					if (razon.getRazon().equals(alumno.getRazon())){
						out.print(" <option value='"+razon.getRazon()+"'");			
						out.print("Selected>"+ razon.getDescripcion()+"</option>");
					}else{
						out.print(" <option value='"+razon.getRazon()+"'");
						out.print(" >"+ razon.getDescripcion()+"</option>");
					}
				}
%>
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