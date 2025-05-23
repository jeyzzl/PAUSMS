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
public class MentAcceso {
	private String codigoPersonal;
	private String acceso;
	
	public MentAcceso(){
		codigoPersonal	= "";
		acceso			= "";
	}

	/**
	 * @return the acceso
	 */
	public String getAcceso() {
		return acceso;
	}

	/**
	 * @param acceso the acceso to set
	 */
	public void setAcceso(String acceso) {
		this.acceso = acceso;
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
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps= null;
		try{
			ps= conn.prepareStatement("INSERT INTO ENOC.MENT_ACCESO(CODIGO_PERSONAL, "+ 
					"ACCESO) VALUES( ?, ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, acceso);
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAcceso|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_ACCESO "+ 
					"SET ACCESO = ? WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, acceso);
			ps.setString(2, codigoPersonal);
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAcceso|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_ACCESO "+ 
				"WHERE CODIGO_PERSONAL= ?");
			ps.setString(1, codigoPersonal);
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAcceso|deleteReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		acceso			= rs.getString("ACCESO");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, ACCESO "+
					"FROM ENOC.MENT_ACCESO WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAcceso|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_ACCESO WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAcceso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean tieneAccesoCarrera(Connection conn, String codigoPersonal, String carreraId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL" +
					" FROM ENOC.MENT_ACCESO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND ACCESO LIKE '%'||?||'%'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAcceso|tieneAccesoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}