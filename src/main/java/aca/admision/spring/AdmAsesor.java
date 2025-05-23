package aca.admision.spring;

public class AdmAsesor {
	private String asesorId;	
	private String correo;
	private String chat;
	private String telefono;
	private String estado;
	
	public AdmAsesor(){
		asesorId 	= "0";
		correo		= "-";
		chat		= "-";
		telefono	= "-";
		estado 		= "A";
	}
	
	public String getAsesorId() {
		return asesorId;
	}

	public void setAsesorId(String asesorId) {
		this.asesorId = asesorId;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}