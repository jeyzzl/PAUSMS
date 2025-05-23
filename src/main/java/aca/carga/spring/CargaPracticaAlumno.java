package aca.carga.spring;

public class CargaPracticaAlumno {
	
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String folio;
	private String fechaIni;
	private String fechaFin;
	private String comentario;
	private String estado;
	
	public CargaPracticaAlumno() {
		codigoPersonal	= "0";		
		folio			= "0";
		cargaId 		= "0";
		bloqueId		= "0";
		fechaIni		= aca.util.Fecha.getHoy();
		fechaFin		= aca.util.Fecha.getHoy();
		comentario		= "-";
		estado			= "A";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
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

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	
}
