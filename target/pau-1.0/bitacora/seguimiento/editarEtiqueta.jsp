<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitEtiqueta"%>


<script type="text/javascript">	
	
	function Grabar() {
		document.frmEtiqueta.submit();
	}
</script>
<%	
	String codigoPersonal	= (String) request.getAttribute("codigoPersonal");
	String folio			= (String) request.getAttribute("folio");
	String etiquetaId		= (String) request.getAttribute("etiquetaId");
	String comentario 		= (String) request.getAttribute("comentario");
	String nombreUsuario 	= (String) request.getAttribute("nombreUsuario");
	String nombreEstado 	= (String) request.getAttribute("nombreEstado");
	String nombreTramite 	= (String) request.getAttribute("nombreTramite");
	
	BitTramiteAlumno bitTramiteAlumno 	= (BitTramiteAlumno) request.getAttribute("bitTramiteAlumno");
	BitTramite bitTramite				= (BitTramite) request.getAttribute("bitTramite");
	BitEtiqueta	bitEtiqueta				= (BitEtiqueta) request.getAttribute("bitEtiqueta");
	
%>
<div class="container-fluid">
	<h2>BSE / Seguimiento / Etiquetas<small class="text-muted h5">(<%=bitTramiteAlumno.getCodigoPersonal()%> - <%=nombreUsuario%>)</small></h2>
	<div class="alert alert-info">
		<a href="etiquetas?Folio=<%=folio%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;			
	</div>
	<form name="frmEtiqueta" action="updateComentario" method="post">
	<input name="Folio" type="hidden" value="<%=folio%>">
	<input name="EtiquetaId" type="hidden" value="<%=etiquetaId%>">
	<div class="alert alert-info">		
		Tramite: <b><%=folio%></b>&nbsp;&nbsp;<b><%=nombreTramite%></b>&nbsp;&nbsp;				 
		Registrado: <b><%=bitTramiteAlumno.getFechaInicio()%></b>&nbsp;&nbsp;		
		Estado: <b><%=nombreEstado%></b>
	</div>
	<div class="row container-fluid">
		<div class="span5">
			<label>Comentario:</label>
			<textarea id="Comentario" class="form-control" name="Comentario" rows="7" cols="50"><%=bitEtiqueta.getComentario()%></textarea>
		</div> 
	</div>&nbsp;
	<div class="alert alert-info" >
		<a href="javascript:Grabar()" class="btn btn-primary">Grabar</a>
	</div>
	</form>
</div>