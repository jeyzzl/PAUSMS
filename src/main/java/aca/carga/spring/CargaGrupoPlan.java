package aca.carga.spring;

public class CargaGrupoPlan{
	private String cursoCargaId;
	private String estudios;
	private String ocupacion;
	private String lugar;
	private String horario;
	private String oficina;
	private String telefono;
	private String tiempo;
	private String atencion;
	private String correo;
	private String descripcion;
	private String perspectiva;
	private String estado;
	
	
	public CargaGrupoPlan(){
	    cursoCargaId  = "";	
		estudios      = "";
		ocupacion     = "";
		lugar         = "";
		horario       = "";
		oficina       = "";
		telefono      = "";
		tiempo        = "";
		atencion      = "";
	    correo	      = "";
		descripcion   = "";
		perspectiva   = "";
		estado        = "";
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEstudios() {
		return estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getAtencion() {
		return atencion;
	}

	public void setAtencion(String atencion) {
		this.atencion = atencion;
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

	public String getPerspectiva() {
		return perspectiva;
	}

	public void setPerspectiva(String perspectiva) {
		this.perspectiva = perspectiva;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}