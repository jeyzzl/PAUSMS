package aca.leg.spring;

public class LegPermisos {
	private String codigo;
	private String folio;
	private String usuarioAlta;	
	private String usuarioBaja;	
	private String fechaIni;
	private String fechaLim;
	private String status;
	
	public LegPermisos(){
		codigo			= "0";
		folio 			= "0";
		usuarioAlta		= "0";	
		usuarioBaja		= "0";	
		fechaIni		= aca.util.Fecha.getHoy();	
		fechaLim 		= aca.util.Fecha.getHoy();
		status 			= "A";		
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getUsuarioBaja() {
		return usuarioBaja;
	}

	public void setUsuarioBaja(String usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaLim() {
		return fechaLim;
	}

	public void setFechaLim(String fechaLim) {
		this.fechaLim = fechaLim;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}