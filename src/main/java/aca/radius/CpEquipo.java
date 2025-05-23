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
public class CpEquipo {
	private int id;
	private String usuario;
	private String mac;
	private boolean usuarioEdita;
	private String descripcion;
	
	public CpEquipo(){
		id				= 0;
		usuario			= "";
		mac				= "";
		usuarioEdita	= false;
		descripcion		= "";
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return the usuarioEdita
	 */
	public boolean isUsuarioEdita() {
		return usuarioEdita;
	}

	/**
	 * @param usuarioEdita the usuarioEdita to set
	 */
	public void setUsuarioEdita(boolean usuarioEdita) {
		this.usuarioEdita = usuarioEdita;
	}
	
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO CP_EQUIPO(USUARIO, MAC, USUARIO_EDITA, DESCRIPCION) " + 
					"VALUES(?,?,?,?)");
			ps.setString(1, usuario);
			ps.setString(2, mac);
			ps.setBoolean(3, usuarioEdita);
			ps.setString(4, descripcion);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpEquipo|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE CP_EQUIPO" +
					" SET USUARIO = ?," +
					" MAC = ?," +
					" USUARIO_EDITA = ?," +
					" DESCRIPCION = ?" +
					" WHERE ID = ?");			 
			ps.setString(1, usuario);
			ps.setString(2, mac);
			ps.setBoolean(3, usuarioEdita);
			ps.setString(4, descripcion);
			ps.setInt(5, id);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpEquipo|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM CP_EQUIPO WHERE ID = ?"); 
			ps.setInt(1, id);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpEquipo|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		id				= rs.getInt("ID");
		usuario			= rs.getString("USUARIO");
		mac				= rs.getString("MAC");
		usuarioEdita	= rs.getBoolean("USUARIO_EDITA");
		descripcion		= rs.getString("DESCRIPCION");
	}
	
	public void mapeaRegId(Connection con, int id) throws SQLException{
		PreparedStatement ps =null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID, USUARIO, MAC, USUARIO_EDITA, DESCRIPCION"+
					" FROM CP_EQUIPO WHERE ID = ?"); 
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpEquipo|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM CP_EQUIPO WHERE ID = ?"); 
			ps.setInt(1, id);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpEquipo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeRegUsuarioMac(Connection conn, String usuario, String mac) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT ID, USUARIO, MAC, USUARIO_EDITA, DESCRIPCION FROM CP_EQUIPO WHERE USUARIO = ? AND MAC = ?"); 
			ps.setString(1, usuario);
			ps.setString(2, mac);
			rs= ps.executeQuery();		
			if(rs.next()){
				mapeaReg(rs);
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.CpEquipo|existeRegUsername|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}
