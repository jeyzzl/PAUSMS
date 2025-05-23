package aca.pron.spring;

public class PronUnidad {
	
	private String cursoCargaId;
	private String unidadId;
	private String aporte;
	private String orden;
	
	public PronUnidad() {
		cursoCargaId 	= "0";
		unidadId 		= "0";
		aporte 			= "-";
		orden 			= "-";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public String getAporte() {
		return aporte;
	}

	public void setAporte(String aporte) {
		this.aporte = aporte;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}


}
