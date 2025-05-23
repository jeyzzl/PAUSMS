<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.ByteArrayInputStream"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ArchEntregaU" scope="page" class="aca.archivo.ArchEntregaUtil"/>
<jsp:useBean id="ArchEntrega" scope="page" class="aca.archivo.ArchEntrega"/>

<%	
	String codigoAlumno	= (String)session.getAttribute("codigoAlumno");
	String folio   		= request.getParameter("Folio");
	String opcion		= request.getParameter("Opcion");
	
	ArchEntrega = ArchEntregaU.getEntrega(conEnoc, codigoAlumno, folio);
	
	BufferedImage img = null;
	
	if(opcion.equals("1")){
		img = ImageIO.read(new ByteArrayInputStream(ArchEntrega.getIdentificacion()));
		response.setContentType("image/jpg");
		response.getOutputStream().write(ArchEntrega.getIdentificacion());
	}else if(opcion.equals("2")){
		img = ImageIO.read(new ByteArrayInputStream(ArchEntrega.getPoder()));
		response.setContentType("image/jpg");
		response.getOutputStream().write(ArchEntrega.getPoder());
	}else if(opcion.equals("3")){
		img = ImageIO.read(new ByteArrayInputStream(ArchEntrega.getEnvio()));
		response.setContentType("image/jpg");
		response.getOutputStream().write(ArchEntrega.getEnvio());
	}else if(opcion.equals("4")){
		img = ImageIO.read(new ByteArrayInputStream(ArchEntrega.getFirma()));
		response.setContentType("image/jpg");
		response.getOutputStream().write(ArchEntrega.getFirma());
	}
	else if(opcion.equals("5")){
		img = ImageIO.read(new ByteArrayInputStream(ArchEntrega.getExtra()));
		response.setContentType("image/jpg");
		response.getOutputStream().write(ArchEntrega.getFirma());
	}
	ImageIO.write(img, "jpg", response.getOutputStream());
%>

<%@ include file= "../../cierra_enoc.jsp" %>