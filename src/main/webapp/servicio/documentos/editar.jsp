
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

 <%@ page import= "aca.ssoc.spring.SsocDocumentos"%>

<script type="text/javascript">	

	function Grabar() {
		if (document.frmDocumento.DocumentoNombre != "") {
			document.frmDocumento.submit();
		} else {
			alert("Ocurrió un error al grabar el documento");
		}
	}
</script>
<%
	// Declaracion de variables	
	SsocDocumentos documento	= (SsocDocumentos)request.getAttribute("documento");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	if (mensaje.equals("1")) mensaje = "¡ Grabado !"; else if (mensaje.equals("2")) mensaje = "¡ Error al grabar !";
%>
<body>
<div class="container-fluid">
	<h2>Editar Documento</h2>
	<form action="grabar" method="post" name="frmDocumento">
		<input type="hidden" name="Accion">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="listado"><spring:message code="aca.Regresar"/></a>
		</div>
<%	if (!mensaje.equals("-")){%>	
		<div class="alert alert-info">
			<%=mensaje%>		
		</div>	
<%	} %>	
		<div class="form-group">
			<label for="DocumentoId">Clave:</label>
		    <input type="text" class="input input-mini" name="DocumentoId" id="DocumentoId" required value="<%=documento.getDocumentoId()%>" readonly>
		    <br><br>
		    <label for="DocumentoNombre">Nombre:</label>
		    <input type="text" class="input input-xlarge" name="DocumentoNombre" id="DocumentoNombre" required value="<%=documento.getDocumentoNombre()%>">
		    <br><br>
		    <label for="Orden">Orden:</label>
		    <input type="text" class="input input-medium" name="Orden" id="Orden" required value="<%=documento.getOrden()%>">
		    <br><br>	    
		    <label for="Obligatorio">Tipo:</label>
		    <select class="input input-medium" name="Obligatorio"  id="Obligatorio" required>
		    	<option value="S" <% if (documento.getObligatorio().equals("S")) out.print("selected"); %> >Obligatorio</option>
		    	<option value="N" <% if (documento.getObligatorio().equals("N")) out.print("selected"); %> >Opcional</option>
		    	<option value="P" <% if (documento.getObligatorio().equals("P")) out.print("selected"); %> >Prerrequisito</option>
		    </select>
		    <br><br>
		    <label for="Acceso">Acceso:</label>
		    <select class="input input-medium" name="Acceso"  id="Acceso" required>
		    	<option value="A" <% if (documento.getAcceso().equals("A")) out.print("selected"); %> >Administrador</option>
		    	<option value="C" <% if (documento.getAcceso().equals("C")) out.print("selected"); %> >Coordinador</option>
		    </select>		    
		</div>
		<div class="alert alert-info">   
	        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
	    </div>	
	</form>
</div>