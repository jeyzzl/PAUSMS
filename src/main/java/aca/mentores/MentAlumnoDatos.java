/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


/**
 * @author elifo
 *
 */
public class MentAlumnoDatos {
	private String codigoPersonal;
	private String iglesia;
	private String claseEs;
	
	public MentAlumnoDatos(){
		codigoPersonal	= "";
		iglesia			= "";
		claseEs			= "";
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
	 * @return the iglesia
	 */
	public String getIglesia() {
		return iglesia;
	}

	/**
	 * @param iglesia the iglesia to set
	 */
	public void setIglesia(String iglesia) {
		this.iglesia = iglesia;
	}

	/**
	 * @return the claseEs
	 */
	public String getClaseEs() {
		return claseEs;
	}

	/**
	 * @param claseEs the claseEs to set
	 */
	public void setClaseEs(String claseEs) {
		this.claseEs = claseEs;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps= null;
		try{
			ps= conn.prepareStatement("INSERT INTO ENOC.MENT_ALUMNO_DATOS" + 
					" (CODIGO_PERSONAL, IGLESIA, CLASE_ES)" +
					" VALUES( ?, ?, ?)");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, iglesia);
			ps.setString(3, claseEs);
			
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_ALUMNO_DATOS" + 
					" SET IGLESIA = ?," +
					" CLASE_ES = ?" +
					" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, iglesia);
			ps.setString(2, claseEs);
			ps.setString(3, codigoPersonal);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_ALUMNO_DATOS"+ 
				" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|deleteReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		iglesia				= rs.getString("IGLESIA")==null?"":rs.getString("IGLESIA");
		claseEs				= rs.getString("CLASE_ES")==null?"":rs.getString("CLASE_ES");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, IGLESIA, CLASE_ES"+
					" FROM ENOC.MENT_ALUMNO_DATOS" + 
					" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_ALUMNO_DATOS WHERE CODIGO_PERSONAL = ?");		
		ps.setString(1, codigoPersonal);						
		rs = ps.executeQuery();
		if (rs.next())
			ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String iglesia( Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String iglesia			= "";
		
		try{
			ps	= conn.prepareStatement("SELECT IGLESIA FROM ENOC.MENT_ALUMNO_DATOS" + 
					" WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				iglesia = rs.getString("IGLESIA");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|iglesia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return iglesia;
	}
	
	public static String clase( Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String clase			= "";
		
		try{
			ps	= conn.prepareStatement("SELECT CLASE_ES FROM ENOC.MENT_ALUMNO_DATOS" + 
					" WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				clase = rs.getString("CLASE_ES");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|clase|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return clase;
	}
	
	public HashMap<String, MentAlumnoDatos> getMapAll(Connection conn) throws SQLException{
		
		HashMap<String, MentAlumnoDatos> list		= new HashMap<String,MentAlumnoDatos>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, IGLESIA, CLASE_ES FROM ENOC.MENT_ALUMNO_DATOS ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MentAlumnoDatos ment = new MentAlumnoDatos();
				ment.mapeaReg(rs);
				list.put(ment.getCodigoPersonal(), ment);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoDatos|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}