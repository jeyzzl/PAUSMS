package adm.alumno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmSaludUtil {
	
	public ArrayList<AdmSalud> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmSalud> list		= new ArrayList<AdmSalud>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO" +
			" FROM SALOMON.ADM_SALUD "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmSalud obj = new AdmSalud();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSaludUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}

}
