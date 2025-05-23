<%
	String ruta		= request.getParameter("ruta")==null?"":request.getParameter("ruta");
	String nombre	= request.getParameter("nombre")==null?"vacio.txt":request.getParameter("nombre");
	
	if(request.getParameter("ruta") != null){
		java.io.File f = new java.io.File(ruta);		
		byte[] archivo = null;
		java.io.FileInputStream instream = null;		
		if(f.exists()){
			archivo = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(ruta);
		}
		instream.read(archivo,0,(int)f.length());
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=\""+nombre+ "\"");
		response.getOutputStream().write(archivo);
		response.flushBuffer();
		instream.close();
	}
%>