// Clase para la tabla de Acceso
package aca.acceso.spring;

public class Acceso{
	private String codigoPersonal;
	private String administrador;
	private String supervisor;
	private String cotejador;
	private String accesos;
	private String modalidad;
	private String usuario;
	private String clave;
	private String expira;
	private String ingreso;
	private String convalida;
	private String becas;
	private String portalAlumno;
	private String portalMaestro;
	private String idioma;
	private String menu;
	private String alumnoMovil;
	private String claveInicial;
	private String claveHexa;
	private String enLinea;
	private String buscaAdmin;
	
	// Constructor
	public Acceso(){		
		codigoPersonal	= "0";
		administrador	= "N";
		supervisor		= "N";
		cotejador		= "N";
		accesos			= "";
		modalidad		= "0";
		usuario  		= "-";
		clave    		= "";
		expira    		= "";
		ingreso    		= "";
		convalida 		= "N";
		becas			= "N";
		portalAlumno	= "N";
		portalMaestro	= "N";
		idioma			= "en";
		menu			= "1";
		alumnoMovil		= "N";
		claveInicial	= "-";
		claveHexa		= "X";
		enLinea 		= "N";
		buscaAdmin		= "N";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getAdministrador() {
		return administrador;
	}
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getCotejador() {
		return cotejador;
	}
	public void setCotejador(String cotejador) {
		this.cotejador = cotejador;
	}

	public String getAccesos() {
		return accesos;
	}
	public void setAccesos(String accesos) {
		this.accesos = accesos;
	}

	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public String getExpira() {
		return expira;
	}
	public void setExpira(String expira) {
		this.expira = expira;
	}

	public String getIngreso() {
		return ingreso;
	}
	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	public String getConvalida() {
		return convalida;
	}
	public void setConvalida(String convalida) {
		this.convalida = convalida;
	}

	public String getBecas() {
		return becas;
	}
	public void setBecas(String becas) {
		this.becas = becas;
	}

	public String getPortalAlumno() {
		return portalAlumno;
	}
	public void setPortalAlumno(String portalAlumno) {
		this.portalAlumno = portalAlumno;
	}

	public String getPortalMaestro() {
		return portalMaestro;
	}
	public void setPortalMaestro(String portalMaestro) {
		this.portalMaestro = portalMaestro;
	}

	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getAlumnoMovil() {
		return alumnoMovil;
	}
	public void setAlumnoMovil(String alumnoMovil) {
		this.alumnoMovil = alumnoMovil;
	}

	public String getClaveInicial() {
		return claveInicial;
	}
	public void setClaveInicial(String claveInicial) {
		this.claveInicial = claveInicial;
	}

	public String getClaveHexa() {
		return claveHexa;
	}
	public void setClaveHexa(String claveHexa) {
		this.claveHexa = claveHexa;
	}

	public String getEnLinea() {
		return enLinea;
	}
	public void setEnLinea(String enLinea) {
		this.enLinea = enLinea;
	}

	public String getBuscaAdmin() {
		return buscaAdmin;
	}

	public void setBuscaAdmin(String buscaAdmin) {
		this.buscaAdmin = buscaAdmin;
	}	
}