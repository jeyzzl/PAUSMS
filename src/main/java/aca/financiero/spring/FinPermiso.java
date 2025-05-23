/**
 * 
 */
package aca.financiero.spring;

public class FinPermiso {
	private String codigoPersonal;
	private String folio;
	private String fInicio;
	private String fLimite;
	private String usuario;
	private String comentario;
	
	public FinPermiso(){
		codigoPersonal	= "0";
		folio			= "1";
		fInicio			= "";
		fLimite			= "";
		usuario			= "0";
		comentario		= "-";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFInicio() {
		return fInicio;
	}

	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	public String getFLimite() {
		return fLimite;
	}

	public void setFLimite(String limite) {
		fLimite = limite;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}