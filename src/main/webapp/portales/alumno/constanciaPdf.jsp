<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.util.Fecha"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.awt.Color" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="com.itextpdf.text.*" %>
<%@ page import="com.itextpdf.text.pdf.*" %>
<%@ page import="com.itextpdf.text.pdf.events.*" %>

<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.financiero.spring.FesCcobro"%>
<%@ page import="aca.plan.spring.MapaPlan" %>
<%@ page import="aca.carga.spring.Carga" %>
<%@ page import="aca.carga.spring.CargaPracticaAlumno"%>

 <!-- page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" -->

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	
	java.text.DecimalFormat getformato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cargaId				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String bloqueId				= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	AlumPersonal alumPersonal	= (AlumPersonal)request.getAttribute("alumPersonal");
	FesCcobro cobro				= (FesCcobro)request.getAttribute("cobro");	
	String planNombre			= (String)request.getAttribute("planNombre");
	
	List<CargaPracticaAlumno> lisPracticas		= (List<CargaPracticaAlumno>) request.getAttribute("lisPracticas");
	HashMap<String, String> mapaPracticas		= (HashMap<String, String>)  request.getAttribute("mapaPracticas");
	HashMap<String, String> mapaLibres			= (HashMap<String, String>)  request.getAttribute("mapaLibres");
	
	Fecha fecha					= new Fecha();
	String folio 				= cobro.getFolio()==null?"XX":cobro.getFolio();
	String dia 					= fecha.getDia(aca.util.Fecha.getHoy());
	String mes 					= fecha.getMesNombre(aca.util.Fecha.getHoy());
	String year					= aca.util.NumberToLetter.convertirYear(Integer.valueOf(fecha.getYear(aca.util.Fecha.getHoy())));
	String fraseDia				= Integer.parseInt(dia)>=2?"a los "+dia+" días":"al primer día";	
	String rutaCarpeta 			= application.getRealPath("/WEB-INF/pdf/inscripcion/");
	if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
	
	String institucion 			= (String) session.getAttribute("institucion");
	String codigoAlumno	 		= (String) session.getAttribute("codigoAlumno");
	String carpeta				= application.getRealPath("/WEB-INF/pdf/inscripcion/");
	String dir 					= carpeta+codigoAlumno+cargaId+bloqueId+".pdf";	
	
	Carga carga					= (Carga) request.getAttribute("carga");
	
	String fechaInicia 			= carga.getFInicio();
	
	String diaInicia					= fecha.getDia(fechaInicia);
	String mesInicia 					= fecha.getMesNombre(fechaInicia);	
	String yearInicia 					= fecha.getYear(fechaInicia);

	String fechaFin 		= carga.getFFinal();

	String diaFin					= fecha.getDia(fechaFin);
	String mesFin 					= fecha.getMesNombre(fechaFin);	
	String yearFin 					= fecha.getYear(fechaFin);
	
	int posX 	= 0; 
	int posY	= 0;	
	
	PdfPCell celda 	= null;	
	float size0		= 6f;
	float size1		= 7f;
	float size2		= 9;
	float size3		= 11f;
	
	boolean esLibre 	= false;		
	if (mapaLibres.containsKey(cargaId+bloqueId)){
		esLibre = true;
	}
	
	boolean esPractica	= false;
	if (mapaPracticas.containsKey(cargaId+bloqueId)){
		esPractica = true;
	}
	
	String fechasPracticas = "";
	for (CargaPracticaAlumno practica : lisPracticas){
		if (cargaId.equals(practica.getCargaId()) && bloqueId.equals(practica.getBloqueId())){
			if (fechasPracticas.length()==0 ) 
				fechasPracticas += aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" al "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());
			else
				fechasPracticas += ", "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" al "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());	
		}
	}
	
	int paginaAnterior		= 1;
	boolean printEncabezado = false;
	
	//Creación de la fuente
	BaseFont base = BaseFont.createFont("../../fonts/adventsanslogo.ttf", BaseFont.WINANSI,true);
	Font fontAdv11 = new Font(base, 11f, Font.BOLD);
	
	Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
	document.setMargins(40,35,180,10);
	//document.setMargins(-40,-40,84,30);	
	
	try{
		if(!new File(carpeta).exists()) new File(carpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addTitle("Universidad de Montemorelos, A.C.");
		document.addAuthor("Sistema Académico");
        document.addSubject("Constancia de "+codigoAlumno);    
	    document.open();
	    
	    Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/tituloConstancia.png");
	    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	    jpg.scaleAbsolute(600,50);
	    jpg.setAbsolutePosition(10, posY+712);
	    //jpg.setAbsolutePosition(posX+65, posY+732);
	    document.add(jpg);    
	    
	    int r = 0, g = 0, b = 0;	   
	     
	    PdfContentByte canvas = pdf.getDirectContentUnder();
	    
//     	TEXTO SUPERIOR DERECHO	    
	    Phrase direccion = new Phrase( "DIRECCION SERVICIOS ESCOLARES",  new Font(base, size1, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, direccion, posX+400, posY+700, 0);
    	Phrase avenida = new Phrase( "Ave. Libertad No. 1300 Pte.",  new Font(base, size1, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, avenida, posX+400, posY+690, 0);
    	Phrase barrio = new Phrase( "Barrio Matamoros",  new Font(base, size1, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, barrio, posX+400, posY+680, 0);
    	Phrase montemorelos = new Phrase( "Montemorelos, N.L. México",  new Font(base, size1, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, montemorelos, posX+400, posY+670, 0);
    	Phrase telefono = new Phrase( "C.P. 67530  Tel. +52(826) 263 0900",  new Font(base, size1, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, telefono, posX+400, posY+660, 0);
	  
//     	INICIA REDACCION    	
    	Phrase constancia = new Phrase( "CONSTANCIA DSE "+folio,  new Font(base, size2, Font.BOLD)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, constancia, posX+96, posY+650, 0);
    	
    	Phrase corresponda = new Phrase( "A QUIEN CORRESPONDA",  new Font(base, size2, Font.BOLD)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, corresponda, posX+96, posY+610, 0);
		
		Phrase nombreAlumnoC = new Phrase( alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno(),  new Font(base, size2, Font.BOLD)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, nombreAlumnoC, posX+300, posY+540, 0);   	
    	
    	Phrase atte = new Phrase( "Atentamente",  new Font(base, size2, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, atte, posX+96, posY+280, 0);
    	
    	Phrase moralis = new Phrase( "MORALIS, MENTALIS, CORPORALIS",  new Font(base, size2, Font.BOLD)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, moralis, posX+96, posY+270, 0);
    	
    	Phrase ingeniero = new Phrase( "Ing. José H. Méndez Wilson",  new Font(base, size2, Font.BOLD)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, ingeniero, posX+96, posY+210, 0);
    	
    	Phrase director = new Phrase( "Director de Servicios Escolares",  new Font(base, size2, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, director, posX+96, posY+200, 0);
    	
    	Phrase resolucion = new Phrase( "Creada por el Gobierno del Estado de Nuevo León, México, mediante Resolución Oficial, publicada el 5 de Mayo de 1973.",  new Font(base, size1, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, resolucion, posX+308, posY+25, 0);
    	
    	Phrase clave = new Phrase( "Clave de institución ante SEP y Dirección General Estadística 19MSU1017U", new Font(base, size1, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, clave, posX+306, posY+17, 0);
        
        PdfPCell cell = null;       		
    
		// TABLA DE TERMINOS Y CONDICIONES
		//float headerwidths2[] = {10f,45f,15f,15f,15f};
		PdfPTable tableTexto = new PdfPTable(1);
		tableTexto.getDefaultCell().setBorder(0);
		tableTexto.setTotalWidth(document.getPageSize().getWidth()-40);
		tableTexto.setSpacingBefore(10);
		
		cell = new PdfPCell(new Phrase(2,"\nEl que suscribe, Ing. José Hermenegildo Méndez Wilson, Director de Servicios Escolares de la Universidad de Montemorelos, por este medio hace constar que:", new Font(base, size2, Font.NORMAL)));		        
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);	
		cell.setLeading(1.2f, 1.8f);
		tableTexto.addCell(cell);
		
		String textoYear = yearInicia.equals(yearFin)?"":" del "+yearInicia;
		Phrase fraseDatos = new Phrase(2,"\nCon número de registro escolar ", new Font(base, size2, Font.NORMAL));
		fraseDatos.add(new Chunk(alumPersonal.getCodigoPersonal(), new Font(base, size2, Font.BOLD)));
		fraseDatos.add(new Chunk(", es alumno inscrito en el programa de ", new Font(base, size2, Font.NORMAL)));
		fraseDatos.add(new Chunk(planNombre, new Font(base, size2, Font.BOLD)));
		fraseDatos.add(new Chunk(" durante el período académico comprendido del "+diaInicia+" de "+mesInicia+textoYear+" al "+diaFin+" de "+mesFin+" del "+yearFin
				+". Esta constancia otorga vigencia a la credencial del alumno durante este período sustituyendo temporalmente el resello físico.", new Font(base, size2, Font.NORMAL)));		
		cell = new PdfPCell(fraseDatos);        
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);
		cell.setLeading(1.2f, 1.8f);
		tableTexto.addCell(cell);
		
		// Agrega este párrafo para especificar que el alumno es requerido presencialmente
		if (esLibre ==false && esPractica == true){
			Phrase frasePracticas = new Phrase("Con el propósito de cumplir con las asignaturas que por su naturaleza práctica involucran tareas presenciales, "
					+"se ha establecido un programa de asistencia escalonado apegado a las medidas sanitarias. Por esta razón es necesaria su presencia en "
					+"la Institución durante el(los) siguiente(s) período(s): ", new Font(base, size2, Font.NORMAL));
			frasePracticas.add(new Chunk(fechasPracticas, new Font(base, size2, Font.BOLD)));
			frasePracticas.add(new Chunk(". Al presentarse el primer día el estudiante deberá acudir a Servicios Escolares con copia impresa de esta constancia y su credencial de estudiante para su resello.", new Font(base, size2, Font.NORMAL)));
		    
			cell = new PdfPCell(frasePracticas);		
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);		
			cell.setBorder(0);
			cell.setLeading(1.2f, 1.8f);
			tableTexto.addCell(cell);
		}
		
		cell = new PdfPCell(new Phrase("Para fines y usos que convengan, se extiende la presente en la ciudad de Montemorelos, Nuevo León, México "
							+fraseDia+" del mes de "+mes+" del año "+year+".",
			   new Font(base, size2, Font.NORMAL) ));		
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);		
		cell.setBorder(0);
		cell.setLeading(1.2f, 1.8f);
		tableTexto.addCell(cell);	
		
		document.add(tableTexto);
		
		Image marcaAgua = Image.getInstance(application.getRealPath("/imagenes/")+"/flama2.png");
		marcaAgua.scaleAbsolute(170,220);
		marcaAgua.setAbsolutePosition(posX+240, 0f);
		//marcaAgua.scaleAbsolute(600,300);
		//marcaAgua.setAbsolutePosition(0f, 0f);		
		marcaAgua.setAlignment(Element.ALIGN_CENTER);
		
		canvas.addImage(marcaAgua);		
		
		Image firma = Image.getInstance(application.getRealPath("/imagenes/")+"/firmaJoseMendez.png");
		firma.scaleAbsolute(200,100);		
		firma.setAbsolutePosition(posX+56, posY+170);
		
		canvas.addImage(firma);
		
	}catch(IOException ioe){
		System.err.println("Error certificado en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = codigoAlumno+cargaId+bloqueId+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%> 