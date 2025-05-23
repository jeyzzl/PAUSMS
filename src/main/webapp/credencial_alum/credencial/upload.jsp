<%
	String matricula 	= (String)session.getAttribute("codigoAlumno")==null?"-":(String)session.getAttribute("codigoAlumno");
	String foto 		= application.getRealPath("/WEB-INF/fotos/"+matricula+".jpg");
	//System.out.println("Direccion:"+foto);
	byte[] buff;
	java.io.InputStream inputStream = request.getInputStream();
	java.io.OutputStream archivo = new java.io.FileOutputStream (foto);
	byte[] buf = new byte [1024] ;
	int len;
	while((len=inputStream.read(buf))>0) archivo.write(buf,0,len);
	archivo.close();
	inputStream.close();
	//System.out.println("Antes de Recortar ...");
	try{
		// Recorta la imagen sin utlizar JFrame
    	if (aca.util.RecortarImagen.getSubImagen(foto));
    		System.out.println("Tome Foto:"+matricula+".jpg");
    		
    	// Esta clase extiende de JFrame por lo que necesita un ambiente grafico	
/*  	aca.util.CropToSelection cs = new aca.util.CropToSelection(140, 0, 360, 480, foto);
    	if(cs.getRecortado()){
            System.out.println("Tome Foto:"+matricula+".jpg");
        }
*/	
    }catch(Exception ex){
		System.out.println("Error - aca.util.RecortarImagen|getSubImagen|:"+ex);
		ex.printStackTrace();
	}
%>