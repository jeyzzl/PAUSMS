<%@page import="aca.investiga.InvPresupuesto"%>
<%@page import="aca.investiga.InvActividad"%>
<%@page import="aca.investiga.InvProyecto"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@page import="java.io.File"%>

<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>

<jsp:useBean id="InvProyecto" scope="page" class="aca.investiga.InvProyecto"/>
<jsp:useBean id="InvMetodologia" scope="page" class="aca.investiga.InvMetodologia"/>
<jsp:useBean id="InvActividad" scope="page" class="aca.investiga.InvActividad"/>
<jsp:useBean id="InvActividadU" scope="page" class="aca.investiga.InvActividadUtil"/>
<jsp:useBean id="InvPresupuesto" scope="page" class="aca.investiga.InvPresupuesto"/>
<jsp:useBean id="InvPresupuestoU" scope="page" class="aca.investiga.InvPresupuestoUtil"/>
<jsp:useBean id="InvResultado" scope="page" class="aca.investiga.InvResultado"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>

<% 
	String institucion 		= (String) session.getAttribute("institucion");
	String proyectoId		= request.getParameter("proyectoId")==null?"":request.getParameter("proyectoId");	
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String nombreUsuario	= " ";
		
	Usuario.setCodigoPersonal(codigo);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigo);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}		
	
	int posX 	= 0; 
	int posY	= 0;
	
	String carpeta 	= application.getRealPath("/investigacion/proyecto/archivos/"); 
	String dir 		= "";
	if(request.getParameter("Accion")==null){
		//Document document = new Document(PageSize.A4 ); //Crea un objeto para el documento PDF
		Rectangle rec = new Rectangle(14.0f , 21.0f);
		Document document = new Document(PageSize.LETTER);
		document.setMargins(-30,-30,50,30);
		
		
		try{		
			if(!new File(carpeta).exists()) new File(application.getRealPath(carpeta)).mkdirs();
			dir = carpeta+proyectoId+".pdf";
			PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
			document.addAuthor("DPI");
	        
			document.open();			
			//String pathImagen = application.getRealPath("/imagenes/")+"umColor.png";
			String pathImagen = application.getRealPath("/imagenes/")+"uni.png";
			if (java.io.File.separator.equals("\\")){
				pathImagen = pathImagen.replace("\\", "/");
			}		
			
			Image jpg = Image.getInstance(pathImagen);
		    jpg.setAlignment(Image.LEFT | Image.UNDERLYING);
		    jpg.scaleAbsolute(100,100);
		    jpg.setAbsolutePosition(70, 650);
		    document.add(jpg);
		    
		    int r = 0, g = 0, b = 0;
		    
		    PdfPCell celda = null;
		     
		    PdfContentByte canvas = pdf.getDirectContentUnder();
		     
		    Phrase uni = new Phrase( institucion.toUpperCase(),  FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0)) );
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, uni, posX+340, posY+710, 0);
	    	
	    	Phrase servicio = new Phrase( "DIRECCIÓN DE INVESTIGACIÓN E INNOVACIÓN",  FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0,0,0)) );
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, servicio, posX+340, posY+690, 0);

		    Phrase title1 = new Phrase( "Formato para el registro y la evaluación de proyectos de"
		    		, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(0,0,0)) );
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, title1, posX+340, posY+655, 0);
	    	
	    	Phrase title2 = new Phrase( "investigación, innovación y desarrollo UM"
		    		, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(0,0,0)) );
	    	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, title2, posX+340, posY+640, 0);
		     
	       	//Tabla Invisible
	
	    	float w = 80f;
	    	float s = 400f;
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
		    
		    //tabla de general	    
		    InvProyecto.mapeaRegId(conEnoc, proyectoId);	    
		    InvMetodologia.mapeaRegId(conEnoc, proyectoId);
		    
		    String nombreTipo = " ";
			if(InvProyecto.getTipo().equals("1")){
				nombreTipo = "Académico-científica";
			}else if(InvProyecto.getTipo().equals("2")){
				nombreTipo = "Investigación institucional";
			}else if(InvProyecto.getTipo().equals("3")){
				nombreTipo = "Investigación educativa";
			}else{
				nombreTipo = "Desarrollo de habilidades de investigación";
			}
		    
		    float ve = 115f;
	    	float qe = 70f;
			PdfPTable tab2 = new PdfPTable(7);
			int tab2Widths[] = {2,30,17,8,5,10,10};
			tab2.setWidths(tab2Widths);
			tab2.setSpacingBefore(ve);
			tab2.setWidthPercentage(qe);
			
			//titulo del proyecto
			celda = new PdfPCell(new Phrase("Título del proyecto: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getProyectoNombre()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//nombre del investigador
			celda = new PdfPCell(new Phrase("Nombre del investigador: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (nombreUsuario
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);

			//tipo			
			celda = new PdfPCell(new Phrase("Tipo de investigación: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (nombreTipo
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);	
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//lineas			
			celda = new PdfPCell(new Phrase("Líneas de investigación: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getLinea()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//Depto			
			celda = new PdfPCell(new Phrase("Departamento: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getDepartamento()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//Colaboradores			
			celda = new PdfPCell(new Phrase("Investigadores Colaboradores: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getInvestigadores()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//fecha ini			
			celda = new PdfPCell(new Phrase("Fecha de inicio del proyecto: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getFechaInicio()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//fecha fin			
			celda = new PdfPCell(new Phrase("Fecha de conclusión del proyecto: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(3);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(4);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getFechaFinal()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
	
			//resumen			
			celda = new PdfPCell(new Phrase("Introducción: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getResumen()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//responsabilidad docente			
			celda = new PdfPCell(new Phrase("Responsablilidades del docente: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getResDocente()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//responsabilidad alumno			
			celda = new PdfPCell(new Phrase("Responsablilidades del alumno: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getResAlumno()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//antecedentes			
			celda = new PdfPCell(new Phrase("Antecedentes: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getAntecedentes()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//justificacion			
			celda = new PdfPCell(new Phrase("Justificación: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getJustificacion()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//estado del arte			
			celda = new PdfPCell(new Phrase("Estado del Arte: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvProyecto.getEstadoArte()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab2.addCell(celda);
			
			//objetivos			
			celda = new PdfPCell(new Phrase("Objetivos: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab2.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getObjetivo()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab2.addCell(celda);
			
			document.add(tab2);
			
			//tabla 3 Metodología			
			float sb = 12f;
			
			PdfPTable tab3 = new PdfPTable(7);
			int tab3Widths[] = {2,30,17,8,5,10,10};
			tab3.setWidths(tab3Widths);
			tab3.setSpacingBefore(sb);
			tab3.setWidthPercentage(qe);
			
			//diseño Renglon 1		
			celda = new PdfPCell(new Phrase("Diseño: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab3.addCell(celda);
			
			//Renglon 2
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getDiseno()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab3.addCell(celda);
			
			//Selección de muestra Renglon 3			
			celda = new PdfPCell(new Phrase("Selección de muestra: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab3.addCell(celda);
			
			// Renglon 4
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getMuestra()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab3.addCell(celda);
			
			//Recolección de datos Renglon 5			
			celda = new PdfPCell(new Phrase("Recolección de datos: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab3.addCell(celda);
			
			// Renglon 6
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getRecoleccion()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab3.addCell(celda);
			
			//Confidencialidad -Renglon 7	
			celda = new PdfPCell(new Phrase("Confidencialidad: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab3.addCell(celda);
			
			// Renglon 8
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getConfidencialidad()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab3.addCell(celda);
			
			// Renglon 9
			//Planteamiento			
			celda = new PdfPCell(new Phrase("Planteamiento del Problema: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab3.addCell(celda);
			
			// Renglon 10
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getProblema()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab3.addCell(celda);
			
			// Renglon 11
			//Validez			
			celda = new PdfPCell(new Phrase("Confiabilidad y Validez: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab3.addCell(celda);
			
			// Renglon 12
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getValidez()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab3.addCell(celda);
			
			// Renglon 13
			//Hipótesis			
			celda = new PdfPCell(new Phrase("Hipótesis: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(2);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab3.addCell(celda);
			
			// Renglon 14
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab3.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvMetodologia.getHipotesis()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab3.addCell(celda);
		
			//document.add(tab3);			
		
			if(InvMetodologia.getVinculacion().equals("I")){
				
				// rENGLON 15a
				celda = new PdfPCell(new Phrase("Carrera: "
						, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);
			
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 16a
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, InvMetodologia.getOrganizacion())
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);
			
				// Renglon 15a
				//Responsable		
				celda = new PdfPCell(new Phrase("Responsable: "
							, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 16a
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (InvMetodologia.getResponsable()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);
			
				//document.add(tab3);
				
				// Renglon 17a
				//Actividades a Realizar		
				celda = new PdfPCell(new Phrase("Actividades a Realizar: "
							, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 18a
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (InvMetodologia.getActividades()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);
			
				//document.add(tab3);
				
				// Renglon 19a
				//Entregable		
				celda = new PdfPCell(new Phrase("Entregable: "
							, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);			
				
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 20a
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (InvMetodologia.getEntregable()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);				
				//document.add(tab3);
			}else if(InvMetodologia.getVinculacion().equals("E")){
				
				// Renglon 15b
				//Organización
				celda = new PdfPCell(new Phrase("Organización: "
							, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 16b
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (InvMetodologia.getOrganizacion()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);
			
				//document.add(tab3);
				
				// Renglon 17b
				//Responsable		
				celda = new PdfPCell(new Phrase("Responsable: "
							, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 18b
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (InvMetodologia.getResponsable()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);
			
				//document.add(tab3);
				
				// Renglon 19b
				//Actividades a Realizar		
				celda = new PdfPCell(new Phrase("Actividades a Realizar: "
							, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 20b
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (InvMetodologia.getActividades()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);
			
				//document.add(tab3);
				
				// Renglon 21b
				//Entregable		
				celda = new PdfPCell(new Phrase("Entregable: "
							, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(2);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(5);
				tab3.addCell(celda);
				
				// Renglon 22b
				celda = new PdfPCell(new Phrase(" "));
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				tab3.addCell(celda);
				
				celda = new PdfPCell(new Phrase (InvMetodologia.getEntregable()
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(6);
				tab3.addCell(celda);					
			}			
			
			document.add(tab3);
			
			//tabla 4 cronograma
			ArrayList<InvActividad> listAct = InvActividadU.getListActividad(conEnoc, proyectoId, "ORDER BY ACTIVIDAD_ID");
			
			PdfPTable tab4 = new PdfPTable(4);
			int tab4Widths[] = {5,60,12,12};
			tab4.setWidths(tab4Widths);
			tab4.setSpacingBefore(sb);
			tab4.setWidthPercentage(qe);
			
			celda = new PdfPCell(new Phrase("Cronograma: "
					, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(4);
			tab4.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab4.addCell(celda);
			
			celda = new PdfPCell(new Phrase("No"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab4.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Actividad"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab4.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Fecha Inicio"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab4.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Fecha Fin"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab4.addCell(celda);
			
			for (int i=0; i<listAct.size(); i++){
				aca.investiga.InvActividad actividad = (aca.investiga.InvActividad) listAct.get(i);
				
				celda = new PdfPCell(new Phrase (actividad.getActividadId()
						, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab4.addCell(celda);
				
				celda = new PdfPCell(new Phrase (actividad.getActividadNombre()
						, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab4.addCell(celda);
				
				celda = new PdfPCell(new Phrase (actividad.getFechaIni()
						, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab4.addCell(celda);
				
				celda = new PdfPCell(new Phrase (actividad.getFechaFin()
						, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab4.addCell(celda);
			}
			
			document.add(tab4);
			
			//tabla 5 presupuesto
			
			ArrayList<InvPresupuesto> listPres = InvPresupuestoU.getListPresupuesto(conEnoc, proyectoId, " ORDER BY PRESUPUESTO_ID");
			
			
			
			PdfPTable tab5 = new PdfPTable(5);
			int tab5Widths[] = {5,10,60,14,12};
			tab5.setWidths(tab5Widths);
			tab5.setSpacingBefore(sb);
			tab5.setWidthPercentage(qe);
			
			celda = new PdfPCell(new Phrase("Presupuesto: "
					, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(4);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(5);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase("No"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Tipo"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Presupuesto"
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Monto Corr."
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Monto Inv."
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab5.addCell(celda);
			
			
			int totalC = 0;
			int totalI = 0;
			int totalGral = 0;
			for (int i=0; i<listPres.size(); i++){
				aca.investiga.InvPresupuesto presupuesto = (aca.investiga.InvPresupuesto) listPres.get(i);
				
				celda = new PdfPCell(new Phrase (presupuesto.getPresupuestoId()
						, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab5.addCell(celda);
				
				String tipo;
				if(presupuesto.getTipo().equals("C")){
					tipo = "Corriente";
				}else	tipo = "Inversión";
				
				celda = new PdfPCell(new Phrase (tipo
						, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab5.addCell(celda);
				
				celda = new PdfPCell(new Phrase (presupuesto.getPresupuestoNombre()
						, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOX);
				celda.setColspan(1);
				tab5.addCell(celda);
				
				if(presupuesto.getTipo().equals("C")){
					celda = new PdfPCell(new Phrase ("$ " + presupuesto.getMonto()
					, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					celda.setColspan(1);
					tab5.addCell(celda);
					
					celda = new PdfPCell(new Phrase (" "
					, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					celda.setColspan(1);
					tab5.addCell(celda);
					
					totalC += Integer.parseInt(presupuesto.getMonto());
				}else {
					celda = new PdfPCell(new Phrase (" "
					, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					celda.setColspan(1);
					tab5.addCell(celda);
					
					celda = new PdfPCell(new Phrase ("$ " + presupuesto.getMonto()
					, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOX);
					celda.setColspan(1);
					tab5.addCell(celda);
					
					totalI += Integer.parseInt(presupuesto.getMonto());
				}			
				
			}
			
			totalGral = totalC + totalI;
			
			celda = new PdfPCell(new Phrase("Total general = $ " + totalGral
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(3);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase("$ " + totalC
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab5.addCell(celda);
			
			celda = new PdfPCell(new Phrase("$ " + totalI
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setBorder(Rectangle.BOX);
			celda.setBackgroundColor(new BaseColor(230,230,230));
			celda.setColspan(1);
			tab5.addCell(celda);
			
			document.add(tab5);
			
			
			//tabla 6 Resultado
			
			InvResultado.mapeaRegId(conEnoc, proyectoId);
			
			PdfPTable tab6 = new PdfPTable(7);
			int tab6Widths[] = {2,30,17,8,5,10,10};
			tab6.setWidths(tab6Widths);
			tab6.setSpacingBefore(sb);
			tab6.setWidthPercentage(qe);
			
			//infraestructura
			celda = new PdfPCell(new Phrase("Infraestructura disponible: "
					, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab6.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab6.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvResultado.getInfraestructura()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab6.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab6.addCell(celda);
			
			//Referencias
			celda = new PdfPCell(new Phrase("Referencias: "
					, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab6.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(1);
			tab6.addCell(celda);
			
			celda = new PdfPCell(new Phrase (InvResultado.getBibliografia()
					, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(r,g,b))));
			celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(6);
			tab6.addCell(celda);
			
			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(7);
			tab6.addCell(celda);
			
			document.add(tab6);

	
		}catch(IOException ioe){
			System.err.println("Error al generar el proyecto de investigación en PDF: "+ioe.getMessage());
		}
		
		document.close();
	}
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = proyectoId + ".pdf";	
	response.sendRedirect("../subirProyecto?proyectoId="+proyectoId+"&ruta="+dir+"&nombre="+nombreArchivo);
%>
<%@ include file= "../../cierra_enoc.jsp" %>

