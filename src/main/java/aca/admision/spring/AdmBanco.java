package aca.admision.spring;

public class AdmBanco {
	private String folio;
	private String banco;
	private String bancoRama;
	private String cuentaNombre;
	private String cuentaNumero;
	private String numeroBbs;
	private String cuentaTipo;
	private String codigoSwift;
	
	public AdmBanco() {
		folio				= "0";
		banco				= "-";
		bancoRama 			= "-";
		cuentaNombre 		= "-";
		cuentaNumero 		= "-";
		numeroBbs 			= "-";
		cuentaTipo 			= "-";
		codigoSwift 		= "-";
	}	
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getBancoRama() {
		return bancoRama;
	}

	public void setBancoRama(String bancoRama) {
		this.bancoRama = bancoRama;
	}

	public String getCuentaNombre() {
		return cuentaNombre;
	}

	public void setCuentaNombre(String cuentaNombre) {
		this.cuentaNombre = cuentaNombre;
	}

	public String getCuentaNumero() {
		return cuentaNumero;
	}

	public void setCuentaNumero(String cuentaNumero) {
		this.cuentaNumero = cuentaNumero;
	}

	public String getNumeroBbs() {
		return numeroBbs;
	}

	public void setNumeroBbs(String numeroBbs) {
		this.numeroBbs = numeroBbs;
	}

	public String getCuentaTipo() {
		return cuentaTipo;
	}

	public void setCuentaTipo(String cuentaTipo) {
		this.cuentaTipo = cuentaTipo;
	}

	public String getCodigoSwift() {
		return codigoSwift;
	}

	public void setCodigoSwift(String codigoSwift) {
		this.codigoSwift = codigoSwift;
	}

	
}