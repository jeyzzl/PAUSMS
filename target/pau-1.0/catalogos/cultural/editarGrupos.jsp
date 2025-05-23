<%@ page import="aca.catalogo.spring.CatCultural"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%
	CatCultural cultural 	= (CatCultural)request.getAttribute("cultural");
	String mensaje 			= (String)request.getAttribute("mensaje");
	
	if(mensaje.equals("1")){
		mensaje = "Saved";
	}else if(mensaje.equals("2")){
		mensaje = "Updated";
	}
%>
<body>
<div class="container-fluid">
	<h2>Cultural Groups Catalog</h2>
	<form action="grabarGrupo" method="post" name="frmcultural" target="_self">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="grupos?CulturalId="><i class="fas fa-arrow-left"></i></a>
		</div>
<% 		if(!mensaje.equals("0")){ %>
		<div class="alert alert-success">
			<%=mensaje%>
		</div>
<% 		} %>
		<div class="form-group">
			<label><strong><spring:message code="aca.Clave"/></strong></label>
			<input class="input input-mini form-control" name="CulturalId" type="text" class="text" id="CulturalId" value="<%=cultural.getCulturalId()%>" readonly="readonly" >
			<br>
			<label><strong>Main Cultural Group</strong></label>
			<input class="input input-mini form-control" name="Principal" type="text" class="text" id="Principal" value="<%=cultural.getPrincipal()%>" >
			<br>
			<label><strong>Regional Group</strong></label>
			<input class="input input-mini form-control" name="NombreCultural" type="text" class="text" id="NombreCultural" value="<%=cultural.getNombreCultural()%>" >
			<br>
		</div>
		<div class="alert alert-info">
			<input class="btn btn-info" type="submit" value="Save">
		</div>
	</form>
</div>
</body>