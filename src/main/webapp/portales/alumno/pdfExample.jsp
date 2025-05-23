<%@page import="org.w3c.dom.traversal.DocumentTraversal"%>
<%@page import = "java.awt.Color" %>
<%@page import = "java.io.FileOutputStream" %>
<%@page import = "java.io.IOException" %>
<%@page import = "com.itextpdf.text.*" %>
<%@page import = "com.itextpdf.text.pdf.*" %>
<%@page import = "com.itextpdf.text.pdf.events.*" %>

<%@page import = "java.util.List"%>
<%@page import = "java.util.HashMap"%>
<%-- <%@page import="aca.plan.spring.MapaFedCurso"%> --%>
<%-- <%@page import="aca.plan.spring.MapaFedBiblio"%> --%>
<%-- <%@page import="aca.plan.spring.MapaFedTema"%> --%>
<%-- <%@page import="aca.plan.spring.MapaFedUnidad"%> --%>
<%-- <%@page import="aca.plan.spring.MapaFedPlan"%> --%>


<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String esAdmin 	   		= (String) session.getAttribute("esAdmin");
	String cursoId			= request.getParameter("CursoId")==null?"0": request.getParameter("CursoId");
	String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	
// 	MapaFedCurso curso 	    = (MapaFedCurso)request.getAttribute("curso");
// 	MapaFedPlan plan 	    = (MapaFedPlan)request.getAttribute("plan");	
	String planNombre 	    = (String)request.getAttribute("planNombre");	

	String institucion		= "UNIVERSIDAD DE MONTEMORELOS";
	String usuario			= "Curso"+cursoId;
	
