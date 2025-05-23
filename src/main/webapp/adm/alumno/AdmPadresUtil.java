package adm.alumno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmPadresUtil {

	public ArrayList<AdmPadres> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmPadres> list		= new ArrayList<AdmPadres>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, PADRE_NOMBRE, PADRE_APELLIDO, PADRE_RELIGION, PADRE_NACIONALIDAD, PADRE_OCUPACION, " +
					" MADRE_NOMBRE, MADRE_APELLIDO, MADRE_RELIGION, MADRE_NACIONALIDAD, MADRE_OCUPACION " +
			" FROM SALOMON.ADM_PADRES "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmPadres obj = new AdmPadres();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPadresUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
}
