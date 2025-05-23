/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class BajaAlumno {
	private String bajaId;
	private String codigoPersonal;
	private String cargaId;
	private String carreraId;
	private String planId;
	private String fInicio;
	private String fBaja;
	private String comentario;
	
	public BajaAlumno(){
		bajaId			= "";
		codigoPersonal	= "";
		cargaId			= "";
		carreraId		= "";
		planId			= "";
		fInicio			= "";
		fBaja			= "";
		comentario		= "";
	}

	/**
	 * @return the bajaId
	 */
	public String getBajaId() {
		return bajaId;
	}

	/**
	 * @param bajaId the bajaId to set
	 */
	public void setBajaId(String bajaId) {
		this.bajaId = bajaId;
	}

	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}

	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the fBaja
	 */
	public String getFBaja() {
		return fBaja;
	}

	/**
	 * @param baja the fBaja to set
	 */
	public void setFBaja(String baja) {
		fBaja = baja;
	}

	/**
	 * @return the fInicio
	 */
	public String getFInicio() {
		return fInicio;
	}

	/**
	 * @param inicio the fInicio to set
	 */
	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		bajaId			= rs.getString("BAJA_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cargaId			= rs.getString("CARGA_ID");
		carreraId		= rs.getString("CARRERA_ID");
		planId			= rs.getString("PLAN_ID");
		fInicio			= rs.getString("F_INICIO");
		fBaja			= rs.getString("F_BAJA");
		comentario		= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId(Connection conn, String bajaId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, CODIGO_PERSONAL," +
					" CARGA_ID, CARRERA_ID, PLAN_ID, F_INICIO, F_BAJA, COMENTARIO" +
					" FROM ENOC.BAJA_ALUMNO WHERE BAJA_ID = ? "); 
			
			ps.setString(1, bajaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}