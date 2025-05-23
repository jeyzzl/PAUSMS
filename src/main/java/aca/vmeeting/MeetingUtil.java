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
public class MeetingUtil {
	public static ArrayList<Meeting> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Meeting> list	= new ArrayList<Meeting>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT ID, NOMBRE, TO_CHAR(F_INICIO, 'DD/MM/YYYY HH24:MI') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY HH24:MI') AS F_FINAL," +
					" BBB_MEETING_ID, BBB_ATTENDEE_PW, BBB_MODERATOR_PW, BBB_VOICE_BRIDGE," +
					" BBB_RECORD, IDENTIFICADOR_OWNER, ID_SISTEMA" +
 				" FROM MEETING "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Meeting meeting = new Meeting();
				meeting.mapeaReg(rs);
				list.add(meeting);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return list;
	}
	
	public static ArrayList<Meeting> getListOwner(Connection conn, String sistemaId, String owner, String orden ) throws SQLException{
		
		ArrayList<Meeting> list	= new ArrayList<Meeting>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT ID, NOMBRE, TO_CHAR(F_INICIO, 'DD/MM/YYYY HH24:MI') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY HH24:MI') AS F_FINAL," +
					" BBB_MEETING_ID, BBB_ATTENDEE_PW, BBB_MODERATOR_PW, BBB_VOICE_BRIDGE," +
					" BBB_RECORD, IDENTIFICADOR_OWNER, ID_SISTEMA" +
 				" FROM MEETING" +
 				" WHERE ID_SISTEMA = "+sistemaId+
 				" AND IDENTIFICADOR_OWNER = '"+owner+"' "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Meeting meeting = new Meeting();
				meeting.mapeaReg(rs);
				list.add(meeting);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return list;
	}
	
	public static ArrayList<Meeting> getListInvitado(Connection conn, String sistemaId, String identificador, String orden ) throws SQLException{
		
		ArrayList<Meeting> list	= new ArrayList<Meeting>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT ID, NOMBRE, TO_CHAR(F_INICIO, 'DD/MM/YYYY HH24:MI') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY HH24:MI') AS F_FINAL," +
					" BBB_MEETING_ID, BBB_ATTENDEE_PW, BBB_MODERATOR_PW, BBB_VOICE_BRIDGE," +
					" BBB_RECORD, IDENTIFICADOR_OWNER, ID_SISTEMA" +
 				" FROM MEETING" +
 				" WHERE ID IN (SELECT ID_MEETING FROM ASISTENTE" +
 							" WHERE ID_SISTEMA = "+sistemaId+
 							" AND IDENTIFICADOR = '"+identificador+"')" +
 				" "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Meeting meeting = new Meeting();
				meeting.mapeaReg(rs);
				list.add(meeting);
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
