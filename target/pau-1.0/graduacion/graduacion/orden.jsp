<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.util.Fecha"%>

<html>
<head>
	<script type="text/javascript">		
		function guardar(){
			if(document.forma.Orden.value != ""){				
				document.forma.submit();
			}else{
				alert("El orden es requerido para guardar");
				document.forma.Orden.focus();
			}
		}		
	</script>
</head>
<%
	// Declaracion de Variables
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String carreraId 	= request.getParameter("CarreraId");
	String orden 		= request.getParameter("Orden");
%>
<body>
<div class="container-fluid">
	<h2>Editar orden <small class="text-muted fs-4">( <%=carreraId%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="alumnos"><spring:message code="aca.Regresar"/></a>
	</div>
	<form id="forma" name="forma" action="grabar?CarreraId=<%=carreraId%>" method="post">
		<div class="row">
			<div class="col-3">					
				<label for="orden"><spring:message code="aca.Orden"/><b><font color="#AE2113"> *</font></label>
				<input type="text" class="text" id="Orden" name="Orden" value="<%=orden%>" maxlength="6" size="6" /> [999.99]
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="button" onclick="guardar();" value="Guardar" />
		</div>
	</form>
</div>
</body>
</html>