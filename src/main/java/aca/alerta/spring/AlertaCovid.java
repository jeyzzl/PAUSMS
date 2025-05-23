package aca.alerta.spring;

public class AlertaCovid{
	
	private String codigoPersonal;
	private String fecha;
	private String fechaUno;
	private String fechaDos;
	private String paisUno;
	private String paisDos;
	private String ciudadUno;
	private String ciudadDos;
	private String contacto;
	private String contactoFecha;
	private String fiebre;
	private String tos;
	private String cabeza;
	private String respirar;
	private String garganta;
	private String escurrimiento;
	private String olfato;
	private String gusto;
	private String cuerpo;
	private String folio;
	
	public AlertaCovid(){
		
		codigoPersonal	= "0";
		fecha			= aca.util.Fecha.getHoy();
		fechaUno		= aca.util.Fecha.getHoy();
		fechaDos		= aca.util.Fecha.getHoy();
		paisUno			= "0";
		paisDos			= "0";
		ciudadUno		= "-";
		ciudadDos		= "-";
		contacto		= "N";
		contactoFecha	= "";
		fiebre			= "N";
		tos				= "N";
		cabeza			= "N";
		respirar		= "N";
		garganta		= "N";
		escurrimiento	= "N";
		olfato			= "N";
		gusto			= "N";
		cuerpo			= "N";
		folio			= "-";
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
	
	public String getFechaUno() {
		return fechaUno;
	}

	public void setFechaUno(String fechaUno) {
		this.fechaUno = fechaUno;
	}

	public String getFechaDos() {
		return fechaDos;
	}

	public void setFechaDos(String fechaDos) {
		this.fechaDos = fechaDos;
	}

	public String getPaisUno() {
		return paisUno;
	}

	public void setPaisUno(String paisUno) {
		this.paisUno = paisUno;
	}

	public String getPaisDos() {
		return paisDos;
	}

	public void setPaisDos(String paisDos) {
		this.paisDos = paisDos;
	}

	public String getCiudadUno() {
		return ciudadUno;
	}

	public void setCiudadUno(String ciudadUno) {
		this.ciudadUno = ciudadUno;
	}

	public String getCiudadDos() {
		return ciudadDos;
	}

	public void setCiudadDos(String ciudadDos) {
		this.ciudadDos = ciudadDos;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getContactoFecha() {
		return contactoFecha;
	}

	public void setContactoFecha(String contactoFecha) {
		this.contactoFecha = contactoFecha;
	}

	public String getFiebre() {
		return fiebre;
	}

	public void setFiebre(String fiebre) {
		this.fiebre = fiebre;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public String getCabeza() {
		return cabeza;
	}

	public void setCabeza(String cabeza) {
		this.cabeza = cabeza;
	}

	public String getRespirar() {
		return respirar;
	}

	public void setRespirar(String respirar) {
		this.respirar = respirar;
	}

	public String getGarganta() {
		return garganta;
	}

	public void setGarganta(String garganta) {
		this.garganta = garganta;
	}

	public String getEscurrimiento() {
		return escurrimiento;
	}

	public void setEscurrimiento(String escurrimiento) {
		this.escurrimiento = escurrimiento;
	}

	public String getOlfato() {
		return olfato;
	}

	public void setOlfato(String olfato) {
		this.olfato = olfato;
	}

	public String getGusto() {
		return gusto;
	}

	public void setGusto(String gusto) {
		this.gusto = gusto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}	
	
}