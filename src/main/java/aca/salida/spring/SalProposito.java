package aca.salida.spring;

public class SalProposito {

	private String propositoId;
	private String propositoNombre;
	
	public SalProposito(){
		propositoId    		= "0";
		propositoNombre		= "-";
	}

	public String getPropositoId() {
		return propositoId;
	}

	public void setPropositoId(String propositoId) {
		this.propositoId = propositoId;
	}

	public String getPropositoNombre() {
		return propositoNombre;
	}

	public void setPropositoNombre(String propositoNombre) {
		this.propositoNombre = propositoNombre;
	}
	
}