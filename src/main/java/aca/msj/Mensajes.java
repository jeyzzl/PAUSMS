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
public class Mensajes {
	private String mensajeId;
	private String moduloId;
	private String usuario;
	private String fecha;
	private String mensaje;
	
	public Mensajes(){
		mensajeId	= "";
		moduloId	= "";
		usuario		= "";
		fecha		= "";
		mensaje		= "";
	}

	/**
	 * @return Returns the fecha.
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha The fecha to set.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return Returns the mensaje.
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje The mensaje to set.
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return Returns the mensajeId.
	 */
	public String getMensajeId() {
		return mensajeId;
	}

	/**
	 * @param mensajeId The mensajeId to set.
	 */
	public void setMensajeId(String mensajeId) {
		this.mensajeId = mensajeId;
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
	 * @return Returns the usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario The usuario to set.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MENSAJES(MENSAJE_ID, MODULO_ID,"+ 
					" USUARIO, FECHA, MENSAJE) VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?)");
			ps.setString(1, mensajeId);
			ps.setString(2, moduloId);
			ps.setString(3, usuario);
			ps.setString(4, fecha);
			ps.setString(5, mensaje);
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.Mensajes|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENSAJES"+
					" SET MODULO_ID = ?," +
					" USUARIO = ?," +
					" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
					" MENSAJE = ?" +
					" WHERE MENSAJE_ID = ? ");
			ps.setString(1, moduloId);
			ps.setString(2, usuario);
			ps.setString(3, fecha);
			ps.setString(4, mensaje);
			ps.setString(5, mensajeId);
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.Mensajes|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENSAJES "+
				"WHERE MENSAJE_ID= ?");
			ps.setString(1, mensajeId);
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.Mensajes|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public void mapeaReg(ResultSet rs) throws SQLException{
		mensajeId		= rs.getString("MENSAJE_ID");
		moduloId		= rs.getString("MODULO_ID");
		usuario			= rs.getString("USUARIO");
		fecha			= rs.getString("FECHA");
		mensaje			= rs.getString("MENSAJE");
	}
	
	public void mapeaRegId(Connection conn, String mensajeId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT MENSAJE_ID, MODULO_ID, USUARIO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MENSAJE"+
					" FROM ENOC.MENSAJES WHERE MENSAJE_ID = ?");
			ps.setString(1, mensajeId);
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.msj.Mensajes|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENSAJES WHERE MENSAJE_ID = ?");
			ps.setString(1, mensajeId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.msj.Mensajes|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public int getMaxReg(Connection conn) throws SQLException{
		int ok 					= 1;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CASE MAX(MENSAJE_ID)+1 WHEN NULL THEN 1 ELSE MAX(MENSAJE_ID)+1 END AS MAXIMO FROM ENOC.MENSAJES ");
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = rs.getInt("MAXIMO");
			else
				ok = 1;
			
		}catch(Exception ex){
			System.out.println("Error - aca.msj.Mensajes|getMaxReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}