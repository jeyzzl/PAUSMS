<%response.setHeader("Cache-Control","no-cache");response.setHeader("Cache-Control","no-store");response.setHeader("Cache-Control","must-revalidate");response.setHeader("Pragma","no-cache");response.setDateHeader ("Expires", 0);%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.nio.channels.FileChannel"%>

<%	
	String matricula = session.getAttribute("codigoAlumno").toString();
	String rutaTmp = application.getRealPath("residencia/docext/"+matricula)+"/tmp";

	File carpeta = new File(rutaTmp);
	if(!carpeta.exists()) carpeta.mkdirs();
	
	int numSig = Integer.parseInt(request.getParameter("NumSiguiente"));
	
	File[] listaF = new File[0];
	try{
		com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(request, rutaTmp, 100*1024*1024);
		
		listaF = carpeta.listFiles();
		
		for(File file : listaF){
			String numSiguiente = "";
			for(int i=String.valueOf(numSig).length();i<3;i++)numSiguiente+="0";
			numSiguiente+=numSig;
			FileChannel srcChannel = null;
			FileChannel dstChannel = null;
			try {
				srcChannel = new FileInputStream(file).getChannel ();
				dstChannel = new FileOutputStream(carpeta.getParent()+"//"+matricula+" "+numSiguiente+".jpg").getChannel();
				dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
				srcChannel.close();
				dstChannel.close();
			} catch (Exception e) {
				srcChannel.close();
				dstChannel.close();
				throw new Exception();
			}
			numSig++;
		}
	}catch(Exception e){
		System.out.println("Error uploading file: "+e);
	}
	finally{
		listaF = carpeta.listFiles();
		for(File f : listaF) f.delete();
		carpeta.delete();
		%><script>window.parent.location.href='documentosExternos';</script><%
	}
%>