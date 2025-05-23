package aca.bec;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class BecPrecioUtil {
	
	public static HashMap<String, String> mapaPrecios(Connection conn) throws SQLException{
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT ID_EJERCICIO, PRECIO FROM ENOC.BEC_PRECIO";
			rs = st.executeQuery(comando);			
			while (rs.next()){							
				mapa.put(rs.getString("ID_EJERCICIO"), rs.getString("PRECIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPrecioUtil|mapaPrecios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
}