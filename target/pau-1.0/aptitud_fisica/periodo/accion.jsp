<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%@ page import= "java.util.* "%>

<jsp:useBean id="periodo" scope="page" class="aca.afis.AfisPeriodo"/>
<jsp:useBean id="periodoUtil" scope="page" class="aca.afis.AfisPeriodoUtil"/>

<%
	String periodoId	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(accion);
	int accionFmt 		= 0;
	
	switch (nAccion) {
	case 1: { // Grabar
		periodo.setPeriodoId(periodoId);
		periodo.setPeriodoNombre(request.getParameter("PeriodoNombre"));
		periodo.setFechaIni(request.getParameter("FechaInicio"));
		periodo.setFechaFin(request.getParameter("FechaFinal"));	
		periodo.setEstado(request.getParameter("Estado"));
		periodo.setFiltro(request.getParameter("Filtro"));
		
		if (!periodoUtil.existeReg(conEnoc, periodoId)) {
			if (periodoUtil.insertReg(conEnoc, periodo)) {
				accionFmt = 1;
			} else {
				accionFmt = 2;
			}
			
		} else {
			if (periodoUtil.updateReg(conEnoc, periodo)) {
				accionFmt = 3;
			} else {
				accionFmt = 4;
			}
		}
		break;
		}
	case 2 ://Elimina
		periodo.setPeriodoId(periodoId);
		if(periodoUtil.existeReg(conEnoc, periodoId)){
			if(periodoUtil.deleteReg(conEnoc, periodoId)){
				accionFmt = 5;
				response.sendRedirect("periodo.?Accion=5");
			}else{
				accionFmt = 6;
			}
		}else{
			accionFmt = 7;
		}
		break;
	}
	
	if ( nAccion == 0){
		periodoId 	= periodoUtil.MaximoReg(conEnoc);
		periodo.setPeriodoId(periodoId);		
	} else{
		periodo = periodoUtil.mapeaRegId(conEnoc, request.getParameter("PeriodoId"));	
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
		<a href="periodo.?PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i><spring:message code='aca.Atras'/></a>
	</div>
	<br />
<%		if(accionFmt==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
		}else if(accionFmt==2){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoGuardar'/></h3></div>
<%	
		}else if(accionFmt==3){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeModifico'/></h3></div>
<%	
		}else if(accionFmt==4){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoModificar'/></h3></div>
<%	
		}else if(accionFmt==5){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	
		}else if(accionFmt==6){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoEliminar'/></h3></div>
<%	
		}else if(accionFmt==7){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoExiste'/></h3></div>
<%	
		}
%>  
	<form id="datos" name="datos" action="accion." method="post">
		<input name="Accion" type="hidden" value="<%=accion%>"/>		
			<div class="row">
				<div class="span4">
					<div class="control-group">
						<label for="PeriodoId"><spring:message code="aca.PeriodoId"/><span class="required-indicator">*</span></label>
						<input type="text" required id="PeriodoId" name="PeriodoId" value="<%=periodo.getPeriodoId()%>"  readonly/>
					</div><br>
					<div class="control-group">
						<label for="PeriodoNombre"><spring:message code="aca.NombrePeriodo"/><span class="required-indicator">*</span></label>
						<input type="text" required id="PeriodoNombre" name="PeriodoNombre" value="<%=periodo.getPeriodoNombre()%>" />
					</div><br>
					<div class="control-group">
						<label for="FechaInicio"><spring:message code='aca.FechaInicial'/><span class="required-indicator">*</span></label>
						<input type="text" data-date-format="dd/mm/yyyy" required id="FechaInicio" name="FechaInicio" value="<%=periodo.getFechaIni()%>" />
					</div><br>
					<div class="control-group">
						<label for="FechaFinal"><spring:message code='aca.FechaFinal'/><span class="required-indicator">*</span></label>
						<input type="text" data-date-format="dd/mm/yyyy" required id="FechaFinal" name="FechaFinal" value="<%=periodo.getFechaFin()%>" />
					</div><br>
					<div class="control-group">
						<label for="Filtro"><spring:message code="aca.Acceso"/><span class="required-indicator">*</span></label>
						<select style="width: 100px" id="Filtro" name="Filtro" >
							<option value="TODO" <%=periodo.getFiltro().equals("TODO")?"selected":""%>>TODOS</option>
							<option value="FGAF133" <%=periodo.getFiltro().equals("FGAF134")?"selected":""%>>FGAF133</option>
							<option value="FGAF134" <%=periodo.getFiltro().equals("FGAF134")?"selected":""%>>FGAF134</option>
							<option value="FGAF233" <%=periodo.getFiltro().equals("FGAF233")?"selected":""%>>FGAF233</option>
							<option value="FGAF234" <%=periodo.getFiltro().equals("FGAF234")?"selected":""%>>FGAF234</option>
							<option value="FGAF333" <%=periodo.getFiltro().equals("FGAF333")?"selected":""%>>FGAF333</option>
							<option value="FGAF334" <%=periodo.getFiltro().equals("FGAF334")?"selected":""%>>FGAF334</option>
						</select>
					</div>
					<br>
					<div class="control-group">
						<label for="estado"><spring:message code="aca.Estado"/><span class="required-indicator">*</span></label>
						<select style="width: 100px" id="Estado" name="Estado" >
							<option value="A" <%if(periodo.getEstado().equals("A"))out.print("selected"); %>><spring:message code='aca.Activo'/></option>
							<option value="I" <%if(periodo.getEstado().equals("I"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>
						</select>
					</div>					
				</div>
			</div>
			<div class="alert alert-info">
				<a onclick="Grabar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
			</div>
		</form>
</div>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>

<script type="text/javascript">
	
	function Grabar() {
		if(document.datos.PeriodoId.value != ""
		&& document.datos.PeriodoNombre.value != ""
		&& document.datos.FechaInicio.value != ""
		&& document.datos.FechaFinal.value != ""
		&& document.datos.Estado.value != ""){
			
			document.datos.Accion.value = "1";
			document.datos.submit();
		}else{
			alert("Llene todos los campos");
		}
	}
	
</script>
<%@ include file= "../../cierra_enoc.jsp" %>