package aca.internado.spring;


/**
 * @author Carlos
 *
 */
public class IntDatosAlumno {
	private String codigoPersonal;
	private String computadora;
	private String tratamiento;
	private String motivo;
	private String tipoSangre;					
	private String instrumentos;
	private String celular;
	private String correo;
	private String telefono;
	
	public IntDatosAlumno(){
		codigoPersonal 	= "0";
		computadora 	= "-";
		tratamiento 	= "-";
		motivo 			= "-";
		tipoSangre 		= "";		
		instrumentos 	= "-";
		celular 		= "-";
		correo 			= "-";
		telefono 		= "-";		
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getComputadora() {
		return computadora;
	}

	public void setComputadora(String computadora) {
		this.computadora = computadora;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public String getInstrumentos() {
		return instrumentos;
	}

	public void setInstrumentos(String instrumentos) {
		this.instrumentos = instrumentos;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}