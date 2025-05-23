<%@page import="java.util.List"%>
<%@page import="aca.graduacion.spring.AlumEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		function guardar(){
			if(document.forma.Nombre.value != ""){
				if(document.forma.Fecha.value != ""){
					document.forma.submit();
				}else{
					alert("Llene el campo de \"Fecha\" para poder guardar");					
				}
			}else{
				alert("Llene el campo de \"Nombre\" para poder guardar");
				document.forma.nombre.focus();
			}
		}
	</script>
</head>
<%	
	String eventoId 		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");	
	AlumEvento alumEvento 	= (AlumEvento) request.getAttribute("alumEvento");	
%>
<div class="container-fluid">
	<h2>Editar evento de graduación</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="eventos"><spring:message code="aca.Regresar"/></a>
	</div>
	<form id="forma" name="forma" action="grabarEvento" method="post">
	<input name="EventoId" type="hidden" value="<%=eventoId%>"/>
		<div class="row">
			<div class="col-3">
				<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>					
				<input type="text" class="form-control" id="Nombre" name="Nombre" value="<%=alumEvento.getEventoNombre()%>" size="30" maxlength="70"/>
				<br>
				<label for="Fecha"><spring:message code="aca.Fecha"/> (DD/MM/AAAA):</label>
				<input type="text" class="form-control" id="Fecha" data-date-format="dd/mm/yyyy" name="Fecha" value="<%=alumEvento.getFecha() %>" size="8" maxlength="10" />				
				<br>
				<label for="Estado"><spring:message code="aca.Estado"/>:</label>
				<select name="Estado" class="form-select" style="width:120px;">
					<option value="A" <%=alumEvento.getEstado().equals("A")?"selected":""%>>Activo</option>
					<option value="I" <%=alumEvento.getEstado().equals("I")?"selected":""%>>Inactivo</option>
				</select>
				<br>
				<label for="Archivo"><spring:message code="aca.Archivo"/>:</label>
				<select name="Archivo" class="form-select" style="width:120px;">
					<option value="N" <%=alumEvento.getArchivo().equals("N")?"selected":""%>>NO</option>
					<option value="S" <%=alumEvento.getArchivo().equals("S")?"selected":""%>>SI</option>
				</select>	      		
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<input type="button" class="btn btn-primary" onclick="guardar();" value="Guardar" />
		</div>
	</form>
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>