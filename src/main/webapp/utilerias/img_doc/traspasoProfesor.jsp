<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" buffer="none" %>

<%@ include file="../../idioma.jsp"%>
<!-- inicio de estructura -->
<% 
	String 		archivoId		= "";
	int 		folio			= 0;
	String 		matricula		= "";
	String		fecha			= "";
	String 		nombre			= "";
	String 		comentario		= "";
	int 		tamano			= 0;
	String 		autorizacion	= "";
	
	int			operacion	= 0;
	int 		row			= 0;
	String NombreArch		= application.getRealPath("archivo/documentos_alumno")+"/";
	out.println("Path:"+NombreArch);
	
	String 		COMANDO		="";
	ResultSet   rset 		= null;
	ResultSet   rsetU		= null;
	PreparedStatement pstmt = null;
	
	out.println("conectando ATLAS..");
	// CONECTAR POSTGRES EN ATLAS
	DriverManager.registerDriver (new org.postgresql.Driver());
	Connection conAtlas = DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	conAtlas.setAutoCommit(false);
	Statement stmtAtlas= conAtlas.createStatement();
	
	out.println("conectando UNAV..");
	//CONECTAR POSTGRES EN UNAV
	DriverManager.registerDriver (new org.postgresql.Driver());
	Connection conUnav = DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	conUnav.setAutoCommit(false);
	Statement stmtUnav= conUnav.createStatement();
	
	out.println("Ejecutar Query..");
	COMANDO = "SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA, NOMBRE, COMENTARIO, TAMANO, AUTORIZACION, ARCHIVO "+
			"FROM PORTAL.ARCHIVOS_PROFESOR "+ 
			"WHERE ARCHIVO IS NOT NULL "+
			"ORDER BY 1";
//			
	rset = stmtAtlas.executeQuery(COMANDO);
	while (rset.next()){
		
		row++;
		archivoId 		= rset.getString("ARCHIVO_ID");
		folio	 		= rset.getInt("FOLIO");
		matricula		= rset.getString("CODIGO_PERSONAL");
		fecha			= rset.getString("FECHA");
		nombre			= rset.getString("NOMBRE");
		comentario		= rset.getString("COMENTARIO");
		tamano			= rset.getInt("TAMANO");
		autorizacion	= rset.getString("AUTORIZACION");
		
		// Leer el Obj
		out.println(row+":Leer Objeto:"+matricula+":"+nombre+":"+comentario);
		
		long oid = rset.getLong("ARCHIVO");
		org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conAtlas).getLargeObjectAPI();		
		org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);		
		byte buf[] = new byte[obj.size()];
		obj.read(buf,0,obj.size());
		
		out.println("Tamaño:"+obj.size()+" ");

/*
		FileOutputStream fileOutStream = new FileOutputStream(NombreArch+"prueba.jpg");
		fileOutStream.write(buf,0,obj.size());
		fileOutStream.flush();
		fileOutStream.close();
*/		
		
		//byte buf[] = obj.read(obj.size());
		
		org.postgresql.largeobject.LargeObjectManager lom2 = ((org.postgresql.PGConnection)conUnav).getLargeObjectAPI();
		long oid2 = lom2.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
		org.postgresql.largeobject.LargeObject obj2 = lom2.open(oid2, org.postgresql.largeobject.LargeObjectManager.WRITE);
		obj2.write(buf);
		
/*	
		File file = new File(NombreArch+"prueba.jpg");
		if (file.exists()){
			FileInputStream fis = new FileInputStream(file);
			byte buf2[] = new byte[(int)file.length()];
			int le; while ((le=fis.read(buf2)) !=-1){obj2.write(buf2,0,le);}
		}		
*/		
		out.println("Tamaño:"+obj2.size()+" ");

		// Pasar el obj a UNAV
		COMANDO = "select archivo_id from portal.archivos_profesor where archivo_id = '"+archivoId+"' and folio = "+folio+" and codigo_personal = '"+matricula+"' "; 
		rsetU = stmtUnav.executeQuery(COMANDO);
		if (rsetU.next()){
  			COMANDO = "update portal.archivos_profesor set archivo = ? where archivo_id = '"+archivoId+"' and folio = "+folio+" and codigo_personal = '"+matricula+"' "; 
  			operacion = 1;
		}else{
			operacion = 2;
			COMANDO = "insert into portal.archivos_profesor(archivo_id,folio,codigo_personal,fecha,nombre,comentario,tamano,autorizacion,archivo)"+ 
					  "Values ('"+archivoId+"',"+folio+",'"+matricula+"','"+fecha+"','"+nombre+"','"+comentario+"',"+tamano+",'"+autorizacion+"',?)";
		}
		
		pstmt = conUnav.prepareStatement(COMANDO);
		pstmt.setLong(1,oid2);
		pstmt.execute();
		conUnav.commit();
%>
<br>
<%		
		if ((row % 1000) == 0 && row!=0 ){
			conUnav.commit();
			conUnav.close();
			out.println("conectando UNAV..");
			//CONECTAR POSTGRES EN UNAV
			DriverManager.registerDriver (new org.postgresql.Driver());
			conUnav = DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
			conUnav.setAutoCommit(false);
			stmtUnav= conUnav.createStatement();
		}
	}
	conUnav.commit();
	conAtlas.setAutoCommit(true);
	conUnav.setAutoCommit(true);
	
	if (stmtAtlas!=null) stmtAtlas.close();
	if (rset!=null) rset.close();
	if (stmtAtlas!=null) stmtAtlas.close();
	if (rset!=null) rset.close();
	if (pstmt!=null) pstmt.close();

	out.println("cierra ..");
	conAtlas.close();
	conUnav.close();
	out.println("Termina el Proceso");
%>