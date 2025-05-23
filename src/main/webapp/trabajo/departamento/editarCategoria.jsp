<%@ page import="aca.trabajo.spring.TrabCategoria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String deptId = (String)request.getAttribute("deptId");
	TrabCategoria categoria = (TrabCategoria)request.getAttribute("categoria");
	String mensaje = (String)request.getAttribute("mensaje");
	
	if(mensaje.equals("1")){
		mensaje = "Saved";
	}else if(mensaje.equals("2")){
		mensaje = "Updated";
	}
%>

<body>
<div class="container-fluid">
	<h2>CTP Categories Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="categorias?DeptId=<%=deptId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 		if(!mensaje.equals("0")){%>
	<div class="alert alert-success">
		<%=mensaje%>
	</div>
<% 		}%>
	<form action="grabarCategoria" method="post" name="frmcategoria" target="_self">
		<input type="hidden" name="DeptId" value="<%=deptId%>">
		<label><spring:message code="aca.Clave"/>:</label>
		<input class="form-control" name="CatId" type="text" id="CatId" size="3" maxlength="3" value="<%=categoria.getCategoriaId()%>" readonly="readonly">
		<br>
		<label><spring:message code="aca.Nombre"/></label>
		<input class="form-control" name="Nombre" type="text" id="Nombre" value="<%=categoria.getNombreCategoria() %>" size="40" maxlength="40">
		<br>				
		<label><spring:message code="aca.Status"/></label>
		<select class="input input-medium form-select" style="width:200px" name="Estado"  id="Estado" required>
	    	<option value="A" <% if (categoria.getEstado().equals("A")) out.print("selected"); %> >Active</option>
	    	<option value="I" <% if (categoria.getEstado().equals("I")) out.print("selected"); %> >Inactive</option>
	    </select>
	    <br>
		<div class="alert alert-info">
			<input class="btn btn-info" type="submit" value="Save">
		</div>	
	</form>
</div>
</body>