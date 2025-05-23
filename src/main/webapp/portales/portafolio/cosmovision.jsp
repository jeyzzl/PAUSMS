<%@page import="aca.portafolio.spring.PorPeriodo"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@page import="java.util.List"%>

<jsp:useBean id="PorCosmovision" scope="page"
	class="aca.portafolio.PorCosmovision" />

<%@ include file="menuPortal.jsp"%>

<script>
	function guardar(){
		if(
		  	document.forma.filosofia.value 	!= "" &&
		  	document.forma.mision.value 	!= "" &&
		  	document.forma.vision.value 	!= "" &&
		  	document.forma.valores.value 	!= "" &&
		  	document.forma.reflexion.value 	!= "" 
		  ){
				document.forma.Accion.value = "1";
				document.forma.submit();
		   }else{
			   alert("Todos los campos son requeridos");
		   }
	}
</script>


<%
	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
	String periodoId		= (String) session.getAttribute("porPeriodo");
	
	List<aca.portafolio.spring.PorPeriodo> lisPeriodos	= (List<aca.portafolio.spring.PorPeriodo>)request.getAttribute("lisPeriodos");
	
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String msj 				= "";
	
	PorCosmovision.setCodigoPersonal(codigoPersonal);
	PorCosmovision.setPeriodoId(periodoId);
	
	if( accion.equals("1") ){
		PorCosmovision.setFilosofia(request.getParameter("filosofia"));
		PorCosmovision.setMision(request.getParameter("mision"));
		PorCosmovision.setVision(request.getParameter("vision"));
		PorCosmovision.setValores(request.getParameter("valores"));
		PorCosmovision.setReflexion(request.getParameter("reflexion"));
		
		if(PorCosmovision.existeReg(conEnoc)){
			if(PorCosmovision.updateReg(conEnoc)){
				msj = "<div class='alert alert-success'>Se actualizó la información</div>";
			}else{
				msj = "<div class='alert alert-danger'>Ocurrió un error al actualizar la información</div>";
			}
		}else{
			if(PorCosmovision.insertReg(conEnoc)){
				msj = "<div class='alert alert-success'>Se guardó la información</div>";
			}else{
				msj = "<div class='alert alert-danger'>Ocurrió un error al guardar la información</div>";
			}
		}
		
	}
	
	if(PorCosmovision.existeReg(conEnoc)){
		PorCosmovision.mapeaRegId(conEnoc, codigoPersonal, periodoId);
	}
	
%>

<style>
h3 {
	margin-bottom: 10px;
}
</style>

<div class="container-fluid">
		<form name="formPeriodo" action="cosmovision">
			Elige periodo: 
			<select name="PeriodoId" onchange="javaScritp:cambioPeriodo()" >
			<% for(PorPeriodo periodo : lisPeriodos){%>
				<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getPeriodoNombre()%></option>
			<% }%>
			</select>&nbsp;&nbsp;&nbsp;
			<a href="javaScript:traspasar();" class="btn btn-success">Traspazar a...</a>&nbsp;&nbsp;&nbsp;
			<select name="traspasaPeriodoId" >
			<% for(PorPeriodo periodo : lisPeriodos){%>
				<option value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>
			<% }%>
			</select>&nbsp;&nbsp;&nbsp;
		</form>
		<h2>
			Cosmovisión <small>(<%=periodoId%>)
			</small>
		</h2>
	
		<%=msj %>
	
		<hr />
	
		<form action="" method="post" name="forma">
			<input type="hidden" name="Accion" />
	
			<div
				style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
				class="alert alert-info ">
	
				<h3>1. Filosofía</h3>
				<textarea name="filosofia" id="filosofia" rows="2"
					style="width: 100%;"><%=PorCosmovision.getFilosofia() %></textarea>
	
			</div>
	
			<div style="clear: both;"></div>
			<br>
	
			<div
				style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
				class="alert alert-info ">
	
				<h3>2. Misión</h3>
				<textarea name="mision" id="mision" rows="2" style="width: 100%;"><%=PorCosmovision.getMision() %></textarea>
	
			</div>
	
			<div style="clear: both;"></div>
			<br>
	
			<div
				style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
				class="alert alert-info ">
	
				<h3>3. Visión</h3>
				<textarea name="vision" id="vision" rows="2" style="width: 100%;"><%=PorCosmovision.getVision() %></textarea>
	
			</div>
	
			<div style="clear: both;"></div>
			<br>
	
			<div
				style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
				class="alert alert-info ">
	
				<h3>4. Valores</h3>
				<textarea name="valores" id="valores" rows="2" style="width: 100%;"><%=PorCosmovision.getValores() %></textarea>
	
			</div>
	
			<div style="clear: both;"></div>
			<br>
	
			<div
				style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
				class="alert alert-info ">
	
				<h3>5. Reflexión</h3>
				<textarea name="reflexion" id="reflexion" rows="2"
					style="width: 100%;"><%=PorCosmovision.getReflexion() %></textarea>
	
			</div>
	
			<div style="clear: both;"></div>
			<br>
	
			<div class="alert alert-info">
				<a class="btn btn-primary btn-large" href="javascript:guardar();"><i
					class="icon-white icon-ok"></i> Guardar</a>
			</div>
	
		</form>
</div>

<link rel="stylesheet" href="../../js/maxlength/jquery.maxlength.css" />
<script src="../../js/maxlength/jquery.maxlength.min.js"></script>
<script>
	jQuery('#filosofia').maxlength({ 
	    max: 2000
	});
	jQuery('#mision').maxlength({ 
	    max: 2000
	});
	jQuery('#vision').maxlength({ 
	    max: 2000
	});
	jQuery('#valores').maxlength({ 
	    max: 2000
	});
	jQuery('#reflexion').maxlength({ 
	    max: 2000
	});

	function cambioPeriodo(){	
		document.formPeriodo.submit();
	}
</script>

<script>
	jQuery('.cosmovision').addClass('active');

	function traspasar(){

		var periodoId = document.formPeriodo.traspasaPeriodoId.value;
		var filosofia = document.forma.filosofia.value;
		var mision = document.forma.mision.value;
		var vision = document.forma.vision.value;
		var valores = document.forma.valores.value;
		var reflexion = document.forma.reflexion.value;

		window.location.href = "cosmovisionTraspasar?PeriodoId="+periodoId+"&Filosofia="+filosofia+"&Mision="+mision+"&Vision="+vision+"&Valores="+valores+"&Reflexion="+reflexion;
	}
	
</script>
<%@ include file="../../cierra_enoc.jsp"%>