package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContCcosto {
	private String idEjercicio;
	private String idCcosto;	
	private String nombre;
	private String detalle;
	private String iniciales;
	
	// Constructor
	public ContCcosto(){		
		idEjercicio		= "";
		idCcosto		= "";
		nombre			= "";
		detalle			= "";
		iniciales		= "";
		
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		idEjercicio	         	= rs.getString("ID_EJERCICIO");
		idCcosto	         	= rs.getString("ID_CCOSTO");
		nombre		         	= rs.getString("NOMBRE");
		detalle		         	= rs.getString("DETALLE");
		iniciales				= rs.getString("INICIALES");
	}
	
	 	
	/**
	 * @return the idEjercicio
	 */
	public String getIdEjercicio() {
		return idEjercicio;
	}

	/**
	 * @param idEjercicio the idEjercicio to set
	 */
	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	/**
	 * @return the idCcosto
	 */
	public String getIdCcosto() {
		return idCcosto;
	}

	/**
	 * @param idCcosto the idCcosto to set
	 */
	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return the iniciales
	 */
	public String getIniciales() {
		return iniciales;
	}

	/**
	 * @param iniciales the iniciales to set
	 */
	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}
	
	public void mapeaRegId(Connection conn, String idEjercicio, String idCcosto) throws SQLException, IOException{
		
		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES" +	 					
	 				" FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?");
	 		ps.setString(1, idEjercicio);
	 		ps.setString(2, idCcosto);	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcosto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		} 		
 	
 	}
	
}