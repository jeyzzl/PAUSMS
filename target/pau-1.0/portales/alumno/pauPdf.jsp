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
<%@page import = "aca.plan.spring.MapaMayorMenor"%>
<%@page import = "aca.catalogo.spring.CatTipoCal"%>
<%@page import = "aca.catalogo.spring.CatNivelInicio"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");

	String esAdmin 	   		= (String) session.getAttribute("esAdmin");
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
	
	String fecha 			= (String)request.getAttribute("fecha");
	String nombreAlum 		= (String)request.getAttribute("nombreAlum");
	String entry			= (String)request.getAttribute("entry");
	String dateOfBirth		= (String)request.getAttribute("dob");
	String planNombre 		= (String)request.getAttribute("planNombre");
	String mayor 			= (String)request.getAttribute("mayor");
	String menor 			= (String)request.getAttribute("menor");
	String nivelInicio 		= (String)request.getAttribute("nivelInicio");
	
	List<MapaCurso> lisCursos 						= (List<MapaCurso>)request.getAttribute("lisCursos");
	List<AlumnoCurso> lisNotas 						= (List<AlumnoCurso>)request.getAttribute("lisNotas");
	HashMap<String,String> mapaGradePoint 			= (HashMap<String,String>) request.getAttribute("mapaGradePoint");
	HashMap<String,AlumnoCurso> mapaCursos 			= (HashMap<String,AlumnoCurso>) request.getAttribute("mapaCursos");
	HashMap<String,CatTipoCal> mapaTipoCal 			= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipoCal");
	HashMap<String,String> mapaEscuelaPlan 			= (HashMap<String,String>) request.getAttribute("mapaEscuelaPlan");
	
	String institucion		= "PACIFIC ADVENTIST UNIVERSITY";
	String facultadId		= mapaEscuelaPlan.get(planId);

	boolean esBusiness 		= false;
	boolean esArtes			= false;
	// CHECA SI ES FACULTAD DE BUSINESS
	if(facultadId.equals("101")) esBusiness = true;
	// CHECA SI ES UN PLAN DE ARTES
	if(planId.equals("BAENGE23") || planId.equals("BAENHI23") || planId.equals("BAGEHI23")) esArtes = true;

