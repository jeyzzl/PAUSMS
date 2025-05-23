package aca.rotaciones.spring;

public class RotProgramacion {

	private String programacionId;
	private String hospitalId;
	private String especialidadId;
	private String codigoPersonal;
	private String fInicio;
	private String fFinal;
	private String inscripcion;
	private String pago;
	private String anual;
	private String integrada;
	
	public RotProgramacion(){	
		programacionId		= "";
		hospitalId			= "";
		especialidadId		= "";
		codigoPersonal 		= "";
		fInicio				= "";
		fFinal				= "";
		inscripcion			= "";
		pago				= "";
		anual				= "";
		integrada			= "";
		
	}

	public String getProgramacionId() {
		return programacionId;
	}
	
	public void setProgramacionId(String programacionId) {
		this.programacionId = programacionId;
	}
	
	public String getHospitalId() {
		return hospitalId;
	}
	
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public String getEspecialidadId() {
		return especialidadId;
	}

	public void setEspecialidadId(String especialidadId) {
		this.especialidadId = especialidadId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getfInicio() {
		return fInicio;
	}

	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}

	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(String inscripcion) {
		this.inscripcion = inscripcion;
	}


	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public String getAnual() {
		return anual;
	}

	public void setAnual(String anual) {
		this.anual = anual;
	}

	public String getIntegrada() {
		return integrada;
	}

	public void setIntegrada(String integrada) {
		this.integrada = integrada;
	}
	
}
