<%
	String matricula 	= (String) session.getAttribute("CodigoId");
	String foto 		= application.getRealPath("/WEB-INF/fotos/"+matricula+".jpg");
	
	if(session.getAttribute("CodigoId")!=null){
		byte[] buff;
		java.io.InputStream inputStream = request.getInputStream();
		java.io.OutputStream archivo = new java.io.FileOutputStream (foto);
		byte[] buf = new byte [1024] ;
		int len;
		while((len=inputStream.read(buf))>0) archivo.write(buf,0,len);
		archivo.close();
		inputStream.close();	
	    aca.util.CropToSelection cs = new aca.util.CropToSelection(140, 0, 360, 480, foto);
	    if(cs.getRecortado()){
	        System.out.println("Tome Foto:"+matricula+".jpg");
	    }
	}else{ 
		out.print("Sin privilegios...");
	}
%>