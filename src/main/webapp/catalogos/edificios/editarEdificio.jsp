<%@page import="aca.catalogo.spring.CatEdificio"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
function Grabar(){
	if(document.frmEdificio.EdificioId.value!=""
	 && document.frmEdificio.NombreEdificio.value!="" ){	
		document.frmEdificio.submit();
	}else{
		alert("Fill out the entire form");
	}
}
</script>
<%
	// Declaracion de variables	
	CatEdificio catEdificio		= (CatEdificio) request.getAttribute("catEdificio");	
		String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	//List<Maestros> lisMaestros = (List<Maestros>)request.getAttribute("lisMaestros");	
	//HashMap<String, String> mapaMaestros 	= (HashMap<String,String>)request.getAttribute("mapaMaestros");
%>
<html>
<div class="container-fluid">
	<h2><spring:message code="catalogos.edificio.Titulo2"/></h2>
	<div class="alert alert-info"> 
		<a class="btn btn-primary" href="edificios"><i class="fas fa-arrow-left"></i></a> 
		<%if (!mensaje.equals("-")) out.print(mensaje);%>	
	</div>
	<form name="frmEdificio" action="grabarEdificio" method="post">
	<div class="row">
		<div class="span3">
			<label for="EdificioId"><spring:message code="aca.Clave"/>:</label>
			<input name="EdificioId" type="text" class="form-control" id="EdificioId" size="3" maxlength="3" value="<%=catEdificio.getEdificioId()%>" readonly>
			<br><br>
			<label for="NombreEdificio">Building Name:</label>
			<input name="NombreEdificio" type="text" class="form-control" id="NombreEdificio" size="20" maxlength="40" value="<%=catEdificio.getNombreEdificio()%>">				
			<br><br>
		</div>
	</div>
	<div class="alert alert-info">
			<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a> &nbsp;&nbsp;
		</div>
	</form>
</div>
</body>
</html>