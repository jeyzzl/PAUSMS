<%@ page import="aca.catalogo.spring.CatPais"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<%
	CatPais pais 	= (CatPais)request.getAttribute("pais");
	String mensaje 	= (String)request.getAttribute("mensaje");
	
	if(mensaje.equals("1")){
		mensaje = "Saved";
	}else if(mensaje.equals("2")){
		mensaje = "Updated";
	}
	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="catalogos.paises.Paises"/></h2>
	<form action="grabarPais" method="post" name="frmpais" target="_self">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="paises?PaisId="><i class="fas fa-arrow-left"></i></a>
	</div>
<% 		if(!mensaje.equals("0")){%>
	<div class="alert alert-success">
		<%=mensaje%>
	</div>
<% 		}%>
	<div class="form-group">
		<label><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="PaisId" type="text" class="text" id="PaisId" maxlength="3" value="<%=pais.getPaisId()%>" readonly="readonly">
		<br>
		<label><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="input input-large form-control" name="NombrePais" type="text" class="text" id="NombrePais" value="<%=pais.getNombrePais()%>" maxlength="40">
		<br>
		<label><strong><spring:message code="aca.Nacionalidad"/></strong></label>
		<input class="input input-xlarge form-control" name="Nacionalidad" type="text" class="text" id="Nacionalidad" value="<%=pais.getNacionalidad()%>" maxlength="80">
		<br>
		<label><strong><spring:message code="catalogos.paises.Interamericana"/></strong></label>
		<select id="Interamerica" name="Interamerica" class="form-select">
			<option value="N"><spring:message code='aca.No'/></option>
			<option value="S" <%if(pais.getInteramerica().equals("S"))out.print("selected"); %>><spring:message code='aca.Si'/></option>
		</select>
		<br>
	</div>
		<div class="alert alert-info">
			<input class="btn btn-info" type="submit" value="Save">
		</div>
	</form>
</div>
</body>
</html>