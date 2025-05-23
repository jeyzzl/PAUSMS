package aca.emp.spring;

public class EmpDependiente {
	
	private String idEmpleado;
	private String idDependiente;	
	private String relacion;	
	private String folio;
	private String cotejado;
	
	public EmpDependiente(){
		idEmpleado	 	= "0";
		idDependiente 	= "0";
		folio          	= "0";		
		relacion      	= "-";		
		cotejado		= "N";
	}

	public String getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getIdDependiente() {
		return idDependiente;
	}

	public void setIdDependiente(String idDependiente) {
		this.idDependiente = idDependiente;
	}

	public String getRelacion() {
		return relacion;
	}

	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCotejado() {
		return cotejado;
	}

	public void setCotejado(String cotejado) {
		this.cotejado = cotejado;
	}
	
}