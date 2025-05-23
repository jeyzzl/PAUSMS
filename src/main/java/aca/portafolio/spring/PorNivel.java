package aca.portafolio.spring;

public class PorNivel {

	private String nivelId;
	private String nivelNombre;	
	private String archivo;
	private String documento_id;
	
	public PorNivel(){
		nivelId 		= "";
		nivelNombre		= "";		
		archivo			= "";
		documento_id 	= "";
	}
	
	public String getNivelId() {
		return nivelId;
	}

	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	public String getNivelNombre() {
		return nivelNombre;
	}

	public void setNivelNombre(String nivelNombre) {
		this.nivelNombre = nivelNombre;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getDocumento_id() {
		return documento_id;
	}

	public void setDocumento_id(String documento_id) {
		this.documento_id = documento_id;
	}

}