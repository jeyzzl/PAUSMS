<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>

<jsp:useBean id="SeccionEmp" scope="page" class="aca.por.PorSeccionEmp"/>

<%		 
	String portafolioId		= (String) session.getAttribute("portafolioId");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String seccionId		= request.getParameter("SeccionId");
	
	String rutaOrigen		= application.getRealPath("/portales/porDocente/")+"/";
	
	SeccionEmp.setCodigoPersonal(codigoPersonal);
	SeccionEmp.setSeccionId(seccionId);
	String folio			= SeccionEmp.maximoReg(conEnoc, portafolioId, seccionId, codigoPersonal);
	
	// Crea la carpeta del empleado si no existe
	String rutaCarpeta		= application.getRealPath("/WEB-INF/porDocente")+"/"+codigoPersonal;
	File carpeta = new File(rutaCarpeta);		
	if (!carpeta.exists()){
		carpeta.mkdirs();
	}	
	String rutaDestino		= application.getRealPath("/WEB-INF/porDocente")+"/"+codigoPersonal+"/"+portafolioId+"-"+seccionId+"-"+folio+".jpg";
	String comentario 		= "X";
	
	boolean guardo = false;	
	try{
		
		com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(request, rutaOrigen, 5*1024*1024);
	    String archivo		= multi.getFilesystemName("archivo");
	    comentario 			= multi.getParameter("Comentario");
	    
	    // Leer el archivo en objeto File y FileInputStream
	    File fi 			= new File(rutaOrigen+archivo);
	    FileInputStream fis = new FileInputStream(fi);
	    
	    // crear un arreglo de bytes con la longitud del archivo 
		byte buf[] 			= new byte[(int)fi.length()];
	    
		// llenar el arreglo de bytes con los bytes del archivo
		fis.read(buf,0,(int)fi.length());
		
		// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream
		FileOutputStream fos = new FileOutputStream(rutaDestino);
		fos.write(buf,0,(int)fi.length());
		fos.flush();
		
		// Cerrar los objetos
		if (fos!=null) fos.close();
		if (fis!=null) fis.close();
		fi.delete();
		
		guardo = true;
		
	}catch(Exception e){
		System.out.println("Error al subir el archivo: "+e);
%>
		<font size="4"><b>Puede que el archivo que intenta subir mida mas de 5 MB. Intente con uno mas peque&ntilde;o</b> <a href="seccionAccion.jsp?SeccionId=<%=seccionId%>"><i class="icon-arrow-left icon-white"></i>Regresar</a></font>
<%
	}
	if (guardo){ 
		System.gc();
		SeccionEmp.setPorId(portafolioId);
		SeccionEmp.setCodigoPersonal(codigoPersonal);
		SeccionEmp.setSeccionId(seccionId);
		SeccionEmp.setTextoCorto(rutaDestino);
		SeccionEmp.setTextoLargo("-");
		SeccionEmp.setComentario(comentario);
		SeccionEmp.setFolio(folio);
		
		if (!SeccionEmp.existeReg(conEnoc)){
			if (SeccionEmp.insertReg(conEnoc)){
				response.sendRedirect("seccion.jsp");	
			}else{
%>
			<font size="4"><b>No guardó los datos</b> <a href="seccionAccion.jsp?SeccionId=<%=seccionId%>"><i class="icon-arrow-left icon-white"></i>Regresar</a></font>
<%			
			}
		}else{
			if (SeccionEmp.updateReg(conEnoc)){
				response.sendRedirect("seccion.jsp");
			}else{
%>
				<font size="4"><b>No modificó los datos</b> <a href="seccionAccion.jsp?SeccionId=<%=seccionId%>"><i class="icon-arrow-left icon-white"></i>Regresar</a></font>
<%			
			}			
		}
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>