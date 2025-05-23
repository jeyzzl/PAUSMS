<%@page import = "org.w3c.dom.traversal.DocumentTraversal"%>
<%@ page import = "java.awt.Color" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

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
	// DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");
	
	String institucion		= "FULTON ADVENTIST UNIVERSITY COLLEGE";

    String folio            = (String)request.getAttribute("folio");
    String nombre           = (String)request.getAttribute("nombre");
    String matricula        = (String)request.getAttribute("matricula");
    String curso            = (String)request.getAttribute("curso");
    String ciclo            = (String)request.getAttribute("ciclo");
    String fecha            = (String)request.getAttribute("fecha");
    String fechaRegistro    = (String)request.getAttribute("fechaRegistro");
    String fechaOrientacion = (String)request.getAttribute("fechaOrientacion");
    String fechaInicio      = (String)request.getAttribute("fechaInicio");
    String fechaCierre      = (String)request.getAttribute("fechaCierre");
    String fechaArribo      = (String)request.getAttribute("fechaArribo");
    String direccionB       = (String)request.getAttribute("direccion");
    String ciclos           = (String)request.getAttribute("numeroCiclos");
    
    String year             = fecha.substring(fecha.length() - 4);

    System.out.println(fecha);

	
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
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_logo_fulton.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(190, 68);
        jpg.setAbsolutePosition(370, 720);
        document.add(jpg);

        // Direccion Institucion
		Paragraph direccion = new Paragraph(
			"Private Mail Bag | Nadi Airport | Fiji Island\nMasimasi Road, Sabeto, NADI\nPhone: 999-3118 | Email: info@fulton.ac.fj\nwwww.Fulton.ac.fj",
			FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK)
		);
		direccion.setLeading(10);
		direccion.setAlignment(Element.ALIGN_RIGHT);

    	float llx = 200;  // lower-left x
    	float lly = 200; // lower-left y
    	float urx = 550; // upper-right x (adjust width as needed)
    	float ury = 715; // upper-right y (adjust height as needed)

		ColumnText columnText = new ColumnText(canvas);
		columnText.setSimpleColumn(llx, lly, urx, ury);
		columnText.addElement(direccion);
		columnText.go();

        // Inside Address
        String  texto1   =   fecha + " \n"
                        +    "Inside Address\n"
                        +    "Inside Address\n"
                        +    "Inside Address\n"
                        +    "Inside Address";

		Paragraph text1 = new Paragraph(texto1, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLUE));
		text1.setLeading(12);
		text1.setAlignment(Element.ALIGN_LEFT);

    	float llx2 = 45;  // lower-left x  
    	float lly2 = 100; // lower-left y
    	float urx2 = 560; // upper-right x (adjust width as needed) 
    	float ury2 = 720; // upper-right y (adjust height as needed)

		ColumnText columnText2 = new ColumnText(canvas);
		columnText2.setSimpleColumn(llx2, lly2, urx2, ury2);
		columnText2.addElement(text1);
		columnText2.go();

        // Titulo de Registrador
        Phrase titulo = new Phrase("OFFICE OF THE REGISTRAR", FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, titulo, 330, 650, 0);

        // Destinatario
        Phrase destinatario = new Phrase("Dear "+nombre, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, destinatario, 45, 615, 0);

        // Saludo
        Phrase saludo = new Phrase("OFFER OF ADMISSION TO FULTON ADVENTIST UNIVERSITY COLLEGE FOR "+year, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, saludo, 45, 595, 0);

        // Parrafo 1
        String texto2 = "Thank you for your application which we have received.  On behalf of Fulton Adventist University College, I am pleased to offer you admission beginning this semester 1, "+year+":";

		Paragraph text2 = new Paragraph(texto2, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK));
		text2.setLeading(12);
		text2.setAlignment(Element.ALIGN_LEFT);

    	float llx3 = 45;  // lower-left x  
    	float lly3 = 100; // lower-left y
    	float urx3 = 560; // upper-right x (adjust width as needed)
    	float ury3 = 585; // upper-right y (adjust height as needed)

		ColumnText columnText3 = new ColumnText(canvas);
		columnText3.setSimpleColumn(llx3, lly3, urx3, ury3);
		columnText3.addElement(text2);
		columnText3.go();

        // Tabla
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); // Set table width to 100% of the page width
        float[] columnWidths = {0.4f, 0.6f}; // Set the relative widths of the columns
        table.setWidths(columnWidths);

        String[][] cellTexts = {
            {"Program Name:", curso},
            {"Student's Provisional ID No:", matricula},
            {"Year of Entry:", year},
            {"Duration of Program:", ciclos+" Full Time"},
            {"Annual Fee for the year:", "(Insert Tuition Fee) and Boarding (Insert boarding Fee)"}
        };

		for (int i = 0; i < cellTexts.length; i++) {
			for (int j = 0; j < cellTexts[i].length; j++) {
				String text = cellTexts[i][j];
				Font font = FontFactory.getFont(FontFactory.HELVETICA, 9, (j == 0) ? Font.BOLD : Font.NORMAL, new BaseColor(0, 0, 0));
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
        float trx = 450; // upper-right x
        float tury = 545; // upper-right y

        ColumnText columnText4 = new ColumnText(canvas);
        columnText4.setSimpleColumn(tlx, tly, trx, tury);
        columnText4.addElement(table);
        columnText4.go();

        // Parrafo 2
        String texto3   =    "As a Seventh-day Adventist Church institution, Fulton Adventist University College promotes the academic, "
                        +    "social, spiritual, and physical aspects of life. There are three distinctively unique aspects that you need to be "
                        +    "aware of when pursuing an academic career at Fulton Adventist University College. Firstly, the concept of"
                        +    "Sabbath Rest that reminds us of God as our Creator.  The Sabbath hours begin from sunset on Friday "
                        +    "evening to sunset Saturday evening.  Sabbath observance requires that students make necessary "
                        +    "preparations prior to sunset on Fridays.  Secondly, its emphasis on health and wholeness.  Maintaining "
                        +    "personal integrity is important, not only for our relationship with God but also to avoid the many "
                        +    "consequences poor choices bring.  Therefore, using of marijuana, alcohol, smoking, chewing of betel nut "
                        +    "etc., do not promote the goal of wholeness.  This means that students agree to refrain from the use of such "
                        +    "“recreational” drugs while enrolling at FAUC.  Thirdly, physical labour or work education program that the "
                        +    "students are required to be involved in.  This aspect of life helps train students with new skills which will "
                        +    "prepare them for the work environment after they graduate from FAUC.  More information on this is "
                        +    "available in the Students Handbook available from the Students Services office.";

		Paragraph text3 = new Paragraph(texto3, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK));
		text3.setLeading(12);
		text3.setAlignment(Element.ALIGN_LEFT);

    	float llx4 = 45;  // lower-left x  
    	float lly4 = 100; // lower-left y
    	float urx4 = 550; // upper-right x (adjust width as needed)
    	float ury4 = 465; // upper-right y (adjust height as needed)

		ColumnText columnText5 = new ColumnText(canvas);
		columnText5.setSimpleColumn(llx4, lly4, urx4, ury4);
		columnText5.addElement(text3);
		columnText5.go();

        // Subtitulo 
        Phrase subtitulo = new Phrase("STUDY PERMIT - NON-FIJIAN RESIDENCE: Read this section carefully.", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, subtitulo, 45, 275, 0);

        // Parrafo 3
        String texto4   =    "Fiji Immigration requires that your Study Permit status MUST be secured as soon as you arrive.  You will be "
                        +    "issued either 14 days or one month Visitor's visa on arrival.  This is the time that you need to sort your "
                        +    "study permit at the Dean of Student Services office with Mrs Asenaca Qiolevu immediately.";

		Paragraph text4 = new Paragraph(texto4, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK));
		text4.setLeading(12);
		text4.setAlignment(Element.ALIGN_LEFT);

    	float llx5 = 45;  // lower-left x  
    	float lly5 = 100; // lower-left y
    	float urx5 = 550; // upper-right x (adjust width as needed)
    	float ury5 = 260; // upper-right y (adjust height as needed)

		ColumnText columnText6 = new ColumnText(canvas);
		columnText6.setSimpleColumn(llx5, lly5, urx5, ury5);
		columnText6.addElement(text4);
		columnText6.go();

        // Lista
        Phrase subtitulo2 = new Phrase("You must have the following documents to complete your APPLICATION FOR A PERMIT TO STUDY:", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, subtitulo2, 45, 200, 0);

        String texto5   =    "1.	  Four passport size photos\n"
                        +    "2.	  Your passport that is at least valid after 3 months of your study program\n"
                        +    "3.	  Copy of your Award Letter (if you are sponsored) OR";

		Paragraph lista = new Paragraph(texto5, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK));
		lista.setLeading(14);
		lista.setAlignment(Element.ALIGN_LEFT);

    	float llx6 = 65;  // lower-left x  
    	float lly6 = 100; // lower-left y
    	float urx6 = 550; // upper-right x (adjust width as needed)
    	float ury6 = 180; // upper-right y (adjust height as needed)

		ColumnText columnText7 = new ColumnText(canvas);
		columnText7.setSimpleColumn(llx6, lly6, urx6, ury6);
		columnText7.addElement(lista);
		columnText7.go();

        Phrase waterMark1 = new Phrase("Owned and operated by ", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, waterMark1, 160, 71, 0);

        Phrase pagina = new Phrase("Page 2/.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, pagina, 45, 90, 0);

        //Footer Pagina 1
        Image jpg2 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_footer_fulton.png");
        jpg2.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg2.scaleAbsolute(160, 15);
        jpg2.setAbsolutePosition(240, 70);
        document.add(jpg2);

        // Abre segunda pagina
        document.newPage();

        // Logo 2
        Image jpg3 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_logo_fulton.png");
        jpg3.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg3.scaleAbsolute(190, 68);
        jpg3.setAbsolutePosition(370, 720);
        document.add(jpg3);


        // Lista Pagina 2
        String texto6   =    "4.	  Copy of sponsor/parents/guardian's letter of confirmation of sponsorship.\n      Additionally, every private sponsored student must provide a copy of\n      your parent or guardian's bank statement.\n"
                        +    "5.	  Copy of the Offer Letter\n"
                        +    "6.	  Certified copy of your birth certificate\n"
                        +    "7.	  Certified copy of your marriage certificate (for married students only)\n"
                        +    "8.	  Medical clearance (All STUDENTS or members of the family over 18 years)\n"
                        +    "9.	  Police clearance (ALL students OR MEMBERS OF THE FAMILY over 18 year)\n"
                        +    "10.	Copy of your Passport bio-data page\n"
                        +    "11.	Return airline ticket(s)";

		Paragraph lista2 = new Paragraph(texto6, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK));
		lista2.setLeading(14);
		lista2.setAlignment(Element.ALIGN_LEFT);

    	float llx7 = 65;  // lower-left x  
    	float lly7 = 100; // lower-left y
    	float urx7 = 550; // upper-right x (adjust width as needed)
    	float ury7 = 710; // upper-right y (adjust height as needed)

		ColumnText columnText8 = new ColumnText(canvas);
		columnText8.setSimpleColumn(llx7, lly7, urx7, ury7);
		columnText8.addElement(lista2);
		columnText8.go();

        // Create fonts for normal and bold text
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);

        // Create the main paragraph
        Paragraph texto7 = new Paragraph();

        // Add the regular text
        texto7.add(new Phrase("It is important that all documents above are secured in a folder, to bring with you before you depart your home country. " +
            "Submit these documents to the Director of Students Services office as soon as you arrive in Fiji. Failure to apply for your study permit within the time granted upon arrival will result in you being classified as a prohibited immigrant. " +
            "You will be penalized and receive a fine of $FJD189.00. If you are in Fiji, you must explain the reason for your overstay, pay your fine before the penalty imposed on you can be uplifted. " +
            "If you are overseas, you may only re-enter Fiji once you have paid the penalty fee and an uplift has been approved by the Director of Immigration.\n\n", normalFont));

        // Add the bold subtitle
        texto7.add(new Phrase("ACCOMMODATION\n", boldFont));

        // Add the regular text
        texto7.add(new Phrase("You have been offered on-campus accommodation. This offer is guaranteed and if you wish to take advantage of this opportunity, please contact the Director of Student Services office to confirm your space on email aqiolevu@fulton.ac.fj.\n\n", normalFont));

        // Add the bold subtitle
        texto7.add(new Phrase("FINANCE\n", boldFont));

        // Add the regular text
        texto7.add(new Phrase("We require all applicants who have received their offer to study to make sure that they have paid 60% of your semester 1 fees in advance before you travel. " +
            "All students who have paid the 60% of their fees are required to arrive on campus after "+fechaArribo+" and NOT before this date. " +
            "Contact Accounts@fulton.ac.fj for a copy of your fee invoice.\n\n", normalFont));

        // Add the bold subtitle
        texto7.add(new Phrase("REGISTRATION & ORIENTATION:\n", boldFont));

        // Add the regular text
        texto7.add(new Phrase("Registration is scheduled for "+fechaRegistro+", and Orientation on "+fechaOrientacion+". Classes will commence on "+fechaInicio+". NO student will be allowed to register AFTER "+fechaCierre+".\n\n", normalFont));

        // Add the remaining regular text
        texto7.add(new Phrase("The opportunity to spend your student years in a vibrant and safe environment surrounded by exceptional people provides the foundation for an extraordinary life. " +
            "This offer is only valid for "+year+".\n\n", normalFont));

        texto7.add(new Phrase("The faculty, staff, and students at FAUC hope that you will find your studies here both challenging and rewarding.", normalFont));

        // Set paragraph properties
        texto7.setLeading(12);
        texto7.setAlignment(Element.ALIGN_LEFT);

        // Define the column text
        float llx8 = 45;  // lower-left x  
        float lly8 = 100; // lower-left y
        float urx8 = 550; // upper-right x (adjust width as needed)
        float ury8 = 560; // upper-right y (adjust height as needed)

        ColumnText columnText9 = new ColumnText(canvas);
        columnText9.setSimpleColumn(llx8, lly8, urx8, ury8);
        columnText9.addElement(texto7);
        columnText9.go();

        // Remitente
        Phrase remitente = new Phrase("Sincerely", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, remitente, 45, 185, 0);

        // Firma
        Image jpg4 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_firma_fulton.jpg");
        jpg4.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg4.scaleAbsolute(75, 45);
        jpg4.setAbsolutePosition(45, 130);
        document.add(jpg4);

        Phrase registrador = new Phrase("Neli Manuca", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, registrador, 45, 120, 0);

        Phrase registar = new Phrase("ACADEMIC REGISTRAR", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, registar, 45, 105, 0);

        //Footer Pagina 2
        Phrase waterMark2 = new Phrase("Owned and operated by ", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)));
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, waterMark2, 160, 71, 0);

        Image jpg5 = Image.getInstance(application.getRealPath("/imagenes/")+"/carta_footer_fulton.png");
        jpg5.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg5.scaleAbsolute(160, 15);
        jpg5.setAbsolutePosition(240, 70);
        document.add(jpg5);

    }catch(IOException ioe){
		System.err.println("Error al generar el recibo en PDF: "+ioe.getMessage());
	}

	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
	
	String nombreArchivo  = "file.pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>