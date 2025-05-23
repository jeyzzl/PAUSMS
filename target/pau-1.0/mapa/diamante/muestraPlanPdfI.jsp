<%@page import="java.awt.Color" %>
<%@page import="java.io.IOException" %>
<%@page import="java.io.File" %>
<%@page import="java.io.FileOutputStream" %>
<%@page import="com.itextpdf.text.*" %>
<%@page import="com.itextpdf.text.pdf.*" %>
<%@page import="com.itextpdf.text.pdf.events.*" %>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaNuevoCurso"%>
<%@page import="aca.plan.MapaNuevoUnidad"%>
<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoBiblioUnidad"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.plan.MapaNuevoPlan"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoCursoU" class="aca.plan.MapaNuevoCursoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoUnidad" class="aca.plan.MapaNuevoUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografia" class="aca.plan.MapaNuevoBibliografia" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografiaU" class="aca.plan.MapaNuevoBibliografiaUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidad" class="aca.plan.MapaNuevoBiblioUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidadU" class="aca.plan.MapaNuevoBiblioUnidadUtil" scope="page"/>
<%
	String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");
	String versionId		= request.getParameter("versionId")==null?"0":request.getParameter("versionId");	
	String ciclo			= "";
	String institucion 		= (String)session.getAttribute("institucion");
	
	ArrayList<MapaNuevoCurso> listCursos = null;
	ArrayList<MapaNuevoUnidad> listUnidades = null;
	ArrayList<MapaNuevoActividad> listActividades = null;
	ArrayList<MapaNuevoBibliografia> listBibliografias = null;
	ArrayList<MapaNuevoBiblioUnidad> listBiblioUnidad = null;
	
	mapaNuevoPlan.mapeaRegId(conEnoc, planId, versionId);
	
	listCursos = mapaNuevoCursoU.getListPlan(conEnoc, planId, versionId, "ORDER BY CICLO, NOMBRE");
	
	Document document = new Document(PageSize.LETTER.rotate()); //Crea un objeto para el documento PDF
	document.setMargins(30,30,40,45);
	
	File fileCarpeta = new File(application.getRealPath("/WEB-INF/pdf/plan/"));
	if(!fileCarpeta.exists()) fileCarpeta.mkdirs();
	
	String dir = application.getRealPath("/WEB-INF/pdf/plan/")+"/"+"plan"+planId.replace("*","")+".pdf";
	
	try{
		
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Direccion de Sistemas");
        document.addSubject("Plan de "+mapaNuevoPlan.getNombre());
        
        final class PageNumbers extends PdfPageEventHelper {
        	/** A template that will hold the total number of pages. */
            public PdfTemplate tpl;
        	/** The font that will be used. */
            public BaseFont helv;        	
        	private MapaNuevoPlan mapaNuevoPlan;
        	private MapaNuevoCurso mapaNuevoCurso;
        	
        	public PageNumbers(MapaNuevoPlan mnp){        		
        		mapaNuevoPlan = mnp;
        		mapaNuevoCurso = new MapaNuevoCurso();
        	}
        	
        	public void setMapaNuevoCurso(MapaNuevoCurso mapaNuevoCurso){
        		this.mapaNuevoCurso = mapaNuevoCurso;
        	}        	
            
            public void onOpenDocument(PdfWriter writer, Document document) {
                try {
                    // initialization of the template
                    tpl = writer.getDirectContent().createTemplate(100, 100);
                    tpl.setBoundingBox(new Rectangle(-20, -20, 100, 100));
                    // initialization of the font
                    helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
                }
                catch(Exception e) {
                    throw new ExceptionConverter(e);
                }
            }
        	
            public void onEndPage(PdfWriter writer, Document document) {
            	if(writer.getPageNumber() > 0){
	                PdfContentByte cb = writer.getDirectContent();
	                cb.saveState();
	                cb.restoreState();
	                // compose the footer
	                String text = String.valueOf(writer.getPageNumber());
	                float textSize = helv.getWidthPoint(text, 8);
	                float textBase = document.bottomMargin();
	                cb.beginText();
	                cb.setFontAndSize(helv, 8);
	                
	                // for even numbers, show the footer at the right
	               
	                float adjust = helv.getWidthPoint("0", 8);
	                cb.setTextMatrix(document.right() - textSize - adjust, textBase-20);
	                cb.showText(text);
	                cb.endText();
	                cb.addTemplate(tpl, document.right() - adjust, textBase);
	                
	                cb.saveState();
	                cb.restoreState();
	                //--------------------------- Footer -----------------------
	        		try{
		        		PdfPTable footerDocument = new PdfPTable(1);
	        	        int footerDocumentWidths[] = {100};
	        	        footerDocument.setWidths(footerDocumentWidths);
	        	        footerDocument.setTotalWidth(600f);
	        		
	        	        PdfPCell footerCell = new PdfPCell(new Phrase(mapaNuevoPlan.getNombre()+" plan 2018 - "+mapaNuevoCurso.getNombre(),
	        	        		FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
	        	        footerCell.setBorder(Rectangle.NO_BORDER);
	        	        footerDocument.addCell(footerCell);
	        	        
	        	      	footerDocument.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin()-10, writer.getDirectContent());
	        		}catch(Exception e) {
	        			System.err.println("Error kardex en PDF: "+e.getMessage());
	        		}

	                
	                //----------------------------------------------------------
            	}
            }
        }
        
        PageNumbers pn = new PageNumbers(mapaNuevoPlan);
        pdf.setPageEvent(pn);
        
        document.open();
        
        PdfPTable tituloDocumento = new PdfPTable(1);
        int tituloDocumentoWidths[] = {100};
        tituloDocumento.setWidths(tituloDocumentoWidths);
        tituloDocumento.setTotalWidth(document.getPageSize().getWidth() - 40);
        
        PdfPCell cell = new PdfPCell(new Phrase(institucion.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        tituloDocumento.addCell(cell);
        
        cell = new PdfPCell(new Phrase(mapaNuevoPlan.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        tituloDocumento.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Materias de "+planId, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        tituloDocumento.addCell(cell);
        
        tituloDocumento.writeSelectedRows(0, -1, 20, (document.getPageSize().getHeight() / 2) + 50, pdf.getDirectContent());
        document.setPageCount(0);
        //document.add(tituloDocumento);
        document.newPage();
        
        //Imprimir pies de pagina
        for(int i = 0; i < listCursos.size(); i++){
        	document.setPageCount(1);
    		mapaNuevoCurso = (MapaNuevoCurso) listCursos.get(i);
    		String cursoId = mapaNuevoCurso.getCursoId();
    		listUnidades = mapaNuevoUnidadU.getListCurso(conEnoc, cursoId, "ORDER BY ORDEN, UNIDAD_ID");
    		//---------- Colocar el nombre de la materia en el footer ---------------
    		pn.setMapaNuevoCurso(mapaNuevoCurso);
    		/*if(!ciclo.equals(mapaNuevoCurso.getCiclo())){ //Imprimir pagina de separacion de semestres
    			ciclo = mapaNuevoCurso.getCiclo();
    			
    			Table tituloSemestre = new Table(1);
    	        tituloSemestre.setBorder(0);
    	        tituloSemestre.setPadding(0);
    	        tituloSemestre.setSpacing(0);
    	        int tituloSemestreWidths[] = {100};
    	        tituloSemestre.setWidths(tituloSemestreWidths);
    	        tituloSemestre.setWidth(100);
    	        
    	        Cell cell2 = new Cell(new Phrase((mapaNuevoPlan.getCarreraId().substring(0,3).equals("107")?"Tetramestre ":"Semestre ")+ciclo, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    	        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
    	        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	        cell2.setBorder(Rectangle.NO_BORDER);
    	        tituloSemestre.addCell(cell2);
    	        
    	        document.add(tituloSemestre);
    	        document.newPage();
    		}*/
            
            //------------- Imagen y datos de la um -------------------------------
            Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_large.jpg");
            jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
            jpg.scaleAbsolute(80, 80);
            jpg.setAbsolutePosition(90, 500);
            document.add(jpg);
            
            PdfPTable datosUM = new PdfPTable(1);
    		int datosUMWidths[] = {100};
    		datosUM.setWidths(datosUMWidths);
    		datosUM.setTotalWidth(200f);
    		
            PdfPCell celda = null;
            int r = 0, g = 0, b = 0;
            
            Paragraph parrafo = new Paragraph();
            parrafo.add(new Phrase("Created "
            		, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(r,g,b))));
            parrafo.add(new Phrase("by the State\nGovernment of Nuevo\nLeón, México, by\nOfficial resolution\nissued on may 5,1973\n\n"
            		, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(r,g,b))));
            parrafo.add(new Phrase("Institution code\n"
            		, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(r,g,b))));
            parrafo.add(new Phrase("Before the SEP and\nGeneral Statistics\n19MSU1017U"
            		, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(r,g,b))));
            celda = new PdfPCell(parrafo);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			datosUM.addCell(celda);
            
    		datosUM.writeSelectedRows(0, -1, 630, 575, pdf.getDirectContent());
            //---------------------------------------------------------------------
    		
    		PdfPTable tituloCurso = new PdfPTable(1);
    		int tituloCursoWidths[] = {100};
    		tituloCurso.setWidths(tituloCursoWidths);
            
            
            /*celda = new PdfPCell(new Phrase(" "
            		, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.NO_BORDER);
			tituloCurso.addCell(celda);*/
            
            celda = new PdfPCell(new Phrase(institucion.toUpperCase()
            		, FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.NO_BORDER);
			tituloCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId()))
					, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.NO_BORDER);
			tituloCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoPlan.getNombre()
					, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.NO_BORDER);
			tituloCurso.addCell(celda);
			
			document.add(tituloCurso);
			
			float width = 95f;
			PdfPTable cabeceraCurso = new PdfPTable(12);
    		int cabeceraCursoWidths[] = {16, 16, 6, 6, 6, 6, 6, 6, 6, 6, 10, 10};
    		cabeceraCurso.setWidths(cabeceraCursoWidths);
    		cabeceraCurso.setSpacingBefore(20f);
    		cabeceraCurso.setWidthPercentage(width);
    		//-------------------------------------------------------------
    		celda = new PdfPCell(new Phrase(" "
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOTTOM);
			celda.setColspan(12);
			cabeceraCurso.addCell(celda);
    		//-------------------------------------------------------------
    		celda = new PdfPCell(new Phrase("SUBJECT:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getNombre()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
			
			String tipoPeriodo = "";
    		if (mapaNuevoPlan.getTipo().equals("S")) 
    			tipoPeriodo= "SEMESTER"; 
    		else if (mapaNuevoPlan.getTipo().equals("T"))
    			tipoPeriodo= "TETRAMESTRE";
    		else if (mapaNuevoPlan.getTipo().equals("A"))
    			tipoPeriodo= "YEAR";
    		
    		celda = new PdfPCell(new Phrase(tipoPeriodo
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			celda.setColspan(2);
			cabeceraCurso.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getCiclo()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.RECTANGLE);
			celda.setColspan(2);
			cabeceraCurso.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase("CODE:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			celda.setColspan(2);
			cabeceraCurso.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getClave()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			celda.setColspan(2);
			cabeceraCurso.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase("SERIATION:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase(mapaNuevoCurso.getSeriacion()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
			//-------------------------------------------------------------
			celda = new PdfPCell(new Phrase("CURRICULUM AREA:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getUbicacion()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
			
			int numCamposHrs = 0;
			
			if(mapaNuevoPlan.getHfd().equals("S"))
				numCamposHrs++;
			if(mapaNuevoPlan.getHps().equals("S"))
				numCamposHrs++;
			if(mapaNuevoPlan.getHts().equals("S"))
				numCamposHrs++;
			if(mapaNuevoPlan.getHei().equals("S"))
				numCamposHrs++;
			if(mapaNuevoPlan.getHss().equals("S"))
				numCamposHrs++;
			if(mapaNuevoPlan.getHas().equals("S"))
				numCamposHrs++;		
			
			PdfPTable horasCreditos = new PdfPTable(10);;
			if(numCamposHrs == 1){
				horasCreditos = new PdfPTable(6);
    			int horasCreditosWidths[] = {16, 16, 16, 16, 18, 18};
    			horasCreditos.setWidths(horasCreditosWidths);
			}else if(numCamposHrs == 2){
				horasCreditos = new PdfPTable(8);
    			int horasCreditosWidths[] = {13, 12, 13, 12, 13, 12, 13, 12};
    			horasCreditos.setWidths(horasCreditosWidths);
			}else if(numCamposHrs == 3){
				horasCreditos = new PdfPTable(10);
    			int horasCreditosWidths[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
    			horasCreditos.setWidths(horasCreditosWidths);
			}else if(numCamposHrs == 4){
				horasCreditos = new PdfPTable(12);
				int horasCreditosWidths[] = {8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9};
    			horasCreditos.setWidths(horasCreditosWidths);
			}
    		//thshei.setSpacingBefore(20f);
    		horasCreditos.setWidthPercentage(100f);
    		
    		if(mapaNuevoPlan.getHts().equals("S")){
				celda = new PdfPCell(new Phrase("WST:"
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoCurso.getHst()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
    		}
			
    		if(mapaNuevoPlan.getHps().equals("S")){
				celda = new PdfPCell(new Phrase("WSP:"
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoCurso.getHsp()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
    		}
    		
    		if(mapaNuevoPlan.getHfd().equals("S")){
				celda = new PdfPCell(new Phrase("HFD:"
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoCurso.getHfd()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
    		}
    		
    		if(mapaNuevoPlan.getHss().equals("S")){
	    		celda = new PdfPCell(new Phrase("HSS:"
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoCurso.getHss()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
    		}
    		
    		if(mapaNuevoPlan.getHas().equals("S")){
	    		celda = new PdfPCell(new Phrase("HAS:"
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoCurso.getHas()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				horasCreditos.addCell(celda);
    		}
    		
    		celda = new PdfPCell(new Phrase("WTS:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.RECTANGLE);
			horasCreditos.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getThs()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			horasCreditos.addCell(celda);
				
			celda = new PdfPCell(new Phrase("CRS:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.RECTANGLE);
			horasCreditos.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getCreditos()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			horasCreditos.addCell(celda);
			
			celda = new PdfPCell(horasCreditos);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setColspan(8);
			cabeceraCurso.addCell(celda);
				
			celda = new PdfPCell(new Phrase("TOTAL HOURS:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getHt()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.RECTANGLE);
			cabeceraCurso.addCell(celda);
			
				
			//-------------------------------------------------------------
			celda = new PdfPCell(new Phrase("COMPETENCE PROFILE ATTENDING BY THE SUBJECT:"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			celda.setColspan(4);
			cabeceraCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getCompetencia()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			celda.setColspan(8);
			cabeceraCurso.addCell(celda);
			
			document.add(cabeceraCurso);
			
			PdfPTable objetivoCurso = new PdfPTable(1);
    		int objetivoCursoWidths[] = {100};
    		objetivoCurso.setWidths(objetivoCursoWidths);
    		objetivoCurso.setSpacingBefore(40f);
    		objetivoCurso.setWidthPercentage(width);
    		
    		//-------------------------------------------------------------
    		celda = new PdfPCell(new Phrase(" "
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOTTOM);
			objetivoCurso.addCell(celda);
    		
    		//-------------------------------------------------------------
    		celda = new PdfPCell(new Phrase("COURSE OBJECTIVE:"
					, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.RECTANGLE);
			objetivoCurso.addCell(celda);
    		
			Phrase p = new Phrase(1.5f, mapaNuevoCurso.getDescripcion()
					, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(r,g,b)));
    		celda = new PdfPCell(p);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			objetivoCurso.addCell(celda);
			
			document.add(objetivoCurso);
			
			document.newPage();
			//Esta es la parte de la unidad
			for(int j = 0; j < listUnidades.size(); j++){
				mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(j);
				String uid = mapaNuevoUnidad.getUnidadId();
				String tipo = "";
				listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, ACTIVIDAD_ID");
				listBiblioUnidad = mapaNuevoBiblioUnidadU.getListCursoUnidad(conEnoc, cursoId, uid, "ORDER BY (SELECT TIPO FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA"+ 
				" WHERE CURSO_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.CURSO_ID AND BIBLIOGRAFIA_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.BIBLIOGRAFIA_ID), ID");
				
				PdfPTable unidad = new PdfPTable(5);
	    		int unidadWidths[] = {15, 15, 31, 19, 20};
	    		unidad.setWidths(unidadWidths);
	    		unidad.setSpacingBefore(40f);
	    		unidad.setWidthPercentage(width);
				unidad.setHeaderRows(2);
	    		
	    		celda = new PdfPCell(new Phrase(" "
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOTTOM);
				celda.setPadding(0f);
				celda.setColspan(5);
				unidad.addCell(celda);
	    		
	    		celda = new PdfPCell(new Phrase("ESTIMATED TIME"
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
	    		
	    		celda = new PdfPCell(new Phrase("NAME AND PURPOSE OF THE UNIT"
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
	    		
	    		celda = new PdfPCell(new Phrase("TOPICS AND SUBTOPICS"
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
	    		
	    		celda = new PdfPCell(new Phrase("LEARNING ACTIVITIES"
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
	    		
	    		celda = new PdfPCell(new Phrase("BIBLIOGRAPHY"
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
				//-------------------------------------------------------------
				celda = new PdfPCell(new Phrase(mapaNuevoUnidad.getTiempo()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
				//*********Tabla que contiene tanto el nombre de la unidad como los objetivos**************************
				PdfPTable nombreObjetivoUnidad = new PdfPTable(2);
	    		int nombreObjetivoUnidadWidths[] = {10, 90};
	    		nombreObjetivoUnidad.setWidths(nombreObjetivoUnidadWidths);
	    		nombreObjetivoUnidad.setSpacingAfter((float)20);
	    		//nombreObjetivoUnidad.setWidthPercentage(width);
	    		
	    		celda = new PdfPCell(new Phrase(mapaNuevoUnidad.getNombre()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				nombreObjetivoUnidad.addCell(celda);
	    		
	    		celda = new PdfPCell(new Phrase(" "
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				nombreObjetivoUnidad.addCell(celda);
	    		
	    		celda = new PdfPCell(new Phrase("OBJECTIVE:"
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				nombreObjetivoUnidad.addCell(celda);
					
				tipo = "";
				for(int k = 0; k < listActividades.size(); k++){
					mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(k);
					if(!tipo.equals(mapaNuevoActividad.getTipo())){
						tipo = mapaNuevoActividad.getTipo();
						
						celda = new PdfPCell(new Phrase(tipo.equals("1")?"Knowledge:":tipo.equals("2")?"Skills:":"Attitudes:"
								, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						celda.setBorder(Rectangle.NO_BORDER);
						celda.setPadding(1f);
						celda.setColspan(2);
						nombreObjetivoUnidad.addCell(celda);
					}
					
					celda = new PdfPCell(new Phrase("-"
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					nombreObjetivoUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase(mapaNuevoActividad.getObjetivo()
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					nombreObjetivoUnidad.addCell(celda);
				}
				//*******Termina tabla que contiene tanto el nombre de la unidad como los objetivos*********************
				celda = new PdfPCell(nombreObjetivoUnidad);
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoUnidad.getTemas()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(1f);
				unidad.addCell(celda);
				//*********Tabla que contiene las actividades de aprendizaje**************************
				PdfPTable actividadesUnidad = new PdfPTable(2);
	    		int actividadesUnidadWidths[] = {10, 90};
	    		actividadesUnidad.setWidths(actividadesUnidadWidths);
	    		actividadesUnidad.setSpacingAfter((float)20);
	    		//actividadesUnidad.KEEPTOGETHER = true;
	    		actividadesUnidad.setWidthPercentage(width);
	    		
				tipo = "";
				for(int k = 0; k < listActividades.size(); k++){
					mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(k);
					if(!tipo.equals(mapaNuevoActividad.getTipo())){
						tipo = mapaNuevoActividad.getTipo();
						
						celda = new PdfPCell(new Phrase(tipo.equals("1")?"KNOWLEDGE (know)":tipo.equals("2")?"SKILLS (know what to do)":"ATTITUDES (Know how to be)"
								, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						celda.setBorder(Rectangle.NO_BORDER);
						celda.setPadding(0f);
						celda.setColspan(2);
						actividadesUnidad.addCell(celda);
					}
					
					celda = new PdfPCell(new Phrase("Activities:"
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					celda.setColspan(2);
					actividadesUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase("-"
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					actividadesUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase(mapaNuevoActividad.getDescripcion()
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					actividadesUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase("Performance criteria:"
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					celda.setColspan(2);
					actividadesUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase("-"
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					actividadesUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase(mapaNuevoActividad.getCriterio()
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					celda.setPadding(0f);
					actividadesUnidad.addCell(celda);
				}
				//*******Termina tabla que contiene las actividades de aprendizaje*********************
				celda = new PdfPCell(actividadesUnidad);
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
				
				//*********Tabla que contiene las bibliografias**************************
				PdfPTable bibliografiasUnidad = new PdfPTable(2);
	    		int bibliografiasUnidadWidths[] = {10, 90};
	    		bibliografiasUnidad.setWidths(bibliografiasUnidadWidths);
	    		bibliografiasUnidad.setSpacingAfter((float)20);
	    		//bibliografiasUnidad.setWidthPercentage(width);
	    		
				tipo = "";
				for(int k = 0; k < listBiblioUnidad.size(); k++){
					mapaNuevoBiblioUnidad = (MapaNuevoBiblioUnidad) listBiblioUnidad.get(k);
					mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, mapaNuevoBiblioUnidad.getBibliografiaId());
					if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
						tipo = mapaNuevoBibliografia.getTipo();
						
						celda = new PdfPCell(new Phrase(tipo.equals("1")?"Books and magazines:":"Links:"
								, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						celda.setBorder(Rectangle.NO_BORDER);
						celda.setColspan(2);
						bibliografiasUnidad.addCell(celda);
					}
					
					celda = new PdfPCell(new Phrase("-"
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					bibliografiasUnidad.addCell(celda);
					
					celda = new PdfPCell(new Phrase(mapaNuevoBibliografia.getReferencia()
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.NO_BORDER);
					bibliografiasUnidad.addCell(celda);
					//Si no tienes especificacion no muestra nada
					if(!mapaNuevoBiblioUnidad.getEspecificacion().equals("-")){
						celda = new PdfPCell(new Phrase(" "
								, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						celda.setBorder(Rectangle.NO_BORDER);
						bibliografiasUnidad.addCell(celda);
						
						celda = new PdfPCell(new Phrase(mapaNuevoBiblioUnidad.getEspecificacion()
								, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						celda.setBorder(Rectangle.NO_BORDER);
						bibliografiasUnidad.addCell(celda);
					}
				}
				//*********Termina tabla que contiene las bibliografias**************************
				celda = new PdfPCell(bibliografiasUnidad);
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setPadding(0f);
				unidad.addCell(celda);
				
				document.add(unidad);
				document.newPage();
			}
			
			PdfPTable pieCurso = new PdfPTable(1);
    		int pieCursoWidths[] = {100};
    		pieCurso.setWidths(pieCursoWidths);
    		pieCurso.setSpacingAfter((float)20);
    		pieCurso.setWidthPercentage(width);
    		pieCurso.setHeaderRows(1);
    		
    		celda = new PdfPCell(new Phrase(" "
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOTTOM);
			pieCurso.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase("TEACHER ACTION (Methodology):"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			//**************** Tabla de accion docente *************************************
			PdfPTable accionDocente = new PdfPTable(2);
    		int accionDocenteWidths[] = {20, 80};
    		accionDocente.setWidths(accionDocenteWidths);
    		accionDocente.setSpacingAfter((float)20);
    		//accionDocente.setWidthPercentage(width);
    		
			for(int j = 0; j < listUnidades.size(); j++){
				mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(j);
				String uid = mapaNuevoUnidad.getUnidadId();
				
				celda = new PdfPCell(new Phrase(mapaNuevoUnidad.getNombre()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				accionDocente.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoUnidad.getAccionDocente()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				accionDocente.addCell(celda);
			}
			//**************** Termina tabla de accion docente *************************************
			celda = new PdfPCell(accionDocente);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase("DIDACTIC RESOURCES:"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getMediosRecursos()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase("ASSESSMENT ELEMENTS"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Diagnostic:"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getEeDiagnostica()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Formative:"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getEeFormativa()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Summative:"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getEeSumativa()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase("GRADING SCALE (Weighting grades):"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			celda = new PdfPCell(new Phrase(mapaNuevoCurso.getEscala()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.RECTANGLE);
			pieCurso.addCell(celda);
			
			document.add(pieCurso);
			document.newPage();
			
			//**************** Tabla de Bibliografia Global *************************************
			PdfPTable bibliografiaGlobal = new PdfPTable(2);
    		int bibliografiaGlobalWidths[] = {30, 70};
    		bibliografiaGlobal.setWidths(bibliografiaGlobalWidths);
    		bibliografiaGlobal.setSpacingAfter((float)20);
    		bibliografiaGlobal.setWidthPercentage(width);
    		bibliografiaGlobal.setHeaderRows(1);
    		
    		celda = new PdfPCell(new Phrase(" "
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.BOTTOM);
			celda.setColspan(2);
			bibliografiaGlobal.addCell(celda);
    		
    		celda = new PdfPCell(new Phrase("TOTAL BIBLIOGRAPHY"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.RECTANGLE);
			celda.setColspan(2);
			bibliografiaGlobal.addCell(celda);
			
			String tipo = "";
			listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoId, "ORDER BY TIPO, BIBLIOGRAFIA");
			for(int j = 0; j < listBibliografias.size(); j++){
				mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(j);
				if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
					tipo = mapaNuevoBibliografia.getTipo();
					
					celda = new PdfPCell(new Phrase(tipo.equals("1")?"Books and Magazines:":"Links:"
							, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setBorder(Rectangle.RECTANGLE);
					celda.setColspan(2);
					bibliografiaGlobal.addCell(celda);
				}
				
				celda = new PdfPCell(new Phrase(mapaNuevoBibliografia.getReferencia()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				bibliografiaGlobal.addCell(celda);
				
				celda = new PdfPCell(new Phrase(mapaNuevoBibliografia.getBibliografia()
						, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.RECTANGLE);
				bibliografiaGlobal.addCell(celda);
			}
			
			document.add(bibliografiaGlobal);
			document.newPage();
		}
        document.add(new Phrase("End of List"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));

	}catch(IOException ioe) {
		System.err.println("Error plan nuevo en PDF: "+ioe.getMessage());
	}

	document.close();
	
	String nombreArchivo = planId.replace("*","")+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>
<%@ include file= "../../cierra_enoc.jsp" %>