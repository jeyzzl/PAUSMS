package aca.catalogo.spring;

public class CatPatrocinador {
	private String patrocinadorId;
	private String nombrePatrocinador;
	private String detalles;
	private String direccion;
	private String telefono;
	private String email;
	private String tipo;
	
	public CatPatrocinador() {
		patrocinadorId 		= "0";
		nombrePatrocinador 	= "-";
		detalles 			= "";
		direccion 			= "";
		telefono 			= "";
		email 				= "";
		tipo 				= "";
	}

	public String getPatrocinadorId() {
		return patrocinadorId;
	}
	public void setPatrocinadorId(String patrocinadorId) {
		this.patrocinadorId = patrocinadorId;
	}

	public String getNombrePatrocinador() {
		return nombrePatrocinador;
	}
	public void setNombrePatrocinador(String nombrePatrocinador) {
		this.nombrePatrocinador = nombrePatrocinador;
	}

	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
