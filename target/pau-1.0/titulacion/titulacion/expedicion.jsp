<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitExpedicion"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.tit.spring.TitServicio"%>
<%@page import="aca.tit.spring.TitAlumno"%>
<%@page import="aca.tit.spring.TitModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<%	
	String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");
	String planId				= (String) request.getAttribute("planId");
	String nivel				= (String) request.getAttribute("nivel");
	boolean existe				= (boolean) request.getAttribute("existe");
	boolean btnGrabar			= request.getParameter("BtnGrabar") == null ? true : false;
	
	TitExpedicion titExpedicion	= (TitExpedicion) request.getAttribute("titExpedicion");
	
	if(!existe){
		if(nivel.equals("3") || nivel.equals("4")){
			titExpedicion.setServicio("0");
			titExpedicion.setFundamentoId("5");
		}else{
			titExpedicion.setServicio("2");
		}
	}
	
	if(titExpedicion.getModalidadId().equals("")){
		titExpedicion.setModalidadId("1");
	}
	
	TitAlumno titAlumno			= (TitAlumno) request.getAttribute("titAlumno");
	
	ArrayList<TitModalidad> lisModalidad	= (ArrayList<TitModalidad>) request.getAttribute("lisModalidad");
	ArrayList<TitServicio> lisFundamento	= (ArrayList<TitServicio>) request.getAttribute("lisFundamento");
	ArrayList<CatEstado> lisEstados			= (ArrayList<CatEstado>) request.getAttribute("lisEstados");
	
	// Validar valores
	if (titExpedicion.getFechaExpedicion().equals("0")) titExpedicion.setFechaExpedicion(aca.util.Fecha.getHoyGuion());
	if (titExpedicion.getEntidadId().equals("0")) titExpedicion.setEntidadId("19");
%>
<body>
<div class="container-fluid">
	<h2>Expedición<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%> )</small></h2>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="titulacion"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>	
	<form id="frmExpedicion" name="frmExpedicion" method="post" action="grabarExpedicion">
		<input type="hidden" name="Folio" id="Folio" value="<%=titExpedicion.getFolio()%>"/>
		<div class="row">
			<div class="span4">
				<fieldset>
					<label for="Folio">Folio: <span class="badge bg-dark"><%=titExpedicion.getFolio()%></span></label>
					<% if (existe==false) out.print("¡ Sin Grabar !");%>
				</fieldset>
				<br>
				<fieldset>
					<label for="FechaExpedicion">Fecha de Expedición</label>
					<input type="text" class="input input-medium form-control" maxlength="10" name="FechaExpedicion" id="FechaExpedicion" data-date-format="yyyy-mm-dd" value="<%=titExpedicion.getFechaExpedicion()==null?"":titExpedicion.getFechaExpedicion()%>" required/>
					(AAAA-MM-DD)
				</fieldset>
				<br>
				<fieldset>
					<label for="ModalidadId">Modalidad</label>
					<select onchange="javascript:cambioModalidad();" name="ModalidadId" id="ModalidadId" class="input input-xlarge form-select">
<%			for (TitModalidad modalidad : lisModalidad){ %>
						<option value="<%=modalidad.getModalidadId()%>" <%=titExpedicion.getModalidadId().equals(modalidad.getModalidadId()) ? "Selected" : ""%>><%=modalidad.getModalidadNombre()%></option>
<%			} %>									
					</select>
				</fieldset>						
				<br>
				<div style="display: <%=titExpedicion.getModalidadId().equals("1") ? "block;" : "none;"%>" id="selectTesis">
					<fieldset>
						<label for="FechaExamen">Fecha de Examen</label>
						<input type="text" class="input input-medium form-control" maxlength="10" name="FechaExamen" id="FechaExamen" value="<%=titExpedicion.getFechaExamen() == null ? "" : titExpedicion.getFechaExamen()%>" data-date-format="yyyy-mm-dd"/>
						(AAAA-MM-DD)
					</fieldset>
					<br>
				</div>
				<div style="display: <%=!titExpedicion.getModalidadId().equals("1") ? "block;" : "none;"%>" id="selectOtra">
					<fieldset>
						<label for="FechaExencion">Fecha Exención</label>
						<input type="text" class="input input-medium form-control" maxlength="10" name="FechaExencion" id="FechaExencion" value="<%=titExpedicion.getFechaExencion() == null ? "" : titExpedicion.getFechaExencion()%>" data-date-format="yyyy-mm-dd"/>
						(AAAA-MM-DD)
					</fieldset>
				</div>
			</div>
			<div class="span4">
				<fieldset>
					<label for="Servicio">Cumplió Servicio Social</label>
					<select name="Servicio" class="form-select">
						<option value="1" <%=titExpedicion.getServicio().equals("1") ? "selected" : ""%>>SI</option>
						<option value="0" <%=titExpedicion.getServicio().equals("0") ? "selected" : ""%>>NO</option>
					</select>
				</fieldset>
				<br>
				<fieldset>
					<label for="FundamentoId">Fundamento</label>
					<select name="FundamentoId" class="form-select">
<%			for (TitServicio servicio : lisFundamento){ %>
						<option value="<%=servicio.getServicioId()%>" <%=titExpedicion.getFundamentoId().equals(servicio.getServicioId())?"Selected":""%>><%=servicio.getServicioNombre()%></option>
<%			} %>									
					</select>
				</fieldset>			
				<br>
				<fieldset>
					<label for="EntidadId">Entidad</label>
					<select name="EntidadId" class="form-select">
