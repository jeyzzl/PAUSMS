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
public class AdmContactoUtil {
	public ArrayList<AdmContacto> getLista(Connection conn, String folio, String orden) throws SQLException{
		
		ArrayList<AdmContacto> lisContacto	= new ArrayList<AdmContacto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, ORDEN, ENVIO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI') AS FECHA, MENSAJE, ESTADO"+
					" FROM SALOMON.ADM_CONTACTO" +
					" WHERE FOLIO = TO_NUMBER('"+folio+"','99999999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmContacto admContacto = new AdmContacto();
				admContacto.mapeaReg(rs);
				lisContacto.add(admContacto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmContactoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisContacto;
	}
}