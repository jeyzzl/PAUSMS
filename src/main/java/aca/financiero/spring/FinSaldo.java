package aca.financiero.spring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FinSaldo {
	private String cCobro;
	private String cCobroTotal;
	private String saldoUM;
	private String saldoSP;
	private String saldoGlobal;
	private String pagareNoVencido;
	private String saldoVencido;
	
	public FinSaldo(){
		cCobro				= "0";
		cCobroTotal			= "0";
		saldoUM				= "0";
		saldoSP				= "0";
		saldoGlobal			= "0";
		pagareNoVencido		= "0";
		saldoVencido		= "0";
	}

	public String getcCobro() {
		return cCobro;
	}

	public void setcCobro(String cCobro) {
		this.cCobro = cCobro;
	}

	public String getcCobroTotal() {
		return cCobroTotal;
	}

	public void setcCobroTotal(String cCobroTotal) {
		this.cCobroTotal = cCobroTotal;
	}

	public String getSaldoUM() {
		return saldoUM;
	}

	public void setSaldoUM(String saldoUM) {
		this.saldoUM = saldoUM;
	}

	public String getSaldoSP() {
		return saldoSP;
	}

	public void setSaldoSP(String saldoSP) {
		this.saldoSP = saldoSP;
	}

	public String getSaldoGlobal() {
		return saldoGlobal;
	}

	public void setSaldoGlobal(String saldoGlobal) {
		this.saldoGlobal = saldoGlobal;
	}

	public String getPagareNoVencido() {
		return pagareNoVencido;
	}

	public void setPagareNoVencido(String pagareNoVencido) {
		this.pagareNoVencido = pagareNoVencido;
	}

	public String getSaldoVencido() {
		return saldoVencido;
	}

	public void setSaldoVencido(String saldoVencido) {
		this.saldoVencido = saldoVencido;
	}

}