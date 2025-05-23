package aca.bitacora.spring;

public class BitRequisito {
	
	private String requisitoId;
	private String nombre;
	private String tipo;
	private String comentario;
	
	public BitRequisito(){
		requisitoId = "0";
		nombre 		= "-";
		tipo 		= "M";
		comentario	= "-";
	}

	public String getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
