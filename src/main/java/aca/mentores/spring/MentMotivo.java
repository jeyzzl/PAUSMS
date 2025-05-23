/**
 * 
 */
package aca.mentores.spring;

/**
 * @author Elifo
 *
 */
public class MentMotivo {
	private String motivoId;
	private String motivoNombre;
	private String comentario;
	
	public MentMotivo(){
		motivoId		= "";
		motivoNombre	= "";
		comentario		= "";
	}
	
	public String getMotivoId() {
		return motivoId;
	}
	
	public void setMotivoId(String motivoId) {
		this.motivoId = motivoId;
	}
	
	public String getMotivoNombre() {
		return motivoNombre;
	}
	
	public void setMotivoNombre(String motivoNombre) {
		this.motivoNombre = motivoNombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
		
}