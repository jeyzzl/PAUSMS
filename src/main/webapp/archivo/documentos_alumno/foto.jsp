<%@ page import="java.sql.*" %>
<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal")==null?"-":(String)session.getAttribute("codigoPersonal");
	String matricula 		= request.getParameter("matricula");
	String s_iddocumento 	= request.getParameter("documento");
	String s_nhoja 			= request.getParameter("hoja");
	
	//Si tiene una sesión activa
	if (codigoPersonal.substring(0,1).equals("9")||codigoPersonal.substring(0,1).equals("0")||codigoPersonal.substring(0,1).equals("1")||codigoPersonal.substring(0,1).equals("2")){		
		
		//coneccion a ATLAS
		DriverManager.registerDriver (new org.postgresql.Driver());
		Connection conn=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
		Statement stmt= conn.createStatement();
		ResultSet rset= null;
		conn.setAutoCommit(false);
		String COMANDO = "SELECT "+
				"IMAGEN, FUENTE, ORIGEN, USUARIO, "+
				"TO_CHAR(F_INSERT,'DD/MM/YYYY') AS F_I, "+
				"TO_CHAR(F_UPDATE,'DD/MM/YYYY') AS F_U "+
				"FROM ARCH_DOCALUM "+ 
				"WHERE MATRICULA = '"+matricula+"' "+
				"AND IDDOCUMENTO = '"+s_iddocumento+"' "+
				"AND HOJA = "+s_nhoja;
		
		rset = stmt.executeQuery(COMANDO);
		if (rset.next()){
			
			org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
			org.postgresql.largeobject.LargeObject obj;
			long oid = rset.getLong("IMAGEN");
				obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ); 
		    byte buf[] = new byte[obj.size()];
		    //obj.read(buf, 0, obj.size());
		    buf = obj.read(obj.size());
		    response.setContentType("image/jpeg");
			response.getOutputStream().write(buf);
			response.flushBuffer();
			obj.close();			
		}
		conn.setAutoCommit(true);
		try { conn.close(); } catch (Exception ignore) { }		
		try { stmt.close(); } catch (Exception ignore) { }
		try { rset.close(); } catch (Exception ignore) { }		
	}	
%>