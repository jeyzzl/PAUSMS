package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmModCarreraUtil {
	
	public ArrayList<AdmModCarrera> getAll(Connection conn, String orden) throws SQLException{
		ArrayList<AdmModCarrera> listaModCarrera	= new ArrayList<AdmModCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM SALOMON.ADM_MODCARRERA "+ orden; 
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				AdmModCarrera admModCarrera = new AdmModCarrera();
				admModCarrera.mapeaReg(rs);
				listaModCarrera.add(admModCarrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmModCarrera|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listaModCarrera;
	}
}