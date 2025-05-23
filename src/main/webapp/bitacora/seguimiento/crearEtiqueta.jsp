<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>

<%
	String codigoPersonal	= (String) request.getAttribute("codigoPersonal");
	String folio			= (String) request.getAttribute("folio");
	String areaId			= (String) request.getAttribute("areaId");
	String etiquetaId		= (String) request.getAttribute("etiquetaId");
	String comentario		= (String) request.getAttribute("comentario");
	String nombreUsuario	= (String) request.getAttribute("nombreUsuario");
	String nombreArea		= (String) request.getAttribute("nombreArea");
	String mensaje			= (String) request.getAttribute("mensaje");
	
	BitTramiteAlumno bitTramiteAlumno 	= (BitTramiteAlumno) request.getAttribute("bitTramiteAlumno");
	

%>

<div class="container-fluid">
	<h2>BSE / Seguimiento / Nueva etiqueta <small class="text-muted h5">(<%=bitTramiteAlumno.getCodigoPersonal()%> - <%=nombreUsuario%>)</small></h2>
	<div class="alert alert-info">
		<a href="etiquetas?Folio=<%=folio%>&AreaId=<%=areaId%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<% if(!mensaje.equals("X")){%>
	<div class="alert alert-success" role="alert"><%=mensaje%></div>
<% }%>
	<form name="form" action="guardarEtiqueta" method="POST">
	<input type="hidden" name="Folio" value="<%=folio%>">
	<input type="hidden" name="AreaId" value="<%=areaId%>">
	<input type="hidden" name="EtiquetaId" value="<%=etiquetaId%>">
		<div class="row container-fluid">
			<div class="span3">
				<label for="folio">Folio</label>
				<input type="text" class="text form-control" name="folio" size="8"  value="<%=folio%>" readonly="readonly">
				<br><br>
				<label for="area">Area</label>
				<input type="text" class="text form-control" name="area" size="50" value="<%=nombreArea%>" readonly="readonly">  
				<br><br>
				<label for="Comentario">Comentario</label>
				<textarea name="Comentario" class="text form-control" style="width:730px;" rows="3" cols="50"><%=comentario%></textarea><br><br>
			</div>
		</div>
	<div class="alert alert-info">
		<input type="submit" class="btn btn-primary" value="Guardar">
	</div>
	</form>
</div>