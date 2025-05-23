<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import ="java.util.List"%>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

<%@page import="java.io.File"%>

<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.kardex.spring.KrdxCursoCal"%>
<%@page import="aca.util.Fecha"%>

<% 
	String institucion 		= (String) request.getAttribute("institucion");
	String cursoCargaId 	= (String) request.getAttribute("cursoCargaId");
	String cursoId			= (String) request.getAttribute("cursoId");
	String materia			= (String) request.getAttribute("materia");
	String maestro			= (String) request.getAttribute("maestro");
	String nombreFacultad	= (String) request.getAttribute("nombreFacultad");
	String nombreCarrera	= (String) request.getAttribute("nombreCarrera");
	String nombreModalidad	= (String) request.getAttribute("nombreModalidad");
	int numCiclo			= (int) request.getAttribute("ciclo");
	String getCiclo			= (String) request.getAttribute("getCiclo");
	Fecha fecha				= (Fecha) request.getAttribute("fecha");
	
	String carreraId		= (String) request.getAttribute("carreraId");
	String yearName			= (String) request.getAttribute("yearName");

	List<KrdxCursoCal> lisDiferida 				= (List<KrdxCursoCal>) request.getAttribute("lisDiferida");
	HashMap<String, String> cursos 				= (HashMap<String, String>) request.getAttribute("cursos");
	HashMap<String, String> mapAlumnosMateria 	= (HashMap<String, String>) request.getAttribute("mapAlumnosMateria");
	HashMap<String,KrdxCursoAct> mapaNotas 		= (HashMap<String,KrdxCursoAct>) request.getAttribute("mapaNotas");
	
	int posX 	= 0; 
	int posY	= 0;
	
	String dir 		= "";
	String carpeta 	= application.getRealPath("/WEB-INF/pdf/correccion/");
	File fileCarpeta = new File(application.getRealPath("/WEB-INF/pdf/correccion/"));
	if(!fileCarpeta.exists()) fileCarpeta.mkdirs();

	if(request.getParameter("Accion")==null){
		//Document document = new Document(PageSize.A4 ); //Crea un objeto para el documento PDF
		Rectangle rec = new Rectangle(14.0f , 21.0f);
		Document document = new Document(PageSize.LETTER);
		document.setMargins(-30,-30,50,30);
		
		try{		
			if(!new File(carpeta).exists()) new File(carpeta).mkdirs(); 
			dir = carpeta+cursoCargaId+".pdf";
			PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
			document.addAuthor("Sistema Escolar");
	        document.addSubject("Correccion de notas"+materia);
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
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+340, posY+720, 0);
	    	
	    	Phrase servicio = new Phrase( "SERVICIOS ESCOLARES",  FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, servicio, posX+340, posY+700, 0);
		     
		    Phrase contrato = new Phrase( "Corrección de calificación", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(0,0,0)) );
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, contrato, posX+340, posY+680, 0);
		     
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
			
			celda = new PdfPCell(new Phrase(nombreFacultad
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
			ciclo.add(new Phrase("Carrera: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			ciclo.add(new Phrase(nombreCarrera, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));			        
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
			
			Paragraph modalidad = new Paragraph();
			modalidad.add(new Phrase("Modalidad: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			modalidad.add(new Phrase(nombreModalidad, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
			modalidad.add(new Phrase("       Acta: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			modalidad.add(new Phrase(cursoCargaId, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
			modalidad.add(new Phrase("       Semestre: ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			modalidad.add(new Phrase(String.valueOf(numCiclo), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));			    
	        celda = new PdfPCell(modalidad);
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
	        parrafo.add(new Phrase(fecha.getDia(fecha.getHoy())
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
	        parrafo.add(new Phrase(" del mes de "
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
	        parrafo.add(new Phrase(fecha.getMesNombre(fecha.getHoy())
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
	        parrafo.add(new Phrase(" de "
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
	        parrafo.add(new Phrase(yearName
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
	        parrafo.add(new Phrase(" se levantó la presente acta en donde se consignan la(s) calificación(es) obtenidas por los siguientes estudiantes en:"
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
			carrera.add(new Phrase(getCiclo
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));
			carrera.add(new Phrase("   del ciclo escolar: "
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			carrera.add(new Phrase("20"+ cursoCargaId.substring(0,2)+" - "+ "20"+ cursoCargaId.substring(2,4)
	        		, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.UNDERLINE, new BaseColor(r,g,b))));        
	        celda = new PdfPCell(carrera);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(4);
			tab2.addCell(celda);
	
			document.add(tab2);
			
		    //tabla de alumnos con calificacion diferida
		    float a = 10f;
		    float d = 80f;
			PdfPTable tab3 = new PdfPTable(7);
			int tab3Widths[] = {10,30,17,8,5,10,10};
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
			
			celda = new PdfPCell(new Phrase("Materia"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Cal. Ant."
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
			
			celda = new PdfPCell(new Phrase("Tipo Nota"
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
			
			
			for(int i=0;i<lisDiferida.size();i++){
				KrdxCursoCal cal = lisDiferida.get(i);
				
				String nota = "0";
				if(mapaNotas.containsKey(cal.getCodigoPersonal())){
					nota = mapaNotas.get(cal.getCodigoPersonal()).getNota();
				}
				
				String tipoNota = "";
				if(cal.getTipoNota().equals("O")){
					tipoNota = "Ordinario";
				}else{
					tipoNota = "Extraordinario";
				}
				
				String nombreAlumno = "";
				if(mapAlumnosMateria.containsKey(cal.getCodigoPersonal())){
					nombreAlumno = mapAlumnosMateria.get(cal.getCodigoPersonal());
				}
				
				celda = new PdfPCell(new Phrase(cal.getCodigoPersonal()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(nombreAlumno
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(cursos.get(cal.getCodigoPersonal())
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(nota
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
				
				celda = new PdfPCell(new Phrase(tipoNota
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(cal.getFecha()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
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
	}
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = cursoCargaId+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>