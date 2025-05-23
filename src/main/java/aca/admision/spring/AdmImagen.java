package aca.admision.spring;

public class AdmImagen {
	
	private String folio;
	private String documentoId;
	private String hoja;
	private byte[] imagen;
	private String fecha;
	
	public AdmImagen(){
		folio		= "0";
		documentoId	= "0";
		hoja		= "1";
		imagen		= null;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public String getHoja() {
		return hoja;
	}

	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
