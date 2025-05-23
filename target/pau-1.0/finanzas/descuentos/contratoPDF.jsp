<%@page import="aca.util.Fecha"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>

<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@page import="java.io.File"%>

<jsp:useBean id="alumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="contCcosto" scope="page" class="aca.financiero.ContCcosto"/>
<jsp:useBean id="alumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="descuento" scope="page" class="aca.alumno.AlumDescuento"/>
<jsp:useBean id="descuentoU" scope="page" class="aca.alumno.AlumDescuentoUtil"/>
<jsp:useBean id="carrera" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha"/>

<% 
	
	File fileCarpeta = new File(application.getRealPath("/WEB-INF/pdf/contrato/"));
	if(!fileCarpeta.exists()) fileCarpeta.mkdirs();

	String idEjercicio 						= request.getParameter("idEjercicio");
	String institucion 						= (String) session.getAttribute("institucion");
	String codigoAlumno	 					= request.getParameter("CodigoAlumno");	
	String carpeta							= application.getRealPath("/WEB-INF/pdf/contrato/");
	String dir 								= carpeta+"/"+codigoAlumno+".pdf";
	String ejercicioId						= request.getParameter("EjercicioId");
	java.text.DecimalFormat getformato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	//Mapeo para carrera del alumno
	carrera = CarreraU.mapeaRegIdsinFac(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, codigoAlumno));	
	
	
	//Mapeo para nombre de centro de costo
	
	int posX 	= 0; 
	int posY	= 0;
	
	ArrayList<String> SemLineas = new ArrayList<String>();
	PdfPCell celda = null;
	int[] pagPrn = {1,0,0};
	
	float size0		= 6f;
	float size1		= 7.7f;
	float size2		= 10f;
	float size3		= 12f;	
	
	int paginaAnterior		= 1;
	boolean printEncabezado = false;
	
	alumPersonal = AlumUtil.mapeaRegId(conEnoc, codigoAlumno);
	
	//Creación de la fuente
	BaseFont base = BaseFont.createFont("../../fonts/Baker Signet BT.ttf", BaseFont.WINANSI,true);
	Font font = new Font(base, 23f, Font.NORMAL);	
	
	Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
	document.setMargins(-30,-30,50,30);
	//document.setMargins(-40,-40,84,30);	
	
	try{
		if(!new File(carpeta).exists()) new File(carpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addTitle(""+aca.parametros.Parametros.getInstitucion(conEnoc, "1"));
		document.addAuthor("Sistema Académico");
        document.addSubject("Contrato de "+codigoAlumno);    
	    document.open();
	    
	    Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/encabezado.png");
	    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	    jpg.scaleAbsolute(480,36);
	    jpg.setAbsolutePosition(posX+65, posY+732);
	    document.add(jpg);
	    
	    
	    
	    Image marcaAgua = Image.getInstance(application.getRealPath("/imagenes/")+"/flama2.png");
	    marcaAgua.setAbsolutePosition(document.getPageSize().getWidth()-300, 5f);
	    //marcaAgua.scaleAbsolute(document.getPageSize().getWidth()-25, document.getPageSize().getHeight()-100);
	    document.add(marcaAgua);
	    
	    int r = 0, g = 0, b = 0;	   
	     
	    PdfContentByte canvas = pdf.getDirectContentUnder();
	    
	    Phrase encabezado = new Phrase( "CREADA POR EL GOBIERNO DEL ESTADO DE NUEVO LEÓN, MÉXICO MEDIANTE LA RESOLUCIÓN OFICIAL PUBLICADA EL 5 DE MAYO DE 1973. CLAVE DE LA INSTITUCIÓN ANTE LA SEP Y DIRECCIÓN",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, encabezado, posX+25, posY+724, 0);
    	
    	Phrase encabezado2 = new Phrase( "GENERAL DE ESTADÍSTICA 19MSU1017U",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, encabezado2, posX+250, posY+718, 0);
    	 
	    
	    /*Phrase uni = new Phrase( institucion.toUpperCase(),  FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+340, posY+720, 0);*/
    	
    	Phrase servicio = new Phrase( "DESCUENTO ESPECIAL PARA ALUMNOS",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, servicio, posX+308, posY+695, 0);	     
    	
    	Phrase generales = new Phrase( "DATOS GENERALES",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, generales, posX+25, posY+670, 0);
    	
    	/*String universidad = "UNIVERSIDAD DE MONTEMORELOS";
    	Phrase prueba = new Phrase( universidad,  font );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, prueba, posX+340, posY+717, 0);*/
    	
    	// Insertar linea debajo de DATOS GENERALES
    	canvas.setLineWidth(2.0f); 
        canvas.setGrayStroke(0.5f);     
        canvas.moveTo(posX+25, posY+665);
        canvas.lineTo(posX+25 + document.getPageSize().getWidth()-50, posY+665); 
        canvas.stroke();
        
        // Rectangulo Matricula
        canvas.saveState();        
        canvas.setColorStroke(new BaseColor(0,0,0));        
        canvas.setLineWidth(0.5f);
        canvas.roundRectangle(posX+25, posY+645, 70, 15, 7);
        canvas.stroke();
        canvas.restoreState();
        
        // Dato de Matrícula
        Phrase matricula = new Phrase( codigoAlumno,  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, matricula, posX+41, posY+649, 0);     
    	
    	// Texto de Matrícula
        Phrase textoMatricula = new Phrase( "MATRÍCULA",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoMatricula, posX+37, posY+637, 0);
        
    	// Rectangulo nombre
        canvas.saveState();        
        canvas.setColorStroke(new BaseColor(0,0,0));        
        canvas.setLineWidth(0.5f);
        canvas.roundRectangle(posX+100, posY+645, 487, 15, 7);
        canvas.stroke();
        canvas.restoreState();
        
     	// Contenido de nombre
        Phrase valueNombre= new Phrase( aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "APELLIDO"),  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valueNombre, posX+110, posY+649, 0);
        
     	// Texto de nombre
        Phrase textoNombre= new Phrase( "NOMBRE",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoNombre, posX+325, posY+637, 0);
    	
    	// Rectangulo carrera
        canvas.saveState();        
        canvas.setColorStroke(new BaseColor(0,0,0));        
        canvas.setLineWidth(0.5f);
        canvas.roundRectangle(posX+25, posY+608, 562, 15, 7);
        canvas.stroke();
        canvas.restoreState();
        
     	// Contenido de carrera
        Phrase valueCarrera = new Phrase( carrera.getNombreCarrera(),  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, valueCarrera, posX+35, posY+613, 0);        
        
     	// Texto de carrera
        Phrase textoCarrera = new Phrase( "CARRERA",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoCarrera, posX+275, posY+599, 0);    	
           	
        /*Phrase montos = new Phrase( "DETALLE DE MONTOS Y DEDUCCIONES",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, montos, posX+25, posY+448, 0);*/
    	
    	Phrase beca = new Phrase( "DETALLE DEL DESCUENTO",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, beca, posX+25, posY+570, 0);
    	
    	// Insertar linea
    	canvas.setLineWidth(2.0f);	 // Make a bit thicker than 1.0 default 
        canvas.setGrayStroke(0.5f); // 0 = black, 1 = white         
        canvas.moveTo(posX+25, posY+560);
        canvas.lineTo(posX+25 + document.getPageSize().getWidth()-50, posY+560); 
        canvas.stroke();      
    	
        
        PdfPCell cell = null;
        
        // TOP TABLE (Uso temporal para poder crear espacio)
        float anchoColumnas[] = {100f};
		PdfPTable tableTop = new PdfPTable(anchoColumnas);		
		tableTop.setTotalWidth(document.getPageSize().getWidth()-50);		
		tableTop.setSpacingAfter(10f);
		
		// Renglón en blanco
		cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));		
		cell.setBorder(0);
		tableTop.addCell(cell);
				
		document.add(tableTop);
	
		int cont = 1;
		float suma = 0;
		String cargaId	 = (String) session.getAttribute("cargaId");
		String descuentoId	= descuentoU.getDescuentoId(conEnoc, codigoAlumno, cargaId);
 		descuento.setCodigoPersonal(codigoAlumno);
		descuento.setCargaId(cargaId);
		descuento.setDescuentoId(descuentoId);
		descuento = descuentoU.mapeaRegId(conEnoc, codigoAlumno, cargaId, descuentoId);
		String internado = descuento.getInternado();
		String tipoInternado = descuento.getTipoInternado();
		if(tipoInternado.equals("P"))tipoInternado = "Porcentaje"; else tipoInternado ="Cantidad";
		String ensenanza = descuento.getEnsenanza();
		String tipoEnsenanza = descuento.getTipoEnsenanza();
		if(tipoEnsenanza.equals("P"))tipoEnsenanza = "Porcentaje"; else tipoEnsenanza ="Cantidad";
		String matriculaImporte = descuento.getMatricula();
		String tipoMatricula = descuento.getTipoMatricula();
		if(tipoMatricula.equals("P"))tipoMatricula = "Porcentaje"; else tipoMatricula ="Cantidad";
		String total = descuento.getTotal();
		
		
		// TABLA DE ACUERDOS
		float headerwidths[]= {10f,35f,45f,15f,15f};
		PdfPTable tableAcuerdo = new PdfPTable(headerwidths);
		//tableAcuerdo.setWidths(headerwidths);
		tableAcuerdo.getDefaultCell().setBorder(1);
		tableAcuerdo.setTotalWidth(document.getPageSize().getWidth()-40);
		tableAcuerdo.setSpacingBefore(170);
		
		//PRIMERA FILA**********************
		// Primer columna 10% 
		cell = new PdfPCell(new Phrase("#", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		// Segunda columna 45%
		cell = new PdfPCell(new Phrase("DESCUENTO", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		//Tercer columna 15%
        cell = new PdfPCell(new Phrase("TIPO DE DESCUENTO", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Cuarta columna 15%
        cell = new PdfPCell(new Phrase("IMPORTE", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Quinta columna 15%
        cell = new PdfPCell(new Phrase("Total", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);	
		
		cell = new PdfPCell(new Phrase("1", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		// Segunda columna 45%
		cell = new PdfPCell(new Phrase("Matricula", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		//Tercer columna 15%
        cell = new PdfPCell(new Phrase(tipoMatricula, FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Cuarta columna 15%
        cell = new PdfPCell(new Phrase(getformato.format(Float.valueOf(matriculaImporte)), FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Quinta columna 15%
        cell = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);	
		
		
		cell = new PdfPCell(new Phrase("2", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		// Segunda columna 45%
		cell = new PdfPCell(new Phrase("Enseñanza", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		//Tercer columna 15%
        cell = new PdfPCell(new Phrase(tipoEnsenanza, FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Cuarta columna 15%
        cell = new PdfPCell(new Phrase(getformato.format(Float.valueOf(ensenanza)), FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Quinta columna 15%
        cell = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		
		
		cell = new PdfPCell(new Phrase("3", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		// Segunda columna 45%
		cell = new PdfPCell(new Phrase("Internado", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
		cell.setBorder(1);		
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		//Tercer columna 15%
        cell = new PdfPCell(new Phrase(tipoInternado, FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Cuarta columna 15%
        cell = new PdfPCell(new Phrase(getformato.format(Float.valueOf(internado)), FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);		
		tableAcuerdo.addCell(cell);
		
		//Quinta columna 15%
        cell = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		
// *********** FIN ACUERDOS **********************************/


		//FILA DE TOTAL*****************
		cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size0, Font.BOLD, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(4);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);
		
		cell = new PdfPCell(new Phrase("$"+getformato.format(Integer.parseInt(total)), FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(1);
		cell.setBorder(Rectangle.BOX);
		tableAcuerdo.addCell(cell);	
		
		
		document.add(tableAcuerdo);
		
        Phrase terminos = new Phrase( "TÉRMINOS Y CONDICIONES",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, terminos, posX+25, posY+350, 0);
    	
    	
    	
    	// Insertar linea
    	canvas.setLineWidth(2.0f);	 // Make a bit thicker than 1.0 default 
        canvas.setGrayStroke(0.5f); // 0 = black, 1 = white         
        canvas.moveTo(posX+25, posY+344);
        canvas.lineTo(posX+25 + document.getPageSize().getWidth()-50, posY+344); 
        canvas.stroke();
   /*     
		// TABLA DE TERMINOS Y CONDICIONES
		//float headerwidths2[] = {10f,45f,15f,15f,15f};
		PdfPTable tableTerminos = new PdfPTable(1);
		tableTerminos.getDefaultCell().setBorder(0);
		tableTerminos.setTotalWidth(document.getPageSize().getWidth()-40);
		tableTerminos.setSpacingBefore(55);
		
		
        cell = new PdfPCell(new Phrase("El estudiante se compromete"+
        		" a cumplir su servicio becario de acuerdo al horario que maneja el departamento al cual se le ha asignado.", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
		cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
		
        cell = new PdfPCell(new Phrase("El estudiante tiene que cumplir"+
        		" con el reglamento de la institución y las reglas internas del departamento asignado.", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
		cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
        cell = new PdfPCell(new Phrase("El departamento se compromete a registrar mensualmente las horas cumplidas con la finalidad"+
        		" que sea acreditado el monto correspondiente de dicho mes.", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
		cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
        cell = new PdfPCell(new Phrase("El departamento registrará las horas cumplidas por el estudiante hasta el tope"+
        		" máximo. Sin embargo, en el caso de becas adicionales con base a promesa de pago,"+
        		" las horas se aplicarán de acuerdo al cumplimiento de la promesa.", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
		cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
        cell = new PdfPCell(new Phrase("Montemorelos, Nuevo León, México   "+ Fecha.getFechaPDF("2"), FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		tableTerminos.addCell(cell);
		
		document.add(tableTerminos);
		
		*/    	
        
    	// Insertar linea
    	canvas.setLineWidth(1.0f);	 // Make a bit thicker than 1.0 default 
        canvas.setGrayStroke(0f); // 0 = black, 1 = white         
        canvas.moveTo(posX+180, posY+74);
        canvas.lineTo(posX+180 + document.getPageSize().getWidth()-370, posY+74); 
        canvas.stroke();
        
     	// Texto firma comisión de becas
     	Phrase textoFirmaComision = new Phrase( "Firma del que Autoriza",  FontFactory.getFont(FontFactory.TIMES_ROMAN, size2, Font.NORMAL, new BaseColor(0,0,0)) ); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, textoFirmaComision , posX+250, posY+63, 0);
		
			
	}catch(IOException ioe){
		System.err.println("Error certificado en PDF: "+ioe.getMessage());
	}	
	document.close();
	
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = codigoAlumno+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>
 <%@ include file= "../../cierra_enoc.jsp" %>
 