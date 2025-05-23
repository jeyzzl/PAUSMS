package  aca.financiero.spring;

import java.util.Date;

public class FinQuote{
	private String periodoId;
	private int quoteId;
	private String codigoPersonal;
	private String fecha;	
	private String status;
	private String description;
	private int semester;
	
	public FinQuote(){
		periodoId			= "0000";
		quoteId				= 0;
		codigoPersonal		= "0";
		fecha				= aca.util.Fecha.getHoy();
		status				= "I";
		description			= "";
		semester 			= 1;
	}

	public int getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
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

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	
}
