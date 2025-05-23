package aca.investiga.spring;

public class InvResultado {
	private String proyectoId;
	private String infraestructura;
	private String bibliografia;
	
	public InvResultado(){
		proyectoId		= "";
		infraestructura = "-";
		bibliografia	= "-";
	}

	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getInfraestructura() {
		return infraestructura;
	}
	public void setInfraestructura(String infraestructura) {
		this.infraestructura = infraestructura;
	}
	public String getBibliografia() {
		return bibliografia;
	}
	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}	
}
