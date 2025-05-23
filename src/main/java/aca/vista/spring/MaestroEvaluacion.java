// Beans para la vista Maestro_Grupos

package aca.vista.spring;

public class MaestroEvaluacion {

	private String cursoCargaId;
	private String maestro;
	private String evaluacionId;
	private String nombreEvaluacion;
	private String fecha;
	private String estragiaId;
	private String valor;
	private String tipo;
	private String numAlumnos;
	private String numAct;
	private String numEval;
	private String actEval;
	
	public MaestroEvaluacion(){
		cursoCargaId			= "";
		maestro					= "";
		evaluacionId			= "";
		nombreEvaluacion		= "";
		fecha					= "";
		estragiaId				= "";
		valor					= "";
		tipo					= "";
		numAlumnos				= "";
		numAct					= "";
		numEval					= "";
		actEval					= "";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getMaestro() {
		return maestro;
	}

	public void setMaestro(String maestro) {
		this.maestro = maestro;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstragiaId() {
		return estragiaId;
	}

	public void setEstragiaId(String estragiaId) {
		this.estragiaId = estragiaId;
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

	public String getNumAlumnos() {
		return numAlumnos;
	}

	public void setNumAlumnos(String numAlumnos) {
		this.numAlumnos = numAlumnos;
	}

	public String getNumAct() {
		return numAct;
	}

	public void setNumAct(String numAct) {
		this.numAct = numAct;
	}

	public String getNumEval() {
		return numEval;
	}

	public void setNumEval(String numEval) {
		this.numEval = numEval;
	}

	public String getActEval() {
		return actEval;
	}

	public void setActEval(String actEval) {
		this.actEval = actEval;
	}



}