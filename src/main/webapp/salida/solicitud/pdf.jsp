<%@page import = "java.awt.Color" %>
<%@page import = "java.io.FileOutputStream" %>
<%@page import = "java.io.IOException" %>
<%@page import = "com.itextpdf.text.*" %>
<%@page import = "com.itextpdf.text.pdf.*" %>
<%@page import = "com.itextpdf.text.pdf.events.*" %>
<%@page import = "java.util.List"%>
<%@page import = "java.util.HashMap"%>
<%@page import = "aca.salida.spring.SalSolicitud"%>
<%@page import = "aca.salida.spring.SalGrupo"%>
<%@page import = "aca.salida.spring.SalAlumno"%>
<%@page import = "aca.salida.spring.SalConsejero"%>
<%@page import = "aca.salida.spring.SalInvitado"%>
<%@page import = "aca.salida.spring.SalAuto"%>
<%@page import = "aca.salida.spring.SalBitacora"%>
<%@page import = "aca.alumno.spring.AlumAcademico"%>
<%@page import = "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String institucion 			= (String)session.getAttribute("institucion");
	String salidaId 			= request.getParameter("salida")==null?"0":request.getParameter("salida"); 
	SalSolicitud salSolicitud	= (SalSolicitud) request.getAttribute("salSolicitud");
	SalGrupo salGrupo			= (SalGrupo) request.getAttribute("salGrupo");
	String usuario				= salSolicitud.getUsuario();
	String usuarioResponsable	= (String) request.getAttribute("usuarioResponsable");
	String usuarioLlenado		= (String) request.getAttribute("usuarioLlenado");
	String usuarioPreautorizado	= (String) request.getAttribute("usuarioPreautorizado");
	String usuarioAutorizado	= (String) request.getAttribute("usuarioAutorizado");
	String paisNombre			= (String) request.getAttribute("paisNombre");
	String estadoNombre			= (String) request.getAttribute("estadoNombre");
	String proposito			= (String) request.getAttribute("proposito");
	
	List<SalConsejero> lisConsejeros				= (List<SalConsejero>) request.getAttribute("lisConsejeros");			
	List<SalAlumno> lisAlumnos						= (List<SalAlumno>) request.getAttribute("lisAlumnos");			
	List<SalInvitado> lisInvitados 					= (List<SalInvitado>) request.getAttribute("lisInvitados");	
	List<SalAuto> lisAutos							=  (List<SalAuto>) request.getAttribute("lisAutos");
	int acompañantes 			= lisConsejeros.size();
	int alumnos 				= lisAlumnos.size();
	
	HashMap<String,String> mapaResidencias 			= (HashMap<String,String>)request.getAttribute("mapaResidencias");
	HashMap<String,String> mapaBitacora 			= (HashMap<String,String>)request.getAttribute("mapaBitacora");
	HashMap<String,String> mapaAlumnos	 			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,AlumAcademico> mapaAcademicos	= (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademicos");
	HashMap<String,String> mapaCarrerasAlumnos		= (HashMap<String,String>)request.getAttribute("mapaCarrerasAlumnos");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaComAutorizacion		= (HashMap<String,String>)request.getAttribute("mapaComAutorizacion");
	
	
	String fechaSolicitud = "";
	if (mapaBitacora.containsKey(salidaId+"1")) fechaSolicitud = mapaBitacora.get(salidaId+"1");
	
	String fechaLlenado = "";
	if (mapaBitacora.containsKey(salidaId+"1")) fechaSolicitud = mapaBitacora.get(salidaId+"1");
	
	String fechaPreautorizado = "";
	if (mapaBitacora.containsKey(salidaId+"3")) fechaPreautorizado = mapaBitacora.get(salidaId+"3");
	
	String fechaAutorizado = "";
	if (mapaBitacora.containsKey(salidaId+"4")) fechaAutorizado = mapaBitacora.get(salidaId+"4");
	
	int externos = 0;
	if (mapaResidencias.containsKey("E")){
		externos = Integer.parseInt(mapaResidencias.get("E"));
	}
	int internos = 0;
	if (mapaResidencias.containsKey("I")){
		internos = Integer.parseInt(mapaResidencias.get("I"));
	}	
//------PDF----->
		
	int posX 	= 0; 
	int posY	= 0;
	//int salto 	= 20;
	
	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.LETTER ); 
	document.setMargins(-30,-30,50,30);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/salida/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/salida/")+"/"+usuario+".pdf";	
	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Sistema Escolar");
        document.addSubject("Recibo de "+codigoAlumno);        
        
        document.open();//Abrir documento
        
        PdfContentByte canvas = pdf.getDirectContentUnder(); //pdf por posiciones
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
    //Logo Um y texto debajo
    
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_small.jpg");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(90, 90);
        jpg.setAbsolutePosition(70, 660);
        document.add(jpg);
       
    //Encabezado	
		
        Phrase uni = new Phrase( institucion.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+360, posY+730, 0);
    	
    	Phrase planCurso = new Phrase( "VICERRECTORÍA ESTUDIANTIL", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, planCurso, posX+360, posY+710, 0);

    	Phrase curso = new Phrase( "DEPTO. DE ACTIVIDADES COMPLEMENTARIAS", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, curso, posX+360, posY+690, 0);

    	Phrase plan = new Phrase( "Solicitud de Salidas", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, plan, posX+360, posY+670, 0);
    	  	
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
			
		document.add(tablaInvisible);	
		
	//Primera Tabla
		
		PdfPTable tabla1 = new PdfPTable(2);
		int tabla1Widths[] = {55,45};
		tabla1.setWidths(tabla1Widths);
		tabla1.setSpacingBefore(100f);
		tabla1.setWidthPercentage(80f);    	
	   	
		Paragraph parrafo = new Paragraph();
		parrafo.add(new Phrase("Grupo: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo.add(new Phrase(salGrupo.getGrupoNombre()
				, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		String fechaSplit[] = salSolicitud.getFecha().split("/");
		
		Paragraph parraf1 = new Paragraph();
		parraf1.add(new Phrase("Llenado por: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parraf1.add(new Phrase( usuarioLlenado, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		parraf1.add(new Phrase("\nFecha: "+fechaSplit[2]+"/"+fechaSplit[1]+"/"+fechaSplit[0], FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parraf1);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla1.addCell(celda);	
		
		Paragraph parrafo3 = new Paragraph();
		parrafo3.add(new Phrase("Propósito de la salida: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo3.add(new Phrase(proposito
				, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo3);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tabla1.addCell(celda);
		
		Paragraph parraf3 = new Paragraph();
		parraf3.add(new Phrase("Preautorizado por: "+usuarioPreautorizado
			, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		parraf3.add(new Phrase("\nFecha: "+fechaPreautorizado, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parraf3);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla1.addCell(celda);	
		
		Paragraph parrafo2 = new Paragraph();
		parrafo2.add(new Phrase("Teléfono de contacto: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo2.add(new Phrase(salSolicitud.getTelefono(), FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo2);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		Paragraph parraf2 = new Paragraph();
		parraf2.add(new Phrase("Autorizado por: "+usuarioAutorizado
			, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		parraf2.add(new Phrase("\nFecha: "+fechaAutorizado, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda = new PdfPCell(parraf2);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla1.addCell(celda);
		
		//System.out.println("Folio:"+salSolicitud.getFolio());		
		if(!salSolicitud.getFolio().equals("0")){
			celda = new PdfPCell(new Phrase(""
					, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(0);
			tabla1.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Autorización No.: "+salSolicitud.getFolio(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(0);
			tabla1.addCell(celda);
		}
		
		document.add(tabla1);
		
	//Segunda Tabla (Salida y Regreso)
		
		PdfPTable tabla2 = new PdfPTable(7);
		int tabla2Widths[] = {10,10,10,10,10,5,45};
		tabla2.setWidths(tabla2Widths);
		tabla2.setSpacingBefore(10f);
		tabla2.setWidthPercentage(80f);    	
	   	
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Hora "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Dia "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Mes "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Firmas/\nVigilancia "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("" //Para uso Exclusivo de la Vicerrectoría Estudiantil 
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		//dividr fecha de salida		
		String [] salida 	= salSolicitud.getFechaSalida().split("/");
		String diaSalida 	= salida[0];
		String mesSalida 	= salida[1];
		String horaSalida 	= salSolicitud.getFechaSalida().split(" ")[1];		
		
		celda = new PdfPCell(new Phrase("Salida "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(horaSalida
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(diaSalida
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(mesSalida
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
	/*if(!salSolicitud.getFolio().equals("0")){		
		celda = new PdfPCell(new Phrase("Autorización No.: "+salSolicitud.getFolio()
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		} else if (salSolicitud.getFolio().equals("0")){ 
			celda = new PdfPCell(new Phrase("No autorizada"
					, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(0);
			tabla2.addCell(celda);	
		}*/
	
		celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
	
		String [] regreso 	= salSolicitud.getFechaLlegada().split("/");//dividr fecha de regreso
		String diaRegreso 	= regreso[0];
		String mesRegreso 	= regreso[1];
		String horaRegreso 	= salSolicitud.getFechaLlegada().split(" ")[1];
		
		celda = new PdfPCell(new Phrase("Regreso "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla2.addCell(celda);
	
		celda = new PdfPCell(new Phrase(horaRegreso
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(diaRegreso
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(mesRegreso
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		/*
		celda = new PdfPCell(new Phrase("Fecha en que se autorizó la salida:___________________"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(6);
		tabla2.addCell(celda);
		*/
	
		
		Paragraph parrafo16 = new Paragraph();
		/*parrafo16.add(new Phrase("TIPOS DE SOLICITUDES: \n SALIDAS DE UN DÍA (deberá ser enviada con 7 días de anticipación) \n"+
				" SALIDAS QUE IMPLIQUEN COMIDA DEL COMEDOR O PERNOTAR FUERA DE LA UNIVERSIDAD (deberá ser enviada con 14 días anticipación)"
				, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo16);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(7);*/
		tabla2.addCell(celda);
		
		document.add(tabla2);
		
		
	//Tercera Tabla (Numero de alumnos y acompañantes)
	
		PdfPTable tabla3 = new PdfPTable(5);
		int tabla3Widths[] = {10,10,10,10,10};
		tabla3.setWidths(tabla3Widths);
		tabla3.setSpacingBefore(10f);
		tabla3.setWidthPercentage(80f);
		
		celda = new PdfPCell(new Phrase("Punto de Reunión"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(5);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(salSolicitud.getLugarSalida(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);		
		//celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(5);		
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(5);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Comentario"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(5);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(salSolicitud.getComentario(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);		
		//celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(5);		
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(5);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Número de integrantes"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(5);
		tabla3.addCell(celda);
	   	
		celda = new PdfPCell(new Phrase("Internos "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Externos "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Tot. Alumnos "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Empleados:  "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Total "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla3.addCell(celda);
	
		celda = new PdfPCell(new Phrase(""+internos
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+externos
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+alumnos
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+acompañantes
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+(alumnos+acompañantes)
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Maestros\\Empleados de tiempo completo que acompañan al grupo (uno por cada 10 alumnos)\n"
				+"(Campamentos, uno por cada 15 alumnos) "
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		tabla3.addCell(celda);
		document.add(tabla3);
		
	//Cuarta Tabla (Responsable y Lugar)
		
		PdfPTable tabla4 = new PdfPTable(5);
		int tabla4Widths[] = {10,10,10,20,10};
		tabla4.setWidths(tabla4Widths);
		tabla4.setSpacingBefore(10f);
		tabla4.setWidthPercentage(80f);  
		
		celda = new PdfPCell(new Phrase("Información del viaje"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(5);
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("País "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Estado "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4.addCell(celda);
	   	
		celda = new PdfPCell(new Phrase("Lugar "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Responsable "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Firma "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(paisNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(estadoNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4.addCell(celda);

		celda = new PdfPCell(new Phrase(""+salSolicitud.getLugar()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		/*
		String auto = salSolicitud.getAuto();
		if(auto.equals("C"))auto="Comercial";
		if(auto.equals("U"))auto="Universitario";
		if(auto.equals("P"))auto="Particular";
		if(auto.equals("O"))auto="Otro";
		*/		
		
		celda = new PdfPCell(new Phrase(usuarioResponsable, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("(Cualquier tipo de transporte debe tener su seguro vigente con cobertura amplia) "
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		tabla4.addCell(celda);
		
		document.add(tabla4);
		
		
	//Tabla 4.5 Comidas requeridas por SAUM
	
		PdfPTable tabla4_5 = new PdfPTable(3);
		int tabla4_5Widths[] = {20,20,20};
		tabla4_5.setWidths(tabla4_5Widths);
		tabla4_5.setSpacingBefore(10f);
		tabla4_5.setWidthPercentage(80f);
		
		celda = new PdfPCell(new Phrase("Comidas requeridas del SAUM"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(3);
		tabla4_5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Desayuno "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4_5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Comida "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4_5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Cena "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla4_5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+salSolicitud.getDesayuno()
		, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4_5.addCell(celda);

		celda = new PdfPCell(new Phrase(""+salSolicitud.getComida()
		, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4_5.addCell(celda);

		celda = new PdfPCell(new Phrase(""+salSolicitud.getCena()
		, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla4_5.addCell(celda);
		
		document.add(tabla4_5);
		
		
	//Quinta Tabla (Costos y comidas)
	
		
		PdfPTable tabla5 = new PdfPTable(5);
		int tabla5Widths[] = {7,7,7,7,7};
		tabla5.setWidths(tabla5Widths);
		tabla5.setSpacingBefore(10f);
		tabla5.setWidthPercentage(80f);   
		
		
		celda = new PdfPCell(new Phrase("Costos del viaje"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorderWidth(0);
		celda.setColspan(7);
		tabla5.addCell(celda);
	   	
		celda = new PdfPCell(new Phrase("Transporte "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Hospedaje "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Alimentos "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Costo Total "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Costo por Persona "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);
		tabla5.addCell(celda);
	
		celda = new PdfPCell(new Phrase(""+salSolicitud.getTransporte()
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+salSolicitud.getHospedaje()
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+salSolicitud.getAlimento()
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""+salSolicitud.getTotal()
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla5.addCell(celda);

		celda = new PdfPCell(new Phrase(""+salSolicitud.getTotalPersona()
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla5.addCell(celda);
		
	/*
		
		int tabla5Widths[] = {25,25,25,25};
		tabla5.setWidths(tabla5Widths);
		tabla5.setSpacingBefore(10f);
		tabla5.setWidthPercentage(80f); 
		
		celda = new PdfPCell(new Phrase("(Cualquier tipo de transporte debe tener su seguro vigente con cobertura amplia) "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tabla5.addCell(celda);
		
		Paragraph parrafo11 = new Paragraph();
		parrafo11.add(new Phrase("Costo Transporte: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo11.add(new Phrase(salSolicitud.getTransporte()
				, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo11);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla5.addCell(celda);
		
		Paragraph parrafo12 = new Paragraph();
		parrafo12.add(new Phrase("Costo Hospedaje: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo12.add(new Phrase(salSolicitud.getHospedaje()
				, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo12);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Alimentos (cantidad) solicitados: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		Paragraph parrafo13 = new Paragraph();
		parrafo13.add(new Phrase("Desayuno: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo13.add(new Phrase(salSolicitud.getDesayuno()
				, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo13);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		Paragraph parrafo14 = new Paragraph();
		parrafo14.add(new Phrase("Comida: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo14.add(new Phrase(salSolicitud.getComida()
				, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo14);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla5.addCell(celda);
		
		Paragraph parrafo15 = new Paragraph();
		parrafo15.add(new Phrase("Cena: "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		parrafo15.add(new Phrase(salSolicitud.getCena()
				, FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo15);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);
		tabla5.addCell(celda);
		*/
		document.add(tabla5);
		
		// TABLA DE AUTOS
		
		PdfPTable tablaAuto = new PdfPTable(4);
		int tablaAutoWidths[] = {10,30,30,30};
		tablaAuto.setWidths(tablaAutoWidths);
		tablaAuto.setSpacingBefore(10f);
		tablaAuto.setWidthPercentage(80f); 
		tablaAuto.setKeepTogether(true);		
		
		celda = new PdfPCell(new Phrase("Listado de Autos "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tablaAuto.addCell(celda);
		
		// Encabezados
		
		celda = new PdfPCell(new Phrase("# "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaAuto.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TIPO "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaAuto.addCell(celda);		
	
		celda = new PdfPCell(new Phrase("POLIZA "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaAuto.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TELEFONO "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tablaAuto.addCell(celda);		
		
		int row = 0;
		for(SalAuto auto : lisAutos){
			row++;
			String tipoNombre = "-";
			if (auto.getTipo().equals("C")) tipoNombre = "Comercial";
			if (auto.getTipo().equals("P")) tipoNombre = "Particular";
			if (auto.getTipo().equals("U")) tipoNombre = "Universitario";
			
			celda = new PdfPCell(new Phrase(String.valueOf(row), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);			
			tablaAuto.addCell(celda);
			
			celda = new PdfPCell(new Phrase(tipoNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);			
			tablaAuto.addCell(celda);		
		
			celda = new PdfPCell(new Phrase(auto.getPoliza(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);			
			tablaAuto.addCell(celda);
			
			celda = new PdfPCell(new Phrase(auto.getTelefono(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);			
			tablaAuto.addCell(celda);			
		} 
		
		document.add(tablaAuto);
		
		
	//Sexta Tabla (Tabla de Consejeros)
		
		PdfPTable tabla6 = new PdfPTable(5);
		int tabla6Widths[] = {5,8,27,35,25};
		tabla6.setWidths(tabla6Widths);
		tabla6.setSpacingBefore(10f);
		tabla6.setWidthPercentage(80f); 
		tabla6.setKeepTogether(true);
		

		celda = new PdfPCell(new Phrase("Listado de Empleados "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		tabla6.addCell(celda);
		
		/*celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tabla6.addCell(celda);*/
		
		celda = new PdfPCell(new Phrase("# "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla6.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("NÓMINA "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla6.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRES "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla6.addCell(celda);		
	
		celda = new PdfPCell(new Phrase("LUGAR DE TRABAJO "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla6.addCell(celda);
		
		celda = new PdfPCell(new Phrase("FIRMO COMO ACOMPAÑANTE Y APOYO AL RESPONSABLE "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla6.addCell(celda);
		
		//---->> Lista de Consejeros
		
		for(int i=0; i<lisConsejeros.size(); i++){
		SalConsejero consejero = (SalConsejero) lisConsejeros.get(i);
		
		celda = new PdfPCell(new Phrase(String.valueOf(i+1), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla6.addCell(celda);
		
		celda = new PdfPCell(new Phrase(consejero.getClave()
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setLeading(1f,1f);
		celda.setColspan(0);
		tabla6.addCell(celda);
		
		celda = new PdfPCell(new Phrase(consejero.getConsejero()
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setLeading(1f,1f);
		celda.setColspan(0);
		tabla6.addCell(celda);
				
		celda = new PdfPCell(new Phrase(consejero.getTrabajo()
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setLeading(1f,1f);
		celda.setColspan(0);
		tabla6.addCell(celda);		
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setLeading(1f,1f);
		celda.setColspan(0);
		tabla6.addCell(celda);
		
		}

		document.add(tabla6);
		
	//Septima Tabla (Tabla de Alumnos)
		
		PdfPTable tabla7 = new PdfPTable(7);
		int tabla7Widths[] = {5,8,35,30,12,5,5};
		tabla7.setWidths(tabla7Widths);
		tabla7.setSpacingBefore(10f);
		tabla7.setWidthPercentage(80f);
		tabla7.setKeepTogether(true);
		
		celda = new PdfPCell(new Phrase("Listado de Alumnos "
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(7);
		tabla7.addCell(celda);		
		
		celda = new PdfPCell(new Phrase("#", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("MAT.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRES", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CARRERA", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("RES.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("DOR.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("COM.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		for(int i=0; i<lisAlumnos.size(); i++){
		SalAlumno Alum = (SalAlumno) lisAlumnos.get(i);
		
		String comidas = "";
		String des = "";
		String com = "";
		String cen = "";
		
		if(mapaComAutorizacion.containsKey(Alum.getCodigoPersonal())){
			comidas = mapaComAutorizacion.get(Alum.getCodigoPersonal());
			
			des = comidas.substring(0, 1).equals("1") ? "S" : "-";
			com = comidas.substring(1, 2).equals("1") ? "S" : "-";
			cen = comidas.substring(2, 3).equals("1") ? "S" : "-";
			
			comidas = des+com+cen;
		}
		
		celda = new PdfPCell(new Phrase(String.valueOf(i+1), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(Alum.getCodigoPersonal()
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(Alum.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(Alum.getCodigoPersonal());
		}
		
		String residencia 	= "-";
		String dormitorio	= "0";
		if (mapaAcademicos.containsKey(Alum.getCodigoPersonal())){
			residencia = mapaAcademicos.get(Alum.getCodigoPersonal()).getResidenciaId();
			dormitorio = mapaAcademicos.get(Alum.getCodigoPersonal()).getDormitorio();
		}
		
		celda = new PdfPCell(new Phrase(alumnoNombre, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		String carreraId		= "0";
		String nombreCarrera 	= "-";
		if (mapaCarrerasAlumnos.containsKey(Alum.getCodigoPersonal())){
			carreraId = mapaCarrerasAlumnos.get(Alum.getCodigoPersonal());
			if (mapaCarreras.containsKey(carreraId)){
				nombreCarrera = mapaCarreras.get(carreraId).getNombreCarrera();
			}
		}		
		
		if (nombreCarrera.length()>40) nombreCarrera = nombreCarrera.substring(0, 40)+"...";
		celda = new PdfPCell(new Phrase( nombreCarrera, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(residencia.equals("E")?"Externo":"Interno"
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);		

		
		celda = new PdfPCell(new Phrase(dormitorio.equals("X")?"-":dormitorio
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);

		celda = new PdfPCell(new Phrase(comidas.equals("")?"-":comidas
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla7.addCell(celda);		
		
		}
		
		document.add(tabla7);		
		
		
		//Octava Tabla (Tabla de Invitados)
		
		PdfPTable tabla8 = new PdfPTable(3);
		int tabla8Widths[] = {10,70,20};
		tabla8.setWidths(tabla8Widths);
		tabla8.setSpacingBefore(10f);
		tabla8.setWidthPercentage(80f);
		tabla8.setKeepTogether(true);
		
		celda = new PdfPCell(new Phrase("Listado de Invitados", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(3);
		tabla8.addCell(celda);	
		
		celda = new PdfPCell(new Phrase("FOLIO", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla8.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRE", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla8.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TIPO", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorderWidth(0.5f);
		celda.setColspan(0);
		tabla8.addCell(celda);
		
		for(int i=0; i<lisInvitados.size(); i++){
			SalInvitado invitado = (SalInvitado) lisInvitados.get(i);
			
			celda = new PdfPCell(new Phrase(String.valueOf(i), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tabla8.addCell(celda);
			
			celda = new PdfPCell(new Phrase(invitado.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tabla8.addCell(celda);			
			
			celda = new PdfPCell(new Phrase( invitado.getTipo().equals("C")?"Consejero":"Invitado", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setBorderWidth(0.5f);
			celda.setColspan(0);
			tabla8.addCell(celda);	
			
		}
		document.add(tabla8);
		
		
	

	}catch(IOException ioe){
		System.err.println("Error al generar el recibo en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
	
	String nombreArchivo = usuario+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>