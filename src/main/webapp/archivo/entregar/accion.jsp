<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>

<%@page import="java.io.File"%>

<jsp:useBean id="ArchEntrega" scope="page" class="aca.archivo.ArchEntrega"/>
<jsp:useBean id="ArchEntregaU" scope="page" class="aca.archivo.ArchEntregaUtil"/>

<%
	String codigoAlumno	= (String)session.getAttribute("codigoAlumno");
	String folio   		= request.getParameter("Folio");
	String opcion		= request.getParameter("Opc");
	String imagen		= request.getParameter("Imagen");
	
	byte[] firma 		= (byte[]) session.getAttribute("firma");
	
	byte[]  bTmp 			= new byte[0];
	String mensaje 			= "x";
	String codigo  			= "";
	String falloImagen		= "";
	String exitoImagen		= "";
	

	System.out.println("Entré - "+folio+":"+opcion+":"+imagen);
	
	//APACHE FILE UPLOAD ------------------------------------------>	
	
	DiskFileItemFactory factory 	= new DiskFileItemFactory();
	ServletFileUpload upload 		= new ServletFileUpload(factory);
	
	//Parse the request
	java.util.List<FileItem> items 	= upload.parseRequest(request);
	
	// Process the uploaded items
	java.util.Iterator<FileItem> iter = items.iterator();		
	
	String filename = "";
	File path = null;
	
	while (iter.hasNext()){
		FileItem item = (FileItem) iter.next();
	
		if (!item.isFormField()) {			
	        filename = item.getName();            
	        
	        if(filename.equals("")){
	        	mensaje = "Selecciona una imagen";
	        }else{
	        
	        	try {
	        		String[] codigoArray = filename.split("-");
		            codigo = codigoArray[0];
		            String[] folioArray = codigo.split("\\."); 
		            String extension = folioArray[1];    	   
		            
		            if(extension.equals("jpg")){
			            java.io.InputStream filecontent = item.getInputStream();
			            byte[] b = new byte[filecontent.available()];
			            filecontent.read(b);
			            bTmp = b;		        
			            
			            exitoImagen += codigo+"-"+folio+" ";
			            
			          	//GUARDAR CADA VEZ QUE LLEGA AL TEXTAREA (DESCRIPCION)
				    	if(bTmp.length>0){//Si el input tiene algun archivo/imagen entonces guardarlo    
				    		ArchEntrega = ArchEntregaU.getEntrega(conEnoc, codigoAlumno, folio);
				    		if(imagen.equals("Identificacion")){
				    			ArchEntrega.setIdentificacion(bTmp);
				    		}else if(imagen.equals("Poder")){
				    			ArchEntrega.setPoder(bTmp);
				    		}else if(imagen.equals("Envio")){
				    			ArchEntrega.setEnvio(bTmp);
				    		}else if(imagen.equals("Firma")){
				    			ArchEntrega.setFirma(bTmp);
				    		}else if(imagen.equals("Extra")){
				    			ArchEntrega.setExtra(bTmp);
				    		}
				    		
							if(!ArchEntregaU.updateReg(conEnoc, ArchEntrega)){ 
								falloImagen += codigo+"-"+folio+" ";
								mensaje = "Error";
			    	    	}else{
			    	    		mensaje = "1";
			    	    	}
						}
		            }else{    	            	    
		            	falloImagen += codigo+"-"+folio+" ";
		            	mensaje = "Error";
		            }
	        	} catch (ArrayIndexOutOfBoundsException e) {            		
	        		falloImagen += codigo+" ";
	        		mensaje = "Error";
	        	} 
	        }
		}
	}
%>
	<meta http-equiv="refresh" content="0; url=subir?Mensaje=<%=mensaje%>&Folio=<%=folio%>&Opc=<%=opcion%>&Imagen=<%=imagen%>"/>

<%@ include file= "../../cierra_enoc.jsp" %>