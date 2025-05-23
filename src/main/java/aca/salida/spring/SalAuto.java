package aca.salida.spring;

public class SalAuto {
	private String salidaId;
	private String folio;
	private String tipo;
	private String poliza;
	private String telefono;
	private byte[] imagen;
	
	public SalAuto(){
		salidaId	= "";
		folio		= "0"; 
		tipo		= "";
		poliza		= "";
		telefono	= "";
		imagen		= null;
	}

	public String getSalidaId() {
		return salidaId;
	}

	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPoliza() {
		return poliza;
	}

	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}