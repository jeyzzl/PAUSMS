<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int grabados 		= (int)request.getAttribute("grabados");
	int errores 		= (int)request.getAttribute("errores");
%>
<div class="container-fluid">
	<h2>Internacionalización<small>( Idiomas )</small></h2>
	<div class="alert alert-info">
		<a href="sincronizar?Accion=1" class="btn btn-primary">Subir Español</a>&nbsp;&nbsp;
		<a href="sincronizar?Accion=2" class="btn btn-primary">Subir Inglés</a>
	</div>
<%
	if (grabados > 0 || errores > 0){
		out.print("<span>Grabados ="+grabados+" Errores="+errores+"</span>");
	}
%>	
</div>