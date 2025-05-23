/**
 * 
 */
package aca.admision;

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
		
		ArrayList<AdmAlumPre> lisPre	= new ArrayList<AdmAlumPre>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT ALUM_FOLIO, CARRERA_ID, FOLIO, ESTADO " +
					" FROM SALOMON.ADM_ALUMPRE "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmAlumPre alumPre = new AdmAlumPre();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.AdmAlumPreUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public ArrayList<AdmAlumPre> getLista(Connection conn, String alumFolio, String carreraId, String orden) throws SQLException{
		
		ArrayList<AdmAlumPre> lisPre	= new ArrayList<AdmAlumPre>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT ALUM_FOLIO, CARRERA_ID, FOLIO, ESTADO " +
					" FROM SALOMON.ADM_ALUMPRE" +
					" WHERE ALUM_FOLIO = TO_NUMBER('"+alumFolio+"','9999999')" +
					" AND CARRERA_ID = '"+carreraId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmAlumPre admPrerrequisito = new AdmAlumPre();
				admPrerrequisito.mapeaReg(rs);
				lisPre.add(admPrerrequisito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.AdmAlumPreUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
}