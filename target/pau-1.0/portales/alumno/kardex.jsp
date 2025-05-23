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

<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="curso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="alumnoCursoUtil" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="cursoUtil" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="TipoCalU" scope="page" class="aca.catalogo.TipoCalUtil"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumPersonalU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="mapaPlan" scope="page" class="aca.plan.MapaPlan"/>
<jsp:useBean id="MapaPlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="cancelaEstudio" scope="page" class="aca.alumno.CancelaEstudio"/>
<jsp:useBean id="cancelaEstudioU" scope="page" class="aca.alumno.CancelaEstudioUtil"/>
<jsp:useBean id="mapaCredito" scope="page" class="aca.plan.MapaCredito"/>
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<%
java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.0;(###,##0.0)");
	java.text.NumberFormat nf = java.text.NumberFormat.getInstance(); 
	nf.setMinimumFractionDigits(2); 
	nf.setMaximumFractionDigits(1);
	
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String institucion 		= (String)session.getAttribute("institucion");
	
	Parametros.mapeaRegId(conEnoc, "1");
	
	alumno = AlumPersonalU.mapeaRegId(conEnoc,matricula);
	String planid=request.getParameter("plan");
	if(planid==null){
		plan.mapeaRegId(conEnoc,matricula);
		planid=plan.getPlanId();
	}
	else plan.mapeaRegId(conEnoc,matricula,planid);
	
	if(!cancelaEstudioU.existeRegId(conEnoc, matricula, plan.getPlanId())){ 
	
	String planActual=plan.getPlanId();
	ArrayList lisCursosAlumno = alumnoCursoUtil.getListAlumno(conEnoc,matricula," AND PLAN_ID='"+planid+"' AND TIPOCAL_ID IN ('1','I') AND NOTA !=0 AND CREDITOS !=0 ORDER BY CICLO, NOMBRE_CURSO");
	if(lisCursosAlumno.size()>0)alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(0);
	
	ArrayList lisCursos = cursoUtil.getLista(conEnoc, planid, "AND CREDITOS != 0 ORDER BY CICLO, NOMBRE_CURSO");
	String colorNota    = "";
	String escala       = plan.getEscala();
	
	
	Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
	document.setMargins(35,35,20,20);

	try{
		int contCurso=0,contSem=0,totalAcreditados = 0;
		float totalCreditos = 0;
		String semestre;
		boolean nuevaFila=false,OKc,OKe,OKd,Salir=false;
			
		semestre = "0";
		double sumaNota[] = new double[20];
		double sumaCreditos[] = new double[20];
		double sumaCreditosAC[] = new double[20];
		double creditosNoValidos[] = new double[20];
		double creditosOptativosAC[] = new double[20];
		int numCursos=0,cursosAprobados=0,cursosDesaprobados=0,cursosRetirados=0;
		float creditosAprobados=0,creditosDesaprobados=0;
		mapaPlan = MapaPlanU.mapeaRegId(conEnoc, alumnoCurso.getPlanId());
		
		String dir = application.getRealPath("/portales/alumno/")+"/"+"kardex.pdf";
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		document.addAuthor("Direccion de Sistemas");
		        document.addSubject("Kardex de "+alumno.getNombre());
		HeaderFooter footer = new HeaderFooter(new Phrase("Nota: Cr=Creditos; HT=Hrs teóricas; HP=Hrs prácticas; Tipo= de nota[Ext=Extraordinaria; Conv=Convalidacion; TS=Título Suficiencia; CD=Calif. Diferida]; Edo=Estado", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)), true);
		        document.setFooter(footer);
		        document.open();
		        
		        Image jpg = null;
		dir=application.getRealPath("/WEB-INF/fotos/")+"/";
		java.io.File f = new java.io.File(dir+session.getAttribute("mat")+".jpg");
		if(f.exists()){
			jpg = Image.getInstance(dir+matricula+".jpg");
		}else{
			jpg = Image.getInstance(dir+"nofoto.png");
		}
		jpg.scaleAbsolute(60, 78);
		jpg.setAbsolutePosition(35, 695);
		document.add(jpg);
		        
		        Tabla datatable = new Tabla(2);
		            datatable.setBorder(2);
		            datatable.setPadding(0);
		            datatable.setSpacing(0);
		            //datatable.setBorder(Rectangle.NO_BORDER);
		            int headerwidths[] = {50,50};
		            datatable.setWidths(headerwidths);
		            datatable.setWidth(100);
		            
		            
		            Cell cell = new Cell(new Phrase(institucion.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            cell.setColspan(2);
		            cell.setBorder(Rectangle.NO_BORDER);
		            cell.setBackgroundColor(new BaseColor(255,255,255));
		            datatable.addCell(cell);
		            
		            cell = new Cell(new Phrase("Registro de control escolar", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            cell.setColspan(2);
		            cell.setBorder(Rectangle.NO_BORDER);
		            cell.setBackgroundColor(new BaseColor(255,255,255));
		            datatable.addCell(cell);
		            
		            cell = new Cell(new Phrase(alumnoUtil.getCarrera(conEnoc,matricula, alumnoCurso.getPlanId()), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            cell.setColspan(2);
		            cell.setBorder(Rectangle.NO_BORDER);
		            cell.setBackgroundColor(new BaseColor(255,255,255));
		            datatable.addCell(cell);
		            
		         	String fInicio = mapaPlan.getFInicio();
		         	
		         	if(fInicio == null | fInicio.trim().equals(""))
		         		fInicio = "******";
		         	else
		         		fInicio = fecha.getFechaTexto(fInicio);
		         	
		            cell = new Cell(new Phrase("Estudios con R.V.O.E. No. "+mapaPlan.getRvoe()+" de fecha "+fInicio+"\n otorgado por el Gobierno del Estado de Nuevo León", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            cell.setColspan(2);
		            cell.setBorder(Rectangle.NO_BORDER);
		            cell.setBackgroundColor(new BaseColor(255,255,255));
		            datatable.addCell(cell);
		            
		            cell = new Cell(new Phrase("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            cell.setColspan(2);
		            cell.setBorder(Rectangle.NO_BORDER);
		            cell.setBackgroundColor(new BaseColor(255,255,255));
		            datatable.addCell(cell);
		            
		            datatable.setDefaultCellBorderWidth(0);
		            
		            String fechaIngreso = aca.alumno.AlumUtil.getFechaIngreso(conEnoc, matricula, alumnoCurso.getPlanId());
		            String fechaNacimiento = alumno.getFNacimiento();
		            if(fechaIngreso == null)
		            	fechaIngreso = "******";
		            else
		            	fechaIngreso = fecha.getFechaTexto(fechaIngreso);
		            
		            if(fechaNacimiento == null)
		            	fechaNacimiento = "******";
		            else
		            	fechaNacimiento = fecha.getFechaTexto(fechaNacimiento);
		            datatable.addCell(new Phrase("Nombre: "+alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
		            datatable.addCell(new Phrase("No. Mat.: "+matricula, FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
		            datatable.addCell(new Phrase("Fecha de Ingreso: "+fechaIngreso+"\n ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
		            datatable.addCell(new Phrase("Fecha de Impresión: "+fecha.getFechaTexto(aca.util.Fecha.getHoy())+"\n ", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
		            
	            document.add(datatable);
	            
	            
	            boolean cursado;
	    		int cont = 0;
	    		
	    		PdfPTable tabla = new PdfPTable(12);
	    		int headerwidthss[] = {3,  8,     40,     4,  3,   3,  5,    9,     5,    9,    5,    4};
	            tabla.setWidths(headerwidthss);
	            tabla.setSpacingAfter((float)20);
	            int pagina = -1;
	            
	            PdfPCell celda = null;
	            
	    		for(int j = 0; j < lisCursos.size(); j++){	//Ciclo que recorre (el listor) todos los cursos de la carrera
	    			aca.plan.MapaCurso mapaCurso = (aca.plan.MapaCurso) lisCursos.get(j);
	    			nuevaFila=false;
	    			cursado = false;
	    			for(int i = 0; i < lisCursosAlumno.size(); i++){//Ciclo que ubica el curso con el mismo ya cursado por el alumno
	    				alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(i);
	    				if(mapaCurso.getCursoId().equals(alumnoCurso.getCursoId())){
	    					i = lisCursos.size();
	    					cursado = true;
	    				}
	    			}
	    			
	    			if(!semestre.equals(mapaCurso.getCiclo())){//Condicion de cambio de ciclo
	    				
	    				
	    				if(!semestre.equals("0")){//Condicion para que no entre la primera vez
	    					String promedio = "", estado = "";
	    					int nSemestre = Integer.parseInt(semestre);
	    					double promMes = sumaNota[nSemestre]/((sumaCreditosAC[nSemestre]+creditosOptativosAC[nSemestre])-creditosNoValidos[nSemestre]);
	    					if (sumaCreditosAC[nSemestre] > 0 || creditosOptativosAC[nSemestre] > 0){
	    						promedio = nf.format(promMes);
	    					}else
	    						promedio = "0.00";
	    					
	    					totalCreditos += Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim());
	    					totalAcreditados += sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre];
		    	            if((Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim()))==(sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre])) 
		    	            	estado = "¡Completo!"; 
		    	            else 
		    	            	estado = "¡Incompleto!"; 
		    	            
		    	            celda = new PdfPCell(new Phrase(
		    		            	"Creditos["+
		    		                	"Tot: "+(Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim()))+"; "+
		    		                	"CO: "+mapaCredito.getCreditos()+"; CE: "+mapaCredito.getOptativos()+";] "+ 
		    		                "Faltan["+
		    		                	"CO: "+(int)(Float.parseFloat(mapaCredito.getCreditos().trim())-sumaCreditosAC[nSemestre]) +"; "+ 
		    		                	"CE: "+(Float.parseFloat(mapaCredito.getOptativos().trim())-creditosOptativosAC[nSemestre]) +"; "+
		    		                "] "+
		    		                "["+estado+"]", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(248,128,7))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setColspan(13);
					celda.setBorder(Rectangle.NO_BORDER);
		            tabla.addCell(celda);
	    				}//Termina if de primera vez
	    				mapaCredito.mapeaRegId(conEnoc, mapaCurso.getPlanId(), mapaCurso.getCiclo());
	    				semestre = mapaCurso.getCiclo().trim();
	    				cont = 0;    	            
	    				
	    				document.add(tabla);
	    				tabla = new PdfPTable(12);
	    	            tabla.setWidths(headerwidthss);
	    	            //tabla.setSpacingBefore((float)3);
	    	            //tabla.setSpacingAfter((float)5);
	    	            tabla.setTotalWidth((float)541);
	    	            tabla.setLockedWidth(true);
	    	            //tabla.KEEPTOGETHER = true;
	    	            
	    	            
	    	            celda = new PdfPCell(new Phrase(aca.plan.CreditoUtil.getTitulo(conEnoc, mapaCurso.getPlanId(), mapaCurso.getCiclo()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
	    	            celda.setBorder(Rectangle.NO_BORDER);
	    	            //celda.setNoWrap(true);
	    	            celda.setColspan(12);
	    	            tabla.addCell(celda);
	    	            
	    	            
	    	            if(pagina != document.getPageNumber()){
		            celda = new PdfPCell(new Phrase("#", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("Clave", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            tabla.addCell(new Phrase("Nombre de la Materia", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
		            celda = new PdfPCell(new Phrase("Cr.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("HT", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("HP", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("Cal", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("Fecha", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("Extra", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("F. Extra", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("Tipo", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("Edo", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            pagina = document.getPageNumber();
	            }
	    
	    			}//Termina if de cambio de ciclo
	    			cont++;
	    			sumaCreditos[Integer.parseInt(semestre)] += Float.parseFloat(mapaCurso.getCreditos());
	    			String tipoCurso = mapaCurso.getTipoCursoId().trim();
	    			
	    			int color1,color2,color3;
	    			if(cursado){//Si el ciclo fue cursado por el alumno
	    				OKc=OKe=false;numCursos++;
	    				if (!alumnoCurso.getNotaExtra().trim().equals("0"))OKe=true;
	    				if (!alumnoCurso.getNota().trim().equals("0")) OKc=true;
	    				
	    				if(tipoCurso.equals("1") | tipoCurso.equals("2") | tipoCurso.equals("7")){
	    					if (OKe){
	    						sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNotaExtra())*Float.parseFloat(alumnoCurso.getCreditos());
	    					}else{
	    						sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNota())*Float.parseFloat(alumnoCurso.getCreditos());
	    					}
	    					if(OKe || OKc){
	    						if(tipoCurso.equals("1"))
	    							sumaCreditosAC[Integer.parseInt(semestre)]+=Float.parseFloat(alumnoCurso.getCreditos());
	    						else
	    							creditosOptativosAC[Integer.parseInt(semestre)] += Float.parseFloat(alumnoCurso.getCreditos());
	    					}
	    				}else{
	    					creditosNoValidos[Integer.parseInt(semestre)] += Float.parseFloat(mapaCurso.getCreditos());
	    				}
	    				if (alumnoCurso.getTipoCalId().equals("1")){
	    					cursosAprobados++;
	    					creditosAprobados+=Float.parseFloat(alumnoCurso.getCreditos());
	    				}else if (alumnoCurso.getTipoCalId().equals("2")){
	    					cursosDesaprobados++;
	    					creditosDesaprobados+=Float.parseFloat(alumnoCurso.getCreditos());
	    				}else if (alumnoCurso.getTipoCalId().equals("2")) cursosRetirados++;
	    				/*if (alumnoCurso.getEstado().equals("I")){ 
	    					color1 = 0; // #66CC00
					color2 = 0;
					color3 = 255;
	    				}else{ 
	    					color1 = 255;
					color2 = 0;
					color3 = 0;
	    				}*/
	    				
	    				celda = new PdfPCell(new Phrase(String.valueOf(cont), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				tabla.addCell(new Phrase(alumnoCurso.getCursoId().substring(8), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				tabla.addCell(new Phrase(alumnoCurso.getNombreCurso(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda = new PdfPCell(new Phrase(nf.format(Double.parseDouble(alumnoCurso.getCreditos())) , FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase(alumnoCurso.getHt(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase(alumnoCurso.getHp(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				String nota 		= "0";
				if(OKc){
					if (escala.equals("10"))
						nota = getFormato.format( Double.valueOf(String.valueOf(Integer.parseInt(alumnoCurso.getNota())/10)) );
					else
						nota = alumnoCurso.getNota();
					celda = new PdfPCell(new Phrase(nota, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
				}else{
					celda = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
				}
				celda = new PdfPCell(new Phrase(alumnoCurso.getFEvaluacion(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				if(OKe){
					celda = new PdfPCell(new Phrase(alumnoCurso.getNotaExtra(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
				}else{
					celda = new PdfPCell(new Phrase("***", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
				}
				if(alumnoCurso.getFExtra()==null){
					celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
				}else{
					celda = new PdfPCell(new Phrase(alumnoCurso.getFExtra(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
				}
				
	                	if(alumnoCurso.getTipoCalId().trim().equals("1") | alumnoCurso.getTipoCalId().trim().equals("2")){
	                		if(alumnoCurso.getTitulo().trim().equals("S")){
	                			celda = new PdfPCell(new Phrase("TS", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
						celda.setHorizontalAlignment(Element.ALIGN_CENTER);
						tabla.addCell(celda);
	                		}
					else if(alumnoCurso.getConvalidacion().trim().equals("S")){
						celda = new PdfPCell(new Phrase("Conv", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
						celda.setHorizontalAlignment(Element.ALIGN_CENTER);
						tabla.addCell(celda);
					}
					else if(alumnoCurso.getNotaExtra().trim().equals("0")){
						celda = new PdfPCell(new Phrase("Ord", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
						celda.setHorizontalAlignment(Element.ALIGN_CENTER);
						tabla.addCell(celda);
					}
					else{
						celda = new PdfPCell(new Phrase("Ext", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
						celda.setHorizontalAlignment(Element.ALIGN_CENTER);
						tabla.addCell(celda);
					}
	                	}
	                	else{
	                		if(alumnoCurso.getTipoCalId().trim().equals("3")){
	                			celda = new PdfPCell(new Phrase("Baja", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
						celda.setHorizontalAlignment(Element.ALIGN_CENTER);
						tabla.addCell(celda);
	                		}
	                		else if(alumnoCurso.getTipoCalId().trim().equals("4")){
	                			celda = new PdfPCell(new Phrase("Dif", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
						celda.setHorizontalAlignment(Element.ALIGN_CENTER);
						tabla.addCell(celda);
	                		}
	                			else if(alumnoCurso.getTipoCalId().trim().equals("5")){
	                				celda = new PdfPCell(new Phrase("RA", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
							celda.setHorizontalAlignment(Element.ALIGN_CENTER);
							tabla.addCell(celda);
	                			}
	                	}
	                	aca.catalogo.CatTipoCal ctc = new aca.catalogo.CatTipoCal(); 
	                	ctc = TipoCalU.mapeaRegId(conEnoc, alumnoCurso.getTipoCalId());  					
	                	celda = new PdfPCell(new Phrase(ctc.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
	    			}else{//Termina if de cursados
	    				
	    				String creditos = "";
	    				if(mapaCurso.getCreditos().length() <= 1){
	    					creditos = mapaCurso.getCreditos() + ".0";
	    				}else{
	    					creditos = nf.format(Double.parseDouble(mapaCurso.getCreditos()));
	    				}
	    				
	    				celda = new PdfPCell(new Phrase(String.valueOf(cont), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				tabla.addCell(new Phrase(mapaCurso.getCursoId().substring(8), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				tabla.addCell(new Phrase(mapaCurso.getNombreCurso(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda = new PdfPCell(new Phrase(creditos, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase(mapaCurso.getHt(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase(mapaCurso.getHp(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase("***", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase("***", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase("****", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
				celda = new PdfPCell(new Phrase("***", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(0,0,0))));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.addCell(celda);
	    			}//else de cursados   
	    			
	    		}  //for principal que recorre el listor
	    		String promedio = "", estado = "";
	    		int nSemestre = Integer.parseInt(semestre);
	    		double promMes = sumaNota[nSemestre]/((sumaCreditosAC[nSemestre]+creditosOptativosAC[nSemestre])-creditosNoValidos[nSemestre]);
	    		if (sumaCreditosAC[nSemestre] > 0 || creditosOptativosAC[nSemestre] > 0){
	    			promedio = nf.format(promMes);
	    		}else
	    			promedio = "0.00";
	    		
	    		totalCreditos += Integer.parseInt(mapaCredito.getCreditos().trim())+Integer.parseInt(mapaCredito.getOptativos().trim());
	    		totalAcreditados += sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre];
	            if((Integer.parseInt(mapaCredito.getCreditos().trim())+Integer.parseInt(mapaCredito.getOptativos().trim()))==(sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre])) 
	            	estado = "¡Completo!"; 
	            else 
	            	estado = "¡Incompleto!"; 
	            
	            celda = new PdfPCell(new Phrase(
	            	"Creditos["+
	                	"Tot: "+(Integer.parseInt(mapaCredito.getCreditos().trim())+Integer.parseInt(mapaCredito.getOptativos().trim()))+"; "+
	                	"CO: "+mapaCredito.getCreditos()+"; CE: "+mapaCredito.getOptativos()+";] "+ 
	                "Faltan["+
	                	"CO: "+(int)(Integer.parseInt(mapaCredito.getCreditos().trim())-sumaCreditosAC[nSemestre]) +"; "+ 
	                	"CE: "+(Integer.parseInt(mapaCredito.getOptativos().trim())-creditosOptativosAC[nSemestre]) +"; "+
	                "] "+
	                "["+estado+"]", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new BaseColor(248,128,7))));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setColspan(13);
		celda.setBorder(Rectangle.NO_BORDER);
	            tabla.addCell(celda);
	    		document.add(tabla);
	    		
		//--------------------------------------------------------------------------- 
		
		
	
				contCurso 	= 0;
				ArrayList lisCAlumno = alumnoCursoUtil.getListAlumno(conEnoc,matricula," AND PLAN_ID='"+planid+"' AND TIPOCAL_ID IN ('1','I') AND CREDITOS =0 AND HT = 0 ORDER BY CURSO_ID, CICLO, NOMBRE_CURSO");
				
				tabla = new PdfPTable(11);
				tabla.setSpacingBefore((float)3);
	    	            tabla.setTotalWidth((float)541);
	    	            tabla.setLockedWidth(true);
	    	            //tabla.KEEPTOGETHER = true;
				
	            int headerWidthss[] = {20,8,8,8,8,8,8,8,8,8,8};
	            tabla.setWidths(headerWidthss);
	            boolean inscrito = false;
	            
	            if(lisCAlumno.size() != 0){
	            	
		            tabla.addCell(new Phrase("Componentes de:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
		            celda = new PdfPCell(new Phrase("I", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("II", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("III", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("IV", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("V", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("VI", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("VII", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("VIII", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("IX", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
		            celda = new PdfPCell(new Phrase("X", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0,0,0))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
					
					int ciclo = -1,contador = 1,secuencial = 0;
					String fechas[] = new String[11];
					for(int s = 0; s < 11; s++)
						fechas[s] = "";
					
					
					for(int j = 0; j < lisCAlumno.size(); j++){
						aca.vista.AlumnoCurso alumnoC = (aca.vista.AlumnoCurso) lisCAlumno.get(j);
						
						if(ciclo != Integer.parseInt(alumnoC.getCiclo())){
							if(ciclo != -1){
								if(inscrito)
									contador++;
								for(int l = 1; l < 12-contador;l++){
									celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
									celda.setHorizontalAlignment(Element.ALIGN_CENTER);
									tabla.addCell(celda);
								}
								
								inscrito = false;
								contador = 1;
								//------------------------------
								//celda = new PdfPCell();
								//celda.setHorizontalAlignment(Element.ALIGN_CENTER);
								//tabla.addCell(celda);
								//------------------------------
								celda = new PdfPCell(new Phrase("Fecha de acreditación", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
								celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
								tabla.addCell(celda);
								
									for(int k = 1; k < 11; k++){ 
										
										if(!fechas[k].equals("")){
											celda = new PdfPCell(new Phrase(fechas[k], FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0))));
											celda.setHorizontalAlignment(Element.ALIGN_CENTER);
											tabla.addCell(celda);
										}else{
											celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0))));
											celda.setHorizontalAlignment(Element.ALIGN_CENTER);
											tabla.addCell(celda);
										} 
										fechas[k] = "";
									} 
	
								}
							
							switch(secuencial){
								case 0:{tabla.addCell(new Phrase("Legado Cultural", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));}break;
								case 1:{tabla.addCell(new Phrase("Salud", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));}break;
								case 2:{tabla.addCell(new Phrase("Servicio comunitario", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));}break;
							case 3:{tabla.addCell(new Phrase("Trabajo manual", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));}break;
							}
							secuencial++;
							ciclo = Integer.parseInt(alumnoC.getCiclo());
						}
						
						aca.catalogo.CatTipoCal ctcc = new aca.catalogo.CatTipoCal(); 
						ctcc = TipoCalU.mapeaRegId(conEnoc, alumnoC.getTipoCalId());
						
						String tipoCalif = ctcc.getNombre().trim();
						if(tipoCalif.equals("AC")){
							
							celda = new PdfPCell(new Phrase(ctcc.getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
							celda.setHorizontalAlignment(Element.ALIGN_CENTER);
							tabla.addCell(celda); 
							fechas[contador] = alumnoC.getFEvaluacion(); 
							contador++;
						}else{
							if(tipoCalif.equals("NA") || tipoCalif.equals("BA") || tipoCalif.equals("RA") || tipoCalif.equals("CP") || tipoCalif.equals("CD") || tipoCalif.equals("Asignada")){
								celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
								celda.setHorizontalAlignment(Element.ALIGN_CENTER);
								tabla.addCell(celda); 
								fechas[contador] = "******"; 
								contador++;
							}else{
								if(!inscrito){
									contador--;
									inscrito = true;
								}
							}
							
						}
						
						
						if(contador < 11)
							if(fechas[contador] == null )
								fechas[contador] = "";
							
					} 
					
					if(inscrito)
						contador++;
					for(int l = 1; l < 12-contador;l++){
						celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
						celda.setHorizontalAlignment(Element.ALIGN_CENTER);
						tabla.addCell(celda);
					}
					
					tabla.addCell(new Phrase("Fecha de acreditación", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(0,0,0))));
						for(int k = 1; k < 11; k++){ 
							if(!fechas[k].equals("")){
								celda = new PdfPCell(new Phrase(fechas[k], FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0))));
								celda.setHorizontalAlignment(Element.ALIGN_CENTER);
								tabla.addCell(celda);
							}else {
								celda = new PdfPCell(new Phrase("******", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, new BaseColor(0,0,0))));
								celda.setHorizontalAlignment(Element.ALIGN_CENTER);
								tabla.addCell(celda);
							}  
							fechas[k] = "";
						} 
					
	            }
	            
					String programaAC = "";
					for (int x=1;x<20;x++) sumaNota[0]+=sumaNota[x];
					for (int x=1;x<20;x++) sumaCreditos[0]+=sumaCreditos[x];
					
					if(totalCreditos == totalAcreditados) 
						programaAC = "¡Completo!"; 
					else 
						programaAC = "¡Incompleto!";
					celda = new PdfPCell(new Phrase("Resumen del programa: "+totalCreditos+" Creditos[ Aprobados: "+totalAcreditados+"; No Aprobados: "+(totalCreditos-totalAcreditados)+"] Promedio ponderado "+aca.alumno.AlumUtil.getPromedioFinal(conEnoc,matricula,planid)+" ["+programaAC+"]", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new BaseColor(248,128,7))));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setColspan(11);
					celda.setBorder(Rectangle.NO_BORDER);
		            tabla.addCell(celda);
					
		            celda = new PdfPCell(new Phrase("_________________________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		            celda.setColspan(11);
		            celda.setPaddingTop((float)30);
		            celda.setBorder(Rectangle.NO_BORDER);
		            celda.setBackgroundColor(new BaseColor(255,255,255));
		            tabla.addCell(celda);
		            
		            celda = new PdfPCell(new Phrase(Parametros.getCardex(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		            celda.setColspan(11);
		            celda.setBorder(Rectangle.NO_BORDER);
		            celda.setBackgroundColor(new BaseColor(255,255,255));
		            tabla.addCell(celda);
		            
		            celda = new PdfPCell(new Phrase("Directora de Registro", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		            celda.setColspan(11);
		            celda.setBorder(Rectangle.NO_BORDER);
		            celda.setBackgroundColor(new BaseColor(255,255,255));
		            tabla.addCell(celda);
		            
		            document.add(tabla);
		            
	
	}catch(IOException ioe) {
		System.err.println("Error kardex en PDF: "+ioe.getMessage());
	}

	document.close();
%>
<head>
<meta http-equiv='REFRESH' content='0; url=kardex.pdf'>
</head>
<%}else{
%>
<table style="width:100%; margin:0 auto">
<tr><td align="center"><br><br><br><font size="3">
Tus estudios han sido cancelados por el siguiente motivo:<br><%cancelaEstudio.mapeaRegId(conEnoc, matricula, plan.getPlanId()); out.print(cancelaEstudio.getComentario()); %></font>
</td></tr>
</table>
<%
} %>
<%@ include file = "../../cierra_enoc.jsp"%>