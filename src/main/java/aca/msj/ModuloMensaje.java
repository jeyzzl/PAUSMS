/**
 * 
 */
package aca.msj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author general
 *
 */
public class ModuloMensaje {
	private String moduloId;
	private String nombre;
	private String usuarios;
	
	public ModuloMensaje(){
		moduloId	= "";
		nombre		= "";
		usuarios	= "";
	}

	/**
	 * @return Returns the moduloId.
	 */
	public String getModuloId() {
		return moduloId;
	}

	/**
	 * @param moduloId The moduloId to set.
	 */
	public void setModuloId(String moduloId) {
		this.moduloId = moduloId;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the usuarios.
	 */
	public String getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios The usuarios to set.
	 */
	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MODULO_MENSAJES(MODULO_ID,"+ 
					" NOMBRE, USUARIOS) VALUES( ?, ?, ?)");
			ps.setString(1, moduloId);
			ps.setString(2, nombre);
			ps.setString(3, usuarios);
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.ModuloMensaje|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_MENSAJES"+ 
					" SET NOMBRE = ?," +
					" USUARIOS = ?" +
					" WHERE MODULO_ID = ? ");
			ps.setString(1, nombre);
			ps.setString(2, usuarios);
			ps.setString(3, moduloId);
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.ModuloMensaje|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MODULO_MENSAJE"+ 
				" WHERE MODULO_ID = ?");
			ps.setString(1, moduloId);
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.ModuloMensaje|deleteReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public void mapeaReg(ResultSet rs) throws SQLException{
		moduloId		= rs.getString("MODULO_ID");
		nombre			= rs.getString("NOMBRE");
		usuarios		= rs.getString("USUARIOS");
	}
	
	public void mapeaRegId(Connection conn, String mensajeId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT MODULO_ID, NOMBRE, USUARIOS" +
					" FROM ENOC.MODULO_MENSAJE WHERE MODULO_ID = ?"); 
			ps.setString(1, moduloId);
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.msj.ModuloMensaje|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegUsuario(Connection conn, String usuario) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT MODULO_ID, NOMBRE, USUARIOS" +
					" FROM ENOC.MODULO_MENSAJE WHERE USUARIOS LIKE ?"); 
			ps.setString(1, ("%"+usuario+"%"));
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.msj.ModuloMensaje|mapeaRegUsuario|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.MODULO_MENSAJE WHERE MODULO_ID = ?"); 
			ps.setString(1, moduloId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.msj.ModuloMensaje|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public int getMasReg(Connection conn) throws SQLException{
		int ok 					= 0;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(MODULO_ID) AS MAXIMO FROM ENOC.MODULO_MENSAJES "); 
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = rs.getInt("MAXIMO");
			else
				ok = 1;
			
		}catch(Exception ex){
			System.out.println("Error - aca.msj.ModuloMensaje|getMasReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}