package aca.financiero.spring;

public class ContEjercicio {
	private String idEjercicio;
	private String nombre;	
	private String status;
	private String mascBalance;
	private String mascAuxiliar;
	private String mascCcosto;
	private String nivelContable;
	private String nivelTauxiliar;
	
	// Constructor
	public ContEjercicio(){		
		
		idEjercicio		= "";
		nombre			= "";	
		status			= "";
		mascBalance		= "";
		mascAuxiliar	= "";
		mascCcosto		= "";
		nivelContable	= "";
		nivelTauxiliar	= "";
		
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMascBalance() {
		return mascBalance;
	}

	public void setMascBalance(String mascBalance) {
		this.mascBalance = mascBalance;
	}

	public String getMascAuxiliar() {
		return mascAuxiliar;
	}

	public void setMascAuxiliar(String mascAuxiliar) {
		this.mascAuxiliar = mascAuxiliar;
	}

	public String getMascCcosto() {
		return mascCcosto;
	}

	public void setMascCcosto(String mascCcosto) {
		this.mascCcosto = mascCcosto;
	}

	public String getNivelContable() {
		return nivelContable;
	}

	public void setNivelContable(String nivelContable) {
		this.nivelContable = nivelContable;
	}

	public String getNivelTauxiliar() {
		return nivelTauxiliar;
	}

	public void setNivelTauxiliar(String nivelTauxiliar) {
		this.nivelTauxiliar = nivelTauxiliar;
	}	
}