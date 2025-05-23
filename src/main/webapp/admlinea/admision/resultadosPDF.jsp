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
	String tipoExamen			= "1";
	
	float resLog		= (float)request.getAttribute("resLog");
	float resMat		= (float)request.getAttribute("resMat");
	float resEsp		= (float)request.getAttribute("resEsp");
	float resIng		= (float)request.getAttribute("resIng");
	float resBio		= (float)request.getAttribute("resBio");
	float resFis		= (float)request.getAttribute("resFis");
	float resQui		= (float)request.getAttribute("resQui");
	
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
        
        if(admAcademico.getCarreraId().equals("10301") || admAcademico.getCarreraId().equals("10314") || admAcademico.getCarreraId().equals("10313") || admAcademico.getCarreraId().equals("10303")){
        	tipoExamen = "2";	
        }else{
        	tipoExamen = "1";
        }
      	Phrase titulo = new Phrase( "RESULTADOS DE LA PRUEBA PODIUM "+tipoExamen, fontBold12);
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
    	
     	int tabAncho[] = {33,33,33};     	
     	PdfPTable table = new PdfPTable(3);
     	
	    table.setWidths(tabAncho);
	    table.setWidthPercentage(100);
	    table.setSpacingBefore(150);
	    
	    Paragraph pa = new Paragraph();
	    pa 		= new Paragraph();
		pa.add(new Phrase("Áreas", fontBold11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		if (tipoExamen.equals("1") || tipoExamen.equals("2")){
			pa 		= new Paragraph();
			pa.add(new Phrase("Puntajes mínimos",fontBold11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
		}
		
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
		
 		if (mapaAreas.containsKey(1)){
			puntosRequeridos	+= mapaAreas.get(1).getMinimoPre();
 		}
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(puntosRequeridos),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
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
		
		totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0; tiempo = 0;
 		if (mapaAreas.containsKey(2)){
 				puntosRequeridos	+= mapaAreas.get(2).getMinimoPre();
 		}
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(puntosRequeridos),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
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
		
		totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0; tiempo = 0;
 		if (mapaAreas.containsKey(3)){
			puntosRequeridos	+= mapaAreas.get(3).getMinimoPre();
 		}
 		if (mapaAreas.containsKey(4)){
			puntosRequeridos	+= mapaAreas.get(4).getMinimoPre();
 		}
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(puntosRequeridos),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
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
		
		totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0; tiempo = 0;
 		if (mapaAreas.containsKey(5)){
			puntosRequeridos	+= mapaAreas.get(5).getMinimoPre();
 		}
 		if (mapaAreas.containsKey(6)){
			puntosRequeridos	+= mapaAreas.get(6).getMinimoPre();
 		}
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(puntosRequeridos),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(formato.format(resIng)),fontNormal11));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		int ajusteAltura = 40; 
		
		if(tipoExamen.equals("2")){
			
			ajusteAltura = -18;
			pa = new Paragraph();
		    pa 		= new Paragraph();
			pa.add(new Phrase("Biología",fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0; tiempo = 0;
	 		if (mapaAreas.containsKey(7)){
				puntosRequeridos	+= mapaAreas.get(7).getMinimoPre();
	 		}
			
			pa 		= new Paragraph();
			pa.add(new Phrase(String.valueOf(puntosRequeridos),fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			pa 		= new Paragraph();
			pa.add(new Phrase(String.valueOf(formato.format(resBio)),fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
	
			pa = new Paragraph();
		    pa 		= new Paragraph();
			pa.add(new Phrase("Física",fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0; tiempo = 0;
	 		if (mapaAreas.containsKey(8)){
 				puntosRequeridos	+= mapaAreas.get(8).getMinimoPre();
	 		}
			
			pa 		= new Paragraph();
			pa.add(new Phrase(String.valueOf(puntosRequeridos),fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			pa 		= new Paragraph();
			pa.add(new Phrase(String.valueOf(formato.format(resFis)),fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
	
			pa = new Paragraph();
		    pa 		= new Paragraph();
			pa.add(new Phrase("Química",fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0; tiempo = 0;
	 		if (mapaAreas.containsKey(9)){
 				puntosRequeridos	+= mapaAreas.get(9).getMinimoPre();
	 		}
			
			pa 		= new Paragraph();
			pa.add(new Phrase(String.valueOf(puntosRequeridos),fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			pa 		= new Paragraph();
			pa.add(new Phrase(String.valueOf(formato.format(resQui)),fontNormal11));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
		}	
    	
	    document.add(table);
	    
	    Phrase mensaje = new Phrase("Cuando en una o dos áreas no se alcanza el puntaje mínimo el aspirante tiene la opción de presentar nuevamente SOLO esas áreas.", new Font(base, 11f, Font.ITALIC));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, mensaje, 25, ajusteAltura+430, 0); 
	    Phrase mensaje2 = new Phrase("Si son tres o más tomará de nuevo la prueba COMPLETA. Otra opción es inscribir cursos remediales especificos.", new Font(base, 11f, Font.ITALIC)); 
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
 
 