/**
 * 
 */
package aca.vmeeting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class AsistenteUtil {
	public static ArrayList<Asistente> getListMeeting(Connection conn, int sistemaId, int meetingId, String orden ) throws SQLException{
		
		ArrayList<Asistente> list	= new ArrayList<Asistente>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT ID, IDENTIFICADOR, ID_SISTEMA, ID_MEETING," +
	 			" MODERADOR, ENTERADO" +
 				" FROM ASISTENTE" +
 				" WHERE ID_SISTEMA = "+sistemaId+
 				" AND ID_MEETING = "+meetingId+" "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Asistente asistente = new Asistente();
				asistente.mapeaReg(rs);
				list.add(asistente);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return list;
	}
}
