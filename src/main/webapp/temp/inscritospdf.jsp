<%@ include file= "../con_enoc.jsp" %>
<% String idJsp = "000"; %>
<%@ include file= "../seguro.jsp" %>
<%@ include file= "../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.lowagie.text.*" %>
<%@ page import = "com.lowagie.text.pdf.*" %>
<%@ page import = "com.lowagie.text.html.*" %>

<jsp:useBean id="estinscritosU"  class="aca.vista.EstInscritosUtil" scope="page"/>
<%
ArrayList<aca.vista.EstInscritos> lisEstInscritos			=  estinscritosU.getListAll(conEnoc, "ORDER BY 2");
	String sBgcolor					= "";
	int i							= 0;
	int con							= 0;
	int nInternos					= 0;
	int nExternos					= 0;
	int nHombres					= 0;
	int nMujeres					= 0;
	
	Document document = new Document(PageSize.LETTER);
	
	try{
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream("F:\\academico\\temp\\inscritos.pdf"));
		document.addAuthor("Elipho");
        document.addSubject("Reporte Inscritos");
		HeaderFooter footer = new HeaderFooter(new Phrase("Pag. "), true);
        document.setFooter(footer);
        document.open();
        
        Tabla datatable = new Tabla(11);
            datatable.setBorder(0);
            datatable.setPadding(0);
            datatable.setSpacing(0);
            //datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] = {4,6,28,5,6,5,16,7,6,7,10};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            
            Cell cell = new Cell(new Phrase("Alumnos Inscritos", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(11);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(70, 150, 70));
            datatable.addCell(cell);
            
            Cell cel = new Cell(new Phrase("Nota: La edad se calcula a partir de la fecha de nacimiento registrada en los datos personales del alumno.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
            cel.setHorizontalAlignment(Element.ALIGN_LEFT);
            cel.setColspan(11);
            cel.setBorder(Rectangle.NO_BORDER);
            cel.setBackgroundColor(new Color(255, 255, 255));
            //cell.setBackgroundColor(new Color(0xEE, 0xEE, 0xEE));
            datatable.setDefaultCellBackgroundColor(new Color(255,255,255));
            datatable.addCell(cel);
            
            datatable.setDefaultCellBorderWidth(0);
            datatable.setDefaultHorizontalAlignment(1);
            
            //datatable.setDefaultCellBackgroundColor(new Color(0,0,255));
            datatable.addCell(new Phrase("N°", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Mat.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Nombre", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Edad", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Genero", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Resi.", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Rel.", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Tipo", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Carga", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Modo", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.addCell(new Phrase("Plan", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0,0,255))));
            datatable.setDefaultCellBackgroundColor(new Color(255,255,255));
            datatable.endHeaders();
            
            for(i=0; i<lisEstInscritos.size(); i++){
		con =i+1;
		aca.vista.EstInscritos estinscrito = (aca.vista.EstInscritos) lisEstInscritos.get(i);
		if (i % 2 == 0 ) 
			datatable.setDefaultCellBackgroundColor(new Color(255,255,255)); 
		else 
			datatable.setDefaultCellBackgroundColor(new Color(200,200,200)); 
		
		datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
		datatable.addCell(new Phrase(String.valueOf(con), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getCodigoPersonal(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getEdad(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getSexo(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getResidenciaId(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getNombreReligion(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getTipo(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getCargaId(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getModalidad(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		datatable.addCell(new Phrase(estinscrito.getPlanId(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
		
		
            	if (estinscrito.getResidenciaId().equals("I")){ nInternos++; }else{ nExternos++; }
		if (estinscrito.getSexo().equals("M")){nHombres++; }else{ nMujeres++; }
  			}
  			
  			cell = new Cell(new Phrase("Inscritos: "+i, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xEE, 0xEE, 0xEE));
            datatable.addCell(cell);
            
            cell = new Cell(new Phrase("Internos: "+nInternos, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xEE, 0xEE, 0xEE));
            datatable.addCell(cell);
            
            cell = new Cell(new Phrase("Externos: "+nExternos, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xEE, 0xEE, 0xEE));
            datatable.addCell(cell);
            
            cell = new Cell(new Phrase("Hombres: "+nHombres, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xEE, 0xEE, 0xEE));
            datatable.addCell(cell);
            
            cell = new Cell(new Phrase("Mujeres: "+nMujeres, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xEE, 0xEE, 0xEE));
            datatable.addCell(cell);
            
            document.add(datatable);
    	
	}catch(IOException ioe) {
		System.err.println("Error al hacer PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	lisEstInscritos			= null;
	estinscritosU			= null;
%>
<head>
<meta http-equiv='REFRESH' content='0; url=inscritos.pdf'>
</head>
<%@ include file = "../cierra_enoc.jsp"%>