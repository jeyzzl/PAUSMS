package aca.carga;

import java.sql.*;

public class CargaUnidad {
	private String cursoCargaId;
	private String unidadId;	
	private String unidadNombre;
	private String orden;
	
	public CargaUnidad(){
		cursoCargaId = "";
		unidadId	 = "";
		unidadNombre = "";
		orden        = "";
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	/**
	 * @return the unidadId
	 */
	public String getUnidadId() {
		return unidadId;
	}

	/**
	 * @param unidadId the unidadId to set
	 */
	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	/**
	 * @return the unidadNombre
	 */
	public String getUnidadNombre() {
		return unidadNombre;
	}

	/**
	 * @param unidadNombre the unidadNombre to set
	 */
	public void setUnidadNombre(String unidadNombre) {
		this.unidadNombre = unidadNombre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		unidadId 			= rs.getString("UNIDAD_ID");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		unidadNombre		= rs.getString("UNIDAD_NOMBRE");
		orden				= rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String unidadId) throws SQLException{	
		
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE, ORDEN "+
			"FROM ENOC.CARGA_UNIDAD WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?"); 
		ps.setString(1,cursoCargaId);
		ps.setString(2,unidadId);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
		
	}
}