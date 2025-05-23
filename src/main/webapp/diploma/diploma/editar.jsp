<%@page import="java.util.List"%>
<%@page import="aca.diploma.spring.DipCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String mensaje		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	DipCurso diploma 	= (DipCurso)request.getAttribute("dipCurso");
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
	<input name="DiplomaId" type="hidden" value="<%=diploma.getDiplomaId()%>">
	<div class="form-group">		
		<label><strong><spring:message code="aca.Institucion"/></strong></label>
		<input class="form-control" name="Institucion" type="text" id="Institucion" value="<%=diploma.getInstitucion()%>" maxlength="100">
		<br>
		<label><strong><spring:message code="aca.Curso"/></strong></label>
		<input class="form-control" name="Curso" type="text" id="Curso" maxlength="100" value="<%=diploma.getCurso()%>">
		<br>
		<label><strong><spring:message code="aca.Tema"/></strong></label>		
		<input class="form-control" name="Tema" type="text" id="Tema" maxlength="100" value="<%=diploma.getTema()%>">
		<br>
		<label><strong><spring:message code="aca.Horas"/></strong></label>
		<input class="form-control" name="Horas" type="text" id="Horas" maxlength="100" value="<%=diploma.getHoras()%>">
		<br>
		<label><strong><spring:message code="aca.Periodo"/></strong></label>
		<input class="form-control" name="Periodo" type="text" id="Periodo" maxlength="100" value="<%=diploma.getPeriodo()%>">
		<br>
		<label><strong><spring:message code="aca.Fecha"/></strong></label>
		<input class="form-control" name="Fecha" type="text" id="Fecha" maxlength="100" value="<%=diploma.getFecha()%>">		
		<br>
		<label><strong><spring:message code="aca.Firma"/> Izquierda</strong></label>
		<input class="form-control" name="FirmaUno" type="text" id="Firma" maxlength="100" value="<%=diploma.getFirmaUno()%>">	
		<br>
		<label><strong><spring:message code="aca.Firma"/> Derecha</strong></label>
		<input class="form-control" name="FirmaDos" type="text" id="Firma" maxlength="100" value="<%=diploma.getFirmaDos()%>">	
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