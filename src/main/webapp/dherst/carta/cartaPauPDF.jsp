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
<%@page import = "aca.admision.spring.AdmAcomodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>   
<%
	DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");
	
	String institucion		= "PACIFIC ADVENTIST UNIVERSITY";

	String folio            = (String)request.getAttribute("folio");
    String nombre           = (String)request.getAttribute("nombre");
    String matricula        = (String)request.getAttribute("matricula");
    String curso            = (String)request.getAttribute("curso");
    // String ciclo            = (String)request.getAttribute("ciclo");
	String acomodo 			= (String)request.getAttribute("acomodo");
	String plan 			= (String)request.getAttribute("plan");
	// String ciclos 			= (String)request.getAttribute("ciclos");
    String fecha            = (String)request.getAttribute("fecha");
	String fechaRegistro 	= (String)request.getAttribute("fechaRegistro");
	String fechaOrientacion = (String)request.getAttribute("fechaOrientacion");
	String fechaApertura 	= (String)request.getAttribute("fechaApertura");
	String fechaInicio 		= (String)request.getAttribute("fechaInicio");
	String fechaCierre 		= (String)request.getAttribute("fechaCierre");
	String estadoResidencia = (String)request.getAttribute("estadoResidencia");
	String direccionPostal  = (String)request.getAttribute("direccionPostal");
	String fechaEscrita 	= (String)request.getAttribute("fechaEscrita");
	String fechaCierreEscrita 	= (String)request.getAttribute("fechaCierreEscrita");
	
	String year             = fecha.substring(fecha.length() - 4);

	// Load the Open Sans font
	String fontPathRegular = application.getRealPath("/WEB-INF/fonts/OpenSans-Italic-VariableFont_wdth,wght.ttf");
	String fontPathBold = application.getRealPath("/WEB-INF/fonts/OpenSans-VariableFont_wdth,wght.ttf");

	// Register the fonts
	BaseFont openSansRegularBase = BaseFont.createFont(fontPathBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	BaseFont openSansBoldBase = BaseFont.createFont(fontPathBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

	Font openSansRegular = new Font(openSansRegularBase, 10, Font.NORMAL, BaseColor.BLACK);
	Font openSansBold = new Font(openSansBoldBase, 10, Font.BOLD, BaseColor.BLACK);

//------PDF----->		
	int posX 	= 0; 
	int posY	= 0;
	final int MAX_COURSES_PER_PAGE = 25; // Adjust this value as needed

	//int salto 	= 20;
	
	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.A4); 
	document.setMargins(100,35,30,30);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/")+"/file.pdf";	

	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor(institucion);
        document.open();//Abrir documento
        
        PdfContentByte canvas = pdf.getDirectContentUnder();//pdf por posiciones
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
    	//Logo Um y texto debajo
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_pau.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(115, 65);
        jpg.setAbsolutePosition(440, 760);
        document.add(jpg);
		
        // Encabezado
        Phrase uni = new Phrase( "Office of the Registrar", new Font(openSansBoldBase, 14, Font.BOLD, BaseColor.BLACK));   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, uni, posX+55, posY+735, 0);
    	
    	// Linea divisora
    	Phrase contacto = new Phrase( "_______________________________________________________________________________________________________________________", openSansRegular);   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, contacto, posX+55, posY+730, 0);
    	
    	// Contact Address
    	Phrase direccionContacto = new Phrase("Contact Address: ",openSansRegular);
    	direccionContacto.add(new Phrase(direccionPostal, openSansRegular));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, direccionContacto, posX+55, posY+700, 0);

    	// Fecha
    	Phrase fechaTitulo = new Phrase("Insert Date: ",openSansRegular);
    	fechaTitulo.add(new Phrase(fechaEscrita, openSansRegular));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fechaTitulo, posX+55, posY+685, 0);
    	
    	// Nombre alumno
    	Phrase nombreAlum = new Phrase("Name of Applicant: ",openSansRegular);
		nombreAlum.add(new Phrase(nombre, openSansRegular));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, nombreAlum, posX+55, posY+670, 0);
    	
    	// Saludo
    	Phrase entryLevel = new Phrase("Dear ",openSansRegular);
    	entryLevel.add(new Phrase(nombre.toUpperCase(), openSansRegular));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, entryLevel, posX+55, posY+645, 0);

        // Titulo carta
        Phrase tituloCarta = new Phrase( "LETTER OF ACCEPTANCE - ", openSansBold);
        tituloCarta.add(new Phrase(curso.toUpperCase() , openSansRegular));  	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, tituloCarta, posX+55, posY+625, 0);

        // Texto 1
    	Phrase text1 = new Phrase("Thank you for choosing to study at Pacific Adventist University in "+year+".", openSansRegular);
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, text1, posX+55, posY+595, 0);

        // Texto 2
		Paragraph text2 = new Paragraph(
			"We are pleased to inform you of your placement in the "+plan+" here at Pacific Adventist University, as your choice of institution from the online selection system of the Department of Higher Education, Research, Science & Technology. This offer also confirms your residential status.\n\nHowever, before you confirm your acceptance to enroll in given course at PAU, you are expected to know and be aware of the following important principles that PAU upholds:",
			openSansRegular
		);
		text2.setLeading(14);
		text2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx = posX + 55;  // lower-left x
    	float lly = posY + 100; // lower-left y
    	float urx = posX + 525; // upper-right x (adjust width as needed)
    	float ury = posY + 590; // upper-right y (adjust height as needed)

		ColumnText columnText = new ColumnText(canvas);
		columnText.setSimpleColumn(llx, lly, urx, ury);
		columnText.addElement(text2);
		columnText.go();
    
		// Texto 3
		Paragraph text3 = new Paragraph(
			"PAU is owned and operated by the Seventh Day Adventist Church and we require all students to abide by the important principles, policies, rules and regulations of the university. PAU is focused on providing academic excellence, spiritual growth in addition to the social and physical aspects of life resulting in commitment to service. Students are expected to respect and attend the spiritual events organized on campus. The use of substances such as marijuana, tobacco or chewing of betelnut and alcohol on campus is strictly forbidden. Any student caught will be penalized accordingly. For boarding students, they will be expected to work in an allocated department as part of their training. More of these will be emphasized during the Orientation program.",
			openSansRegular
		);
		text3.setLeading(14);
		text3.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx8 = posX + 55;  // lower-left x
    	float lly8 = posY + 100; // lower-left y
    	float urx8 = posX + 525; // upper-right x (adjust width as needed)
    	float ury8 = posY + 485; // upper-right y (adjust height as needed)

		ColumnText columnText7 = new ColumnText(canvas);
		columnText7.setSimpleColumn(llx8, lly8, urx8, ury8);
		columnText7.addElement(text3);
		columnText7.go();

       // Texto 4
		Paragraph text4 = new Paragraph(
			"After reading the above, you have the right to accept or reject PAU's offer. Once you confirm your acceptance to enroll at PAU, you have chosen to abide by these principles and practices.\n\nPlease take note of your student information as you plan to confirm your enrolment.",
			openSansRegular
		);
		text4.setLeading(14);
		text4.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx4 = 55;  // lower-left x
    	float lly4 = 20; // lower-left y
    	float urx4 = 525; // upper-right x (adjust width as needed)
    	float ury4 = 365; // upper-right y (adjust height as needed)

		ColumnText columnText3 = new ColumnText(canvas);
		columnText3.setSimpleColumn(llx4, lly4, urx4, ury4);
		columnText3.addElement(text4);
		columnText3.go();


        // Tabla
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); // Set table width to 100% of the page width

        // Define los textos de la tabla
        String[][] cellTexts = {
            {"Course:", curso},
			{"Specialisation:", plan.toLowerCase()},
            {"Provisional ID:", matricula},
            {"Residential Status:", acomodo},
            {"Registration & Orientation Dates:", fechaRegistro+" & "+fechaOrientacion},
            {"Opening & Convocation:", fechaApertura},
            {"Class Commencement Date:", fechaInicio}
        };

		for (int i = 0; i < cellTexts.length; i++) {
			for (int j = 0; j < cellTexts[i].length; j++) {
				String text = cellTexts[i][j];
				// Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, (j == 0) ? Font.BOLD : Font.NORMAL, new BaseColor(0, 0, 0));
				PdfPCell cell = new PdfPCell(new Phrase(text, (j == 0) ? openSansBold : openSansRegular));
				cell.setBorderWidth(0.25f); // Set border width
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left for labels, right for values
				cell.setVerticalAlignment(Element.ALIGN_LEFT); // Center vertically
				cell.setMinimumHeight(6f);
				table.addCell(cell);
			}
		}

        float tlx = 55; // lower-left x
        float tly = 180; // lower-left y
        float trx = 525; // upper-right x
        float tury = 295; // upper-right y

        ColumnText columnText2 = new ColumnText(canvas);
        columnText2.setSimpleColumn(tlx, tly, trx, tury);
        columnText2.addElement(table);
        columnText2.go();

		// Footer
		Image jpg2 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_footer_pau.png");
        jpg2.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg2.scaleAbsolute(515, 50);
        jpg2.setAbsolutePosition(50, 30);
        document.add(jpg2);


		// Start a new page
        document.newPage();

    	//Logo Um y texto debajo
        Image jpg5 = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_pau.png");
        jpg5.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg5.scaleAbsolute(115, 65);
        jpg5.setAbsolutePosition(440, 760);
        document.add(jpg5);

       // Texto 5
		Paragraph text5 = new Paragraph(
			"If you are definitely planning to come to PAU in "+year+", please complete the confirmation of acceptance form and submit to our office before "+fechaCierreEscrita,
			openSansRegular
		);
		text5.setLeading(14);
		text5.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx9 = 55;  // lower-left x
    	float lly9 = 20; // lower-left y
    	float urx9 = 525; // upper-right x (adjust width as needed)
    	float ury9 = 750; // upper-right y (adjust height as needed)

		ColumnText columnText8 = new ColumnText(canvas);
		columnText8.setSimpleColumn(llx9, lly9, urx9, ury9);
		columnText8.addElement(text5);
		columnText8.go();

       // Texto 3 Pagina 2
		Paragraph text7p2 = new Paragraph(
			"Because of the limited spaces available, we have allocated quotas for each course. This means that you have taken up a space in the "+plan+" and that YOU CANNOT CHANGE or TRANSFER TO ANOTHER COURSE.  Should you wish to change course then you automatically forfeit your space and that your application for enrollment in another course will have to be reconsidered.",
			openSansRegular
		);
		text7p2.setLeading(14);
		text7p2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx12 = 55;  	// lower-left x
    	float lly12 = 20; 	// lower-left y
    	float urx12 = 525; 	// upper-right x (adjust width as needed)
    	float ury12 = 700; 	// upper-right y (adjust height as needed)

		ColumnText columnText12 = new ColumnText(canvas);
		columnText12.setSimpleColumn(llx12, lly12, urx12, ury12);
		columnText12.addElement(text7p2);
		columnText12.go();

       // Texto 3 Pagina 2
		Paragraph text8p2 = new Paragraph(
			"As our standard process is, your acceptance from this institution has been granted without a scholarship. This means you have to secure your own sponsorship or be prepared to meet all the prescribed fees of the university as a self-sponsored student. We require applicants who have been accepted, and are definitely planning to come to PAU, to pay 80% of the first semesters fees to confirm their acceptance and a copy of the receipt and confirmation of acceptance be emailed to the Admission Officer by "+fechaCierreEscrita+". Please note that DHERST will confirm its TESAS Scholarship Offers and communicate directly with all accepted students. However, for your information, TESAS Fees are as given: HECAS - K2,160 per semester and AES - K2,880 per semester",
			openSansRegular
		);
		text8p2.setLeading(14);
		text8p2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx13 = 55;  	// lower-left x
    	float lly13 = 20; 	// lower-left y
    	float urx13 = 525; 	// upper-right x (adjust width as needed)
    	float ury13 = 635; 	// upper-right y (adjust height as needed)

		ColumnText columnText13 = new ColumnText(canvas);
		columnText13.setSimpleColumn(llx13, lly13, urx13, ury13);
		columnText13.addElement(text8p2);
		columnText13.go();
		
		// Texto 1 Pagina 2
		Phrase text1p2 = new Phrase("Registration Requirements", new Font(openSansRegularBase, 10, Font.UNDERLINE, BaseColor.BLACK));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, text1p2, 55, 490, 0);

		// Texto 2 Pagina 2
        String formattedText = "During registration you will be required to provide the following information:\n" +
				"\n"+
                "               -\t Your Offer Letter\n" +
                "               -\t Copy of Confirmation of acceptance\n" +
                "               -\t Original school Fee Receipt\n" +
                "               -\t Original Copies of Certificates (Grade 10 & Grade 12)\n" +
                "               -\t A copy of an ID (Birth Certificate, NID or Passport etc...)";

		Paragraph text2p2 = new Paragraph(formattedText,openSansRegular);
		text2p2.setLeading(14);
		text2p2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx5 = 55;  // lower-left x
    	float lly5 = 20; // lower-left y
    	float urx5 = 525; // upper-right x (adjust width as needed)
    	float ury5 = 475; // upper-right y (adjust height as needed)

		ColumnText columnText4 = new ColumnText(canvas);
		columnText4.setSimpleColumn(llx5, lly5, urx5, ury5);
		columnText4.addElement(text2p2);
		columnText4.go();

       // Texto 3 Pagina 2
		Paragraph text3p2 = new Paragraph(
			"Finally, find enclosed an acceptance information brochure and your fee estimate for "+year+". These documents will assist you in your preparation for your arrival at the university.",
			openSansRegular
		);
		text3p2.setLeading(14);
		text3p2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx6 = 55;  	// lower-left x
    	float lly6 = 20; 	// lower-left y
    	float urx6 = 525; 	// upper-right x (adjust width as needed)
    	float ury6 = 365; 	// upper-right y (adjust height as needed)

		ColumnText columnText5 = new ColumnText(canvas);
		columnText5.setSimpleColumn(llx6, lly6, urx6, ury6);
		columnText5.addElement(text3p2);
		columnText5.go();

		// Texto 4 Pagina 2
		Paragraph text5p2 = new Paragraph(
			"If we don't hear from you by "+fechaCierreEscrita+", we will assume that you are not coming and hence may allocate your space to someone else.",
			openSansRegular
		);
		text5p2.setLeading(14);
		text5p2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx10 = 55;  	// lower-left x
    	float lly10 = 20; 	// lower-left y
    	float urx10 = 525; 	// upper-right x (adjust width as needed)
    	float ury10 = 320; 	// upper-right y (adjust height as needed)

		ColumnText columnText9 = new ColumnText(canvas);
		columnText9.setSimpleColumn(llx10, lly10, urx10, ury10);
		columnText9.addElement(text5p2);
		columnText9.go();

		// Texto 4 Pagina 2
		Paragraph text6p2 = new Paragraph(
			"Looking forward to hearing from you.\n\nYours sincerely,",
			openSansRegular
		);
		text6p2.setLeading(14);
		text6p2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx11 = 55;  	// lower-left x
    	float lly11 = 20; 	// lower-left y
    	float urx11 = 525; 	// upper-right x (adjust width as needed)
    	float ury11 = 280; 	// upper-right y (adjust height as needed)

		ColumnText columnText10 = new ColumnText(canvas);
		columnText10.setSimpleColumn(llx11, lly11, urx11, ury11);
		columnText10.addElement(text6p2);
		columnText10.go();

		// Firma
		Image jpg3 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_firma_pau.png");
        jpg3.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg3.scaleAbsolute(90, 30);
        jpg3.setAbsolutePosition(55, 200);
        document.add(jpg3);

		// Texto 4 Pagina 2
		Paragraph text4p2 = new Paragraph(
			"Yvonne N'Dramei\nAdmissions Officer\nOn behalf of the Admissions Committee",openSansRegular);
		text4p2.setLeading(14);
		text4p2.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx7 = 55;  	// lower-left x
    	float lly7 = 20; 	// lower-left y
    	float urx7 = 525; 	// upper-right x (adjust width as needed)
    	float ury7 = 200; 	// upper-right y (adjust height as needed)

		ColumnText columnText6 = new ColumnText(canvas);
		columnText6.setSimpleColumn(llx7, lly7, urx7, ury7);
		columnText6.addElement(text4p2);
		columnText6.go();

		// Footer
		Image jpg4 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_footer_pau.png");
        jpg4.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg4.scaleAbsolute(515, 50);
        jpg4.setAbsolutePosition(50, 30);
        document.add(jpg4);


	}catch(IOException ioe){
		System.err.println("Error al generar el recibo en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
	
	String nombreArchivo = "file.pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>