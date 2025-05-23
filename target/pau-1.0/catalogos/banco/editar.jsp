<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.catalogo.spring.CatBanco"%>
<%@ page import="aca.catalogo.spring.CatPais" %>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script type="text/javascript">		
		function Grabar() {
			if (document.frmBanco.BancoId.value != "" && document.frmBanco.Nombre != "") {
				document.frmBanco.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}	
	</script>
</head>
<%
	String bancoId 				= request.getParameter("BancoId")==null?"0":request.getParameter("BancoId");
	String mensaje	 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	CatBanco catBanco 			= (CatBanco) request.getAttribute("catBanco");	
	List<CatPais> lisPais 		= (List<CatPais>) request.getAttribute("lisPais");
%>
<body>
<div class="container-fluid">
	<h2>Edit Bank</h2>
	<form name="frmBanco" action="grabar" method="post">	
	<div class="alert alert-info">
		<a href="banco" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<div class="form-group">
		<label for="clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input name="BancoId" type="text" id="BancoId" class="form-control" maxlength="3" required value="<%=catBanco.getBancoId()%>" readonly style="width: 20rem;">
	    <br>
	    <label for="nombre"><strong><spring:message code="aca.Religion"/></strong></label>
	    <input name="Nombre" type="text" id="Nombre" class="form-control" maxlength="40" value="<%=catBanco.getNombre()%>" style="width: 20rem;">
	    <br>
	    <label for="Corto"><strong>Short name</label>
	    <input name="NombreCorto" type="text" id="NombreCorto" class="form-control" maxlength="40" value="<%=catBanco.getNombreCorto()%>" style="width: 20rem;">
	    <br>
	    <label for="PaisId"><strong>Country</label>
		<select name="PaisId" id="PaisId" class="form-select" style="width: 20rem;">
<%	for(CatPais pais : lisPais){%>
			<option value="<%=pais.getPaisId()%>" <%=pais.getPaisId().equals(catBanco.getPaisId())?"selected":""%>><%=pais.getNombrePais()%></option>
<%	}%>
		</select>
	    <br>
	    <label for="Swift"><strong>Swift</label>
	    <input name="Swift" type="text" id="Swift" class="form-control" maxlength="11" value="<%=catBanco.getSwift()%>" style="width: 20rem;">
	</div>
	<div class="alert alert-info mt-4">			
		<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Guardar"/></a>
	</div>	
	</form>
</div>
</body>
</html>