<%@ page import="java.sql.*" %>

<jsp:useBean id="AlumFotoUtil"  class="aca.pg.archivo.AlumFotoUtil" scope="page"/>
<jsp:useBean id="alumFoto"  class="aca.pg.archivo.AlumFoto" scope="page"/>

<%
	Connection conEnoc 		= null;
	Connection conArchivo	= null;
	String matricula 		= (String) session.getAttribute("codigoEmpleado");
	String dependienteId 	= (String) session.getAttribute("dependienteId");
	String usuario  		= (String) session.getAttribute("codigoPersonal");
	String resolucion 		= (String) session.getAttribute("resolucion");	
	String foto 			= application.getRealPath("/WEB-INF/fotos/"+matricula+"-"+dependienteId+".jpg");

	java.io.InputStream inputStream = request.getInputStream();
	java.io.OutputStream archivo = new java.io.FileOutputStream (foto);
	byte[] fotoByte = new byte [1024] ;
	int len;
	while((len=inputStream.read(fotoByte))>0) archivo.write(fotoByte,0,len);
	archivo.close();	
	inputStream.close();
	
	try{
		// Obtener las conexiones del request 
		conEnoc 		= (java.sql.Connection)request.getAttribute("conEnoc");
		conArchivo 		= (java.sql.Connection)request.getAttribute("conArchivo");
		
    	// Leer de nuevo la imagen para subirla a la base de datos de postgres 
		java.io.FileInputStream instream = null;
		java.io.File f = new java.io.File(foto);    			
		fotoByte = null;
		if(f.exists()){
			fotoByte = new byte[(int)f.length()];
			instream = new java.io.FileInputStream(foto);
		}
		instream.read(fotoByte,0,(int)f.length());
		instream.close();
		
		// Grabar la foto en postgres
		alumFoto.setCodigoPersonal(matricula);
		alumFoto.setFecha(aca.util.Fecha.getHoy());
		alumFoto.setFoto(fotoByte);
		alumFoto.setTipo(dependienteId);
		alumFoto.setUsuario(usuario);
		if (AlumFotoUtil.existeReg(matricula, dependienteId)){
			AlumFotoUtil.updateReg(alumFoto);
		}else{
			AlumFotoUtil.insertRegByte(alumFoto);
		}
    }catch(Exception ex){
		System.out.println("Error - aca.util.RecortarImagen|getSubImagen|:"+ex);
		ex.printStackTrace();
	}finally{
		request.removeAttribute("conEnoc");
		request.removeAttribute("conArchivo");
		if (conEnoc!=null) 		conEnoc.close();
		if (conArchivo!=null) 	conArchivo.close();
	}
	
%>