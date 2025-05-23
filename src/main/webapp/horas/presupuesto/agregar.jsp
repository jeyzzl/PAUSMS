<%@page import="aca.emp.spring.EmpHorasPres"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<body>
<%
	String cargaId			= (String) request.getAttribute("cargaId");
	String departamentoId	= (String) request.getAttribute("departamentoId");
	EmpHorasPres empHorasPres = (EmpHorasPres) request.getAttribute("empHorasPres");
%>
<div class="container-fluid">
	<h2>Editar Presupuesto <small class="text-muted fs-4" >(<%=cargaId%>)</small></h2>
	<div class="alert alert-info">
		<a href="lista" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>
	</div>
	<form name="frmPres" action="grabar" method="post">
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
		

	<div class="row">
		<div class="col-2" >
			<label for="DepartamentoId">Departamento:</label>
			<input type="text" name="DepartamentoId" id="DepartamentoId" class="form-control" style="width:350px" size="30" maxlength="30" value="<%=empHorasPres.getDepartamentoId()%>" />
			<br><br>
			<label for="Year">Año:</label>
			<input type="text" name="Year" id="Year" size="12" class="form-control" style="width:350px" maxlength="4" value="<%=empHorasPres.getYear()%>" />
			<br><br>
			<label for="Importe">Saldo Ant.:</label>
			<input type="text" name="SaldoAnt" id="SaldoAnt" size="12"  class="form-control" style="width:350px" maxlength="10" value="<%=empHorasPres.getSaldoAnt()%>" />
			<br><br> 
			<label for="Importe">Importe:</label>
			<input type="text" name="Importe" id="Importe" size="12"  class="form-control" style="width:350px" maxlength="10" value="<%=empHorasPres.getImporte()%>" />
		</div>
	</div>
	<br>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
	</div>
	</form>
</div>

<script>
	function Guardar() {
		if (document.frmPres.DepartamentoId.value != "") {
			document.frmPres.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
</script>  	
</html>