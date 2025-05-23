package aca.envio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnvioServicio {
	private String servicioId;
	private String servicioNombre;
	private String telefonos;
	
	public EnvioServicio(){
		servicioId		= "";
		servicioNombre	= "";
		telefonos	    = "";
	}

	/**
	 * @return the servicioId
	 */
	public String getServicioId() {
		return servicioId;
	}

	/**
	 * @param servicioId the servicioId to set
	 */
	public void setServicioId(String servicioId) {
		this.servicioId = servicioId;
	}

	/**
	 * @return the servicioNombre
	 */
	public String getServicioNombre() {
		return servicioNombre;
	}

	/**
	 * @param servicioNombre the servicioNombre to set
	 */
	public void setServicioNombre(String servicioNombre) {
		this.servicioNombre = servicioNombre;
	}

	/**
	 * @return the telefonos
	 */
	public String getTelefonos() {
		return telefonos;
	}

	/**
	 * @param telefonos the telefonos to set
	 */
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		servicioId		= rs.getString("SERVICIO_ID");
		servicioNombre	= rs.getString("SERVICIO_NOMBRE");
		telefonos		= rs.getString("TELEFONOS");
	}
	
	public void mapeaRegId(Connection con, String servicioId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT SERVICIO_ID, SERVICIO_NOMBRE, TELEFONOS FROM ENOC.ENVIO_SERVICIO " + 
					" WHERE SERVICIO_ID = ?");
			
			ps.setString(1, servicioId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EncioServicio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}