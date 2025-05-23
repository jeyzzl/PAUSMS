package aca.financiero.spring;

public class FesContratoFinanciero {
	private String id;
	private String matricula;
	private String fechaVencimiento;
	private String importe;
	private String liquidado;
	private String fechaLiquidacion;
	private String observaciones;
	
	public FesContratoFinanciero(){
		id					= "";
		matricula			= "";
		fechaVencimiento	= "";
		importe				= "";
		liquidado			= "";
		fechaLiquidacion	= "";
		observaciones		= "";	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getLiquidado() {
		return liquidado;
	}

	public void setLiquidado(String liquidado) {
		this.liquidado = liquidado;
	}

	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}