2<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="GrupoPlanU" scope="page" class="aca.carga.CargaGrupoPlanUtil"/>
<jsp:useBean id="Materia" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="PreU" scope="page" class="aca.plan.PrerrequisitoUtil"/>
<jsp:useBean id="GrupoPlan" scope="page" class="aca.carga.CargaGrupoPlan"/>
<jsp:useBean id="EjeU" scope="page" class="aca.carga.CargaGrupoEjeUtil"/>
<jsp:useBean id="CompU" scope="page" class="aca.carga.CargaGrupoCompetenciaUtil"/>
<jsp:useBean id="BiblioU" scope="page" class="aca.carga.CargaGrupoBiblioUtil"/>
<jsp:useBean id="UnidadCompU" scope="page" class="aca.carga.CargaUnidadCompUtil"/>
<jsp:useBean id="TemaU" scope="page" class="aca.carga.CargaUnidadTemaUtil"/>
<jsp:useBean id="ActividadU" scope="page" class="aca.carga.CargaUnidadActividadUtil"/>
<jsp:useBean id="CriterioU" scope="page" class="aca.carga.CargaUnidadCriterioUtil"/>
<jsp:useBean id="InstrumentoU" scope="page" class="aca.carga.CargaUnidadInstrumentoUtil"/>
<jsp:useBean id="PlanEvalU" scope="page" class="aca.carga.CargaPlanEvalUtil"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@page import="aca.plan.MapaNuevoActividad"%>

