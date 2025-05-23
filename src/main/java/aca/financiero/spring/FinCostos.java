package aca.financiero.spring;

public class FinCostos {
	
	private String cargaId;
	private String internado;	
	private String comedor;
	private String matMex;
	private String matExt;
	private String pagare;
	
	public FinCostos() {
		cargaId 	= "";
		internado 	= "";	
		comedor 	= "";
		matMex 		= "";
		matExt 		= "";
		pagare 		= "";
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getInternado() {
		return internado;
	}

	public void setInternado(String internado) {
		this.internado = internado;
	}

	public String getComedor() {
		return comedor;
	}

	public void setComedor(String comedor) {
		this.comedor = comedor;
	}

	public String getMatMex() {
		return matMex;
	}

	public void setMatMex(String matMex) {
		this.matMex = matMex;
	}

	public String getMatExt() {
		return matExt;
	}

	public void setMatExt(String matExt) {
		this.matExt = matExt;
	}

	public String getPagare() {
		return pagare;
	}

	public void setPagare(String pagare) {
		this.pagare = pagare;
	}
}
