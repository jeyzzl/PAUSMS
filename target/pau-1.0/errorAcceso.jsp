<html>
<%@ include file= "head.jsp"%>
<body>
<%
	String rutaError 	= request.getParameter("Ruta")==null?"X":request.getParameter("Ruta");
	String error 		= request.getParameter("Error")==null?"X":request.getParameter("Error");
	String mensaje 		= "No tienes acceso a la direcci�n:"+rutaError;
%>
<div class="container-fluid">
	<h2>Restricci�n de seguridad <small class="text-muted fs-6"> ( <%=mensaje%> )</small></h2>
	<div class="alert alert-danger"><h6>Si consideras que debes tener este privilegio, comunicate a la Direcci�n de Sistemas</h6></div>	
</div>
</body>
</html>