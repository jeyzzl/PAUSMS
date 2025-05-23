<%@ page import= "aca.conva.spring.ConvEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	//variables
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");	
	String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
	String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje				= (String)request.getAttribute("mensaje");
	String colorMensaje			= (String)request.getAttribute("colorMensaje");
	String estado 				= (String)request.getAttribute("estado");
	ConvEvento evento			= (ConvEvento)request.getAttribute("evento");
	String nombreAlumno			= (String)request.getAttribute("nombreAlumno");	
%>
<script>
	function Grabar(){
		document.frmEvento.Accion.value = "1";
		document.frmEvento.submit();
	}
</script>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>
<div class="container-fluid">
  	<h2>Modificar la convalidación<small class="text-muted fs-4">(<%=codigoAlumno%> - <%=nombreAlumno%> - <%=evento.getFecha()%> - <%=estado%>)</small></h2>
	<div class="alert alert-info">
		<a href="interna" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if (!mensaje.equals("-")){ %>
	<div class="alert <%=colorMensaje%>"><%=mensaje%></div>
<% 	}%>	
	<form name="frmEvento" action="editar" method="post">
	<input name="Accion" type="hidden">
	<input name="ConvalidacionId" type="hidden" value="<%=convalidacionId%>">	
	<div class="row">
		<div class="span5">
			<label>Dictamen:</label>
			<input class="form-control" type="text" id="Dictamen" name="Dictamen" size="40" value="<%=evento.getDictamen()%>">
			<br>
			<label>Comentario:</label>
			<textarea class="form-control" id="Comentario" name="Comentario" rows="7" cols="50"><%=evento.getComentario()%></textarea>
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