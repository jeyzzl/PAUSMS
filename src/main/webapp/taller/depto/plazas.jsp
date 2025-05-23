<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.bec.spring.BecPeriodo"%>
<%@page import="aca.bec.spring.BecPlazas"%>
<%@page import="aca.bec.BecAcceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String ejercicioId			= (String) session.getAttribute("ejercicioId");
	String periodoId 			= (String) session.getAttribute("periodoBecas");
	String ccosto				= request.getParameter("Ccosto")==null?"0":request.getParameter("Ccosto");
	String mensaje 				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	
	BecPlazas becPlazas 		= (BecPlazas) request.getAttribute("becPlazas");
	String ccostoNombre 		= (String) request.getAttribute("ccostoNombre");
	String periodoNombre 		= (String) request.getAttribute("periodoNombre");
	int numBas 					= (int) request.getAttribute("numBas");
	int numInd 					= (int) request.getAttribute("numInd");
	int numTem 					= (int) request.getAttribute("numTem");
	int numPre 					= (int) request.getAttribute("numPre");
	int numPos 					= (int) request.getAttribute("numPos");
%>
<style>
	body{
		background:white;
	}
</style>
<body>
<div class="container-fluid">		
	<h2>Plazas por departamento <small class="text-muted fs-5">( <%=ejercicioId %> - <%=ccostoNombre%> - <%=periodoNombre %>)</small></h2>			
	<div class="alert alert-info">
		<a href="depto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if(mensaje.equals("1")){%>
	<div class="alert alert-info">Se modificó el número de plazas</div>
<%	
	}
	if(mensaje.equals("2")){%>
		<div class="alert alert-danger">No se pudo modificar el número de plazas</div>
<%	
	}
	if(mensaje.equals("3")){%>
	<div class="alert alert-danger">No se pudo disminuir el número de Becas Básicas debido a que ya hay <%=numBas%> alumnos asignados en este puesto </div>
<%	
	}if(mensaje.equals("4")){%>
	<div class="alert alert-danger">No se pudo disminuir el número de Becas Industriales debido a que ya hay <%=numInd%> alumnos asignados en este puesto </div>
<%	
	}if(mensaje.equals("5")){%>
	<div class="alert alert-danger">No se pudo disminuir el número de Becas Temporales debido a que ya hay <%=numTem%> alumnos asignados en este puesto </div>
<%	
	}if(mensaje.equals("6")){%>
	<div class="alert alert-danger">No se pudo disminuir el número de Becas Pre Industriales debido a que ya hay <%=numPre%> alumnos asignados en este puesto </div>
<%	
	}if(mensaje.equals("7")){%>
	<div class="alert alert-danger">No se pudo disminuir el número de Becas de Postgrado debido a que ya hay <%=numPos%> alumnos asignados en este puesto </div>
<%	
	}
%>			
	<form id="frmPlaza" name="frmPlaza" method="post" action="grabar">
	<div class="row">
		<div class="col-6">			
			<label for="IdEjercicio">Id del Ejercicio</label>
			<input type="text" id="IdEjercicio" name="IdEjercicio" class="form-control" value="<%=ejercicioId%>" readonly="readonly" />		
			<br>			
			<label for="CentroCosto">Centro de Costo</label>
			<input type="text" id="Ccosto" name="Ccosto" class="form-control" value="<%=ccosto%>" readonly="readonly"/>					
			<br>			
			<label for="Contacto">Contacto</label>
			<input type="text" id="Contacto" name="Contacto" class="form-control" value="<%=becPlazas.getContacto()%>"/>
			<br>			
			<label for="Telefono">Teléfono</label>
			<input type="text" id="Telefono" name="Telefono" class="form-control" value="<%=becPlazas.getTelefono()%>"/>
			<br>			
			<label for="Correo">Correo</label>
			<input type="text" id="Correo" name="Correo" class="form-control" value="<%=becPlazas.getCorreo()%>"/>	
			<br>
		</div>
		<div class="col-6">			
			<label for="NumBasicas">Becas Básicas<strong> (<%=numBas %>)</strong></label>
			<input type="text" id="NumBasicas" name="NumBasicas" class="form-control" value="<%=becPlazas.getNumPlazas()%>"/>
			<br>			
			<label for="NumIndustriales">Becas Industriales<strong> (<%=numInd %>)</strong></label>
			<input type="text" id="NumIndustriales" name="NumIndustriales" class="form-control" value="<%=becPlazas.getNumIndustriales()%>"/>		
			<br>			
			<label for="NumPreindustriales">Becas Pre-Industriales<strong> (<%=numPre %>)</strong></label>
			<input type="text" id="NumPreindustriales" name="NumPreindustriales" class="form-control" value="<%=becPlazas.getNumPreIndustriales() %>"/>		
			<br>			
			<label for="NumPosgrado">Becas Posgrado<strong> (<%=numPos %>)</strong></label>
			<input type="text" id="NumPosgrado" name="NumPosgrado" class="form-control" value="<%=becPlazas.getNumPosgrado()%>"/>		
			<br>			
			<label for="NumTemporales">Becas Temporales<strong> (<%=numTem %>)</strong></label>
			<input type="text" id="NumTemporales" name="NumTemporales" class="form-control" value="<%=becPlazas.getNumTemporales()%>"/>	
			<br>
		</div>
	</div>
	<div class="alert alert-info">
		<fieldset>
			<a class="btn btn-info btn-large" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
		</fieldset>
	</div>
	</form>
</div>
</body>
<script>
	function Guardar(){
		if (document.frmPlaza.IdEjercicio.value != ""
			&& document.frmPlaza.Ccosto.value != ""
			&& document.frmPlaza.NumBasicas.value != ""
			&& document.frmPlaza.NumIndustriales.value != ""
			&& document.frmPlaza.NumTemporales.value != ""
			&& document.frmPlaza.NumPreindustriales.value != ""
			&& document.frmPlaza.Contacto.value != ""
			&& document.frmPlaza.Telefono.value != ""
			&& document.frmPlaza.Correo.value != ""
			&& document.frmPlaza.NumPosgrado.value != "")
		{
			document.frmPlaza.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
</script>
