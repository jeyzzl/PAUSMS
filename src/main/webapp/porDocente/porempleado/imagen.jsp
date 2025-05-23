<%
	String codigoPersonal	= request.getParameter("codigoPersonal")==null?"-":request.getParameter("codigoPersonal");
	String seccionId 		= request.getParameter("SeccionId")==null?"0":request.getParameter("SeccionId");
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String porId			= request.getParameter("porId");
	String dir				= application.getRealPath("/WEB-INF/porDocente/")+"/";
	
	if(codigoPersonal!= null){
		
		byte[] buff = null;
		java.io.File f = new java.io.File(dir+codigoPersonal+"/"+porId+"-"+seccionId+"-"+folio+".jpg");
		
		java.io.FileInputStream instream;
		if(f.exists()){			
			buff = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(dir+codigoPersonal+"/"+porId+"-"+seccionId+"-"+folio+".jpg");
		}
		else{
			f = new java.io.File(dir+"nofoto.png");
			buff = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(dir+"nofoto.png");
		}	
		instream.read(buff,0,(int)f.length());
		
		response.setContentType("image/jpeg");
		response.getOutputStream().write(buff);
		response.flushBuffer();
		instream.close();
	}else{
		out.print("Sin privilegios");
	}	
%>