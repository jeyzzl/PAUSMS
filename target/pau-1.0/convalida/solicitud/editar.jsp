<%@ page import= "aca.conva.spring.ConvEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	//variables
	String codigoPersonal	= (String)request.getAttribute("codigoPersonal");
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje			= (String)request.getAttribute("mensaje");
	String colorMensaje		= (String)request.getAttribute("colorMensaje");
	String estado 			= (String)request.getAttribute("estado");
	ConvEvento evento		= (ConvEvento)request.getAttribute("evento");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
%>
<script>
	function Grabar(){
		document.frmEvento.Accion.value = "1";
		document.frmEvento.submit();
	}
</script>
<html>
<body>
<div class="container-fluid">
  	<h2>Editar el dictamen<small class="text-muted fs-5">(<%=codigoAlumno%> - <%=nombreAlumno%> - <%=evento.getFecha()%> - <%=estado%>)</small></h2>
	<div class="alert alert-info">
		<a href="solicitud" class="btn btn-primary">Regresar</a>
	</div>
<%	if (!mensaje.equals("-")){ %>
	<div class="alert <%=colorMensaje%>"><%=mensaje%></div>
<% 	}%>	
	<form name="frmEvento" action="editar" method="post">
	<input name="Accion" type="hidden">
	<input name="ConvalidacionId" type="hidden" value="<%=convalidacionId%>">	
	<div class="row">
		<div class="col-6">
			<label>Dictamen:</label>
			<input type="text" id="Dictamen" name="Dictamen" class="form-control" size="40" value="<%=evento.getDictamen()%>">
			<br>
			<label>Comentario:</label>
			<textarea id="Comentario" name="Comentario" rows="7" cols="50" class="form-control"><%=evento.getComentario()%></textarea>
		</div>		
	</div>
	<br>
	<div class="alert alert-info" >
		<a href="javascript:Grabar()" class="btn btn-primary">Grabar</a>
	</div>
	</form>	
</div>
</body>
</html>