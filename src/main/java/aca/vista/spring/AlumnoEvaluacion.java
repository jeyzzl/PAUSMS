// Clase para la vista CARGA_ACADEMICA
package  aca.vista.spring;

public class AlumnoEvaluacion{
	private String cursoCargaId;
	private String codigoPersonal;
	private String evaluacionId;
	private String nombreEvaluacion;
	private String estrategiaId;
	private String fecha;
	private String valor;
	private String tipo;
	private String nota;	
	
	public AlumnoEvaluacion(){
		cursoCargaId		= "";
		codigoPersonal		= "";
		evaluacionId		= "";
		nombreEvaluacion	= "";
		estrategiaId		= "";
		fecha				= "";
		valor				= "";
		tipo				= "";
		nota				= "";
	}
	
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}


	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}


	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getEvaluacionId() {
		return evaluacionId;
	}


	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}


	public String getNombreEvaluacion() {
		return nombreEvaluacion;
	}


	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
	}


	public String getEstrategiaId() {
		return estrategiaId;
	}


	public void setEstrategiaId(String estrategiaId) {
		this.estrategiaId = estrategiaId;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}

}