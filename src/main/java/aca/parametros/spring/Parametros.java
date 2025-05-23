package aca.parametros.spring;

public class Parametros {
	private String id;
	private String institucion;
	private String certificados;
	private String constancias;
	private String cardex;
	private String monitor;
	private String monitorRuta;
	private String paisId;

	public Parametros(){
		id				= "1";
		institucion		= "-";
		certificados	= "-";
		constancias		= "-";
		cardex			= "-";
		monitor			= "S";
		monitorRuta 	= "X";
		paisId			= "0";
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getCertificados() {
		return certificados;
	}
	public void setCertificados(String certificados) {
		this.certificados = certificados;
	}

	public String getConstancias() {
		return constancias;
	}
	public void setConstancias(String constancias) {
		this.constancias = constancias;
	}

	public String getCardex() {
		return cardex;
	}
	public void setCardex(String cardex) {
		this.cardex = cardex;
	}

	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getMonitorRuta() {
		return monitorRuta;
	}
	public void setMonitorRuta(String monitorRuta) {
		this.monitorRuta = monitorRuta;
	}

	public String getPaisId() {
		return paisId;
	}
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}
	
	
	
}