<%response.setHeader("Cache-Control","no-cache");response.setHeader("Cache-Control","no-store");response.setHeader("Cache-Control","must-revalidate");response.setHeader("Pragma","no-cache");response.setDateHeader ("Expires", 0);%>
<%@ include file= "../../con_enoc.jsp" %>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="RotProgramacion" scope="page" class="aca.rotaciones.RotProgramacion"/>
<jsp:useBean id="RotHospital" scope="page" class="aca.rotaciones.RotHospital"/>
<jsp:useBean id="RotHospitalEspecialidad" scope="page" class="aca.rotaciones.RotHospitalEspecialidad"/>
<jsp:useBean id="RotInstitucion" scope="page" class="aca.rotaciones.RotInstitucion"/>
<jsp:useBean id="RotEspecialidadUtil" scope="page" class="aca.rotaciones.RotEspecialidadUtil"/>
<jsp:useBean id="RotEspecialidad" scope="page" class="aca.rotaciones.RotEspecialidad"/>
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>

<% 
	File carpeta = new File(application.getRealPath("/WEB-INF/pdf/rotacionesEval/"));
	if(!carpeta.exists()) carpeta.mkdirs();
	else{
		File[] arrFiles = carpeta.listFiles();
		for(File f : arrFiles) f.delete();
	}
	String dir = carpeta+"/todos.pdf";

	Document document = new Document(PageSize.LETTER);
	int posX 	= 0; 
	int posY	= 0;
    int r = 0, g = 0, b = 0;
	PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
	document.addAuthor("Sistema Rotaciones");
	document.addSubject("Programacion todos");
	document.open();

	String [] arrProgramaciones = request.getParameter("Programaciones").split("-");
	boolean presentacion = request.getParameter("Presentacion")!=null?true:false;
	boolean evaluacion = request.getParameter("Evaluacion")!=null?true:false;
	for(String programacionId : arrProgramaciones){
		RotProgramacion.mapeaRegId(conEnoc, programacionId);
		RotHospital.mapeaRegId(conEnoc, RotProgramacion.getHospitalId());
		RotInstitucion.mapeaRegId(conEnoc, RotHospital.getInstitucionId());
		RotHospitalEspecialidad.mapeaRegId(conEnoc, RotProgramacion.getHospitalId(), RotProgramacion.getEspecialidadId());
		AlumPersonal = AlumUtil.mapeaRegId(conEnoc, RotProgramacion.getCodigoPersonal());
		
		HashMap<String, aca.rotaciones.RotEspecialidad> mapaEspecialidades = RotEspecialidadUtil.getMapAll(conEnoc, "");
	
		//Document document = new Document(PageSize.A4 ); //Crea un objeto para el documento PDF
		document.setMargins(20,15,50,30);
		try{
		 	PdfPCell celda = null;
			PdfPTable tInvisible = new PdfPTable(1);
			
			float w = 0;
	    	float s = 0;
			float w2 = 0;
			float s2 = 0;
			PdfPTable t2 = new PdfPTable(2);
			
			String fechaIni = "";
			String mesIni = "";
			String fechaFin = "";
			String mesFin = "";
			
			PdfPTable t3 = new PdfPTable(3);
			
			if(presentacion){
			    PdfContentByte canvas = pdf.getDirectContentUnder();
			    
			    Image txtUm = Image.getInstance(application.getRealPath("/imagenes/")+"/textoRotaciones.jpg");
			    txtUm.setAbsolutePosition(12f, -30f);
			    txtUm.scaleAbsolute(document.getPageSize().getWidth()-25, document.getPageSize().getHeight());
			    document.add(txtUm);
			    
		       	//Tabla Invisible
		    	w = 100f;
		    	s = 0f;
				tInvisible = new PdfPTable(1);
				int tWidths[] = {100};
				tInvisible.setWidths(tWidths);
				tInvisible.setSpacingBefore(s);
				tInvisible.setWidthPercentage(w);
				
				celda = new PdfPCell(new Phrase("\n\n\n\n\n\n"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setBorder(Rectangle.NO_BORDER);
				tInvisible.addCell(celda);
				
				document.add(tInvisible);
				
				boolean tieneJefeEnsenanza = RotHospitalEspecialidad.getContactoPrincipal()!=null&&!RotHospitalEspecialidad.getContactoPrincipal().trim().equals("");
				
				w2 = 88f;
		    	s2 = 45f;
				t2 = new PdfPTable(2);
				int tWidths2[] = {140,tieneJefeEnsenanza?90:60};
				t2.setWidths(tWidths2);
				t2.setSpacingAfter(s2-30);
				t2.setWidthPercentage(w2);				
				
				boolean tieneSecundario = RotHospitalEspecialidad.getContactoSecundario()!=null&&!RotHospitalEspecialidad.getContactoSecundario().equals("null")&&!RotHospitalEspecialidad.getContactoSecundario().trim().equals("");
				
				celda = new PdfPCell(new Phrase(RotHospital.getMedico()
									+"\n"+RotHospital.getPuesto()
									+"\n"+RotHospital.getHospitalNombre()
									+"\n"+RotInstitucion.getInstitucionNombre()
									+"\n"+RotHospital.getCalle()+", "+RotHospital.getColonia()
									+(RotHospital.getMunEdo()!=null?"\n"+RotHospital.getMunEdo()+(RotHospital.getPais()!=null?", "+RotHospital.getPais():""):"")
				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				//celda.setBorderWidth(2f);
			    //celda.setBorderColor(BaseColor.BLACK);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setRowspan(tieneSecundario?5:3);
				t2.addCell(celda);
				
				celda = new PdfPCell(new Phrase(Fecha.getDia(Fecha.getHoy())+" de "+ Fecha.getMesNombre(Fecha.getHoy())+" de "+Fecha.getYear(Fecha.getHoy())
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
				celda.setVerticalAlignment(Element.ALIGN_TOP);
				celda.setFixedHeight(tieneSecundario?40:70);
				//celda.setBorderWidth(2f);
			    //celda.setBorderColor(BaseColor.BLACK);
				celda.setBorder(Rectangle.NO_BORDER);
				t2.addCell(celda);
				
				celda = new PdfPCell(new Phrase(tieneJefeEnsenanza?"AT´N \t "+RotHospitalEspecialidad.getContactoPrincipal():""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
				celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
				//celda.setBorderWidth(2f);
			    //celda.setBorderColor(BaseColor.BLACK);
			    celda.setBorder(Rectangle.NO_BORDER);
				t2.addCell(celda);
				
				celda = new PdfPCell(new Phrase(tieneJefeEnsenanza?RotHospitalEspecialidad.getPuestoPrincipal():""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
				celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
				//celda.setBorderWidth(2f);
			    //celda.setBorderColor(BaseColor.BLACK);
				celda.setBorder(Rectangle.NO_BORDER);
				t2.addCell(celda);
				
				if(tieneSecundario){
					celda = new PdfPCell(new Phrase(RotHospitalEspecialidad.getContactoSecundario()
							, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
					celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
					celda.setBorder(Rectangle.NO_BORDER);
					t2.addCell(celda);
					
					celda = new PdfPCell(new Phrase(RotHospitalEspecialidad.getPuestoSecundario()
							, FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
					celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
					celda.setBorder(Rectangle.NO_BORDER);
					t2.addCell(celda);
				}
				
				celda = new PdfPCell(new Phrase("\n\n\n"+RotHospital.getSaludo(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				t2.addCell(celda);
				
				celda = new PdfPCell(new Phrase("\nEl que suscribe, DR. EBENEZER D. MITA CARRERA, Coordinador de Campos Clínicos de la Carrera de Medicina"
									+" de la "+aca.parametros.Parametros.getInstitucion(conEnoc, "1")+", presenta a usted a "+(AlumPersonal.getSexo().equals("M")?"el":"la")+" alumn"+(AlumPersonal.getSexo().equals("M")?"o":"a")+":"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				t2.addCell(celda);
				
				celda = new PdfPCell(new Phrase("\n"+AlumPersonal.getNombreLegal()
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				t2.addCell(celda);
				
				
				Paragraph parrafo = new Paragraph();
		        parrafo.add(new Phrase("\nIdentificad"+(AlumPersonal.getSexo().equals("M")?"o":"a")+" con el número de matrícula "
		        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
		        parrafo.add(new Phrase(AlumPersonal.getCodigoPersonal()
		        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
		        parrafo.add(new Phrase(" quien de acuerdo a lo autorizado, está programad"+(AlumPersonal.getSexo().equals("M")?"o":"a")+" para realizar"
		        			+" su rotación clínica correspondiente en el hospital a su digno cargo, en las"
		        			+" fechas que a continuación se indican:"
        				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
		        
		        celda = new PdfPCell(parrafo);
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				t2.addCell(celda);
				
				document.add(t2);
				
				t3 = new PdfPTable(3);
				int tWidths3[] = {33,33,33};
				t3.setWidths(tWidths3);
				t3.setSpacingAfter(s2-30);
				t3.setWidthPercentage(88);
				
				celda = new PdfPCell(new Phrase("ROTACIÓN"
				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.UNDERLINE+Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				t3.addCell(celda);
				
				celda = new PdfPCell(new Phrase("FECHA DE INICIO"
				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.UNDERLINE+Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.NO_BORDER);
				t3.addCell(celda);
				
				celda = new PdfPCell(new Phrase("FECHA TERMINO"
				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.UNDERLINE+Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
				celda.setBorder(Rectangle.NO_BORDER);
				t3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(""
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(3);
				t3.addCell(celda);
						
				celda = new PdfPCell(new Phrase(mapaEspecialidades.get(RotProgramacion.getEspecialidadId()).getEspecialidadNombre()
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				t3.addCell(celda);
				
				fechaIni = RotProgramacion.getfInicio().replaceAll("/", "-");
				mesIni = aca.util.Fecha.getMesNombre(Integer.parseInt(fechaIni.substring(3, 5)));
				fechaIni = fechaIni.substring(0, 3)+mesIni+fechaIni.substring(5);
				
				fechaFin = RotProgramacion.getfFinal().replaceAll("/", "-");
				mesFin = aca.util.Fecha.getMesNombre(Integer.parseInt(fechaFin.substring(3, 5)));
				fechaFin = fechaFin.substring(0, 3)+mesFin+fechaFin.substring(5);
				
				celda = new PdfPCell(new Phrase(fechaIni
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.NO_BORDER);
				t3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(fechaFin
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
				celda.setBorder(Rectangle.NO_BORDER);
				t3.addCell(celda);
				
				document.add(t3);
				
				PdfPTable t4 = new PdfPTable(1);
				int tWidths4[] = {100};
				t4.setWidths(tWidths4);
				t4.setWidthPercentage(w2);
				
				celda = new PdfPCell(new Phrase("Agradezco anticipadamente la atención y ayuda que brinda a nuestros estudiantes en"
								+" esta importante etapa en su formación médica."
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				t4.addCell(celda);
				
				celda = new PdfPCell(new Phrase("\n\n\n\nAtentamente,"
								+"\n\n\nDr. Ebenezer D. Mita Carrera"
								+"\nCoordinador de Campos Clínicos"
								+"\nCarrera de Medicina"
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.NO_BORDER);
				t4.addCell(celda);
				
				celda = new PdfPCell(new Phrase("\n\nSe anexan tres hojas de evaluación"
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				t4.addCell(celda);
				
				document.add(t4);
				
				document.newPage();
			}
//-----------------------------------------------------HOJAS DE EVALUACION----------------------------------------------------------------
			if(evaluacion){
				for(int i=0; i<(presentacion?3:1); i++){
					RotEspecialidad.mapeaRegId(conEnoc, RotProgramacion.getEspecialidadId());
					
					document.setMargins(20,15,50,30);
					
				    Image image = Image.getInstance(application.getRealPath("/imagenes/")+"/evaluacion1.jpg");
				    image.scaleAbsolute(520,130);
				    image.setAbsolutePosition(40f, 650f);
				    document.add(image);
				    
			       	//Tabla Invisible
			    	w = 88f;
			    	s = 0f;
					tInvisible = new PdfPTable(1);
					int tWidths5[] = {100};
					tInvisible.setWidths(tWidths5);
					tInvisible.setSpacingBefore(s);
					tInvisible.setWidthPercentage(w);
					
					celda = new PdfPCell(new Phrase("\n\n\n\n\n\n\n\n"
							, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setBorder(Rectangle.NO_BORDER);
					tInvisible.addCell(celda);
					
					document.add(tInvisible);
					
					w2 = 88f;
			    	s2 = 45f;
					t2 = new PdfPTable(2);
					int tWidths6[] = {70,30};
					t2.setWidths(tWidths6);
					t2.setSpacingAfter(s2-30);
					t2.setWidthPercentage(w2);
					
					Paragraph parrafo = new Paragraph();
					
			        parrafo.add(new Phrase("Nombre del alumno: "
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase(AlumPersonal.getNombreLegal()
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase("\nRotación: "
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase(RotEspecialidad.getEspecialidadNombre()
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase("\nHospital: "
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase(RotHospital.getHospitalNombre().replace("Hospital ", "")
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase("\n"+RotInstitucion.getInstitucionNombre()
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase("\n"+RotHospital.getCalle()
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase("\n"+RotHospital.getColonia()
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo.add(new Phrase(RotHospital.getMunEdo()!=null?"\n"+RotHospital.getMunEdo()+", "+RotHospital.getPais():""
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
		
			        celda = new PdfPCell(parrafo);
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					t2.addCell(celda);
					
					Paragraph parrafo2 = new Paragraph();
					
					parrafo2.add(new Phrase("Matrícula: "
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
			        parrafo2.add(new Phrase(AlumPersonal.getCodigoPersonal()
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo2.add(new Phrase("\nClave: "
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
			        parrafo2.add(new Phrase(RotEspecialidad.getPlanId()+"-"+RotEspecialidad.getEspecialidadId()
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        
			        fechaIni = RotProgramacion.getfInicio().replaceAll("/", "-");
					mesIni = aca.util.Fecha.getMesNombre(Integer.parseInt(fechaIni.substring(3, 5))).substring(0,3);
					fechaIni = fechaIni.substring(0, 3)+mesIni+fechaIni.substring(5);
					
					fechaFin = RotProgramacion.getfFinal().replaceAll("/", "-");
					mesFin = aca.util.Fecha.getMesNombre(Integer.parseInt(fechaFin.substring(3, 5))).substring(0,3);
					fechaFin = fechaFin.substring(0, 3)+mesFin+fechaFin.substring(5);
			        
			        parrafo2.add(new Phrase("\nInicio: "
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
			        parrafo2.add(new Phrase(fechaIni
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
			        parrafo2.add(new Phrase("\nTérmino: "
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
			        parrafo2.add(new Phrase(fechaFin
			        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					
					celda = new PdfPCell(parrafo2);
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					t2.addCell(celda);
					
					celda = new PdfPCell(new Phrase("\nEscala de calificación: Nulo = 0 puntos,  Deficiente = 1 punto,"  
											+" Regular = 2 puntos,  Bueno = 3 puntos, \n Muy bueno = 4 puntos,  Excelente = 5 puntos"
							, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setColspan(2);
					t2.addCell(celda);
					
					celda = new PdfPCell(new Phrase("\nNota: Cada ítem debe ser evaluado de acuerdo la escala de calificación "
											+"y estos puntos se colocan en la columna \"Puntos\" de la línea correspondiente; los "
											+"puntos se multiplican por el factor correspondiente y se obtiene la \"Nota\" para cada ítem. "
											+"La suma de todas las notas dará la calificación final, la que será escrita en números y letras. "
											+"En caso de no existir el ítem a evaluar en el servicio, favor de colocar \"NO EVALUABLE\"."
											+"NO SE ACEPTARAN HOJAS CON ENMENDADURAS. Muchas Gracias."
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setColspan(2);
					t2.addCell(celda);
					
					document.add(t2);
					
					t3 = new PdfPTable(4);
					int tWidths7[] = {50,9,9,9};
					t3.setWidths(tWidths7);
					t3.setSpacingAfter(s2-30);
					t3.setWidthPercentage(w2);
					
					celda = new PdfPCell(new Phrase("Descripción del ítem a evaluar"
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("Puntos"
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("Factor"
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("Nota"
					, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("1.	Asiste al total de días programados en su rotación"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("2.	Llega siempre puntual a las actividades que se le programan"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("3. Su apariencia y vestido es el adecuado para el trabajo médico"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("4.	Mantiene una adecuada relación con sus compañeros de trabajo"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("5.	Demuestra siempre una conducta ética"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("6.	Demuestra madurez y estabilidad emocional en sus acciones"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("7.	Demuestra iniciativa en su participación"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("8.	Demuestra dedicación y esmero en la atención de los pacientes"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("2"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("9.	Las historias clínicas realizadas son satisfactorias"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("2"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("10. Sus conocimientos teóricos generales son óptimos"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("11. Sus conocimientos teóricos de la especialidad son óptimos"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("2"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("12. Sus destrezas médicas en general son óptimas"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("2"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("13. Sus destrezas médicas adquiridas en la especialidad son óptimas"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("2"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("14. Reconoce sus limitaciones y muestra preocupación por superarlas"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("15. Demuestra en sus actos y valores principios elevados"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase("1"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("CALIFICACIÓN FINAL ( 0- 100)"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setColspan(3);
					t3.addCell(celda);
					celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					t3.addCell(celda);
					
					celda = new PdfPCell(new Phrase("Calificación final en letras:"
						, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setColspan(4);
					t3.addCell(celda);
					
					document.add(t3);
					
					Image image2 = Image.getInstance(application.getRealPath("/imagenes/")+"/evaluacion2.jpg");
				    image2.scaleAbsolute(480,155);
				    image2.setAbsolutePosition(65f, 15f);
				    document.add(image2);
				    
				    document.newPage();
				}
			}
		}catch(IOException ioe){
			System.err.println("Error al generar la carta de admision en PDF: "+ioe.getMessage());
		}
	}
	
	document.close();
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = "todos.pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);	
%>
<%@ include file= "../../cierra_enoc.jsp" %>
