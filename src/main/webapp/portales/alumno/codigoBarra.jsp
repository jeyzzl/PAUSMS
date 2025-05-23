<%@ include file="../../idioma.jsp"%>

<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="net.sourceforge.jbarcodebean.BarcodeException" %>
<%@ page import="net.sourceforge.jbarcodebean.JBarcodeBean" %>
<%@ page import="net.sourceforge.jbarcodebean.model.Code39" %>
<%@ page import="java.awt.Font" %>

<%
	String dir				= application.getRealPath("/portales/alumno/")+"/";
	String matricula		= request.getParameter("Matricula");	
	JBarcodeBean barcode	= new JBarcodeBean();
	Font fuente 			= new Font("Arial", Font.BOLD, 12);
	
	//nuestro tipo de codigo de barra
	//barcode.setCodeType(new Interleaved25());
	barcode.setCodeType(new Code39());
	barcode.setFont(fuente);
	//barcode.setLabelPosition(JBarcodeBean.LABEL_NONE);
	
	// nuestro valor a codificar y algunas configuraciones mas
	barcode.setCode(matricula);
	barcode.setCheckDigit(true);
	
	BufferedImage bufferedImage	= barcode.draw(new BufferedImage(170, 70, BufferedImage.TYPE_INT_RGB));
	
	// guardar en disco como png
	File file = new File(dir+matricula+".png");
	ImageIO.write(bufferedImage, "png", file);
	
	byte[] buff = null;
	java.io.FileInputStream instream = null;
	if(file.exists()){
		buff = new byte[(int)file.length()];
		instream = new java.io.FileInputStream(dir+matricula+".png");
	}
	instream.read(buff,0,(int)file.length());
	
	response.setContentType("image/png");
	response.getOutputStream().write(buff);
	response.flushBuffer();
	instream.close();
	file.delete();
%>