package aca.carga.spring;

public class CargaUnidadActividad {
	private String cursoCargaId;	
	private String actividadId;
	private String actividadNombre;
	private String comentario;
	private String valor;
	private String orden;
		
	public CargaUnidadActividad(){
	    cursoCargaId       = "";	
		actividadId        = "";
		actividadNombre    = "";
		comentario         = "";
		valor              = "";
		orden              = "";
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getActividadId() {
		return actividadId;
	}

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getActividadNombre() {
		return actividadNombre;
	}

	public void setActividadNombre(String actividadNombre) {
		this.actividadNombre = actividadNombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}