package aca.idioma.spring;

public class MsgEsp {

	private String clave;
	private String valor;
	
	public MsgEsp(){
		clave 	= "";
		valor 	= "";
	}
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}	
}
