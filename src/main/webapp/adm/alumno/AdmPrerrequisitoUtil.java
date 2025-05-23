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
public class AdmPrerrequisitoUtil {
	
	public ArrayList<AdmPrerrequisito> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmPrerrequisito> lisPre	= new ArrayList<AdmPrerrequisito>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARRERA_ID, FOLIO, NOMBRE " +
					" FROM SALOMON.ADM_PRERREQUISITO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmPrerrequisito admPrerrequisito = new AdmPrerrequisito();
				admPrerrequisito.mapeaReg(rs);
				lisPre.add(admPrerrequisito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumReqUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public ArrayList<AdmPrerrequisito> getLista(Connection conn, String carreraId, String orden) throws SQLException{
		
		ArrayList<AdmPrerrequisito> lisPre	= new ArrayList<AdmPrerrequisito>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARRERA_ID, FOLIO, NOMBRE " +
					" FROM SALOMON.ADM_PRERREQUISITO" + 
					" WHERE CARRERA_ID = '"+carreraId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmPrerrequisito admPrerrequisito = new AdmPrerrequisito();
				admPrerrequisito.mapeaReg(rs);
				lisPre.add(admPrerrequisito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumReqUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
}