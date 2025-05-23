// Clase para la tabla NOE.AFE_CONTRATO_ALUMNO
package aca.afe;
import java.sql.*;

public class AfeContratoAlumno{	
	private String id;
	private String ccostoPuestoId;
	private String matricula;
	private String status;
	private String fechaAlta;
	private String userAltaId;
	private String plazaId;	
	
	// Constructor
	public AfeContratoAlumno(){		
		id 				= "";		
		ccostoPuestoId	= "";
		matricula		= "";
		status			= "";
		fechaAlta		= "";
		userAltaId		= "";
		plazaId			= "";
	}
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the ccostoPuestoId
	 */
	public String getCcostoPuestoId() {
		return ccostoPuestoId;
	}


	/**
	 * @param ccostoPuestoId the ccostoPuestoId to set
	 */
	public void setCcostoPuestoId(String ccostoPuestoId) {
		this.ccostoPuestoId = ccostoPuestoId;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the fechaAlta
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}


	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	/**
	 * @return the userAltaId
	 */
	public String getUserAltaId() {
		return userAltaId;
	}


	/**
	 * @param userAltaId the userAltaId to set
	 */
	public void setUserAltaId(String userAltaId) {
		this.userAltaId = userAltaId;
	}	
	
	/**
	 * @return the plazaId
	 */
	public String getPlazaId() {
		return plazaId;
	}	

	/**
	 * @param plazaId the plazaId to set
	 */
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		id 				= rs.getString("ID");		
		ccostoPuestoId	= rs.getString("CCOSTO_PUESTO_ID");
		matricula		= rs.getString("MATRICULA");
		status			= rs.getString("STATUS");
		fechaAlta		= rs.getString("FECHA_ALTA");
		userAltaId		= rs.getString("USER_ALTA_ID");
		plazaId			= rs.getString("PLAZA_ID");
	}	
	
	public void mapeaRegId(Connection con, String id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT ID, CCOSTO_PUESTO_ID, MATRICULA, "+
				"STATUS, FECHA_ALTA, USER_ALTA_ID, PLAZA_ID "+				
				"FROM NOE.AFE_CONTRATO_ALUMNO WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeContratoAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}