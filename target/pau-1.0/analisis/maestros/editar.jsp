<%@page import="aca.est.spring.EstCcosto"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>	
	<script type="text/javascript">	
		function Grabar() {
			if (document.frmEst.CodigoPersonal.value != "" && document.frmEst.CodigoPersonal != "" &&
				document.frmEst.CodigoPersonal.value != "" && document.frmEst.CodigoPersonal != "" ) {				
				document.frmEst.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}
	
	</script>
</head>
<%
	// Declaracion de variables	
	String id				= request.getParameter("Id")==null?"0":request.getParameter("Id");
	String facultad			= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");
	String estado			= request.getParameter("Estado")==null?"1":request.getParameter("Estado");
	EstCcosto costo 		= (EstCcosto) request.getAttribute("costo");
%>
<body>
	<div class="container-fluid">
	<h1><spring:message code="aca.GradePoint"/></h1>	
	<form action="grabar" method="post" name="frmEst" target="_self">
	<input name="Id" type="hidden" value="<%=id%>">	
	<input name="Estado" type="hidden" value="<%=estado%>">
	<div class="alert alert-info">
		<a href="maestros?Facultad=<%=costo.getUbicacion()%>&Estado=<%=estado%>&Facultad=<%=facultad%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	
	<div class="form-group">
		<label for="aca.Facultad">Facultad</label>
		<select name="Facultad">
			<option value="0" <%=costo.getUbicacion().equals("1")?"selected":""%>>OTRO</option>
			<option value="1" <%=costo.getUbicacion().equals("1")?"selected":""%>>FACSA</option>
			<option value="2" <%=costo.getUbicacion().equals("2")?"selected":""%>>ESTOMATOLOGÍA</option>
			<option value="3" <%=costo.getUbicacion().equals("3")?"selected":""%>>FIT</option>
			<option value="4" <%=costo.getUbicacion().equals("4")?"selected":""%>>EDUCACION</option>
			<option value="5" <%=costo.getUbicacion().equals("5")?"selected":""%>>FACEJ</option>
			<option value="6" <%=costo.getUbicacion().equals("6")?"selected":""%>>TEOLOGIA</option>
			<option value="7" <%=costo.getUbicacion().equals("7")?"selected":""%>>PREPA</option>
			<option value="8" <%=costo.getUbicacion().equals("8")?"selected":""%>>MUSICA</option>
			<option value="9" <%=costo.getUbicacion().equals("9")?"selected":""%>>UM-VIRTUAL</option>
			<option value="10" <%=costo.getUbicacion().equals("10")?"selected":""%>>PSICOLOGIA</option>
			<option value="11" <%=costo.getUbicacion().equals("11")?"selected":""%>>ARTE</option>			
		</select>
		<br><br>
		<label for="aca.CodigoPersonal">CodigoPersonal</label>
		<input name="CodigoPersonal" type="text" class="input input-xlarge" id="CodigoPersonal" size="40" maxlength="40" required value="<%=costo.getCodigoPersonal()%>">
		<br><br>
		<label for="aca.Horas">Horas</label>
		<input name="Horas" type="text" class="input input-xlarge" id="Horas" size="3" maxlength="3" required value="<%=costo.getHoras()%>">
		<br><br>
		<label for="aca.Total">Tot.Alum.</label>
		<input name="Total" type="text" class="input input-xlarge" id="Total" size="3" maxlength="3" required value="<%=costo.getTotal()%>">
		<br><br>	
		<label for="aca.Alumnos">Alum.</label>
		<input name="Alumnos" type="text" class="input input-xlarge" id="Alumnos" size="3" maxlength="3" required value="<%=costo.getAlumnos()%>">
		<br><br>
		<label for="aca.Porcentaje">Porcentaje</label>	
		<input name="Porcentaje" type="text" class="input input-xlarge" id="Porcentaje" size="4" maxlength="4" required value="<%=costo.getPorcentaje()%>">
		<br><br>		
	</div>         
    <div class="alert alert-info">				
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
	</div>	
	
	</form>
	</div>
</body>
</html>