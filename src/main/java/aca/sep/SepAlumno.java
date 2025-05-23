package aca.sep;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SepAlumno {
	
	private String plantel;
	private String planSep;
	private String ciclo;
	private String curp;
	private String nombre;
	private String paterno;
	private String materno;
	private String fecha;
	private String codigoPersonal;
	private String planUm;
	private String genero;
	private String edad;
	private String grado;
	private String paisId;
	private String estadoId;
	private String prepaLugar;
	private String usado;
	
	public SepAlumno() {
		plantel 		= "";
		planSep 		= "";
		ciclo 			= "";
		curp 			= "";
		nombre 			= "";
		paterno 		= "";
		materno 		= "";
		fecha 			= "";
		codigoPersonal  = "";
		planUm 			= "";
		genero 			= "";
		edad 			= "";
		grado 			= "";
		paisId 			= "";
		estadoId 		= "";
		prepaLugar	 	= "";
		usado 			= "";
	}

	public String getPlantel() {
		return plantel;
	}

	public void setPlantel(String plantel) {
		this.plantel = plantel;
	}

	public String getPlanSep() {
		return planSep;
	}

	public void setPlan_sep(String planSep) {
		this.planSep = planSep;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanUm() {
		return planUm;
	}

	public void setPlanUm(String planUm) {
		this.planUm = planUm;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPais_id(String paisId) {
		this.paisId = paisId;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstado_id(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getPrepaLugar() {
		return prepaLugar;
	}

	public void setPrepaLugar(String prepaLugar) {
		this.prepaLugar = prepaLugar;
	}

	public String getUsado() {
		return usado;
	}

	public void setUsado(String usado) {
		this.usado = usado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		plantel 		= rs.getString("PLANTEL");
		planSep 		= rs.getString("PLAN_SEP");
		ciclo 			= rs.getString("CICLO");
		curp 			= rs.getString("CURP");
		nombre 			= rs.getString("NOMBRE");
		paterno 		= rs.getString("PATERNO");
		materno 		= rs.getString("MATERNO");
		fecha 			= rs.getString("FECHA");
		codigoPersonal = rs.getString("CODIGO_PERSONAL");
		planUm 		= rs.getString("PLAN_UM");
		genero 			= rs.getString("GENERO");
		edad 			= rs.getString("EDAD");
		grado 			= rs.getString("GRADO");
		paisId 		= rs.getString("PAIS_ID");
		estadoId 		= rs.getString("ESTADO_ID");
		prepaLugar 	= rs.getString("PREPA_LUGAR");
		usado 			= rs.getString("USADO");
	}

}
