<%@page import="aca.salida.spring.SalProposito"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">	

	function Grabar() {
		if (document.frmproposito.PropositoId.value != ""
				&& document.frmproposito.NombreProposito != "") {
			document.frmproposito.Accion.value = "2";
			document.frmproposito.submit();
		} else {
			alert("<spring:message code="catalogos.area.JSCompletar"/>");
		}
	}
</script>
<%
	// Declaracion de variables	
	SalProposito proposito 		= (SalProposito)request.getAttribute("proposito");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	if (mensaje.equals("1")) mensaje = "¡ Grabado !"; else if (mensaje.equals("2")) mensaje = "¡ Error al grabar !";
%>
<div class="container-fluid">
	<h2>Listado</h2>
	<form action="grabar" method="post" name="frmproposito">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if (!mensaje.equals("-")){%>	
	<div class="alert alert-info">
		<%=mensaje%>		
	</div>	
<%	} %>	
	<div class="form-group">
		<label for="PropositoId">Proposito Id:</label>
	    <input type="text" class="input input-mini form-control" style="width:200px" name="PropositoId" id="PropositoId" required value="<%=proposito.getPropositoId()%>" maxlength="1">
	    <br>
	    <label for="PropositoNombre">Nombre:</label>
	    <input type="text" class="input input-xlarge form-control" style="width:400px" name="PropositoNombre" id="PropositoNombre" required value="<%=proposito.getPropositoNombre()%>" maxlength="40">    
	</div>
	&nbsp;
	<div class="alert alert-info">	    
        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
    </div>	
	</form>
</div>