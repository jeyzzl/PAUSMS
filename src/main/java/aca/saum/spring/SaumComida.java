// Bean del Catalogo de Roles
package  aca.saum.spring;

public class SaumComida{
	private String folio;	
	private String fecha;
	private String recetaId;
	private String comida;
	private String rendimiento;
	
	public SaumComida(){
		folio 			= "";
		fecha			= "";
		recetaId		= "";		
		comida			= "";
		rendimiento		= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getRecetaId() {
		return recetaId;
	}

	public void setRecetaId(String recetaId) {
		this.recetaId = recetaId;
	}

	public String getComida() {
		return comida;
	}

	public void setComida(String comida) {
		this.comida = comida;
	}

	public String getRendimiento() {
		return rendimiento;
	}

	public void setRendimiento(String rendimiento) {
		this.rendimiento = rendimiento;
	}	
	
}