// Bean de Estado del alumno en el procesos de matrxcula( Por cada bloque).
package  aca.objeto;

import java.sql.*;

public class Beca{
	private String codigoPersonal;
	private String nombre;	
	private String cargaId;
	private String bloqueId;
	private String importedos;
	private String tipoplaza;
	private String ccostoId;
	private String importe;
	private String tipoBeca;
	private String nombreBeca;
	private String beca;
	private String beca1;
	private String beca2;
	private String beca3;
	private String carreraId;
	private String facultadId;
	
	
	public Beca(){
		codigoPersonal			= "";
		nombre					= "";
		cargaId					= "";
		bloqueId				= "";
		importedos				= "";
		tipoplaza				= "";
		ccostoId				= "";
		importe					= "";
		tipoBeca				= "";
		nombreBeca				= "";
		beca					= "";
		beca1					= "";
		beca2					= "";
		beca3					= "";
		carreraId				= "";
		facultadId				= "";
	}	


	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	
	public String getBloqueId() {
		return bloqueId;
	}
	
	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}


	public String getImportedos() {
		return importedos;
	}

	public void setImportedos(String importedos) {
		this.importedos = importedos;
	}

	public String getTipoplaza() {
		return tipoplaza;
	}

	public void setTipoplaza(String tipoplaza) {
		this.tipoplaza = tipoplaza;
	}

	public String getCcostoId() {
		return ccostoId;
	}

	public void setCcostoId(String ccostoId) {
		this.ccostoId = ccostoId;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getTipoBeca() {
		return tipoBeca;
	}

	public void setTipoBeca(String tipoBeca) {
		this.tipoBeca = tipoBeca;
	}

	public String getNombreBeca() {
		return nombreBeca;
	}

	public void setNombreBeca(String nombreBeca) {
		this.nombreBeca = nombreBeca;
	}

	public String getBeca() {
		return beca;
	}

	public void setBeca(String beca) {
		this.beca = beca;
	}

	public String getBeca1() {
		return beca1;
	}

	public void setBeca1(String beca1) {
		this.beca1 = beca1;
	}

	public String getBeca2() {
		return beca2;
	}

	public void setBeca2(String beca2) {
		this.beca2 = beca2;
	}

	public String getBeca3() {
		return beca3;
	}

	public void setBeca3(String beca3) {
		this.beca3 = beca3;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("MATRICULA");
		nombre				= rs.getString("NOMBRE");		
		cargaId				= rs.getString("CARGA_ID");
		bloqueId			= rs.getString("BLOQUE_ID");
		importedos	 		= rs.getString("IMPORTEDOS");	
		tipoplaza	 		= rs.getString("TIPOPLAZA");	
		ccostoId	 		= rs.getString("CCOSTO_ID");	
		importe	 			= rs.getString("IMPORTE");	
		tipoBeca	 		= rs.getString("TIPO_BECA");	
		nombreBeca	 		= rs.getString("NOMBRE_BECA");
		beca		 		= rs.getString("BECA");
		beca1	 			= rs.getString("BECA1");
		beca2	 			= rs.getString("BECA2");
		beca3	 			= rs.getString("BECA3");
		carreraId 			= rs.getString("CARRERA");
		facultadId 			= rs.getString("FACULTAD");
	}	
}