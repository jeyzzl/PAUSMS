<%@page import = "org.w3c.dom.traversal.DocumentTraversal"%>
<%@page import = "java.awt.Color" %>
<%@page import = "java.io.FileOutputStream" %>
<%@page import = "java.io.IOException" %>
<%@page import = "com.itextpdf.text.*" %>
<%@page import = "com.itextpdf.text.pdf.*" %>
<%@page import = "com.itextpdf.text.pdf.events.*" %>
<%@page import = "java.text.*" %>
<%@page import = "java.io.ByteArrayOutputStream" %>
<%@page import = "java.util.List"%>
<%@page import = "java.util.HashMap"%>
<%@page import = "aca.plan.spring.MapaCurso"%>
<%@page import = "aca.vista.spring.AlumnoCurso"%>
<%@page import = "aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");

	String esAdmin 	   		= (String) session.getAttribute("esAdmin");
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
	
	AlumPersonal alumPersonal 	= (AlumPersonal)request.getAttribute("alumPersonal");
	String nombreAlum 			= (String)request.getAttribute("nombreAlum");
	String entry				= (String)request.getAttribute("institucion");
	String dateOfBirth			= (String)request.getAttribute("dob");
	String planNombre 			= (String)request.getAttribute("planNombre");
	
	List<MapaCurso> lisCursos 				= (List<MapaCurso>)request.getAttribute("lisCursos");
	List<AlumnoCurso> lisNotas 				= (List<AlumnoCurso>)request.getAttribute("lisNotas");
	HashMap<String,String> mapaGradePoint 	= (HashMap<String,String>) request.getAttribute("mapaGradePoint");
	
	String institucion		= "PACIFIC ADVENTIST UNIVERSITY";
	
