package aca.admision.spring;

public class AdmCartaFulton {
	
	private String cartaId;
	private String fechaRegistro;
	private String fechaOrientacion;
	private String fechaInicio;
	private String fechaCierre;
	private String fechaArribo;
	
	public AdmCartaFulton() {
		fechaRegistro 		= "";
		fechaOrientacion 	= "";
		fechaInicio 		= "";
		fechaCierre 		= "";
		cartaId 			= "";
		fechaArribo 		= "";
	}

	public String getCartaId() {
		return cartaId;
	}
	public void setCartaId(String cartaId) {
		this.cartaId = cartaId;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFechaOrientacion() {
		return fechaOrientacion;
	}
	public void setFechaOrientacion(String fechaOrientacion) {
		this.fechaOrientacion = fechaOrientacion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getFechaArribo() {
		return fechaArribo;
	}
	public void setFechaArribo(String fechaArribo) {
		this.fechaArribo = fechaArribo;
	}
	
}
