package aca.podium.spring;

public class Notas {
	
	private String folio;
	private float resLog;		
	private float resMat;		
	private float resEsp;		
	private float resIng;
	private float resBio;
	private float resFis;
	private float resQui;
	
	public Notas() {
		resLog = 0;		
		resMat = 0;		
		resEsp = 0;		
		resIng = 0;
		resBio = 0;
		resFis = 0;
		resQui = 0;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public float getResLog() {
		return resLog;
	}

	public void setResLog(float resLog) {
		this.resLog = resLog;
	}

	public float getResMat() {
		return resMat;
	}

	public void setResMat(float resMat) {
		this.resMat = resMat;
	}

	public float getResEsp() {
		return resEsp;
	}

	public void setResEsp(float resEsp) {
		this.resEsp = resEsp;
	}

	public float getResIng() {
		return resIng;
	}

	public void setResIng(float resIng) {
		this.resIng = resIng;
	}

	public float getResBio() {
		return resBio;
	}

	public void setResBio(float resBio) {
		this.resBio = resBio;
	}

	public float getResFis() {
		return resFis;
	}

	public void setResFis(float resFis) {
		this.resFis = resFis;
	}

	public float getResQui() {
		return resQui;
	}

	public void setResQui(float resQui) {
		this.resQui = resQui;
	}
}
