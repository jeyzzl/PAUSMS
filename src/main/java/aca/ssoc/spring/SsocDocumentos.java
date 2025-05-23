/*
 * Created on 14/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc.spring;

public class SsocDocumentos {
	String documentoId 			= "";
    String documentoNombre		= "";
    String orden 				= "";
    String obligatorio			= "";
    String acceso				= "A";
    
	public String getDocumentoId() {
		return documentoId;
	}
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}
	public String getDocumentoNombre() {
		return documentoNombre;
	}
	public void setDocumentoNombre(String documentoNombre) {
		this.documentoNombre = documentoNombre;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}
	public String getAcceso() {
		return acceso;
	}
	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}    
	
}