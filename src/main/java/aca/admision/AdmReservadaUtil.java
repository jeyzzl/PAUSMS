package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdmReservadaUtil {	
	
	public boolean existeReservacion(Connection conn, String folio, String estados) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok 			= false;
		
		try{
			comando = "SELECT COUNT(*) FROM SALOMON.ADM_RESERVADA WHERE FOLIO = TO_NUMBER('"+folio+"','9999999') AND ESTADO IN ("+estados+")"; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmProcesoUtil|existeReservacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}