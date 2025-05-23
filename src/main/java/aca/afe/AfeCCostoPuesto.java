// Clase para la tabla NOE.AFE_CCOSTO_PUESTO
package aca.afe;
import java.sql.*;

public class AfeCCostoPuesto{	
	private String id;
	private String ejercicioId;
	private String ccostoId;
	private String puestoId;
	private String turno;
	private String dias;
	private String requisitos;
	private String email;
	private String maximoHoras;
	private String clave;
	private String status;
	
	// Constructor
	public AfeCCostoPuesto(){		
		id 				= "";
		ejercicioId 	= "";
		ccostoId		= "";
		puestoId		= "";
		turno			= "";
		dias			= "";
		requisitos		= "";
		email			= "";
		maximoHoras		= "";
		clave 			= "";
		status			= ""; 
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
	 * @return the ejercicioId
	 */
	public String getEjercicioId() {
		return ejercicioId;
	}

	/**
	 * @param ejercicioId the ejercicioId to set
	 */
	public void setEjercicioId(String ejercicioId) {
		this.ejercicioId = ejercicioId;
	}

	/**
	 * @return the ccostoId
	 */
	public String getCcostoId() {
		return ccostoId;
	}

	/**
	 * @param ccostoId the ccostoId to set
	 */
	public void setCcostoId(String ccostoId) {
		this.ccostoId = ccostoId;
	}

	/**
	 * @return the puestoId
	 */
	public String getPuestoId() {
		return puestoId;
	}

	/**
	 * @param puestoId the puestoId to set
	 */
	public void setPuestoId(String puestoId) {
		this.puestoId = puestoId;
	}

	/**
	 * @return the turno
	 */
	public String getTurno() {
		return turno;
	}

	/**
	 * @param turno the turno to set
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}

	/**
	 * @return the dias
	 */
	public String getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(String dias) {
		this.dias = dias;
	}

	/**
	 * @return the requisitos
	 */
	public String getRequisitos() {
		return requisitos;
	}

	/**
	 * @param requisitos the requisitos to set
	 */
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the maximoHoras
	 */
	public String getMaximoHoras() {
		return maximoHoras;
	}

	/**
	 * @param maximoHoras the maximoHoras to set
	 */
	public void setMaximoHoras(String maximoHoras) {
		this.maximoHoras = maximoHoras;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		id 				= rs.getString("ID");
		ejercicioId 	= rs.getString("EJERCICIO_ID");
		ccostoId		= rs.getString("CCOSTO_ID");
		puestoId		= rs.getString("PUESTO_ID");
		turno			= rs.getString("TURNO");
		dias			= rs.getString("DIAS");
		requisitos		= rs.getString("REQUISITOS");
		email			= rs.getString("EMAIL");
		maximoHoras		= rs.getString("MAXIMO_HORAS");
		clave 			= rs.getString("CLAVE");
		status			= rs.getString("STATUS");
	}
	
	public void mapeaRegId(Connection con, String id) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		 
		try{
			ps = con.prepareStatement("SELECT ID,EJERCICIO_ID, CCOSTO_ID, PUESTO_ID, "+
					"TURNO,DIAS, REQUISITOS, EMAIL, MAXIMO_HORAS, CLAVE, STATUS "+
					"FROM NOE.AFE_CCOSTO_PUESTO WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfeCCostoPuestoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}

	}	
	
}