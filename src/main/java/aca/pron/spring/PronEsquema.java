package aca.pron.spring;

public class PronEsquema {
	
	private String cursoCargaId;
	private String estrategiaId;
	private String estrategiaNombre;
	private String valor;
	private String tipo;
	private String orden;
	
	public PronEsquema() {
		cursoCargaId 		= "0";
		estrategiaId 		= "0";
		estrategiaNombre 	= "-";
		valor 				= "0";
		tipo 				= "-";
		orden 				= "0";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEstrategiaId() {
		return estrategiaId;
	}

	public void setEstrategiaId(String estrategiaId) {
		this.estrategiaId = estrategiaId;
	}

	public String getEstrategiaNombre() {
		return estrategiaNombre;
	}

	public void setEstrategiaNombre(String estrategiaNombre) {
		this.estrategiaNombre = estrategiaNombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}
