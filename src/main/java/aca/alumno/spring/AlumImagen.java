//Bean del periodo de vacaciones del alumno
package aca.alumno.spring;

public class AlumImagen {
	private String codigoPersonal;
	private String consentimiento;
	private String tipo;	
	private String fecha;
	
	public AlumImagen(){
		codigoPersonal	= "0";
		consentimiento 	= "-";
		tipo			= "-";
		fecha			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getConsentimiento() {
		return consentimiento;
	}
	public void setConsentimiento(String consentimiento) {
		this.consentimiento = consentimiento;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	
}