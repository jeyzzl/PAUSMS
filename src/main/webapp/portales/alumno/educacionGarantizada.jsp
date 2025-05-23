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
<%@ page import="aca.residencia.spring.ResDatos"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%

	AlumPersonal alumPersonal	= (AlumPersonal) request.getAttribute("alumPersonal");
	ResDatos resDatos			= (ResDatos) request.getAttribute("resDatos");
	AlumPlan alumPlan			= (AlumPlan) request.getAttribute("alumPlan");
	AlumAcademico alumAcademico = (AlumAcademico) request.getAttribute("alumAcademico");
	
	String carpeta 		= application.getRealPath("/WEB-INF/pdf/educacion/"); 
	String dir 			= "";	
	
	Rectangle rec = new Rectangle(14.0f , 21.0f);
	Document document = new Document(PageSize.LETTER);
	document.setMargins(-40,-30,0,0);
	
	try{
		
		if(!new File(carpeta).exists()) new File(application.getRealPath(carpeta)).mkdirs();
		dir = carpeta+alumPersonal.getCodigoPersonal()+".pdf";
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		
		document.addAuthor("UM");
        document.addSubject("Educación Garantizada");
		document.open();
		
		PdfContentByte canvas = pdf.getDirectContentUnder();
		
		Image hojaUno = Image.getInstance(application.getRealPath("/imagenes/")+"/educacionGarantizada.jpg");
	    hojaUno.setAlignment(Image.LEFT | Image.UNDERLYING);
	    hojaUno.scaleAbsolute(612,792);
	    hojaUno.setAbsolutePosition(0, 0);
	    canvas.addImage(hojaUno);
	    
    	Phrase texto1 = new Phrase(alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()+" "+alumPersonal.getNombre(), 
    			FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto1, 183, 662, 0);
    	
    	Phrase texto2 = new Phrase(alumPersonal.getCodigoPersonal(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto2, 467, 662, 0);
    	
    	if(alumAcademico.getResidenciaId().equals("E")){
    		Phrase texto3 = new Phrase(resDatos.getCalle()+" #"+resDatos.getNumero()+" "+resDatos.getColonia()+", "+resDatos.getMpio()+" N.L. MÉXICO", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
        	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto3, 183, 642, 0);
    	}else if(alumAcademico.getResidenciaId().equals("I")){
    		Phrase texto3 = new Phrase("DORMITORIO #"+alumAcademico.getDormitorio()+" CAMPUS UM, MONTEMORELOS N.L. MÉXICO", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
        	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto3, 183, 642, 0);
    	}
    	
    	Phrase texto4 = new Phrase(alumPersonal.getFNacimiento(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto4, 368, 622, 0);
    	
    	if(alumPersonal.getEstadoCivil().equals("S")){
    		Phrase texto5 = new Phrase("X", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
        	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, texto5, 188, 623, 0);
    	}else if(alumPersonal.getEstadoCivil().equals("C")){
    		Phrase texto5 = new Phrase("X", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
        	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, texto5, 220, 623, 0);
    	}
    	
    	String fechaIngreso = alumPlan.getPrimerMatricula().substring(8,10)+"/"+alumPlan.getPrimerMatricula().substring(5,7)+"/"+alumPlan.getPrimerMatricula().substring(0,4);
    	Phrase texto6 = new Phrase(fechaIngreso, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, texto6, 183, 602, 0);
		
	}catch(Exception ex){
			System.err.println("Error al generar el PDF de educacion garantizada: "+ex.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
			
	String nombreArchivo = alumPersonal.getCodigoPersonal()+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>