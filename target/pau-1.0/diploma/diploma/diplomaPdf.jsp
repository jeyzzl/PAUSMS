<%@ page import = "java.util.List"%>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "aca.diploma.spring.DipCurso"%>
<%@ page import = "aca.diploma.spring.DipAlumno"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "java.io.File" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@ page import = "com.itextpdf.layout.element.Text" %> 

<%
	String diplomaId 			= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");
	String codigoPersonal 		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");	
	DipCurso dipCurso 			= (DipCurso)request.getAttribute("dipCurso");
	DipAlumno dipAlumno 		= (DipAlumno)request.getAttribute("dipAlumno");
	
	File carpeta 		= new File(application.getRealPath("/pdf/diploma/"));
	if(!carpeta.exists()) carpeta.mkdirs();	
	String dir 			= carpeta+"/"+dipAlumno.getCodigoPersonal().replace("*","")+".pdf";	
	PdfPCell celda 		= null;
	
	//int r = 71, g = 75, b = 78;
	int r = 90, g = 90, b = 90, a=255;
	
	Document document = new Document(PageSize.LETTER.rotate());
	//Crea un objeto para el documento PDF
	// Parametros: Izquierda,derecha,arriba,abajo
	document.setMargins(30,30,110,45);		
	
	BaseFont baseTitulo	= BaseFont.createFont("../../fonts/TrajanRegular.ttf", BaseFont.WINANSI,true);
	BaseFont baseDatos	= BaseFont.createFont("../../fonts/ActaDisplayMedium.ttf", BaseFont.WINANSI, true);	
	
	Font tituloGrande	 	= new Font(baseTitulo, 20f, Font.NORMAL);
	tituloGrande.setColor(new BaseColor(r,g,b,a));
	
	Font tituloMediano	 	= new Font(baseTitulo, 15f, Font.NORMAL);
	tituloMediano.setColor(new BaseColor(r,g,b,a));
	
	Font tituloMedianoBold 	= new Font(baseTitulo, 15f, Font.BOLD);
	tituloMedianoBold.setColor(new BaseColor(r,g,b,a));
	
	Font tituloSmall	 	= new Font(baseTitulo, 12f, Font.NORMAL);
	tituloSmall.setColor(new BaseColor(r,g,b,a));
	
	
	Font fontBold15	 	= new Font(baseDatos, 15f, Font.BOLD);
	fontBold15.setColor(new BaseColor(r,g,b,a));
	
	Font fontNormal12	 	= new Font(baseDatos, 12f, Font.NORMAL);
	fontNormal12.setColor(new BaseColor(r,g,b,a));
	
	Font fontBold12	 	= new Font(baseDatos, 12f, Font.BOLD);
	fontBold12.setColor(new BaseColor(r,g,b,a));
	
	Font fontNormal11	 	= new Font(baseDatos, 11f, Font.NORMAL);
	fontNormal11.setColor(new BaseColor(r,g,b,a));	
	
	BaseFont baseTexto	= BaseFont.createFont("../../fonts/eb-garamond-v9-latin-regular.ttf", BaseFont.WINANSI,true);
	Font fontTexto15	 	= new Font(baseTexto, 15f, Font.NORMAL);
	fontTexto15.setColor(new BaseColor(r,g,b,a));
	
	
	try{
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addTitle("Diploma");
		document.addAuthor("Sistema Académico");
            
        /******* Clase para sobreescribir el metodo OnStartPage *******/
	    document.open();
	    
	    Image fondo = Image.getInstance(application.getRealPath("/imagenes/")+"/diploma.jpg");
	    fondo.setAlignment(Image.LEFT | Image.UNDERLYING);
	    fondo.scaleAbsolute(795,550);
	    fondo.setAbsolutePosition(0, 20);
        document.add(fondo);

        Image firmaUno = Image.getInstance(application.getRealPath("/imagenes/")+"/FirmaRaquel.png");
        firmaUno.setAlignment(Image.LEFT | Image.UNDERLYING);
        firmaUno.scaleAbsolute(250,138);
        firmaUno.setAbsolutePosition(45, 28);
        document.add(firmaUno);

        Image firmaDos = Image.getInstance(application.getRealPath("/imagenes/")+"/FirmaIsmael.png");
        firmaDos.setAlignment(Image.LEFT | Image.UNDERLYING);
        firmaDos.scaleAbsolute(250,100);
        firmaDos.setAbsolutePosition(505, 55);
        document.add(firmaDos);
           
        if (dipAlumno.getImagenQr()!=null){
        	Image qr = Image.getInstance(dipAlumno.getImagenQr());
        	qr.setAlignment(Image.LEFT | Image.UNDERLYING);
 		    qr.scaleAbsolute(120,120);
 		    qr.setAbsolutePosition(677, 200);
            document.add(qr);            	
        }
        
        PdfPTable datos = new PdfPTable(1);
   		int datosWidths[] = {100};
   		datos.setWidthPercentage(100);
   		datos.setWidths(datosWidths);
   		//datos.setTotalWidth(200f);
   		  
        Paragraph parrafo = new Paragraph();       
        
        parrafo = new Paragraph("LA UNIVERSIDAD DE MONTEMORELOS", tituloGrande);
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
		celda = new PdfPCell(parrafo);
		celda.setLeading(0.7f, 1.5f);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);				
		datos.addCell(celda);
        
        parrafo = new Paragraph(dipCurso.getInstitucion().equals("-")?" ":dipCurso.getInstitucion(), tituloMediano);		
		celda = new PdfPCell(parrafo);
		celda.setLeading(0.7f, 1.5f);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);				
		datos.addCell(celda);
		
		String quienExtiende = "extiende la presente";
		if (dipCurso.getInstitucion().length() >= 2 ) quienExtiende = "extienden la presente";
			
		parrafo = new Paragraph(quienExtiende,fontNormal12);
		//parrafo = new Paragraph();       
		//parrafo.add(new Phrase(quienExtiende, FontFactory.getFont(FontFactory.TIMES, 12,Font.NORMAL, new BaseColor(r,g,b,a))));
		
		celda = new PdfPCell(parrafo);
		celda.setLeading(0.7f, 1.5f);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPaddingBottom(40);
		datos.addCell(celda);
		
		parrafo = new Paragraph(dipAlumno.getNombre().equals("-")?" ":dipAlumno.getNombre(), fontBold15);	
		//parrafo = new Paragraph();       
		//parrafo.add(new Phrase(dipAlumno.getNombre().equals("-")?" ":dipAlumno.getNombre(), FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD, new BaseColor(r,g,b,a))));
		celda = new PdfPCell(parrafo);
		celda.setLeading(0.7f, 1.5f);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPaddingTop(35);
		celda.setPaddingBottom(12);
		datos.addCell(celda);
		
		parrafo = new Paragraph("en virtud de que tomó el curso",fontNormal12);	
		//parrafo = new Paragraph();       
		//parrafo.add(new Phrase("en virtud de que tomó el curso", FontFactory.getFont(FontFactory.TIMES, 12,Font.NORMAL, new BaseColor(r,g,b,a))));
		celda = new PdfPCell(parrafo);
		celda.setLeading(0.7f, 1.5f);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPaddingBottom(10);
		datos.addCell(celda);
		
		parrafo = new Paragraph(dipCurso.getCurso().equals("-")?" ":dipCurso.getCurso(),tituloMedianoBold);	
		//parrafo = new Paragraph(); 
		//parrafo.add(new Phrase(dipCurso.getCurso().equals("-")?" ":dipCurso.getCurso(), FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD, new BaseColor(r,g,b,a))));
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPaddingTop(8);
		datos.addCell(celda);

		parrafo = new Paragraph(dipCurso.getTema().equals("-")?" ":dipCurso.getTema(), tituloSmall);
		//parrafo = new Paragraph(); 
		//parrafo.add(new Phrase(dipCurso.getTema().equals("-")?" ":dipCurso.getTema(), FontFactory.getFont(FontFactory.TIMES, 12,Font.NORMAL, new BaseColor(r,g,b,a))));
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPaddingTop(5);
		datos.addCell(celda);

		parrafo = new Paragraph(dipCurso.getHoras().equals("-")?" ":dipCurso.getHoras(), fontNormal12);
		//parrafo = new Paragraph();
		//parrafo.add(new Phrase(dipCurso.getHoras().equals("-")?" ":dipCurso.getHoras(), FontFactory.getFont(FontFactory.TIMES, 12,Font.NORMAL, new BaseColor(r,g,b,a))));		
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPaddingTop(16);
		datos.addCell(celda);
		
		parrafo = new Paragraph(dipCurso.getPeriodo().equals("-")?" ":dipCurso.getPeriodo(),fontNormal12);
		//parrafo = new Paragraph();
		//parrafo.add(new Phrase(dipCurso.getPeriodo().equals("-")?" ":dipCurso.getPeriodo(), FontFactory.getFont(FontFactory.TIMES, 12,Font.NORMAL, new BaseColor(r,g,b,a))));
		
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		datos.addCell(celda);

		parrafo = new Paragraph(dipCurso.getFecha().equals("-")?" ":dipCurso.getFecha(),fontNormal12);
		//parrafo = new Paragraph();
		//parrafo.add(new Phrase(dipCurso.getFecha().equals("-")?" ":dipCurso.getFecha(), FontFactory.getFont(FontFactory.TIMES, 12,Font.NORMAL, new BaseColor(r,g,b,a))));
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPaddingTop(20);
		datos.addCell(celda);
		document.add(datos);
		
		PdfPTable tablaFirma = new PdfPTable(3);
   		int tablaFirmaWidths[] = {30,40,30};
   		tablaFirma.setWidths(tablaFirmaWidths);    		    		
   		tablaFirma.setWidthPercentage(90);
   		tablaFirma.setHorizontalAlignment(Element.ALIGN_CENTER);
   		tablaFirma.setSpacingBefore(55);
   		
   		/* FIRMAS */	
		
		String array[] = dipCurso.getFirmaUno().split("-");
		parrafo = new Paragraph(dipCurso.getFirmaUno().equals("-")?" ":dipCurso.getFirmaUno().replace("-", "\n"),fontNormal11);
		//parrafo = new Paragraph();
		//parrafo.add(new Phrase(dipCurso.getFirmaUno().equals("-")?" ":dipCurso.getFirmaUno().replace("-", "\n"), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11,Font.NORMAL, new BaseColor(r,g,b,a))));
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);			
		tablaFirma.addCell(celda);		
		
		parrafo = new Paragraph();
		parrafo.add(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11,Font.NORMAL, new BaseColor(r,g,b,a))));		
   		celda = new PdfPCell(parrafo);	
   		celda.setBorder(Rectangle.NO_BORDER);			
		tablaFirma.addCell(celda);
		
		parrafo = new Paragraph(dipCurso.getFirmaDos().equals("-")?" ":dipCurso.getFirmaDos().replace("-", "\n"),fontNormal11);
		//parrafo = new Paragraph();
		//parrafo.add(new Phrase(dipCurso.getFirmaDos().equals("-")?" ":dipCurso.getFirmaDos().replace("-", "\n"), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11,Font.NORMAL, new BaseColor(r,g,b,a))));
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);			
		tablaFirma.addCell(celda);			
		
		document.add(tablaFirma);
		
		document.close();
        
	}catch(IOException ioe){
		System.err.println("Error certificado en PDF: "+ioe.getMessage());
	}
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}

	String nombreArchivo = dipAlumno.getCodigoPersonal().replace("*","")+".pdf";	
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>