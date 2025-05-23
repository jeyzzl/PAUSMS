package aca.carga.spring;

public class CargaPractica {
	
	private String cursoCargaId;
	private String folio;
	private String fechaIni;
	private String fechaFin;
	private String estado;
	
	public CargaPractica() {
		cursoCargaId 	= "0";;
		folio			= "0";
		fechaIni		= aca.util.Fecha.getHoy();
		fechaFin		= aca.util.Fecha.getHoy();
		estado			= "A";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
