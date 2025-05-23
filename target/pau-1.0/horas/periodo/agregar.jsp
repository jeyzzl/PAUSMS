<%@page import="aca.emp.spring.EmpPeriodo"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<body>
<%
	EmpPeriodo empPeriodo	= (EmpPeriodo)request.getAttribute("empPeriodo");

	String mensaje			= request.getAttribute("mensaje")==null?"X":(String)request.getAttribute("mensaje");
	String colorMensaje		= request.getAttribute("colorMensaje")==null?"0":(String)request.getAttribute("colorMensaje");
	String accion 			= empPeriodo.getPeriodoId().equals("")?"1":"2";
	
	String periodoId 		= empPeriodo.getPeriodoId().equals("")?"0":empPeriodo.getPeriodoId();
	String periodoNombre 	= empPeriodo.getPeriodoNombre()==null?"":empPeriodo.getPeriodoNombre();
	String fechaIni 		= empPeriodo.getFechaIni()==null?"":empPeriodo.getFechaIni();
	String fechaFin 		= empPeriodo.getFechaFin()==null?"":empPeriodo.getFechaFin();
	
%>
	<div class="container-fluid">
		<div class="alert alert-info">
			<a href="periodo" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
		</div>
<%
	if(!mensaje.equals("X")){
%>
		<div class="alert alert-"<%=colorMensaje%>>
			<%=mensaje%>
		</div>
<%
	}
%>
		<form name="frmPeriodo" action="agregar">
			<input type="hidden" name="Accion" value="<%=accion%>">
			<input type="hidden" name="PeriodoId" value="<%=periodoId%>">
			<div class="row">
				<div class="span3">
					<label for="PeriodoNombre">Nombre</label>			
					<input type="text" name="PeriodoNombre" class="form-control" style="width:200px" id="PeriodoNombre" value="<%=periodoNombre%>" />
					<br><br>
					<label for="FechaIni">Fecha Inicial:</label>			
					<input type="text" name="FechaIni"  class="form-control" style="width:200px"id="FechaIni" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fechaIni%>" />
					<br><br>
					<label for="FechaFin">Fecha Final:</label>			
					<input type="text" name="FechaFin"  class="form-control" style="width:200px" id="FechaFin" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fechaFin%>" />
					<br><br>
				</div>
			</div>
			<br>
			<div class="alert alert-info">
				<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
			</div>
		</form>
		
	</div>
</body>
<script>
	function Guardar() {
		if (document.frmPeriodo.PeriodoNombre.value != "" && document.frmPeriodo.FechaIni.value != "" && document.frmPeriodo.FechaFin.value != "") {
				document.frmPeriodo.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}

	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
</html>