<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal")==null?"-":(String)session.getAttribute("codigoPersonal");
	String seccionId 		= request.getParameter("SeccionId")==null?"0":request.getParameter("SeccionId");
	
	String dir				= application.getRealPath("/WEB-INF/porDocente/")+"/";
	
	String nombre 			= request.getParameter("archivoNombre")==null?"":request.getParameter("archivoNombre");
	
	if(session.getAttribute("codigoPersonal") != null){
		
		byte[] buff = null;
		java.io.File f = new java.io.File(dir+codigoPersonal+"/"+nombre);
		
		java.io.FileInputStream instream;
		if(f.exists()){
			buff = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(dir+codigoPersonal+"/"+nombre);
		}
		else{
			f = new java.io.File(dir+"nofoto.png");
			buff = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(dir+"nofoto.png");
		}	
		instream.read(buff,0,(int)f.length());
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=\""+nombre+ "\"");
		response.getOutputStream().write(buff);
		response.flushBuffer();
		instream.close();
	}else{
		out.print("Sin privilegios");
	}	
%>