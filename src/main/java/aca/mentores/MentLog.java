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
public class MentLog {
	private String folio;
	private String mentorId;
	private String codigoPersonal;
	private String fecha;
	private String tab;
	
	public MentLog(){
		folio			= "";
		mentorId		= "";
		codigoPersonal	= "";
		fecha			= "";
		tab				= "";
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
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
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * @return the tab
	 */
	public String getTab() {
		return tab;
	}

	/**
	 * @param tab the tab to set
	 */
	public void setTab(String tab) {
		this.tab = tab;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps= null;
		try{
			ps= conn.prepareStatement("INSERT INTO ENOC.MENT_LOG" + 
					" (FOLIO, MENTOR_ID, CODIGO_PERSONAL, FECHA, TAB)" +
					" VALUES(TO_NUMBER(?, '99999'), ?, ?, now(), TO_NUMBER(?, '99'))");
			
			ps.setString(1, folio);
			ps.setString(2, mentorId);
			ps.setString(3, codigoPersonal);
			ps.setString(4, tab);
			
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentLog|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_LOG" + 
					" SET FECHA = TO_DATE(?, 'DD/MM/YYYY HH:MM:SS')." +
					" TAB = TO_NUMBER(?, '99')" +
					" WHERE FOLIO = TO_NUMBER(?, '99999')" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, fecha);
			ps.setString(2, folio);
			ps.setString(3, mentorId);
			ps.setString(4, codigoPersonal);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentLog|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_LOG"+ 
				" WHERE FOLIO = TO_NUMBER(?, '99999')" +
				" AND MENTOR_ID = ?" +
				" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, folio);
			ps.setString(2, mentorId);
			ps.setString(3, codigoPersonal);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentLog|deleteReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		folio				= rs.getString("FOLIO");
		mentorId			= rs.getString("MENTOR_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		fecha				= rs.getString("FECHA");
		tab					= rs.getString("TAB");
	}
	
	public void mapeaRegId(Connection conn, String folio, String mentorId, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT FOLIO, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'), TAB"+
					" FROM ENOC.MENT_LOG" + 
					" WHERE FOLIO = TO_NUMBER(?, '99999')" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, folio);
			ps.setString(2, mentorId);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentLog|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_LOG" + 
					" WHERE FOLIO = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?");
		
		ps.setString(1, folio);
		ps.setString(2, mentorId);
		ps.setString(3, codigoPersonal);		
						
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentLog|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String mentorId, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO" +
					" FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, mentorId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNumAlumConsultados(Connection conn, String periodoId, String mentorId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String num			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUMALUM FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
							      " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)"); 
			
			ps.setString(1, mentorId);
			ps.setString(2, periodoId);
			ps.setString(3, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				num = rs.getString("NUMALUM");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getNumAlumConsultados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
	
	public static String getConsultasTab(Connection conn, String periodoId, String mentorId, String tab) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String num			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) NUMCONSULTAS FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
							      " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
					" AND TAB = TO_NUMBER(?, '99')");
			
			ps.setString(1, mentorId);
			ps.setString(2, periodoId);
			ps.setString(3, periodoId);
			ps.setString(4, tab);
			
			rs = ps.executeQuery();
			if (rs.next())
				num = rs.getString("NUMCONSULTAS");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getConsultasTab|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
	
	public static String getConsultasAlumnoTab(Connection conn, String periodoId, String mentorId, String codigoPersonal, String tab) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String num			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) NUMCONSULTAS FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
							      " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
					" AND TAB = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, mentorId);
			ps.setString(2, periodoId);
			ps.setString(3, periodoId);
			ps.setString(4, tab);
			ps.setString(5, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				num = rs.getString("NUMCONSULTAS");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getConsultasTab|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
}