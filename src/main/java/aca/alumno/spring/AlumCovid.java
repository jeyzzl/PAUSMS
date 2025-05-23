package aca.alumno.spring;

public class AlumCovid {
	private String codigoPersonal;
	private String periodoId;
	private String fechaLlegada;
	private String vacuna;
	private String fechaVacuna;
	private String positivoCovid;
	private String fechaPositivo;
	private String sospechoso;
	private String fechaSospechoso;
	private String aislamiento;
	private String finAislamiento;
	private String usuarioAlta;
	private String fechaAlta;
	private String comentario;
	private String tipo;
	private String validado;
	
	public AlumCovid(){
		codigoPersonal		= "0";
		periodoId			= "";
		tipo				= "F";
		fechaLlegada		= "";
		vacuna				= "N";
		fechaVacuna			= "";
		sospechoso			= "N";
		fechaSospechoso		= "";
		positivoCovid		= "N";
		fechaPositivo		= "";
		aislamiento			= "N";
		finAislamiento		= "";
		usuarioAlta			= "";
		fechaAlta			= "";
		comentario			= "";
		validado 			= "N";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFechaLlegada() {
		return fechaLlegada;
	}
	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}
	
	public String getVacuna() {
		return vacuna;
	}
	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}
	
	public String getPositivoCovid() {
		return positivoCovid;
	}
	public void setPositivoCovid(String positivoCovid) {
		this.positivoCovid = positivoCovid;
	}
	
	public String getSospechoso() {
		return sospechoso;
	}
	public void setSospechoso(String sospechoso) {
		this.sospechoso = sospechoso;
	}
	
	public String getAislamiento() {
		return aislamiento;
	}
	public void setAislamiento(String aislamiento) {
		this.aislamiento = aislamiento;
	}
	
	public String getFinAislamiento() {
		return finAislamiento;
	}
	public void setFinAislamiento(String finAislamiento) {
		this.finAislamiento = finAislamiento;
	}
	
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getFechaPositivo() {
		return fechaPositivo;
	}
	public void setFechaPositivo(String fechaPositivo) {
		this.fechaPositivo = fechaPositivo;
	}

	public String getFechaVacuna() {
		return fechaVacuna;
	}
	public void setFechaVacuna(String fechaVacuna) {
		this.fechaVacuna = fechaVacuna;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFechaSospechoso(){
		return fechaSospechoso;
	}
	public void setFechaSospechoso(String fechaSospechoso){
		this.fechaSospechoso = fechaSospechoso;
	}

	public String getValidado() {
		return validado;
	}
	public void setValidado(String validado) {
		this.validado = validado;
	}
	
}