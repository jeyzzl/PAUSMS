<%@ page import="aca.catalogo.spring.CatEstado"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
<%
	String paisId 			= (String)request.getAttribute("paisId");
	CatEstado estado 		= (CatEstado)request.getAttribute("estado");
	String mensaje 	 		= (String)request.getAttribute("mensaje");
	
	if(mensaje.equals("1")){
		mensaje = "Saved";
	}else if(mensaje.equals("2")){
		mensaje = "Updated";
	}
%>
	<body>
	<div class="container-fluid">
	<h1><spring:message code="catalogos.paises.Estados"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="estados?PaisId=<%=paisId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 		if(!mensaje.equals("0")){%>
	<div class="alert alert-success">
		<%=mensaje%>
	</div>
<% 		}%>
		<form action="grabarEstado" method="post" name="frmestado" target="_self">
			<input type="hidden" name="PaisId" value="<%=paisId%>">
			<label><spring:message code="aca.Clave"/>:</label>
			<input class="form-control" name="EstadoId" type="text" id="EstadoId" size="3" maxlength="3" value="<%=estado.getEstadoId()%>" readonly="readonly">
			<br>
			<label><spring:message code="aca.Nombre"/></label>
			<input class="form-control" name="NombreEstado" type="text" id="NombreEstado" value="<%=estado.getNombreEstado()%>" size="40" maxlength="40">
			<br>
			<label><spring:message code="aca.NombreCorto"/></label>
			<input class="form-control" name="NombreCorto" type="text" id="NombreCorto" value="<%=estado.getCorto()%>" size="40" maxlength="40">
			<br>
			<label>COVID-19 <spring:message code="aca.Semaforo"/></label>
			<select name="Semaforo" class="form-select">
				<option value="1" <%=estado.getSemaforo().equals("1") ? "selected" : ""%>><spring:message code="aca.Rojo"/></option>
				<option value="2" <%=estado.getSemaforo().equals("2") ? "selected" : ""%>><spring:message code="aca.Naranja"/></option>
				<option value="3" <%=estado.getSemaforo().equals("3") ? "selected" : ""%>><spring:message code="aca.Amarillo"/></option>
				<option value="4" <%=estado.getSemaforo().equals("4") ? "selected" : ""%>><spring:message code="aca.Verde"/></option>
			</select>
			<br>
			<label>GOB ID</label>
			<input class="form-control" name="ClaveSep" type="text" value="<%=estado.getSepId()%>" size="40" maxlength="40" readonly>
			<br>
			<label>GOB Name</label>
			<input class="form-control" name="NombreSep" type="text" value="<%=estado.getSepCorto()%>" size="40" maxlength="40" readonly>
			<br>
			<div class="alert alert-info">
				<input class="btn btn-info" type="submit" value="Save">
			</div>
		</form>
		</div>
	</body>
</html>