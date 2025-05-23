<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>

<%@page import="aca.cert.CertCiclo"%>
<%@page import="aca.cert.CertCurso"%>
<%@page import="aca.cert.CertRelacion"%>
<%@page import="aca.util.NumberToLetter"%>
<%@page import="aca.kardex.KrdxCursoAct"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.cert.CertAlumNota"%>

<%@page import="aca.alumno.AlumPlan"%>
<jsp:useBean id="alumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="planU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="alumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="certPlan" scope="page" class="aca.cert.CertPlan"/>
<jsp:useBean id="certPlanU" scope="page" class="aca.cert.CertPlanUtil"/>
<jsp:useBean id="certCiclo" scope="page" class="aca.cert.CertCiclo"/>
<jsp:useBean id="certCicloU" scope="page" class="aca.cert.CertCicloUtil"/>
<jsp:useBean id="certCurso" scope="page" class="aca.cert.CertCurso"/>
<jsp:useBean id="certCursoU" scope="page" class="aca.cert.CertCursoUtil"/>
<jsp:useBean id="certRelacion" scope="page" class="aca.cert.CertRelacion"/>
<jsp:useBean id="krdxCursoAct" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="certAlumNota" scope="page" class="aca.cert.CertAlumNota"/>
<jsp:useBean id="certAlumNotaU" scope="page" class="aca.cert.CertAlumNotaUtil"/>
<jsp:useBean id="certAlum" scope="page" class="aca.cert.CertAlum"/>
<jsp:useBean id="certAlumU" scope="page" class="aca.cert.CertAlumUtil"/>
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<% 	
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");
	String planId		= request.getParameter("plan");
	String leyenda		= request.getParameter("Leyenda")==null?"S":request.getParameter("Leyenda");
	String curp			= request.getParameter("Curp")==null?"S":request.getParameter("Curp");
	String ciclo		= request.getParameter("Ciclo")==null?"J":request.getParameter("Ciclo");
	String espacios		= request.getParameter("Espacios")==null?"0":request.getParameter("Espacios");
	
	// Para dejar espacio para la foto del alumno
	String foto 		= request.getParameter("foto")==null?"Con":request.getParameter("foto");
	
	File carpeta = new File(application.getRealPath("/WEB-INF/pdf/certificado/"));
	if(!carpeta.exists()) carpeta.mkdirs();	
	String dir 			= application.getRealPath("/WEB-INF/pdf/certificado/")+codigoAlumno+planId.replace("*","")+".pdf";
	
	
	String curpAlumno	= aca.alumno.AlumUtil.getCurp(conEnoc,codigoAlumno);
	
	String carreraId	= aca.plan.PlanUtil.getCarreraId(conEnoc, planId);
	String numNivel     = aca.catalogo.CatCarreraUtil.getNivelId(conEnoc, carreraId);
		
	String lineas 		= request.getParameter("lineas");
	String [] arr 		= lineas.split("-");
	ArrayList<String> SemLineas = new ArrayList<String>();
	for(String str: arr)SemLineas.add(str);
		
	String materia 		= "";
	String nivelFrase   = "";
	PdfPCell celda 		= null;
	
	int r = 0, g = 0, b = 0;
	int[] pagPrn = {1,0,0};
	
	float size0			= 6f;
	float size1			= 7.7f;
	float size2			= 9.7f;
	float size3			= 12f;		
	
	int paginaAnterior		= 1;
	boolean printEncabezado = false;
	
	certPlan = certPlanU.mapeaRegId(conEnoc, planId);
	alumPersonal = AlumUtil.mapeaRegId(conEnoc, codigoAlumno);
	ArrayList<CertCiclo> lisCiclos 	= certCicloU.getListPlan(conEnoc, planId, "ORDER BY CICLO_ID");
	ArrayList<CertCurso> lisCursos 	= certCursoU.getListPlan(conEnoc, planId, "ORDER BY CICLO_ID, ORDEN");
	
	Document document = new Document(PageSize.LEGAL);
	//Crea un objeto para el documento PDF
	// Parametros: Izquierda,derecha,arriba,abajo
	//document.setMargins(-40,-40,70,30);
	document.setMargins(-25,-45,120,87); //PRUEBA
// 	document.setMargins(-25,-45,110,77);
	
	Parametros.mapeaRegId(conEnoc, "1");
	
	switch (Integer.parseInt(numNivel)){
			
			case 1: {
				nivelFrase = "del";
				break;			
			}
			case 2: {
				nivelFrase = "de la Carrera de";
				break;			
			}
			case 3: {
				nivelFrase = "del grado de";
				break;			
			}
			case 4: {
				nivelFrase = "del grado de";
				break;			
			}
			case 5: {
				nivelFrase = "del";
				break;			
			}					
		}
	
	try{
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addTitle(""+aca.parametros.Parametros.getInstitucion(conEnoc, "1"));
		document.addAuthor("Sistema Académico");
        document.addSubject("Certificado de "+codigoAlumno);               
            
        /******* Clase para sobreescribir el metodo OnStartPage *******/
		final class PageHeaderFooter extends PdfPageEventForwarder {
        	
			private String alumno;
			private String carrera;
        	private String piePagina;
        	private String foto;
        	private String leyenda;
        	
        	public PageHeaderFooter(){   		
	        	 
	        }
        	
        	public void setAlumno(String alumno){
        		this.alumno = alumno;
        	}
        	
        	public void setCarrera(String carrera){
        		this.carrera = carrera;
        	}
        	
	        public void setPiePagina(String pie){
        		this.piePagina = pie;
        	}
	        
	        public void setFoto(String foto){
        		this.foto = foto;
        	}
	        
	        public void setLeyenda(String leyenda){
        		this.leyenda = leyenda;
        	}	        	
	        
	        public void onStartPage(PdfWriter writer, Document document) {
	        	try {
	        		PdfContentByte canvas = writer.getDirectContentUnder();
	        	/*	
	        		Phrase frase30 = new Phrase( "30", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, frase30,100, 30, 0);
	        		
	        		Phrase frase40 = new Phrase( "40", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, frase40,100, 40, 0);
	        		
	        		Phrase frase50 = new Phrase( "50", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, frase50,100, 50, 0);
	        	*/
	        		if (leyenda.equals("S")){
	        			Phrase fraseTitulo1 = new Phrase( "Estudios con Reconocimiento de Validez Oficial, otorgado y publicado por el Gobierno del Estado de", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
	        			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo1,195, 957, 0); // y=977 //PRUEBA
// 	        			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo1,195, 972, 0); // y=977
	        		
		        		Phrase fraseTitulo2 = new Phrase( "Nuevo León, México, en el Periódico Oficial de fecha 5 de mayo de 1973", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0)) );   	
		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo2,243, 950, 0); // y=970 //PRUEBA 
// 		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo2,243, 965, 0); // y=970
	        		}
	        		
	        		/* Encabezado pára la primera página */
	        		if (document.getPageNumber() == 1){
	        			
	        			// Si muestra la foto del alumno deja un espacio a la izquierda para la foto
	        			if (foto.equals("Con")){
	        				Phrase fraseTitulo = new Phrase( "CERTIFICADO DE ESTUDIOS", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0)) );   	
			        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo,257, 907, 0); //PRUEBA 
