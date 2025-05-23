package aca.leg.spring;


public class LegExtranjero {
	private String codigo;
	private String rne;	
	private String carrera;	
	private String comentario;
	private String telefono;
	
	public LegExtranjero(){
		codigo		= "0";		
		rne			= "-";	
		carrera		= "0";	
		comentario	= "-";	
		telefono 	= "-";
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRne() {
		return rne;
	}

	public void setRne(String rne) {
		this.rne = rne;
	}
	
}