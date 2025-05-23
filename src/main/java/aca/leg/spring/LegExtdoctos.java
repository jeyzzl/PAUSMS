package aca.leg.spring;

public class LegExtdoctos {
	public String codigo;
	public String idDocumento;
	public String fechaVence;
	public String numDocto;
	public String fecha;
	public String fechaTramite;
	public String estado;
	
	public LegExtdoctos(){
		codigo			= "";
		idDocumento		= "";
		fechaVence		= "";
		numDocto		= "";
		fecha			= "";
		fechaTramite	= "";
		estado 			= "";		
	}

	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getFechaVence() {
		return fechaVence;
	}

	public void setFechaVence(String fechaVence) {
		this.fechaVence = fechaVence;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}		
	
	public String getNumDocto() {
		return numDocto;
	}

	public void setNumDocto(String numDocto) {
		this.numDocto = numDocto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaTramite() {
		return fechaTramite;
	}

	public void setFechaTramite(String fechaTramite) {
		this.fechaTramite = fechaTramite;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}