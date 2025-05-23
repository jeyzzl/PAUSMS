package aca.residencia.spring;

public class ResDocumento {
	private String folio;
	private String descripcion;
	private byte[] documento;
	private String folioExpediente;
	private String nombre;
	
	public ResDocumento(){
		folio 			= "0";
		descripcion 	= "";
		documento		= null;
		folioExpediente	= "0";
		nombre			= "-";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getFolioExpediente() {
		return folioExpediente;
	}

	public void setFolioExpediente(String folioExpediente) {
		this.folioExpediente = folioExpediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}