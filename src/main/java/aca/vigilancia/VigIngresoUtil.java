package aca.vigilancia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VigIngresoUtil {
	
	public ArrayList<VigIngreso> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<VigIngreso> lis		= new ArrayList<VigIngreso>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, TO_CHAR(FECHA, 'DD/MM/YYY HH:MI:SS AM') AS FECHA, CODIGO_PERSONAL, RESIDENCIA_ID, DORMITORIO, TIPO " +
 				" FROM ENOC.VIG_INGRESO " +orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				VigIngreso vig = new VigIngreso();
				vig.mapeaReg(rs);
				lis.add(vig);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigIngresoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lis;
	}
}