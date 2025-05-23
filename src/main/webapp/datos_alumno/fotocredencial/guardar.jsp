<%@ page import="java.sql.*" %>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>

<jsp:useBean id="AlumFotoUtil"  class="aca.pg.archivo.AlumFotoUtil" scope="page"/>
<jsp:useBean id="alumFoto"  class="aca.pg.archivo.AlumFoto" scope="page"/>
<%	
	Connection conEnoc 		= null;
	Connection conArchivo	= null;	
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	String usuario  		= (String) session.getAttribute("codigoPersonal");
	String ruta 			= application.getRealPath("/datos_alumno/fotocredencial/")+"/";
	String nombre 			= "";
	String dir				= application.getRealPath("/WEB-INF/fotos/"+codigoAlumno+".jpg");
	
	boolean guardo = false;	
	try{
		System.out.println("Antes de conexiones");
		// Obtener las conexiones del request 
		conEnoc 		= (java.sql.Connection)request.getAttribute("conEnoc");
		conArchivo 		= (java.sql.Connection)request.getAttribute("conArchivo");
		System.out.println("Despues de conexiones");
		com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(request, ruta, 5*1024*1024);	        
	    nombre				= multi.getFilesystemName("archivo");
	    System.out.println("Despues de archivo");
	    
	    // Leer el archivo en objeto File y FileInputStream
	    File fi 			= new File(ruta+nombre);
	    FileInputStream fis = new FileInputStream(fi);
	      
	    // crear un arreglo de bytes con la longitud del archivo 
		byte fotoByte[] 			= new byte[(int)fi.length()];
		System.out.println("Antes de llenar byte");
		// llenar el arreglo de bytes con los bytes del archivo
		fis.read(fotoByte,0,(int)fi.length());
		
		// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream 
		FileOutputStream fos = new FileOutputStream(dir);
		fos.write(fotoByte,0,(int)fi.length());
		//fos.flush();		
		System.out.println("Antes de foto en bd");
		// Grabar la foto en postgres
		alumFoto.setCodigoPersonal(codigoAlumno);
		alumFoto.setFecha(aca.util.Fecha.getHoy());
		alumFoto.setFoto(fotoByte);
		alumFoto.setTipo("O");
		alumFoto.setUsuario(usuario);
		if (AlumFotoUtil.existeReg(codigoAlumno, "O")){
			AlumFotoUtil.updateReg(alumFoto);
		}else{
			AlumFotoUtil.insertRegByte(alumFoto);
		}
		
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
	}finally{
		if (conEnoc!=null) 		conEnoc.close();
		if (conArchivo!=null) 	conArchivo.close();
	}
	if (guardo){ 
		System.gc();
		out.print("<div class='alert alert-success'><b>Grabado...<a class='btn btn-primary' href='datos.jsp'>Regresar</a></div>");
		//response.sendRedirect("datos.jsp");
	}
%>