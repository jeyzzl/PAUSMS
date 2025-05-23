<%@ page import="aca.util.Fecha"%>
<%@ page import="java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.financiero.spring.FinPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">

	function Guardar(){					
		if( document.forma.Mensaje.value != "" && document.forma.FechaIni.value != "" && 
			document.forma.FechaFin.value != "" && document.forma.Cantidad.value != ""){			
			document.forma.submit();
		}else{
			alert("Ingrese todos los datos requeridos (*)");
		}		
	}
	
</script>
<%	
	String matricula		= (String) session.getAttribute("codigoAlumno");
	FinPeriodo	periodo		= (FinPeriodo)request.getAttribute("periodo");	
%>	
<div class="container-fluid">
	<h2>Periodo de Finanzas</h2>
	<div class="alert alert-info">
		<a href="periodo" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form id="forma" name="forma" action="grabar" method="post">
	<input name="Cargas" type="hidden" class="form-control" value="<%=periodo.getCargas()%>">	
	<input name="Modalidades" type="hidden" value="<%=periodo.getModalidades()%>">
	<table class="table table-condensed">
	<tr>
		<td width="10%"><spring:message code='aca.Periodo'/>*</td>
		<td width="90%"><input type="text"  id="PeriodoId"  class="form-control" style="width:200px" name="PeriodoId" maxlength="2" size="5" value="<%=periodo.getPeriodoId()%>" readonly/></td>
	</tr>
	<tr>
		<td><spring:message code="aca.FechaInicio"/><b><font color="#AE2113"> *</font></b></td>
		<td>
			<input name="FechaIni" type="text" class="form-control" style="width:200px" id="FechaIni" size="12" maxlength="10" data-date-format="dd/mm/yyyy"  value="<%=periodo.getFechaIni() %>"> 
		</td>
	</tr>
	<tr>
		<td><spring:message code='aca.FechaFinal'/><b><font color="#AE2113"> *</font></b></td>
		<td>
			<input name="FechaFin" type="text" id="FechaFin" size="12" class="form-control" style="width:200px" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=periodo.getFechaFin()%>"> 
		</td>
	</tr>
	<tr>
		<td><spring:message code="aca.Comentario"/><b><font color="#AE2113">*</font></b></td>
		<td><textarea type="text" class="form-control" style="width:480px" id="Mensaje" name="Mensaje" maxlength="500" ><%=periodo.getMensaje()%></textarea></td>
	</tr>
	<tr>
		<td><spring:message code="aca.Estado"/><b><font color="#AE2113"> *</font></b></td>
		<td>
			<select  id="Estado" name="Estado" class="form-select" style="width:200px">										
				<option value="A" <%if(periodo.getEstado().equals("A"))out.print("selected"); %>>Aviso</option>
				<option value="B" <%if(periodo.getEstado().equals("B"))out.print("selected"); %>>Bloqueado</option>
				<option value="I" <%if(periodo.getEstado().equals("I"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>
			</select>					
		</td>
	</tr>
	<tr>
		<td><spring:message code='aca.Cantidad'/><b><font color="#AE2113"> *</font></b></td>
		<td>
			<input name="Cantidad" type="text" class="form-control" style="width:200px" id="Cantidad" size="10" maxlength="7" value="<%=periodo.getCantidad()%>"> 
		</td>
	</tr>
	</table>
	<div class="alert alert-info">
		<a href="javascript:Guardar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
	</div>		
</form>
</div>
<script>
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>