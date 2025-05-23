// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.HashMap;

public class CargaPronUtil{	
	
	public boolean existeReg(Connection conn, String cursoCargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_PRON WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPronUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public HashMap<String, String> mapaTodos(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando ="SELECT CURSO_CARGA_ID FROM ENOC.CARGA_PRON ";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("CURSO_CARGA_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPronUtil|mapaTodos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
}