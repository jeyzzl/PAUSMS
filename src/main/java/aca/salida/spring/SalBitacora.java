package aca.salida.spring;

public class SalBitacora {

	private String salidaId;
	private String estado;
	private String fecha;
	
	public SalBitacora(){
		salidaId    = "";
		estado		= "";
		fecha  		= "";
	}

	public String getSalidaId() {
		return salidaId;
	}

	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}