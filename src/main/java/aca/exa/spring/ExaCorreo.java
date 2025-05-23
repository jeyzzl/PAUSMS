package  aca.exa.spring;

public class ExaCorreo{
	private String correoId;	
	private String matricula;
	private String correo;	
	private String fechaAct;
	private String eliminado;
	private String idCorreo;
	
	public ExaCorreo(){
		correoId 			= "";
		matricula			= "";
		correo 				= "";
		fechaAct			= "";
		eliminado			= "";
		idCorreo			= "";
	}

	public String getCorreoId() {
		return correoId;
	}

	public void setCorreoId(String correoId) {
		this.correoId = correoId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFechaAct() {
		return fechaAct;
	}

	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(String idCorreo) {
		this.idCorreo = idCorreo;
	}
}