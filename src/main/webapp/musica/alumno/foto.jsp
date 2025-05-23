<%
	String codigoId = (String) session.getAttribute("CodigoId");
	String dir		= application.getRealPath("/WEB-INF/fotos/")+"/";
	
	if(session.getAttribute("CodigoId")!=null){
		java.io.File f = new java.io.File(dir+codigoId+".jpg");
		byte[] buff;
		java.io.FileInputStream instream;
		if(f.exists()){			
			buff = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(dir+codigoId+".jpg");
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
		out.print("Sin privilegios...");
	}
%>