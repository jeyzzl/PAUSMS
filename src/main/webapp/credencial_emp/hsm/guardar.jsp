<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%	
	String clave		= request.getParameter("CodigoEmpleado");
	String ruta 		= application.getRealPath("/credencial_emp/hsm/")+"/";
	String nombre 		= "";
	String dir			= application.getRealPath("/WEB-INF/fotos/"+clave+".jpg");
	
	boolean guardo = false;	
	try{	
		com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(request, ruta, 5*1024*1024);	        
	    nombre				= multi.getFilesystemName("archivo");
	    
	    // Leer el archivo en objeto File y FileInputStream
	    File fi 			= new File(ruta+nombre);
	    FileInputStream fis = new FileInputStream(fi);
	      
	    // crear un arreglo de bytes con la longitud del archivo 
		byte buf[] 			= new byte[(int)fi.length()];
		
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
	    
	}catch(Exception e){
		System.out.println("Error al subir el archivo: "+e);
%>
		<font size="4"><b>Puede que el archivo que intenta subir mida mas de 5 MB. Intente con uno mas peque&ntilde;o</b> <a href="subir.jsp">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></font>
<%
	}
	if (guardo){ 
		System.gc();
		out.print("<div class='alert alert-success'>Grabado...<a class='btn btn-primary' href='empleado.jsp?CodigoEmpleado="+clave+"'>Regresar</a></div>");
		//response.sendRedirect("empleado.jsp?CodigoEmpleado="+clave);
	}
%>