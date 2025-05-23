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
	String estadoDomicilio		= (String)request.getAttribute("estadoDomicilio");
	String ciudadNombre			= (String)request.getAttribute("ciudadNombre");
	String ciudadDomicilio		= (String)request.getAttribute("ciudadDomicilio");
	String paisDomicilio		= (String)request.getAttribute("paisDomicilioNombre");
	String nacionalidad			= (String)request.getAttribute("nacionalidad");
	String religionNombre		= (String)request.getAttribute("religionNombre");
	String edad					= (String)request.getAttribute("edad");
	String carreraNombre		= (String)request.getAttribute("carreraNombre");
	String religionPadre		= (String)request.getAttribute("religionPadre");
	String religionMadre		= (String)request.getAttribute("religionMadre");
	
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
	
	int paginaAnterior = 0;
	
	//------PDF----->	
	Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
	document.setMargins(-33,-30,30,30);
	
	try{		
		File carpeta = new File(application.getRealPath("/pdf/admisionEnLinea/"));
		if(!carpeta.exists()) carpeta.mkdirs();
		String dir = carpeta+"/"+folio+".pdf";
		
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Admission");
        document.addSubject("Application: "+"1");
                
        document.open();
        
        PdfContentByte canvas = pdf.getDirectContentUnder();
       
        //Datos de la fotografia
        
    	PdfPTable datosUM = new PdfPTable(1);
		int datosUMWidths[] = {100};
		datosUM.setWidths(datosUMWidths);
		datosUM.setTotalWidth(200f);
		
        PdfPCell celda = null;
        int r = 0, g = 0, b = 0;
      	//Logo Um
        
        Image jpg = Image.getInstance(application.getRealPath("/imagenes/")+"/SolUno.jpg");
        jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
        jpg.scaleAbsolute(605,750);
        jpg.setAbsolutePosition(0, 25);
        document.add(jpg);

		//Informacion Personal
		
		PdfPTable table2 = new PdfPTable(1);
		int tWidths2[] = {100};
		table2.setWidths(tWidths2);
		table2.setSpacingBefore(70f);
		table2.setWidthPercentage(200f);    	
		
		//QUITAR ESTA SECCION
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table2.addCell(celda);
		
		document.add(table2);
		
		//Nombre/Name
		PdfPTable table3 = new PdfPTable(3);
		int tWidths3[] = {40,33,27};
		table3.setWidths(tWidths3);
		table3.setSpacingBefore(165f);
		table3.setWidthPercentage(85f);    	
		
		celda = new PdfPCell(new Phrase(admSolicitud.getNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table3.addCell(celda);

		celda = new PdfPCell(new Phrase(admSolicitud.getApellidoPaterno()==null ? "" : admSolicitud.getApellidoPaterno()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table3.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getApellidoMaterno()==null ? "" : admSolicitud.getApellidoMaterno() 
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table3.addCell(celda);
		
		document.add(table3);
		
		//Lugar de Nacimiento / Place of Birth	
		PdfPTable table4 = new PdfPTable(2);
		int tWidths4[] = {57,43};
		table4.setWidths(tWidths4);
		table4.setSpacingBefore(12f);
		table4.setWidthPercentage(85f);    	
		
		celda = new PdfPCell(new Phrase(ciudadNombre
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table4.addCell(celda);
		
		celda = new PdfPCell(new Phrase(estadoNombre
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table4.addCell(celda);
		
		document.add(table4);
		
		//Lugar de Nacimiento / Place of Birth	
		PdfPTable table41 = new PdfPTable(2);
		int tWidths41[] = {52,48};
		table41.setWidths(tWidths41);
		table41.setSpacingBefore(11f);
		table41.setWidthPercentage(85f);    	
		
		celda = new PdfPCell(new Phrase(paisNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table41.addCell(celda);
		
		celda = new PdfPCell(new Phrase(nacionalidad, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table41.addCell(celda);
		
		document.add(table41);
		
		//Fecha de Nacimiento / Date of Birth
		
		PdfPTable table5 = new PdfPTable(5);
		int tWidths5[] = {16,11,15,13,46};
		table5.setWidths(tWidths5);
		table5.setSpacingBefore(11f);
		table5.setWidthPercentage(85f);    	
		
		celda = new PdfPCell(new Phrase(admSolicitud.getFechaNac()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(edad
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table5.addCell(celda);
		
		String estadoCivil = "";
		if(admSolicitud.getEstadoCivil()!=null){
			if(admSolicitud.getEstadoCivil().equals("C")){
				estadoCivil="Married";
			}
			if(admSolicitud.getEstadoCivil().equals("S")){
				estadoCivil="Single";
			}
			if(admSolicitud.getEstadoCivil().equals("V")){
				estadoCivil="Widowed";
			}
			if(admSolicitud.getEstadoCivil().equals("D")){
				estadoCivil="Divorced";
			}
		}
		celda = new PdfPCell(new Phrase(estadoCivil
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table5.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getGenero()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table5.addCell(celda);
		
		int colspan = 1;
		if(!admSolicitud.getReligionId().equals("1"))colspan=2;
		celda = new PdfPCell(new Phrase(religionNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setColspan(colspan);
		celda.setBorder(Rectangle.NO_BORDER);
		table5.addCell(celda);
		
		document.add(table5);
		
		//Domicilio Legal permanente		
		calle = admTutor.getCalle()==null?"":admTutor.getCalle();
		numero =admTutor.getNumero()==null?"":admTutor.getNumero();
		colonia = admTutor.getColonia()==null?"":admTutor.getColonia();
		cp = admTutor.getCodigoPostal()==null?"":admTutor.getCodigoPostal();
		ciudad = admTutor.getCiudadId()==null?"0":admTutor.getCiudadId();
		estado = admTutor.getEstadoId()==null?"0":admTutor.getEstadoId();
		pais = admTutor.getPaisId()==null?"0":admTutor.getPaisId();

		PdfPTable table63 = new PdfPTable(2);
		int tWidths63[] = {40,60};
		table63.setWidths(tWidths63);
		table63.setSpacingBefore(11f);
		table63.setWidthPercentage(85f);    
		
		celda = new PdfPCell(new Phrase(admSolicitud.getTelefono()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table63.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSolicitud.getEmail()==null?"":admSolicitud.getEmail()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table63.addCell(celda);

		document.add(table63);
		
		PdfPTable table6 = new PdfPTable(3);
		int tWidths6[] = {46,9,43};
		table6.setWidths(tWidths6);
		table6.setSpacingBefore(43f);
		table6.setWidthPercentage(85f);    	
		
		celda = new PdfPCell(new Phrase(calle
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase(numero
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table6.addCell(celda);
		
		celda = new PdfPCell(new Phrase(colonia
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table6.addCell(celda);
		
		document.add(table6);
		
		//Segunda y TERCERALinea de Domicilio Legal permanente
		
		PdfPTable table7 = new PdfPTable(4);
		int tWidths7[] = {38,29,17,16};
		table7.setWidths(tWidths7);
		table7.setSpacingBefore(12f);
		table7.setWidthPercentage(85f);
		
		/* CORRECCION*/
		celda = new PdfPCell(new Phrase(ciudadDomicilio
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(estadoDomicilio, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(paisDomicilio, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		table7.addCell(celda);
		
		celda = new PdfPCell(new Phrase(cp
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table7.addCell(celda);
		
		document.add(table7);
		
		//Informacion academica y...... carrera/programa que desea cursar y fecha de inicio
		PdfPTable table8 = new PdfPTable(2);
		int tWidths8[] = {80,20};
		table8.setWidths(tWidths8);
		table8.setSpacingBefore(44f);
		table8.setWidthPercentage(85f);    	
		
		celda = new PdfPCell(new Phrase(carreraNombre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table8.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admAcademico.getFecha()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table8.addCell(celda);
		
		document.add(table8);
		
		//Bachillerato/High School
		
		PdfPTable table9 = new PdfPTable(1);
		int tWidths9[] = {100};
		table9.setWidths(tWidths9);
		table9.setSpacingBefore(12f);
		table9.setWidthPercentage(85f);  
		
		String institucion 	= "";
		String paisEstudio	= ""; 
		String completo 	= "";
		
		for(int i=0; i<lisEstudios.size(); i++){
			AdmEstudio tmp = lisEstudios.get(i);
			
			celda = new PdfPCell(new Phrase(tmp.getTitulo()
					, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorder(Rectangle.NO_BORDER);
			table9.addCell(celda);
			
			paisEstudio = tmp.getPaisId();
			institucion = tmp.getInstitucion();
			completo 	= tmp.getCompleto().equals("S")?"S͍":"No";
			
			break;
		}
		document.add(table9);
		
		PdfPTable table91 = new PdfPTable(3);
		int tWidths91[] = {63,27,10};
		table91.setWidths(tWidths91);
		table91.setSpacingBefore(12f);
		table91.setWidthPercentage(85f);  
		
		celda = new PdfPCell(new Phrase(institucion
		, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table91.addCell(celda);
	
		String estudioPais = "-";
		if (mapPaises.containsKey(paisEstudio)){
			estudioPais = mapPaises.get(paisEstudio).getNombrePais();
		}
	
		celda = new PdfPCell(new Phrase(estudioPais, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table91.addCell(celda);
	
		celda = new PdfPCell(new Phrase(completo
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table91.addCell(celda);

		document.add(table91);
		
		//Informacion sobre salud
		PdfPTable table12 = new PdfPTable(2);
		int tWidths12[] = {17,83};
		table12.setWidths(tWidths12);
		table12.setSpacingBefore(46f);
		table12.setWidthPercentage(85f);    	
		
		celda = new PdfPCell(new Phrase(admSalud.getEnfermedad()==null?"":admSalud.getEnfermedad().equals("S")?"Si":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table12.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSalud.getEnfermedadDato()==null?"":admSalud.getEnfermedadDato().equals("S")?"Si":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table12.addCell(celda);
		
		document.add(table12);

		PdfPTable table123 = new PdfPTable(2);
		int tWidths123[] = {17,83};
		table123.setWidths(tWidths123);
		table123.setSpacingBefore(12f);
		table123.setWidthPercentage(85f);    	

		celda = new PdfPCell(new Phrase(admSalud.getImpedimentoDato()==null?"":admSalud.getImpedimentoDato().equals("S")?"Si":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table123.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admSalud.getImpedimentoDato()==null?"":admSalud.getImpedimentoDato()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table123.addCell(celda);
		
		document.add(table123);
		
		//INFORMACION SOBRE EL PADRE
		PdfPTable table134 = new PdfPTable(2);
		int tWidths134[] = {50,50};
		table134.setWidths(tWidths134);
		table134.setSpacingBefore(41f);
		table134.setWidthPercentage(85f);
		
		celda = new PdfPCell(new Phrase(admPadres.getPadreNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table134.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getPadreApellido()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table134.addCell(celda);
		
		document.add(table134);
	
		PdfPTable table13 = new PdfPTable(2);
		int tWidths13[] = {44,56};
		table13.setWidths(tWidths13);
		table13.setSpacingBefore(12f);
		table13.setWidthPercentage(85f);
		
		celda = new PdfPCell(new Phrase(religionPadre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table13.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getPadreOcupacion()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table13.addCell(celda);
		
		document.add(table13);
		
		//INFORMACION SOBRE LA MADRE
		PdfPTable table141 = new PdfPTable(2);
		int tWidths141[] = {50,50};
		table141.setWidths(tWidths141);
		table141.setSpacingBefore(12f);
		table141.setWidthPercentage(85f);
		
		celda = new PdfPCell(new Phrase(admPadres.getMadreNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table141.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getMadreApellido()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table141.addCell(celda);
		
		document.add(table141);

		PdfPTable table14 = new PdfPTable(2);
		int tWidths14[] = {44,56};
		table14.setWidths(tWidths14);
		table14.setSpacingBefore(12f);
		table14.setWidthPercentage(85f);
		
		celda = new PdfPCell(new Phrase(religionMadre, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table14.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admPadres.getMadreOcupacion()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table14.addCell(celda);
		
		document.add(table14);
		
		document.newPage();
		
		Image dos = Image.getInstance(application.getRealPath("/imagenes/")+"/SolDos.jpg");
        dos.setAlignment(Image.LEFT | Image.UNDERLYING);
        dos.scaleAbsolute(605,750);
        dos.setAbsolutePosition(0, 25);
        
    	document.add(dos);
    	
    	PdfPTable table17 = new PdfPTable(1);
		int tWidths17[] = {100};
		table17.setWidths(tWidths17);
		table17.setSpacingBefore(0f);
		table17.setWidthPercentage(85f);
	
		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table17.addCell(celda);

		document.add(table17);

		//Informacion sobre la forma de pago
		PdfPTable table15 = new PdfPTable(5);
		int tWidths15[] = {7,7,8,9,69};
		table15.setWidths(tWidths15);
		table15.setSpacingBefore(26f);
		table15.setWidthPercentage(85f);
		
		String tutor = admTutor.getTutor();			 
		
		celda = new PdfPCell(new Phrase(tutor.equals("0")?"Si":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tutor.equals("1")?"Si":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tutor.equals("2")?"Si":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(tutor.equals("3")?"Si":"No"
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table15.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admTutor.getNombre()==null?" ":admTutor.getNombre()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table15.addCell(celda);
		
		document.add(table15);

		//Segunda y TERCERALinea de FORMA DE PAGO
		PdfPTable table16 = new PdfPTable(4);
		int tWidths16[] = {42,9,36,15};
		table16.setWidths(tWidths16);
		table16.setSpacingBefore(14f);
		table16.setWidthPercentage(85f);
		
		celda = new PdfPCell(new Phrase(calle
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(numero
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(colonia
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table16.addCell(celda);
		
		celda = new PdfPCell(new Phrase(cp
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table16.addCell(celda);
		
		document.add(table16);

		PdfPTable table161 = new PdfPTable(4);
		int tWidths161[] = {32,31,18,20};
		table161.setWidths(tWidths161);
		table161.setSpacingBefore(14f);
		table161.setWidthPercentage(85f);
		
		String tutorCiudad = "-";
		if (mapCiudades.containsKey(pais+estado+ciudad)){
			tutorCiudad = mapCiudades.get(pais+estado+ciudad).getNombreCiudad();
		}
		
		celda = new PdfPCell(new Phrase(tutorCiudad
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table161.addCell(celda);
	
		String tutorEstado = "-";
		if (mapEstados.containsKey(pais+estado)){
			tutorEstado = mapEstados.get(pais+estado).getNombreEstado();
		}
		
		celda = new PdfPCell(new Phrase(tutorEstado
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table161.addCell(celda);
		
		String tutorPais = "-";
		if (mapPaises.containsKey(pais)){
			tutorPais = mapPaises.get(pais).getNombrePais();
		}
		
		celda = new PdfPCell(new Phrase(tutorPais
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table161.addCell(celda);
		
		celda = new PdfPCell(new Phrase(admTutor.getTelefono()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		table161.addCell(celda);
		
		document.add(table161);

		PdfPTable table18 = new PdfPTable(2);
		int tWidths18[] = {10,90};
		table18.setWidths(tWidths18);
		table18.setSpacingBefore(400f);
		table18.setWidthPercentage(85f);

		celda = new PdfPCell(new Phrase(""
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		table18.addCell(celda);

		celda = new PdfPCell(new Phrase(admSolicitud.getNombre()+" "+admSolicitud.getApellidoPaterno()+" "+admSolicitud.getApellidoMaterno()
				, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(r,g,b))));
		celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		celda.setBorder(Rectangle.NO_BORDER);
		table18.addCell(celda);

		document.add(table18);
		
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