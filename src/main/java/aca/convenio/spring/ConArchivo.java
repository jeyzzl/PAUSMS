package aca.convenio.spring;

public class ConArchivo{
	
	private String convenioId;
	private String folio;
	private String nombre;
	private byte[] archivo;
			
	public ConArchivo(){
		convenioId 			= "";
		folio				= "";
		nombre				= "";
		archivo				= null;
	}

	public String getConvenioId() {
		return convenioId;
	}

	public void setConvenioId(String convenioId) {
		this.convenioId = convenioId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
}