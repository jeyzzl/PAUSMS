<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.plan.spring.MapaCurso"%>
<%@ page import="aca.plan.spring.MapaNuevoCurso"%>
<%@ page import="aca.plan.spring.MapaNuevoProducto"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.pron.spring.PronMateria"%>
<%@ page import="aca.pron.spring.PronValor"%>
<%@ page import="aca.pron.spring.PronUnidad"%>
<%@ page import="aca.pron.spring.PronEjes"%>
<%@ page import="aca.pron.spring.PronEsquema"%>
<%@ page import="aca.pron.spring.PronBiblio"%>
<%@ page import="aca.carga.spring.Carga"%>
<%	
	String cursoCargaId						= (String) request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String nombreFacultad 					= (String)request.getAttribute("nombreFacultad");
	String nombreCarrera   	 				= (String)request.getAttribute("nombreCarrera");
	String materia		    				= (String)request.getAttribute("materia");
	MapaCurso mapaCurso						= (MapaCurso)request.getAttribute("mapaCurso");
	MapaNuevoCurso mapaNuevoCurso			= (MapaNuevoCurso)request.getAttribute("mapaNuevoCurso");
	CatCarrera catCarrera					= (CatCarrera)request.getAttribute("catCarrera");
	PronMateria pronMateria					= (PronMateria)request.getAttribute("pronMateria");
	PronValor pronValor						= (PronValor)request.getAttribute("pronValor");
	HashMap<String, PronUnidad> mapaUnidad 	= (HashMap<String,PronUnidad>)request.getAttribute("mapaUnidad");
	PronEjes pronEjes						= (PronEjes)request.getAttribute("pronEjes");
	String codigoMaestro		    		= (String)request.getAttribute("codigoMaestro");
	String nombreMaestro		    		= (String)request.getAttribute("nombreMaestro");
	Carga carga								= (Carga)request.getAttribute("carga");
	
	List<PronEsquema> lisEsquema 		= (List<PronEsquema>)request.getAttribute("lisEsquema");
	List<PronBiblio> lisBiblio			= (List<PronBiblio>)request.getAttribute("lisBiblio");
	List<MapaNuevoProducto> lisProductos= (List<MapaNuevoProducto>)request.getAttribute("productos");

	List<aca.plan.spring.MapaNuevoUnidad> unidades 				= (List<aca.plan.spring.MapaNuevoUnidad>)request.getAttribute("unidades");
	List<aca.pron.spring.PronSemana> semanasUnidad 	= (List<aca.pron.spring.PronSemana>)request.getAttribute("semanasUnidad");
	
	//------PDF----->
	int posX 	= 0; 
	int posY	= 0;	
	
	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.LETTER ); 
	document.setMargins(-30,-30,50,30);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/prontuario/");
	String dir = application.getRealPath("/WEB-INF/pdf/prontuario/")+"/"+cursoCargaId+".pdf";
	
	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Academic Management");
        document.addSubject("Course Plan "+cursoCargaId);        
        
        document.open();//Abrir documento
        
        PdfContentByte canvas = pdf.getDirectContentUnder();
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
    //Logo Um y texto debajo
    
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_small.jpg");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(90, 90);
        jpg.setAbsolutePosition(70, 675);
        document.add(jpg);
        
    //Encabezado	
    
    	String nivel = "-";
    	if (!catCarrera.getFacultadId().equals("107")) nivel = "Semester"; else nivel = "Tetramester";
    	
    	if(carga.getCiclo().equals("1")){
    		nivel = "First "+nivel;
    	}else if(carga.getCiclo().equals("2")){
    		nivel = "Second "+nivel;
    	}else if(carga.getCiclo().equals("3")){
    		nivel = "Third "+nivel;
    	}else if(carga.getCiclo().equals("4")){
    		nivel = "Forth "+nivel;
    	}else if(carga.getCiclo().equals("5")){
    		nivel = "Fifth "+nivel;
    	}else if(carga.getCiclo().equals("6")){
    		nivel = "Sixth "+nivel;
    	}else if(carga.getCiclo().equals("7")){
    		nivel = "Seventh "+nivel;
    	}else if(carga.getCiclo().equals("8")){
    		nivel = "Eigth "+nivel;
    	}else if(carga.getCiclo().equals("9")){
    		nivel = "Ninth "+nivel;
    	}
    		
    	String periodo = carga.getNombreCarga().substring(0, 10);
		
	 	Phrase uni = new Phrase( "PACIFIC ADVENTIST UNIVERSITY", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0)) );   	
   		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+360, posY+730, 0);
	    	
    	Phrase facultad = new Phrase( "School of "+nombreFacultad, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, facultad, posX+360, posY+710, 0);

    	Phrase carrera = new Phrase( nombreCarrera, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, carrera, posX+360, posY+690, 0);

    	Phrase curso = new Phrase( "School Cycle "+periodo, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, curso, posX+360, posY+640, 0);	

    	Phrase semestre = new Phrase( nivel, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, semestre, posX+360, posY+625, 0);	

    	Phrase plan = new Phrase( "Course Plan of "+materia, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, plan, posX+360, posY+600, 0);	
	 	
    	
    	Phrase textIma1 = new Phrase( "Established by the Government", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
   		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma1, posX+115, posY+650, 0);
	    	
    	Phrase textIma2 = new Phrase( "del estado de Nuevo León,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma2, posX+115, posY+640, 0);

    	Phrase textIma3 = new Phrase( "México, mediante Resolución", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma3, posX+115, posY+630, 0);

    	Phrase textIma4 = new Phrase( "Oficial publicada el 5 de mayo", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma4, posX+115, posY+620, 0);	

    	Phrase textIma5 = new Phrase( "de 1973", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma5, posX+115, posY+610, 0);	

    	Phrase textIma6 = new Phrase( "Clave de la Institución ante la", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
   		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma6, posX+115, posY+590, 0);
	    	
    	Phrase textIma7 = new Phrase( "SEP y la Dirección General de", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma7, posX+115, posY+580, 0);

    	Phrase textIma8 = new Phrase( "Estadística 19MSU1017U", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, textIma8, posX+115, posY+570, 0);
    	
   	//Tabla Invisible
    	
    	PdfPTable tablaInvisible = new PdfPTable(1);
		int tablaInvisibleWidths[] = {100};
		tablaInvisible.setWidths(tablaInvisibleWidths);
		tablaInvisible.setSpacingBefore(200f);
		tablaInvisible.setWidthPercentage(80f);    	
	   	
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaInvisible.addCell(celda);
		tablaInvisible.addCell(celda);
		tablaInvisible.addCell(celda);
		tablaInvisible.addCell(celda);
		tablaInvisible.addCell(celda);
		tablaInvisible.addCell(celda);
		tablaInvisible.addCell(celda);
			
		document.add(tablaInvisible);	
		
	//Primera Tabla
	
		PdfPTable tabla1 = new PdfPTable(6);
		int tabla1Widths[] = {10,10,10,10,10,10};
		tabla1.setWidths(tabla1Widths);
		tabla1.setSpacingBefore(100f);
		tabla1.setWidthPercentage(80f);		
		
		//Primera fila
		String nombreCiclo = "";
		if (!catCarrera.getFacultadId().equals("107")) nombreCiclo = "Semester "; else nombreCiclo = "Tetramester ";
		
		celda = new PdfPCell(new Phrase("Location:", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+nombreCiclo+mapaCurso.getCiclo(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);

		celda = new PdfPCell(new Phrase("Key:", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+mapaCurso.getCursoClave(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("Serial: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);

		celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		//Segunda fila
		
		celda = new PdfPCell(new Phrase("Credits: "+mapaCurso.getCreditos(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase("HFD: "+mapaCurso.getHfd(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase("HEI:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+mapaCurso.getHi(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		int ths = 0;
		ths = Integer.parseInt(mapaCurso.getHp())+Integer.parseInt(mapaCurso.getHt())+Integer.parseInt(mapaCurso.getHi());
		celda = new PdfPCell(new Phrase("THS:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);

		celda = new PdfPCell(new Phrase(""+ths, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);

		//Tercera fila		
		celda = new PdfPCell(new Phrase("Competency(s) addressed by the course: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getCompetencia(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(4);
		tabla1.addCell(celda);

		//Cuarta fila
		
		celda = new PdfPCell(new Phrase("Learning assets: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getDescripcion(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(4);
		tabla1.addCell(celda);
		
		//Quinta fila
		
		celda = new PdfPCell(new Phrase("Integration project: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getProyecto(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(4);
		tabla1.addCell(celda);
		
		//Sexta fila
		
		celda = new PdfPCell(new Phrase("Class Schedule: "+pronMateria.getHoraClase(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Place: "+pronMateria.getLugar(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(4);
		tabla1.addCell(celda);
		
		//Septima fila
		
		celda = new PdfPCell(new Phrase("Professor: "+nombreMaestro, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Academic background:", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);

		celda = new PdfPCell(new Phrase(pronMateria.getFormacion(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		//Ocatava fila
		
		celda = new PdfPCell(new Phrase("Tutoring schedule: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronMateria.getHoraTutoria(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla1.addCell(celda);

		celda = new PdfPCell(new Phrase("Email: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronMateria.getCorreo(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla1.addCell(celda);
	
		//Noveba fila sin borde
		
		celda = new PdfPCell(new Phrase("HFP = Hours In Front of Professor ", FontFactory.getFont(FontFactory.HELVETICA, 5, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		celda = new PdfPCell(new Phrase("ISH = Independent Study Hours ", FontFactory.getFont(FontFactory.HELVETICA, 5, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla1.addCell(celda);

		celda = new PdfPCell(new Phrase("TWH = Total Weekly Hours ", FontFactory.getFont(FontFactory.HELVETICA, 5, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla1.addCell(celda);
		
		document.add(tabla1);
		
	//Segunda Tabla
		
		PdfPTable tabla2 = new PdfPTable(1);
		int tabla2Widths[] = {100};
		tabla2.setWidths(tabla2Widths);
		tabla2.setSpacingBefore(20f);
		tabla2.setWidthPercentage(80f);
	   	
		Paragraph parrafo = new Paragraph();
		parrafo.add(new Phrase("COURSE DESCRIPTION ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);

		Paragraph parrafoBlanco = new Paragraph();
		celda = new PdfPCell (new Phrase(pronMateria.getDescripcion()));		
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		celda = new PdfPCell (new Phrase(" "));
		celda.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(celda);
		
		Paragraph parrafo1 = new Paragraph();
		parrafo1.add(new Phrase("COURSE PERSPECTIVE AND APPROACH ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo1);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(parrafo1);
		celda = new PdfPCell(new Phrase(pronMateria.getEnfoque()));		
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		celda = new PdfPCell (new Phrase(" "));
		celda.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(celda);
		
		Paragraph parrafo2 = new Paragraph();
		parrafo2.add(new Phrase("SPECIAL COURSE REQUIREMENTS ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo2);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(parrafo2);
		celda = new PdfPCell(new Phrase(pronMateria.getEspecial()));		
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		celda = new PdfPCell (new Phrase(" "));
		celda.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(celda);
		
		Paragraph parrafo3 = new Paragraph();
		parrafo3.add(new Phrase("ACADEMIC INTEGRITY ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo3);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(parrafo3);
		celda = new PdfPCell(new Phrase(pronMateria.getIntegridad()));		
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		celda = new PdfPCell (new Phrase(" "));
		celda.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(celda);

		Paragraph parrafo4 = new Paragraph();
		parrafo4.add(new Phrase("LEARNING ORGANIZATION", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo4);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
 		tabla2.addCell(celda); 
 		
		document.add(tabla2);	
		
	//Ciclo tablas unidades
	
		for(aca.plan.spring.MapaNuevoUnidad unidad : unidades){
			
			PdfPTable tablaUnidad = new PdfPTable(4);
			int tablaUnidadWidths[] = {20,20,20,20};
			tablaUnidad.setWidths(tablaUnidadWidths);
			tablaUnidad.setSpacingBefore(20f);
			tablaUnidad.setWidthPercentage(80f);
			
			celda = new PdfPCell(new Phrase(""+unidad.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(4);
			tablaUnidad.addCell(celda);
			
			String productos = "-";
			for (MapaNuevoProducto producto : lisProductos){
				if (producto.getUnidadId().equals(unidad.getUnidadId())){
					productos += producto.getDescripcion().replaceAll("\n","")+" ";
				}
			}
			
			celda = new PdfPCell(new Phrase("Learning assets:"+productos, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(4);
			tablaUnidad.addCell(celda);
			
			String aporte = mapaUnidad.containsKey(unidad.getUnidadId())?mapaUnidad.get(unidad.getUnidadId()).getAporte():"-";
	
			celda = new PdfPCell(new Phrase("Contribution and integrating project: "+aporte, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(4);
			tablaUnidad.addCell(celda);
	
			celda = new PdfPCell(new Phrase("Date and Week", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaUnidad.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Content", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaUnidad.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Learning Activities", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaUnidad.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Learning evidences", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaUnidad.addCell(celda);
			
			for(aca.pron.spring.PronSemana semana : semanasUnidad){

				if(semana.getUnidadId().equals(unidad.getUnidadId())){

					celda = new PdfPCell(new Phrase(semana.getSemanaNombre(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.BOX);
					celda.setBorderWidth(0.5f);
					celda.setColspan(0);
					tablaUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase(semana.getContenido(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.BOX);
					celda.setBorderWidth(0.5f);
					celda.setColspan(0);
					tablaUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase(semana.getActividades(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.BOX);
					celda.setBorderWidth(0.5f);
					celda.setColspan(0);
					tablaUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase(semana.getEvidencias(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.BOX);
					celda.setBorderWidth(0.5f);
					celda.setColspan(0);
					tablaUnidad.addCell(celda);
				}
			}
			document.add(tablaUnidad);
		}
		
	//Tabla Ejes
	/*
		PdfPTable tablaEjes = new PdfPTable(1);
		int tablaEjesWidths[] = {100};
		tablaEjes.setWidths(tablaEjesWidths);
		tablaEjes.setSpacingBefore(20f);
		tablaEjes.setWidthPercentage(80f);
		
		Paragraph parrafoEjes = new Paragraph();
		parrafoEjes.add(new Phrase("ABORDAJE DE LOS EJES TRANSVERSALES Y LOS VALORES INSTITUCIONALES", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafoEjes);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		parrafoBlanco.add(new Phrase());
		celda = new PdfPCell(parrafoBlanco);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);

		Paragraph parrafoEjes2 = new Paragraph();
		parrafoEjes2.add(new Phrase("EJES TRANSVERSALES", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafoEjes2);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(parrafoBlanco);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);
	
		if(!pronEjes.getFe().equals("-")){
		celda = new PdfPCell(new Phrase("Integración de la fe y el aprendizaje", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);		
		
		celda = new PdfPCell(new Phrase(pronEjes.getFe(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		if(!pronEjes.getPensamiento().equals("-")){
		celda = new PdfPCell(new Phrase("Pensamiento crítico", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronEjes.getPensamiento(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		if(!pronEjes.getAmbiente().equals("-")){
		celda = new PdfPCell(new Phrase("Cuidado del ambiente", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronEjes.getAmbiente(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		if(!pronEjes.getLiderazgo().equals("-")){
		celda = new PdfPCell(new Phrase("Liderazgo", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronEjes.getLiderazgo(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		if(!pronEjes.getEmprendimiento().equals("-")){
		celda = new PdfPCell(new Phrase("Emprendimiento", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronEjes.getEmprendimiento(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		if(!pronEjes.getSustentabilidad().equals("-")){
		celda = new PdfPCell(new Phrase("Sustentabilidad", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronEjes.getSustentabilidad(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		if(!pronEjes.getServicio().equals("-")){
		celda = new PdfPCell(new Phrase("Servicio abnegado", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronEjes.getServicio(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		if(!pronEjes.getInvestigacion().equals("-")){		
		celda = new PdfPCell(new Phrase("Investigación", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		
		celda = new PdfPCell(new Phrase(pronEjes.getInvestigacion(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		celda.setPaddingBottom(10);
		tablaEjes.addCell(celda);
		}
		
		parrafoBlanco.add(new Phrase());
		celda = new PdfPCell(parrafoBlanco);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);
		tablaEjes.addCell(celda);
		
		document.add(tablaEjes);	
		*/
		//Tabla Valores
		/*
		PdfPTable tablaValores = new PdfPTable(1);
		int tablaValoresWidths[] = {100};
		tablaValores.setWidths(tablaValoresWidths);
		tablaValores.setSpacingBefore(20f);
		tablaValores.setWidthPercentage(80f);
		
		Paragraph parrafoValores = new Paragraph();
		parrafoValores.add(new Phrase("VALORES INSTITUCIONALES", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafoValores);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaValores.addCell(celda);

		parrafoBlanco.add(new Phrase());
		celda = new PdfPCell(parrafoBlanco);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaValores.addCell(celda);
		tablaValores.addCell(celda);
		tablaValores.addCell(celda);
	
		if(!pronValor.getAmor().equals("-")){
			celda = new PdfPCell(new Phrase("Amor", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);		
			
			celda = new PdfPCell(new Phrase(pronValor.getAmor(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getLealtad().equals("-")){
			celda = new PdfPCell(new Phrase("Lealtad", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getLealtad(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getConfianza().equals("-")){
			celda = new PdfPCell(new Phrase("Confianza", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getConfianza(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getReverencia().equals("-")){
			celda = new PdfPCell(new Phrase("Reverencia", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getReverencia(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getObediencia().equals("-")){
			celda = new PdfPCell(new Phrase("Obediencia", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getObediencia(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getArmonia().equals("-")){
			celda = new PdfPCell(new Phrase("Armonía", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getArmonia(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getRespeto().equals("-")){
			celda = new PdfPCell(new Phrase("Respeto", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getRespeto(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getPureza().equals("-")){		
			celda = new PdfPCell(new Phrase("Pureza", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getPureza(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getHonestidad().equals("-")){		
			celda = new PdfPCell(new Phrase("Honestidad", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getHonestidad(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getVeracidad().equals("-")){		
			celda = new PdfPCell(new Phrase("Veracidad", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getVeracidad(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getContentamiento().equals("-")){		
			celda = new PdfPCell(new Phrase("Contentamiento", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getContentamiento(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		if(!pronValor.getServicio().equals("-")){		
			celda = new PdfPCell(new Phrase("Servicio", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tablaValores.addCell(celda);
			
			celda = new PdfPCell(new Phrase(pronValor.getServicio(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingBottom(10);
			tablaValores.addCell(celda);
		}
		
		parrafoBlanco.add(new Phrase());
		celda = new PdfPCell(parrafoBlanco);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaValores.addCell(celda);
		tablaValores.addCell(celda);
		tablaValores.addCell(celda);
		tablaValores.addCell(celda);
		
		document.add(tablaValores);
		*/
		//Tabla Esquema

		PdfPTable tablaEsquema = new PdfPTable(1);
		int tablaEsquemaWidths[] = {100};
		tablaEsquema.setWidths(tablaEsquemaWidths);
		tablaEsquema.setSpacingBefore(20f);
		tablaEsquema.setWidthPercentage(80f);
		
		Paragraph parrafoEsquema = new Paragraph();
		parrafoEsquema.add(new Phrase("GRADING GUIDELINES", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafoEsquema);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaEsquema.addCell(celda);
		
		document.add(tablaEsquema);
		
		//Tabla
		PdfPTable tabla = new PdfPTable(3);
		int tablaWidths[] = {60,15,15};
		tabla.setWidths(tablaWidths);
		tabla.setSpacingBefore(15f);
		tabla.setWidthPercentage(70f);
		
		int total = 0;
		String tipo = "";
		String signo = "";
		for(PronEsquema esquema : lisEsquema){
			celda = new PdfPCell(new Phrase(esquema.getEstrategiaNombre(), FontFactory.getFont(FontFactory.HELVETICA, 8, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tabla.addCell(celda);
			
			if(esquema.getTipo().equals("P")){
				tipo = "Percentage (%)";
				signo = "%";
			}else{
				tipo = "Value (PTS)";
				signo = "POINTS";
			}
	
			celda = new PdfPCell(new Phrase(tipo, FontFactory.getFont(FontFactory.HELVETICA, 8, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tabla.addCell(celda);
			
			celda = new PdfPCell(new Phrase(esquema.getValor()+signo, FontFactory.getFont(FontFactory.HELVETICA, 8, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tabla.addCell(celda);
			
			total += Integer.parseInt(esquema.getValor());
		}
		
		celda = new PdfPCell(new Phrase("Final Grade", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(2);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase(total+signo, FontFactory.getFont(FontFactory.HELVETICA, 8, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla.addCell(celda);
		
		parrafoBlanco.add(new Phrase());
		celda = new PdfPCell(parrafoBlanco);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla.addCell(celda);
		tabla.addCell(celda);
		tabla.addCell(celda);
		tabla.addCell(celda);
		
		document.add(tabla);
		
		
		//Tabla Biblio

		PdfPTable tablaBiblio = new PdfPTable(1);
		int tablaBiblioWidths[] = {100};
		tablaBiblio.setWidths(tablaBiblioWidths);
		tablaBiblio.setSpacingBefore(20f);
		tablaBiblio.setWidthPercentage(80f);
		
		Paragraph parrafoBiblio = new Paragraph();
		parrafoBiblio.add(new Phrase("BIBLIOGRAPHY .", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafoBiblio);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tablaBiblio.addCell(celda);
		
		for(PronBiblio biblio : lisBiblio){
			celda = new PdfPCell(new Phrase(biblio.getBiblioNombre(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			celda.setPaddingTop(10);
			tablaBiblio.addCell(celda);
		}
	
		document.add(tablaBiblio);	

	}catch(IOException ioe){
		System.err.println("Error generating PDF receipt: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = cursoCargaId+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>
