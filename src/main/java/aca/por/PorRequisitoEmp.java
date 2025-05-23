package aca.por;

import java.sql.*;

public class PorRequisitoEmp {
	
	private String requisitoId;
	private String portafolioId;
	private String empleadoId;
	
	
	public String getRequisitoId() {
		return requisitoId;
	}
	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}
	public String getPortafolioId() {
		return portafolioId;
	}
	public void setPortafolioId(String portafolioId) {
		this.portafolioId = portafolioId;
	}
	public String getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(String empleadoId) {
		this.empleadoId = empleadoId;
	}
	
	public boolean insertReg (Connection conn) throws Exception{
		
		PreparedStatement ps = null;
		boolean ok			 = false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.POR_REQUISITO_EMP (PORTAFOLIO_ID, REQUISITO_ID, EMPLEADO_ID)"
					+ "VALUES (TO_NUMBER(?, '999'), TO_NUMBER (?, 9999999), ?)");
			ps.setString(1, portafolioId);
			ps.setString(2, requisitoId);
			ps.setString(3, empleadoId);
			
			if (ps.executeUpdate() == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoEmp|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn) throws Exception{
		
		PreparedStatement ps = null;
		boolean ok			 = false;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.POR_REQUISITO_EMP WHERE REQUISITO_ID = TO_NUMBER(?, '9999999')");
			ps.setString(1, requisitoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
		}catch (Exception ex){
			System.out.println("Error - aca.por.PorRequisitoEmp|deleteReg|:"+ex);
		}finally{
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		portafolioId	= rs.getString("PORTAFOLIO_ID");
		requisitoId		= rs.getString("REQUISITO_ID");
		empleadoId		= rs.getString("EMPLEADO_ID");
	}
	
	public void mapeaRegId(Connection conn, String requisitoId, String empleadoId) throws SQLException{
		ResultSet rs	 = null;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("SELECT REQUISITO_ID, PORTAFOLIO_ID, SECCION_ID FROM ENOC.POR_REQUISITO_EMP"
					+ " WHERE REQUISITO_ID = TO_NUMBER(?, '9999999')"
					+ " AND EMPLEADO_ID = ?");
			ps.setString(1, requisitoId);
			ps.setString(2, empleadoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.PorRequisitoEmp|mapeaRegId|:"+ex);
		}finally{
			if (rs != null) rs.close();
			if (ps != null) ps.close();
		}
	}
	
	public void mapeaRegId( Connection conn) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT PORTAFOLIO_ID, REQUISITO_ID, EMPLEADO_ID FROM ENOC.POR_REQUISITO_EMP"
					+ " WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"
					+ " AND EMPLEADO_ID = ?"); 
			ps.setString(1, requisitoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoEmp|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_REQUISITO_EMP"
					+ " WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"
					+ " AND EMPLEADO_ID = ?");
			ps.setString(1, requisitoId);
			ps.setString(2, empleadoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
		}catch (Exception ex){
			System.out.println("Error - aca.por.PorRequisitoEmp|existeReg|:"+ex);
		}finally{
			if (rs != null) rs.close();
			if (ps != null) ps.close();
		}
		
		return ok;
	}	

}