//------PDF----->
		
	int posX 	= 0; 
	int posY	= 0;
	//int salto 	= 20;
	
	//Crea un objeto para el documento PDF
	Document document = new Document(PageSize.LETTER ); 
	document.setMargins(-30,-30,50,20);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/")+"/"+usuario+".pdf";	
	try{
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Sistema Escolar");
           
        
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
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+360, posY+700, 0);
    	
    	Phrase direccion = new Phrase( "", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, direccion, posX+360, posY+700, 0);

    	Phrase EncabezadoDoc = new Phrase(planNombre, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, EncabezadoDoc, posX+360, posY+680, 0);

    	//Tabla Invisible

    	PdfPTable tablaInvisible = new PdfPTable(1);
   		int tablaInvisibleWidths[] = {100};
   		tablaInvisible.setWidths(tablaInvisibleWidths);
   		tablaInvisible.setSpacingBefore(40f);   		
   		   	
    	celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
    	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celda.setBorder(Rectangle.NO_BORDER);
    	celda.setColspan(0);
    	tablaInvisible.addCell(celda);
    	document.add(tablaInvisible);	
    	document.add(tablaInvisible);
    	document.add(new Phrase(" "));   
		
		PdfPTable tabla1 = new PdfPTable(1);
		int tabla1Widths[] = {100};
		tabla1.setWidths(tabla1Widths);
		tabla1.setSpacingBefore(20f);
		tabla1.setWidthPercentage(70f);
	//	tabla1.setHorizontalAlignment(40);
		
		celda = new PdfPCell(new Phrase("DENOMINACIÓN DE LA ASIGNATURA O UNIDAD DE APRENDIZAJE ", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(r,g,b) )));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);	
		celda.setBackgroundColor(new BaseColor(230,230,230));		
		tabla1.addCell(celda);
		String fraseMateria = "HA :"+curso.getHa()+"  HI:"+curso.getHi()+"  CRÉDITOS:"+curso.getCreditos()+"  SERIACIÓN :"+curso.getSeriacion();
		celda = new PdfPCell(new Phrase(curso.getNombre()+"\n\n"+fraseMateria, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);		
		tabla1.addCell(celda);
		
		document.add(tabla1);
				
		PdfPTable tabla2 = new PdfPTable(3);
		tabla2.setWidthPercentage(70);
		int tabla2Widths[] = {44,22,44};	
		tabla2.setWidths(tabla2Widths);
		tabla2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		tabla2.setSpacingBefore(10f);
		
		celda = new PdfPCell(new Phrase(curso.getCiclo(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);			
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(curso.getClave(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);
		
		tabla2.addCell(celda);
		document.add(tabla2);
		
		PdfPTable tabla3 = new PdfPTable(3);
		tabla3.setWidthPercentage(70);
		int tabla3Widths[] = {44,22,44};	
		tabla3.setWidths(tabla3Widths);
		tabla3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		
		celda = new PdfPCell(new Phrase("SEMESTRE", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);				
		tabla3.addCell(celda);
	
		celda = new PdfPCell(new Phrase(" "));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(0);			
		tabla3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CLAVE DE LA ASIGNATURA", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(0);			
		tabla3.addCell(celda);
		
		document.add(tabla3);
		
		
		PdfPTable tabla4 = new PdfPTable(1);
		tabla4.setWidthPercentage(70f);
		int tabla4Widths[] = {100};	
		tabla4.setWidths(tabla4Widths);
		tabla4.setSpacingBefore(10f);
		tabla4.setSpacingAfter(10f);
		celda = new PdfPCell(new Phrase("FINES DE APRENDIZAJE O FORMACIÓN\n(Competencias del Perfil de Egresos que atiende la asignatura)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.getWidth();
		celda.getRowspan();	
		tabla4.addCell(celda);		
		
		celda = new PdfPCell(new Phrase(curso.getCompetencia(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(4);			
		tabla4.addCell(celda);
		document.add(tabla4);
		
		
		PdfPTable tabla5 = new PdfPTable(1);
		tabla5.setWidthPercentage(70f);
		int tabla5Widths[] = {100};	
		tabla5.setWidths(tabla5Widths);
		tabla5.setSpacingBefore(5f);
		tabla5.setSpacingAfter(10f);
		celda = new PdfPCell(new Phrase("CONTENIDO TEMÁTICO", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		tabla5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(curso.getContenido(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
		celda.setBorder(Rectangle.BOX);
		tabla5.addCell(celda);
	
		document.add(tabla5);
		
		
		PdfPTable tabla6 = new PdfPTable(1);
		tabla6.setWidthPercentage(70f);
		int tabla6Widths[] = {100};	
		tabla6.setWidths(tabla6Widths);
		tabla6.setSpacingBefore(10f);
		tabla6.setSpacingAfter(10f);
		celda = new PdfPCell(new Phrase("REFERENCIAS BIBLIOGRÁFICAS (últimos 5 años)", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		tabla6.addCell(celda);
     
		celda = new PdfPCell(new Phrase(curso.getBibliografia(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
		celda.setBorder(Rectangle.BOX);
		tabla6.addCell(celda);
		tabla6.setKeepTogether(true);	
		document.add(tabla6);
		
		
		PdfPTable tabla7 = new PdfPTable(1);
		tabla7.setWidthPercentage(70f);
		int tabla7Widths[] = {100};	
		tabla7.setWidths(tabla7Widths);
		tabla7.setSpacingBefore(5f);
		tabla7.setSpacingAfter(10f);
		celda = new PdfPCell(new Phrase("ACTIVIDADES DE APRENDIZAJE BAJO CONDUCCIÓN DE UN ACADÉMICO", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		tabla7.addCell(celda);
		celda = new PdfPCell(new Phrase(curso.getActGuiadas(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
		celda.setBorder(Rectangle.BOX);
		tabla7.addCell(celda);
		tabla7.setKeepTogether(true);
		document.add(tabla7);
	
		
		PdfPTable tabla8 = new PdfPTable(1);
		tabla8.setWidthPercentage(70f);
		int tabla8Widths[] = {100};	
		tabla8.setSpacingBefore(5f);
		tabla8.setSpacingAfter(10f);
		tabla8.setWidths(tabla8Widths);
		celda = new PdfPCell(new Phrase("ACTIVIDADES DE APRENDIZAJE INDEPENDIENTES", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		tabla8.addCell(celda);
		celda = new PdfPCell(new Phrase(curso.getActIndependientes(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
		celda.setBorder(Rectangle.BOX);
		tabla8.addCell(celda);
		tabla8.setKeepTogether(true); 
		
		document.add(tabla8);
		
		
		
		PdfPTable tabla9 = new PdfPTable(1);
		tabla9.setWidthPercentage(70f);
		int tabla9Widths[] = {100};	
		tabla9.setWidths(tabla9Widths);
		tabla9.setSpacingBefore(5f);
		tabla9.setSpacingAfter(10f);
		celda = new PdfPCell(new Phrase("CRITERIOS DE EVALUACIÓN", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		tabla9.addCell(celda);
		celda = new PdfPCell(new Phrase(curso.getCriterios(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
		celda.setBorder(Rectangle.BOX);
		tabla9.addCell(celda);
		document.add(tabla9);
		tabla9.setKeepTogether(true);
		
		
	 if(plan.getEnLinea().equals("S")){
		PdfPTable tabla10 = new PdfPTable(1);
		tabla10.setWidthPercentage(70f);
		int tabla10Widths[] = {100};	
		tabla9.setWidths(tabla10Widths);
		tabla9.setSpacingBefore(5f);
		tabla9.setSpacingAfter(10f);
		celda = new PdfPCell(new Phrase("MODALIDADES TECNOLÓGICA E INFORMÁTICAS", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		tabla10.addCell(celda);
		celda = new PdfPCell(new Phrase(curso.getTecnologias(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
		celda.setBorder(Rectangle.BOX);
		tabla10.addCell(celda);
		document.add(tabla10);
		tabla10.setKeepTogether(true);
		
	}
		
		
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