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
public class AlumMensaje {
	private String mensajeId;
	private String codigoPersonal;
	private String revisado;
	private String fecha;
	
	public AlumMensaje(){
		mensajeId		= "";
		codigoPersonal	= "";
		revisado		= "";
		fecha			= "";
	}

	/**
	 * @return Returns the codigoPersonal.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal The codigoPersonal to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	 * @return Returns the revisado.
	 */
	public String getRevisado() {
		return revisado;
	}

	/**
	 * @param revisado The revisado to set.
	 */
	public void setRevisado(String revisado) {
		this.revisado = revisado;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ALUM_MENSAJE(MENSAJE_ID, CODIGO_PERSONAL,"+
					" REVISADO, FECHA) VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'))");
			ps.setString(1, mensajeId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, revisado);
			ps.setString(4, fecha);
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.AlumMensaje|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ALUM_MENSAJE"+
					" SET MENSAJE_ID = ?," +
					" REVISADO = ?," +
					" FECHA = TO_DATE(?, 'DD/MM/YYYY')" +
					" WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, mensajeId);
			ps.setString(2, revisado);
			ps.setString(3, fecha);
			ps.setString(4, codigoPersonal);
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.AlumMensaje|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ALUM_MENSAJE "+
				"WHERE CODIGO_PERSONAL= ?");
			ps.setString(1, codigoPersonal);
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.msj.AlumMensaje|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public void mapeaReg(ResultSet rs) throws SQLException{
		mensajeId		= rs.getString("MENSAJE_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		revisado		= rs.getString("REVISADO");
		fecha			= rs.getString("FECHA");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT MENSAJE_ID, CODIGO_PERSONAL, REVISADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA"+
					" FROM ALUM_MENSAJE WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.msj.AlumMensaje|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ALUM_MENSAJE WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.msj.AlumMensaje|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}