<%			for (CatEstado estado : lisEstados){ %>
						<option value="<%=estado.getSepId()%>" <%=titExpedicion.getEntidadId().equals(estado.getSepId())?"Selected":""%>><%=estado.getNombreEstado()%></option>
<%			} %>									
					</select>				
				</fieldset>
				<br>
			</div>
		</div>
		<br>	
<%	if (btnGrabar){	%>		
		<div class="alert alert-info">
<%		if(titAlumno.getEstado().equals("A")){ %>
			<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i>Grabar</a>
<%			if (existe){ %>
			&nbsp;&nbsp;<a onclick="javascript:Borrar('<%=folio%>');" class="btn btn-primary"><i class="fas fa-check"></i>Borrar</a>
<%			}
		}
	}
%>					
		</div>
	</form>	
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#FechaExpedicion').datepicker();
	jQuery('#FechaExamen').datepicker();
	jQuery('#FechaExencion').datepicker();

	function cambioModalidad() {
		var modalidad = document.getElementById("ModalidadId").value;
		var x = document.getElementById("selectTesis");
		var y = document.getElementById("selectOtra");

		if(modalidad === '1'){
		    x.style.display = "block";
		    y.style.display = "none";
		    document.getElementById("FechaExencion").value = "";
		}else{
		    x.style.display = "none";
		    y.style.display = "block";
		    document.getElementById("FechaExamen").value = "";
		}
	}

	function Grabar() {
		validaFecha();
	}
	
	function Borrar(folio) {
		if (confirm("¿ Estás seguro de borrar los datos de expedición ?")){
			document.location.href = "borrarExpedicion?Folio="+folio;
		}	
	}
	
	function validaFecha(){
		var dateformat = /^[0-9\-]+$/;
		var fechasCorrectas = true;
		//FECHA EXPEDICION
	  	if(document.getElementById("FechaExpedicion").value.match(dateformat) && document.getElementById("FechaExpedicion").value.length > 0 ){
	  		var pdate = document.getElementById("FechaExpedicion").value.split('-');
  			var yyyy 	= pdate[0];
	  		var mm  	= pdate[1];
	  		var dd 		= pdate[2];
  			
	  		var dia = new Date(yyyy+"/"+mm+"/"+dd);

	  		if (dia.getDay() === 0){
  				alert('Fecha expedición no puede ser domingo!');
  				return false;
	  		}

	  		if (yyyy.length != 4){
  				alert('Año en fecha expedición incorrecto!');
  				return false;
	  		}
	  		
	  		if (mm.length != 2){
	  			alert('Mes en fecha expedición incorrecto!');
  				return false;
	  		}

	  		if (dd.length != 2){
	  			alert('Dia en fecha expedición incorrecto!');
  				return false;
	  		}
	  	}else if (document.getElementById("FechaExpedicion").value.length > 0) {
	  		fechasCorrectas = false;
	  		alert("Formato de fecha expedición invalido!");
	  	}
	  	
		//FECHA EXAMEN
	  	if(document.getElementById("FechaExamen").value.match(dateformat) && document.getElementById("FechaExamen").value.length > 0){
	  		var pdate = document.getElementById("FechaExamen").value.split('-');
  			var yyyy 	= pdate[0];
	  		var mm  	= pdate[1];
	  		var dd 		= pdate[2];

	  		var dia = new Date(yyyy+"/"+mm+"/"+dd);

	  		if (dia.getDay() === 0){
  				alert('Fecha examen no puede ser domingo!');
  				return false;
	  		}

	  		if (yyyy.length != 4){
  				alert('Año en fecha examen incorrecto!');
  				return false;
	  		}
	  		
	  		if (mm.length != 2){
	  			alert('Mes en fecha examen incorrecto!');
  				return false;
	  		}

	  		if (dd.length != 2){
	  			alert('Dia en fecha examen incorrecto!');
  				return false;
	  		}
	  	}else if (document.getElementById("FechaExamen").value.length > 0) {
	  		fechasCorrectas = false;
	  		alert("Formato de fecha examen invalido!");
	  	}
	 
		//FECHA EXENCION
		if(document.getElementById("FechaExencion").value.match(dateformat) && document.getElementById("FechaExencion").value.length > 0){
	  		var pdate = document.getElementById("FechaExencion").value.split('-');
  			var yyyy 	= pdate[0];
	  		var mm  	= pdate[1];
	  		var dd 		= pdate[2];

	  		var dia = new Date(yyyy+"/"+mm+"/"+dd);

	  		if (dia.getDay() === 0){
  				alert('Fecha exención no puede ser domingo!');
  				return false;
	  		}
  			
	  		if (yyyy.length != 4){
  				alert('Año en fecha exención incorrecto!');
  				return false;
	  		}
	  		
	  		if (mm.length != 2){
	  			alert('Mes en fecha exención incorrecto!');
  				return false;
	  		}

	  		if (dd.length != 2){
	  			alert('Dia en fecha exención incorrecto!');
  				return false;
	  		}
	  	}else if (document.getElementById("FechaExencion").value.length > 0) {	  	 	
	  		fechasCorrectas = false;
	  		alert("Formato de fecha exención invalido!");
	  	}

// 		var examen 		= document.getElementById("FechaExamen").value;
// 		var exencion 	= document.getElementById("FechaExencion").value;

// 		if(examen != "" && exencion != ""){
// 			alert("No puedes poner fecha de examen y exención. Elige una.");
// 		}else{
			if(fechasCorrectas == true){
				document.frmExpedicion.submit();
// 			}

		}

	  }	
</script>
</html>