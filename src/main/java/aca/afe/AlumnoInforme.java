package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumnoInforme {
	private String codigoPersonal;
	private String cargaId;
	private String ejercicioId;
	private String ccostoId;
	private String descripcion;
	private String contratoId;
	private String fechaRegistro;
	private String status;
	private String numHoras;
	private String becaAdicional;
	
	// Constructor
	public AlumnoInforme(){		
		codigoPersonal  = "";		
		cargaId			= "";
		ejercicioId		= "";
		ccostoId		= "";
		descripcion		= "";
		contratoId		= "";
		fechaRegistro	= "";
		status			= "";
		numHoras		= "";
		becaAdicional   = "";
	}
	


	public String getCodigoPersonal() {
		return codigoPersonal;
	}



	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}



	public String getCargaId() {
		return cargaId;
	}



	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}



	public String getEjercicioId() {
		return ejercicioId;
	}



	public void setEjercicioId(String ejercicioId) {
		this.ejercicioId = ejercicioId;
	}



	public String getCcostoId() {
		return ccostoId;
	}



	public void setCcostoId(String ccostoId) {
		this.ccostoId = ccostoId;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getContratoId() {
		return contratoId;
	}



	public void setContratoId(String contratoId) {
		this.contratoId = contratoId;
	}



	public String getFechaRegistro() {
		return fechaRegistro;
	}



	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getNumHoras() {
		return numHoras;
	}



	public void setNumHoras(String numHoras) {
		this.numHoras = numHoras;
	}



	public String getBecaAdicional() {
		return becaAdicional;
	}



	public void setBecaAdicional(String becaAdicional) {
		this.becaAdicional = becaAdicional;
	}



	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");		
		cargaId			= rs.getString("CARGA_ID");
		ejercicioId		= rs.getString("EJERCICIO_ID");
		ccostoId		= rs.getString("CCOSTO_ID");
		descripcion		= rs.getString("DESCRIPCION");
		contratoId		= rs.getString("CONTRATO_ID");
		fechaRegistro	= rs.getString("FECHA_REGISTRO");
		status			= rs.getString("STATUS");
		numHoras		= rs.getString("NUM_HORAS");
		becaAdicional	= rs.getString("BECA_ADICIONAL");
	}	
	
	public void mapeaRegId(Connection con, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = con.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, EJERCICIO_ID, "+
			"CCOSTO_ID, DESCRIPCION, CONTRATO_ID, FECHA_REGISTRO, STATUS, NUM_HORAS, BECA_ADICIONAL "+	
			"FROM ENOC.ALUMNO_INFORME WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? "); 
		ps.setString(1,codigoPersonal);
		ps.setString(2,cargaId);
		rs = ps.executeQuery();
		
		if(rs.next()){
			mapeaReg(rs);
		}
		
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoInformeUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}