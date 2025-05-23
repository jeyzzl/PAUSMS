package aca.emp.spring;

public class EmpContrato {
	private String contratoId;
	private String codigoPersonal;
	private String fecha;
	private String costo;
	private String comentario;
	private String estado;
	private String fechaIni;
	private String fechaFin;
	private String institucion;
	private String firma;
	
	public EmpContrato(){
		contratoId		= "0";
		codigoPersonal	= "0";
		fecha			= aca.util.Fecha.getHoy();
		costo			= "0";
		comentario		= "-";
		estado			= "A";
		fechaIni		= aca.util.Fecha.getHoy();
		fechaFin		= aca.util.Fecha.getHoy();
		institucion		= "-";
		firma			= "N";
	}

	public String getContratoId() {
		return contratoId;
	}
	public void setContratoId(String contratoId) {
		this.contratoId = contratoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getCosto() {
		return costo;
	}
	public void setCosto(String costo) {
		this.costo = costo;
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
	
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}	
}