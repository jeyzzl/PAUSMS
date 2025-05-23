<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@ page import = "com.itextpdf.text.Document" %>
<%@ page import = "com.itextpdf.text.DocumentException" %>
<%@ page import = "com.itextpdf.text.Paragraph" %>
<%@ page import = "com.itextpdf.text.pdf.PdfWriter" %>
<%@ page import	= "java.io.OutputStream"%>
<%@ page import	= "jakarta.servlet.http.HttpServletResponse"%>

<%@ page import="aca.alumno.spring.AlumPersonal"%>
<% 

	AlumPersonal alumno	= (AlumPersonal) request.getAttribute("alumno");
	
	String carpeta 		= application.getRealPath("/WEB-INF/pdf/seguro/"); 
	String dir 			= "";	
	
	Rectangle rec = new Rectangle(14.0f , 21.0f);
	Document document = new Document(PageSize.LETTER);
	document.setMargins(-40,-30,0,0);
	
	try{		
		if(!new File(carpeta).exists()) new File(application.getRealPath(carpeta)).mkdirs();
		dir = carpeta+alumno.getCodigoPersonal()+".pdf";
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		
		document.addAuthor("UM");
        document.addSubject("Seguro de vida");
		document.open();
		
		PdfContentByte canvas = pdf.getDirectContentUnder();
		
		Image hojaUno = Image.getInstance(application.getRealPath("/imagenes/")+"/seg-accidente1.jpg");
	    hojaUno.setAlignment(Image.LEFT | Image.UNDERLYING);
	    hojaUno.scaleAbsolute(612,792);
	    hojaUno.setAbsolutePosition(0, 0);
	    canvas.addImage(hojaUno);
	    
    	Phrase texto1 = new Phrase(alumno.getCodigoPersonal()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+" "+alumno.getNombre() , 
    			FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto1, 146, 591, 0);
    	
    	String sexo = "";
    	if(alumno.getSexo().equals("M")){
    		sexo = "Hombre";
    	}else if(alumno.getSexo().equals("F")){
    		sexo = "Mujer";
    	}
    	
    	Phrase texto2 = new Phrase(sexo, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto2, 62, 578, 0);
    	
    	Phrase texto3 = new Phrase(alumno.getFNacimiento(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto3, 410, 578, 0);
    	
    	document.newPage();
    	
    	PdfContentByte canvas4 = pdf.getDirectContentUnder();
    	
    	Image hojaDos = Image.getInstance(application.getRealPath("/imagenes/")+"/seg-accidente2.jpg");
    	hojaDos.setAlignment(Image.LEFT | Image.UNDERLYING);
    	hojaDos.scaleAbsolute(612,792);
    	hojaDos.setAbsolutePosition(0, 0);
	    canvas.addImage(hojaDos);
    	
    	Phrase texto4 = new Phrase( "Montemorelos, N.L.   "+aca.util.Fecha.getHoy(),
    			FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas4, Element.ALIGN_LEFT, texto4, 35, 251, 0);
		
	}catch(Exception ex){
			System.err.println("Error al generar el PDF del seguro de vida: "+ex.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
			
	String nombreArchivo = alumno.getCodigoPersonal()+".pdf";	
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>