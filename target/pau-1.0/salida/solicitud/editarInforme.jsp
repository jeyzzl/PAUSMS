<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.salida.spring.SalInforme"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head>	
	<script type="text/javascript">
		function Guardar() {
			if ( document.frmInforme.Incidentes.value != "" && document.frmInforme.Observaciones.value != ""					
				) {				
				document.frmInforme.submit();
			} else {
				alert("Favor de completar todos los campos");
			}			
		}		
	</script>	
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />	
</head>	
<%	
	String usuario 					= (String) session.getAttribute("codigoPersonal");	
	String salidaId	 				= request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");	
	SalInforme salInforme	 		= (SalInforme) request.getAttribute("salInforme");	
	boolean existe	 				= (boolean) request.getAttribute("existe");
	String mensaje 					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<div class="container-fluid">
	<h2>Informe de llegada
<%	if (existe){ %>	
	<small class="text-muted h5">( <%=salInforme.getUsuario()%> - <%=salInforme.getFecha()%>)</small>
<%	} %>		
	</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="solicitud">Regresar</a>
	</div>
<%	if (!mensaje.equals("-")){ %>	
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>		
	<form id="frmInforme" name="frmInforme" action="grabarInforme" method="post">	
	<input type="hidden" name="SalidaId" value="<%=salidaId%>">
	<div class="row">
		<div class="span4">			
			<label for="Incidentes">Incidentes</label>
			<textarea maxlength="999" name="Incidentes" style="width:300px;" class="form-control" id="Incidentes" cols="37" rows="7" placeholder="Descripción breve de los incidentes." required><%=salInforme.getIncidentes()%></textarea>
			<br><br>
			<label for="Observaciones">Observaciones</label>
			<textarea maxlength="999" name="Observaciones" style="width:300px;" class="form-control" id="Incidente" cols="37" rows="7" placeholder="Descripción breve de las observaciones." required><%=salInforme.getObservaciones()%></textarea>	
		</div>		
	</div>
	<br>
	<div class="alert alert-info">
		<a href="javascript:Guardar();" class="btn btn-primary">Grabar</a>
	</div>
	</form>
</div>