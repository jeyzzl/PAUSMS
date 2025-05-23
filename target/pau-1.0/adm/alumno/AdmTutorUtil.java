package adm.alumno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmTutorUtil {
	
public ArrayList<AdmTutor> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmTutor> list		= new ArrayList<AdmTutor>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL, PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD " +
			" FROM SALOMON.ADM_TUTOR "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmTutor obj = new AdmTutor();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmTutorUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
}
