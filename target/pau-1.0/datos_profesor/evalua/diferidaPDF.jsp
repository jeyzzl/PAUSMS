<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>`

<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="CalU" scope="page" class="aca.kardex.KrdxCursoCalUtil"/>
<% 

	String institucion 		= (String) session.getAttribute("institucion");
	String cursoCargaId 	= request.getParameter("CursoCargaId");
	String cursoId			= request.getParameter("CursoId");
	String materia			= request.getParameter("Materia");
	String maestro			= request.getParameter("Maestro");
	
	String carreraOrigen	= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cursoCargaId);
	String carreraId		= aca.plan.PlanUtil.getCarreraId(conEnoc,carreraOrigen.substring(0,8));
	
	String strFecha			= aca.util.Fecha.getHoy();
	String yearName			= aca.util.NumberToLetter.convertirLetras(Integer.parseInt(fecha.getYear(strFecha))).trim();
	
	String primer			= cursoCargaId.substring(0,2);
	String segundo			= cursoCargaId.substring(2,4);
	
	ArrayList lisDiferida	= CalU.getListDiferidas(conEnoc, cursoCargaId,"D", "ORDER BY CODIGO_PERSONAL");
	
	int posX 	= 0; 
	int posY	= 0;
	

	//Document document = new Document(PageSize.A4 ); //Crea un objeto para el documento PDF
	Rectangle rec = new Rectangle(14.0f , 21.0f);
	Document document = new Document(PageSize.LETTER);
	document.setMargins(-30,-30,50,30);
	
	File fileCarpeta = new File(application.getRealPath("/WEB-INF/pdf/diferida/"));
	if(!fileCarpeta.exists()) fileCarpeta.mkdirs();
	
	String dir = application.getRealPath("/WEB-INF/pdf/diferida/")+cursoCargaId+".pdf";
	
	try{		
		
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Sistema Escolar");
        document.addSubject("Calificacion Diferida"+materia);
		document.open();
		
		Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_small.jpg");
	    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	    jpg.scaleAbsolute(90,90);
	    jpg.setAbsolutePosition(70, 660);
	    document.add(jpg);
	    
	    int r = 0, g = 0, b = 0;
	    
	    PdfPCell celda = null;
	     
	    PdfContentByte canvas = pdf.getDirectContentUnder();
	     
	    Phrase uni = new Phrase( institucion.toUpperCase(),  FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, uni, posX+300, posY+700, 0);
	     
	    Phrase contrato = new Phrase( "Contrato para calificación Diferida", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, contrato, posX+350, posY+680, 0);
	     
       	//Tabla Invisible

    	float w = 80f;
    	float s = 400f;
		PdfPTable t = new PdfPTable(1);
		int tWidths[] = {100};
		t.setWidths(tWidths);
		t.setSpacingBefore(s);
		t.setWidthPercentage(w);    	
    	
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(12);
		t.addCell(celda);
		
		document.add(t);

	    
	    //tabla de facultad y carrera
	    float ve = 80f;
    	float qe = 80f;
		PdfPTable tab2 = new PdfPTable(4);
		int tab2Widths[] = {5,30,10,10};
		tab2.setWidths(tab2Widths);
		tab2.setSpacingBefore(ve);
		tab2.setWidthPercentage(qe);
		 
		celda = new PdfPCell(new Phrase("Facultad: "
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc,carreraId))
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(3);
		tab2.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
		Paragraph ciclo = new Paragraph();
		ciclo.add(new Phrase("Carrera: "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		ciclo.add(new Phrase(aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,carreraId)
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
		ciclo.add(new Phrase("       Semestre: "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		ciclo.add(new Phrase(String.valueOf(aca.plan.CursoUtil.getCiclo(conEnoc,cursoId))
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));        
        celda = new PdfPCell(ciclo);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
        Paragraph parrafo = new Paragraph();
        parrafo.add(new Phrase("En la Ciudad de Montemorelos, Nuevo León, México, siendo los "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(fecha.getDia(fecha.getHoy())+" días"
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(" del mes de "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(fecha.getMesNombre(fecha.getHoy())
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(" de "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(yearName
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(" se levantó la presente acta en donde de solicita aplazar"+
        		" la entrega de calificaciones de la siguiente materia a la fecha mencionada.\nSi el estudiante no cumple con su compromiso"+
        		" recibirá la calificación aquí asentada. Una vez cumplidos los requisitos de la materia, la coordinación levantará un" +
        		" acta de calificación ordinaria en la que se consignará la calificación correspondiente."
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 12f, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		Paragraph mat = new Paragraph();
        mat.add(new Phrase("       Materia: "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
        mat.add(new Phrase(materia
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
                celda = new PdfPCell(mat);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(3);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		Paragraph profesor = new Paragraph();
		profesor.add(new Phrase("Profesor(a): "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		profesor.add(new Phrase(maestro
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
        celda = new PdfPCell(profesor);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(3);
		tab2.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
		Paragraph carrera = new Paragraph();
		carrera.add(new Phrase("y que corresponden al semestre: "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		carrera.add(new Phrase(aca.carga.CargaUtil.getCiclo(conEnoc,cursoCargaId.substring(0,6))
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
		carrera.add(new Phrase("       del ciclo escolar: "
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		carrera.add(new Phrase("20"+primer+"-"+"20"+segundo
        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));        
        celda = new PdfPCell(carrera);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);

		document.add(tab2);
		
	    //tabla de alumnos con calificacion diferida
	    float a = 10f;
	    float d = 70f;
		PdfPTable tab3 = new PdfPTable(6);
		int tab3Widths[] = {10,30,10,10,10,20};
		tab3.setWidths(tab3Widths);
		tab3.setSpacingBefore(a);
		tab3.setWidthPercentage(d);
		
		
		celda = new PdfPCell(new Phrase("No. Mat."
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(1);
		tab3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Nombre del Alumno"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(1);
		tab3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Fecha"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(1);
		tab3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TipoCal"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(1);
		tab3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Calif."
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(1);
		tab3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Firma Alumno"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(1);
		tab3.addCell(celda);
		
		for(int i=0;i<lisDiferida.size();i++){
			aca.kardex.KrdxCursoCal cal = (aca.kardex.KrdxCursoCal) lisDiferida.get(i);
			
			celda = new PdfPCell(new Phrase(cal.getCodigoPersonal()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(aca.alumno.AlumUtil.getNombreAlumno(conEnoc,cal.getCodigoPersonal(),"NOMBRE")
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(cal.getFechaFinal()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase( aca.catalogo.TipoCalUtil.getNombreCorto(conEnoc,cal.getTipoCalId())
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(cal.getNota()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(""
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			tab3.addCell(celda);
		}
		
		document.add(tab3);
		
		//tabla de firmas
	    float x = 15f;
	    float z = 60f;
		PdfPTable tab4 = new PdfPTable(2);
		int tab4Widths[] = {50,50};
		tab4.setWidths(tab4Widths);
		tab4.setSpacingBefore(x);
		tab4.setWidthPercentage(z);
		
		celda = new PdfPCell(new Phrase("______________________________"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("______________________________"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Firma del Coordinador"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab4.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("Firma del Docente"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab4.addCell(celda);

		document.add(tab4);
		
        
	}catch(IOException ioe){
		System.err.println("Error al generar el formato de calificaciones diferidas en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = cursoCargaId+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);	
%>
<%@ include file= "../../cierra_enoc.jsp" %>
