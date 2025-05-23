<%@page import= "java.util.ArrayList"%>
<%@page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitAlumno"%>
<%@page import="aca.tit.spring.TitAlumnoDao"%>
<%@page import="aca.tit.spring.TitAntecedente"%>
<%@page import="aca.tit.spring.TitInstitucion"%>
<%@page import="aca.tit.spring.TitEstudio"%>
<%@page import="aca.catalogo.spring.CatEstado"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<%
	String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	String planId			= (String) request.getAttribute("planId");
	boolean existe			= (boolean) request.getAttribute("existe");
	boolean btnGrabar			= request.getParameter("BtnGrabar") == null ? true : false;
	
	TitAlumno titAlumno			= (TitAlumno) request.getAttribute("titAlumno");
	TitAntecedente antecedente	= (TitAntecedente) request.getAttribute("antecedente");
	ArrayList<TitEstudio> lisEstudio	= (ArrayList<TitEstudio>) request.getAttribute("lisEstudio");
	ArrayList<CatEstado> lisEstados		= (ArrayList<CatEstado>) request.getAttribute("lisEstados");
	
%>
<body>
<div class="container-fluid">
	<h2>Antecedente<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="titulacion"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form id="frmAntecedente" name="frmAntecedente" method="post" action="grabarAntecedentes">
		<input type="hidden" name="Folio" id="Folio" value="<%=antecedente.getFolio()%>"/>
		<div class="row">
			<div class="span5">
				<fieldset>
					<label for="Folio">Folio: <span class="badge bg-dark"><%=antecedente.getFolio()%></span></label>
					<% if (existe==false) out.print("¡ Sin Grabar !");%>
				</fieldset>
				<br>
				<fieldset>
					<label for="Institucion">Instituci&oacute;n</label>
					<input class="input input-mini form-control" type="text" name="Institucion" id="Institucion" maxlength="200" value="<%=antecedente.getInstitucion()%>" size="49" required/>				
				</fieldset>						
				<br>
				<fieldset>
					<label for="Estudio">Estudio</label>
					<select name="EstudioId" class="form-select"> 
<%			for (TitEstudio estudio : lisEstudio){%>
						<option value="<%=estudio.getEstudioId()%>" <%=antecedente.getEstudioId().equals(estudio.getEstudioId())?"Selected":""%>><%=estudio.getEstudioNombre()%></option>
<%			}%>
					</select>
				</fieldset>
				<br>	
				<fieldset>
					<label for="Entidad">Entidad</label>
					<select name="EntidadId" class="form-select">
<%			for (CatEstado estado : lisEstados){
				if (antecedente.getEntidadId().equals("0")) antecedente.setEntidadId("19");%>
					<option value="<%=estado.getSepId()%>" <%=antecedente.getEntidadId().equals(estado.getSepId())?"Selected":""%>><%=estado.getNombreEstado()%></option>
<%			}%>
				</select>
			</fieldset>						
		</div>
		<div class="span3">			
			<fieldset>
				<label for="FechaInicio">Fecha Inicio</label>
				<input type="text" class="input input-medium form-control" name="FechaInicio" id="FechaInicio" maxlength="10" value="<%=antecedente.getFechaInicio()%>" data-date-format="yyyy-mm-dd" required/>
				(AAAA-MM-DD)
			</fieldset>
			<br>
			<fieldset>
				<label for="FechaTerminacion">Fecha Terminaci&oacute;n</label>
				<input type="text" class="input input-medium form-control" name="FechaTerminacion" id="FechaTerminacion" maxlength="10" value="<%=antecedente.getFechaTerminacion()%>" data-date-format="yyyy-mm-dd" required/>
				(AAAA-MM-DD)
			</fieldset>
			<br>
			<fieldset>
				<label for="Cedula">C&eacute;dula</label>
				<input type="text" class="input input-small form-control" name="Cedula" id="Cedula" maxlength="8" value="<%=antecedente.getCedula()==null?"":antecedente.getCedula()%>" required/>
			</fieldset>
		</div>
	</div>
	<br>
<%	if (btnGrabar){	%>
	<div class="alert alert-info">
<%		if(titAlumno.getEstado().equals("A")){%>
			<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i>Grabar</a>
<%			if (existe){%>
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
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaTerminacion').datepicker();
	
	function Grabar() {
// 		document.frmAntecedente.submit();
		validaFecha();
	}
	
	function Borrar(folio) {
		if (confirm("¿ Estás seguro de borrar los datos antecedentes ?")){
			document.location.href = "borrarAntecedente?Folio="+folio;
		}	
	}
	
	function validaFecha(){
		var dateformat = /^[0-9\-]+$/;
		var fechasCorrectas = true;
		//FECHA INICIO
	  	if(document.getElementById("FechaInicio").value.match(dateformat)){
	  		var pdate = document.getElementById("FechaInicio").value.split('-');
  			var yyyy 	= pdate[0];
	  		var mm  	= pdate[1];
	  		var dd 		= pdate[2];

	  		var dia = new Date(yyyy+"/"+mm+"/"+dd);

	  		if (dia.getDay() === 0){
  				alert('Fecha inicio no puede ser domingo!');
  				return false;
	  		}
  			
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

	  		var dia = new Date(yyyy+"/"+mm+"/"+dd);

	  		if (dia.getDay() === 0){
  				alert('Fecha terminación no puede ser domingo!');
  				return false;
	  		}
  			
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
	  		document.frmAntecedente.submit();
		}
	  }
</script>
</html>