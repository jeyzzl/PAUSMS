<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.archivo.spring.ArchGrupoPlan"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	ArchGrupoPlan grupoPlan	= (ArchGrupoPlan) request.getAttribute("grupoPlan");
    String mensaje 			= (String) request.getAttribute("mensaje");
	
%>
<head>
</head>
<body>
<div class="container-fluid">
	<h1>New</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado">Return</a>
	</div>
<%  if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Data saved
	</div>
<%  }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		Data not saved
	</div>
<%  }%>
	<form id="forma" name="forma" action="grabar" method="post">
		<input type="hidden" name="GrupoId" value="<%=grupoPlan.getGrupoId()%>" />
		<div class="form-group row">
			<div class="col-sm-4">
				<label>Plan</label>			
				<input class="form-control" type="text" name="Nombre" value="<%=grupoPlan.getPlanId()%>" required="required"/>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<button class="btn btn-primary" type="submit">Save</button>
		</div>
	</form>
</div>
</body>
