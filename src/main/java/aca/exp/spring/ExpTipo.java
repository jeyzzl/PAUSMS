package aca.exp.spring;

public class ExpTipo {
	private String codigoPersonal;
	private String tipo;
	private String estado;

	public ExpTipo(){
		codigoPersonal	= "0";
		tipo			= "-";
		estado 			= "-";
	}	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
}		