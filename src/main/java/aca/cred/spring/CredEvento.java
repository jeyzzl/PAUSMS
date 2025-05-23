package aca.cred.spring;


public class CredEvento {
	private String eventoId;	
	private String eventoNombre;
	private String codigoInicial;	

	
	public CredEvento(){
		eventoId 				= "";
		eventoNombre  			= "";
		codigoInicial	  		= "";	
	}	
	
	
	public String getEventoId() {
		return eventoId;
	}


	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}


	public String getEventoNombre() {
		return eventoNombre;
	}


	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}


	public String getCodigoInicial() {
		return codigoInicial;
	}


	public void setCodigoInicial(String codigoInicial) {
		this.codigoInicial = codigoInicial;
	}

}
  