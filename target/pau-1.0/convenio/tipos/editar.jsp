<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.convenio.spring.ConTipo"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>		
</head>	
<% 	
	ConTipo convenio 	= (ConTipo)request.getAttribute("convenio");
	String mensaje		=  request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");		 
%>
<body>
<div class="container-fluid">
	<h2>Nuevo / Editar tipo</h2>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary">Regresar</a>	
	</div>	
<%	if (!mensaje.equals("-")){ %>
	<div class="alert alert-info"><%=mensaje%></div>
<% 	}%>	
	<form name="frmConvenio" action="grabar" method="post">
		<input name="TipoId" type="hidden" value="<%=convenio.getTipoId()%>">
		<div class="row container-fluid">
			<div class="col">		
					<fieldset>
						<label ><b>Nombre</b></label>
						<input type="text" name="TipoNombre" class="form-control" value="<%=convenio.getTipoNombre()%>" size="45" required/>
					</fieldset>			
			</div>	
		</div><br>
		<div class="alert alert-info">
			<a href="javascript:Grabar()" class="btn btn-primary">Grabar</a>
		</div>
	</form>
</div>		
</body>
<script>
	function Grabar(){
		document.frmConvenio.submit();
	}
</script>
</html>