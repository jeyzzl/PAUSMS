<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" buffer="none" %>

<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<% 
	String 		matricula	= "";
	int 		documento	= 0;
	int 		hoja		= 0;
	int 		row			= 0;
		
	String 		COMANDO		="";
	ResultSet   rset 		= null;
	ResultSet   rset2		= null;
	PreparedStatement pstmt = null;
	
	out.println("conectando ATLAS..");
	// CONECTAR POSTGRES EN ATLAS
	DriverManager.registerDriver (new org.postgresql.Driver());
	Connection conAtlas = DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	conAtlas.setAutoCommit(false);
	Statement stmtAtlas= conAtlas.createStatement();

	out.println("Ejecutar Query..");
	COMANDO = "SELECT MATRICULA,IDDOCUMENTO,HOJA,IMAGEN "+
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
				
		// Leer el Obj
		//out.println(row+":Leer Objeto:"+matricula+":"+documento+":"+hoja);
				
		long oid = rset.getLong("IMAGEN");
		org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conAtlas).getLargeObjectAPI();	
		org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
		byte buf[] = new byte[obj.size()];
		obj.read(buf,0,obj.size());	
		if (obj.size()<1000){
			out.println("Vacio: "+row+":"+matricula+" : "+documento+" : "+hoja);
			out.println("Tamaño:"+obj.size()+" ");
			/*
			COMANDO = "UPDATE ENOC.ARCH_DOCALUM SET IMAGEN = NULL WHERE MATRICULA = ? AND IDDOCUMENTO = ? AND HOJA = ?"; 
			pstmt = conAtlas.prepareStatement(COMANDO);
			pstmt.setString(1,matricula);
			pstmt.setInt(2,documento);
			pstmt.setInt(3,hoja);
			pstmt.execute();
			conAtlas.commit();
			*/
		}else{
			out.println("");
		}
%>
<%
	}
	conAtlas.setAutoCommit(true);
	if (stmtAtlas!=null) stmtAtlas.close();
	if (rset!=null) rset.close();
	if (pstmt!=null) pstmt.close();

	out.println("cierra ..");
	conAtlas.close();
	out.println("Termina el Proceso");
%>