// Bean del Catalogo de Roles
package  aca.rol;

import java.sql.*;

public class Rol{
	private String rolId;	
	private String rolNombre;
	
	public Rol(){
		rolId 		= "";
		rolNombre	= "";
	}
	
	/**
	 * @return the rolId
	 */
	public String getRolId() {
		return rolId;
	}

	/**
	 * @param rolId the rolId to set
	 */
	public void setRolId(String rolId) {
		this.rolId = rolId;
	}

	/**
	 * @return the rolNombre
	 */
	public String getRolNombre() {
		return rolNombre;
	}

	/**
	 * @param rolNombre the rolNombre to set
	 */
	public void setRolNombre(String rolNombre) {
		this.rolNombre = rolNombre;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ROL(ROL_ID, ROL_NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1, rolId);
			ps.setString(2, rolNombre);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ROL "+ 
				"SET ROL_NOMBRE = ? "+
				"WHERE ROL_ID = TO_NUMBER(?,'999')");
			ps.setString(1, rolNombre);
			ps.setString(2, rolId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ROL "+ 
				"WHERE ROL_ID = TO_NUMBER(?,'999')");
			ps.setString(1, rolId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		rolId 		= rs.getString("ROL_ID");
		rolNombre 	= rs.getString("ROL_NOMBRE");		
	}
	
	public void mapeaRegId( Connection conn, String rolId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ROL_ID, ROL_NOMBRE "+
				"FROM ENOC.ROL WHERE ROL_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,rolId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROL WHERE ROL_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,rolId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(ROL_ID)+1,1) AS MAXIMO FROM ENOC.ROL"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreTipo(Connection conn, String rolId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String rolNombre 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT ROL_NOMBRE FROM ENOC.ROL WHERE ROL_ID = ?"); 
			ps.setString(1,rolId);
			rs = ps.executeQuery();
			if (rs.next()){
				rolNombre = rs.getString("ROL_NOMBRE");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|getNombreTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return rolNombre;
	}
}