//------PDF----->		
	int posX 	= 0; 
	int posY	= 0;
	final int MAX_COURSES_PER_PAGE = 40; // Adjust this value as needed

	//int salto 	= 20;
	
	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.A4 ); 
	document.setMargins(40,-10,50,20);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/")+"/"+planId+"-"+codigoAlumno+".pdf";	
	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("PACIFIC ADVENTIST UNIVERSITY");
        document.open();//Abrir documento
        
        PdfContentByte canvas = pdf.getDirectContentUnder(); //pdf por posiciones
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
    //Logo Um y texto debajo
    
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_pau.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(126, 66);
        jpg.setAbsolutePosition(42	, 750);
        document.add(jpg);
		
        // Encabezado, Titulo Institucion
        Phrase uni = new Phrase( institucion.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+295, posY+796, 0);
    	
    	// Datos de contacto
    	Phrase contacto = new Phrase( "PMB Boroko NCD, Papua New Guinea", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, contacto, posX+549, posY+776, 0);
    	Phrase telefono = new Phrase( "Phone: [675] 328 0200 Fax: [675] 328 1257", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, telefono, posX+549, posY+766, 0);   	
    	
    	// Nombre del Alumno		
    	Phrase alumno = new Phrase( "TRANSCRIPT FOR ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)));
    	alumno.add(new Phrase(nombreAlum.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, alumno, posX+295, posY+745, 0);
    	
    	//Nombre del Curso / Plan
    	Phrase course = new Phrase("COURSE: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	course.add(new Phrase("         "+planNombre.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, course, posX+42, posY+725, 0);
    	
		if(!esBusiness){
			//Mayor
			if(!mayor.equals("n/a")){
			Phrase major = new Phrase("MAJOR: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
			major.add(new Phrase("           "+mayor, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, major, posX+42, posY+714, 0);
			}

			if(!menor.equals("n/a")){
			//Menor
			Phrase minor = new Phrase(esArtes?"MAJOR":"MINOR: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
			minor.add(new Phrase("            "+menor, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, minor, posX+42, posY+702, 0);
			}
		}
    	
    	// Nivel de entrada
    	Phrase entryLevel = new Phrase("ENTRY LEVEL:    ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	entryLevel.add(new Phrase(" "+nivelInicio, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, entryLevel, posX+420, posY+725, 0);
    	
    	// Fecha de nacimiento del alumno
    	Phrase dob = new Phrase("DATE OF BIRTH: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	dob.add(new Phrase(" "+dateOfBirth, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dob, posX+420, posY+715, 0);
    	
    	
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

		// Texto 1
		Paragraph text1 = new Paragraph(
			"Established and incorporated as \na Seventh-day Adventist Tertiary\nCollege under the Pacific Adventist\nCollege Act, No.24 of 1983.",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text1.setLeading(8);
		text1.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx = posX + 420;  // lower-left x
    	float lly = posY + 100; // lower-left y
    	float urx = posX + 550; // upper-right x (adjust width as needed)
    	float ury = posY + 685; // upper-right y (adjust height as needed)

		ColumnText columnText = new ColumnText(canvas);
		columnText.setSimpleColumn(llx, lly, urx, ury);
		columnText.addElement(text1);
		columnText.go();

		// Texto 2
		Paragraph text2 = new Paragraph(
			"Incorporated as a Seventh-day\nAdventist University under the\nPacific Adventist University Act, No.\n34 of 1997.",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text2.setLeading(8);
		text2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx2 = posX + 420;  // lower-left x
    	float lly2 = posY + 100; // lower-left y
    	float urx2 = posX + 550; // upper-right x (adjust width as needed)
    	float ury2 = posY + 635; // upper-right y (adjust height as needed)

		ColumnText columnText2 = new ColumnText(canvas);
		columnText2.setSimpleColumn(llx2, lly2, urx2, ury2);
		columnText2.addElement(text2);
		columnText2.go();

		// Texto 3
    	Phrase gradingSys = new Phrase("Grading System:", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gradingSys, posX+420, posY+570, 0);

		Paragraph text3 = new Paragraph(
			"High\nDistinction\nDistinction\nCredit\nUpper Pass\nPass\nFail",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text3.setLeading(10);
		text3.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx3 = posX + 420;  // lower-left x
    	float lly3 = posY + 100; // lower-left y
    	float urx3 = posX + 550; // upper-right x (adjust width as needed)
    	float ury3 = posY + 570; // upper-right y (adjust height as needed)

		ColumnText columnText3 = new ColumnText(canvas);
		columnText3.setSimpleColumn(llx3, lly3, urx3, ury3);
		columnText3.addElement(text3);
		columnText3.go();

		Paragraph text4 = new Paragraph(
			"\n-           HD\n-           D\n-           C\n-           UP\n-           P\n-           F",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text4.setLeading(10);
		text4.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx4 = posX + 485;  // lower-left x
    	float lly4 = posY + 100; // lower-left y
    	float urx4 = posX + 590; // upper-right x (adjust width as needed)
    	float ury4 = posY + 570; // upper-right y (adjust height as needed)

		ColumnText columnText4 = new ColumnText(canvas);
		columnText4.setSimpleColumn(llx4, lly4, urx4, ury4);
		columnText4.addElement(text4);
		columnText4.go();

		// Texto 4
    	Phrase gradingPoints = new Phrase("Grading Points:", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, gradingPoints, posX+420, posY+480, 0);

		Paragraph text5 = new Paragraph(
			"High\nDistinction\nDistinction\nCredit\nUpper Pass\nPass\nFail",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text5.setLeading(10);
		text5.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx5 = posX + 420;  // lower-left x
    	float lly5 = posY + 100; // lower-left y
    	float urx5 = posX + 550; // upper-right x (adjust width as needed)
    	float ury5 = posY + 480; // upper-right y (adjust height as needed)

		ColumnText columnText5 = new ColumnText(canvas);
		columnText5.setSimpleColumn(llx5, lly5, urx5, ury5);
		columnText5.addElement(text5);
		columnText5.go();

		Paragraph text6 = new Paragraph(
			"\n=           4\n=           3.7\n=           3.2\n=           2.7\n=           2.2\n=           0",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text6.setLeading(10);
		text6.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx6 = posX + 485;  // lower-left x
    	float lly6 = posY + 100; // lower-left y
    	float urx6 = posX + 590; // upper-right x (adjust width as needed)
    	float ury6 = posY + 480; // upper-right y (adjust height as needed)

		ColumnText columnText6 = new ColumnText(canvas);
		columnText6.setSimpleColumn(llx6, lly6, urx6, ury6);
		columnText6.addElement(text6);
		columnText6.go();

		// Texto 5
    	Phrase semesterCodes = new Phrase("Semester Codes:", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, semesterCodes, posX+420, posY+380, 0);

		Paragraph text7 = new Paragraph(
			"0\n1",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text7.setLeading(10);
		text7.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx7 = posX + 420;  // lower-left x
    	float lly7 = posY + 100; // lower-left y
    	float urx7 = posX + 550; // upper-right x (adjust width as needed)
    	float ury7 = posY + 380; // upper-right y (adjust height as needed)

		ColumnText columnText7 = new ColumnText(canvas);
		columnText7.setSimpleColumn(llx7, lly7, urx7, ury7);
		columnText7.addElement(text7);
		columnText7.go();

		Paragraph text8 = new Paragraph(
			"=  Transfer Credits\n=  First Semester",
			FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)
		);
		text8.setLeading(10);
		text8.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx8 = posX + 485;  // lower-left x
    	float lly8 = posY + 100; // lower-left y
    	float urx8 = posX + 590; // upper-right x (adjust width as needed)
    	float ury8 = posY + 380; // upper-right y (adjust height as needed)

		ColumnText columnText8 = new ColumnText(canvas);
		columnText8.setSimpleColumn(llx8, lly8, urx8, ury8);
		columnText8.addElement(text8);
		columnText8.go();
    	
    	// Tabla de calificaiones
    	
    	PdfPTable tblMat = new PdfPTable(4);
		int tblMatWidths[] = {12,40,8,8};
		tblMat.setWidths(tblMatWidths);
		tblMat.setSpacingBefore(80f);
		tblMat.setWidthPercentage(65f);
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
		
		celda = new PdfPCell(new Phrase("CREDITS", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
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
		    	tblMat = new PdfPTable(4);
				tblMat.setWidths(tblMatWidths);
				tblMat.setSpacingBefore(200f);
				tblMat.setWidthPercentage(45f);
				tblMat.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				//Encabezados
				celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				celda.setPadding(2f);
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase("GRADE", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase("CREDITS", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				tblMat.addCell(celda);
		       
		    }
		    coursesOnCurrentPage++;

			// CALCULA EL TOTAL DE CREDITOS DEL PLAN
			if(curso.getTipoCursoId().equals("1")){
				tot_creditos += Integer.parseInt(curso.getCreditos());
			}else if(curso.getTipoCursoId().equals("2") && mapaCursos.containsKey(curso.getCursoId())){
				tot_creditos += Integer.parseInt(curso.getCreditos());
			}
			String SEM = "";
			String CUM = "";
			
			String nota 	= "0";
			
			String letra 	= "";
			String valor	= "";
			String tipoCalId = "";
			
			int entryYear = Integer.parseInt(entry);

			// Calculate the year based on the semester
			int year = Integer.parseInt(curso.getCiclo()); 
			int offset = (year - 1) / 2; 
			int cicloYear = entryYear + offset;

			if (!ciclo.equals(curso.getCiclo())){			
				SEM = getFormato.format(GPASEM);
				CUM = getFormato.format(GPACUM);
				ciclo = curso.getCiclo();
				celda = new PdfPCell(new Phrase("YEAR "+cicloYear+" SEMESTER "+curso.getCiclo(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setColspan(4);
				celda.setBorder(0);	
				celda.setPadding(0);
				
				tblMat.addCell(celda);	
				
				celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				celda.setPadding(0);
				
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setColspan(4);
				celda.setBorder(0);	
				celda.setPadding(0);
				
				tblMat.addCell(celda);
				
				GPASEM = 0;
				creditosSEM = 0;
				notasxcreditosSEM = 0;
				
				currentCiclo++;

			}
			
			String optativa = "";
			boolean esElectivaAsignada = false;
			for (AlumnoCurso alumnoCurso : lisNotas){
				// CHECA SI ES MATERIA ELECTIVA Y SI ESTA ASIGNADA AL ALUMNO
				if(curso.getTipoCursoId().equals("2")){
					if(curso.getCursoId().equals(alumnoCurso.getCursoId()) && 
						(alumnoCurso.getTipoCalId().equals("1") || alumnoCurso.getTipoCalId().equals("2") || alumnoCurso.getTipoCalId().equals("I") || alumnoCurso.getTipoCalId().equals("M"))){
							esElectivaAsignada = true;
					}
				}
				// CALCULA CREDITOS Y NOTAS
				if (alumnoCurso.getCursoId().equals(curso.getCursoId())){
					if(!alumnoCurso.getOptativa().equals("-") && !alumnoCurso.getOptativa().equals("*")){
						optativa = " - "+alumnoCurso.getOptativa().toUpperCase();
					}
					// EXCLUYE MATERIAS NO ACREDITADAS O CALIFICADAS
					if(alumnoCurso.getTipoCalId().equals("1")){
						creditos_obt += Integer.parseInt(curso.getCreditos());
						nota = alumnoCurso.getNota();
						if (mapaGradePoint.containsKey(nota)){						 
							letra = mapaGradePoint.get(nota).split(";")[0];
							valor = mapaGradePoint.get(nota).split(";")[1];
						}

						tipoCalId = alumnoCurso.getTipoCalId();
						notasxcreditosSEM = notasxcreditosSEM + Float.parseFloat(alumnoCurso.getCreditos())*Float.valueOf(valor);
						
						creditosSEM = creditosSEM + Float.parseFloat(alumnoCurso.getCreditos().trim());
						notasxcreditosCUM = notasxcreditosCUM + Float.parseFloat(alumnoCurso.getCreditos())*Float.valueOf(valor);
						creditosCUM = creditosCUM + Float.parseFloat(alumnoCurso.getCreditos().trim());
					}else{
						if(!alumnoCurso.getTipoCalId().equals("I") && !alumnoCurso.getTipoCalId().equals("M")){
							if(mapaTipoCal.containsKey(alumnoCurso.getTipoCalId())){
								letra = mapaTipoCal.get(alumnoCurso.getTipoCalId()).getNombreCorto();
							}
						}else{
							letra = "";
						}
					}
				}
			}

			// CALCULA LOS VALORES DEL GPA
			GPASEM = (double) notasxcreditosSEM / creditosSEM;
			GPACUM = (double) notasxcreditosCUM / creditosCUM;
			
			// IMPRIME MATERIAS MANDATORIAS Y ELECTIVAS ASIGNADAS
			if(curso.getTipoCursoId().equals("1") || esElectivaAsignada){
				celda = new PdfPCell(new Phrase(" "+curso.getCursoClave(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				celda.setPadding(0);
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase(curso.getNombreCurso().toUpperCase()+optativa, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);	
				celda.setPadding(0);
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase(letra, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				celda.setPadding(0);
				tblMat.addCell(celda);
				
				celda = new PdfPCell(new Phrase(tipoCalId.equals("1") ? curso.getCreditos(): "0", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b) )));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(0);	
				celda.setPadding(0);
				tblMat.addCell(celda);
			}
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

		double truncGPACUM = Double.parseDouble(String.format("%.4f", GPACUM));
		
		// Signature
    	Phrase sign_line = new Phrase("_____________________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, sign_line, posX+440, posY+130, 0);
    	Phrase sign_text = new Phrase("Deputy Vice-Chancellor", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, sign_text, posX+440, posY+120, 0);
    	
    	//Credits and GPA
    	Phrase total_credits = new Phrase("TOTAL CREDITS EARNED: "+creditos_obt+" out of "+tot_creditos+" with GPA of "+getFormato.format(GPACUM), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, total_credits, posX+45, posY+130, 0);
    	//Award date of Issue
    	Phrase date_issue = new Phrase("AWARD (DATE OF ISSUE): ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, date_issue, posX+45, posY+120, 0);
    	//Date printed
    	Phrase date_print = new Phrase("DATE PRINTED: "+fecha, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, date_print, posX+45, posY+100, 0);
		
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