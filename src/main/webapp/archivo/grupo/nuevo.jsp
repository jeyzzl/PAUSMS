<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.archivo.spring.ArchGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp"%>
<%
	ArchGrupo grupo	= (ArchGrupo) request.getAttribute("grupo");    
    String mensaje = (String) request.getAttribute("mensaje");	
%>
<head>
</head>
<body>
<div class="container-fluid">
	<h1>New</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
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
		<input type="hidden" name="GrupoId" value="<%=grupo.getGrupoId()%>" />
		<div class="form-group row"  style="margin: 10px 0px 0 0px;">
			<div class="col-sm-12">
				<label>Name</label>			
				<input class="form-control" type="text" name="Nombre" value="<%=grupo.getGrupoNombre()%>" required="required"/>
				<label>Comment</label>
				<input class="form-control" type="text" name="Comentario" value="<%=grupo.getComentario()%>"/>
			</div>
		</div>
		<br>
		<div class="alert alert-info d-flex align-items-center">
			<button class="btn btn-primary" type="submit">Save</button>
		</div>		
	</form>	
</div>
</body>
