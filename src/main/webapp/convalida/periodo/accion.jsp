<%@page import="aca.conva.spring.ConvPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">	
	function Grabar(){
		if(document.frmPeriodo.PeriodoId != "" && document.frmPeriodo.PeriodoNombre!=""){
			document.frmPeriodo.submit();
		}else{
			alert("¡Complete el formulario! ");
		}
	}	
</script>
<%
	// Declaracion de variables	
	String codigoUsuario 	= (String) session.getAttribute("codigoPersonal");
	
	String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");;
	
	boolean existe			= (boolean)request.getAttribute("existe");
	ConvPeriodo convPeriodo	= (ConvPeriodo)request.getAttribute("convPeriodo");
%>
<!-- inicio de estructura -->
<html>
<div class="container-fluid">
	<h2>Añadir Periodo</h2>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<% 	}%>	
	<form name="frmPeriodo" action="grabar" method="post">
		<input type="hidden" name="PeriodoId" value="<%=periodoId%>">			    
	    <label>Nombre:</label>		    
	    <input name="PeriodoNombre" type="text" class="form-control" id="PeriodoNombre" size="20" maxlength="40" value="<%=convPeriodo.getPeriodoNombre()%>">
		<br>
	    <label>Fecha Inicial:</label>
	    <input name="FechaIni" type="text" id="FechaIni" class="form-control" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=convPeriodo.getFechaIni()%>">
	    <br>
	    <label>Fecha Final:</label>
	    <input name="FechaFin" type="text" id="FechaFin" class="form-control" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=convPeriodo.getFechaFin()%>">
	    <br>    
		<div class="alert alert-info">
			<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a> &nbsp;
		</div>
	</form>
</div>
</body>
<script>
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
</html>