// Clase Util para la tabla de Cat_Division
package aca.sep;

import java.sql.*;
import java.util.ArrayList;
//import java.util.HashMap;

public class SepLugarUtil{
		
	public ArrayList<SepLugar> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<SepLugar> lista		= new ArrayList<SepLugar>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT LUGAR_ID, LUGAR_NOMBRE, ORDEN FROM ENOC.SEP_LUGAR "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				SepLugar pais = new SepLugar();
				pais.mapeaReg(rs);
				lista.add(pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugarUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
		
}