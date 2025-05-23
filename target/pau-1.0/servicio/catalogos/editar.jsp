<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.ssoc.spring.SsocRequisito"%>

<script type="text/javascript">	

	function Grabar() {
		if (document.frmDocumento.RegistroNombre != "") {
			document.frmDocumento.submit();
		} else {
			alert("Ocurrió un error al grabar el documento");
		}
	}
</script>
<%
	// Declaracion de variables	
	SsocRequisito requisito		= (SsocRequisito)request.getAttribute("requisito");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	if (mensaje.equals("1")) mensaje = "¡ Grabado !"; else if (mensaje.equals("2")) mensaje = "¡ Error al grabar !";
%>
<body>
	<div class="container-fluid">
		<h2>Editar Requisito</h2>
		<form action="grabar" method="post" name="frmDocumento">
			<div class="alert alert-info">
				<a class="btn btn-primary" href="catalogos"><spring:message code="aca.Regresar"/></a>
			</div>
<%		if (!mensaje.equals("-")){%>	
			<div class="alert alert-info">
				<%=mensaje%>		
			</div>	
<%		} %>	
			<div class="form-group">
				<label for="RequisitoId">Clave:</label>
			    <input type="text" class="input input-mini" name="RequisitoId" id="RequisitoId" value="<%=requisito.getRequisitoId()%>" required readonly="readonly" />
			    <br><br>
			    <label for="RequisitoNombre">Nombre:</label>
			    <textarea name="RequisitoNombre" id="RequisitoNombre" cols="100" rows="5" required/><%=requisito.getRequisitoNombre()%></textarea>
			    <br><br>
			    <label for="Orden">Orden:</label>
			    <input type="text" class="input input-medium" name="Orden" id="Orden" value="<%=requisito.getOrden()%>" required/>
			    <br><br>	   
			</div>
			<div class="alert alert-info">   
		        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
		    </div>	
		</form>
	</div>
</body>