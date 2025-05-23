// Clase para la vista ALUMNO_CURSO
package  aca.vista.spring;

public class AlumnoNota{
	private String codigoPersonal;
	private String cursoCargaId;
	private String evaluados;
	private String porcentaje;
	private String puntos;
	private	String extras;
	private	String nota;
	
	public AlumnoNota(){
		codigoPersonal	= "0";
		cursoCargaId	= "0";
		evaluados		= "0";
		porcentaje		= "0";
		puntos			= "0";
		extras			= "0";
		nota 			= "0";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEvaluados() {
		return evaluados;
	}

	public void setEvaluados(String evaluados) {
		this.evaluados = evaluados;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}	
	
}