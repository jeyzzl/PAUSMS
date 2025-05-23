// Bean del Catalogo Curso
package  aca.plan.spring;

public class MapaMayorMenor{
	
	private String folio;
	private String planId;
	private String tipo;
	private String porDefecto;
	private String nombre;
	
	public MapaMayorMenor(){
		folio		= "0";
		planId		= "0";
		tipo 		= "MA";
		porDefecto 	= "N";
		nombre		= "";	
	}

	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPorDefecto() {
		return porDefecto;
	}
	public void setPorDefecto(String porDefecto) {
		this.porDefecto = porDefecto;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}