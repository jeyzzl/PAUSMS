//Clase  para la tabla SSOC_PLAZA
package aca.ssoc;

import java.sql.*;
import java.util.ArrayList;

public class PlazaUtil {
	public ArrayList<Plaza> getListAll(Connection conn, String Institucion_Id, String orden ) throws SQLException{
		
		ArrayList<Plaza> lisPlaza 			= new ArrayList<Plaza>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PLAZA_ID, PLAZA_NOMBRE, INSTITUCION_ID FROM ENOC.SSOC_PLAZA WHERE INSTITUCION_ID = '"+Institucion_Id+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Plaza plaza = new Plaza();
				plaza.mapeaReg(rs);
				lisPlaza.add(plaza);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.PlazaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlaza;
	}
}