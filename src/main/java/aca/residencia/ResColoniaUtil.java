package aca.residencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ResColoniaUtil {

public ArrayList<ResColonia> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<ResColonia> lisColonia = new ArrayList<ResColonia>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT COLONIA_ID, COLONIA_NOMBRE FROM ENOC.RES_COLONIA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResColonia colonia = new ResColonia();
				colonia.mapeaReg(rs);
				lisColonia.add(colonia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ColoniaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisColonia;
	}
}