<%@ page import = "java.util.List"%>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "aca.catalogo.spring.CatPais"%>
<%@ page import = "aca.catalogo.spring.CatEstado"%>
<%@ page import = "aca.catalogo.spring.CatCiudad"%>
<%@ page import = "aca.catalogo.spring.CatReligion"%>
<%@ page import = "aca.admision.spring.AdmEstudio"%>
<%@ page import = "aca.admision.spring.AdmAcademico"%>
<%@ page import = "aca.admision.spring.AdmSolicitud"%>
<%@ page import = "aca.admision.spring.AdmSalud"%>
<%@ page import = "aca.admision.spring.AdmPadres"%>
<%@ page import = "aca.admision.spring.AdmTutor"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "java.io.File" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

<%
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	AdmAcademico admAcademico 	= (AdmAcademico)request.getAttribute("admAcademico");
	AdmSolicitud admSolicitud 	= (AdmSolicitud)request.getAttribute("admSolicitud");
	AdmSalud admSalud 			= (AdmSalud)request.getAttribute("admSalud");
	AdmPadres admPadres			= (AdmPadres)request.getAttribute("admPadres");
	AdmTutor admTutor 			= (AdmTutor)request.getAttribute("admTutor");
	String paisNombre			= (String)request.getAttribute("paisNombre");
	String estadoNombre			= (String)request.getAttribute("estadoNombre");
	String ciudadNombre			= (String)request.getAttribute("ciudadNombre");
	String nacionalidad			= (String)request.getAttribute("nacionalidad");
	String religionNombre		= (String)request.getAttribute("religionNombre");
	String edad					= (String)request.getAttribute("edad");
	String carreraNombre		= (String)request.getAttribute("carreraNombre");
	
	List<AdmEstudio> lisEstudios 				= (List<AdmEstudio>)request.getAttribute("lisEstudios");
	
	HashMap<String,CatPais> mapPaises 			= (HashMap<String,CatPais>)request.getAttribute("mapPaises");
	HashMap<String,CatEstado> mapEstados 		= (HashMap<String,CatEstado>)request.getAttribute("mapEstados");
	HashMap<String,CatCiudad> mapCiudades	 	= (HashMap<String,CatCiudad>)request.getAttribute("mapCiudades");
	HashMap<String,CatReligion> mapReligiones 	= (HashMap<String,CatReligion>)request.getAttribute("mapReligiones");

	String pais		= "";
	String estado 	= "";
	String ciudad	= "";
	String calle	= "";
	String numero 	= "";
	String colonia	= "";
	String cp		= "";
	String tel		= "";	
	
	int paginaAnterior = 0;
	
	//------PDF----->	
	Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
	document.setMargins(-30,-30,30,30);
	
	try{		
		File carpeta = new File(application.getRealPath("/pdf/admisionEnLinea/"));
		if(!carpeta.exists()) carpeta.mkdirs();
		String dir = carpeta+"/"+folio+".pdf";
		
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Admisiones");
        document.addSubject("Solicitud: "+"1");
                
        document.open();
        
        PdfContentByte canvas = pdf.getDirectContentUnder();
       
        //Datos de la fotografia
        
    	PdfPTable datosUM = new PdfPTable(1);
		int datosUMWidths[] = {100};
		datosUM.setWidths(datosUMWidths);
		datosUM.setTotalWidth(200f);
		
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
        
        Paragraph parrafo = new Paragraph();
        parrafo.add(new Phrase("Pegue una fotografía\nreciente en este\nespacio\n\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
        parrafo.add(new Phrase("Pase a recent\nphotograph in this\nspace"
        		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		datosUM.addCell(celda);
        
		datosUM.writeSelectedRows(0, -1, 420, 740, pdf.getDirectContent());
		
		//Lineas de la foto
    	
    	PdfPTable raya = new PdfPTable(1);
		int rayaWidths[] = {100};
		raya.setWidths(rayaWidths);
		raya.setTotalWidth(200f);
        
        Paragraph parrafo2 = new Paragraph();
        parrafo2.add(new Phrase("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n|"
        		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo2);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		raya.addCell(celda);
        
		raya.writeSelectedRows(0, -1, 375, 765, pdf.getDirectContent());
		
		   //raya2
		PdfPTable raya2 = new PdfPTable(1);
		int raya2Widths[] = {100};
		raya2.setWidths(raya2Widths);
		raya2.setTotalWidth(200f);
        
        Paragraph parrafo3 = new Paragraph();
        parrafo3.add(new Phrase("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n|"
        		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
        celda = new PdfPCell(parrafo3);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		raya2.addCell(celda);
        
		raya2.writeSelectedRows(0, -1, 465, 765, pdf.getDirectContent());
		
      	//Logo Um
        
       	Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/logo_large.jpg");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(90, 90);
        jpg.setAbsolutePosition(40, 670);
        document.add(jpg);

        //Encabezado

		PdfPTable table = new PdfPTable(1);
		int tWidths[] = {100};
		table.setWidths(tWidths);
		table.setSpacingBefore(100f);
		table.setWidthPercentage(200f);    	
    	
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);;
		table.addCell(celda);
		
		celda = new PdfPCell(new Phrase("UNIVERSIDAD DE MONTEMORELOS, A.C.", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);;
		table.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Solicitud de Admisión/ Admission Form", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table.addCell(celda);
		
		document.add(table);
		
		//Informacion Personal
		
		PdfPTable table2 = new PdfPTable(1);
		int tWidths2[] = {100};
		table2.setWidths(tWidths2);
		table2.setSpacingBefore(70f);
		table2.setWidthPercentage(200f);    	
		
		celda = new PdfPCell(new Phrase("Información Personal/ Personal Information"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table2.addCell(celda);
		
		document.add(table2);
		
		//Nombre/Name
		
		PdfPTable table3 = new PdfPTable(3);
		int tWidths3[] = {33,33,33};
		table3.setWidths(tWidths3);
		table3.setSpacingBefore(5f);
		table3.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("1. Nombre/ Name"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(3);
		table3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getApellidoPaterno()==null ? "" : admSolicitud.getApellidoPaterno()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getApellidoMaterno()==null ? "" : admSolicitud.getApellidoMaterno()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("APELLIDO PATERNO/ LAST NAME"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("APELLIDO MATERNO/ MOTHER´S MAIDEN NAME"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table3.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRE(S)/ NAME(S)"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table3.addCell(celda);
		
		document.add(table3);
		
		//Lugar de Nacimiento / Place of Birth		
		PdfPTable table4 = new PdfPTable(4);
		int tWidths4[] = {25,25,25,25};
		table4.setWidths(tWidths4);
		table4.setSpacingBefore(5f);
		table4.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("2. Lugar de Nacimiento/ Place of Birth"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(ciudadNombre
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(estadoNombre
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(paisNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(nacionalidad, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CIUDAD/ COUNTRY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("ESTADO/ STATE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("PAÍS/ COUNTRY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NACIONALIDAD/ NATIONALITY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table4.addCell(celda);
		
		document.add(table4);
		
		//Fecha de Nacimiento / Date of Birth
		
		PdfPTable table5 = new PdfPTable(6);
		int tWidths5[] = {25,10,16,8,23,14};
		table5.setWidths(tWidths5);
		table5.setSpacingBefore(5f);
		table5.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("3. Fecha de Nacimiento/ Date of Birth"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(6);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getFechaNac()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(edad, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		String estadoCivil = "";
		if(admSolicitud.getEstadoCivil()!=null){
			if(admSolicitud.getEstadoCivil().equals("C")){
				if(admSolicitud.getGenero().equals("M"))estadoCivil="Casado";
				else estadoCivil="Casada";
			}
			if(admSolicitud.getEstadoCivil().equals("S")){
				if(admSolicitud.getGenero().equals("M"))estadoCivil="Soltero";
				else estadoCivil="Soltera";
			}
			if(admSolicitud.getEstadoCivil().equals("V")){
				if(admSolicitud.getGenero().equals("M"))estadoCivil="Viudo";
				else estadoCivil="Viuda";
			}
			if(admSolicitud.getEstadoCivil().equals("D")){
				if(admSolicitud.getGenero().equals("M"))estadoCivil="Divorciado";
				else estadoCivil="Divorciada";
			}
		}
		celda = new PdfPCell(new Phrase(estadoCivil
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getGenero()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		int colspan = 1;
		if(!admSolicitud.getReligionId().equals("1"))colspan=2;
		celda = new PdfPCell(new Phrase(religionNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setColspan(colspan);
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		if(admSolicitud.getReligionId().equals("1")){
		String bautizado = "-";
		if(admSolicitud.getBautizado()!=null){
			if(admSolicitud.getBautizado().equals("S"))bautizado="Sí";
			if(admSolicitud.getBautizado().equals("N"))bautizado="No";
		}
		celda = new PdfPCell(new Phrase(bautizado
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		}
		
		celda = new PdfPCell(new Phrase("DÍA/ DAY       MES/ MONTH       AÑO/ YEAR"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("EDAD/ AGE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("ESTADO CIVIL/ MARITAL STATUS"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("SEXO/ SEX"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase("RELIGIÓN/ RELIGION"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		celda.setColspan(colspan);
		table5.addCell(celda);
		
		if(admSolicitud.getReligionId().equals("1")){
		
		celda = new PdfPCell(new Phrase("BAUTIZADO/ BAPTIZED"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table5.addCell(celda);
		
		}
		
		document.add(table5);
		
		//Domicilio Legal permanente		
		calle = admTutor.getCalle()==null?"":admTutor.getCalle();
		numero =admTutor.getNumero()==null?"":admTutor.getNumero();
		colonia = admTutor.getColonia()==null?"":admTutor.getColonia();
		cp = admTutor.getCodigoPostal()==null?"":admTutor.getCodigoPostal();
		ciudad = admTutor.getCiudadId()==null?"0":admTutor.getCiudadId();
		estado = admTutor.getEstadoId()==null?"0":admTutor.getEstadoId();
		pais = admTutor.getPaisId()==null?"0":admTutor.getPaisId();
		tel = admTutor.getTelefono()==null?"":admTutor.getTelefono();
		
		
		PdfPTable table6 = new PdfPTable(3);
		int tWidths6[] = {40,19,40};
		table6.setWidths(tWidths6);
		table6.setSpacingBefore(5f);
		table6.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("4. Domicilio legal permanente para recibir correspondencia/ Permanent Mailing Address"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(3);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase(calle
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase(numero
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase(colonia
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CALLE/ STREET"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NÚMERO/ NUMBER"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase("COLONIA/ ZONE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table6.addCell(celda);
		
		document.add(table6);
		
		//Segunda y TERCERA Linea de Domicilio Legal permanente
		
		PdfPTable table7 = new PdfPTable(3);
		int tWidths7[] = {33,33,33};
		table7.setWidths(tWidths7);
		table7.setSpacingBefore(0f);
		table7.setWidthPercentage(80f);
		
		String ciudadDomicilio = "-";
		if (mapCiudades.containsKey(pais+estado+ciudad)){
			ciudadDomicilio = mapCiudades.get(pais+estado+ciudad).getNombreCiudad();
		}
		
		celda = new PdfPCell(new Phrase(ciudadDomicilio, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		String estadoDomicilio = "-";
		if (mapEstados.containsKey(pais+estado)){
			estadoDomicilio = mapEstados.get(pais+estado).getNombreEstado();
		}
		
		celda = new PdfPCell(new Phrase( estadoDomicilio, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		String paisDomicilio = "-";
		if (mapPaises.containsKey(pais)){
			paisDomicilio = mapPaises.get(pais).getNombrePais();
		}
		
		celda = new PdfPCell(new Phrase(paisDomicilio, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("MUNICIPIO/ CITY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("ESTADO/ STATE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("PAÍS/ COUNTRY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(cp
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tel
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getEmail()==null?"":admSolicitud.getEmail()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CÓDIGO POSTAL/ ZIP CODE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TELÉFONO/ TELEPHONE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CORREO ELECTRÓNICO/ E-MAIL"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table7.addCell(celda);
		
		document.add(table7);
		
		//Informacion academica y...... carrera/programa que desea cursar y fecha de inicio
		
		PdfPTable table8 = new PdfPTable(2);
		int tWidths8[] = {70,30};
		table8.setWidths(tWidths8);
		table8.setSpacingBefore(20f);
		table8.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("Información Académica/ Academia Information"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		table8.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		table8.addCell(celda);
		
		celda = new PdfPCell(new Phrase("5. Carrera/ Programa que desea cursar y fecha de inicio/ Program You Want To Study and Beginning Date"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		table8.addCell(celda);
		
		celda = new PdfPCell(new Phrase(carreraNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table8.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admAcademico.getFecha()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table8.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRE DE LA CARRERA QUE DESEAS ESTUDIAR/ NAME OF PROGRAM YOU WISH TO STUDY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table8.addCell(celda);
		
		celda = new PdfPCell(new Phrase("AÑO Y PERÍODO/ YEAR AND TERM"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table8.addCell(celda);
		
		document.add(table8);
		
		//Bachillerato/High School
		
		PdfPTable table9 = new PdfPTable(4);
		int tWidths9[] = {32,35,25,8};
		table9.setWidths(tWidths9);
		table9.setSpacingBefore(5f);
		table9.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("6. Antecedentes Académicos/ Academic Background"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(4);
		table9.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("TÍTULO/ TITLE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table9.addCell(celda);
		
		celda = new PdfPCell(new Phrase("INSTITUCIÓN/ INSTITUTION"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table9.addCell(celda);
		
		celda = new PdfPCell(new Phrase("PAÍS/ COUNTRY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table9.addCell(celda);
		
		celda = new PdfPCell(new Phrase("COMPLETO/ COMPLETE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table9.addCell(celda);		
		
		for(int i=0; i<lisEstudios.size(); i++){
			AdmEstudio tmp = (AdmEstudio)lisEstudios.get(i);
			
			celda = new PdfPCell(new Phrase(tmp.getTitulo()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			table9.addCell(celda);
			
			celda = new PdfPCell(new Phrase(tmp.getInstitucion()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			table9.addCell(celda);
			
			String estudioPais = "-";
			if (mapPaises.containsKey(tmp.getPaisId())){
				estudioPais = mapPaises.get(tmp.getPaisId()).getNombrePais();
			}
			
			celda = new PdfPCell(new Phrase(estudioPais, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			table9.addCell(celda);
			
			celda = new PdfPCell(new Phrase(tmp.getCompleto().equals("S")?"Sí":"No"
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.BOX);
			table9.addCell(celda);
		}
		document.add(table9);
		
		//Informacion sobre salud
		
		PdfPTable table12 = new PdfPTable(2);
		int tWidths12[] = {50,50};
		table12.setWidths(tWidths12);
		table12.setSpacingBefore(5f);
		table12.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("8. Información sobre su salud/ Health Information"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(3);
		table12.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSalud.getEnfermedad()==null?"":admSalud.getEnfermedad()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSalud.getEnfermedadDato()==null?"":admSalud.getEnfermedadDato().equals("S")?"Sí":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("ENFERMEDAD CRÓNICA/ CHRONIC AILMENT"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		celda = new PdfPCell(new Phrase("SI LA RESPUESTA ES AFIRMATIVA, POR FAVOR EXPLIQUE/ IF ANSWER IS AFFIRMATIVE, PLEASE EXPLAIN "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase(admSalud.getImpedimentoDato()==null?"":admSalud.getImpedimentoDato().equals("S")?"Sí":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSalud.getImpedimentoDato()==null?"":admSalud.getImpedimentoDato()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		
		celda = new PdfPCell(new Phrase("IMPEDIMENTO FÍSICO/ PHYSICAL IMPEDIMENT"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		celda = new PdfPCell(new Phrase("SI LA RESPUESTA ES AFIRMATIVA, POR FAVOR EXPLIQUE/ IF ANSWER IS AFFIRMATIVE, PLEASE EXPLAIN "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table12.addCell(celda);
		
		document.add(table12);
		
		//Informacion sobre el padre
		
		PdfPTable table13 = new PdfPTable(5);
		int tWidths13[] = {20,20,20,20,20};
		table13.setWidths(tWidths13);
		table13.setSpacingBefore(20f);
		table13.setWidthPercentage(80f);
		
		celda = new PdfPCell(new Phrase("Información sobre sus padres/ Information About Your Parents"
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase("9. Padre/ Father"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getPadreNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getPadreApellido()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		String padreReligion = "-";
		if (mapReligiones.containsKey(admPadres.getPadreReligion())){
			padreReligion = mapReligiones.get(admPadres.getPadreReligion()).getNombreReligion();
		}
		celda = new PdfPCell(new Phrase( padreReligion, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		String padreNacionalidad = "-";
		if (mapPaises.containsKey(admPadres.getPadreNacionalidad())){
			padreNacionalidad = mapPaises.get(admPadres.getPadreNacionalidad()).getNacionalidad();
		}
		
		celda = new PdfPCell(new Phrase(padreNacionalidad, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getPadreOcupacion()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRE/ NAME"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase("APELLIDO/ LAST NAME"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" RELIGIÓN "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NACIONALIDAD/ NATIONALITY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase("OCUPACIÓN/ OCCUPATION"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table13.addCell(celda);
	
		document.add(table13);
		
		//Informacion sobre la madre
		
		PdfPTable table14 = new PdfPTable(5);
		int tWidths14[] = {20,20,20,20,20};
		table14.setWidths(tWidths14);
		table14.setSpacingBefore(5f);
		table14.setWidthPercentage(80f);
		
		celda = new PdfPCell(new Phrase("10. Madre/ Mother"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getMadreNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getMadreApellido()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		String madreReligion = "-";
		if (mapReligiones.containsKey(admPadres.getMadreReligion())){
			madreReligion = mapReligiones.get(admPadres.getMadreReligion()).getNombreReligion();
		}
		
		celda = new PdfPCell(new Phrase(madreReligion, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		String madreNacionalidad = "-";
		if (mapPaises.containsKey(admPadres.getMadreNacionalidad())){
			madreNacionalidad = mapPaises.get(admPadres.getMadreNacionalidad()).getNacionalidad();
		}
		
		celda = new PdfPCell(new Phrase(madreNacionalidad, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getMadreOcupacion()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRE/ NAME"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase("APELLIDO/ LAST NAME"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" RELIGIÓN "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NACIONALIDAD/ NATIONALITY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase("OCUPACIÓN/ OCCUPATION"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table14.addCell(celda);
		
		document.add(table14);
		
		//Informacion sobre la forma de pago
		
		PdfPTable table15 = new PdfPTable(5);
		int tWidths15[] = {10,10,10,10,60};
		table15.setWidths(tWidths15);
		table15.setSpacingBefore(5f);
		table15.setWidthPercentage(80f);
		
		celda = new PdfPCell(new Phrase("11. Persona que paga la colegiatura (proveer dirección solo si difiere de la anterior)/ "+
				"Person Who Pays the Tuition\n(provide address if diferent from above) "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(5);
		table15.addCell(celda);
		
		String tutor = admTutor.getTutor();		 
		
		celda = new PdfPCell(new Phrase(tutor.equals("0")?"Sí":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tutor.equals("1")?"Sí":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tutor.equals("2")?"Sí":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tutor.equals("3")?"Sí":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admTutor.getNombre()==null?" ":admTutor.getNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase("PADRE/ FATHER"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" MADRE/ MOTHER "
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase("YO MISMO/ MYSELF"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TUTOR/ GUARDIAN"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase("SI ES TUTOR, NOMBRE Y APELLIDO/ IF GUARDIAN, NAME AND SURNAME"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table15.addCell(celda);
		
		document.add(table15);
		
	//Segunda y TERCERALinea de FORMA DE PAGO
		
		PdfPTable table16 = new PdfPTable(4);
		int tWidths16[] = {25,25,25,25};
		table16.setWidths(tWidths16);
		table16.setSpacingBefore(0f);
		table16.setWidthPercentage(80f);
		
		celda = new PdfPCell(new Phrase(calle
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(numero
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(colonia
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(cp
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
	
		
		celda = new PdfPCell(new Phrase("CALLE / STREET"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" NÚMERO / NUMBER"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase("COLONIA / ZONE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase("CÓDIGO POSTAL/ ZIP CODE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		String tutorCiudad = "-";
		if (mapCiudades.containsKey(pais+estado+ciudad)){
			tutorCiudad = mapCiudades.get(pais+estado+ciudad).getNombreCiudad();
		}
		
		celda = new PdfPCell(new Phrase(tutorCiudad
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		String tutorEstado = "-";
		if (mapEstados.containsKey(pais+estado)){
			tutorEstado = mapEstados.get(pais+estado).getNombreEstado();
		}
		
		celda = new PdfPCell(new Phrase(tutorEstado, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		String tutorPais = "-";
		if (mapPaises.containsKey(pais)){
			tutorPais = mapPaises.get(pais).getNombrePais();
		}
		
		celda = new PdfPCell(new Phrase(tutorPais, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tel
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase("MUNICIPIO/ CITY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase("ESTADO/ STATE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase("PAÍS/ COUNTRY"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TELÉFONO/ TELEPHONE"
				, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(255,255,255))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(new BaseColor(0,0,0));
		celda.setBorder(Rectangle.BOX);
		table16.addCell(celda);
		
		document.add(table16);
		
		//Compromiso del alumno padre o tutor
		PdfPTable table10 = new PdfPTable(1);
		int tWidths10[] = {100};
		table10.setWidths(tWidths10);
		table10.setSpacingBefore(30f);
		table10.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("COMPROMISO DEL ALUMNO Y DEL PADRE/TUTOR/ STUDENT AND PARENT/GUARDIAN COMMITMENT"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table10.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table10.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Hago constar que los datos sometidos en esta solicitud son verdaderos."+
				" Entiendo que es mi responsabilidad entregar toda la documentación oficial original debidamente legalizada/ auténtificada al momento de inscribirme."+
				" Estoy de acuerdo en que la UNIVERSIDAD DE MONTEMORELOS, A.C. no me expida documento oficial alguno en tanto que no haya hecho entrega de la"+
				" documentación estipulada y haber cumplido con todos mis compromisos financieros. Me comprometo a cumplir con cada uno de los reglamentos"+
				" establecidos en cuanto a disciplina y estudios. Además, por este medio autorizo a la UNIVERSIDAD DE MONTEMORELOS, A.C. a informar a mis padres/ tutor oficial"+
				" sobre mi avance académico, situación financiera y de conducta, según lo amerite el caso."
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table10.addCell(celda);
		
		celda = new PdfPCell(new Phrase(" "
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table10.addCell(celda);
		
		celda = new PdfPCell(new Phrase("I herby state that the information submitted in this application is true. I understand that it is my sole responsability "+
				" to submit the stipulated original official documentation duly legalized/ authenticated at the time of registration. I agree to the fact that Montemorelos"+
				" University will not issue any official academic document to my name unless the aforementioned documentation has been turned in and I have comp lied with all"+
				" of my financial responsabilities I agree to comply with the Institution´s rules and code of conduct. Furthermore, I herby authorize Montemorelos"+
				" Montemorelos University to release to my parents/ legal guardian information regarding my academic achievements, financial obligations and behavior."
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table10.addCell(celda);
		
		document.add(table10);
		
		PdfPTable table11 = new PdfPTable(3);
		int tWidths11[] = {38,38,24};
		table11.setWidths(tWidths11);
		table11.setSpacingBefore(70f);
		table11.setWidthPercentage(80f);    	
		
		celda = new PdfPCell(new Phrase("Firma del estudiante/ Student´s Signature"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table11.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Firma del padre o tutor/ Parent or Tutor´s Signature"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table11.addCell(celda);
		
		celda = new PdfPCell(new Phrase("Fecha/ Date"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(1);
		table11.addCell(celda);
		
		document.add(table11);
		
	}catch(IOException ioe){
		System.err.println("Error al generar La Solicitd en PDF: "+ioe.getMessage());
	}
	
	document.close();
	
	String carpeta	= application.getRealPath("/pdf/admisionEnLinea/");
	String dir 		= carpeta+folio+".pdf";
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
		
	String nombreArchivo = folio+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>