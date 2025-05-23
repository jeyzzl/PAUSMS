<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import = "java.util.List"%>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "aca.vista.spring.Estadistica"%>
<%@ page import = "aca.vista.spring.AlumnoCurso"%>
<%@ page import = "aca.carga.spring.CargaBloque"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@ page import = "com.itextpdf.kernel.pdf.canvas.PdfCanvas" %>

<% 	
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String cargaId 				= request.getParameter("CargaId");
	String carreraId 			= request.getParameter("CarreraId");
	String carreraNombre 		= (String) request.getAttribute("carreraNombre");
	List<Estadistica> lisAlumnos 				= (List<Estadistica>) request.getAttribute("lisAlumnos");
	List<AlumnoCurso> lisMaterias 				= (List<AlumnoCurso>) request.getAttribute("lisMaterias");
	HashMap<String, CargaBloque> mapaBloques 	= (HashMap<String, CargaBloque>) request.getAttribute("mapaBloques");
		
	File carpeta = new File(application.getRealPath("/WEB-INF/pdf/boleta/"));
	if(!carpeta.exists()) carpeta.mkdirs();	
	String dir 			= application.getRealPath("/WEB-INF/pdf/boleta/")+carreraId+".pdf";
	
	int r = 0, g = 0, b = 0;	
	
	float size0			= 6f;
	float size1			= 7.7f;
	float size2			= 9.7f;
	float size3			= 12f;	
	
	Document document = new Document(PageSize.LETTER);	
	document.setMargins(70,-25,20,20);
	
	try{
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addTitle("Titulo");
		document.addAuthor("Sistema Académico");
        document.addSubject("Boletas de "+cargaId+" - "+carreraId);  
        
        /******* Clase para sobreescribir el metodo OnStartPage *******/
		final class PageHeaderFooter extends PdfPageEventForwarder {
        	
			private String alumno;		
        	
        	public PageHeaderFooter(){
	        	 
	        }
        	
        	public void setAlumno(String alumno){
        		this.alumno = alumno;
        	}       		        	
	        
	        public void onStartPage(PdfWriter writer, Document document) {
	        	try {
	        		PdfContentByte canvas = writer.getDirectContentUnder();	
	        		
	        		Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo.jpg");
	    		    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	    		    jpg.scaleAbsolute(70,70);
	    		    jpg.setAbsolutePosition(20, 700);	    		    
	    		    document.add(jpg);
	        		
	    		    //Direccion de Admisiones
	        		Phrase fraseOficial = new Phrase( "Dirección de", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20,680, 0);  	        		
    		
	        		fraseOficial = new Phrase( "Admisiones y Registro", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 670, 0);
	        		
	        		fraseOficial = new Phrase( "Apdo. 16-5 C.P. 67530", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 660, 0);
	        		
	        		fraseOficial = new Phrase( "Montemorelos, Nl,", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 650, 0);
	        		
	        		fraseOficial = new Phrase( "México", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 640, 0);
	        		
	        		//telefonos
	        		fraseOficial = new Phrase( "Telefonos:", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 620, 0);
	        		
	        		fraseOficial = new Phrase( "Directo(826) 263-0908", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 610, 0);
	        		
	        		fraseOficial = new Phrase( "Conmutador 263-0900", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20,600, 0);
	        		
	          		fraseOficial = new Phrase( "Ext. 119,120,121", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 590, 0);
	        		
	        		fraseOficial = new Phrase( "Fax (826) 263-0979", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 580, 0);
	        		
	        		//creada 
	        		fraseOficial = new Phrase( "Creada", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 560, 0);
	        		
	        		fraseOficial = new Phrase( "por el Gobierno", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas,60,fraseOficial,45, 560, 0);
	        		
	        		fraseOficial = new Phrase( "del estado de Nuevo", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 550, 0);
	        		
	        		fraseOficial = new Phrase( "León, México, mediante", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20,540, 0);
	        		
	          		fraseOficial = new Phrase( "Resolución Oficial", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 530, 0);
	        		
	        		fraseOficial = new Phrase( "publicada el 5 de mayo", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 520, 0);
	        		
	        		fraseOficial = new Phrase( "de 1973.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 510, 0);
	        		
	        		//Clave Institucion
	        		fraseOficial = new Phrase( "Clave de la Institución", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 490, 0);
	        		
	        		fraseOficial = new Phrase( "ante la SEP y Dirección", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20, 480, 0);
	        		
	        		fraseOficial = new Phrase( "General de Estadística", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20,470, 0);
	        	
	        		fraseOficial = new Phrase( "19MSU1017U", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseOficial,20,460, 0);
	        	
	        		
	        		//CMYKColor magentaColor = new CMYKColor(0.f, 1.f, 0.f, 0.f);
	                canvas.setColorStroke(new BaseColor(0,0,0));
	                canvas.moveTo(120, 770);
	                canvas.lineTo(120, 300);	                
	                canvas.closePathStroke();       		      		
	        		
	   
	            }catch(Exception e) {
	                    System.out.println("Error en Metodo OnStartPage: "+e);
	            }
	        }            
        }		
		    
        PageHeaderFooter pageHeaderFooter = new PageHeaderFooter();      
        pageHeaderFooter.setAlumno("Alumno");       
	    pdf.setPageEvent(pageHeaderFooter);

        document.open();
        for( Estadistica alumno : lisAlumnos){       	        
        	//pageHeaderFooter.setAlumno("Alumno"+alumno.getCodigoPersonal());
        	String fechaInicio 	= "";
        	String fechaFinal 	= "";
        	String yearInicio	= "20"+cargaId.substring(0,2);
        	String yearFinal	= "20"+cargaId.substring(2,4);
        	if (mapaBloques.containsKey(alumno.getCargaId()+alumno.getBloqueId())){
        		fechaInicio = mapaBloques.get(alumno.getCargaId()+alumno.getBloqueId()).getInicioClases();
        		fechaFinal 	= mapaBloques.get(alumno.getCargaId()+alumno.getBloqueId()).getFFinal();
        	}
        	
        	String mesInicio 	= aca.util.Fecha.getNombreMes(fechaInicio);
        	String mesFinal 	= aca.util.Fecha.getNombreMes(fechaFinal);
        	
		    float headerwidths[] = {100f};
			PdfPTable topTable = new PdfPTable(headerwidths);
			topTable.getDefaultCell().setBorder(1);
			topTable.setTotalWidth(document.getPageSize().getWidth()-100);		
			topTable.setSpacingAfter(0f);
			
			PdfPCell cell 	= null;		
		    Phrase frase 	= new Phrase();
		    
		    frase= new Phrase("UNIVERSIDAD DE MONTEMORELOS, A.C.", FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0)));
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);		
			topTable.addCell(cell);		
		    
		    frase = new Phrase("BOLETA DE CALIFICACIONES", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0)));
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);		
			topTable.addCell(cell);		
			
			frase = new Phrase("Curso escolar "+yearInicio+" "+yearFinal+" - periodo "+mesInicio +" a "+mesFinal, FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0)));
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);		
			topTable.addCell(cell);			
			
			// Renglon vacio
			frase = new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0)));
			cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			cell.setBorder(0);		
			topTable.addCell(cell);
			
			//nombre Alumno
			frase = new Phrase("Matrícula: ",FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0)));
			frase.add(new Phrase(alumno.getCodigoPersonal(),FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
			frase.add(new Phrase("   Nombre: ",FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0))));			
			frase.add(new Phrase(alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+", "+alumno.getNombre(), 
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
			frase.add(new Phrase("   Plan: ",FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0))));
			frase.add(new Phrase(alumno.getPlanId(),FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
			
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);		
			topTable.addCell(cell);			
			
			document.add(topTable);
			
			float headerMat[] = {8f, 53f, 8f, 8f, 8f, 15f};
			PdfPTable matTable = new PdfPTable(headerMat);
			topTable.getDefaultCell().setBorder(1);
			topTable.setTotalWidth(document.getPageSize().getWidth()-100);		
			matTable.setSpacingBefore(10f);
			
			frase = new Phrase("N°",
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0)));	
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);		
			matTable.addCell(cell);
		
			frase = new Phrase("Clave y Nombre de la Materia", 
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0)));	
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);		
			matTable.addCell(cell);
			
			frase = new Phrase("Cred.", 
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0)));	
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);		
			matTable.addCell(cell);
			
			frase = new Phrase("Cal.", 
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0)));	
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);		
			matTable.addCell(cell);
			
			frase = new Phrase("Extra", 
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0)));	
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);		
			matTable.addCell(cell);
			
			frase = new Phrase("Anotaciones", 
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0)));	
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);		
			matTable.addCell(cell);
			int row = 0;
			for (AlumnoCurso curso : lisMaterias){				
				if (curso.getCodigoPersonal().equals(alumno.getCodigoPersonal())){
					row++;
					
					String anotacion = "";
					if (curso.getTipoCalId().equals("1")) anotacion = "ACREDITADO";
					else if (curso.getTipoCalId().equals("2")) anotacion = "NO ACREDITADO";
					else if (curso.getTipoCalId().equals("3")) anotacion = "BAJA";
					else if (curso.getTipoCalId().equals("4")) anotacion = "REPROBADO X AUSENCIAS";
					else if (curso.getTipoCalId().equals("5")) anotacion = "CAL. PENDIENTE";
					else if (curso.getTipoCalId().equals("6")) anotacion = "CAL. DIFERIDA";
					else if (curso.getTipoCalId().equals("I")) anotacion = "SIN EVALUAR";
					
					frase = new Phrase(String.valueOf(row), 
							FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0)));	
				    cell = new PdfPCell(frase);		
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);		
					matTable.addCell(cell);
					
					frase = new Phrase(curso.getCursoId().substring(8,15)+" "+curso.getNombreCurso(), 
							FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0)));	
				    cell = new PdfPCell(frase);		
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);		
					matTable.addCell(cell);
					
					frase = new Phrase(curso.getCreditos(), 
							FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0)));	
				    cell = new PdfPCell(frase);		
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);		
					matTable.addCell(cell);
					
					frase = new Phrase(curso.getNota(), 
							FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0)));	
				    cell = new PdfPCell(frase);		
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);		
					matTable.addCell(cell);
					
					frase = new Phrase(curso.getNotaExtra(), 
							FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0)));	
				    cell = new PdfPCell(frase);		
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);		
					matTable.addCell(cell);
					
					frase = new Phrase(anotacion, 
							FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0)));	
				    cell = new PdfPCell(frase);		
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);		
					matTable.addCell(cell);		
				}				
			}		
			frase = new Phrase("Fin de la boleta que incluye "+String.valueOf(row)+" materia(s) matriculada(s)", 
					FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL|Font.ITALIC, new BaseColor(0,0,0)));	
		    cell = new PdfPCell(frase);		
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.TOP);		
			cell.setColspan(6);
			matTable.addCell(cell);	
			
			document.add(matTable);
			
			document.newPage();
        }	
		
		document.close();			
	}catch(IOException ioe){
		document.close();
		System.err.println("Error certificado en PDF: "+ioe.getMessage());
	}
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = carreraId+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
 %>
 