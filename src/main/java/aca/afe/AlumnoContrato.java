package aca.afe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumnoContrato {
	private String codigoPersonal;
	private String cargaId;
	private String plazaId;
	private String status;
	private String numHoras;
	private String precioHora;
	private String tipoPlaza;
	private String tipoBeca;
	private String puestoId;
	private String turno;
	private String dias;
	private String departamento;
	private String jefe;
	private String industrial;
	
	// Constructor
	public AlumnoContrato(){		
		codigoPersonal 	 = "";		
		cargaId	         = "";
		plazaId			 = "";
		status			 = "";
		numHoras		 = "";
		precioHora		 = "";
		tipoPlaza		 = "";
		tipoBeca		 = "";
		puestoId		 = "";
		turno			 = "";
		dias			 = "";
		departamento	 = "";
		jefe			 = "";
		industrial		 = "";
		
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
	 * @return the numHoras
	 */
	public String getNumHoras() {
		return numHoras;
	}

	/**
	 * @param numHoras the numHoras to set
	 */
	public void setNumHoras(String numHoras) {
		this.numHoras = numHoras;
	}

	/**
	 * @return the precioHora
	 */
	public String getPrecioHora() {
		return precioHora;
	}

	/**
	 * @param precioHora the precioHora to set
	 */
	public void setPrecioHora(String precioHora) {
		this.precioHora = precioHora;
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
	 * @return the tipoBeca
	 */
	public String getTipoBeca() {
		return tipoBeca;
	}

	/**
	 * @param tipoBeca the tipoBeca to set
	 */
	public void setTipoBeca(String tipoBeca) {
		this.tipoBeca = tipoBeca;
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
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the jefe
	 */
	public String getJefe() {
		return jefe;
	}

	/**
	 * @param jefe the jefe to set
	 */
	public void setJefe(String jefe) {
		this.jefe = jefe;
	}

	/**
	 * @return the industrial
	 */
	public String getIndustrial() {
		return industrial;
	}

	/**
	 * @param industrial the industrial to set
	 */
	public void setIndustrial(String industrial) {
		this.industrial = industrial;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");		
		cargaId			= rs.getString("CARGA_ID");
		plazaId			= rs.getString("PLAZA_ID");
		status			= rs.getString("STATUS");
		numHoras		= rs.getString("NUM_HORAS");
		precioHora		= rs.getString("PRECIO_HORA");
		tipoPlaza		= rs.getString("TIPO_PLAZA");
		tipoBeca		= rs.getString("TIPO_BECA");
		puestoId		= rs.getString("PUESTO_ID");
		turno			= rs.getString("TURNO");
		dias			= rs.getString("DIAS");
		departamento	= rs.getString("DEPARTAMENTO");
		jefe			= rs.getString("JEFE");
		industrial		= rs.getString("INDUSTRIAL");
		
	}	
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cargaId ) throws SQLException, IOException{
		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" CODIGO_PERSONAL, CARGA_ID, PLAZA_ID, STATUS, NUM_HORAS,PRECIO_HORA,TIPO_PLAZA, "+
	 			" TIPO_BECA,PUESTO_ID, TURNO, DIAS, DEPARTAMENTO,JEFE,INDUSTRIAL "+
	 			" FROM ENOC.ALUMNO_CONTRATO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? "); 
	 		ps.setString(1, codigoPersonal);
	 		ps.setString(2, cargaId);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoContratoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}

}