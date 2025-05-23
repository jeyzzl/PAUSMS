<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" buffer="none" %>

<%@ include file="../../idioma.jsp"%>
<!-- inicio de estructura -->
<% 
	String 		matricula	= "";
	int 		documento	= 0;
	int 		hoja		= 0;
	String		fuente		= "";
	String 		origen		= "";
	String 		tipo		= "";
	String 		fInsert		= "";
	String 		fUpdate		= "";
	
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
	Connection conUnav = DriverManager.getConnection("jdbc:postgresql://172.16.254.21/archivo","postgres","unavunav");
	conUnav.setAutoCommit(false);
	Statement stmtUnav= conUnav.createStatement();
	
	out.println("Ejecutar Query..");
	COMANDO = "SELECT MATRICULA,IDDOCUMENTO,HOJA,IMAGEN,FUENTE,ORIGEN,TIPO,F_INSERT,F_UPDATE "+
			"FROM ENOC.ARCH_DOCALUM "+ 
			"WHERE IMAGEN IS NOT NULL "+
			//"AND MATRICULA >= '0970000' "+
			"ORDER BY 1";
//			
	rset = stmtAtlas.executeQuery(COMANDO);
	while (rset.next()){
		
		row++;
		matricula 	= rset.getString("MATRICULA");
		documento 	= rset.getInt("IDDOCUMENTO");
		hoja		= rset.getInt("HOJA");
		fuente		= rset.getString("FUENTE");
		origen		= rset.getString("ORIGEN");
		tipo		= rset.getString("TIPO");
		fInsert		= rset.getString("F_INSERT");
		fUpdate 	= rset.getString("F_UPDATE");
		
		// Leer el Obj
		out.println(row+":Leer Objeto:"+matricula+":"+documento+":"+hoja);
		
		long oid = rset.getLong("IMAGEN");
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
		COMANDO = "select hoja from arch_docalum where iddocumento = "+documento+" and matricula = '"+matricula+"' and hoja = "+hoja; 
		rsetU = stmtUnav.executeQuery(COMANDO);
		if (rsetU.next()){
  			COMANDO = "update arch_docalum set imagen = ? where iddocumento = "+documento+" and matricula = '"+matricula+"' and hoja = "+hoja; 
  			operacion = 1;
		}else{
			operacion = 2;
			COMANDO = "insert into arch_docalum(matricula,iddocumento,imagen,hoja,fuente,tipo,origen,f_insert,f_update)"+ 
					  "Values ('"+matricula+"',"+documento+",?,"+hoja+",'"+fuente+"','"+tipo+"','"+origen+"','"+fInsert+"','"+fUpdate+"')";
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
			conUnav = DriverManager.getConnection("jdbc:postgresql://172.16.254.21/archivo","postgres","jete17");
			conUnav.setAutoCommit(false);
			stmtUnav= conUnav.createStatement();
		}
	}
	conUnav.commit();
	if (stmtAtlas!=null) stmtAtlas.close();
	if (rset!=null) rset.close();
	if (stmtAtlas!=null) stmtAtlas.close();
	if (rset!=null) rset.close();
	if (pstmt!=null) pstmt.close();
	
	conAtlas.setAutoCommit(true);
	conUnav.setAutoCommit(true);
	
	out.println("cierra ..");
	conAtlas.close();
	conUnav.close();
	out.println("Termina el Proceso");
%>