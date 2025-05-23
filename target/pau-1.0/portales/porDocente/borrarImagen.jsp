<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>

<jsp:useBean id="SeccionEmp" scope="page" class="aca.por.PorSeccionEmp"/>

<%		 
	String portafolioId		= (String) session.getAttribute("portafolioId");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String seccionId		= request.getParameter("SeccionId");	
	String folio		= request.getParameter("Folio");	
	
	boolean borro 			= false;
	
	// Verifica si existe el archivo
	String archivo		= application.getRealPath("/WEB-INF/porDocente")+"/"+codigoPersonal+"/"+seccionId+".jpg";
	File  imagen = new File(archivo);
	if (imagen.exists()){
		imagen.delete();	
		borro = true;
	}
	
	SeccionEmp.setPorId(portafolioId);
	SeccionEmp.setCodigoPersonal(codigoPersonal);
	SeccionEmp.setSeccionId(seccionId);
	SeccionEmp.setFolio(folio);
	if (SeccionEmp.existeReg(conEnoc)){
		if (SeccionEmp.deleteReg(conEnoc)){
			response.sendRedirect("seccion.jsp");
		}else{
%>
			<font size="4"><b>No borró los datos</b> <a href="seccionAccion.jsp?SeccionId=<%=seccionId%>"><i class="icon-arrow-left icon-white"></i>Regresar</a></font>
<%			
		}
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>