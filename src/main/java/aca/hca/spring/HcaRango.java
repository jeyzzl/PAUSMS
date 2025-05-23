/**
 * 
 */
package aca.hca.spring;
/**
 * @author etorres
 *
 */
public class HcaRango {
	private String nivelId;
	private String modalidadId;
	private String rangoId;
	private String rangoIni;
	private String rangoFin;
	private String valor;
	
	public HcaRango(){
		nivelId			= "";
		modalidadId		= "";
		rangoId			= "";
		rangoIni		= "";
		rangoFin		= "";
		valor			= "";
	}

	public String getNivelId() {
		return nivelId;
	}
	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	public String getModalidadId() {
		return modalidadId;
	}
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getRangoId() {
		return rangoId;
	}
	public void setRangoId(String rangoId) {
		this.rangoId = rangoId;
	}

	public String getRangoIni() {
		return rangoIni;
	}
	public void setRangoIni(String rangoIni) {
		this.rangoIni = rangoIni;
	}

	public String getRangoFin() {
		return rangoFin;
	}
	public void setRangoFin(String rangoFin) {
		this.rangoFin = rangoFin;
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}	
}