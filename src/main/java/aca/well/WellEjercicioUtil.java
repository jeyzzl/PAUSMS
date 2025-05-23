//Clase  para la tabla Materias_Insc
package aca.well;

import java.sql.*;
import java.util.ArrayList;

public class WellEjercicioUtil{
	
	public ArrayList<WellCondicion> getListOne(Connection conn, String orden ) throws SQLException{
			
		ArrayList<WellCondicion> lisOne		= new ArrayList<WellCondicion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DOMINGO, LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, CODIGO_PERSONAL"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				WellCondicion obj = new WellCondicion();
				obj.mapeaReg(rs);
				lisOne.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.wellness.WellCondicion|getlistAll|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisOne;
	}
	
	
	
	
	
}