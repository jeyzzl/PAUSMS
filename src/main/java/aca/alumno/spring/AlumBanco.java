package aca.alumno.spring;

public class AlumBanco {
	private String bancoId;
	private String codigoPersonal;
	private String banco;
	private String bancoRama;
	private String cuentaNombre;
	private String cuentaNumero;
	private String numeroBBS;
	private String cuentaTipo;
	private String codigoSwift;

	public AlumBanco() {
		setBancoId("0");
		setCodigoPersonal("0");
		setBanco("-");
		setBancoRama("-");
		setCuentaNombre("-");
		setCuentaNumero("-");
		setNumeroBBS("-");
		setCuentaTipo("-");
		setCodigoSwift("-");
	}
	public String getBancoId() {
		return bancoId;
	}
	public void setBancoId(String bancoId) {
		this.bancoId = bancoId;
	}
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	public String getNumeroBBS() {
		return numeroBBS;
	}
	public void setNumeroBBS(String numeroBBS) {
		this.numeroBBS = numeroBBS;
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
