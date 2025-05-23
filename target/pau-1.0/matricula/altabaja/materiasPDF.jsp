<%@ page import = "java.io.File"%>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@ page import = "aca.carga.spring.CargaAlumno" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "aca.kardex.spring.KrdxCursoAct" %>
<%@ page import = "aca.carga.spring.CargaGrupo" %>
<%@ page import = "aca.plan.spring.MapaCurso" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 

	String codigoAlumno 		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
	String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String bloqueId 			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	
	String fechaHoy				= aca.util.Fecha.getHoy();
	
	CargaAlumno cargaAlumno 	= (CargaAlumno) request.getAttribute("cargaAlumno"); 
	String alumnoNombre 		= (String) request.getAttribute("alumnoNombre");
	String carreraNombre 		= (String) request.getAttribute("carreraNombre");
	String modalidad 			= (String) request.getAttribute("modalidad");
	String ciclo	 			= (String) request.getAttribute("ciclo");
	
	List<KrdxCursoAct> lisActuales 				= (List<KrdxCursoAct>) request.getAttribute("lisActuales");	
	HashMap<String, CargaGrupo> mapaGrupos 		= (HashMap<String, CargaGrupo>) request.getAttribute("mapaGrupos");
	HashMap<String, MapaCurso> mapaCursos 		= (HashMap<String, MapaCurso>) request.getAttribute("mapaCursos");
	
	//Document document = new Document(PageSize.A4 ); //Crea un objeto para el documento PDF
	Rectangle rec = new Rectangle(14.0f , 21.0f);
	Document document = new Document(PageSize.LETTER);
	document.setMargins(-30,-30,50,30);
	String dir = "";
	
	try{
		
		File rutaCarpeta = new File(application.getRealPath("/WEB-INF/pdf/altabaja/"));
		if(!rutaCarpeta.exists()) rutaCarpeta.mkdirs();
		dir = rutaCarpeta+"/"+codigoAlumno+".pdf";
		
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Sistema Academico");
        document.addSubject("");
		document.open();
		
		Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_small.jpg");
	    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	    jpg.scaleAbsolute(90,90);
	    jpg.setAbsolutePosition(50, 660);
	    document.add(jpg);
	    
	    PdfContentByte canvas = pdf.getDirectContentUnder();
		
	    Phrase vice = new Phrase( "VICERRECTORÍA ACADÉMICA", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, vice, 210, 730, 0);
	    
	    Phrase form = new Phrase( "Formulario para Altas y Bajas de Materias", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, form, 229, 700, 0);
    	
    	
    	Phrase nombre = new Phrase( "Nombre:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, nombre, 80, 630, 0);
    	
    	Phrase nombreAlum = new Phrase( alumnoNombre.toUpperCase().trim(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, nombreAlum, 120, 630, 0);
    	
		
    	Phrase fechaN = new Phrase( "Fecha:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fechaN, 475, 630, 0);
    	
    	Phrase fechaAct = new Phrase( fechaHoy, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fechaAct, 510, 630, 0);
    	
    	
    	Phrase carrera = new Phrase( "Carrera:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, carrera, 82, 610, 0);
    	
    	Phrase carreraAlum = new Phrase( carreraNombre.toUpperCase().trim(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, carreraAlum, 120, 610, 0);
    	
    	
    	Phrase tituloMatricula = new Phrase( "No. Matrícula:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, tituloMatricula, 445, 610, 0);
    	
    	Phrase fraseMatricula = new Phrase( codigoAlumno, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseMatricula, 510, 610, 0);    	
    	
    	Phrase tituloModalidad = new Phrase( "Modalidad:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, tituloModalidad, 70, 590, 0);
    	
    	Phrase fraseModalidad = new Phrase( modalidad.toUpperCase().trim(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseModalidad, 120, 590, 0);    	
    	
    	Phrase tituloCiclo = new Phrase( "Semestre:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, tituloCiclo, 460, 590, 0);
    	
    	Phrase fraseCiclo = new Phrase( ciclo, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseCiclo, 510, 590, 0);
    	

		PdfPCell celda = null;
		int r = 0, g = 0, b = 0;
		
    	//Tabla Invisible
    	float w = 80f;
    	float s = 40f;
		PdfPTable t = new PdfPTable(1);
		int tWidths[] = {100};
		t.setWidths(tWidths);
		t.setSpacingBefore(s);
		t.setWidthPercentage(w);    	
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		t.addCell(celda);
		document.add(t);
    	
//Tabla principal----------------------------------------------------------------------------------------------------------------------------
	    float ve = 150f;
    	float qe = 80f;
    	
		PdfPTable tabla = new PdfPTable(8);
		
		int tab2Widths[] = {8, 25, 4, 4, 8, 25, 4, 4};
		tabla.setWidths(tab2Widths);
		tabla.setSpacingBefore(ve);
		tabla.setWidthPercentage(qe);
		
		celda = new PdfPCell(new Phrase("Materias que dará de alta"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(4);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Materias que dará de baja"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(4);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Acta"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Nombre materia"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Gpo."
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Crs."
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);		
		
		celda = new PdfPCell(new Phrase("Acta"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Nombre materia"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Gpo."
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Crs."
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		tabla.addCell(celda);
		
//--------------------------------Llenar Materias--------------------------------------------------------------------------------------
		
		
		List<KrdxCursoAct> lisAltas = new ArrayList();
		List<KrdxCursoAct> lisBajas = new ArrayList();
		
		for(int i=0; i<lisActuales.size(); i++){
	  		KrdxCursoAct tmpAct = lisActuales.get(i);
	  		if(tmpAct.getTipoCalId().equals("M") && tmpAct.getTipo().equals("A")) lisAltas.add(tmpAct);
	  		else if(tmpAct.getTipoCalId().equals("I") && tmpAct.getTipo().equals("B")) lisBajas.add(tmpAct);
		}
				
		int mayor = Math.max(lisAltas.size(), lisBajas.size());

		int cont = 0;
		int creditosAlta = 0;
		int creditosBaja = 0;
		for(int j=0; j<mayor*2; j++){
		  	if(j%2==0){
		  		if(cont<lisAltas.size()){
			  		KrdxCursoAct tmpAct = lisAltas.get(cont);
			  		
			  		// Agrega el nombre de la optativa al nombre del curso
			  		String nombreOptativa 	= "";
			  		String grupoMateria		= "";
			  		if (mapaGrupos.containsKey(tmpAct.getCursoCargaId())){
			  			nombreOptativa = "("+mapaGrupos.get(tmpAct.getCursoCargaId()).getOptativa()+")";
			  			grupoMateria = mapaGrupos.get(tmpAct.getCursoCargaId()).getGrupo();
			  		}		  		
					if (nombreOptativa == null || nombreOptativa.length() < 6){					
						 nombreOptativa	= "";
					}
					
		  			celda = new PdfPCell(new Phrase(tmpAct.getCursoCargaId(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					MapaCurso mapaCurso = new MapaCurso();
					if (mapaCursos.containsKey(tmpAct.getCursoId())){
						mapaCurso = mapaCursos.get(tmpAct.getCursoId());
					}
					
					creditosAlta += Integer.parseInt(mapaCurso.getCreditos());
					
					celda = new PdfPCell(new Phrase(mapaCurso.getNombreCurso()+nombreOptativa, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase(grupoMateria, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase(mapaCurso.getCreditos(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
		  		}else{
		  			celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
		  		}
		  	}
		  	else{
		  		if(cont<lisBajas.size()){
			  		KrdxCursoAct tmpAct = lisBajas.get(cont);
			  		
			  		// Agrega el nombre de la optativa al nombre del curso
			  		String nombreOptativa 	= "";
			  		String grupoMateria 	= "";
			  		if (mapaGrupos.containsKey(tmpAct.getCursoCargaId())){
			  			nombreOptativa = "("+mapaGrupos.get(tmpAct.getCursoCargaId()).getOptativa()+")";
			  			grupoMateria = mapaGrupos.get(tmpAct.getCursoCargaId()).getGrupo();
			  		}			  		
					if (nombreOptativa == null || nombreOptativa.length() < 6){					
						 nombreOptativa	= "";
					}
				  		
		  			celda = new PdfPCell(new Phrase(tmpAct.getCursoCargaId(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					MapaCurso mapaCurso = new MapaCurso();
					if (mapaCursos.containsKey(tmpAct.getCursoId())){
						mapaCurso = mapaCursos.get(tmpAct.getCursoId());
					}
					
					creditosBaja += Integer.parseInt(mapaCurso.getCreditos());
					
					celda = new PdfPCell(new Phrase(mapaCurso.getNombreCurso()+nombreOptativa, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT );
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase(mapaCurso.getCiclo(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase(mapaCurso.getCreditos(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
		  		}
		  		else{
		  			celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
					
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					tabla.addCell(celda);
		  		}
				
				cont++;
	  		}
	  	}
	  	
//------------------------------------------------------------------------------------------------------------------------------------
		
		celda = new PdfPCell(new Phrase("Firma del coordinador"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
		celda.setFixedHeight(50);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(2);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Total Cr:"+ creditosAlta
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(2);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Firma del coordinador"
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(2);
		tabla.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Total Cr:"+ creditosBaja
				, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(2);
		tabla.addCell(celda);
		
		document.add(tabla);
		
//--------------------------------------------------------------------------------------------------------------------------------------

		PdfPTable tabla2 = new PdfPTable(2);
		
		int tab2Widths2[] = {50, 50};
		tabla2.setWidths(tab2Widths2);
		tabla2.setSpacingBefore(30f);
		tabla2.setWidthPercentage(80f);		
		
		celda = new PdfPCell(new Phrase("Finanzas Estudiantiles _____________________________________"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Servicios Escolares _____________________________________"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Nota: Favor de imprimir 4 copias"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tabla2.addCell(celda);
    	
		document.add(tabla2);
    	
	}catch(IOException ioe){
		System.err.println("Error al generar el formato de calificaciones diferidas en PDF: "+ioe.getMessage());
	}
	
	document.close();  
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = codigoAlumno+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>