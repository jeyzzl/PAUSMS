<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal")==null?"-":(String)session.getAttribute("codigoPersonal");
	String codigoEmpleado	= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
	String dir				= application.getRealPath("/WEB-INF/fotos/")+"/";
	String mat = request.getParameter("mat");
	if (codigoEmpleado.substring(0,1).equals("9")||codigoEmpleado.substring(0,1).equals("0")||codigoEmpleado.substring(0,1).equals("1")||codigoEmpleado.substring(0,1).equals("2")){
		if(mat!=null){
			java.io.File f = new java.io.File(dir+mat+".jpg");
			byte[] buff;
			java.io.FileInputStream instream;
			if(f.exists()){
				buff = new byte[(int)f.length()];
				instream = new java.io.FileInputStream(dir+mat+".jpg");
			}
			else{
				f = new java.io.File(dir+"nofoto.png");
				buff = new byte[(int)f.length()];
				instream = new java.io.FileInputStream(dir+"nofoto.png");
			}
			instream.read(buff,0,(int)f.length());
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition","attachment; filename=\""+ mat+".jpg"+ "\"");	
			response.getOutputStream().write(buff);
			response.flushBuffer();
			instream.close();
		}else{
			out.print("Sin privilegios");
		}
	}
%>