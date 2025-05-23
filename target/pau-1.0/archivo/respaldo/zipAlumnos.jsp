<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>

<%@ page import="java.io.*" %>
<%@ page import="java.util.zip.*" %>
<%@ page import="java.sql.*" %>

<%
	boolean error 		= false;

	String dir				= application.getRealPath("/archivo/respaldo")+"/";
	String fechaIni			= request.getParameter("fechaIni");
	String fechaFin			= request.getParameter("fechaFin");
	
	Connection  conn2 				= null;
	Statement   stmt2 				= null;
	ResultSet   rset2 				= null;
	String COMANDO 					= "";
	int cont						= 0;
	org.postgresql.largeobject.LargeObject obj			= null;
	org.postgresql.largeobject.LargeObjectManager lom	= null;
	
	/* --- CREAR CONEXION DIRECTA --- */
	try{
		System.out.println("INICIANDO CONEXION DIRECTA :O");
		
		Class.forName("org.postgresql.Driver");
		conn2 	= DriverManager.getConnection("jdbc:postgresql://172.16.251.11:5432/archivo","postgres","jete17");
		stmt2	= conn2.createStatement();
	   
	   System.out.println("CONECTADO :D");
	}catch (Exception ex){
		System.out.println("NO SE PUDO HACER LA CONEXION :(");
		ex.printStackTrace(); 
		error = true;
	}
	
	/* --- TRAER LARGE OBJECT --- */
	try{
		conn2.setAutoCommit(false);			 
		lom = ((org.postgresql.PGConnection)conn2).getLargeObjectAPI();
	}catch (Exception ex){
		ex.printStackTrace();
		error = true;
	}
	
	/* --- CONSULTAR Y TRAER DE LA BASE DE DATOS LAS IMAGENES --- */
	try{
		 System.out.println("contando documentos..??");
		 COMANDO = "select count(*) as total from PORTAL.ARCHIVOS_ALUMNO where fecha >= to_date('"+fechaIni+"', 'DD/MM/YYYY') and fecha <= to_date('"+fechaFin+"', 'DD/MM/YYYY')"; 
		 rset2 = stmt2.executeQuery(COMANDO);
		 if (rset2.next()){
		 	System.out.println("entro in cont(*)");
		 	System.out.println(rset2.getInt(1));
		 }
		 System.out.println("Obteniendo rset de documentos...");
		 COMANDO = "SELECT * FROM PORTAL.ARCHIVOS_ALUMNO"+
		 		" WHERE FECHA >= to_date('"+fechaIni+"', 'DD/MM/YYYY') and fecha <= to_date('"+fechaFin+"', 'DD/MM/YYYY')"+
		 		" AND NOMBRE IS NOT NULL AND ARCHIVO IS NOT NULL AND ARCHIVO != 0";
		 rset2 = stmt2.executeQuery(COMANDO);
	   
	}catch (Exception ex){ 
		ex.printStackTrace();
		error = true;
	}
	
	/* --- PONER EN UN ARCHIVO .ZIP LAS IMAGENES --- */
	try{ 
		System.out.println("Respaldando...");
		
		String zipFile = dir+"resArchAlum.zip";
		FileOutputStream fout	= new FileOutputStream(zipFile);
		ZipOutputStream zout	= new ZipOutputStream(new BufferedOutputStream(fout));
		
		while (rset2.next()){
			// Tipo de archivo
			String tipo = rset2.getString("nombre")==null?"-":rset2.getString("nombre");
			String arreglo[] = tipo.split("\\.");
			String ext = "-";
			if (arreglo.length > 0){
				ext = arreglo[arreglo.length-1];
			}
			System.out.println("Archivo:"+tipo+" Tipo de archivo:"+ext+":"+arreglo.length);
			
			String nombreArchivo 		= rset2.getString("archivo_id")+"|"+rset2.getString("folio")+"|"+rset2.getString("codigo_personal")+"."+ext;
			
			// Si existe un archivo lo agrega al zip
		    if (!tipo.equals("-")){
				long oid 		= rset2.getLong("archivo");	
				obj 			= lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);    			
				byte buf[] 		= new byte[obj.size()];
				buf 			= obj.read(obj.size());
				
				// ZIP IMAGES -------->	
				
			    zout.putNextEntry(new ZipEntry(nombreArchivo));
			    zout.write(buf,0,obj.size());
			    zout.closeEntry();
			    
				// END ZIP IMAGES -------->
		    }			
			cont++;
			obj.close();
		 }
		
		
		zout.close();
		fout.close();
		
		
		 System.out.println("OK... "+String.valueOf(cont) + " archivos respaldados...");	
	}catch (Exception ex){
		ex.printStackTrace();
		error = true;
	}
	
	finally{
		 conn2.close();	
	};
	
	
	if(error){
		out.print("<div class='error'>Error</div>");
		new File(dir+"resArchAlum.zip").delete();
	}
	
	conn2.setAutoCommit(true);
%>
