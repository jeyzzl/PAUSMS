<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.*" %>

<jsp:useBean id="AlertaAntro" class="aca.alerta.AlertaAntro" scope="page" />
<jsp:useBean id="AlertaAntroU" class="aca.alerta.AlertaAntroUtil" scope="page" />

<html>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<style>
	body{
		background: white;
	} 
	
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
	
	.sintomas label, .sintomas input{
		display: inline-block;
		margin-right: 5px;
	}
</style>
<head>	
</head>
<body>

<%
	String periodoId 	  	= (String) session.getAttribute("periodoSanitaria");
	String codigoPersonal 	= request.getParameter("matricula")==null?(String) session.getAttribute("matricula"):request.getParameter("matricula"); 
	String accion 		  	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion  		  	= Integer.parseInt(accion);	
	int accionFmt		  	= 0;
	
	AlertaAntro.setPeriodoId(periodoId);
	if (AlertaAntroU.existeReg(conEnoc, periodoId, codigoPersonal)){
		AlertaAntro = AlertaAntroU.mapeaRegId(conEnoc, periodoId, codigoPersonal);
	}	
	//System.out.println("Alumno:"+codigoPersonal);
	String msj			= "";
	
	switch (nAccion) {

		case 1: { // Grabar			
			AlertaAntro.setPeriodoId(periodoId);
			AlertaAntro.setCodigoPersonal(codigoPersonal);
			
			AlertaAntro.setGrasa(request.getParameter("Grasa"));
			AlertaAntro.setImc(request.getParameter("Imc"));
			AlertaAntro.setMusculo(request.getParameter("Musculo"));
			AlertaAntro.setPeso(request.getParameter("Peso"));
			AlertaAntro.setCintura(request.getParameter("Cintura"));
			AlertaAntro.setPresion(request.getParameter("Presion"));
			AlertaAntro.setTalla(request.getParameter("Talla"));
			
			if(!AlertaAntroU.existeReg(conEnoc, periodoId, codigoPersonal )){
				if(AlertaAntroU.insertReg(conEnoc, AlertaAntro)) {					
					accionFmt = 1;
	
				}else{
					accionFmt = 2;
					msj = "<div class='alert alert-danger'>Ocurrió un error al guardar el registro</div>";
				}
			}else{
				if(AlertaAntroU.updateReg(conEnoc, AlertaAntro)){
					accionFmt = 3;
					msj = "<div class='alert alert-success'>Registro Actualizado</div>";
				}else{
					accionFmt = 4;
					msj = "<div class='alert alert-danger'>Ocurrió un error al actualizar el registro</div>";
				}
			}
			msj = "<div class='alert alert-success'>Redireccionando...</div>";
%>
			<META HTTP-EQUIV="Refresh" CONTENT="3; URL=vacunas?matricula=<%=request.getParameter("matricula")%>">;
	<%		
		}
	}
	
	boolean existe = AlertaAntroU.existeReg(conEnoc, periodoId, codigoPersonal);
%>
	<div class="container-fluid">
	<%	
		if(existe){ 
	%>
	
	<%	}else{
			out.print("&nbsp;");
		} 
	%>
		<h2><spring:message code='aca.Antropometricas'/><small >( <%=aca.alerta.AlertaPeriodoUtil.getPeriodoNombre(conEnoc, periodoId) %> )</small><small> - <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoPersonal, "")%></small></h2>
		<div class="alert alert-info">
			<a href="datos" class="btn btn-primary"><spring:message code="aca.Datos"/></a>&nbsp;&nbsp;
			<a href="historial?matricula=<%=codigoPersonal%>" class="btn btn-success"><span class="icon icon-white icon-arrow-left" ></span>&nbsp;<spring:message code='aca.Anterior'/></a>&nbsp;&nbsp;
			<a href="vacunas?matricula=<%=codigoPersonal%>" class="btn btn-success"><spring:message code='aca.Siguiente'/>&nbsp;<span class="icon icon-white icon-arrow-right" ></span></a>&nbsp;&nbsp;
		</div>
		<%if(existe){%>
	<%} %>
		<%if(!msj.equals("")){%>
			<%=msj %>
		<%} %>
		<form id="datos" name="datos" action="antro" method="post">
			<input name="Accion" type="hidden" value="<%=accion%>"/>
			<input type="hidden" required id="matricula" name="matricula" value="<%=codigoPersonal %>" />
			
			<div class="row">
				<div class="span4" align="left">						
					<div class="control-group">
						<label for="Peso"><spring:message code='aca.Peso'/> (kg)<span class="required-indicator"> *</span></label>
						<input type="text" size="5" class="form-control" required id="Peso" name="Peso" value="<%=AlertaAntro.getPeso() %>"/>
					</div><br>
					<div class="control-group">
						<label for="Talla"><spring:message code='aca.Talla'/> (m)<span class="required-indicator"> *</span></label>
						<input type="text"  class="form-control" required id="Talla" name="Talla" value="<%=AlertaAntro.getTalla() %>" />
					</div><br>
					<div class="control-group">
						<label for="Imc"><spring:message code='aca.IMC'/></label>
						<input type="text" required id="Imc" name="Imc" value="<%=AlertaAntro.getImc()%>" size="5px" readonly />&nbsp;
						<a href="javascript:ModificarIMC()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>
					</div><br>
					<div class="control-group">
						<label for="Cintura"><spring:message code='aca.Cintura'/><span class="required-indicator"></span></label>
						<input type="text" size="40" class="form-control" required id="Cintura" name="Cintura" value="<%=AlertaAntro.getCintura() %>" size="" />
					</div>
					<br>
				</div>
				<div class="span4" align="left">
					<div class="control-group">
						<label for="Grasa"><spring:message code='aca.Grasa'/></label>
						<input type="text" class="form-control" required id="Grasa" name="Grasa" value="<%=AlertaAntro.getGrasa()%>" />
					</div><br>
					<div class="control-group">
						<label for="Musculo"><spring:message code='aca.Musculo'/></label>
						<input type="text" class="form-control" required id="Musulo" name="Musculo" value="<%=AlertaAntro.getMusculo()%>" />
					</div><br>
					<div class="control-group">
						<label for="Presion"><spring:message code='aca.Presion'/></label>
						<input type="text" class="form-control" required id="Presion" name="Presion" value="<%=AlertaAntro.getPresion()%>" />
					</div>
				</div>	
			</div>
			<div class="alert alert-info">
				<a onclick="javascript:Grabar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
			</div>
		</form>
	</div>
</body>
</html>
<script>
	
	function ModificarIMC(){
		if(document.datos.Peso.value != "" 
		&& document.datos.Talla.value != ""){
			var imc 	= 0;
			
			imc =  document.datos.Peso.value / (document.datos.Talla.value * document.datos.Talla.value);
			imc = imc.toFixed(2);
			
			document.datos.Imc.value = imc;
		}else{
			alert("Llene correctamente el formulario");
		}		
	}	
	
	function Grabar(){
		if(document.datos.Peso.value != "" 
		&& document.datos.Talla.value != ""
		&& document.datos.Imc.value != ""
		&& document.datos.Cintura.value != ""
		&& document.datos.Grasa.value != ""
		&& document.datos.Musculo.value != ""
		&& document.datos.Presion.value != ""
		){		 
			document.datos.Accion.value = "1";
			document.datos.submit();
			
		}else{
			alert("Llene correctamente el formulario");
		}		
	}
</script>
<script>
	jQuery('.nav-pills').find('.antro').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>