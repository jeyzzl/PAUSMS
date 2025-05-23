package aca.baja.spring;

public class BajaAlumno {
	private String bajaId;
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String carreraId;
	private String planId;
	private String fInicio;
	private String fBaja;
	private String comentario;
	private String razonId;
	private String creditos;
	
	public BajaAlumno(){
		bajaId			= "0";
		codigoPersonal	= "0";
		cargaId			= "0";
		bloqueId		= "0";
		carreraId		= "0";
		planId			= "0";
		fInicio			= aca.util.Fecha.getHoy();
		fBaja			= aca.util.Fecha.getHoy();
		comentario		= "-";
		razonId			= "0";
		creditos		= "0";
	}

	public String getBajaId() {
		return bajaId;
	}

	public void setBajaId(String bajaId) {
		this.bajaId = bajaId;
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

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getfInicio() {
		return fInicio;
	}

	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfBaja() {
		return fBaja;
	}

	public void setfBaja(String fBaja) {
		this.fBaja = fBaja;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getRazonId() {
		return razonId;
	}

	public void setRazonId(String razonId) {
		this.razonId = razonId;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}
	
}