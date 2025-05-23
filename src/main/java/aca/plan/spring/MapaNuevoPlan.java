/**
 * 
 */
package aca.plan.spring;

public class MapaNuevoPlan {
	private String planId;
	private String carreraId;
	private String nombre;
	private	String tipo;
	private String versionId;
	private String versionNombre;
	private String estado;	
	private String hts;
	private String hps;
	private String hfd;
	private String hei;
	private String idioma;
	private String hss;
	private String has;
	private String year;
	
	public MapaNuevoPlan(){
		planId			= "";
		carreraId		= "";
		nombre			= "";
		tipo			= "";
		versionId		= "";
		versionNombre	= "";
		estado			= "";
		hts				= "";
		hps				= "";
		hfd				= "";
		hei				= "";
		idioma			= "";
		hss				= "";
		has				= "";
		year			= "";
	}
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getVersionNombre() {
		return versionNombre;
	}

	public void setVersionNombre(String versionNombre) {
		this.versionNombre = versionNombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getHts() {
		return hts;
	}

	public void setHts(String hts) {
		this.hts = hts;
	}

	public String getHps() {
		return hps;
	}

	public void setHps(String hps) {
		this.hps = hps;
	}

	public String getHfd() {
		return hfd;
	}

	public void setHfd(String hfd) {
		this.hfd = hfd;
	}

	public String getHei() {
		return hei;
	}

	public void setHei(String hei) {
		this.hei = hei;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getHss() {
		return hss;
	}

	public void setHss(String hss) {
		this.hss = hss;
	}

	public String getHas() {
		return has;
	}

	public void setHas(String has) {
		this.has = has;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}