// 			        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo,257, 910, 0);
	        			}else{
	        				// Si no muestra la foto en el certificado elimina el espacio hacia la izquierda
	        				Phrase fraseTitulo = new Phrase( "CERTIFICADO DE ESTUDIOS", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0)) );
			        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo, 203, 907, 0); //PRUEBA 
// 			        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseTitulo, 203, 910, 0);
	        			}
	        			
	        		}
	        		
	        		// Encabezado a partir de la segund página
	        		if (document.getPageNumber() >= 2){
	        			/*
	        			PdfPTable table = new PdfPTable(1);
		                table.setTotalWidth(523);
		                PdfPCell cell = new PdfPCell(new Phrase("Espacio en blanco"));
		                cell.setBackgroundColor(BaseColor.ORANGE);
		                table.addCell(cell);
		                cell = new PdfPCell(new Phrase("Otro espacio en blanco"));
		                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		                table.addCell(cell);
		                table.writeSelectedRows(0, -1, 36, 900, writer.getDirectContent());
	        			*/
	        			Phrase fraseAlumno = new Phrase( this.alumno, FontFactory.getFont(FontFactory.HELVETICA, 10f, Font.BOLD, new BaseColor(0,0,0)) );   	
		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseAlumno,45, 905, 0); //PRUEBA 
// 		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseAlumno,45, 920, 0);
		        		
		        		Phrase fraseCarrera = new Phrase( this.carrera, FontFactory.getFont(FontFactory.HELVETICA, 10f, Font.BOLD, new BaseColor(0,0,0)) );   	
		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseCarrera,45, 890, 0);//PRUEBA 
// 		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseCarrera,45, 905, 0);
		        		
		        		Phrase fraseHoja = new Phrase( "Hoja "+document.getPageNumber(),
		        				FontFactory.getFont(FontFactory.HELVETICA, 10f, Font.BOLD, new BaseColor(0,0,0)) );   	
		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseHoja,530, 890, 0);//PRUEBA 
// 		        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, fraseHoja,530, 905, 0);
		        		
		        		if (document.getPageNumber() == 3){
		        			Phrase linea = new Phrase( "____________________________________________________________________________________________________________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
			        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linea,45, 890, 0);//PRUEBA 
// 			        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linea,45, 900, 0);
		        		}      		
		        		
		                
		        	}
	        		
	        		Phrase linea = new Phrase( "____________________________________________________________________________________________________________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linea,45, 50, 0); //PRUEBA 
// 	        		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, linea,45, 30, 0);
	        		
	        		Phrase pie = new Phrase( this.piePagina, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(0,0,0)) );   	
	        		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, pie,300, 40, 0); //PRUEBA 
// 	        		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, pie,300, 20, 0);
	   
	            }catch(Exception e) {
	                    System.out.println("Error en Metodo OnStartPage: "+e);
	            }
	        }            
        }		
		    
        PageHeaderFooter pageHeaderFooter = new PageHeaderFooter();
        pageHeaderFooter.setPiePagina(certPlan.getPie());
        pageHeaderFooter.setAlumno(alumPersonal.getNombreLegal());
        pageHeaderFooter.setCarrera(certPlan.getCarrera());
        pageHeaderFooter.setFoto(foto);
        pageHeaderFooter.setLeyenda(leyenda);
	    pdf.setPageEvent(pageHeaderFooter);
	    
	    document.open();
	    
