<%@ page import="aca.catalogo.spring.CatRegion"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String culturalId 	= (String)request.getAttribute("culturalId");
	CatRegion region 	= (CatRegion)request.getAttribute("region");
	String mensaje 		= (String)request.getAttribute("mensaje");
	
	if(mensaje.equals("1")){
		mensaje = "Saved";
	}else if(mensaje.equals("2")){
		mensaje = "Updated";
	}
%>
<body>
<div class="container-fluid">
	<h2>Provincial Groups Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="regiones?CulturalId=<%=culturalId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 		if(!mensaje.equals("0")){%>
	<div class="alert alert-success">
		<%=mensaje%>
	</div>
<% 		}%>

	<form action="grabarRegion" method="post" name="frmregion" target="_self">
		<input type="hidden" name="CulturalId" value="<%=culturalId%>">
		<label><spring:message code="aca.Clave"/>:</label>
		<input class="form-control" name="RegionId" type="text" id="RegionId" size="3" maxlength="3" value="<%=region.getRegionId()%>" readonly="readonly">
		<br>
		<label><spring:message code="aca.Nombre"/></label>
		<input class="form-control" name="NombreRegion" type="text" id="NombreRegion" value="<%=region.getNombreRegion()%>" size="40" maxlength="40">
		<br>
		<div class="alert alert-info">
			<input class="btn btn-info" type="submit" value="Save">
		</div>
	</form>
</div>
</body>