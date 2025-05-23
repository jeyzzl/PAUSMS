package aca.alumno.spring;

public class AlumDias {
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String dias;
	
	public AlumDias(){
		codigoPersonal	= "0";
		cargaId 		= "0";
		bloqueId		= "0";
		dias			= "0";
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

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}
}