//------PDF----->		
	int posX 	= 0; 
	int posY	= 0;
	final int MAX_COURSES_PER_PAGE = 25; // Adjust this value as needed

	//int salto 	= 20;
	
	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.LETTER ); 
	document.setMargins(25,-10,50,20);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/")+"/"+planId+"-"+codigoAlumno+".pdf";	
	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor(institucion);
        document.open();//Abrir documento
        
        PdfContentByte canvas = pdf.getDirectContentUnder(); //pdf por posiciones
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
    //Logo Um y texto debajo
    
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_pau.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(140, 75);
        jpg.setAbsolutePosition(25, 715);
        document.add(jpg);
		
        // Encabezado, Titulo Institucion
        Phrase uni = new Phrase( institucion.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+310, posY+770, 0);
    	
    	// Datos de contacto
    	Phrase contacto = new Phrase( "PMB Boroko NCD, Papua New Guinea", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, contacto, posX+590, posY+750, 0);
    	Phrase telefono = new Phrase( "Phone: [675] 328 0200 Fax: [675] 328 1257", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, telefono, posX+590, posY+738, 0);   	
    	
    	// Nombre del Alumno		
    	Phrase alumno = new Phrase( "COURSE PLAN FOR ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));
    	alumno.add(new Phrase((alumPersonal.getNombre()+" "+alumPersonal.getApellidoMaterno()).toUpperCase() , FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, alumno, posX+310, posY+700, 0);
    	
    	//Nombre del Curso / Plan
    	Phrase course = new Phrase("COURSE: ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)));
    	course.add(new Phrase("          "+planNombre.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, course, posX+25, posY+680, 0);
    	
    	//Nombre de Carrera
    	Phrase major = new Phrase("MAJOR: ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)));
    	major.add(new Phrase("             "+codigoAlumno, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, major, posX+25, posY+668, 0);
    	
    	// Nivel de entrada
    	Phrase entryLevel = new Phrase("ENTRY LEVEL:    ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)));
    	entryLevel.add(new Phrase(" "+entry, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, entryLevel, posX+465, posY+680, 0);
    	
    	// Fecha de nacimiento del alumno
    	Phrase dob = new Phrase("DATE OF BIRTH: ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)));
    	dob.add(new Phrase(" "+dateOfBirth, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dob, posX+465, posY+668, 0);
    	
    	
    	// Tabla Invisible - Funciona como margen para tabla principal
    	PdfPTable tablaInvisible = new PdfPTable(1);
   		int tablaInvisibleWidths[] = {100};
   		tablaInvisible.setWidths(tablaInvisibleWidths);
   		tablaInvisible.setSpacingBefore(45f);   		
   		   	
    	celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setBorder(Rectangle.NO_BORDER);
    	celda.setColspan(0);
    	tablaInvisible.addCell(celda);
    	document.add(tablaInvisible);  
    	
    	// Tabla de calificaiones
    	
    	PdfPTable tblMat = new PdfPTable(7);
		int tblMatWidths[] = {15,44,10,10,10,6,6};
		tblMat.setWidths(tblMatWidths);
		tblMat.setSpacingBefore(90f);
		tblMat.setWidthPercentage(95f);
		tblMat.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		//Encabezados
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		celda = new PdfPCell(new Phrase("GRADE", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("CREDITS REQ'D", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CREDITS EARNED", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		celda = new PdfPCell(new Phrase("GPA SEM", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		celda = new PdfPCell(new Phrase("GPA CUM", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		
		
		// Bloque de materias
		String ciclo = "0";
		int tot_creditos = 0;
		int creditos_obt = 0;
		float creditosCUM = 0;
		float notasxcreditosCUM = 0;
		float creditosSEM = 0;
		float notasxcreditosSEM = 0;
		int currentCiclo = 1;
		double GPASEM = 0;
		double GPACUM = 0;
		int coursesOnCurrentPage = 0;
		for ( MapaCurso curso : lisCursos){
			
			// Check if a new page is needed
		    if (coursesOnCurrentPage >= MAX_COURSES_PER_PAGE) {
		    	document.add(tblMat);
		        document.newPage();
		     // Tabla de calificaiones
		    	
		    	tblMat = new PdfPTable(7);
				tblMat.setWidths(tblMatWidths);
				tblMat.setSpacingBefore(90f);
				tblMat.setWidthPercentage(90f);
				tblMat.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				//Encabezados
				celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase("GRADE", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				
				celda = new PdfPCell(new Phrase("CREDITS REQ'D", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase("CREDITS EARNED", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase("GPA SEM", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase("GPA CUM", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				tblMat.addCell(celda);
		        // Reset any page-specific layout if needed
		        coursesOnCurrentPage = 0;
		       
		    }
		    coursesOnCurrentPage++;
			tot_creditos += Integer.parseInt(curso.getCreditos());
			String SEM = "";
			String CUM = "";
			
			String nota 	= "0";
			
			String letra 	= "";
			String valor	= "";
			String tipoCalId = "";
			
			if (!ciclo.equals(curso.getCiclo())){
				GPASEM = (double) notasxcreditosSEM / creditosSEM;
				GPACUM = (double) notasxcreditosCUM / creditosCUM;
				
				SEM = getFormato.format(GPASEM);
				CUM = getFormato.format(GPACUM);
				ciclo = curso.getCiclo();
				celda = new PdfPCell(new Phrase("SEMESTER "+curso.getCiclo(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				
				tblMat.addCell(celda);	
				creditos_obt += Integer.parseInt(curso.getCreditos());
				
				// Placeholder cells for SEM and CUM rows
		        int placeholderCellCount = 4; // Adjust based on the number of columns before SEM and CUM
		        for (int i = 0; i < placeholderCellCount; i++) {
		            celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r, g, b))));
		            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		            celda.setBorder(0);
		            tblMat.addCell(celda);
		        }
				
				celda = new PdfPCell(new Phrase(curso.getCiclo().equals("1")? "" : SEM, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase(curso.getCiclo().equals("1")? "" : CUM, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setColspan(7);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				GPASEM = 0;
				creditosSEM = 0;
				notasxcreditosSEM = 0;
				
				currentCiclo++;

			}		
			
			for (AlumnoCurso alumnoNota : lisNotas){
				if (curso.getCursoId().equals(alumnoNota.getCursoId())){
					nota = alumnoNota.getNota();
					if (mapaGradePoint.containsKey(nota)){						 
						letra = mapaGradePoint.get(nota).split(";")[0];
						valor = mapaGradePoint.get(nota).split(";")[1];
					}
					tipoCalId = alumnoNota.getTipoCalId();
					notasxcreditosSEM = notasxcreditosSEM + Float.parseFloat(alumnoNota.getCreditos())*Float.valueOf(valor);
					
					creditosSEM = creditosSEM + Float.parseFloat(alumnoNota.getCreditos().trim());
					notasxcreditosCUM = notasxcreditosCUM + Float.parseFloat(alumnoNota.getCreditos())*Float.valueOf(valor);
					creditosCUM = creditosCUM + Float.parseFloat(alumnoNota.getCreditos().trim());
				}
				
			}
			
			
			
			
			celda = new PdfPCell(new Phrase(" "+curso.getCursoClave(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(0);	
			tblMat.addCell(celda);
			
			celda = new PdfPCell(new Phrase(curso.getNombreCurso(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(0);	
			tblMat.addCell(celda);
			
			celda = new PdfPCell(new Phrase(letra, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(0);	
			tblMat.addCell(celda);
			
			celda = new PdfPCell(new Phrase(curso.getCreditos(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(0);	
			tblMat.addCell(celda);	
			
			celda = new PdfPCell(new Phrase(tipoCalId.equals("1") ? curso.getCreditos(): "0", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(0);	
			tblMat.addCell(celda);
			
			celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(0);	
			tblMat.addCell(celda);
			
			celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(0);	
			tblMat.addCell(celda);
			
			
		}
		
		// Empty row
		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(0);	
		celda.setColspan(4);
		tblMat.addCell(celda);

		celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(0);	
		tblMat.addCell(celda);
		
		// End of Report
		
		celda = new PdfPCell(new Phrase("End of report - no further entries", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(0);	
		celda.setColspan(3);
		tblMat.addCell(celda);
		
		document.add(tblMat);
		
		// Signature
    	Phrase sign_line = new Phrase("_____________________________________", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, sign_line, posX+460, posY+130, 0);
    	Phrase sign_text = new Phrase("Deputy Vice-Chancellor", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, sign_text, posX+460, posY+120, 0);
    	
    	//Credits and GPA
    	Phrase total_credits = new Phrase("TOTAL CREDITS EARNED: "+creditos_obt+" out of "+tot_creditos+" with GPA of 3.09", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, total_credits, posX+25, posY+100, 0);
    	//Award date of Issue
    	Phrase date_issue = new Phrase("AWARD (DATE OF ISSUE): ", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, date_issue, posX+25, posY+90, 0);
    	//Date printed
    	Phrase date_print = new Phrase("DATE PRINTED: ", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, date_print, posX+25, posY+70, 0);
		
	}catch(IOException ioe){
		System.err.println("Error al generar el recibo en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
	
	String nombreArchivo = planId+"-"+codigoAlumno+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>