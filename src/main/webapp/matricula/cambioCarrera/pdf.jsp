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
<%@page import="aca.matricula.spring.CambioCarrera"%>
<%@page import="aca.matricula.spring.CambioMateria"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	CambioCarrera cambioCarrera = (CambioCarrera) request.getAttribute("cambioCarrera");
	AlumPersonal alumno 		= (AlumPersonal) request.getAttribute("alumno");

	List<AlumnoCurso> lisAlumnoCursoBaja	= (List<AlumnoCurso>) request.getAttribute("lisAlumnoCursoBaja");
	List<CargaAcademica> lisAlumnoCursoAlta	= (List<CargaAcademica>) request.getAttribute("lisAlumnoCursoAlta");
	
	HashMap<String,CatTipoCal> mapaTipoCal 			= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipoCal");
	HashMap<String, String> mapNombrePlan			= (HashMap<String, String>) request.getAttribute("mapNombrePlan");
	HashMap<String,CambioMateria> mapaCambioMateria	= (HashMap<String,CambioMateria>)request.getAttribute("mapaCambioMateria");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapCarreraPlan			= (HashMap<String,String>)request.getAttribute("mapCarreraPlan");
	
	String arrFecha[] = cambioCarrera.getFecha().split(" ");
	
	boolean esInscrito 			= (boolean) request.getAttribute("esInscrito");
	
	String carreraBaja = "";
	String carreraAlta = "";
	
	if(mapCarreraPlan.containsKey(cambioCarrera.getCarreraBaja())){
		carreraBaja = mapCarreraPlan.get(cambioCarrera.getCarreraBaja())+" ("+cambioCarrera.getCarreraBaja()+")";
	}

	if(mapCarreraPlan.containsKey(cambioCarrera.getCarreraAlta())){
		carreraAlta = mapCarreraPlan.get(cambioCarrera.getCarreraAlta())+" ("+cambioCarrera.getCarreraAlta()+")";
	}
	
	if(mapaCarreras.containsKey(carreraBaja)){
		carreraBaja = mapaCarreras.get(carreraBaja).getNombreCarrera();
	}

	if(mapaCarreras.containsKey(carreraAlta)){
		carreraAlta = mapaCarreras.get(carreraAlta).getNombreCarrera();
	}
	
	Rectangle rec = new Rectangle(14.0f , 21.0f);
	Document document = new Document(PageSize.LETTER);
	document.setMargins(25,20,135,30);
	String dir = "";
	
	BaseFont base = BaseFont.createFont("../../fonts/adventsanslogo.ttf", BaseFont.WINANSI,true);	
	
	int posX 		= 0;
	int posY		= 0;
	
	float size6		= 6f;
	float size7		= 7f;
	float size8		= 8f;
	float size9		= 9f;
	float size10	= 10f;
	float size11	= 11f;
	
	try{
		File carpeta = new File(application.getRealPath("/WEB-INF/pdf/cambioCarrera/"));
		if(!carpeta.exists()) carpeta.mkdirs();
		dir = carpeta+"/"+cambioCarrera.getSolicitudId()+".pdf";
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		
		Font fontAdvNormal8 	= new Font(base, 8f, Font.NORMAL);
        
		document.open();
		
		Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/flamaUm.png");
	    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	    jpg.scaleAbsolute(64,60);
	    jpg.setAbsolutePosition(140, posY+712);
	    
	    document.add(jpg);
	    
	    PdfContentByte canvas = pdf.getDirectContentUnder();
	    
	    Phrase titulo = new Phrase( "UNIVERSIDAD DE MONTEMORELOS\n",  new Font(base, 12, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, titulo, posX+315, posY+760, 0);  
    	
	    Phrase titulo2 = new Phrase( "VICERRECTORÍA ACADÉMICA\n",  new Font(base, 12, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, titulo2, posX+315, posY+742, 0);  
    	
	    Phrase titulo3 = new Phrase( "Solicitud de Cambio de Carrera\n",  new Font(base, 12, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, titulo3, posX+315, posY+708, 0);  
    	
    	Phrase fecha = new Phrase( "Fecha: "+arrFecha[0],  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, fecha, posX+315, posY+698, 0);
    	
    	Phrase nombre = new Phrase( "Nombre del alumno: ",  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, nombre, posX+115, posY+656, 0);
    	Phrase nombreDato = new Phrase( alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno(),  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, nombreDato, posX+125, posY+656, 0);
    	Phrase matricula = new Phrase( "No. Matricula: ",  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, matricula, posX+115, posY+668, 0);
    	Phrase matriculaDato = new Phrase( alumno.getCodigoPersonal(),  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, matriculaDato, posX+125, posY+668, 0);
    	Phrase baja = new Phrase( "Carrera que deja: ",  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, baja, posX+115, posY+644, 0);
    	Phrase bajaDato = new Phrase(carreraBaja,  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, bajaDato, posX+125, posY+644, 0);
    	Phrase alta = new Phrase( "Carrera nueva:",  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, alta, posX+115, posY+632, 0);
    	Phrase altaDato = new Phrase(carreraAlta,  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, altaDato, posX+125, posY+632, 0);
	    
    	Phrase espacio = new Phrase( "\n\n",  new Font(base, size8, Font.NORMAL)); 
    	document.add(espacio);

    	int r = 0, g = 0, b = 0;    
 	    PdfPCell celda = null;  		        
    		
 	   	int tabAncho[] = {2,5,4,24,3,3,2,3,3};
 	    PdfPTable table = new PdfPTable(9);
 	    table.setWidths(tabAncho);
 	    table.setWidthPercentage(100);
 	   	table.setSpacingBefore(20f);
 	    
 	   	Paragraph pa = new Paragraph();
	    
       	celda = new PdfPCell(new Phrase("Materias de la carrera que da de baja", new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
  		celda.setColspan(9);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda); 
  		
       	celda = new PdfPCell(new Phrase("#", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   		

  		celda = new PdfPCell(new Phrase("Acta", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);
  		
  		celda = new PdfPCell(new Phrase("Clave", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   		
		
		celda = new PdfPCell(new Phrase("Materia", new Font(base, size8, Font.BOLD) ));		
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Gpo.", new Font(base, size8, Font.BOLD) ));		
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
     
		celda = new PdfPCell(new Phrase("Blo.", new Font(base, size8, Font.BOLD) ));		
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Est.", new Font(base, size8, Font.BOLD) ));		
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
     
		celda = new PdfPCell(new Phrase("T.Cal.", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   	
  		
  		celda = new PdfPCell(new Phrase("Crs.", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   		
  		
  		int row = 0;
		double total 	= 0;
		String tipoCal		= "";

		for(AlumnoCurso curso : lisAlumnoCursoBaja){
	    	if(mapaCambioMateria.containsKey(curso.getCursoCargaId())){
		    	row++;
				total = total+Double.parseDouble(curso.getCreditos());
				
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(row), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
		   		pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getCursoCargaId()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		    	
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getCursoId().substring(8)), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getNombreCurso()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getGrupo()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getBloqueId()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getEstado()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				if(mapaTipoCal.containsKey(curso.getTipoCalId())){
					tipoCal = mapaTipoCal.get(curso.getTipoCalId()).getNombreCorto();
	   			}
				
				pa = new Paragraph();
		        pa.add(new Phrase(tipoCal, fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);

		   		pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getCreditos()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		    	
		    }
	    }
		
		celda = new PdfPCell(new Phrase("Total", new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
  		celda.setColspan(8);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda); 
  		
  		celda = new PdfPCell(new Phrase(String.valueOf(total), new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);
  		
  		celda = new PdfPCell(new Phrase("\n_____________________________\nFirma del Coordinador", new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
  		celda.setColspan(9);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);
	    	
  		document.add(table);
  		
  		int tabAncho2[] = {2,5,4,24,6,5,3};
 	    table = new PdfPTable(7);
 	    table.setWidths(tabAncho2);
 	    table.setWidthPercentage(100);
 	   	table.setSpacingBefore(20f);
 	    
 	   	pa = new Paragraph();
 	   	
 		celda = new PdfPCell(new Phrase("Materias de la carrera que da de alta", new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
  		celda.setColspan(7);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda); 
		
		celda = new PdfPCell(new Phrase("#", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   
  		
		celda = new PdfPCell(new Phrase("Acta", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   
  		
  		celda = new PdfPCell(new Phrase("Clave", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);
  		
		celda = new PdfPCell(new Phrase("Materia", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   
  		
		celda = new PdfPCell(new Phrase("Gpo", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   	
  		
  		
		celda = new PdfPCell(new Phrase("Bloque", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   	
  		
		celda = new PdfPCell(new Phrase("Crs", new Font(base, size8, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);   	
  		
  		row = 0;
		total 	= 0;
		tipoCal		= "";

		for(CargaAcademica curso : lisAlumnoCursoAlta){
	    	if(mapaCambioMateria.containsKey(curso.getCursoCargaId())){
		    	row++;
		    	total = total+Double.parseDouble(curso.getCreditos());

		    	pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(row), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);		
		   		
		   		pa = new Paragraph();
		        pa.add(new Phrase(curso.getCursoCargaId(), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
		   		pa = new Paragraph();
		        pa.add(new Phrase(curso.getCursoId().substring(8), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getNombreCurso()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getGrupo()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getBloqueId()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
	   		
				pa = new Paragraph();
		        pa.add(new Phrase(String.valueOf(curso.getCreditos()), fontAdvNormal8));  
		        celda = new PdfPCell(pa);
		   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.BOX);
		   		table.addCell(celda);
		    }
	    }
		
		celda = new PdfPCell(new Phrase("Total", new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
  		celda.setColspan(6);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda); 
  		
  		celda = new PdfPCell(new Phrase(String.valueOf(total), new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda); 
  		
  		celda = new PdfPCell(new Phrase("\n_____________________________\nFirma del Coordinador", new Font(base, size9, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
  		celda.setColspan(7);
		celda.setBorder(Rectangle.BOX);
  		table.addCell(celda);
	    	
  		document.add(table);
  		
  		
  		int tabAncho3[] = {50,50};
 	    table = new PdfPTable(2);
 	    table.setWidths(tabAncho3);
 	    table.setWidthPercentage(100);
 	   	table.setSpacingBefore(20f);
 	    
 	   	pa = new Paragraph();
 	   	
 		celda = new PdfPCell(new Phrase("Para uso exclusivo de la Administración", new Font(base, size10, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
  		celda.setColspan(2);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda); 

		celda = new PdfPCell(new Phrase("\nSaldo del estudiante $\n", new Font(base, size11, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda); 
 	    
		celda = new PdfPCell(new Phrase("\nLlevar a cuenta No.\n", new Font(base, size11, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda); 
 	    
		celda = new PdfPCell(new Phrase("\n____________________________\nDirección de Registro", new Font(base, size11, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda); 
 	    
		celda = new PdfPCell(new Phrase("\n_______________________________________\nDirección de Finanzas Estudiantiles", new Font(base, size11, Font.BOLD) ));
  		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda); 
 	    
  		document.add(table);
  		
  		
  		Phrase notaTitulo = new Phrase( "Nota: ",  new Font(base, size9, Font.BOLD)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, notaTitulo, posX+37, posY+50, 0);
  		Phrase nota = new Phrase( "Es responsabilidad del estudiante haber cumplido con cualquier requisito o trámite de Admisión de la carrera a la que desea ingresar.",  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, nota, posX+578, posY+50, 0);
  		Phrase nota2 = new Phrase( "La duración de este trámite dependerá de que se haya cumplido con esos requisitos.",  new Font(base, size9, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, nota2, posX+380, posY+40, 0);
  		Phrase nota3 = new Phrase( "Se deberá llenar una hoja original y dos copias. Una para la DAR, para Finanzas Estudiantiles y para el estudiante.",  new Font(base, size9, Font.BOLD)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, nota3, posX+300, posY+20, 0);
    	
	}catch(IOException ioe){
		System.err.println("Error al generar la carta de admision en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = cambioCarrera.getSolicitudId()+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>
