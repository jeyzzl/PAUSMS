<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumAtuendo"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<%
	AlumAtuendo atuendo = (AlumAtuendo) request.getAttribute("atuendo");
	String atuendoId 	= (String) request.getAttribute("atuendoId");
	
	if(!atuendo.getAtuendoId().equals("")){
		atuendoId = atuendo.getAtuendoId();
	}
%>
<body>
<div class="container-fluid">	
	<h2>Atuendos para graduación</h2>
	<div class="alert alert-info"><a href="atuendos" class="btn btn-primary">Regresar </a></div>	
	<form action="grabar">
		<label>Id</label>
		<input name="AtuendoId" type="text" class="form-control" value="<%=atuendoId%>" readonly="readonly" style="width:120px"><br>
		<label>Descripcion</label>
		<input name="Descripcion" type="text" class="form-control" value="<%=atuendo.getDescripcion()%>" required="required" style="width:400px"><br>
		<label>Precio</label>
		<input name="Precio" type="text" class="form-control" value="<%=atuendo.getPrecio()%>" required="required" style="width:120px"><br>		
	</form>
	<div class="alert alert-info"><input type="submit" class="btn btn-primary" value="Guardar"></a></div>	
</div>
</body>
</html>
