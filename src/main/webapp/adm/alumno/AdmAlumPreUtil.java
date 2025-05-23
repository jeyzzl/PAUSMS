/**
 * 
 */
package adm.alumno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author ifo
 *
 */
public class AdmAlumPreUtil {
	
	public ArrayList<AdmAlumPre> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmAlumPre> lisAlumPre	= new ArrayList<AdmAlumPre>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT ALUM_FOLIO,CARRERA_ID, FOLIO, ESTADO " +
					" FROM SALOMON.ADM_ALUMPRE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmAlumPre admPre = new AdmAlumPre();
				admPre.mapeaReg(rs);
				lisAlumPre.add(admPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumPreUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumPre;
	}
	
	public ArrayList<AdmAlumPre> getLista(Connection conn, String carreraId, String orden) throws SQLException{
		
		ArrayList<AdmAlumPre> lisAlumPre	= new ArrayList<AdmAlumPre>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT ALUM_FOLIO, CARRERA_ID, FOLIO, ESTADO " +
					" FROM SALOMON.ADM_ALUMPRE" + 
					" WHERE CARRERA_ID = '"+carreraId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmAlumPre admPre = new AdmAlumPre();
				admPre.mapeaReg(rs);
				lisAlumPre.add(admPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumPreUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumPre;
	}
	
}