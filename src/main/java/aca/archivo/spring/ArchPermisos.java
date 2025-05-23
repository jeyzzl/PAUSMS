//Beans de la tabla ARCH_PERMISOS
package aca.archivo.spring;

public class ArchPermisos {
    private String matricula;
    private String folio;
	private String usuarioAlta;
	private String usuarioBaja;
	private String fechaIni;
	private String fechaLim;
	private String estado;	
	private String comentario;
	private String planId;
	
	public ArchPermisos(){
		matricula 	= "0";
		folio		= "0";
		usuarioAlta = "0";
		usuarioBaja = "0";
		fechaIni 	= aca.util.Fecha.getHoy();
		fechaLim 	= aca.util.Fecha.getHoy();
		estado 		= "A";		
		comentario  = "-";
		planId		= "XXXXXXXX";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
}