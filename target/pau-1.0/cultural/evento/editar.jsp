<%@page import="java.util.List"%>
<%@page import="aca.cultural.spring.CompAsistencia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String mensaje					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CompAsistencia compAsistencia 	= (CompAsistencia)request.getAttribute("compAsistencia");
%>
<body>
<div class="container-fluid">
	<h2>Editar eventos</h2>
	<hr>
	<div class="alert alert-info d-flex align-items-center">		
		<a href="listado" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		<%=!mensaje.equals("-")?"&nbsp; "+mensaje:""%>		
	</div>	
	<form name="frmEvento" action="grabar" method="post" >	
	<input name="EventoId" type="hidden" value="<%=compAsistencia.getEventoId()%>">
	<div class="form-group">		
		<label><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="form-control" name="Nombre" type="text" id="Nombre" value="<%=compAsistencia.getNombre()%>" maxlength="40">
		<br>
		<label><strong><spring:message code="aca.Fecha"/></strong></label>
		<input class="form-control" name="Fecha" type="text" id="Fecha" data-date-format="yyyy/mm/dd" maxlength="10" value="<%=compAsistencia.getFecha()%>">
		<br>
		<label><strong><spring:message code="aca.Hora"/></strong></label>		
		<input class="form-control" name="Hora" type="text" id="Hora" maxlength="40" value="<%=compAsistencia.getHora()%>">
		<br>
		<label><strong><spring:message code="aca.Estado"/></strong></label>
		<select name="Estado" class="form-select">
			<option value="A" <%=compAsistencia.getEstado().equals("A")?"selected":""%>>Activo</option>
			<option value="I" <%=compAsistencia.getEstado().equals("I")?"selected":""%>>Inactivo</option>
		</select>		
		<br>
	</div>
	<div class="alert alert-info">
		<a href="javascript:document.frmEvento.submit()" class="btn btn-primary">Grabar</a>
	</div>
	</form>
</body>
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#Fecha').datepicker();
</script>	
</html>