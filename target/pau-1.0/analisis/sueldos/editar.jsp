<%@page import="aca.est.spring.EstCcosto"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>	
	<script type="text/javascript">	
		function Grabar() {
			if (document.frmEst.Importe.value != "") {				
				document.frmEst.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}
	
	</script>
</head>
<%
	String nombreMaestro					= (String) request.getAttribute("nombreMaestro");
	String mensaje						= (String) request.getAttribute("mensaje");
	String color						= "";	
	aca.est.spring.EstMaestro maestro	= (aca.est.spring.EstMaestro) request.getAttribute("maestro");
	
	if(mensaje.equals("1")){
		color = "alert alert-success";
		mensaje = "Grabado exitosamente !!";
	}else if(mensaje.equals("0")){
		color = "alert alert-warning";
		mensaje = "No pudo grabar !!";
	}
%>
<body>
	<div class="container-fluid">
		<h2>Editar <small class="text-muted fs-4">(<%=nombreMaestro%>)</small></h2>
		<form action="grabar" method="post" name="frmEst">
			<div class="alert alert-info">
				<a href="maestros" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
			</div>
<% 			if(!mensaje.equals("")){%>			
			<div class="<%=color%>">
				<%=mensaje%>
			</div>
<%			}%>
			<div class="form-group">
				<label for="aca.CodigoPersonal">CodigoPersonal</label>
				<input id="Nomina" name="Nomina" value="<%=maestro.getCodigoPersonal()%>" class="form-control" style="width:210px" readonly="readonly"/>
				<br><br>
				<label for="aca.Horas">Horas</label>
				<input id="Horas" value="<%=maestro.getHoras()%>" class="form-control" style="width:210px"  readonly="readonly"/>
				<br><br>
				<label for="Importe">Importe</label>
				<input id="Importe" name="Importe" class="form-control" style="width:210px"  value="<%=maestro.getImporte()%>">
				<br><br>
				<label for="Tipo">Tipo</label>
				<input id="Tipo" name="Tipo" class="form-control" style="width:210px"  value="<%=maestro.getTipo()%>">
				<br><br>
			</div>         
		    <div class="alert alert-info">				
				<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
			</div>	
		</form>
	</div>
</body>
</html>