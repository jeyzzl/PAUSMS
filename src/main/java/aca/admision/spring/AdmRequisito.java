// Bean de AdmRequisitos 
package  aca.admision.spring;

public class AdmRequisito{
	private String carreraId;
	private String documentoId;
	private String modalidades;
	private String autorizar;
	private String requerido;
	
	public AdmRequisito(){
		carreraId 		= "";
		documentoId 	= "";
		modalidades		= "";
		autorizar		= "";
		requerido		= "S";	
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getAutorizar() {
		return autorizar;
	}

	public void setAutorizar(String autorizar) {
		this.autorizar = autorizar;
	}

	public String getRequerido() {
		return requerido;
	}

	public void setRequerido(String requerido) {
		this.requerido = requerido;
	}
	
}