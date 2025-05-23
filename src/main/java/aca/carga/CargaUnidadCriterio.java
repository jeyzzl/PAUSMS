package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaUnidadCriterio {
	private String cursoCargaId;
	private String criterioId;	
	private String criterioNombre;
	
	public CargaUnidadCriterio(){
		cursoCargaId   = "";
		criterioId	   = "";
		criterioNombre = "";
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
	 * @return the criterioId
	 */
	public String getCriterioId() {
		return criterioId;
	}

	/**
	 * @param criterioId the criterioId to set
	 */
	public void setCriterioId(String criterioId) {
		this.criterioId = criterioId;
	}

	/**
	 * @return the criterioNombre
	 */
	public String getCriterioNombre() {
		return criterioNombre;
	}

	/**
	 * @param criterioNombre the criterioNombre to set
	 */
	public void setCriterioNombre(String criterioNombre) {
		this.criterioNombre = criterioNombre;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		criterioId 			= rs.getString("CRITERIO_ID");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		criterioNombre	    = rs.getString("CRITERIO_NOMBRE");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String criterioId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE "+
				"FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CURSO_CARGA_ID = ? AND CRITERIO_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,criterioId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCriterio|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
	
}