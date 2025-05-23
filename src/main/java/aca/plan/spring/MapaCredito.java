// Bean del Catalogo Curso
package  aca.plan.spring;

public class MapaCredito{
	
	private String planId;
	private String ciclo;
	private String creditos;
	private String optativos;
	private String titulo;
	private String estado;
	
	public MapaCredito(){
		planId			= "";
		ciclo			= "";
		creditos		= "0";
		optativos		= "0";
		titulo			= "";
		estado			= "O";
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the ciclo.
	 */
	public String getCiclo() {
		return ciclo;
	}

	/**
	 * @param ciclo The ciclo to set.
	 */
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	/**
	 * @return Returns the creditos.
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos The creditos to set.
	 */
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return Returns the planId.
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId The planId to set.
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	/**
	 * @return Returns the optativos.
	 */
	public String getOptativos() {
		return optativos;
	}
	
	/**
	 * @param optativos The optativos to set.
	 */	
	public void setOptativos(String optativos) {
		this.optativos = optativos;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
		
}