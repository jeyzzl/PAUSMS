package aca.archivo.spring;

public class ArchRevalida {
	
	private String codigoPersonal;
	private String revalida;
	private String fechaRevalida;	
	
	public ArchRevalida() {
		codigoPersonal 	= "";
		revalida 		= "N";
		fechaRevalida 	= "";		
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getRevalida() {
		return revalida;
	}

	public void setRevalida(String revalida) {
		this.revalida = revalida;
	}

	public String getFechaRevalida() {
		return fechaRevalida;
	}

	public void setFechaRevalida(String fechaRevalida) {
		this.fechaRevalida = fechaRevalida;
	}
}
