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
	
	String institucion		= "SONOMA ADVENTIST COLLEGE";

    String folio            = (String)request.getAttribute("folio");
    String nombre           = (String)request.getAttribute("nombre");
    String matricula        = (String)request.getAttribute("matricula");
    String curso            = (String)request.getAttribute("curso");
    String ciclo            = (String)request.getAttribute("ciclo");
    String fecha            = (String)request.getAttribute("fecha");
    String ciclos           = (String)request.getAttribute("numeroCiclos");
    String numeroOferta     = (String)request.getAttribute("numeroOferta");
    String fechaFinal       = (String)request.getAttribute("fechaFinal");
    String fechaFinalEscrita = (String)request.getAttribute("fechaFinalEscrita");

    String year             = fecha.substring(fecha.length() - 4);
	
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

    	//Logo Pau
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_pau.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(115, 65);
        jpg.setAbsolutePosition(45, 770);
        document.add(jpg);

    	//Logo Sonoma
        Image jpg2 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_logo_sonoma.png");
        jpg2.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg2.scaleAbsolute(165, 60);
        jpg2.setAbsolutePosition(220, 770);
        document.add(jpg2);

    	//Franja 1
        Image jpg3 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_franja_sonoma.png");
        jpg3.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg3.scaleAbsolute(665, 20);
        jpg3.setAbsolutePosition(-50, 745);
        document.add(jpg3);

        // Direccion
		Paragraph direccion = new Paragraph(
			"Sonoma Adventist College\nP.O Box 360. Kokopo 613\nPapua New Guinea\nPhone: 982 1782, 719 02655\ninfo@sonoma.ac.pg",
			FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLUE)
		);
		direccion.setLeading(12);
		direccion.setAlignment(Element.ALIGN_JUSTIFIED);

    	float llx = 730;  // lower-left x
    	float lly = 100; // lower-left y
    	float urx = 435; // upper-right x (adjust width as needed)
    	float ury = 830; // upper-right y (adjust height as needed)

		ColumnText columnText = new ColumnText(canvas);
		columnText.setSimpleColumn(llx, lly, urx, ury);
		columnText.addElement(direccion);
		columnText.go();

    	// Fecha
    	Phrase fechaTitulo = new Phrase("Date: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(0,0,0)));
    	fechaTitulo.add(new Phrase(fecha, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(255,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fechaTitulo, 45, 730, 0);

    	// Referencia
    	Phrase referencia = new Phrase("Reference: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(0,0,0)));
    	referencia.add(new Phrase("LETTER OF OFFER - New Student", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(0,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, referencia, 45, 715, 0);

    	// Titulo
    	Phrase titulo = new Phrase("Dear ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(0,0,0)));
    	titulo.add(new Phrase(nombre, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(255,0,0))));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, titulo, 45, 690, 0);

        // Texto 1
        String texto1   =    "Congratulations! On behalf of Sonoma Adventist College, I thank you for your choice to study at Sonoma Adventist College in 2023. Hence we are glad to inform you that your application is successful, and you are given this letter of offer to take "+curso+" ("+ciclos+" years).\n\n"
                        +    "You must read the Information Sheet carefully before coming to Sonoma Adventist College. During registration, you must show to the Registrar the original copy of the following documents: (Do not email the documents).";

		Paragraph text1 = new Paragraph(texto1, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
		text1.setLeading(12);
		text1.setAlignment(Element.ALIGN_LEFT);

    	float llx2 = 45;  // lower-left x  
    	float lly2 = 100; // lower-left y
    	float urx2 = 560; // upper-right x (adjust width as needed)
    	float ury2 = 680; // upper-right y (adjust height as needed)

		ColumnText columnText2 = new ColumnText(canvas);
		columnText2.setSimpleColumn(llx2, lly2, urx2, ury2);
		columnText2.addElement(text1);
		columnText2.go();
        
        // Requisito 1
    	Phrase requisito1 = new Phrase("(1) Identification Card", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, requisito1, 55, 590, 0);
        
        // Requisito 2
    	Phrase requisito2 = new Phrase("(2) Grade 10 Certificate", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, requisito2, 55, 578, 0);
        
        // Requisito 3
    	Phrase requisito3 = new Phrase("(3) Grade 12 Certificate", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, requisito3, 55, 566, 0);
        
        // Requisito 4
    	Phrase requisito4 = new Phrase("(4) Medical Certificate (issued not more than 12 months ago)", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, requisito4, 200, 590, 0);
        
        // Requisito 5
    	Phrase requisito5 = new Phrase("(5) Two Character References (forms attached).", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, requisito5, 200, 578, 0);
        
        // Requisito 6
    	Phrase requisito6 = new Phrase("(6) Certificate of Birth Entry (CBE) or fully filled up Birth and National Identity", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, requisito6, 200, 566, 0);

        // Requisito 6.1
    	Phrase requisito = new Phrase("Registration Form", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, requisito, 200, 554, 0);

        // Texto 2
        String texto2   =    "This letter of offer is not a guarantee of reservation of a space. Your enrolment will be confirmed only when your "
                        +    "payment appears on the bank statement of Sonoma account. Make sure that the bank officer writes your Full Name "
                        +    "and your Offer Number on the deposit receipt and the bank statement. 50% (see the table below and the attached fee "
                        +    "schedule for more information) of the total fees for the school year must be paid before "+fechaFinalEscrita+". If we do not "
                        +    "receive (through the bank statement of Sonoma account) the 50% of the total fees for the school year on or before "
                        +    fechaFinal+", then YOUR PLACE IN THE COURSE WILL BE GIVEN TO SOMEONE ELSE on our waiting list.";

		Paragraph text2 = new Paragraph(texto2, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
		text2.setLeading(12);
		text2.setAlignment(Element.ALIGN_LEFT);

    	float llx3 = 45;  // lower-left x  
    	float lly3 = 100; // lower-left y
    	float urx3 = 560; // upper-right x (adjust width as needed)
    	float ury3 = 540; // upper-right y (adjust height as needed)

		ColumnText columnText3 = new ColumnText(canvas);
		columnText3.setSimpleColumn(llx3, lly3, urx3, ury3);
		columnText3.addElement(text2);
		columnText3.go();

        // Tabla
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); // Set table width to 100% of the page width

        // Define los textos de la tabla
        String[][] cellTexts = {
            {"Enrollment Status", "Fees Schedule"},
            {"Boarding Student\n(100%)", "K13,100.00"},
            {"Boarding Student (50%)", "K6,550.00"},
            {"Day Student (100%)", "K8,800.00"},
            {"Day Student (50%)", "K4,400.00"}
        };

		for (int i = 0; i < cellTexts.length; i++) {
			for (int j = 0; j < cellTexts[i].length; j++) {
				String text = cellTexts[i][j];
				Font font = FontFactory.getFont(FontFactory.HELVETICA, 10, (j == 0) ? Font.BOLD : Font.NORMAL, new BaseColor(0, 0, 0));
				PdfPCell cell = new PdfPCell(new Phrase(text, font));
				cell.setBorderWidth(0.25f); // Set border width
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left for labels, right for values
				cell.setVerticalAlignment(Element.ALIGN_LEFT); // Center vertically
				cell.setMinimumHeight(5f);
				table.addCell(cell);
			}
		}

        float tlx = 45; // lower-left x
        float tly = 180; // lower-left y
        float trx = 260; // upper-right x
        float tury = 455; // upper-right y

        ColumnText columnText4 = new ColumnText(canvas);
        columnText4.setSimpleColumn(tlx, tly, trx, tury);
        columnText4.addElement(table);
        columnText4.go();

        // Texto 3
        String texto3   =    "Of the almost 700 students for 2023, only 520 can be accommodated in the dormitory; the 200 will be day students. "
                        +    "Dormitory space is based on “first pay first serve” basis. The first 260 male students and all female students are "
                        +    "expected to be boarders. Only those female students residing with parent or guardian can be day students. After "
                        +    "payment, email your deposit slip to our Student Finance Officer, Miss MarieAnne Aite "
                        +    "(MarieAnne.Aite@sonoma.ac.pg) and ask for your "+year+" Student Sequence Number. There is no accommodation for "
                        +    "married students in the dormitory. Fees can be paid into either of the accounts below.";

		Paragraph text3 = new Paragraph(texto3, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
		text3.setLeading(12);
		text3.setAlignment(Element.ALIGN_LEFT);

    	float llx4 = 45;  // lower-left x  
    	float lly4 = 100; // lower-left y
    	float urx4 = 560; // upper-right x (adjust width as needed)
    	float ury4 = 350; // upper-right y (adjust height as needed)

		ColumnText columnText5 = new ColumnText(canvas);
		columnText5.setSimpleColumn(llx4, lly4, urx4, ury4);
		columnText5.addElement(text3);
		columnText5.go();

        // Cuenta 1
        String texto4   =    "A) Account Name: Sonoma Adventist College\n"
                        +    "Account No.: 290 1178 403\n"
                        +    "Bank Name: Westpac Bank\n"
                        +    "Branch: Kokopo, ENBP";

		Paragraph cuenta1 = new Paragraph(texto4, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
		cuenta1.setLeading(12);
		cuenta1.setAlignment(Element.ALIGN_LEFT);

    	float llx5 = 45;  // lower-left x  
    	float lly5 = 100; // lower-left y
    	float urx5 = 560; // upper-right x (adjust width as needed)
    	float ury5 = 265; // upper-right y (adjust height as needed)

		ColumnText columnText6 = new ColumnText(canvas);
		columnText6.setSimpleColumn(llx5, lly5, urx5, ury5);
		columnText6.addElement(cuenta1);
		columnText6.go();

        // Cuenta 2
        String texto5   =    "B) Account Name: Sonoma Adventist College\n"
                        +    "Account No.: 1000 689 124\n"
                        +    "Bank Name:  Bank of South Pacific\n"
                        +    "Branch: Kokopo, ENBP";

		Paragraph cuenta2 = new Paragraph(texto5, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
		cuenta2.setLeading(12);
		cuenta2.setAlignment(Element.ALIGN_LEFT);

    	float llx6 = 280;  // lower-left x  
    	float lly6 = 100; // lower-left y
    	float urx6 = 560; // upper-right x (adjust width as needed)
    	float ury6 = 265; // upper-right y (adjust height as needed)

		ColumnText columnText7 = new ColumnText(canvas);
		columnText7.setSimpleColumn(llx6, lly6, urx6, ury6);
		columnText7.addElement(cuenta2);
		columnText7.go();

        // Agradecimiento
    	Phrase agradecimiento = new Phrase("Thank you for your time and hoping to have you as our student in "+year+".", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, agradecimiento, 45, 190, 0);

        // Despedida
    	Phrase despedida = new Phrase("Yours faithfully,", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, despedida, 45, 166, 0);

        // Remitente
    	Phrase remitente1 = new Phrase("Mr Seth Puivui", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, remitente1, 45, 142, 0);
    	Phrase remitente2 = new Phrase("Registrar", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, remitente2, 45, 130, 0);

    	//Franja 2
        Image jpg4 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_franja_sonoma.png");
        jpg4.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg4.scaleAbsolute(665, 20);
        jpg4.setAbsolutePosition(-50, 50);
        document.add(jpg4);

        // Footer
        String texto6   =    "An institution operated by Seventh-day Adventist Church and an affiliated campus of Pacific Adventist University";

		Paragraph footer = new Paragraph(texto6, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLUE));
		footer.setLeading(12);
		footer.setAlignment(Element.ALIGN_CENTER);

    	float llx7 = 50;  // lower-left x  
    	float lly7 = 40; // lower-left y
    	float urx7 = 550; // upper-right x (adjust width as needed)
    	float ury7 = 15; // upper-right y (adjust height as needed)

		ColumnText columnText8 = new ColumnText(canvas);
		columnText8.setSimpleColumn(llx7, lly7, urx7, ury7);
		columnText8.addElement(footer);
		columnText8.go();

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