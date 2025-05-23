<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal")==null?"-":(String)session.getAttribute("codigoPersonal");
	String dir				=application.getRealPath("/WEB-INF/fotos/")+"/";
	
	if (codigoPersonal.substring(0,1).equals("9")||codigoPersonal.substring(0,1).equals("0")||codigoPersonal.substring(0,1).equals("1")||codigoPersonal.substring(0,1).equals("2")){	
		if(session.getAttribute("mat")!=null){
			java.io.File f = new java.io.File(dir+session.getAttribute("mat")+".jpg");
			byte[] buff;
			java.io.FileInputStream instream;
			if(f.exists()){
				buff = new byte[(int)f.length()];
				instream = new java.io.FileInputStream(dir+session.getAttribute("mat")+".jpg");
			}
			else{
				f = new java.io.File(dir+"nofoto.png");
				buff = new byte[(int)f.length()];
				instream = new java.io.FileInputStream(dir+"nofoto.png");
			}
			instream.read(buff,0,(int)f.length());
			instream.close();
			response.setContentType("image/jpeg");
			response.getOutputStream().write(buff);
		}else{
			out.print("No access privileges");
		}
	}
%>