<jsp:useBean id="UnidadU" scope="page" class="aca.carga.CargaUnidadUtil"/>
<%
	String cursoCargaId = (String) session.getAttribute("CursoCargaId"); 
	String maestro 	    = (String) session.getAttribute("Maestro");
	String materia 	    = (String) session.getAttribute("Materia");
	
	
	int paginaAnterior	= 0;
	GrupoPlan.mapeaRegId(conEnoc, cursoCargaId);
	
	String cargaId      = aca.carga.CargaGrupoUtil.getCargaId(conEnoc, cursoCargaId);
	String Periodo		= aca.carga.CargaUtil.getPeriodo(conEnoc, cargaId);
	
	String primer 		= Periodo.substring(0,2);
	String segundo      = Periodo.substring(2,4);
		
	String carreraId 		= aca.carga.CargaGrupoUtil.getCarreraId(conEnoc, cursoCargaId); 
	String carreraNombre	= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraId);
	String facultadId 		= aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, carreraId);
	String facultadNombre	=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId);
	String institucion 		= (String)session.getAttribute("institucion");
	
	String cursoId 		= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cursoCargaId);
	String version 		= aca.plan.MapaNuevoCursoUtil.getMaxVersionCurso(conEnoc, cursoId);	
	String nuevoCursoId	= aca.plan.MapaNuevoCursoUtil.getNuevoCursoId(conEnoc, cursoId, version);	
	int TotalHras 		= aca.plan.MapaNuevoCursoUtil.getHorasTotales(conEnoc, Integer.parseInt(nuevoCursoId));
	
	Materia = MapaCursoU.mapeaRegId(conEnoc, cursoId);
	
	ArrayList<aca.plan.MapaCursoPre> lisPre 					= PreU.getListPrerrequisioMateria(conEnoc, cursoId ,"ORDER BY CURSO_ID");
	ArrayList<aca.carga.CargaGrupoEje> lisEjes   				= EjeU.getListEjes(conEnoc, cursoCargaId, "ORDER BY EJE_ID");
	ArrayList<aca.carga.CargaGrupoCompetencia> lisComp 			= CompU.getListCompetencias(conEnoc, cursoCargaId, "ORDER BY COMPETENCIA_ID");
	ArrayList<aca.carga.CargaUnidad> lisUnidad      			= UnidadU.getListUnidad(conEnoc, cursoCargaId, "ORDER BY ORDEN");
	ArrayList<aca.carga.CargaUnidadComp> lisCompetencias		= UnidadCompU.getListCompetencias(conEnoc, cursoCargaId, "ORDER BY COMPETENCIA_ID");
	ArrayList<aca.carga.CargaUnidadTema> lisTemas        		= TemaU.getListTema(conEnoc, cursoCargaId, "ORDER BY ORDEN");
	ArrayList<aca.carga.CargaUnidadActividad> lisActividades	= ActividadU.getListActividades(conEnoc, cursoCargaId, "ORDER BY ACTIVIDAD_ID");
	ArrayList<aca.carga.CargaUnidadCriterio> lisCriterios   	= CriterioU.getListCriterio(conEnoc, cursoCargaId, "ORDER BY CRITERIO_ID");
	ArrayList<aca.carga.CargaUnidadInstrumento> lisInstrumentos = InstrumentoU.getListInstrumento(conEnoc, cursoCargaId, "ORDER BY INSTRUMENTO_ID");
	ArrayList<aca.carga.CargaPlanEval> listEvaluaciones 		= PlanEvalU.getListEvaluaciones(conEnoc, cursoCargaId, "ORDER BY EVALUACION_ID");
	ArrayList<aca.carga.CargaGrupoBiblio> listBiblio 			= BiblioU.getListBiblio(conEnoc, cursoCargaId, " ORDER BY ORDEN");	
	
	String planId			= request.getParameter("planId");
	String versionId		= request.getParameter("versionId");
	String tmpcursoId			= request.getParameter("cursoId");
	mapaNuevoCurso.mapeaRegId(conEnoc, planId, versionId, tmpcursoId);
	
	//------PDF----->
	String codigoEmpleado = (String) session.getAttribute("codigoEmpleado");	
	int posX 	= 0; 
	int posY	= 0;
	//int salto 	= 20; 
	Document document = new Document(PageSize.LETTER ); //Crea un objeto para el documento PDF
	document.setMargins(-30,-30,50,30);
	
	String rutaCarpeta 	= application.getRealPath("/WEB-INF/pdf/planCurso/");
	String dir 			= application.getRealPath("/WEB-INF/pdf/planCurso/")+"/"+cursoCargaId+".pdf";
	
	try{		
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		PdfWriter pdf 	= PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Academico");
        document.addSubject("Plan de Curso"+codigoEmpleado);
        
        /******* Clase para sobreescribir el metodo OnStartPage *******/
		final class PageFooter extends PdfPageEventForwarder {			 
        	private String materia;
			
        	public PageFooter(){   		
	        	 
	        }
        	
	        public PageFooter(String materia){    		
	        	 this.materia  = materia;
	        }
	        
	        public void setMateria(String materia){
        		this.materia = materia;
        	}
	        	
	        /**
	        * @see com.lowagie.text.pdf.PdfPageEventHelper#onOpenDocument(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
	        */
	        public void onStartPage(PdfWriter writer, Document document) {
	        	try {
	        		PdfContentByte canvas = writer.getDirectContentUnder();
	            	// Imprimir el pie de Pagina
	        		Phrase linea = new Phrase( "_______________________________________________________________", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, linea,300, 30, 0);
	        		
	        		Phrase lineaAbajo = new Phrase( "______________________________________________________________________________________________________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, lineaAbajo,300, 27, 0);
	        		
	        		Phrase pie = new Phrase( "Plan de Curso de "+ this.materia, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, pie,40, 18, 0);                   
	        		
	        		Phrase pie2 = new Phrase( "Página "+writer.getPageNumber() , FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, pie2,560, 18, 0);
	   
	            }catch(Exception e) {
	                    System.out.println("Error en Metodo OnStartPage: "+e);
	            }
	        }            
        }
	        
        PageFooter pf = new PageFooter(materia);
	    pdf.setPageEvent(pf);
        
        document.open();
        
    //Logo Um y texto debajo
    
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_small.jpg");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(90, 90);
        jpg.setAbsolutePosition(70, 660);
        document.add(jpg);
        
        PdfContentByte canvas = pdf.getDirectContentUnder();
       
    	PdfPTable datosUM = new PdfPTable(1);
		int datosUMWidths[] = {100};
		datosUM.setWidths(datosUMWidths);
		datosUM.setTotalWidth(200f);
		
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
        Paragraph parrafo = new Paragraph();
        parrafo.add(new Phrase("Creada "
        		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
        parrafo.add(new Phrase("por el Gobierno\ndel estado de Nuevo\nLeón, México, mediante\nResolución Oficial\npublicada el 5 de mayo\nde 1973\n\n"
        		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo.add(new Phrase("Clave de la Institución\n"
        		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
        parrafo.add(new Phrase("ante la SEP y Dirección\nGeneral de Estadística\n19MSU1017U"
        		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		datosUM.addCell(celda);
        
		datosUM.writeSelectedRows(0, -1, 16, 650, pdf.getDirectContent());
		
    	///////////////////////////////////////////////////////////////////////////////////////////////////
    	// Encabezado

    	Phrase uni = new Phrase( institucion.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+390, posY+730, 0);
    	
    	Phrase planCurso = new Phrase( "", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, planCurso, posX+380, posY+640, 0);

    	Phrase curso = new Phrase( "", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, curso, posX+380, posY+600, 0);

    	Phrase plan = new Phrase( "", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, plan, posX+380, posY+570, 0);

    	//Tabla Invisible

    	float w = 80f;
    	float s = 200f;
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
		
		// tabla Facultad y Carrera 
		float ve = 5f;
    	float qe = 80f;
		PdfPTable tab2 = new PdfPTable(2);
		int tab2Widths[] = {30,70};
		tab2.setWidths(tab2Widths);
		tab2.setSpacingBefore(ve);
		tab2.setWidthPercentage(qe);    	
    	
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(facultadNombre
				, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(carreraNombre
				, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Plan de Curso de "+materia
				, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Curso Escolar "+"20"+primer+"-"+"20"+segundo
				, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		celda = new PdfPCell(new Phrase("PLAN DE ESTUDIOS 2010"
				, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		tab2.addCell(celda);
		
		document.add(tab2);
		
		//Tabla
		float width = 80f;
    	float space = 80f;
		PdfPTable cabeceraCurso = new PdfPTable(4);
		int cabeceraCursoWidths[] = {20,20,30,30};
		cabeceraCurso.setWidths(cabeceraCursoWidths);
		cabeceraCurso.setSpacingBefore(space);
		cabeceraCurso.setWidthPercentage(width);
		
		
		//-------------------------------------------------------------
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOTTOM);
		celda.setColspan(4);
		celda.setBorderWidthBottom(2f);
		cabeceraCurso.addCell(celda);
		//-------------------------------------------------------------
		celda = new PdfPCell(new Phrase("Clave: "+Materia.getCursoClave()
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(materia, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Instructor:"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(maestro
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		////----------------------->>>>>>>
		
		celda = new PdfPCell(new Phrase("Créditos:"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(Materia.getCreditos()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("\nFormación académica \n y responsabilidades administrativas:\n"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setRowspan(4);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getEstudios()+" "+GrupoPlan.getOcupacion()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setRowspan(4);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("F.S.T."
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.LEFT);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(Materia.getHt()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RIGHT);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("F.S.P."
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.LEFT);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(Materia.getHp()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RIGHT);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		celda = new PdfPCell(new Phrase("T.H.C."
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(String.valueOf(TotalHras)         
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		//------------------->>>>>>>>>>>>>
		
		celda = new PdfPCell(new Phrase("Competencia(s) que atiende la materia"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(.5f);
		celda.setColspan(2);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getCompetencia()
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(.5f);
		celda.setColspan(2);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		//------------------->>>>>>>>>>>>>>
		
		celda = new PdfPCell(new Phrase("Aula:"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getLugar()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Ubicación:"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(Materia.getCiclo()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		//------------------->>>>>>>>>>>>>>
		
		celda = new PdfPCell(new Phrase("Horario de clases:"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getHorario()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Cubículo y teléfono \nde la oficina"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getOficina()+", "+GrupoPlan.getTelefono()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		//------------------->>>>>>>>>>>>>>
		
		celda = new PdfPCell(new Phrase("Tiempo requerido fuera \ndel aula:"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getTiempo()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Horario para atender \na estudiantes:"
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getAtencion()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setColspan(1);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		//------------------->>>>>>>>>>>>>>
		
		celda = new PdfPCell(new Phrase("Prerrequisitos: \n "
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setBorderWidthBottom(2f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		String mat = "";
		for(int j = 0; j < lisPre.size(); j++){
			aca.plan.MapaCursoPre Pre = (aca.plan.MapaCursoPre) lisPre.get(j);
			String prer = aca.plan.CursoUtil.getMateria(conEnoc,Pre.getCursoIdPre());
			mat = mat+prer+", ";	
		}	
		
		String temp="";
		if(mat.equals("")){
			temp="Ninguno";
		}else{
			 temp = mat.substring(0, mat.length()-2);
		}
		
		celda = new PdfPCell(new Phrase(temp+".\n " , FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setBorderWidthBottom(2f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		//fin de for de prerrequisitos
		
		celda = new PdfPCell(new Phrase("Correo electrónico: \n "
				, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthRight(0f);
		celda.setColspan(1);
		celda.setBorderWidthBottom(2f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getCorreo()+" \n "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.RECTANGLE);
		celda.setBorderWidthLeft(0f);
		celda.setColspan(1);
		celda.setBorderWidthBottom(2f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		cabeceraCurso.addCell(celda);
		
		//------debajo de la tabla--->>
		celda = new PdfPCell(new Phrase("F.S.T.= Frecuencia semanal de teoría "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("F.S.P.= Frecuencia semanal de práctica "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		cabeceraCurso.addCell(celda);
		
		celda = new PdfPCell(new Phrase("T.H.C.= Total de horas del curso "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		cabeceraCurso.addCell(celda);
		
		document.add(cabeceraCurso);
		
		//------tabla de los ejes transversales--->>
		PdfPTable ejes = new PdfPTable(1);
		int ejesWidths[] = {100};
		ejes.setWidths(ejesWidths);
		ejes.setSpacingBefore(15f);
		ejes.setWidthPercentage(80f); 
		ejes.setKeepTogether(true);
		
		celda = new PdfPCell(new Phrase("ABORDAJE DE LOS EJES TRANSVERSALES"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(12);
		ejes.addCell(celda);
		document.add(ejes);
		for(int j=0; j<lisEjes.size();j++){
		  aca.carga.CargaGrupoEje eje = (aca.carga.CargaGrupoEje) lisEjes.get(j);
		 	
		  	PdfPTable cel = new PdfPTable(1);
			int celWidths[] = {100};
			cel.setWidths(celWidths);
			cel.setSpacingBefore(0f);
			cel.setWidthPercentage(80f); 
			cel.setKeepTogether(true);
			
		    celda = new PdfPCell(new Phrase("-"+aca.catalogo.CatEjeUtil.getNombreEje(conEnoc, eje.getEjeId())
					, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(12);
			cel.addCell(celda);
			
			celda = new PdfPCell(new Phrase(eje.getDescripcion(), 
					FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(12);
			cel.addCell(celda);
			 
			document.add(cel);
	    }
		
	//Tabla Descripcion Del Curso
		PdfPTable des = new PdfPTable(1);
		int desWidths[] = {100};
		des.setWidths(ejesWidths);
		des.setSpacingBefore(5f);
		des.setWidthPercentage(80f);
		des.setKeepTogether(true);

		celda = new PdfPCell(new Phrase("DESCRIPCIÓN DEL CURSO"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(12);
		des.addCell(celda); 
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getDescripcion()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(12);
		des.addCell(celda); 
		
		document.add(des);
	//Tabla Perspectiva y Enfoque Del Curso
		PdfPTable per = new PdfPTable(1);
		int perWidths[] = {100};
		per.setWidths(ejesWidths);
		per.setSpacingBefore(5f);
		per.setWidthPercentage(80f);
		per.setKeepTogether(true);
		
		celda = new PdfPCell(new Phrase("PERSPECTIVA Y ENFOQUE DEL CURSO"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(12);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		per.addCell(celda); 
		
		celda = new PdfPCell(new Phrase(GrupoPlan.getPerspectiva()
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(12);
		per.addCell(celda); 
		
		document.add(per);
	//Tabla COMPETENCIAS PARCIALES(o finales) A LOGRAR
		PdfPTable com = new PdfPTable(1);
		int comWidths[] = {100};
		com.setWidths(ejesWidths);
		com.setSpacingBefore(5f);
		com.setWidthPercentage(80f);
		com.setKeepTogether(true);
		
		document.add(com);
	//Tabla ORGANIZACIÓN Y EVALUACION DEL APRENDIZAJE
		PdfPTable orga = new PdfPTable(1);
		int orgaWidths[] = {100};
		orga.setWidths(ejesWidths);
		orga.setSpacingBefore(5f);
		orga.setWidthPercentage(80f);
		orga.setKeepTogether(true);
		
		celda = new PdfPCell(new Phrase("ORGANIZACIÓN Y EVALUACIÓN DEL APRENDIZAJE"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(12);
		orga.addCell(celda); 
		
		celda = new PdfPCell(new Phrase("En esta sección se presentan los temas, la calendarización,"+
				"las experiencias de aprendizaje por tématica, los criterios de desempeño, el o los instrumentos "+
				"para evaluar y el peso que tendrá la evaluación de esta unidad en la nota final."
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(12);
		orga.addCell(celda); 
		
		document.add(orga);
		
		//Tabla de Organizacion y Evaluacion
		
		for (int i=0; i<lisUnidad.size(); i++){
		 aca.carga.CargaUnidad unidad = (aca.carga.CargaUnidad) lisUnidad.get(i);
			PdfPTable org = new PdfPTable(5);
			int orgWidths[] = {13,20,30,30,7};
			org.setWidths(orgWidths);
			org.setSpacingBefore(15f);
			org.setWidthPercentage(80f);   
			
			celda = new PdfPCell(new Phrase(unidad.getUnidadNombre().toUpperCase().replaceAll("\n", " ")
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(5);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			org.addCell(celda);	
			
			celda = new PdfPCell(new Phrase("Competencia/s parciales (o finales): "
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.LEFT );
			celda.setColspan(4);
			org.addCell(celda);
			
			celda = new PdfPCell(new Phrase(""
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RIGHT);
			celda.setColspan(1);
			org.addCell(celda);
			org.setKeepTogether(true);
			
			/*for (int j=0; j<lisCompetencias.size(); j++){
				aca.carga.CargaUnidadComp competencia = (aca.carga.CargaUnidadComp) lisCompetencias.get(j);
				 if(competencia.getUnidadId().equals(unidad.getUnidadId())){
					celda = new PdfPCell(new Phrase(competencia.getCompetenciaId()+". "+aca.carga.CompetenciaUtil.getNombreCompetencia(conEnoc, competencia.getCompetenciaId())
							, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.LEFT);
					celda.setPaddingLeft(20f);
					celda.setColspan(4);
					org.addCell(celda);
					
					celda = new PdfPCell(new Phrase(""
							, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.RIGHT);
					celda.setColspan(1);
					org.addCell(celda);
				 }//Fin if competencias
			}//Fin for Competencias
				 */
				 
			ArrayList<MapaNuevoActividad> listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, nuevoCursoId, unidad.getUnidadId(), "ORDER BY TIPO, ACTIVIDAD_ID");
			String tipo = "";
			Phrase frase = new Phrase();
			for(int j = 0; j < listActividades.size(); j++){
				mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
					
				if(!tipo.equals(mapaNuevoActividad.getTipo().trim())){
					tipo = mapaNuevoActividad.getTipo().trim();
					frase.add(new Phrase(tipo.equals("1")?"Conocimientos:\n":tipo.equals("2")?"Habilidades:\n":"Actitudes:\n", 
							FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				}
				frase.add(new Phrase("- "+mapaNuevoActividad.getObjetivo().replaceAll("\n"," ").replaceAll(" "," ")+"\n", 
						FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			}
			celda = new PdfPCell(frase);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.LEFT);
			celda.setPaddingLeft(20f);
			celda.setColspan(4);
			org.addCell(celda);
			
			celda = new PdfPCell(new Phrase(""
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RIGHT);
			celda.setColspan(1);
			org.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Fecha "
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			org.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Tema "
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			org.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Experiencias de aprendizaje del alumno "
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			org.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Instrumentos para la Evaluación "
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			org.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Valor"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOX);
			celda.setColspan(1);
			org.addCell(celda);
			
			for (int z=0; z<lisTemas.size();z++){
		         aca.carga.CargaUnidadTema tema = (aca.carga.CargaUnidadTema) lisTemas.get(z);
		           if (tema.getTemaId().substring(0,2).equals(unidad.getUnidadId())){
		        	   
		        
					celda = new PdfPCell(new Phrase(tema.getFecha()
							, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.LEFT);
					celda.setColspan(1);
					org.addCell(celda);
					
					celda = new PdfPCell(new Phrase(tema.getTemaNombre()
							, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setColspan(1);
					org.addCell(celda);
					
					celda = new PdfPCell(new Phrase(""
							, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.RIGHT);
					celda.setColspan(3);
					org.addCell(celda);
					
					for (int k=0 ; k<lisActividades.size(); k++){
				        aca.carga.CargaUnidadActividad actividad = (aca.carga.CargaUnidadActividad) lisActividades.get(k);
				         if(actividad.getActividadId().substring(0,4).equals(tema.getTemaId())){
				        	    celda = new PdfPCell(new Phrase(""
										, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
								celda.setHorizontalAlignment(Element.ALIGN_CENTER);
								celda.setBorder(Rectangle.LEFT);
								celda.setColspan(2);
								org.addCell(celda);
				        	 	
								celda = new PdfPCell(new Phrase("- "+actividad.getActividadNombre()
										, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
								celda.setHorizontalAlignment(Element.ALIGN_LEFT);
								celda.setBorder(Rectangle.NO_BORDER);
								celda.setColspan(1);
								org.addCell(celda);
								
								celda = new PdfPCell(new Phrase(""
										, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
								celda.setHorizontalAlignment(Element.ALIGN_LEFT);
								celda.setBorder(Rectangle.NO_BORDER);
								celda.setColspan(1);
								org.addCell(celda);
								
								celda = new PdfPCell(new Phrase(actividad.getValor()
										, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
								celda.setHorizontalAlignment(Element.ALIGN_CENTER);
								celda.setBorder(Rectangle.RIGHT);
								celda.setColspan(1);
								org.addCell(celda);
								
								
								 for (int j=0; j<lisCriterios.size(); j++){
					                   aca.carga.CargaUnidadCriterio criterio = (aca.carga.CargaUnidadCriterio ) lisCriterios.get(j);
					                     if(criterio.getCriterioId().substring(0,6).equals(actividad.getActividadId())){
											celda = new PdfPCell(new Phrase(""
													, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
											celda.setHorizontalAlignment(Element.ALIGN_CENTER);
											celda.setBorder(Rectangle.LEFT);
											celda.setColspan(2);
											org.addCell(celda);
							        	 	
											celda = new PdfPCell(new Phrase(""+" - "+criterio.getCriterioNombre()
													, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(r,g,b))));
											celda.setHorizontalAlignment(Element.ALIGN_LEFT);
											celda.setBorder(Rectangle.NO_BORDER);
											celda.setColspan(1);
											org.addCell(celda);
											
											celda = new PdfPCell(new Phrase(""
													, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
											celda.setHorizontalAlignment(Element.ALIGN_LEFT);
											celda.setBorder(Rectangle.RIGHT);
											celda.setColspan(2);
											org.addCell(celda);
											
											
											for (int x=0; x<lisInstrumentos.size(); x++){
						                         aca.carga.CargaUnidadInstrumento instrumento = (aca.carga.CargaUnidadInstrumento) lisInstrumentos.get(x);
						                          if(instrumento.getInstrumentoId().substring(0,8).equals(criterio.getCriterioId())){
									        	 	
						                        	  celda = new PdfPCell(new Phrase(""
																, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
														celda.setHorizontalAlignment(Element.ALIGN_CENTER);
														celda.setBorder(Rectangle.LEFT);
														celda.setColspan(3);
														org.addCell(celda);
														
						                        	 celda = new PdfPCell(new Phrase(""+" - "+instrumento.getInstrumentoNombre()+"*"
															, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(r,g,b))));
													celda.setHorizontalAlignment(Element.ALIGN_LEFT);
													celda.setBorder(Rectangle.NO_BORDER);
													celda.setColspan(1);
													org.addCell(celda);
													
													celda = new PdfPCell(new Phrase(""
															, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
													celda.setHorizontalAlignment(Element.ALIGN_LEFT);
													celda.setBorder(Rectangle.RIGHT);
													celda.setColspan(1);
													org.addCell(celda);
						                          }//fin if instrumentos
											}//fin for Instrumentos
					                     }//Fin de if de criterios
								 }//Fin de for Criterios
				         }// fin if Actividades
					}//fin For Actividades     
					
					celda = new PdfPCell(new Phrase(""
							, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.RECTANGLE);
					celda.disableBorderSide(1);
					celda.setColspan(5);
					celda.setLeading(.01f,.01f);
					org.addCell(celda);

					
		           }//fin if Temas
		      }//fin For temas
					
			document.add(org);
			
		}//Fin for Unidades
//------>> Fin de la tabla de evaluaciones
		
		PdfPTable abajo = new PdfPTable(5);
		int abajoWidths[] = {20,20,20,20,20};
		abajo.setWidths(abajoWidths);
		abajo.setSpacingBefore(15f);
		abajo.setWidthPercentage(80f);
		abajo.setKeepTogether(true);
		
		celda = new PdfPCell(new Phrase("BIBLIOGRAFÍA "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setColspan(5);
		abajo.addCell(celda);

		for (int z=0; z<listBiblio.size(); z++){
			aca.carga.CargaGrupoBiblio biblio = (aca.carga.CargaGrupoBiblio) listBiblio.get(z);
			
			celda = new PdfPCell(new Phrase(biblio.getBibliografia()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			abajo.addCell(celda);	
		}		
		
		document.add(abajo);
		
		PdfPTable pau = new PdfPTable(5);
		int pauWidths[] = {20,20,20,20,20};
		pau.setWidths(pauWidths);
		pau.setSpacingBefore(15f);
		pau.setWidthPercentage(80f);
		pau.setKeepTogether(true);
		
		celda = new PdfPCell(new Phrase("PAUTAS PARA LA EVALUACIÓN "
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		pau.addCell(celda);
		
		document.add(pau);
		PdfPTable pautas = new PdfPTable(2);
		int pautasWidths[] = {80,20};
		pautas.setWidths(pautasWidths);
		pautas.setSpacingBefore(15f);
		pautas.setWidthPercentage(40f);
		pautas.setKeepTogether(true);
		
		
		for(int a=0; a<listEvaluaciones.size();a++){
			  aca.carga.CargaPlanEval evaluacion = (aca.carga.CargaPlanEval) listEvaluaciones.get(a);
			  
			    celda = new PdfPCell(new Phrase(evaluacion.getEvaluacionNombre()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(0);
				pautas.addCell(celda);
				
				celda = new PdfPCell(new Phrase(evaluacion.getValor()+"%"
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(0);
				pautas.addCell(celda);
				
		}
		
		celda = new PdfPCell(new Phrase("Calificación Final"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setBorderWidthTop(2f);
		celda.setColspan(0);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		pautas.addCell(celda);
		
		celda = new PdfPCell(new Phrase("100 %"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(0);
		celda.setBorderWidthTop(2f);
		celda.setBackgroundColor(new BaseColor(230,230,230));
		pautas.addCell(celda);
		
		document.add(pautas);	
		
	}catch(IOException ioe){
		System.err.println("Error al generar el plan en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	String nombreArchivo = cursoCargaId+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>
<%@ include file= "../../cierra_enoc.jsp" %>
