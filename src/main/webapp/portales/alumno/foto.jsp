
<%@ page import="java.sql.*" %>
<%	
	Connection conn = null;	
	Statement stmt	= null;	
	ResultSet rset	= null;
	try{
		//coneccion a ATLAS
		Class.forName("org.postgresql.Driver");
		conn 	= DriverManager.getConnection(aca.conecta.Conectar.coneccion(), aca.conecta.Conectar.usuario(), aca.conecta.Conectar.password());	
		stmt	= conn.createStatement();	
		rset	= null;
		
		String codigoPersonal	= (String)session.getAttribute("codigoPersonal")==null?"-":(String)session.getAttribute("codigoPersonal");
		String matricula 		= request.getParameter("matricula");
		String s_iddocumento	= request.getParameter("documento");
		String s_nhoja 			= request.getParameter("hoja");
		
		//Si tiene una sesión activa
		if (codigoPersonal.substring(0,1).equals("9")||codigoPersonal.substring(0,1).equals("0")||codigoPersonal.substring(0,1).equals("1")||codigoPersonal.substring(0,1).equals("2")){
			conn.setAutoCommit(false);
			String COMANDO 	= 	"SELECT IMAGEN,FUENTE,ORIGEN FROM ARCH_DOCALUM WHERE MATRICULA = '"+matricula+"' AND IDDOCUMENTO = '"+s_iddocumento+"' AND HOJA = "+s_nhoja;
			rset 			= stmt.executeQuery(COMANDO);
			if (rset.next()){						
			    org.postgresql.largeobject.LargeObject obj;
				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();	
				long oid = rset.getInt("imagen");
					obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
			    byte buf[] = new byte[obj.size()];
			    obj.read(buf, 0, obj.size());
			    response.setContentType("image/jpeg");
				response.getOutputStream().write(buf);
				response.flushBuffer();
				obj.close();
			}
			conn.setAutoCommit(true);
		}
	}catch(Exception ex){
		System.out.println("Error:"+ex);
	}finally{
		try { conn.close(); } catch (Exception ignore) { }
		try { stmt.close(); } catch (Exception ignore) { }
		try { rset.close(); } catch (Exception ignore) { }
	}
%>