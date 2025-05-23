<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.tit.spring.TitTramite"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<%	
	String tramite 				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");	
	TitTramite titTramite		= (TitTramite)request.getAttribute("titTramite");
	boolean existe				= (boolean) request.getAttribute("existe");
	
	String estado 				= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
	String grabo 				= (String)request.getAttribute("grabo");
%>
<body>
<div class="container-fluid">
	<h2>Editar Tramite  <small class="text-muted fs-4">(<%=titTramite.getDescripcion()%>)</small></h2>
	<form name="frmTramite" action="grabar" method="post">				
		<div class="alert alert-info">
			<a class="btn btn-primary" href="tramite?Institucion=<%=titTramite.getInstitucion()%>&Estado=<%=estado%>"><i class="fas fa-arrow-left"></i></a>
		</div>
<% 		if(grabo.equals("1")){%>	
		<div class="alert alert-success" role="alert">
			Grabado
		</div>
<% 		}%>	
		<div class="row">
			<div class="span3">
				<fieldset>
					<label for="Tramite">Tramite</label>
					<input type="text" class="input input-mini form-control" name="Tramite" id="Tramite" value="<%=titTramite.getTramiteId()%>" style="width:100px;" readonly/>				
				</fieldset>
				<br>
				<fieldset>
					<label for="Fecha">Fecha (AAAA/MM/DD)</label>
					<input type="text" class="input input-medium form-control" name="Fecha" id="Fecha" data-date-format="yyyy/mm/dd" value="<%=titTramite.getFecha()%>" style="width:200px;"/>								
				</fieldset>
				<br>
				<fieldset>
					<label for="Descripcion">Descripción</label>
					<input type="text" class="input input-xlarge form-control" name="Descripcion" id="Descripcion" maxlength="100" value="<%=titTramite.getDescripcion()%>" style="width:500px;" required/>		
				</fieldset>
				<br>
				<fieldset>
					<label for="Institucion">Institución</label>
					<select name="Institucion" id="Institucion" class="form-select" style="width:200px;">
					<option value="UM" <%=titTramite.getInstitucion().equals("UM")?"selected":""%>>UM</option>
					<option value="COVOPROM" <%=titTramite.getInstitucion().equals("COVOPROM")?"selected":""%>>COVOPROM</option>
				</select>
				</fieldset>
				<br>
				<fieldset>
					<label for="Estado">Estado</label>
					<select name="Estado" id="Estado" class="form-select" style="width:200px;">
					<option value="A" <%=titTramite.getEstado().equals("A")?"selected":""%>>Activo</option>
					<option value="F" <%=titTramite.getEstado().equals("F")?"selected":""%>>Firmas</option>
					<option value="T" <%=titTramite.getEstado().equals("T")?"selected":""%>>Terminado</option>
				</select>
				</fieldset>
				<br>
				<fieldset>
					<label for="Recibo">Recibo</label>
					<input type="text" class="input input-medium form-control" name="Recibo" id="Recibo" maxlength="100" value="<%=titTramite.getRecibo() %>" style="width:200px;" required/>	
				</fieldset>
				<br>			
			</div>		
		</div>
		<div class="alert alert-info">	
			<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i> Grabar</a>
		</div>
	</form>	
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#Fecha').datepicker();
	function Grabar() {
		document.frmTramite.submit();
	}	

	function validaFecha(){
		var dateformat = /^[0-9\/]+$/;
		var fechaCorrectas = true;
	  	if(document.getElementById("Fecha").value.match(dateformat)){
	  		var pdate = document.getElementById("Fecha").value.split('/');
  			var yyyy 	= pdate[0];
	  		var mm  	= pdate[1];
	  		var dd 		= pdate[2];
  			
	  		if (yyyy.length != 4){
  				alert('Año en fecha incorrecto!');
  				return false;
	  		}
	  		
	  		if (mm.length != 2){
	  			alert('Mes en fecha incorrecto!');
  				return false;
	  		}

	  		if (dd.length != 2){
	  			alert('Dia en fecha incorrecto!');
  				return false;
	  		}
	  	}else {
	  		fechaCorrectas = false;
	  		alert("Formato de fecha invalido!");
	  	}	
		
		if(fechaCorrectas == true){
			document.frmExpedicion.submit();
		}
	}
</script>
</html>