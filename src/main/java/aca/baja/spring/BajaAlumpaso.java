package aca.baja.spring;

public class BajaAlumpaso {
	private String bajaId;
	private String pasoId;
	private String fecha;
	private String estado;
	
	public BajaAlumpaso(){
		bajaId	= "";
		pasoId	= "";
		fecha	= "";
		estado	= "";
	}

	/**
	 * @return the bajaId
	 */
	public String getBajaId() {
		return bajaId;
	}

	/**
	 * @param bajaId the bajaId to set
	 */
	public void setBajaId(String bajaId) {
		this.bajaId = bajaId;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the pasoId
	 */
	public String getPasoId() {
		return pasoId;
	}

	/**
	 * @param pasoId the pasoId to set
	 */
	public void setPasoId(String pasoId) {
		this.pasoId = pasoId;
	}
		
}