<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ArchEntregaU" scope="page" class="aca.archivo.ArchEntregaUtil"/>
<jsp:useBean id="ArchEntrega" scope="page" class="aca.archivo.ArchEntrega"/>

<%	
	String codigoAlumno	= (String)session.getAttribute("codigoAlumno");
	String folio   		= request.getParameter("Folio");
	String imagen		= request.getParameter("Imagen");
	String opcion		= request.getParameter("Opc");
	
	ArchEntrega.setCodigoPersonal(codigoAlumno);
	ArchEntrega.setFolio(folio);
	
	if(ArchEntregaU.deleteImagen(conEnoc, codigoAlumno, folio, imagen)){
		//response.sendRedirect("subir?Folio="+folio+"&Opc="+opcion+"&Imagen="+imagen);
		out.print("<div class='alert alert-success'><b>Grabado...<a class='btn btn-primary' href='subir?Folio='+folio+'&Opc='+opcion+'&Imagen='+imagen'>Regresar</a></div>");
	}	else{
		out.print("<div class='alert alert-danger'><b>No grabó...<a class='btn btn-primary' href='subir?Folio='+folio+'&Opc='+opcion+'&Imagen='+imagen+'&Mensaje=No pudo borrar'>Regresar</a></div>");
		//response.sendRedirect("subir?Folio="+folio+"&Opc="+opcion+"&Imagen="+imagen+"&Mensaje=No pudo borrar");
	}
%>
<%@ include file= "../../cierra_enoc.jsp" %>