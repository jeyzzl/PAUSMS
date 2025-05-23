<%@page import="java.text.*" %>
<%@page import="aca.bec.spring.BecParametro"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<style>
	body{
		background: white;
	}
</style>
<body>
<%
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje"); 
	BecParametro becParametro 	= (BecParametro)request.getAttribute("becParametro");
%>
<div class="container-fluid">
	<h2>Fechas</h2>
	<hr>
<%
	if(mensaje.equals("1")){%>
	<div class="alert alert-info">Las fechas se actualizaron con éxito</div>		
<%	}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">Las fechas no se pudieron actualizar</div>
<%	}else if(mensaje.equals("3")){%>
	<div class="alert alert-danger">Hay un conflicto con las fechas de prepa, verifíquelas</div>
<%	}else if(mensaje.equals("4")){%>
	<div class="alert alert-danger">Hay un conflicto con las fechas de pregrado, verifíquelas</div>
<%	}
%>		
	<form id="frmFecha" name="frmFecha" action="grabar" method="post">
	<div class="row">
		<div class="col-4">
			<label for="PrepaInicio">Inicial prepa<span class="required-indicator">*</span></label>
			<input type="text" data-date-format="dd/mm/yyyy" required id="PrepaInicio" name="PrepaInicio" class="form-control" style="width:280px;" value="<%=becParametro.getPrepaInicio()%>" />
			<br>				
			<label for="PrepaFinal">Final prepa<span class="required-indicator">*</span></label>
			<input type="text" data-date-format="dd/mm/yyyy"  required id="PrepaFinal" name="PrepaFinal" class="form-control" style="width:280px;"  value="<%=becParametro.getPrepaFinal()%>" />
		</div>
		<div class="col-4">
			<label for="PregradoInicio">Inicial pregrado<span class="required-indicator">*</span></label>
			<input type="text" data-date-format="dd/mm/yyyy" required id="PregradoInicio" name="PregradoInicio"  class="form-control" style="width:280px;" value="<%=becParametro.getPregradoInicio()%>"/>
			<br>			
			<label for="PregradoFinal">Final pregrado<span class="required-indicator">*</span></label>
			<input type="text" data-date-format="dd/mm/yyyy" required id="PregradoFinal" name="PregradoFinal" class="form-control" style="width:280px;" value="<%=becParametro.getPregradoFinal()%>" />
		</div>
	</div>
	<div class="alert alert-info">
		<button type="submit" class="btn btn-primary"><spring:message code="aca.Guardar"/></button>						
	</div>
	</form>
</div>
</body>
</html>
<script>
	jQuery('#PrepaInicio').datepicker();
	jQuery('#PrepaFinal').datepicker();
	jQuery('#PregradoInicio').datepicker();
	jQuery('#PregradoFinal').datepicker();
</script>