<%@ page import = "java.awt.Color" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

<%@page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="aca.emp.spring.EmpContrato"%>
<%@ page import="aca.emp.spring.EmpHoras"%>
<%@page import="aca.catalogo.spring.CatCarrera"%> 

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 


// Es un solo pdf para todas las materias seleccionadas..
// El pdf debe llevar El nombre de la UM..
// El numero de contrato, las fechas de inicio y fin del contrato
// el nombre del maestro..
// La lista de materias que contempla..
// en una tabla con los datos de cada materia en un renglón

// En otro renglón resaltado deben aparecer los datos del Total a pagar y Días [16.0] - Salario diario [7,158.50] - Salario 15 [107,377.50]  - Salario 16 [114,536.00]
// Los datos del salario..

// y por ultimo una pequeña tabla, con la información de la distrubicion del gasto por escuelas..

	
	java.text.DecimalFormat formato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	EmpContrato empContrato		= (EmpContrato)request.getAttribute("empContrato");	
	String empleadoNombre		= (String)request.getAttribute("empleadoNombre");
	double totalContrato 		= (double)request.getAttribute("totalContrato");
	
	List<String> lisCarreras 				= (List<String>)request.getAttribute("lisCarreras");
	List<EmpHoras> lisMaterias 				= (List<EmpHoras>)request.getAttribute("lisMaterias");
	HashMap<String, String> mapaCursos		= (HashMap<String, String>)request.getAttribute("mapaCursos");
	HashMap<String,CatCarrera> mapaCarreras	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, String> mapaImportes	= (HashMap<String, String>)request.getAttribute("mapaImportes");
	
	float dias 		= aca.util.Fecha.diasEntreFechas(empContrato.getFechaIni(), empContrato.getFechaFin()) + 1;  
	float salario 	= 0;
	if (Float.valueOf(empContrato.getCosto()) > 0 && dias > 0){
		salario	= Float.valueOf(empContrato.getCosto()) / dias;
	}
	
	float salario15 = salario * 15;
	float salario16 = salario * 16;
	float salario30 = salario * 30;	
	
	String frase30dias = "";
	if (salario30 < Double.valueOf(empContrato.getCosto())){
		frase30dias = "Salario 30: "+formato.format(salario30);
	}
	
	int posX 		= 0;
	int posY		= 0;
	
	float size7		= 7f;
	float size8		= 8f;
	float size9		= 9f;
	float size11	= 11f;
	
	//Creación de la fuente
	//Document document = new Document(PageSize.A4); //Crea un objeto para el documento PDF
	Rectangle rec = new Rectangle(14.0f , 21.0f);
	Document document = new Document(PageSize.LETTER);
	document.setMargins(25,20,135,30);
	String dir = "";
	BaseFont base = BaseFont.createFont("../../fonts/adventsanslogo.ttf", BaseFont.WINANSI,true);	
	Font fontAdvNormal11 	= new Font(base, 11f, Font.NORMAL);
	Font fontAdvNormal10 	= new Font(base, 10f, Font.NORMAL);
	Font fontAdvNormal9 	= new Font(base, 9f, Font.NORMAL);
	Font fontAdvNormal7 	= new Font(base, 7f, Font.NORMAL);
	Font fontAdvNormal6 	= new Font(base, 6f, Font.NORMAL);
	
	Font fontAdvBold11 		= new Font(base, 11f, Font.BOLD);
	Font fontAdvBold10 		= new Font(base, 10f, Font.BOLD);
	Font fontAdvBold9 		= new Font(base, 9f, Font.BOLD);
	Font fontAdvBold7 		= new Font(base, 7f, Font.BOLD);
	Font fontAdvBold6 		= new Font(base, 6f, Font.BOLD);
	
	try{
		File carpeta = new File(application.getRealPath("/WEB-INF/pdf/contrato/"));
		if(!carpeta.exists()) carpeta.mkdirs();
		dir = carpeta+"/"+empContrato.getContratoId()+".pdf";
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addTitle("Universidad de Montemorelos, A.C.");
		document.addAuthor("Sistema Académico");	
        document.addSubject("Contrato de "+empContrato.getCodigoPersonal());
        
		document.open();
		
		if(empContrato.getInstitucion().equals("UM")){
	 		Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logoUniv.jpg");
	 	    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
	 	    jpg.scaleAbsolute(250,60);
	 	    jpg.setAbsolutePosition(187, posY+712);
	 	    document.add(jpg);
			
		}else{
			Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logoCovoprom.png");
		    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
		    jpg.scaleAbsolute(64,60);
		    jpg.setAbsolutePosition(282, posY+712);
		    //jpg.setAbsolutePosition(posX+65, posY+732);
		    document.add(jpg);
		}

		
		PdfContentByte canvas = pdf.getDirectContentUnder();
		
		Phrase contrato = new Phrase( "Contrato de materias\n",  new Font(base, 12, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, contrato, posX+315, posY+694, 0);    	
    	
    	Phrase empleado = new Phrase(empContrato.getCodigoPersonal()+" - "+empleadoNombre,  new Font(base, size8, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, empleado, posX+315, posY+680, 0);
    	
    	Phrase datos = new Phrase( " Folio: "+empContrato.getContratoId()+"   Fecha Inicio: "+empContrato.getFechaIni()+"   Fecha Final:"+empContrato.getFechaFin(),  new Font(base, size8, Font.NORMAL)); 
    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, datos, posX+315, posY+668, 0);
		
	    int r = 0, g = 0, b = 0;    
	    PdfPCell celda = null;  		        
   		
	   	int tabAncho[] = {3,52,10,10,5,5,6,6,9};
	    PdfPTable table = new PdfPTable(9);
	    table.setWidths(tabAncho);
	    table.setWidthPercentage(100);
	    //table.setTotalWidth(document.getPageSize().getWidth());
	    //table.setSpacingBefore(150f);
	    
	    Paragraph pa = new Paragraph();
	    
        celda = new PdfPCell(new Phrase("#", new Font(base, size9, Font.BOLD) ));
   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
   		table.addCell(celda);   		
		
		celda = new PdfPCell(new Phrase("Materia", new Font(base, size9, Font.BOLD) ));		
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Inicio", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Fin", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Frec.", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Sem.", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Hrs", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Precio", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
		
		pa 		= new Paragraph();
		pa.add(new Phrase("Total", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);
        
		int row = 0;
		double totalHoras = 0;
		double total = 0;
	    for(EmpHoras empHoras : lisMaterias){
			double horas = 0;
	    	row++;
	    	String materia = "-";
	    	if(mapaCursos.containsKey(empHoras.getCursoId())){
	    		materia = mapaCursos.get(empHoras.getCursoId());
	    	}

	    	total = Double.valueOf(empHoras.getFrecuencia())*Double.valueOf(empHoras.getSemanas())*Double.valueOf(empHoras.getPrecio());
			pa = new Paragraph();
	        pa.add(new Phrase(String.valueOf(row), fontAdvNormal9));  
	        celda = new PdfPCell(pa);
	   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
	   		table.addCell(celda);
	   		
	   		pa 		= new Paragraph();
	   		if(empHoras.getMateria().equals("-") && !materia.equals("000000000000000")){
	   			pa.add(new Phrase(materia, fontAdvNormal9));	
	   		}else if(materia.equals("-")){
	   			pa.add(new Phrase(empHoras.getMateria(), fontAdvNormal9));
	   		}else if(!empHoras.getMateria().equals("-") && !materia.equals("000000000000000")){
	   			pa.add(new Phrase(materia +" ( "+empHoras.getMateria()+" )", fontAdvNormal9));
	   		}
			
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
	
			pa 		= new Paragraph();
			pa.add(new Phrase(empHoras.getFechaIni(), fontAdvNormal9));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda); 		
			
			pa 		= new Paragraph();
			pa.add(new Phrase(empHoras.getFechaFin(), fontAdvNormal9));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			pa 		= new Paragraph();
			pa.add(new Phrase(empHoras.getFrecuencia(), fontAdvNormal9));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			pa 		= new Paragraph();
			pa.add(new Phrase(empHoras.getSemanas(), fontAdvNormal9));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			horas = Double.valueOf(empHoras.getFrecuencia())*Double.valueOf(empHoras.getSemanas());

			pa 		= new Paragraph();
			pa.add(new Phrase(formato.format(horas), fontAdvNormal9));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			pa 		= new Paragraph();
			pa.add(new Phrase(empHoras.getPrecio(), fontAdvNormal9));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			pa 		= new Paragraph();
			pa.add(new Phrase(String.valueOf(formato.format(Double.valueOf(total))), fontAdvNormal9));
			celda = new PdfPCell(pa);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorder(Rectangle.BOX);
			table.addCell(celda);
			
			totalHoras = horas+totalHoras;
			
		}
	    
	    pa 		= new Paragraph();
		pa.add(new Phrase("TOTAL  ", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(6);
		table.addCell(celda);	    
	    
	    pa 		= new Paragraph();
		pa.add(new Phrase(String.valueOf(totalHoras), fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);    

		pa 		= new Paragraph();
		pa.add(new Phrase("", fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);    

		pa 		= new Paragraph();
		pa.add(new Phrase(formato.format(Double.valueOf(empContrato.getCosto())), fontAdvBold9));
		celda = new PdfPCell(pa);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table.addCell(celda);    
	    
	    document.add(table);
	    
	    
	    int tabAncho2[] = {3,65,15,15};
	    PdfPTable table2 = new PdfPTable(4);
	    table2.setWidths(tabAncho2);
	    table2.setWidthPercentage(100);
	    //table2.setTotalWidth(document.getPageSize().getWidth());
	    table2.setSpacingBefore(20f);
	    
	    Paragraph pa2 = new Paragraph();
        pa2.add(new Phrase("#", fontAdvBold9));  
        celda = new PdfPCell(pa2);
   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
   		table2.addCell(celda);
   		
   		pa2 		= new Paragraph();
		pa2.add(new Phrase("Carrera", fontAdvBold9));
		celda = new PdfPCell(pa2);
		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
		table2.addCell(celda);

		pa2 		= new Paragraph();
		pa2.add(new Phrase("Importe", fontAdvBold9));
		celda = new PdfPCell(pa2);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table2.addCell(celda);
		
		pa2 		= new Paragraph();
		pa2.add(new Phrase("Porcentaje (%)", fontAdvBold9));
		celda = new PdfPCell(pa2);
		celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celda.setBorder(Rectangle.BOX);
		table2.addCell(celda);
		
		int num = 0;
	    for(String carrera : lisCarreras){
	    	num++;
	    	String carreraCorto 	= "-";
			String carreraNombre	= "-"; 
			if (mapaCarreras.containsKey(carrera)){
				carreraNombre 	= mapaCarreras.get(carrera).getNombreCarrera();
				carreraCorto 	= mapaCarreras.get(carrera).getNombreCorto();
			}
			
			String importe = "0";
			if (mapaImportes.containsKey(carrera)){
				importe = mapaImportes.get(carrera);
			}
			
			double porcentaje = 0;
			if (totalContrato>0) porcentaje = Double.valueOf(importe)*100/totalContrato;
			
			pa2 = new Paragraph();
			pa2.add(new Phrase(String.valueOf(num), fontAdvNormal9));  
	        celda = new PdfPCell(pa2);
	   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table2.addCell(celda);
	   		
	   		pa2 		= new Paragraph();
	   		pa2.add(new Phrase(carreraNombre +" ( "+carreraCorto+" )", fontAdvNormal9));
			celda = new PdfPCell(pa2);
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.BOX);
			table2.addCell(celda);
	
			pa2 		= new Paragraph();
			pa2.add(new Phrase(formato.format(Double.parseDouble(importe)), fontAdvNormal9));
			celda = new PdfPCell(pa2);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorder(Rectangle.BOX);
			table2.addCell(celda); 		
			
			pa2 		= new Paragraph();
			pa2.add(new Phrase(String.valueOf(formato.format(porcentaje)), fontAdvNormal9));
			celda = new PdfPCell(pa2);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorder(Rectangle.BOX);
			table2.addCell(celda);
		}
	    
		document.add(table2);
		
		int tabAncho3[] = {100};
	    PdfPTable table3 = new PdfPTable(1);
	    table3.setWidths(tabAncho3);
	    table3.setWidthPercentage(100);
	    //table.setTotalWidth(document.getPageSize().getWidth());
	    table3.setSpacingBefore(10f);	    
	    
	    Paragraph pa3 = new Paragraph();
	    
        pa3.add(new Phrase("Días: "+ (int)dias+",  Salario diario: "+formato.format(salario)+",  Salario 15: "+formato.format(salario15)+
        		",  Salario 16: "+formato.format(salario16)+",  "+frase30dias, fontAdvBold9));        
        celda = new PdfPCell(pa3);
   		celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celda.setBorder(Rectangle.BOX);
   		table3.addCell(celda);
   		
   		document.add(table3);
   		
   		Image marcaAgua = Image.getInstance(application.getRealPath("/imagenes/")+"/flama2.png");
		marcaAgua.scaleAbsolute(170,220);
		marcaAgua.setAbsolutePosition(posX+240, 0f);
		//marcaAgua.scaleAbsolute(600,300);
		//marcaAgua.setAbsolutePosition(0f, 0f);		
		marcaAgua.setAlignment(Element.ALIGN_CENTER);
		
		canvas.addImage(marcaAgua);	
	   		
		
	}catch(IOException ioe){
		System.err.println("Error al generar la carta de admision en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = empContrato.getContratoId()+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
	
%> 