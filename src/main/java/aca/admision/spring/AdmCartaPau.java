package aca.admision.spring;

public class AdmCartaPau {
	
	private String cartaId;
	private String fechaRegistro;
	private String fechaOrientacion;
	private String fechaApertura;
	private String fechaInicio;
	private String fechaCierre;
	
	public AdmCartaPau() {
		cartaId 			= "";
		fechaRegistro 		= "";
		fechaOrientacion 	= "";
		fechaApertura 		= "";
		fechaInicio 		= "";
		fechaCierre 		= "";
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

	public String getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
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

	public String getCartaId() {
		return cartaId;
	}
	public void setCartaId(String cartaId) {
		this.cartaId = cartaId;
	}	
	
}
