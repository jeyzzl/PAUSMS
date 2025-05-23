package aca.alerta.spring;

public class AlertaDatos{
	
	private String periodoId		= "";
	private String codigoPersonal	= "";
	private String fecha			= "";
	private String direccion		= "";
	private String procedencia		= "";
	private String correo			= "";
	private String celular			= "";
	private String sintomas			= "";
	private String usuario			= "";
	private String lugar1			= "";
	private String lugar2			= "";
	private String estado			= "";
	private String otro				= "";
	private String referente		= "";
	
	
	public AlertaDatos(){
		
		periodoId		= "";
		codigoPersonal	= "";
		fecha			= "";
		direccion		= "";
		procedencia		= "";
		correo			= "";
		celular			= "";
		sintomas		= "";
		usuario			= "";
		lugar1			= "";
		lugar2			= "";
		estado			= "";
		otro			= "";
		referente		= "";
	}


	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}


	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getSintomas() {
		return sintomas;
	}

	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getLugar1() {
		return lugar1;
	}

	public void setLugar1(String lugar1) {
		this.lugar1 = lugar1;
	}

	public String getLugar2() {
		return lugar2;
	}

	public void setLugar2(String lugar2) {
		this.lugar2 = lugar2;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getOtro() {
		return otro;
	}

	public void setOtro(String otro) {
		this.otro = otro;
	}
	
	public String getReferente() {
		return referente;
	}

	public void setReferente(String referente) {
		this.referente = referente;
	}

}