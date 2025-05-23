<%@page import="java.util.List"%>
<%@page import="aca.archivo.spring.ArchDocumentos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script type="text/javascript">		
		function Grabar(){
			if(document.frmdocumento.IdDocumento.value!="" && document.frmdocumento.Descripcion!="" && document.frmdocumento.Orden!=""){
				document.frmdocumento.submit();			
			}else{
				alert("Fill out the entire form");
			}
		}		
	</script>
</head>
<%
	// Declaracion de variables	
	ArchDocumentos documento 	= (ArchDocumentos) request.getAttribute("documento");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");	
%>
<div class="container-fluid">
	<h1><spring:message code="aca.CatalogoDeDocumentos"/></h1>
	<form name="frmdocumento" action="grabar" method="post">	
	<div class="alert alert-info d-flex align-items-center">	
		<a href="documentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<div class="row container-fluid">
		<div class="span3">
			<label for="IdDocumento"><spring:message code="aca.Clave"/>:</label>
			<input name="IdDocumento" type="text" class="form-control" id="IdDocumento" style="width:100px" size="3" maxlength="3" value="<%=documento.getIdDocumento()%>" readonly>
			<br>
            <label for="Descripcion"><spring:message code="aca.Descripcion"/>:</label>
            <input name="Descripcion" type="text" class="form-control" style="width:600px" id="Descripcion" size="40" maxlength="40" value="<%=documento.getDescripcion()%>">
            <br>
            <label for="Imagen"><spring:message code="aca.Imagen"/>:</label>
            <input name="Imagen" type="text" class="form-control" id="Imagen" style="width:200px" size="40" maxlength="40" value="<%=documento.getImagen()%>">
            <br>
            <label for="Orden"><spring:message code="aca.Orden"/>:</label>
            <input name="Orden" type="text" class="form-control" id="Orden" size="6" style="width:200px" maxlength="5" value="<%=documento.getOrden()==null ? "0" : documento.getOrden() %>" onKeypress="if ((window.event.keyCode < 48 || window.event.keyCode > 57) && window.event.keyCode!=46) window.event.returnValue = false;">
            <br>
            <label for="Mostrar">Show:</label>
            <select name="Mostrar" class="form-control" style="width:77px">
            	<option value="S" <%=documento.getMostrar().equals("S")?"selected":""%>>YES</option>
            	<option value="N" <%=documento.getMostrar().equals("N")?"selected":""%>>NO</option>
            </select>
		</div>
	</div>
	<br>
	<div class="alert alert-info d-flex align-items-center">	
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;	
	</div>
</form>
</div>
</body>
</html>