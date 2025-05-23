/**
 * 
 */
package aca.hca.spring;

public class HcaMaestroEstado {
	private String codigoPersonal;
	private String cargaId;
	private String semanal;
	private String semestral;
	private String estado;
	
	public HcaMaestroEstado(){
		codigoPersonal	= "";
		cargaId			= "";
		semanal			= "";
		semestral		= "";
		estado			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getSemanal() {
		return semanal;
	}
	public void setSemanal(String tSemanal) {
		this.semanal = tSemanal;
	}

	public String getSemestral() {
		return semestral;
	}
	public void setSemestral(String tSemestral) {
		this.semestral = tSemestral;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
}