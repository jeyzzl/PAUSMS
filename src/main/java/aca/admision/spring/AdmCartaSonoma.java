package aca.admision.spring;

public class AdmCartaSonoma {
	
	private String cartaId;
	private String fechaFinal;
	
	public AdmCartaSonoma() {
		cartaId 			= "";
		fechaFinal 			= "";
	}

	public String getCartaId() {
		return cartaId;
	}
	public void setCartaId(String cartaId) {
		this.cartaId = cartaId;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
}
