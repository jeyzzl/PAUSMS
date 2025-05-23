package aca.salida.spring;

public class SalGrupo {
	private String grupoId;
	private String grupoNombre;
	private String responsable;
	private String correo;
	private String usuarios;
	
	public SalGrupo(){
		grupoId     = "0";
		grupoNombre = "";
		responsable = "";
		correo		= "";
		usuarios	= "";
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getGrupoNombre() {
		return grupoNombre;
	}

	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}
	
}