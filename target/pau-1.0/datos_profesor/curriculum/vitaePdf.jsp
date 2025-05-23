<%@page import="java.awt.Color" %>
<%@page import="java.io.IOException" %>
<%@page import="java.io.File" %>
<%@page import="java.io.FileOutputStream" %>
<%@page import="com.itextpdf.text.*" %>
<%@page import="com.itextpdf.text.pdf.*" %>
<%@page import="com.itextpdf.text.pdf.events.*" %>

<%@page import="aca.emp.spring.EmpCurriculum"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal 			= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");

	EmpCurriculum empCurriculum		= (EmpCurriculum) request.getAttribute("empCurriculum");
	String empleadoNombre 			= (String) request.getAttribute("empleadoNombre");	
	String nacionalidad 			= (String) request.getAttribute("nacionalidad");
	byte[] fotoByte					= (byte[]) request.getAttribute("fotoByte");
	
	Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
	document.setMargins(30,30,60,20);
	String dir = application.getRealPath("/datos_profesor/curriculum/")+java.io.File.separator+"curriculum.pdf";
	
	try{
		
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Direccion de Sistemas");
        document.addSubject("Curriculum de "+codigoPersonal);
        
        document.open();
        
        //---------------- Imagenes --------------------------------        
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_letra.png");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(443, 63);
        jpg.setAbsolutePosition(30, 715);
        document.add(jpg);
         
        jpg = null;			
		jpg = Image.getInstance(fotoByte);		
		float ancho 	= jpg.getWidth();
		float alto		= jpg.getHeight();
		float ajuste 	= (ancho/alto) * 95f;
		
		jpg.scaleAbsolute(ajuste, 95f);		
		jpg.setAbsolutePosition(500, 650);
		document.add(jpg);
        
        //----------------------------------------------------------
        
        PdfPTable cuerpo = new PdfPTable(1);
        int cuerpoWidths[] = {100};
        cuerpo.setWidths(cuerpoWidths);
        //cuerpo.setTotalWidth(document.getPageSize().width() - 60);
        
        PdfPCell cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cuerpo.addCell(cell);
        
        cell = new PdfPCell(new Phrase("CURRICULUM VITAE", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cuerpo.addCell(cell);
        
        cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cuerpo.addCell(cell);
        
        document.add(cuerpo);
        
        PdfPTable datosPersonalesTit = new PdfPTable(1);
        int datosPersonalesTitWidths[] = {100};
        datosPersonalesTit.setWidths(datosPersonalesTitWidths);
        //datosPersonalesTit.KEEPTOGETHER = true;
        
        cell = new PdfPCell(new Phrase("DATOS PERSONALES", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonalesTit.addCell(cell);
        
//---------- Tabla de datos personales -----------------

        PdfPTable datosPersonales = new PdfPTable(1);
        int datosPersonalesWidths[] = {100};
        datosPersonales.setWidths(datosPersonalesWidths);
        datosPersonales.setHeaderRows(1);
        
        cell = new PdfPCell(new Phrase("Nombre completo:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.TOP);
        datosPersonales.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\t\t"+empleadoNombre+"\n",
        		FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Fecha y lugar de Nacimiento:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\t\t"+empCurriculum.getFNacimiento(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\t\t"+empCurriculum.getLugarNac()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
       
        cell = new PdfPCell(new Phrase("Nacionalidad:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\t\t"+nacionalidad+"\n"
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Título profesional (último grado obtenido):", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
       
        cell = new PdfPCell(new Phrase("\t\t"+empCurriculum.getTProfesional()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        datosPersonales.addCell(cell);
        
        
        //---------- Termina tabla de datos personales ---------
        
        cell = new PdfPCell(datosPersonales);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.RECTANGLE);
        datosPersonalesTit.addCell(cell);
        
        document.add(datosPersonalesTit);
        //--------------------------------------------
        
        PdfPTable formacionAcademicaTit = new PdfPTable(1);
        int formacionAcademicaTitWidths[] = {100};
        formacionAcademicaTit.setWidths(formacionAcademicaTitWidths);
        //formacionAcademicaTit.KEEPTOGETHER = true;
        
        cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        formacionAcademicaTit.addCell(cell);
     
        cell = new PdfPCell(new Phrase("FORMACIÓN ACADÉMICA", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOTTOM);
        formacionAcademicaTit.addCell(cell);
        
		//---------- Tabla de formacion academica -----------------
        
        PdfPTable formacionAcademica = new PdfPTable(1);
        int formacionAcademicaWidths[] = {100};
        formacionAcademica.setWidths(formacionAcademicaWidths);
        
        cell = new PdfPCell(new Phrase("Título universitario (licenciatura):", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        formacionAcademica.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\t\t"+(empCurriculum.getFechaLic()!=null?empCurriculum.getFechaLic():" ")+" "+empCurriculum.getTUniversitario()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        formacionAcademica.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Grado de posgrado:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        formacionAcademica.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\t\t"+(empCurriculum.getFechaMae()!=null?empCurriculum.getFechaMae():"")+" "+empCurriculum.getGPosgrado()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        formacionAcademica.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Grado de doctorado:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        formacionAcademica.addCell(cell);        
		String fechaDoc 	= empCurriculum.getFechaDoc()!=null?empCurriculum.getFechaDoc():"";
		String tituloDoc 	= empCurriculum.gettDoctorado()!=null?empCurriculum.gettDoctorado()+"\n":"-";
        cell = new PdfPCell(new Phrase("\t\t"+fechaDoc+" "+tituloDoc+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        formacionAcademica.addCell(cell);
        
        //---------- Termina tabla de datos personales ---------
        
        cell = new PdfPCell(formacionAcademica);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.RECTANGLE);
        formacionAcademicaTit.addCell(cell);
        
        document.add(formacionAcademicaTit);
        
        //------------------------------------------------
        
        PdfPTable experienciaProfesionalTit = new PdfPTable(1);
        int experienciaProfesionalTitWidths[] = {100};
        experienciaProfesionalTit.setWidths(experienciaProfesionalTitWidths);
        //experienciaProfesionalTit.KEEPTOGETHER = true;
       
        cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        experienciaProfesionalTit.addCell(cell);
        
        cell = new PdfPCell(new Phrase("EXPERIENCIA PROFESIONAL", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOTTOM);
        experienciaProfesionalTit.addCell(cell);
        
        document.add(experienciaProfesionalTit);
        
        
      //---------- Tabla de experiencia docente -----------------
        
        PdfPTable tablaDocente = new PdfPTable(2);
        int tablaDocenteWidths[] = {1,99};
        tablaDocente.setWidths(tablaDocenteWidths);
/*        
        cell = new PdfPCell(new Phrase("Responsabilidad anterior:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        
        respAnt.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\t\t"+(empCurriculum.getRespAnterior()!=null?empCurriculum.getRespAnterior():"-")+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        respAnt.addCell(cell);
*/        
        cell = new PdfPCell(new Phrase("Experiencia docente:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        tablaDocente.addCell(cell);
        
        cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        tablaDocente.addCell(cell);
        
        //cell = new PdfPCell(new Phrase(empCurriculum.getExpDocente().replaceAll("-", "\t-")+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));        
        cell = new PdfPCell(new Phrase(empCurriculum.getExpDocente().replaceAll("-", "")+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        tablaDocente.addCell(cell);
        
        document.add(tablaDocente);
        
//---------- Tabla de experiencia profesional -----------------
        
        PdfPTable tablaProfesional = new PdfPTable(1);
        int tablaProfesionalWidths[] = {100};
        tablaProfesional.setWidths(tablaProfesionalWidths);
        tablaProfesional.setSplitLate(false);
        //experienciaProfesionalTit.KEEPTOGETHER = true;
        
        cell = new PdfPCell(new Phrase("Experiencia profesional:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tablaProfesional.addCell(cell);
        
        cell = new PdfPCell(new Phrase(empCurriculum.getRespActual()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tablaProfesional.addCell(cell);
        
        document.add(tablaProfesional);
        
        PdfPTable firma = new PdfPTable(3);

        cell = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(50);
        firma.addCell(cell);
        cell = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setPaddingTop(50);
        firma.addCell(cell);
        cell = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(50);
        firma.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Firma", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        tablaDocente.addCell(cell);
        firma.addCell(cell);
        
        document.add(firma);
		
	}catch(IOException ioe) {
		System.err.println("Error kardex en PDF: "+ioe.getMessage());
	}

	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = codigoPersonal+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>