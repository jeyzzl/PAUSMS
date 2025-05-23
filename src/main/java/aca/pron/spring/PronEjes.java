package aca.pron.spring;

public class PronEjes {
	
	private String cursoCargaId;
	private String fe;
	private String pensamiento;
	private String ambiente;
	private String liderazgo;
	private String emprendimiento;
	private String sustentabilidad; 
	private String servicio;
	private String investigacion;
	
	public PronEjes() {
		cursoCargaId 	= "0";
		fe 				= "";
		pensamiento 	= "";
		ambiente 		= "";
		liderazgo 		= "";
		emprendimiento 	= "";
		sustentabilidad = "";
		servicio 		= "";
		investigacion 	= "";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFe() {
		return fe;
	}

	public void setFe(String fe) {
		this.fe = fe;
	}

	public String getPensamiento() {
		return pensamiento;
	}

	public void setPensamiento(String pensamiento) {
		this.pensamiento = pensamiento;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getLiderazgo() {
		return liderazgo;
	}

	public void setLiderazgo(String liderazgo) {
		this.liderazgo = liderazgo;
	}

	public String getEmprendimiento() {
		return emprendimiento;
	}

	public void setEmprendimiento(String emprendimiento) {
		this.emprendimiento = emprendimiento;
	}

	public String getSustentabilidad() {
		return sustentabilidad;
	}

	public void setSustentabilidad(String sustentabilidad) {
		this.sustentabilidad = sustentabilidad;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getInvestigacion() {
		return investigacion;
	}

	public void setInvestigacion(String investigacion) {
		this.investigacion = investigacion;
	}

}
