<%@page import = "java.awt.Color" %>
<%@page import = "java.io.FileOutputStream" %>
<%@page import = "java.io.IOException" %>
<%@page import = "com.itextpdf.text.*" %>
<%@page import = "com.itextpdf.text.pdf.*" %>
<%@page import = "com.itextpdf.text.pdf.events.*" %>

<%@page import = "java.util.List"%>
<%@page import = "java.util.HashMap"%>
<%@page import="aca.podium.spring.ExaArea"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String folio		 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String examenId		 		= request.getParameter("ExamenId")==null?"0":request.getParameter("ExamenId"); 
	String nombreAspirante		= (String)request.getAttribute("nombre");
	String fechaExamen			= (String)request.getAttribute("fecha");
	AdmAcademico admAcademico	= (AdmAcademico)request.getAttribute("admAcademico");
	String nombreCarrera		= (String)request.getAttribute("nombreCarrera");	
	
	float resLog		= (float)request.getAttribute("resLog");
	float resMat		= (float)request.getAttribute("resMat");
	float resEsp		= (float)request.getAttribute("resEsp");
	float resIng		= (float)request.getAttribute("resIng");
	
	float total = resLog+resMat+resEsp+resIng;

	HashMap<Integer,ExaArea> mapaAreas				= (HashMap<Integer,ExaArea>)request.getAttribute("mapaAreas");

	int totPreguntas = 0; int totPuntos = 0; int puntosRequeridos = 0; int tiempo = 0;

	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/resultados/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/resultados/")+"/"+folio+".pdf";	

	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.LETTER ); 
	document.setMargins(25,20,235,30);
	
	BaseFont base = BaseFont.createFont("../../fonts/Baker Signet BT.ttf", BaseFont.WINANSI,true);
	Font fontNormal12 	= new Font(base, 12f, Font.NORMAL);
	Font fontBold12 	= new Font(base, 12f, Font.BOLD);
	Font fontNormal11 	= new Font(base, 11f, Font.NORMAL);
	Font fontBold11 	= new Font(base, 11f, Font.BOLD);
	Font fontNormal8 	= new Font(base, 8f, Font.NORMAL);
	
	//BaseFont base = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI,true);	
	
	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Sistema de Admisiones");       
        
        document.open();//Abrir documento
        
        PdfContentByte canvas = pdf.getDirectContentUnder(); //pdf por posiciones
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
    	//Logo Um y texto debajo    
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logoLineal.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(550, 60);
        jpg.setAbsolutePosition(30, 700);
        document.add(jpg);
        
      	Phrase titulo = new Phrase( "RESULTADOS DE LA PRUEBA PODIUM 3", fontBold12);
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, titulo, 205, 680, 0);
        
      	Phrase fecha = new Phrase( "Fecha: "+fechaExamen, fontNormal11); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fecha, 260, 670, 0); 
        
      	Phrase nombre = new Phrase( nombreAspirante, fontBold11); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, nombre, 25, 640, 0); 
    	Phrase presente = new Phrase( "Presente",fontNormal11); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,presente, 25, 630, 0);
    	
        
      	Phrase saludo = new Phrase("Con aprecio te saludamos y notificamos que despúes de haber sustentado la Prueba Oficial de Ingreso a la Universidad de Montemorelos",fontNormal11);
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, saludo, 25, 610, 0); 
      	
    	Phrase saludo2 = new Phrase("(PODIUM), de la carrera ", fontNormal11);
    	saludo2.add(new Chunk(nombreCarrera+".", fontBold11));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, saludo2, 25, 596, 0); 
    	
    	Phrase saludo3 = new Phrase("Tus resultados fueron los siguientes: ", fontNormal11); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, saludo3, 25, 565, 0);
    	
     	int tabAncho[] = {50, 50};     	
     	PdfPTable table = new PdfPTable(2);
     	
	    table.setWidths(tabAncho);
	    table.setWidthPercentage(100);
	    //table.setSpacingBefore(250);
	    
	    Paragraph pa = new Paragraph();
	    pa 		= new Paragraph();
		pa.add(new Phrase("Áreas", fontBold11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Puntaje obtenido",fontBold11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);

		pa = new Paragraph();
	    pa 		= new Paragraph();
		pa.add(new Phrase("Logica", fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(formato.format(resLog)),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);

		pa = new Paragraph();
	    pa 		= new Paragraph();
		pa.add(new Phrase("Matemáticas",fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		totPreguntas = 0; totPuntos = 0; tiempo = 0;
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(formato.format(resMat)),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);

		pa = new Paragraph();
	    pa 		= new Paragraph();
		pa.add(new Phrase("Español",fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		totPreguntas = 0; totPuntos = 0; tiempo = 0;
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(formato.format(resEsp)),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);

		pa = new Paragraph();
	    pa 		= new Paragraph();
		pa.add(new Phrase("Inglés",fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		totPreguntas = 0; totPuntos = 0; tiempo = 0;
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(formato.format(resIng)),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);

		pa = new Paragraph();
	    pa 		= new Paragraph();
		pa.add(new Phrase("PUNTAJE TOTAL ",fontBold11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		totPreguntas = 0; totPuntos = 0; tiempo = 0;
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(formato.format(total)),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		int ajusteAltura = 18; 	
    	
	    document.add(table);
	    
	    Phrase mensaje = new Phrase("El puntaje mínimo requerido para el ingreso a un programa de maestría es de 400 puntos y para el ingreso", new Font(base, 11f, Font.ITALIC));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, mensaje, 25, ajusteAltura+430, 0); 
	    Phrase mensaje2 = new Phrase("a un programa de doctorado es de 500 puntos.", new Font(base, 11f, Font.ITALIC)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, mensaje2, 25, ajusteAltura+418, 0);
    	
	    Phrase mensaje4 = new Phrase("La presente constancia ", fontNormal11);
	    mensaje4.add(new Chunk("no es una carta de admisión", fontBold11));
	    mensaje4.add(new Chunk(", solo es un informe de resultados de la prueba PODIUM.", fontNormal11));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, mensaje4, 25, ajusteAltura+386, 0);	     

    	Phrase director = new Phrase("Dr. Ramón Andrés Díaz Valladares", fontBold11); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, director, 25, 280, 0); 
    	Phrase director2 = new Phrase("Director de Posgrado e Investigación", new Font(base, 10f, Font.ITALIC)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, director2, 25, 268, 0); 
    	Phrase director3 = new Phrase("Universidad de Montemorelos", new Font(base, 10f, Font.ITALIC)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, director3, 25, 256, 0); 
    	
    	
    	Phrase foot = new Phrase("MONTEMORELOS, NUEVO LEÓN, MÉXICO", fontNormal8); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, foot, 300, 70, 0); 
    	Phrase foot2 = new Phrase("CONMUTADOR (826) 263 0900 ext. 3650 / www.um.edu.mx - email: rdiaz@um.edu.mx", fontNormal8); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, foot2, 300, 58, 0);  
    	Phrase foot4 = new Phrase("Creada por el Gobierno del Estado de Nuevo León, México, mediante resolución Oficial publicada el 05 de mayo de 1973", fontNormal8); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, foot4, 300, 46, 0); 
    	Phrase foot5 = new Phrase("Clave de la Institución ante la SEP y Dirección General de Estadística 19MSU1017U", fontNormal8); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, foot5, 300, 34, 0);   	
    
	}catch(IOException ioe){
		System.err.println("Error al generar el recibo en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
	
	String nombreArchivo = folio+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);	
%>
 
 