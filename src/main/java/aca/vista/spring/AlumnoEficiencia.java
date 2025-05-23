// Clase para la vista ALUMNO_EFICIENCIA
package  aca.vista.spring;

public class AlumnoEficiencia{	
	private String codigoPersonal;
	private String cursoCargaId;
	private String evaluacionId;
	private String tipo;
	private String valor;
	private String nota;
	private String evaluadas;
	private String puntos;
	private String totActividades;
	
	public AlumnoEficiencia(){
		cursoCargaId		= "";
		codigoPersonal		= "";
		evaluacionId		= "";
		tipo				= "";
		valor				= "";
		nota				= "";
		evaluadas			= "";
		puntos				= "0";
		totActividades		= "0";
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

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getEvaluadas() {
		return evaluadas;
	}

	public void setEvaluadas(String evaluadas) {
		this.evaluadas = evaluadas;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getTotActividades() {
		return totActividades;
	}

	public void setTotActividades(String totActividades) {
		this.totActividades = totActividades;
	}
}