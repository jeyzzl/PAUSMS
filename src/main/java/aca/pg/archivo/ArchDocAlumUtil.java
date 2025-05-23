/**
 * 
 */
package aca.pg.archivo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author ifo
 *
 */
public class ArchDocAlumUtil {
	
	public static HashMap<String, String> mapAlumnosEnDocumento(Connection conn, String documentoId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT MATRICULA, COUNT(MATRICULA) AS TOTAL FROM ARCH_DOCALUM WHERE IDDOCUMENTO = "+documentoId+" GROUP BY MATRICULA";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("MATRICULA"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.ArchDocAlumUtil|mapAlumnosEnDocumento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	
}