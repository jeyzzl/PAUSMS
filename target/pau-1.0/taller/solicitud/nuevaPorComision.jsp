<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecSolicitud"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<head>
	<script type="text/javascript"></script>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>	
</head>
<%
	BecSolicitud becSolicitud 	= (BecSolicitud)request.getAttribute("becSolicitud");
	String tipos 				= request.getParameter("Tipos")==null?"T":request.getParameter("Tipos");
	String mensaje 				= (String)request.getAttribute("mensaje");
	String nombreAlumno			= (String)request.getAttribute("nombreAlumno");
	
	String fechaIni	= request.getParameter("FechaIni")==null?"01"+aca.util.Fecha.getHoy().substring(2,10):request.getParameter("FechaIni");
	String fechaFin	= request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
%>
<body>
<div class="container-fluid">
	<h2>Beca <small class="text-muted fs-4">( Alumno: <%=nombreAlumno%> - Matricula: <%=becSolicitud.getCodigoPersonal()%> - Folio: <%=becSolicitud.getFolio()%>)</small></h2>
	<div class="alert alert-info">
		<a href="listado?Tipos=<%=tipos%>&FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Registro grabado
	</div>
<% }%>
	<form action="grabarPorComision" name="form" method="post">
		<input type="hidden" name="Matricula" value="<%=becSolicitud.getCodigoPersonal()%>">
		<input type="hidden" name="Folio" value="<%=becSolicitud.getFolio()%>">
		<input type="hidden" name="Tipos" value="<%=tipos%>">
		<div style="padding-left:15px;" class="form-group row">
			<div class="col-md-3">
				<label>Porcentaje comisi&oacute;n</label>
				<input class="form-control" type="text" name="PorComision" value="<%=becSolicitud.getPorComision()%>">
			</div>
			<div class="col-md-12"><br>
				<label>Comentario</label>
				<textarea class="form-control" rows="" cols="" name="Comentario" maxlength="500"><%=becSolicitud.getComentario()%></textarea>
			</div>
			<div class="col-md-2"><br>
				<label>Fecha</label>
				<input class="form-control" type="text" data-date-format="dd/mm/yyyy" name="Fecha" id="Fecha" value="<%=becSolicitud.getFecha()%>"> 
			</div>
		</div><br>
		<div style="padding-left:30px;" class="form-group row">
			<div class="alert alert-info">
				<button type="submit" class="btn btn-primary">Grabar</button>
			</div>
		</div>
	</form>
</div>
</body>
<script>
	jQuery('#Fecha').datepicker();
</script>
