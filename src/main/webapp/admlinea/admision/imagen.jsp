<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%@ page import= "java.util.Base64"%>
<%
	String folio			= (String) session.getAttribute("folio"); 
	String documentoId 		= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");
	String hoja 			= request.getParameter("hoja")==null?"0":request.getParameter("hoja");
	String nombreDocumento	= (String)request.getAttribute("nombreDocumento");
	byte imagenByte[]		= (byte[])request.getAttribute("imagenByte");
	String pagina			= request.getParameter("pag")==null?"Proceso":request.getParameter("pag");
	//System.out.println("Imagen:"+imagenByte.length);
	String imagenStr 		= Base64.getEncoder().encodeToString(imagenByte);
	//System.out.println("Imagen:"+imagenStr);
%>
<body style="height: 97%;background:url(../imagenes/um3.png) no-repeat bottom right fixed;background-color:#F7EBEB;">

<div class="container">
		
	<nav class="navbar-right">
	  <ul class="pager">
	<% if(pagina.equals("Alumnos")){%>
		<li><a href="documentos?Folio=<%=folio%>"><span class="glyphicon glyphicon-menu-left" ></span>Regresar</a></li>
	<% }else if(pagina.equals("Proceso")){%>
		<li><a href="documentos?Folio=<%=folio%>&pag=Alumnos"><span class="glyphicon glyphicon-menu-left" ></span>Regresar</a></li>
	<% }else if(pagina.equals("Documentos")){%>
		<li><a href="documentos?Folio=<%=folio%>&pag=Documentos"><span class="glyphicon glyphicon-menu-left" ></span>Regresar</a></li>
	<% }%>
	  </ul>
	</nav>
	
	<div class="page-header">
	  <h1><%=nombreDocumento%><small>( Hoja: <%=hoja%> )</small></h1>
	</div>
	<div class="alert alert-info">
		<div class="row">
			<div class="col-md-3">
				<img id="img" width="100%" src="data:image/jpg;base64,<%=imagenStr%>" />							
			</div>					
		</div>
	</div>
</div>	
</body>