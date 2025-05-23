<%@page import="org.w3c.dom.traversal.DocumentTraversal"%>
<%@page import = "java.awt.Color" %>
<%@page import = "java.io.FileOutputStream" %>
<%@page import = "java.io.IOException" %>
<%@page import = "com.itextpdf.text.*" %>
<%@page import = "com.itextpdf.text.pdf.*" %>
<%@page import = "com.itextpdf.text.pdf.events.*" %>
<%@page import="java.text.*" %>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumEstudio"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaCredito"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@page import = "java.util.List"%>
<%@page import = "java.util.HashMap"%>
<%@page import = "java.util.Date"%>
<%@page import = "java.time.LocalDate"%>
<%@page import = "java.time.format.DateTimeFormatter"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");

	String planId 			 	= (String)request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");	
	String planNombre 	     	= (String)request.getAttribute("planNombre");
	String codigoAlumno		 	= (String)session.getAttribute("codigoAlumno");
	
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumPlan alumPlan         	= (AlumPlan)request.getAttribute("alumPlan");
	AlumEstudio alumEstudio		= (AlumEstudio)request.getAttribute("alumEstudio");
	
	HashMap<String, AlumnoCurso> mapaCursosAlumno = (HashMap<String, AlumnoCurso>) request.getAttribute("mapaCursosAlumno");
	HashMap<String, String> mapaGradePoint = (HashMap<String, String>) request.getAttribute("mapaGradePoint");
	List<MapaCurso> lisCursosCarrera = (List<MapaCurso>) request.getAttribute("lisCursosCarrera");
	
	aca.util.Fecha fecha		= new aca.util.Fecha();
	
	
	String fechaHoy 			= fecha.getFecha("1");
	String fechaNacimiento		= fecha.getDia(alumPersonal.getFNacimiento()) +" "+fecha.getMesNombre(alumPersonal.getFNacimiento()).toUpperCase() +" "+fecha.getYear(alumPersonal.getFNacimiento());
	
	int sumCreditos = 0;
	int sumCreditosTotales = 0;