//------------------------------ Pie de Pagina (primera hoja) -------------------------        
       	
		certAlum = certAlumU.mapeaRegId(conEnoc, codigoAlumno, planId);
		
		// La tabla se divide en dos columnas una de 20%(foto) y otra de 80%(datos)
		float headerwidths[] = {20f, 80f};
		PdfPTable topTable = new PdfPTable(headerwidths);
		topTable.getDefaultCell().setBorder(1);
		topTable.setTotalWidth(document.getPageSize().getWidth()-100);		
		topTable.setSpacingAfter(0f);
		
		PdfPCell cell = null;		
		/*
		if(foto.equals("Con")){
			// Primer columna 20%
			cell = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);		
			topTable.addCell(cell);
		}
		//Segunda columna 80%
        cell = new PdfPCell(new Phrase("DIRECCIÓN DE SERVICIOS ESCOLARES",
        		FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(0);
		if(foto.equals("Sin")){
			cell.setColspan(2);
		}
		topTable.addCell(cell);
		*/		
		
		/* Si tiene foto deja el espacio en blanco en la primera columna */
		if(foto.equals("Con")){
			cell = new PdfPCell(new Phrase(" ",
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			topTable.addCell(cell);
		}		
		
		String nombreLegal = alumPersonal.getNombreLegal();		
		//System.out.println("Escape1:"+org.apache.commons.lang3.StringEscapeUtils.unescapeJava(nombreLegal));
		//System.out.println("Escape2:"+org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(nombreLegal));		
		
		// Busca caracteres especiales
		if (nombreLegal!=null && nombreLegal.length() > 1 ){			
			if (nombreLegal.contains("&")){
				
				nombreLegal = nombreLegal.replaceAll("&acute;", "á");
				nombreLegal = nombreLegal.replaceAll("&ecute;", "é");
				nombreLegal = nombreLegal.replaceAll("&icute;", "í");
				nombreLegal = nombreLegal.replaceAll("&ocute;", "ó");
				nombreLegal = nombreLegal.replaceAll("&ucute;", "ú");
				
				String codigo = "";												
				while(nombreLegal.contains("&#")){
					codigo = nombreLegal.substring(nombreLegal.indexOf("&"),nombreLegal.indexOf(";"));
					codigo = codigo.replace("&#","").replace(";","");
					char caracter = (char)Integer.parseInt(codigo);	
					nombreLegal = nombreLegal.substring(0,nombreLegal.indexOf("&")) +caracter+ nombreLegal.substring(nombreLegal.indexOf(";")+1, nombreLegal.length());							
				}
			}	
		}		
		
		Phrase frase = new Phrase();		
		frase.setLeading(7);		
		String fechaRetro = certPlan.getFechaRetro()==null?"01/01/1950":certPlan.getFechaRetro();
		
		//String apellidos = alumPersonal.getApellidoPaterno().trim()+" "+alumPersonal.getApellidoMaterno().trim();
		//apellidos = apellidos.trim();
		
		frase.add(new Phrase("La Dirección de Servicios Escolares "));
		frase.add(new Phrase("CERTIFICA Y HACE CONSTAR QUE: ", FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
		frase.add(new Phrase("según documentos que obran en el Archivo de esta Institución, "));		
		frase.add(new Phrase( nombreLegal, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
		frase.add(new Phrase(" con número de matrícula ", FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
		frase.add(new Phrase(codigoAlumno+",", FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
		if (curp.equals("S")){
			frase.add(new Phrase("CURP ", FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			frase.add(new Phrase(curpAlumno+",",FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
		}
		frase.add(new Phrase(" cursó y aprobó en esta Universidad, las siguientes asignaturas "+
				"que cubren " +(certAlum.getAvance().equals("C")?"íntegramente":"parcialmente")+" las materias del Plan de Estudios "+ nivelFrase +" ",
				FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
		frase.add(new Phrase(certPlan.getCarrera()+",",
				FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
		frase.add(new Phrase(" obteniendo las calificaciones que a continuación se especifican:",
				FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
/*
		frase.add(new Phrase(" con RVOE "+certPlan.getRvoe()+" retroactivo a "+aca.util.Fecha.getFechaOficial(fechaRetro.length()<10?"01/01/1950":fechaRetro)+
				", obteniendo las calificaciones que a continuación se especifican:",
				FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
*/
		// Datos que se agregan a la segunda columna 
		cell = new PdfPCell(frase);
		cell.setLeading(0.7f,1.3f);
		if(foto.equals("Sin")){
			cell.setColspan(2);
		}
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setBorder(0);		
		topTable.addCell(cell);		
		document.add(topTable);
		
		//topTable.writeSelectedRows(0, -1, 100, 300, pdf.getDirectContent());
		int columnasExtras = 0;
		if(certPlan.getCurso().equals("S"))
			columnasExtras++;
		if(certPlan.getClave().equals("S"))
			columnasExtras++;
		if(certPlan.getFst().equals("S"))
			columnasExtras++;
		if(certPlan.getFsp().equals("S"))
			columnasExtras++;
		
		float tablewidths[] = new float[columnasExtras+4];
//------------------------------ Fijar tamaños y posiciones de las columnas -------------------------		
	//Evaluar Curso
		if(certPlan.getCurso().equals("S")) //Si tiene el Curso
			tablewidths[0] = 10f;
	//Evaluar Clave
		if(certPlan.getClave().equals("S"))	//Si tiene la Clave
			if(certPlan.getCurso().equals("S"))
				tablewidths[1] = 8f;
			else
				tablewidths[0] = 10f;
		
		float tCurso = 10f, tClave = 8f, tMateria = 35f, tFst = 5, tFsp = 10f, tCrs = 8f, tCalif = 7f, tLetra = 17f; // Suma=100
		//Para el nombre de la materia comprobamos
		if(certPlan.getCurso().equals("S"))
			if(certPlan.getClave().equals("S"))
				if(certPlan.getFst().equals("S"))
					if(certPlan.getFsp().equals("S"))
						tablewidths[2] = tMateria;
					else
						tablewidths[2] = tMateria + tFsp;
				else
					if(certPlan.getFsp().equals("S"))
						tablewidths[2] = tMateria + tFst;
					else
						tablewidths[2] = tMateria + tFst + tFsp;
			else
				if(certPlan.getFst().equals("S"))
					if(certPlan.getFsp().equals("S"))
						tablewidths[1] = tMateria + tClave;
					else
						tablewidths[1] = tMateria + tClave + tFsp;
				else
					if(certPlan.getFsp().equals("S"))
						tablewidths[1] = tMateria + tClave + tFst;
					else
						tablewidths[1] = tMateria + tClave + tFst + tFsp;
		else
			if(certPlan.getClave().equals("S"))
				if(certPlan.getFst().equals("S"))
					if(certPlan.getFsp().equals("S"))
						tablewidths[1] = tMateria + tCurso;
					else
						tablewidths[1] = tMateria + tCurso + tFsp;
				else
					if(certPlan.getFsp().equals("S"))
						tablewidths[1] = tMateria + tCurso + tFst;
					else
						tablewidths[1] = tMateria + tCurso + tFst + tFsp;
			else
				if(certPlan.getFst().equals("S"))
					if(certPlan.getFsp().equals("S"))
						tablewidths[0] = tMateria + tCurso + tClave;
					else
						tablewidths[0] = tMateria + tCurso + tClave + tFsp;
				else
					if(certPlan.getFsp().equals("S"))
						tablewidths[0] = tMateria + tCurso + tClave + tFst;
					else
						tablewidths[0] = tMateria + tCurso + tClave + tFst + tFsp;
	//Evaluar Fst
		if(certPlan.getFst().equals("S"))
			if(certPlan.getCurso().equals("S"))
				if(certPlan.getClave().equals("S"))
					tablewidths[3] = 5f;
				else
					tablewidths[2] = 5f;
			else	//Si no tiene el curso 
				if(certPlan.getClave().equals("S"))	//pero si tiene la clave
					tablewidths[2] = 5f;
				else
					tablewidths[1] = 5f;
	//Evaluar Fsp
		if(certPlan.getFsp().equals("S"))
			if(certPlan.getFst().equals("S"))
				if(certPlan.getCurso().equals("S"))
					if(certPlan.getClave().equals("S"))
						tablewidths[4] = 10f;
					else
						tablewidths[3] = 10f;
				else	//Si no tiene le curso 
					if(certPlan.getClave().equals("S"))	//pero si tiene la clave
						tablewidths[3] = 10f;
					else
						tablewidths[2] = 10f;
			else
				if(certPlan.getCurso().equals("S"))
					if(certPlan.getClave().equals("S"))
						tablewidths[3] = 10f;
					else
						tablewidths[2] = 10f;
				else	//Si no tiene el curso 
					if(certPlan.getClave().equals("S"))	//pero si tiene la clave
						tablewidths[2] = 10f;
					else
						tablewidths[1] = 10f;
	//Para los ultimos 3 campos obligatorios
		if(certPlan.getCurso().equals("S")){
			if(certPlan.getClave().equals("S")){
				if(certPlan.getFst().equals("S")){
					if(certPlan.getFsp().equals("S")){
						tablewidths[5] = tCrs;
						tablewidths[6] = tCalif;
						tablewidths[7] = tLetra;
					}else{
						tablewidths[4] = tCrs;
						tablewidths[5] = tCalif;
						tablewidths[6] = tLetra;
					}
				}else{
					if(certPlan.getFsp().equals("S")){
						tablewidths[4] = tCrs;
						tablewidths[5] = tCalif;
						tablewidths[6] = tLetra;
					}else{
						tablewidths[3] = tCrs;
						tablewidths[4] = tCalif;
						tablewidths[5] = tLetra;
					}
				}
			}else{
				if(certPlan.getFst().equals("S")){
					if(certPlan.getFsp().equals("S")){
						tablewidths[4] = tCrs;
						tablewidths[5] = tCalif;
						tablewidths[6] = tLetra;
					}else{
						tablewidths[3] = tCrs;
						tablewidths[4] = tCalif;
						tablewidths[5] = tLetra;
					}
				}else{
					if(certPlan.getFsp().equals("S")){
						tablewidths[3] = tCrs;
						tablewidths[4] = tCalif;
						tablewidths[5] = tLetra;
					}else{
						tablewidths[2] = tCrs;
						tablewidths[3] = tCalif;
						tablewidths[4] = tLetra;
					}
				}
			}
		}else{
			if(certPlan.getClave().equals("S")){
				if(certPlan.getFst().equals("S")){
					if(certPlan.getFsp().equals("S")){
						tablewidths[4] = tCrs;
						tablewidths[5] = tCalif;
						tablewidths[6] = tLetra;
					}else{
						tablewidths[3] = tCrs;
						tablewidths[4] = tCalif;
						tablewidths[5] = tLetra;
					}
				}else{
					if(certPlan.getFsp().equals("S")){
						tablewidths[3] = tCrs;
						tablewidths[4] = tCalif;
						tablewidths[5] = tLetra;
					}else{
						tablewidths[2] = tCrs;
						tablewidths[3] = tCalif;
						tablewidths[4] = tLetra;
					}
				}
			}else{
				if(certPlan.getFst().equals("S")){
					if(certPlan.getFsp().equals("S")){
						tablewidths[3] = tCrs;
						tablewidths[4] = tCalif;
						tablewidths[5] = tLetra;
					}else{
						tablewidths[2] = tCrs;
						tablewidths[3] = tCalif;
						tablewidths[4] = tLetra;
					}
				}else{
					if(certPlan.getFsp().equals("S")){
						tablewidths[2] = tCrs;
						tablewidths[3] = tCalif;
						tablewidths[4] = tLetra;
					}else{
						tablewidths[1] = tCrs;
						tablewidths[2] = tCalif;
						tablewidths[3] = tLetra;
					}
				}
			}
		}
//		------------------------------ Termina fijar tamaños y posiciones de las columnas -------------------------	
		
		PdfPTable tableEncabezado = new PdfPTable(tablewidths);
		tableEncabezado.getDefaultCell().setBorder(1);
		tableEncabezado.setTotalWidth(document.getPageSize().getWidth() - 72);
		tableEncabezado.setSpacingBefore(5f);
		tableEncabezado.setSpacingAfter(0f);
		if(certPlan.getCurso().equals("S")){
			cell = new PdfPCell(new Phrase("CURSO ESCOLAR",
	        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(3);
			tableEncabezado.addCell(cell);
		}
		if(certPlan.getClave().equals("S")){
			cell = new PdfPCell(new Phrase("CLAVE",
	        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(3);
			tableEncabezado.addCell(cell);
		}
		
		cell = new PdfPCell(new Phrase("MATERIAS",
        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(3);
		tableEncabezado.addCell(cell);

		if(certPlan.getFst().equals("S")){
			cell = new PdfPCell(new Phrase(certPlan.getTitulo1(),
	        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(3);
			tableEncabezado.addCell(cell);
		}
		if(certPlan.getFsp().equals("S")){
			cell = new PdfPCell(new Phrase(certPlan.getTitulo2(),
	        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(3);
			tableEncabezado.addCell(cell);
		}
		
		cell = new PdfPCell(new Phrase(certPlan.getTitulo3(),
        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(3);
		tableEncabezado.addCell(cell);
		
		cell = new PdfPCell(new Phrase("CALIF.",
        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(3);
		tableEncabezado.addCell(cell);
		
		cell = new PdfPCell(new Phrase("CON LETRA",
        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(3);
		tableEncabezado.addCell(cell);
		
		document.add(tableEncabezado);

		boolean completo 	= true;
		float creditos 		= 0;
		int materias		= 0;
		
		for(int i = 0; i < lisCiclos.size(); i++){
			certCiclo = (CertCiclo) lisCiclos.get(i);
			
			// ************************** Verifica si el alumno tiene materias en este ciclo *********************************
			if (aca.cert.CertAlumNotaUtil.getNumMatCiclo(conEnoc,codigoAlumno, planId, certCiclo.getCicloId()) > 0){
					
				if(certCiclo.getCicloId().equals("7") && certPlan.getPlanId().equals("ESOFT011")){ 	
			   	
			    	int tWidths1[] = new int[1];
			    	
			    	tWidths1[0] = 90;

			    	PdfPTable tt = new PdfPTable(tWidths1.length);
			    	tt.getDefaultCell().setBorder(1);
			    	tt.setTotalWidth(document.getPageSize().getWidth() - 72);
			    	tt.setSpacingBefore(0f);
			    	tt.setSpacingAfter(0f);
			    	tt.setHeaderRows(1);				
			    	tt.setSkipFirstHeader(true);
			    	//tt.setKeepTogether(true);
			    		
					// Nombre del Alumno (Primer Renglón)
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));					
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setColspan(tWidths1.length+1);
					celda.setBorder(0);					
					tt.addCell(celda);
						
						// Nombre de la carrera (Segundo Renglón)
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setColspan(tWidths1.length);
					celda.setBorder(Rectangle.BOTTOM);
					tt.addCell(celda);				

					celda = new PdfPCell(new Phrase("PRÁCTICAS CLÍNICAS \n ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);
					celda.setFixedHeight(17f);
					celda.setColspan(tWidths1.length+1);
					tt.addCell(celda);

				int totalHorasClinica = 0;
				
				for(int j = 0; j < lisCursos.size(); j++){
					//Para que no se salga del array 
					if(j < lisCursos.size()){
						certCurso = (CertCurso) lisCursos.get(j);
					}
					
					boolean existeNota = false;
					
					certAlumNota.setCodigoPersonal(codigoAlumno);
					certAlumNota.setCursoId(certCurso.getCursoId());
					if(certAlumNotaU.existeReg(conEnoc, codigoAlumno, certCurso.getCursoId())){
						certAlumNota = certAlumNotaU.mapeaRegId(conEnoc, codigoAlumno, certCurso.getCursoId());
						existeNota = true;
					}
					
					// Si es del mismo ciclo y tiene la materia en el certificado...
					if(certCurso.getCicloId().equals(certCiclo.getCicloId()) && existeNota){
						//Titulo de la practica clinica
						materia = certCurso.getCursoNombre();
						
						if(j < lisCursos.size()){
							totalHorasClinica = totalHorasClinica + Integer.parseInt(certCurso.getCreditos());
						}
				
						cell = new PdfPCell(new Phrase(materia+" : "+certCurso.getCreditos()+" horas", FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(0);
						tt.addCell(cell);

					}
				}
				
				//----->>> PenUltima fila								
				celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setColspan(tWidths1.length);
				celda.setBorder(Rectangle.BOTTOM);
				tt.addCell(celda);
				
				cell = new PdfPCell(new Phrase("Total de horas de práctica clínica : "+totalHorasClinica+" horas", FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);										
				tt.addCell(cell);
				
// 				int numEspacios = Integer.valueOf(espacios);
				
// 				for(int ii = 0; ii < numEspacios; ii++){
			    		
// 					celda = new PdfPCell(new Phrase("****", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));					
// 					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
// 					celda.setBorder(0);				
// 					tt.addCell(celda);
					
// 				}

				document.add(tt);

			}else{
				
					PdfPTable tableCiclo = new PdfPTable(tablewidths);
					tableCiclo.getDefaultCell().setBorder(1);
					tableCiclo.setTotalWidth(document.getPageSize().getWidth() - 72);
					tableCiclo.setSpacingBefore(0f);
					tableCiclo.setSpacingAfter(0f);
					tableCiclo.setHeaderRows(1);				
					tableCiclo.setSkipFirstHeader(true);
					
					// Si eligio mantener los ciclos en la misma hoja o no
					if (ciclo.equals("J")){	
						tableCiclo.setKeepTogether(true);
					}else{
						tableCiclo.setKeepTogether(false);
					}
					
					/*
					// Nombre del Alumno
					cell = new PdfPCell(new Phrase(nombreLegal,
				        	FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setColspan(columnasExtras + 4);				
					cell.setBorder(0);
					tableCiclo.addCell(cell);				
					
					// Nombre de la carrera
					cell = new PdfPCell(new Phrase(certPlan.getCarrera(),
				        	FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setColspan(columnasExtras + 4-1);
					cell.setBorder(0);
					//cell.setFixedHeight(17f);
					tableCiclo.addCell(cell);				
					
					// Numero de Página
					int pagina = pdf.getPageNumber()+1;				
					cell = new PdfPCell(new Phrase("Hoja "+pagina,
				        	FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);		
					cell.setBorder(0);
					//cell.setFixedHeight(17f);
					tableCiclo.addCell(cell);
					*/
					
					if(certPlan.getCurso().equals("S")){
						cell = new PdfPCell(new Phrase("CURSO ESCOLAR",
				        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(3);					
						tableCiclo.addCell(cell);
					}
					if(certPlan.getClave().equals("S")){
						cell = new PdfPCell(new Phrase("CLAVE",
				        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(3);
						tableCiclo.addCell(cell);
					}			
					cell = new PdfPCell(new Phrase("MATERIAS",
			        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(3);
					tableCiclo.addCell(cell);
		
					if(certPlan.getFst().equals("S")){
						cell = new PdfPCell(new Phrase(certPlan.getTitulo1(),
				        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(3);
						tableCiclo.addCell(cell);
					}
					if(certPlan.getFsp().equals("S")){
						cell = new PdfPCell(new Phrase(certPlan.getTitulo2(),
				        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(3);
						tableCiclo.addCell(cell);
					}			
					cell = new PdfPCell(new Phrase(certPlan.getTitulo3(),
			        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(3);
					tableCiclo.addCell(cell);
					
					cell = new PdfPCell(new Phrase("CALIF.",
			        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(3);
					tableCiclo.addCell(cell);
					
					cell = new PdfPCell(new Phrase("CON LETRA",
			        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(3);
					tableCiclo.addCell(cell);			
		
					if(certPlan.getCurso().equals("S")){
						cell = new PdfPCell(new Phrase("",
				        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						tableCiclo.addCell(cell);
					}
					if(certPlan.getClave().equals("S")){
						cell = new PdfPCell(new Phrase("",
				        		FontFactory.getFont(FontFactory.HELVETICA, size2, Font.NORMAL, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						tableCiclo.addCell(cell);
					}
					
					// Muestra el titulo del semestre
					cell = new PdfPCell(new Phrase(certCiclo.getTitulo(),
			        		FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cell.setFixedHeight(15);
					cell.setBorder(0);
					tableCiclo.addCell(cell);
					
					if(certPlan.getFst().equals("S")){				
						cell = new PdfPCell(new Phrase("",
				        		FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cell.setBorder(0);
						tableCiclo.addCell(cell);
					}
					
					String colFsp = certCiclo.getFsp().equals("X")?" ":certCiclo.getFsp();		
					if(certPlan.getFsp().equals("S")){
						cell = new PdfPCell(new Phrase(colFsp,
				        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cell.setBorder(0);
						tableCiclo.addCell(cell);
					}
					
					// Columna de créditos
					String colCred = certCiclo.getCreditos().equals("X")?" ":certCiclo.getCreditos();
					cell = new PdfPCell(new Phrase(colCred,
			        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cell.setBorder(0);
					tableCiclo.addCell(cell);
					
					// ultimas dos columnas en blanco
					cell = new PdfPCell(new Phrase("",
			        		FontFactory.getFont(FontFactory.HELVETICA, size0, Font.NORMAL, new BaseColor(0,0,0))));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cell.setColspan(3);
					cell.setBorder(0);
					tableCiclo.addCell(cell);				
					
					
					for(int j = 0; j < lisCursos.size(); j++){				
						certCurso = (CertCurso) lisCursos.get(j);
						
						boolean existeNota = false;
						
						certAlumNota.setCodigoPersonal(codigoAlumno);
						certAlumNota.setCursoId(certCurso.getCursoId());
						if(certAlumNotaU.existeReg(conEnoc, codigoAlumno, certCurso.getCursoId())){
							certAlumNota = certAlumNotaU.mapeaRegId(conEnoc, codigoAlumno, certCurso.getCursoId());
							existeNota = true;
						}
						
						// Si es del mismo ciclo y tiene la materia en el certificado...
						if(certCurso.getCicloId().equals(certCiclo.getCicloId()) && existeNota){
							
							
							// Si es materia optativa
							if (certCurso.getTipoCursoId().equals("2")||certCurso.getTipoCursoId().equals("7")){
								materia = certCurso.getCursoNombre()+(certAlumNota.getOptativa()!=null?certAlumNota.getOptativa():"");
								// Se agrega parentesis cuando tiene nombre la optativa
								//if (!certAlumNota.getOptativa().trim().equals("")) materia+=")";
							}else{
								materia = certCurso.getCursoNombre();
							}
							 
		
							if(certPlan.getCurso().equals("S")){
								cell = new PdfPCell(new Phrase(certAlumNota.getCurso(),
						        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorder(0);		
								
								tableCiclo.addCell(cell);
							}
						
							if(certPlan.getClave().equals("S")){
								cell = new PdfPCell(new Phrase(certCurso.getClave(),
						        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorder(0);
								
								tableCiclo.addCell(cell);
							}
							
							
							cell = new PdfPCell(new Phrase(materia,
					        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setBorder(0);
							
							tableCiclo.addCell(cell);
											
		
							if(certPlan.getFst().equals("S")){
								cell = new PdfPCell(new Phrase(certCurso.getFst(),
						        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(0);							
								tableCiclo.addCell(cell);
							}
				
							if(certPlan.getFsp().equals("S")){
								cell = new PdfPCell(new Phrase(certCurso.getFsp(),
						        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(0);
								
								tableCiclo.addCell(cell);
							}					
							cell = new PdfPCell(new Phrase(certCurso.getCreditos(),
					        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(0);
							
							tableCiclo.addCell(cell);						
		
							String nota = "";
							if(existeNota){
								creditos += Float.parseFloat(certCurso.getCreditos());
								materias++;
								nota = certAlumNota.getNota();
								
								cell = new PdfPCell(new Phrase(nota,
						        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(0);
								
								tableCiclo.addCell(cell);
							}else{
								cell = new PdfPCell(new Phrase("",
						        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(0);							
								tableCiclo.addCell(cell);
								
								completo = false;
							}
							
							frase = new Phrase();
							frase.add(new Phrase(certAlumNota.getNotaLetra()+" ",
						       		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));					
							
							cell = new PdfPCell(frase);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setBorder(0);
							
							tableCiclo.addCell(cell);
						}
					} // termina for de cursos				
						
					if(pdf.getPageNumber() != paginaAnterior){
						pagPrn[pdf.getPageNumber()-1] = 1;
						paginaAnterior = pdf.getPageNumber();
						printEncabezado = true;	            
					} // fin de if de salto de pagina
					
					//Lineas de cada semestre				
					if(SemLineas.contains(certCiclo.getCicloId())){
						
						frase = new Phrase();
						frase.add(new Phrase(" ",
					      		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(0,0,0))));					
						cell = new PdfPCell(frase);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setColspan(7);
						
						cell.setBorder(Rectangle.TOP);
						tableCiclo.addCell(cell);
					}
					// Agrega la tabla del ciclo(Semestre o tetramestre).
					document.add(tableCiclo);
				}
			} // fin de si el ciclo tiene materias
				
		} // termina for de ciclos	
		
						
		if (certAlum.getEquivalencia().trim().equals("NA")){
			PdfPTable tblLinea = new PdfPTable(1);
			tblLinea.setTotalWidth(document.getPageSize().getWidth()- 72);		
			tblLinea.setSpacingBefore(3f);
			tblLinea.setSpacingAfter(2f);		
			
			// Primer Equivalencia
			celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
			celda.setBorder(Rectangle.TOP);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);		
						
			tblLinea.addCell(celda);
			
			document.add(tblLinea);
		}else{
			/******** Tabla de Equivalencias *****************/
			PdfPTable tblEquiv = new PdfPTable(1);			
			tblEquiv.setTotalWidth(document.getPageSize().getWidth()- 72);			
			tblEquiv.setSpacingBefore(3f);
			tblEquiv.setSpacingAfter(2f);
			
			// Primer Equivalencia
			celda = new PdfPCell(new Phrase(certAlum.getEquivalencia(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
			celda.setBorder(Rectangle.TOP);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			tblEquiv.addCell(celda);
			
			document.add(tblEquiv);
			// ------FIN Equivalencias------
		}
		
	// ---->>>>>>>>>>>>>>>>>>>>>>>Tabla "Requisitos Complementarios"
	
			float w = 80f;		
	    	int numComp = Integer.parseInt(certPlan.getComponente()==null?"0":certPlan.getComponente());	    	
	    	int tWidths[] = new int[numComp+1];
	    	
	    	switch (numComp){
	    		case 4:{
	    			tWidths[0]= 52;	tWidths[1]= 12; tWidths[2]= 12;	tWidths[3]= 12; 
	    			tWidths[4]= 12;
	    			break;
	    		}
		    	case 6:{
		    		tWidths[0]= 40;	tWidths[1]= 10; tWidths[2]= 10;	tWidths[3]= 10; 
		    		tWidths[4]= 10; tWidths[5]= 10;	tWidths[6]= 10; 
		    		break;
		    	}
		    	case 8:{
		    		tWidths[0]= 36;	tWidths[1]= 8; tWidths[2]= 8;	tWidths[3]= 8; 
		    		tWidths[4]= 8; tWidths[5]= 8;	tWidths[6]= 8; tWidths[7]= 8;
		    		tWidths[8]= 8;
		    		break;
		    	}
		    	case 9:{
		    		tWidths[0]= 37;	tWidths[1]= 7; tWidths[2]= 7;	tWidths[3]= 7; 
		    		tWidths[4]= 7; tWidths[5]= 7;	tWidths[6]= 7; tWidths[7]= 7;
		    		tWidths[8]= 7; tWidths[9]= 7;
		    		break;
		    	}
		    	case 10:{
		    		tWidths[0]= 30;	tWidths[1]= 7; tWidths[2]= 7;	tWidths[3]= 7;
		    		tWidths[4]= 7; tWidths[5]= 7;	tWidths[6]= 7; tWidths[7]= 7;
		    		tWidths[8]= 7; tWidths[9]= 7; tWidths[10]= 7;
		    		break;
		    	}		    	
		    	case 12:{
		    		tWidths[0]= 28;	tWidths[1]= 6; tWidths[2]= 6;	tWidths[3]= 6;
		    		tWidths[4]= 6; tWidths[5]= 6;	tWidths[6]= 6; tWidths[7]= 6;
		    		tWidths[8]= 6; tWidths[9]= 6; tWidths[10]= 6; tWidths[11]= 6; tWidths[12]= 6; 
		    		break;
		    	}
		    	
	    	}
	    	
	    	//System.out.println("Componentes:"+numComp);
	    	if (numComp > 0){
	    			
				PdfPTable t = new PdfPTable(numComp+1);
				t.setWidths(tWidths);
				t.setSpacingBefore(0f);
				t.setSpacingAfter(5f);
				t.setWidthPercentage(w);
				//t.setKeepTogether(true);
				
				// Agregar el encabezado del nombre del alumno, carrera y numero de hoja antes de "REQUISITOS"
				if (certAlum.getEncabezado().equals("1")){
					
					// Nombre del Alumno (Primer Renglón)
// 					celda = new PdfPCell(new Phrase(nombreLegal,
// 				        	FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));					
// 					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
// 					celda.setColspan(numComp+1);
// 					celda.setBorder(0);					
// 					t.addCell(celda);

					// Nombre de la carrera (Segundo Renglón)
// 					celda = new PdfPCell(new Phrase(certPlan.getCarrera(),
// 				        	FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setColspan(numComp);
					celda.setBorder(Rectangle.BOTTOM);
					//celda.setBorderWidthBottom(1);
					t.addCell(celda);				
					
					// Numero de Página (Segundo Renglón)
// 					int pagina = pdf.getPageNumber()+1;				
// 					celda = new PdfPCell(new Phrase("Hoja "+pagina,
// 				        	FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);					
					celda.setBorder(Rectangle.BOTTOM);			
					celda.setFixedHeight(17f);
					t.addCell(celda);
					
					// Renglon en Blanco
					celda = new PdfPCell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));					
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setColspan(numComp+1);
					celda.setBorder(0);					
					t.addCell(celda);
				}
				
				// Pinta el borde de arriba y abajo
				//celda.setBorder(Rectangle.BOTTOM|Rectangle.TOP);
				
				celda = new PdfPCell(new Phrase("REQUISITOS COMPLEMENTARIOS \n "
						, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOTTOM);
				celda.setFixedHeight(17f);
				celda.setColspan(numComp+1);
				t.addCell(celda);
				
			    // ---primera fila	
			
				celda = new PdfPCell(new Phrase(""
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOTTOM);
				celda.setColspan(1);
				t.addCell(celda);
				
				celda = new PdfPCell(new Phrase("I "
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOTTOM);
				celda.setColspan(1);
				t.addCell(celda);
				
				celda = new PdfPCell(new Phrase("II "
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOTTOM);
				celda.setColspan(1);
				t.addCell(celda);
				
				celda = new PdfPCell(new Phrase("III "
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOTTOM);
				celda.setColspan(1);
				t.addCell(celda);
				
				celda = new PdfPCell(new Phrase("IV "
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.BOTTOM);
				celda.setColspan(1);
				t.addCell(celda);
				
				if (numComp>=6){
				
					celda = new PdfPCell(new Phrase("V "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);
					celda.setColspan(1);
					t.addCell(celda);
					
					celda = new PdfPCell(new Phrase("VI "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);
					celda.setColspan(1);
					t.addCell(celda);
				}				
				if (numComp>=8){
					celda = new PdfPCell(new Phrase("VII "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);
					celda.setColspan(1);
					t.addCell(celda);
					
					celda = new PdfPCell(new Phrase("VIII "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);					
					t.addCell(celda);
				}
				if (numComp>=9){
					celda = new PdfPCell(new Phrase("IX "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);					
					t.addCell(celda);
				}
				if (numComp>=10){
					celda = new PdfPCell(new Phrase("X "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);					
					t.addCell(celda);
				}
				if (numComp>=12){
					celda = new PdfPCell(new Phrase("XI "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);
					celda.setColspan(1);
					t.addCell(celda);
					
					celda = new PdfPCell(new Phrase("XII "
							, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);					
					t.addCell(celda);
				}
				
				//----segunda fila
				celda = new PdfPCell(new Phrase("SERVICIO COMUNITARIO"
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				t.addCell(celda);
				
				int[] comp = aca.vista.AlumnoCursoUtil.getArrayComponente(conEnoc, codigoAlumno, planId, "SC");
				String texto = "";
				
				for (int i=0; i<numComp;i++){
					if (comp[i]==1) texto = "Ac"; else texto = "-";
					celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.NO_BORDER);					
					t.addCell(celda);					
				}
								
				
				//---Tercer fila
				
				celda = new PdfPCell(new Phrase("APTITUD FÍSICA"
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				t.addCell(celda);
				
				comp = aca.vista.AlumnoCursoUtil.getArrayComponente(conEnoc, codigoAlumno, planId, "AF");
				texto = "";
				
				for (int i=0; i<numComp;i++){
					if (comp[i]==1) texto = "Ac"; else texto = "-";
					celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.NO_BORDER);					
					t.addCell(celda);					
				}
				
				//---Cuarta fila
				
				celda = new PdfPCell(new Phrase("TRABAJO MANUAL"
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(1);
				t.addCell(celda);
				
				comp = aca.vista.AlumnoCursoUtil.getArrayComponente(conEnoc, codigoAlumno, planId, "TM");
				texto = "";
				
				for (int i=0; i<numComp;i++){
					if (comp[i]==1) texto = "Ac"; else texto = "-";
					celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.NO_BORDER);					
					t.addCell(celda);					
				}
				
				//---Quinta fila
				
				celda = new PdfPCell(new Phrase("LEGADO CULTURAL"
						, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(Rectangle.BOTTOM);
				t.addCell(celda);
				
				comp = aca.vista.AlumnoCursoUtil.getArrayComponente(conEnoc, codigoAlumno, planId, "LC");
				texto = "";
				
				for (int i=0; i<numComp;i++){
					if (comp[i]==1) texto = "Ac"; else texto = "-";
					celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA, size1, Font.BOLD, new BaseColor(r,g,b))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorder(Rectangle.BOTTOM);	
					t.addCell(celda);					
				}
				
				//----->>> Ultima fila								
				celda = new PdfPCell(new Phrase(certPlan.getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(r,g,b))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setColspan(numComp+1);
				t.addCell(celda);
				
				document.add(t);				
	    	}    		    	
//			------------------------------ Construye el Parrafo Final -------------------------
			float bottomWidths[] = {90f,10};
			PdfPTable bottomTable = new PdfPTable(bottomWidths);
			bottomTable.getDefaultCell().setBorder(0);
			bottomTable.setTotalWidth(document.getPageSize().getWidth() - 72);
			bottomTable.setSpacingBefore(10f);
			bottomTable.setSpacingAfter(0f);
			//bottomTable.setKeepTogether(true);	
			
	    	frase = new Phrase();
			ArrayList<String> frases = aca.cert.CertPlanUtil.getFrases(certPlan.getTFinal());
			
			String renglon = "";
			for (int i=0; i<frases.size(); i++){
				renglon = (String) frases.get(i);
				if (renglon.indexOf("$creditos$") != -1){
					
					// Verifica si es un plan de estudios con decimales en los créditos					 
					int credEntero = (int)creditos; 
					if (creditos==credEntero){					
						renglon = String.valueOf(credEntero);
					}else{
						renglon = String.valueOf(creditos);
					}
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
					
				}else if(renglon.indexOf("$materias$") != -1){
					
					renglon = request.getParameter("pdfmaterias");
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
					
				}else if(renglon.indexOf("$promedio$") != -1){
					
					renglon = request.getParameter("pdfpromedio");
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
					
				}else if(renglon.indexOf("$escala$") != -1){
					
					renglon = Double.parseDouble(request.getParameter("pdfpromedio"))>10?"0 A 100 (CERO A CIEN)":"0 A 10 (CERO A DIEZ)";
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
					
				}else if(renglon.indexOf("$aprobatoria$") != -1){
					
					renglon = Double.parseDouble(request.getParameter("pdfpromedio"))>10?"70 (SETENTA)":"7 (SIETE)";
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
					
				}else if(renglon.indexOf("$materias$") != -1){
					
					renglon = request.getParameter("pdfmaterias");
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
					
				}else if (renglon.indexOf("<b") != -1){
					
					renglon = renglon.replace("</b>","").replace("<b>","");
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
				}else if (renglon.indexOf("</b") != -1){
					
					renglon = renglon.replace("</b>","").replace("<b>","");
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
				}else{
					frase.add( new Phrase(renglon, FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
				}
			}
			
			//INSERTA LOS ESPACIOS EN BLANCO QUE EL USUARIO QUIERE
			float numEspacios = Float.valueOf(espacios);
			
			if(numEspacios % 2 != 0){
				numEspacios++;
			}
			
			for(int ii = 0; ii < numEspacios; ii++){
				celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, size2, Font.BOLD, new BaseColor(0,0,0))));					
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setBorder(0);				
				bottomTable.addCell(celda);
			}
			
			cell = new PdfPCell(frase);
			cell.setLeading(1.5f,1.1f);
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setColspan(2);
			cell.setBorder(0);
			bottomTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(" ",
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(8f);
			cell.setColspan(2);
			cell.setBorder(0);			
			bottomTable.addCell(cell);
			
			// **** Nombre del dia ****
			String diaNombre 	= aca.util.NumberToDay.convertirLetras(Integer.parseInt(fecha.getDia(certAlum.getFecha()))).trim();
			if(diaNombre.equals("diecis&eacute;is")){
				diaNombre = "dieciséis";
			}else if(diaNombre.equals("veinti&uacute;n")){
				diaNombre = "veintiún";
			}else if(diaNombre.equals("veintid&oacute;s")){
				diaNombre = "veintidós";
			}else if(diaNombre.equals("veintitr&eacute;s")){
				diaNombre = "veintitrés";
			}
			if (diaNombre.equals("primer")){
				diaNombre = " al "+diaNombre+" día "; 
			}else{
				diaNombre = " a los "+diaNombre+" días ";
			}
			
			//***** Nombre del año en letra ****
			String yearName		= aca.util.NumberToLetter.convertirYear(Integer.parseInt(fecha.getYear(certAlum.getFecha()))).trim();
			
			frase = new Phrase();			
			frase.add(new Phrase("Para los fines y usos que convengan, se extiende el presente ",
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			frase.add(new Phrase("CERTIFICADO "+(certAlum.getAvance().equals("C")? "COMPLETO," : "PARCIAL,"),
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.BOLD, new BaseColor(0,0,0))));
			frase.add(new Phrase(" en la ciudad de Montemorelos, Nuevo León, México,"+
					diaNombre+"del mes de "+fecha.getMesNombre(certAlum.getFecha())+
					" del año "+yearName+".",
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			cell = new PdfPCell(frase);
			cell.setLeading(1.5f,1.1f);
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setColspan(2);
			cell.setBorder(0);
			bottomTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(" ",
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(40f);
			cell.setColspan(2);
			cell.setBorder(0);
			bottomTable.addCell(cell);			
			
			
			cell = new PdfPCell(new Phrase("DIRECTOR DE SERVICIOS ESCOLARES",
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			cell.setBorder(0);
			bottomTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase("-----------",
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(37f);
			cell.setColspan(2);
			cell.setBorder(0);
			bottomTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(Parametros.getCertificados().toUpperCase(),
	        		FontFactory.getFont(FontFactory.HELVETICA, size3, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			cell.setBorder(0);
			bottomTable.addCell(cell);
			
			// Texto que indica que el plan de estudios no tiene revoe
			if(planId.equals("TEO2000*")){
			cell = new PdfPCell(new Phrase("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nESTUDIOS SIN RECONOCIMIENTO DE VALIDEZ OFICIAL",
	        		FontFactory.getFont(FontFactory.HELVETICA, size1, Font.NORMAL, new BaseColor(0,0,0))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			cell.setBorder(0);
			bottomTable.addCell(cell);			
			
			}
			
			document.add(bottomTable);
			
	}catch(IOException ioe){
		System.err.println("Error certificado en PDF: "+ioe.getMessage());
	}	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");		
	}
	
	String nombreArchivo = codigoAlumno+planId.replace("*","")+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
 %>
 <%@ include file= "../../cierra_enoc.jsp" %>
 