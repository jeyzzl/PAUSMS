package aca.leg.spring;

public class LegCondiciones {
	
	public String grupo;
	public String idDocumento;
	public String validaFecha;

	public LegCondiciones() {
		grupo			= "";
		idDocumento		= "";
		validaFecha		= "";
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getValidaFecha() {
		return validaFecha;
	}

	public void setValidaFecha(String validaFecha) {
		this.validaFecha = validaFecha;
	}
	
}
