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
public class AdmAlumReqUtil {
	public ArrayList<AdmAlumReq> getLista(Connection conn, String folio, String orden) throws SQLException{
		
		ArrayList<AdmAlumReq> lisAlumReq	= new ArrayList<AdmAlumReq>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, PRERREQUISITO, PROMLIC," +
					" PROMMAE, PHCA, PAEP, SERVICIO"+
					" FROM ADM_ALUMREQ" +
					" WHERE FOLIO = TO_NUMBER('"+folio+"','9999999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmAlumReq admAlumReq = new AdmAlumReq();
				admAlumReq.mapeaReg(rs);
				lisAlumReq.add(admAlumReq);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumReqUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumReq;
	}
}