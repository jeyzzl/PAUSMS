// Clase para la vista CARGA_ACADEMICA
package  aca.vista;

import java.sql.*;

public class Inscritos{
	private String cargaId;
	private String bloqueId;
	private String codigoPersonal;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreLegal;
	private String cotejado;
	private String fNacimiento;
	private String sexo;
	private String estadoCivil;
	private String religionId;	
	private String paisId;
	private String estadoId;	
	private String ciudadId;
	private String nacionalidad;
	private String curp;	
	private String modalidadId;
	private String clasFin;
	private String residenciaId;
	private String dormitorio;
	private String planId;
	private String carreraId;
	private String saldo;
	private String fInscripcion;
	private String tipoAlumnoId;
	
	public Inscritos(){
		cargaId			= "";
		bloqueId		= "";
		codigoPersonal	= "";
		nombre			= "";
		apellidoPaterno	= "";
		apellidoMaterno	= "";
		nombreLegal		= "";
		cotejado		= "";
		fNacimiento		= "";
		sexo			= "";
		estadoCivil		= "";
		religionId		= "";
		paisId			= "";
		estadoId		= "";	
		ciudadId		= "";
		nacionalidad	= "";
		curp			= "";		
		modalidadId		= "";
		clasFin			= "";
		residenciaId	= "";
		dormitorio		= "";
		planId			= "";
		carreraId		= "";
		saldo			= "";
		fInscripcion	= "";
		tipoAlumnoId	= "";
	}
	
	/**
	 * @return the saldo
	 */
	public String getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getCargaId(){
		return cargaId;
	}
		
	public String getBloqueId(){
		return bloqueId;
	}	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
		
	public String getNombre(){
		return nombre;
	}			
	public String getApellidoPaterno(){
		return apellidoPaterno;
	}	
		
	public String getApellidoMaterno(){
		return apellidoMaterno;
	}	
		
	public String getNombreLegal(){
		return nombreLegal;
	}
	
	public String getCotejado(){
		return cotejado;
	}	
		
	public String getFNacimiento(){
		return fNacimiento;
	}	
		
	public String getSexo(){
		return sexo;
	}
	
	public String getEstadoCivil(){
		return estadoCivil;
	}
		
	public String getReligionId(){
		return religionId;
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public String getCiudadId(){
		return ciudadId;
	}
	
	public String getNacionalidad(){
		return nacionalidad;
	}
	
	public String getCurp(){
		return curp;
	}
	
	public String getModalidadId(){
		return modalidadId;
	}
	
	public String getClasFin(){
		return clasFin;
	}
	
	public String getResidenciaId(){
		return residenciaId;
	}
	
	public String getPlanId(){
		return planId;
	}
	
	public String getCarreraId(){
		return carreraId;
	}	
		
	public String getDormitorio() {
		return dormitorio;
	}

	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	

	public String getfInscripcion() {
		return fInscripcion;
	}

	public void setfInscripcion(String fInscripcion) {
		this.fInscripcion = fInscripcion;
	}

	/**
	 * @return the tipoAlumnoId
	 */
	public String getTipoAlumnoId() {
		return tipoAlumnoId;
	}

	/**
	 * @param tipoAlumnoId the tipoAlumnoId to set
	 */
	public void setTipoAlumnoId(String tipoAlumnoId) {
		this.tipoAlumnoId = tipoAlumnoId;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cargaId				= rs.getString("CARGA_ID");
		bloqueId			= rs.getString("BLOQUE_ID");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		nombre				= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
		nombreLegal 		= rs.getString("NOMBRE_LEGAL");
		cotejado			= rs.getString("COTEJADO");
		fNacimiento 		= rs.getString("F_NACIMIENTO");
		sexo 				= rs.getString("SEXO");
		estadoCivil			= rs.getString("ESTADO_CIVIL");
		religionId 			= rs.getString("RELIGION_ID");		
		paisId 				= rs.getString("PAIS_ID");
		estadoId 			= rs.getString("ESTADO_ID");
		ciudadId 			= rs.getString("CIUDAD_ID");
		nacionalidad 		= rs.getString("NACIONALIDAD");
		curp				= rs.getString("CURP");		
		modalidadId 		= rs.getString("MODALIDAD_ID");
		clasFin 			= rs.getString("CLAS_FIN");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		dormitorio			= rs.getString("DORMITORIO");
		planId 				= rs.getString("PLAN_ID");
		carreraId			= rs.getString("CARRERA_ID");
		saldo				= rs.getString("SALDO");
		fInscripcion		= rs.getString("F_INSCRIPCION");
		tipoAlumnoId 		= rs.getString("TIPOALUMNO_ID"); 
	}	
}