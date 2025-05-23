<%@ page import= "java.util.* "%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<jsp:useBean id="periodo" scope="page" class="aca.alerta.AlertaPeriodo"/>
<jsp:useBean id="PeriodoU" scope="page" class="aca.alerta.AlertaPeriodoUtil"/>
<%	
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(accion);
	int accionFmt 		= 0;
	
	periodo = PeriodoU.mapeaRegId(conEnoc, request.getParameter("periodoId"));	
	
	switch (nAccion) {
		case 1: { // Grabar
			periodo.setPeriodoId(request.getParameter("periodoId"));
			periodo.setPeriodoNombre(request.getParameter("periodoNombre"));
			periodo.setFechaIni(request.getParameter("fechaInicio"));
			periodo.setFechaFin(request.getParameter("fechaFinal"));	
			periodo.setModalidades(request.getParameter("modalidades"));
			periodo.setEstado(request.getParameter("estado"));
			periodo.setExcepto(request.getParameter("excepto"));
			
			if (!PeriodoU.existeReg(conEnoc, request.getParameter("periodoId"))) {
				
				if (PeriodoU.insertReg(conEnoc, periodo)) {
					accionFmt = 1;
				} else {
					accionFmt = 2;
				}
				
			} else {
				if (PeriodoU.updateReg(conEnoc, periodo)) {
					accionFmt = 3;
				} else {
					accionFmt = 4;
				}
			}
			break;
			}
		case 2 ://Elimina
			periodo.setPeriodoId(request.getParameter("periodoId"));
			if(PeriodoU.existeReg(conEnoc, request.getParameter("periodoId"))){
				if(PeriodoU.deleteReg(conEnoc, request.getParameter("periodoId"))){
					accionFmt = 5;
					out.print("<div class='alert alert-success'><b>Grabado...<a class='btn btn-primary' href='periodos'>Regresar</a></div>");					
				}else{
					accionFmt = 6;
				}
			}else{
				accionFmt = 7;
			}
			break;
	}
	
%>
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">
	<h1><spring:message code='aca.AñadirPeriodos'/></h1>
	<div class="alert alert-info">
		<a href="periodos" class="btn btn-primary"><i class="fas fa-arrow-left"></i><spring:message code='aca.Atras'/></a>
	</div>
	<br />
	<%		if(accionFmt==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
}		if(accionFmt==2){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoGuardar'/></h3></div>
<%	
}		if(accionFmt==3){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeModifico'/></h3></div>
<%	
}		if(accionFmt==4){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoModificar'/></h3></div>
<%	
}		if(accionFmt==5){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	
}		if(accionFmt==6){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoEliminar'/></h3></div>
<%	
}		if(accionFmt==7){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoExiste'/></h3></div>
<%	
}
%>  
	<form id="datos" name="datos" action="accion" method="post">
		<input name="Accion" type="hidden" value="<%=accion%>"/>
			<div class="row">
				<div class="col-4">
						<div class="control-group">
							<label for="periodoId"><spring:message code="aca.Periodo"/><span class="required-indicator">*</span></label>
							<input type="text" class="form-control" required id="periodoId" name="periodoId" value="<%=periodo.getPeriodoId()%>"/>
						</div><br>
						<div class="control-group">
							<label for="periodoNombre"><spring:message code="aca.NombrePeriodo"/><span class="required-indicator">*</span></label>
							<input type="text" class="form-control" required id="periodoNombre" name="periodoNombre" value="<%=periodo.getPeriodoNombre()%>" />
						</div><br>
						<div class="control-group">
							<label for="fechaInicio"><spring:message code='aca.FechaInicial'/><span class="required-indicator">*</span></label>
							<input type="text" class="form-control" data-date-format="dd/mm/yyyy" required id="fechaInicio" name="fechaInicio" value="<%=periodo.getFechaIni()%>" />
						</div>
				</div>
				<div class="col-4">
					<div class="control-group">
						<label for="fechaFinal"><spring:message code='aca.FechaFinal'/><span class="required-indicator">*</span></label>
						<input type="text" class="form-control" data-date-format="dd/mm/yyyy" required id="fechaFinal" name="fechaFinal" value="<%=periodo.getFechaFin()%>" />
					</div><br>
					<div class="control-group">
						<label for="modalidades"><spring:message code="aca.Modalidades"/><span class="required-indicator">*</span> &nbsp;&nbsp;&nbsp; <small class="text-muted">ejem: -1-2-3-</small></label>
						<input type="text" class="form-control" required id="modalidades" name="modalidades" value="<%=periodo.getModalidades()%>" />
					</div><br>
					<div class="control-group">
						<label for="estado"><spring:message code="aca.Estado"/><span class="required-indicator">*</span></label>
						<select class="form-select" style="width: 120px" id="estado" name="estado" >
							<option value="A" <%if(periodo.getEstado().equals("A"))out.print("selected"); %>><spring:message code='aca.Activo'/></option>
							<option value="I" <%if(periodo.getEstado().equals("I"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>
						</select>
					</div>					
				</div>
			<div class="col-4">
				<div class="control-group">
					<label for="excepto">Excepto</label>
					<input type="text" class="form-control" required id="excepto" name="excepto" value="<%=periodo.getExcepto()%>" />
				</div>
				
			</div>
			</div>
			<div class="alert alert-info">
				<a onclick="javascript:Grabar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
			</div>
		</form>
</div>
<script>
	jQuery('#fechaInicio').datepicker();
	jQuery('#fechaFinal').datepicker();
</script>

<script type="text/javascript">
	function Grabar() {
		if(document.datos.periodoId.value != ""
		&& document.datos.periodoNombre.value != ""
		&& document.datos.fechaInicio.value != ""
		&& document.datos.fechaFinal.value != ""
		&& document.datos.modalidades.value != ""
		&& document.datos.estado.value != ""
		&& document.datos.excepto.value != ""){
			
			document.datos.Accion.value = "1";
			document.datos.submit();		
	}else{
			alert("Llene todos los campos");
		}
	}	
</script>
<%@ include file= "../../cierra_enoc.jsp" %>