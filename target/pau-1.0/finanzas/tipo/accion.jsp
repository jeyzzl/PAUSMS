<%@page import="java.text.*"%>
<%@page import="aca.catalogo.spring.CatDescuento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<html>
<style>
	body{
		background: white;
	} 
</style>
<body>
<%
	CatDescuento catDescuento 	= (CatDescuento) request.getAttribute("catDescuento");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<div class="container-fluid">
	<h2>Agregar Alumnos con Descuento</h2>		
	<div class="alert alert-info">
		<a href="descuentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if (!mensaje.equals("-")){%>
	<%=mensaje %>
<%	} %>	
	<form name="frmDescuento" action="grabar" method="post">	
	<input name="DescuentoId" type="hidden" value="<%=catDescuento.getDescuentoId()%>"/>		
	<div class="row">
		<div class="col-6">			
			<label for="Nombre">Descuento Nombre<span class="required-indicator">*</span></label>
			<input name="Nombre" type="text" class="form-control" value="<%=catDescuento.getDescuentoNombre()%>" required/>
			<br>
			<label for="Usuario"><spring:message code='aca.Usuario'/><span class="required-indicator">*</span></label>
			<input name="Usuario" type="text" class="form-control"  value="<%=catDescuento.getUsuarios()%>" required/>
			<br>
		</div>
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Grabar"/></a>
	</div>
	</form>
</div>
</body>
</html>
<script>
	 function Grabar(){
		if(document.frmDescuento.DescuentoId.value != ""
		&& document.frmDescuento.Nombre.value !=""
		&& document.frmDescuento.Usuario.value !=""){				
				document.frmDescuento.submit();
		}else{
			alert("Llene todos los campos indicados");
		}
	}
</script>