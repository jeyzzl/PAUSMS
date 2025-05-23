package aca.pron.spring;

public class PronMateria {
	
	private String cursoCargaId;
	private String cursoId;
	private String horaClase;	
	private String horaTutoria;
	private String lugar;
	private String formacion;
	private String correo;
	private String descripcion; 
	private String enfoque;
	private String especial;
	private String integridad;
	
	public PronMateria() {
		cursoCargaId 	= "0";
		cursoId 		= "0";
		horaClase 		= "-";
		horaTutoria 	= "-";
		lugar			= "-";
		formacion 		= "-";
		correo 			= "-";
		descripcion 	= "-";
		enfoque 		= "-";
		especial		= "-";
		integridad		= "-";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getHoraClase() {
		return horaClase;
	}

	public void setHoraClase(String horaClase) {
		this.horaClase = horaClase;
	}

	public String getHoraTutoria() {
		return horaTutoria;
	}

	public void setHoraTutoria(String horaTutoria) {
		this.horaTutoria = horaTutoria;
	}

	public String getFormacion() {
		return formacion;
	}

	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEnfoque() {
		return enfoque;
	}

	public void setEnfoque(String enfoque) {
		this.enfoque = enfoque;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getEspecial() {
		return especial;
	}

	public void setEspecial(String especial) {
		this.especial = especial;
	}

	public String getIntegridad() {
		return integridad;
	}

	public void setIntegridad(String integridad) {
		this.integridad = integridad;
	}
	
}
