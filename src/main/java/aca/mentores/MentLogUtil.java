/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Elifo
 *
 */
public class MentLogUtil {
	public ArrayList<String> getListAlumnosMentor(Connection conn, String mentorId, String periodoId, String orden) throws SQLException{
		
		ArrayList<String> lisAlumnos	= new ArrayList<String>();
		PreparedStatement ps		= null;
		ResultSet rs 				= null;
		
		try{
			ps = conn.prepareStatement("SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
								  " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?) "+orden); 
			
			ps.setString(1, mentorId);
			ps.setString(2, periodoId);
			ps.setString(3, periodoId);
			
			rs = ps.executeQuery();
			while (rs.next()){
				lisAlumnos.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentorMotivoUtil|getListAlumnosMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumnos;
	}
}