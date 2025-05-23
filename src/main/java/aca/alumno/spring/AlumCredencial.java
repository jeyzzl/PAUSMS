// Bean de Alum_Foto
package  aca.alumno.spring;

public class AlumCredencial{
	private String codigoPersonal;
	private String nombres;	
	private String apellidos;
	private String carrera;
	private String cotejado;
	private String fecha;
	private byte[] foto;
	private String periodo1;
	private String periodo2;
	private String periodo3;
		
	public AlumCredencial(){
		codigoPersonal	= "";
		nombres			= "";		
		apellidos		= "";
		carrera			= "";
		cotejado 		= ""; 
		fecha			= "";
		foto			= null;
		periodo1 		= "";
		periodo2 		= "";
		periodo3 		= "";
		
	}		
		
	/**
	 * @return the periodo1
	 */
	public String getPeriodo1() {
		return periodo1;
	}

	/**
	 * @param periodo1 the periodo1 to set
	 */
	public void setPeriodo1(String periodo1) {
		this.periodo1 = periodo1;
	}

	/**
	 * @return the periodo2
	 */
	public String getPeriodo2() {
		return periodo2;
	}

	/**
	 * @param periodo2 the periodo2 to set
	 */
	public void setPeriodo2(String periodo2) {
		this.periodo2 = periodo2;
	}

	/**
	 * @return the periodo3
	 */
	public String getPeriodo3() {
		return periodo3;
	}

	/**
	 * @param periodo3 the periodo3 to set
	 */
	public void setPeriodo3(String periodo3) {
		this.periodo3 = periodo3;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the carrera
	 */
	public String getCarrera() {
		return carrera;
	}
	
	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	
	/**
	 * @return the cotejado
	 */
	public String getCotejado() {
		return cotejado;
	}

	/**
	 * @param cotejado the cotejado to set
	 */
	public void setCotejado(String cotejado) {
		this.cotejado = cotejado;
	}
	
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the foto
	 */
	public byte[] getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
}