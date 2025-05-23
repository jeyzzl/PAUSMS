<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitAlumno"%>
<%@page import="aca.tit.spring.TitCarrera"%>
<%@page import="aca.tit.spring.TitAutorizacion"%>

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
	String revoe				= (String) request.getAttribute("revoe");
	String clave				= (String) request.getAttribute("clave");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");
	String planId				= (String) request.getAttribute("planId");
	boolean existe				= (boolean) request.getAttribute("existe");
	boolean btnGrabar			= request.getParameter("BtnGrabar") == null ? true : false;
	
	TitCarrera carrera			= (TitCarrera) request.getAttribute("titCarrera");
	TitAlumno titAlumno			= (TitAlumno) request.getAttribute("titAlumno");
	ArrayList<TitAutorizacion> lisAutorizacion	= (ArrayList<TitAutorizacion>) request.getAttribute("lisAutorizacion");
	
	// Validar datos
	if (carrera.getNumeroRvoe().equals("-")) carrera.setNumeroRvoe(revoe);
	if (carrera.getCveCarrera().equals("-")) carrera.setCveCarrera(clave);
	if (carrera.getNombreCarrera().equals("-")) carrera.setNombreCarrera(carreraNombre);
%>
<body>
<div class="container-fluid">
	<h2>Carrera<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%>)</small></h2>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="titulacion"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>	
	<form id="frmCarrera" name="frmCarrera" method="post" action="grabarCarrera">
		<input type="hidden" name="Folio" id="Folio" value="<%=carrera.getFolio()%>"/>
		<div class="row">
			<div class="span9">			
				<fieldset>
					<label for="CveCarrera">Clave de la Carrera</label>
					<input class="form-control" type="text" class="input input-medium" maxlength="7" name="CveCarrera" id="CveCarrera" value="<%=carrera.getCveCarrera()%>" required/>
					<% if (existe==false) out.print("¡ Sin Grabar !");%>
				</fieldset>
				<br>
				<fieldset>
					<label for="NombreCarrera">Nombre de la Carrera</label>
					<input class="form-control" type="text" class="input input-xxlarge" maxlength="100" name="NombreCarrera" id="NombreCarrera" value="<%=carrera.getNombreCarrera()%>" required/>
				</fieldset>
				<br>
				<fieldset>
					<label for="FechaInicio">Fecha de Inicio</label>
					<input class="form-control" type="text" class="input input-medium" maxlength="10" name="FechaInicio" id="FechaInicio" value="<%=carrera.getFechaInicio()%>" data-date-format="yyyy-mm-dd" required placeholder="AAAA-MM-DD"/>
					(AAAA-MM-DD)
				</fieldset>
				<br>
				<fieldset>
					<label for="Fechaterminacion">Fecha de Terminación</label>
					<input type="text" class="input input-medium form-control" maxlength="10" name=FechaTerminacion id="FechaTerminacion" value="<%=carrera.getFechaTerminacion()%>" data-date-format="yyyy-mm-dd" required placeholder="AAAA-MM-DD"/>
					(AAAA-MM-DD)			
				</fieldset>
				<br>
				<fieldset>
					<label for="IdAutorizacion">Id de Autorización</label>
					<select name="IdAutorizacion" class="form-select">
<%			for (TitAutorizacion autorizacion: lisAutorizacion){
				if (carrera.getIdAutorizacion().equals("0")) carrera.setIdAutorizacion("2");%>
						<option value="<%=autorizacion.getAutorizacionId()%>" <%=carrera.getIdAutorizacion().equals(autorizacion.getAutorizacionId())?"Selected":""%>><%=autorizacion.getAutorizacionNombre()%></option>
<%				} %>				
					</select>
				</fieldset>
				<br>		
				<fieldset>
					<label for="NumeroRvoe">Número de RVOE</label>
					<input type="text" class="input input-large form-control" maxlength="100" name="NumeroRvoe" id="NumeroRvoe" value="<%=carrera.getNumeroRvoe()%>" required/>
				</fieldset>
			</div>
		</div>
	<br>			
<%	if (btnGrabar){	%>
		<div class="alert alert-info">
<%		if (titAlumno.getEstado().equals("A")){	%>
			<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i>Grabar</a>
<%			if (existe){%>
			&nbsp;&nbsp;<a onclick="javascript:Borrar('<%=folio%>');" class="btn btn-primary"><i class="fas fa-check"></i>Borrar</a>
<%			}
		}	
	}%>		
		</div>
	</form>	
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaTerminacion').datepicker();
	
	function Grabar() {
// 		document.frmCarrera.submit();
		ValidaFecha();
	}
	
	function Borrar(folio) {
		if (confirm("¿ Estás seguro de borrar los datos de la carrera ?")){
			document.location.href = "borrarCarrera?Folio="+folio;
		}	
	}
	
	function ValidaFecha() {
		var inicia = document.getElementById("FechaInicio").value;
		var termina = document.getElementById("FechaTerminacion").value;
		
		if(Date.parse(termina) < Date.parse(inicia)){
			alert("La fecha de inicio debe ser menor que la fecha de terminacion");
			return false;
		}else{
			var dateformat = /^[0-9\-]+$/;
			var fechasCorrectas = true;
			//FECHA INICIO
		  	if(document.getElementById("FechaInicio").value.match(dateformat)){
		  		var pdate = document.getElementById("FechaInicio").value.split('-');
	  			var yyyy 	= pdate[0];
		  		var mm  	= pdate[1];
		  		var dd 		= pdate[2];
	  			
		  		if (yyyy.length != 4){
	  				alert('Año en fecha inicio incorrecto!');
	  				return false;
		  		}
		  		
		  		if (mm.length != 2){
		  			alert('Mes en fecha inicio incorrecto!');
	  				return false;
		  		}
	
		  		if (dd.length != 2){
		  			alert('Dia en fecha inicio incorrecto!');
	  				return false;
		  		}
		  	}else {
		  		fechasCorrectas = false;
		  		alert("Formato de fecha inicio invalido!");
		  	}
		  	
			//FECHA TERMINACION
		  	if(document.getElementById("FechaTerminacion").value.match(dateformat)){
		  		var pdate = document.getElementById("FechaTerminacion").value.split('-');
	  			var yyyy 	= pdate[0];
		  		var mm  	= pdate[1];
		  		var dd 		= pdate[2];
	  			
		  		if (yyyy.length != 4){
	  				alert('Año en fecha terminación incorrecto!');
	  				return false;
		  		}
		  		
		  		if (mm.length != 2){
		  			alert('Mes en fecha terminación incorrecto!');
	  				return false;
		  		}
	
		  		if (dd.length != 2){
		  			alert('Dia en fecha terminación incorrecto!');
	  				return false;
		  		}
		  	}else {
		  		fechasCorrectas = false;
		  		alert("Formato de fecha terminación invalido!");
		  	}
			
			if(fechasCorrectas == true){
				document.frmCarrera.submit();
			}
		}
	  }
</script>
</html>