<%@ page import = "java.awt.Color" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmProceso"%>
<%@page import="aca.admision.spring.AdmEvaluacion"%>
<%@page import="aca.admision.spring.AdmCarta"%>
<%@page import="aca.admision.spring.AdmDocAlum" %>
<%@page import="aca.admision.spring.AdmDocumento"%>
<%@page import="aca.admision.spring.AdmRequisito"%>

<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="DocAlumU" scope="page" class="aca.admision.DocAlumUtil"/>
<jsp:useBean id="AdmDocumento" scope="page" class="aca.admision.AdmDocumento"/>
<jsp:useBean id="AdmProceso" scope="page" class="aca.admision.AdmProceso" />
<%
	String folio 						= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	int posX 		= 0; 
	int posY		= 0;
	
	String facultadNombre				= (String) request.getAttribute("facultadNombre");
	String carreraNombre				= (String) request.getAttribute("carreraNombre");
	AdmSolicitud admSolicitud			= (AdmSolicitud) request.getAttribute("admSolicitud");
	AdmAcademico admAcademico			= (AdmAcademico) request.getAttribute("admAcademico");
	AdmProceso admProceso				= (AdmProceso) request.getAttribute("admProceso");
	
	List<AdmDocAlum> lisDocumentos		= (List<AdmDocAlum>) request.getAttribute("lisDocumentos");
	List<AdmCarta> lisCondiciones		= (List<AdmCarta>) request.getAttribute("lisCondiciones");	
	List<AdmEvaluacion> evaluaciones	= (List<AdmEvaluacion>)  request.getAttribute("lisEvaluaciones");
	HashMap<String,String> mapaNotas	= (HashMap<String,String>) request.getAttribute("mapaNotas");	
	
	HashMap<String,AdmDocumento> mapaDocumentos				= (HashMap<String,AdmDocumento>)request.getAttribute("mapaDocumentos");
	
	HashMap<String,AdmRequisito> mapaRequisitosPorCarrera	= (HashMap<String,AdmRequisito>)request.getAttribute("mapaRequisitosPorCarrera");
	
	String fechaAdmision = Fecha.getDia(admProceso.getFechaAdmision())+" de "+ Fecha.getMesNombre(admProceso.getFechaAdmision())+" de "+Fecha.getYear(admProceso.getFechaAdmision());

	//Document document = new Document(PageSize.A4 ); //Crea un objeto para el documento PDF
	Rectangle rec = new Rectangle(14.0f , 21.0f);
	Document document = new Document(PageSize.LETTER);
	document.setMargins(25,20,50,30);
	String dir = "";
	
	try{
		File carpeta = new File(application.getRealPath("/WEB-INF/pdf/admisionEnLinea/"));
		if(!carpeta.exists()) carpeta.mkdirs();
		dir = carpeta+"/carta-"+folio+".pdf";
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Sistema Admision");
        document.addSubject("Admitido"+folio);
		document.open();
		
		/*Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_small.jpg");
	    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	    jpg.scaleAbsolute(90,90);
	    jpg.setAbsolutePosition(70, 660);
	    document.add(jpg);*/
	    
	    int r = 0, g = 0, b = 0;
	    
	    PdfPCell celda = null;
	     
	    PdfContentByte canvas = pdf.getDirectContentUnder();
	    
	    Image image = Image.getInstance(application.getRealPath("/imagenes/")+"/pdfBottom.png");
	    image.setAbsolutePosition(100f, 0f);
	    document.add(image);
	    
	    Image txtUm = Image.getInstance(application.getRealPath("/imagenes/")+"/textoUm.png");
	    txtUm.setAbsolutePosition(200f, 15f);
	    txtUm.scaleAbsolute(400,70);
	    document.add(txtUm);
	    
	    Image image2 = Image.getInstance(application.getRealPath("/imagenes/")+"/logoColor.jpg");
	    image2.scaleAbsolute(96,96);
	    image2.setAbsolutePosition(260f, 680f);
	    document.add(image2);
	    
	    Phrase fecha = new Phrase( fechaAdmision, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fecha, posX+85, posY+660, 0);
    	
	    String nombreAlumno = admSolicitud.getNombre()+" "+(admSolicitud.getApellidoPaterno()==null?"":admSolicitud.getApellidoPaterno())+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno());
	    Phrase alumno = new Phrase( nombreAlumno.toUpperCase(),  
	    		FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, new BaseColor(0,0,0)) );   	
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, alumno, posX+85, posY+630, 0);
	     
	    Phrase presente = new Phrase( "Presente", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(0,0,0)) );
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, presente, posX+85, posY+610, 0);
	     
       	//Tabla Invisible

    	float w = 80f;
    	float s = 400f;
		PdfPTable t = new PdfPTable(1);
		int tWidths[] = {100};
		t.setWidths(tWidths);
		t.setSpacingBefore(s);
		t.setWidthPercentage(w);    	
    	
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(12);
		t.addCell(celda);
		
		document.add(t);

	    //tabla hemos recibido
	    float tablaY = 125f;
    	float tablaX = 80f;
		PdfPTable tab2 = new PdfPTable(1);
		int tab2Widths[] = {100};
		tab2.setWidths(tab2Widths);
		tab2.setSpacingBefore(tablaY);
		tab2.setWidthPercentage(tablaX);
		
		String modalidad = admAcademico.getModalidad();
		boolean enLinea = false;
		if(modalidad.equals("1")){enLinea = false ;}
		else{ enLinea = true;} 
		
		String aceptado = "aceptado";
		
		if(admSolicitud.getGenero().equals("F")){
			aceptado = "aceptada";
		}
		
		Paragraph parrafo = new Paragraph();
        parrafo.add(new Phrase("\nHemos recibido su solicitud para ingresar a la "
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));  
        parrafo.add(new Phrase(facultadNombre+",", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(" dependiente de esta universidad, y tras analizarla, hacemos de su conocimiento que, de " +
        		"acuerdo a la puntuación obtenida en el examen de admisión, usted ha sido ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(aceptado, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(" en el programa de ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo.add(new Phrase(carreraNombre, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
        
	   	if(lisCondiciones.size() >= 1){
	   		if(lisCondiciones.size() >= 2){
	        	parrafo.add(new Phrase(", con las siguietes condiciones: \n"
	       		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
	   			for(AdmCarta carta : lisCondiciones){
	   				parrafo.add(new Phrase("            - "+carta.getCondicionNombre()+"\n"
	       			, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
				}
	   		}else if(lisCondiciones.size() >= 1){
	   			AdmCarta carta =  lisCondiciones.get(0);
		        parrafo.add(new Phrase(", con la siguiete condici�n: \n"
	        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
	    		parrafo.add(new Phrase("            - "+carta.getCondicionNombre()	
	        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
	   		}
	    }else {
	        parrafo.add(new Phrase(". "         		
	        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
	    }
   	
        celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
	
        Paragraph parrafo2 = new Paragraph();
        parrafo2.add(new Phrase("\nPara matricularse en el programa que ha sido "+aceptado+", utilice el número de registro "
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo2.add(new Phrase(admSolicitud.getMatricula()       		
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
        parrafo2.add(new Phrase(" que es su identificación institucional como estudiante. Recuerde que, "+
        		"solamente después de concluir la matrícula será formalmente un estudiante "+
        		"de la Universidad de Montemorelos.\n\n"
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo2);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);
		
        Paragraph parrafo3 = new Paragraph();
       	parrafo3.add(new Phrase("Por favor tenga en cuenta las siguientes fechas importantes: \n"
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
       	parrafo3.add(new Phrase(admSolicitud.getFechaIngreso(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo3);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);	
        
		Paragraph parrafo5 = new Paragraph();
		parrafo5.add(new Phrase("\nPara realizar su proceso de matrícula deberá entregar en Archivo Escolar los siguientes "+
				"documentos "
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));     			
		parrafo5.add(new Phrase("originales:"
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD|Font.UNDERLINE, new BaseColor(r,g,b))));     			
		celda = new PdfPCell(parrafo5);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		tab2.addCell(celda);		
				
		document.add(tab2);
		
	    //tabla de documentos
	    float aa = 10f;
	    float bb = 70f;
		PdfPTable tab33 = new PdfPTable(1);
		int tab33Widths[] = {100};
		tab33.setWidths(tab33Widths);
		tab33.setSpacingBefore(aa);
		tab33.setWidthPercentage(bb);
		
		String listaDocumentos = "";

		for(AdmDocAlum docAlum : lisDocumentos){
			AdmDocumento documento = new AdmDocumento();
			
			boolean existeModalidad = false; 
			
			if (mapaDocumentos.containsKey(docAlum.getDocumentoId())){
				documento = mapaDocumentos.get(docAlum.getDocumentoId());
				String modalidadDocumento = "";
				if(mapaRequisitosPorCarrera.containsKey(documento.getDocumentoId())){
					modalidadDocumento = mapaRequisitosPorCarrera.get(documento.getDocumentoId()).getModalidades();
				}
				
				String [] modalidades = modalidadDocumento.split("-");
				for(String mod : modalidades){
					if(mod.equals(modalidad)){
						existeModalidad = true;
						break;
					}
				}
				if(existeModalidad){
					listaDocumentos += "* "+documento.getDocumentoNombre()+" "+(docAlum.getComentario()==null||docAlum.getComentario().trim().equals("-") ? "" : "- "+docAlum.getComentario())+" \n";
				}						
			}						
		}
		
        Paragraph parrafo6 = new Paragraph();
        parrafo6.add(new Phrase(listaDocumentos, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10.5f, Font.ITALIC, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo6);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.NO_BORDER);
		tab33.addCell(celda);		
		
		document.add(tab33);
		
		//tabla de despedida
	    float x = 10f;
	    float z = 80f;
		PdfPTable tab4 = new PdfPTable(1);
		int tab4Widths[] = {100};
		tab4.setWidths(tab4Widths);
		tab4.setSpacingBefore(x);
		tab4.setWidthPercentage(z);
		
		Paragraph parrafo7 = new Paragraph();
		parrafo7.add(new Phrase("Nos sentiremos felices de tenerle con nosotros y deseamos que nuestro Dios le acompañe y le guíe "+
				"en sus planes educacionales.\n\n"
        		, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
		celda = new PdfPCell(parrafo7);
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		tab4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Atentamente,"
				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		tab4.addCell(celda);
		
		Image jpg = Image.getInstance(application.getRealPath("imagenes/firmas")+"/9800400.png");
		jpg.scaleAbsolute(90, 62);
		celda = new PdfPCell(jpg, false);
		celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
		celda.setBorder(Rectangle.NO_BORDER);
	    tab4.addCell(celda);
	    
		celda = new PdfPCell(new Phrase("Elizabeth Domínguez Hernández\nAdmisiones UM"
				, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setVerticalAlignment(Element.ALIGN_TOP);
		celda.setBorder(Rectangle.NO_BORDER);
		tab4.addCell(celda);

		document.add(tab4);
		
	}catch(IOException ioe){
		System.err.println("Error al generar la carta de admision en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	//Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}	
	
	String nombreArchivo = "carta-"+folio+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>