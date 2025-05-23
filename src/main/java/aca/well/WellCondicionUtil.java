//Clase  para la tabla Materias_Insc
package aca.well;

import java.sql.*;
import java.util.ArrayList;

public class WellCondicionUtil{
	
	public ArrayList<WellCondicion> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<WellCondicion> lisOne		= new ArrayList<WellCondicion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
		 	comando = "SELECT CODIGO_PERSONAL,FUERZA,AEROBICO,ACTIVIDAD,CUOTA,INTERES,PRESCRIPCION,"
		 			+ "OBSTACULO,UNIVERSIDAD,RECURSOS FROM ENOC.WELL_CONDICION "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				WellCondicion condicon = new WellCondicion();
				condicon.mapeaReg(rs);
				lisOne.add(condicon);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.wellness.WellCondicion|getlistAll|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisOne;
	}
	
	
	
	
	
}