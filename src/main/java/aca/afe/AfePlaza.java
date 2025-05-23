package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AfePlaza {	
	private String id;
	private String tipoPlaza;
	private String ejercicioId;
	private String ccostoId;
	private String puestoId;
	private String turno;
	private String dias;
	private String requisitos;
	private String email;
	private String maximoHorasUniv;
	private String maximoHorasBach;
	private String proyectoId;
	private String importe;
	private String status;
	private String clave;
	
	// Constructor
	public AfePlaza(){		
		id 				= "";
		tipoPlaza   	= "";
		ejercicioId		= "";
		ccostoId 		= "";
		puestoId		= "";
		turno			= "";
		dias	    	= "";
		requisitos		= "";
		email   		= "";
		maximoHorasUniv	= "";
		maximoHorasBach = "";
		proyectoId      = "";
		importe         = "";
		status          = "";
		clave           = "";
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
	 * @return the tipoPlaza
	 */
	public String getTipoPlaza() {
		return tipoPlaza;
	}

	/**
	 * @param tipoPlaza the tipoPlaza to set
	 */
	public void setTipoPlaza(String tipoPlaza) {
		this.tipoPlaza = tipoPlaza;
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
	 * @return the maximoHorasUniv
	 */
	public String getMaximoHorasUniv() {
		return maximoHorasUniv;
	}

	/**
	 * @param maximoHorasUniv the maximoHorasUniv to set
	 */
	public void setMaximoHorasUniv(String maximoHorasUniv) {
		this.maximoHorasUniv = maximoHorasUniv;
	}

	/**
	 * @return the maximoHorasBach
	 */
	public String getMaximoHorasBach() {
		return maximoHorasBach;
	}

	/**
	 * @param maximoHorasBach the maximoHorasBach to set
	 */
	public void setMaximoHorasBach(String maximoHorasBach) {
		this.maximoHorasBach = maximoHorasBach;
	}

	/**
	 * @return the proyectoId
	 */
	public String getProyectoId() {
		return proyectoId;
	}

	/**
	 * @param proyectoId the proyectoId to set
	 */
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
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
		tipoPlaza   	= rs.getString("TIPOPLAZA");
		ejercicioId		= rs.getString("EJERCICIO_ID");
		ccostoId		= rs.getString("CCOSTO_ID");
		puestoId		= rs.getString("PUESTO_ID");
		turno			= rs.getString("TURNO");
		dias	    	= rs.getString("DIAS");
		requisitos		= rs.getString("REQUISITOS");
		email   		= rs.getString("EMAIL");
		maximoHorasUniv = rs.getString("MAXIMO_HORAS_UNIV");
		maximoHorasBach	= rs.getString("MAXIMO_HORAS_BACH");
		proyectoId	    = rs.getString("PROYECTO_ID");
		importe     	= rs.getString("IMPORTE");
		status      	= rs.getString("STATUS");
		clave       	= rs.getString("CLAVE");
	}
	
	public void mapeaRegId(Connection con, String id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID,TIPOPLAZA, EJERCICIO_ID, CCOSTO_ID, "+
					"PUESTO_ID,TURNO, DIAS, REQUISITOS, EMAIL, MAXIMO_HORAS_UNIV, MAXIMO_HORAS_BACH, "+
					"PROYECTO_ID,IMPORTE, STATUS, CLAVE "+
					"FROM NOE.AFE_PLAZA WHERE ID = ? ");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AfePlazaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}