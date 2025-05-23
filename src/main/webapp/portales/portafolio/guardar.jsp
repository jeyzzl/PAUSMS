<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%> 
<%	
	String codigoEmpleado   = (String) session.getAttribute("codigoPersonal");
	String periodoId		= (String) session.getAttribute("porPeriodo");
	String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
	String hoja				= request.getParameter("Hoja")==null?"1":request.getParameter("Hoja");
	String origen 			= request.getParameter("origen")!=null ? request.getParameter("origen") : "";
	System.out.println("origen " +request.getParameter("origen")!=null);
	String paramOrigen = "";
	if(!origen.equals("")){
		paramOrigen = "&origen="+origen;
	}
	
	String nombre 			= "";
	String ruta 			= application.getRealPath("/WEB-INF/portafolio")+"/";
	// Busca el prefijo del nombre del documento
	String prefijo			= aca.portafolio.PorDocumento.getArchivo(conEnoc, documentoId);
	String nomArchivo		= codigoEmpleado+periodoId+prefijo+hoja;
	String dir 				= "";
	System.out.println("es  " + nomArchivo + "\t" + application.getRealPath("/"));
	
	com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(request, ruta, 5*1024*1024);
	//System.out.println("es 0 " + multi.getFilesystemName("archivo"));
	if((hoja.equals("1"))||(hoja.equals("2"))||(hoja.equals("3"))){
		dir				= application.getRealPath("/WEB-INF/portafolio/"+nomArchivo+".jpg");	
	}else if (hoja.equals("0")){
		//System.out.println("es 0");
		nombre				= multi.getFilesystemName("archivo");
		String [] ext		= nombre.split("\\.");
		String extension	= ext[1];		
		dir					= application.getRealPath("/WEB-INF/portafolio/"+nomArchivo+"."+extension);
	}
	
	
	
	boolean guardo = false;	
	try{	
		        
	    nombre				= multi.getFilesystemName("archivo");
	    System.out.println(nombre);
	    
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
<font size="4"><b>Puede que el archivo que intenta subir mida
		mas de 5 MB. Intente con uno mas peque&ntilde;o</b> <a
	href="subir?DocumentoId=<%=documentoId%>&Hoja=<%=hoja%>"><i
		class="icon-arrow-left icon-white"></i>
	<spring:message code='aca.Regresar' /></a></font>
<%
	}
	if (guardo){		 
		aca.portafolio.PorEmpDocto empDoc = new aca.portafolio.PorEmpDocto();
		String hojas = aca.portafolio.PorEmpDocto.getHojas(conEnoc, codigoEmpleado, periodoId, documentoId);
		// Si no esta registrada la hoja
		if (!hojas.contains("-"+hoja)){
			hojas = hojas +"-"+hoja;
			if (empDoc.updateHojas(conEnoc, codigoEmpleado, periodoId, documentoId, hojas)){
			}
		}
		System.gc();
		response.sendRedirect("subir?DocumentoId="+documentoId+"&Hoja="+hoja + paramOrigen);
	}
%>
<%@ include file="../../cierra_enoc.jsp"%>