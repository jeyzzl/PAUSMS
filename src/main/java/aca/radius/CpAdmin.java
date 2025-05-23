/**
 * 
 */
package aca.radius;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class CpAdmin {
	private String usuario;
	private boolean leer;
	private boolean modificar;
	private boolean agregar;
	
	public CpAdmin(){
		usuario		= "";
		leer		= false;
		modificar	= false;
		agregar		= false;
	}
	
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the leer
	 */
	public boolean isLeer() {
		return leer;
	}

	/**
	 * @param leer the leer to set
	 */
	public void setLeer(boolean leer) {
		this.leer = leer;
	}

	/**
	 * @return the modificar
	 */
	public boolean isModificar() {
		return modificar;
	}

	/**
	 * @param modificar the modificar to set
	 */
	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	/**
	 * @return the agregar
	 */
	public boolean isAgregar() {
		return agregar;
	}

	/**
	 * @param agregar the agregar to set
	 */
	public void setAgregar(boolean agregar) {
		this.agregar = agregar;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		usuario		= rs.getString("USUARIO");
		leer		= rs.getBoolean("LEER");
		modificar	= rs.getBoolean("MODIFICAR");
		agregar		= rs.getBoolean("AGREGAR");
	}
	
	public void mapeaRegId(Connection con, String usuario) throws SQLException{
		PreparedStatement ps =null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT USUARIO, LEER, MODIFICAR, AGREGAR "+
					"FROM CP_ADMIN WHERE USUARIO = ?"); 
			ps.setString(1, usuario);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpAdmin|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM CP_ADMIN WHERE USUARIO = ?"); 
			ps.setString(1, usuario);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpAdmin|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}
