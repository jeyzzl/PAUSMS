// Bean de AdmFormato 
package  aca.admision.spring;

public class AdmFormato{
	private String formatoId;
	private String formatoNombre;
	private String archivo;
		
	
	public AdmFormato(){
		formatoId 		= "";
		formatoNombre 	= "";
		archivo			= "";
	}

	public String getFormatoId() {
		return formatoId;
	}
	
	public void setFormatoId(String formatoId) {
		this.formatoId = formatoId;
	}

	public String getFormatoNombre() {
		return formatoNombre;
	}

	public void setFormatoNombre(String formatoNombre) {
		this.formatoNombre = formatoNombre;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
}