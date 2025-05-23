package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvResultadoUtil {
	
	public boolean insertReg(Connection conn, InvResultado res) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_RESULTADO(PROYECTO_ID, INFRAESTRUCTURA, BIBLIOGRAFIA)"
					+ " VALUES (?, ?, ?)");
			
			ps.setString(1, res.getProyectoId());
			ps.setString(2, res.getInfraestructura());
			ps.setString(3, res.getBibliografia());
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else {
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.InvResultado|insertReg|:"+ex);
		}finally{
			if(ps != null) ps.close();
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, InvResultado res) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_RESULTADO "
					+ " SET INFRAESTRUCTURA = ?,"
					+ " BIBLIOGRAFIA = ?"
					+ " WHERE PROYECTO_ID = ?");
			ps.setString(1, res.getInfraestructura());
			ps.setString(2, res.getBibliografia());
			ps.setString(3, res.getProyectoId());
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else {
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.InvResultado|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String proyectoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_RESULTADO WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else {
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvResultado|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public InvResultado mapeaRegId(Connection conn, String proyectoId) throws SQLException{
		
		InvResultado res = new InvResultado();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PROYECTO_ID, INFRAESTRUCTURA, BIBLIOGRAFIA"
					+ " FROM ENOC.INV_RESULTADO"
					+ " WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				res.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvResultado|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return res;
	}
	
	public boolean existeReg(Connection conn, String proyectoId) throws SQLException{
		boolean ok = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_RESULTADO WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvResultado|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
}