//------PDF----->		
	int posX 	= 0; 
	int posY	= 0;

	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.LETTER ); 
	document.setMargins(-30,-30,50,30);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/")+"/"+planId+"-"+codigoAlumno+".pdf";	
	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Fulton Adventist University");
           
        
        document.open();//Abrir documento
        
        PdfContentByte canvas = pdf.getDirectContentUnder(); //pdf por posiciones
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
    //Logo Fulton y texto debajo
    
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/fulton.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(235, 82);
        jpg.setAbsolutePosition(185,660);
        document.add(jpg);
		
 //Encabezado	
		
        Phrase transcript = new Phrase( "Transcript of Academic Record for", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, transcript, posX+305, posY+640, 0);
        		
    	Phrase direction = new Phrase("Private Mail Bag", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, direction, posX+550, posY+620, 0);
    	Phrase direction2 = new Phrase("Nadi Airport, FIJI ", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, direction2, posX+550, posY+610, 0);
    	
    	Phrase sabeto = new Phrase("Sabeto Road, NADI", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, sabeto, posX+550, posY+590, 0);
    	Phrase phone = new Phrase("Phone: (679) 9993118", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, phone, posX+550, posY+570, 0);
    	Phrase email = new Phrase("Email: info@fulton.ac.fj", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, email, posX+550, posY+560, 0);
    	
    	Phrase alumno = new Phrase(alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno() , FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, alumno, posX+305, posY+625, 0);

    	Phrase program = new Phrase("PROGRAM OF STUDY: "+planNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, program, posX+50, posY+585,0);
    	
    	Phrase entry = new Phrase("ENTRY LEVEL: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, entry, posX+50, posY+555,0);
    	Phrase entryValue = new Phrase(alumEstudio.getTitulo(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, entryValue, posX+145, posY+555,0);
    	Phrase birth = new Phrase("DATE OF BIRTH: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, birth, posX+50, posY+545,0);
       	Phrase birthValue = new Phrase(fechaNacimiento, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, birthValue, posX+145, posY+545,0);
    	Phrase Idno = new Phrase("ID NO: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, Idno, posX+50, posY+535,0);
    	Phrase IdnoValue = new Phrase(codigoAlumno, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, IdnoValue, posX+145, posY+535,0);
    	
     	//Tabla Invisible
    	PdfPTable tablaInvisible = new PdfPTable(1);
   		int tablaInvisibleWidths[] = {100};
   		tablaInvisible.setWidths(tablaInvisibleWidths);
   		tablaInvisible.setSpacingBefore(43f);   		
   		   	
    	celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setBorder(Rectangle.NO_BORDER);
    	celda.setColspan(0);
    	tablaInvisible.addCell(celda);
    	document.add(tablaInvisible);	
    	document.add(tablaInvisible);
    	document.add(tablaInvisible);
    	document.add(tablaInvisible);
    	document.add(tablaInvisible);	
     
  		
    	//Tabla de head
    	PdfPTable tabla1 = new PdfPTable(3);
		int tabla1Widths[] = {80,16,14};
		tabla1.setWidths(tabla1Widths);
		tabla1.setSpacingBefore(0f);
		tabla1.setWidthPercentage(77f);
		
		// Head
		celda = new PdfPCell(new Phrase("MODULES ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);	
		celda.setBackgroundColor(new BaseColor(255,255,255));		
		tabla1.addCell(celda);
		celda = new PdfPCell(new Phrase("GRADE ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBackgroundColor(new BaseColor(255,255,255));		
		tabla1.addCell(celda);
		celda = new PdfPCell(new Phrase(" CREDITS", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);	
		celda.setBackgroundColor(new BaseColor(255,255,255));		
		tabla1.addCell(celda);
		
		document.add(tabla1);
		
		//MAPA MATERIAS CON CALIFICACIONES DEL ALUMNO

		PdfPTable tablaB = new PdfPTable(3);
		int tablaBWidths[] = {70,15,15};
		tablaB.setWidths(tablaBWidths);
		tablaB.setSpacingBefore(5f);
		tablaB.setWidthPercentage(77f);
		
		// OBTIENE CICLO DE LA PRIMERA MATERIA EN LISTA
		String ciclo = lisCursosCarrera.get(0).getCiclo();
		
		PdfPTable tablamate = new PdfPTable(3);
		int tablamateWidths[] = {80,15,10};
		tablamate.setWidths(tablamateWidths);
		tablamate.setSpacingBefore(5f);
		tablamate.setWidthPercentage(77f);	
		
		// CREA LA FILA DEL PRIMER SEMESTRE EN LA LISTA
		celda = new PdfPCell(new Phrase("SEMESTER " + lisCursosCarrera.get(0).getCiclo(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);			
		tablamate.addCell(celda);
		celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);			
		tablamate.addCell(celda);
		celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);	
		tablamate.addCell(celda);
		
		
		//float creditosCUM = 0;
		float notasxcreditosCUM = 0;
		double GPACUM = 0;
		String CUM = "";
		for( MapaCurso materia : lisCursosCarrera){ //FOR PRINCIPAL
			
			// AGREGA UNA FILA CON EL TITULO DEL SEMESTRE
			if(!ciclo.equals(materia.getCiclo())) {
				
				celda = new PdfPCell(new Phrase("SEMESTER " + materia.getCiclo(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);			
				tablamate.addCell(celda);
				celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);			
				tablamate.addCell(celda);
				celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);			
				tablamate.addCell(celda);
				
				ciclo = materia.getCiclo();
			}
			
			// IMPRIME EL NOMBRE DE LA MATERIA
			celda = new PdfPCell(new Phrase("                    "+materia.getCursoId()+" "+materia.getNombreCurso(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);			
			tablamate.addCell(celda);	
			
			
			//OBTIENE LA NOTA DE LA MATERIA
			String calificacion = " ";
			String gradePointNombre = "";
			String gradePointValor	= "";
			
			if(mapaCursosAlumno.containsKey(materia.getCursoId())){
				calificacion = mapaCursosAlumno.get(materia.getCursoId()).getNota();
				
				if (mapaGradePoint.containsKey(calificacion)){
					gradePointNombre 	= mapaGradePoint.get(calificacion);
					gradePointValor 	= gradePointNombre.split(";")[1];
					gradePointNombre	= gradePointNombre.split(";")[0];
				}
				notasxcreditosCUM = notasxcreditosCUM + Float.parseFloat(materia.getCreditos())*Float.valueOf(gradePointValor);
				
			}
			
			//OBTIENE LOS CREDITOS ALCANZADOS	
			if(mapaCursosAlumno.containsKey(materia.getCursoId())){
				sumCreditos = sumCreditos + Integer.valueOf(materia.getCreditos());
			}
			
			//OBTIENE CREDTIOS TOTALES EN EL PLAN
			sumCreditosTotales = sumCreditosTotales + Integer.valueOf(materia.getCreditos());
			
			// IMPRIME LA NOTA DE LA MATERIA
			celda = new PdfPCell(new Phrase(gradePointNombre, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);			
			tablamate.addCell(celda);
			
			//IMPRIME LOS CREDITOS DE LA MATERIA
			celda = new PdfPCell(new Phrase(materia.getCreditos(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);			
			tablamate.addCell(celda);	
		}// FIN FOR PRINCIPAL
		
		document.add(tablamate);
		
		document.add(tablaInvisible);	
		
		// Seccion de Creitos Obtenidos y GPA
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setWidthPercentage(77f);
		int tabla2Widths[] = {100};	
		tabla2.setWidths(tabla2Widths);
		tabla1.setSpacingBefore(40);
		
		GPACUM = (double) notasxcreditosCUM / sumCreditosTotales;
		
		CUM = getFormato.format(GPACUM);
		
		
		celda = new PdfPCell(new Phrase("TOTAL  CREDITS  EARNED:  "+sumCreditos+"  out of   "+sumCreditosTotales + "  with " + CUM + "  GPA  WITH  XXXX", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla2.addCell(celda);
		
		document.add(tabla2);
		
		// Seccion de AWARD
		
		PdfPTable tabla4 = new PdfPTable(2);
		tabla4.setWidthPercentage(77f);
		int tabla4Widths[] = {10, 90};	
		tabla4.setWidths(tabla4Widths);
		tabla4.setSpacingBefore(10);
		
		celda = new PdfPCell(new Phrase("AWARD: ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(planNombre, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla4.addCell(celda);
		
		document.add(tabla4);
		
		// Seccion DATE OF ISSUE
		
		PdfPTable tabla5 = new PdfPTable(2);
		tabla5.setWidthPercentage(77f);
		int tabla5Widths[] = {15, 85};	
		tabla5.setWidths(tabla5Widths);
		tabla5.setSpacingBefore(10);
		
		celda = new PdfPCell(new Phrase("DATE OF ISSUE: "  , FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla5.addCell(celda);

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		String formattedDate = currentDate.format(formatter);
		
		celda = new PdfPCell(new Phrase(fecha.getDia(fechaHoy) +" "+fecha.getMesNombre(fechaHoy) +" "+fecha.getYear(fechaHoy), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla5.addCell(celda);

		document.add(tabla5);
		
		// Footer
		
		PdfPTable tabla3 = new PdfPTable(1);
		tabla3.setWidthPercentage(77);
		int tabla3Widths[] = {100};	
		tabla3.setWidths(tabla3Widths);
		tabla3.setSpacingBefore(10);
		
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase("Any erasures or alterations of the above will invalidate the result", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase("          An official transcript has the Fulton Adventist University", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase("          College embossed seal and signature of the Office of ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase("          the Registrar ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase("Nelli Talei Manuca, Registrar ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		celda = new PdfPCell(new Phrase("Refer to the reverse of each page for an explanation of grades and other codes", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		
		document.add(tabla3);
	
		
	}catch(IOException ioe){
		System.err.println("Error generating PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
	
	String nombreArchivo = planId+"-"+codigoAlumno+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>