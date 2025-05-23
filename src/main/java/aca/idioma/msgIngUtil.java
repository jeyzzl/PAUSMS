package aca.idioma;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class msgIngUtil {

	public HashMap<String, String> getMapAll(Connection conn) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String, String>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = " SELECT CLAVE, VALOR FROM ENOC.MSG_EN";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CLAVE"), rs.getString("VALOR"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.msgIng|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
}
