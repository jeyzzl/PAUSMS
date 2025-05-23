<%@page import="java.util.List"%>
<%@page import="aca.rotaciones.spring.RotInstitucion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
	<script type="text/javascript">
	function guardar(){
		if(document.forma.Nombre.value != ""){
				document.forma.submit();					
		}else{
			alert("Llene el campo de \"Nombre\" para poder guardar");
			document.forma.nombre.focus();
		}
	}	
	</script>
</head>
<%	
	String institucionId 			= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");
	RotInstitucion rotInstitucion	= (RotInstitucion) request.getAttribute("rotInstitucion");
%>
<div class="container-fluid">
	<h1>Editar institución</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="institucion"><spring:message code="aca.Regresar"/></a>
	</div>
	<form id="forma" name="forma" action="grabarInstitucion" method="post">
	<input name="InstitucionId" type="hidden" value="<%=institucionId%>"/>
		<div class="row container-fluid">
			<div class="span3">
				<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>					
				<input type="text" class="form-control" id="Nombre" name="Nombre" value="<%=rotInstitucion.getInstitucionNombre()%>" size="30" maxlength="70"/>
				<br><br>
			</div>
		</div>		
		<div class="alert alert-info">
			<input type="button" class="btn btn-primary" onclick="guardar();" value="Guardar" />
		</div>
	</form>
</div>
