/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class MentCarrera {
	private String carreraId;
	private String mentorId;
	private String periodoId;
	
	public MentCarrera(){
		carreraId	= "";
		mentorId	= "";
		periodoId	= "";
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	/**
	 * @return the mentorId
	 */
	public String getMentorId() {
		return mentorId;
	}

	/**
	 * @param mentorId the mentorId to set
	 */
	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}
	
	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}

	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps= null;
		try{
			ps= conn.prepareStatement("INSERT INTO ENOC.MENT_CARRERA" + 
					" (CARRERA_ID, MENTOR_ID, PERIODO_ID)" +
					" VALUES(?, ?, ?)");
			
			ps.setString(1, carreraId);
			ps.setString(2, mentorId);
			ps.setString(3, periodoId);
			
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentCarrera|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_CARRERA"+ 
				" WHERE CARRERA_ID = ?" +
				" AND MENTOR_ID = ?" +
				" AND PERIODO_ID = ?");
			
			ps.setString(1, carreraId);
			ps.setString(2, mentorId);
			ps.setString(3, periodoId);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentCarrera|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		carreraId	= rs.getString("CARRERA_ID");
		mentorId	= rs.getString("MENTOR_ID");
		periodoId	= rs.getString("PERIODO_ID");
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_CARRERA" + 
					" WHERE CARRERA_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND PERIODO_ID = ?");
			
			ps.setString(1, carreraId);
			ps.setString(2, mentorId);
			ps.setString(3, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentCarrera|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNumMentoresCarrera(Connection conn, String carreraId, String periodoId) throws SQLException{
		String cantidad 		= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(MENTOR_ID) AS CANTIDAD" +
					" FROM ENOC.MENT_CARRERA" + 
					" WHERE CARRERA_ID = ?" +
					" AND PERIODO_ID = ?");
			
			ps.setString(1, carreraId);
			ps.setString(2, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cantidad = rs.getString("CANTIDAD");
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentCarrera|getNumMentoresCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
}