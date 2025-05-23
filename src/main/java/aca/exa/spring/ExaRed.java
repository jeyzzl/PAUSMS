package  aca.exa.spring;

public class ExaRed{
	private String redSocialId;	
	private String matricula;
	private String red;	
	private String fechaAct;
	private String eliminado;
	private String idRedSocial;
	
	public ExaRed(){
		redSocialId 		= "0";
		matricula			= "-";
		red 				= "-";
		fechaAct			= "-";
		eliminado			= "0";
		idRedSocial			= "-";
	}
	
	public String getRedSocialId() {
		return redSocialId;
	}

	public void setRedSocialId(String redSocialId) {
		this.redSocialId = redSocialId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
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

	public String getIdRedSocial() {
		return idRedSocial;
	}

	public void setIdRedSocial(String idRedSocial) {
		this.idRedSocial = idRedSocial;
	}
	
}