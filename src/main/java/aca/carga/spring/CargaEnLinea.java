package aca.carga.spring;

public class CargaEnLinea {
	
	private String cargaId;
	private String nombre;
	private String descripcion;
	private String fInicio;
	private String fFinal;	
	private String estado;
	private String carta;
	
	public CargaEnLinea(){
		cargaId			= "";
		nombre			= "";
		descripcion		= "";
		fInicio			= "";
		fFinal			= "";
		estado			= "";
		carta			= "S";
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getfInicio() {
		return fInicio;
	}

	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}

	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
