<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%	
	String proyectoId		= request.getParameter("ProyectoId");
	String ruta 			= application.getRealPath("/investigacion/proyecto/")+"/";
	String nombre 			= "";
	String dir				= application.getRealPath("/investigacion/proyecto/archivos/"+proyectoId+"b.pdf");
	int widthImage     		= 0;
	int heightImage			= 0;
	
	boolean guardo = false;	
	try{		
		com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(request, ruta, 10*1024*1024);
	    nombre			= multi.getFilesystemName("archivo");
	    
	 	// Leer el archivo en objeto File y FileInputStream
	    File fi = new File(ruta+nombre);
	 	
	    if (nombre.toLowerCase().contains(".pdf")){
	    	
		    FileInputStream fis = new FileInputStream(fi);
		    
		    // crear un arreglo de bytes con la longitud del archivo 
			byte buf[] = new byte[(int)fi.length()];
			
			// llenar el arreglo de bytes con los bytes del archivo
			fis.read(buf,0,(int)fi.length());
			
			// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream 
			FileOutputStream fos = new FileOutputStream(dir);
			fos.write(buf,0,(int)fi.length());		
			fos.flush();	
			
			// Cerrar los objetos
			if (fos!=null) fos.close();
			if (fis!=null) fis.close();
			fi.delete();
			
			guardo = true;
	    }else{
	    	out.print("<div class='alert alert-danger'>El archivo debe estar en formato pdf...</div>");
	    	fi.delete();
	    }
	    	
	    
	}catch(IllegalArgumentException e){
		//System.out.println("Error de dimensiones");
%>
		<div class="container-fluid">
			<div class="alert alert-danger">
				<strong>Error al subir el documento</strong>
				<a href="subirInfo2">Intentar de nuevo</a>
			</div>
		</div>
<%		
	}
	
	if (guardo ){
		System.gc();
		if (aca.investiga.InvProyectoUtil.updateDocumento(conEnoc, proyectoId, "S")){
			response.sendRedirect("proyecto");
		}else{
			out.print("<div class='alert alert-danger'>Error al cambiar el estado del documento...</div>");
		}
	}	
%>
<%@ include file="../../cierra_enoc.jsp" %>