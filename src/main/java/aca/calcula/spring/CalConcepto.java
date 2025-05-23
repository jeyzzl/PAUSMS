package  aca.calcula.spring;

public class CalConcepto{
	
	private String conceptoId;
	private String conceptoNombre;	
	private String tipo;	
		
	public CalConcepto(){
		conceptoId			= "";
		conceptoNombre		= "-";	
		tipo				= "";	
	}
	
	public String getConceptoId() {
		return conceptoId;
	}
	public void setConceptoId(String conceptoId) {
		this.conceptoId = conceptoId;
	}
	public String getConceptoNombre() {
		return conceptoNombre;
	}
	public void setConceptoNombre(String conceptoNombre) {
		this.conceptoNombre = conceptoNombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
}