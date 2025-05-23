// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpDocumento{
	private String documentoId;
	private String documentoNombre;	
	private String orden;
	
	public EmpDocumento(){
		documentoId 	= "0";
		documentoNombre	= "-";
		orden 			= "";
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public String getDocumentoNombre() {
		return documentoNombre;
	}

	public void setDocumentoNombre(String documentoNombre) {
		this.documentoNombre = documentoNombre;
	}
	
	public String getOrden() {
		return orden;
	}
	
	public void setOrden(String orden) {
		this.orden = orden;
	}	
}