// Bean de datos academicos del alumno
package  aca.alumno;
import java.sql.*;

public class AlumSE{
	private String codigoPersonal;
	private String codigoSE;
	private String cargaId;
	private String carreraId;
	private String planId;
	private String grado;
	private String ciclo;
	private String genero;
	private String estado;
		
	public AlumSE(){
		codigoPersonal		= "";
		codigoSE			= "";
		cargaId				= "";
		carreraId			= "";
		planId				= "";
		grado				= "";
		ciclo				= "";
		genero				= "";
		estado				= "";
		
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
	 * @return the codigoSE
	 */
	public String getCodigoSE() {
		return codigoSE;
	}

	/**
	 * @param codigoSE the codigoSE to set
	 */
	public void setCodigoSE(String codigoSE) {
		this.codigoSE = codigoSE;
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

	/**
	 * @return the grado
	 */
	public String getGrado() {
		return grado;
	}

	/**
	 * @param grado the grado to set
	 */
	public void setGrado(String grado) {
		this.grado = grado;
	}

	/**
	 * @return the ciclo
	 */
	public String getCiclo() {
		return ciclo;
	}

	/**
	 * @param ciclo the ciclo to set
	 */
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		codigoSE 		= rs.getString("CODIGO_SE");
		cargaId 		= rs.getString("CARGA_ID");
		carreraId	 	= rs.getString("CARRERA_ID");
		planId 			= rs.getString("PLAN_ID");
		grado		    = rs.getString("GRADO");
		ciclo 			= rs.getString("CICLO");
		genero			= rs.getString("GENERO");
		estado			= rs.getString("ESTADO");
				
	}
	
}