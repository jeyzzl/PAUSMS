<%@page import="aca.residencia.spring.ResHospedaje"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
</head>	
<%	
 	ResHospedaje hospedaje = (ResHospedaje)request.getAttribute("hospedaje");	

 	String mensaje = (String)request.getAttribute("mensaje");	
%>
<body>

<div class="container-fluid">
	<h1>Accommodation</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
  		Saved
	</div>
<%  }%>
	<form class="form-group" action="grabar" name="form" method="post">
		<input type="hidden" name="Id" value="<%=hospedaje.getId()%>">
		<div class="row">
			<div class="col-sm-4">
				<label>Name</label>
				<input class="form-control" type="text" name="Nombre" value="<%=hospedaje.getNombre()%>">
			</div>
			<div class="col-sm-4">
				<label>Surname</label>
				<input class="form-control" type="text" name="Apellidos" value="<%=hospedaje.getApellidos()%>">
			</div>
			<div class="col-sm-4">
				<label>Employee ID</label>
				<input class="form-control" type="text" name="Nomina" value="<%=hospedaje.getNomina()%>">
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-4">
				<label>Address</label>
				<input class="form-control" type="text" name="Direccion" value="<%=hospedaje.getDireccion()%>" required="required">
			</div>
			<div class="col-sm-2">
				<label>Phone Number</label>
				<input class="form-control" type="text" name="Telefono" value="<%=hospedaje.getTelefono()%>" required="required">
			</div>
			<div class="col-sm-1">
				<label>Male Cap.</label>
				<input class="form-control" type="text" name="CupoH" value="<%=hospedaje.getCupoHombres()%>" required="required">
			</div>
			<div class="col-sm-1">
				<label>Female Cap.</label>
				<input class="form-control" type="text" name="CupoM" value="<%=hospedaje.getCupoMujeres()%>" required="required">
			</div>
			<div class="col-sm-1">
				<label>Bedrooms</label>
				<input class="form-control" type="text" name="Cuartos" value="<%=hospedaje.getCuartos()%>" required="required">
			</div>
			<div class="col-sm-1">
				<label>Status</label>
				<select name="Estado" class="form-control" style="width:110px;">
					<option value="A" <%=hospedaje.getEstado().equals("A") ? "selected" : ""%>>Active</option>
					<option value="I" <%=hospedaje.getEstado().equals("I") ? "selected" : ""%>>Inactive</option>
				</select>
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-12">
				<button class="btn btn-primary" type="submit">Save</button>
			</div>
		</div>
	</form>
<%	
	
%>
</div>
</body>
<script type="text/javascript"> 
</script>
