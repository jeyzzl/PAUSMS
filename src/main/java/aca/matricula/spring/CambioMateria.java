package aca.matricula.spring;

public class CambioMateria {
	
	private String solicitudId;
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String tipo;
	
	public CambioMateria() {
		solicitudId 	= "";
		codigoPersonal 	= "";
		cursoCargaId 	= "";
		cursoId 		= "";
		tipo 			= "";
	}
	
	public String getSolicitudId() {
		return solicitudId;
	}
	public void setSolicitudId(String solicitudId) {
		this.solicitudId = solicitudId;
	}
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getCursoCargaId() {
		return cursoCargaId;
	}
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}
	public String getCursoId() {
		return cursoId;
	}
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
