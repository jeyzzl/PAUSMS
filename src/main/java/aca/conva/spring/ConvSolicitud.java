package aca.conva.spring;

public class ConvSolicitud{
	
	private String codigoPersonal;
	private String cursoId;
	private String usuario;
	private String fecha;
	private String tipo;	
	private String procesoId;
	private String carrera;
	private String nota;
	private String materia_O;
	private String creditos_O;
	private String nota_O;
	
	public ConvSolicitud(){
		codigoPersonal		= "";
		cursoId				= "";
		usuario				= "";
		fecha				= "";
		tipo				= "";	
		procesoId			= "";
		carrera				= "";
		nota				= "";
		materia_O			= "";
		creditos_O			= "";
		nota_O				= "";
	}
	
	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(String procesoId) {
		this.procesoId = procesoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

		public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getCreditos_O() {
		return creditos_O;
	}

	public void setCreditos_O(String creditos_O) {
		this.creditos_O = creditos_O;
	}

	public String getMateria_O() {
		return materia_O;
	}

	public void setMateria_O(String materia_O) {
		this.materia_O = materia_O;
	}

	public String getNota_O() {
		return nota_O;
	}

	public void setNota_O(String nota_O) {
		this.nota_O = nota_O;
	}
	
}