package aca.sep.spring;

public class SepDatos {
	
	private String carrera;
	private String matricula;
	private String nombre;
	private String curp;
	private String grado;
	private String nacimiento;	
	private String nuevo;
	private String antecedente;		
	private String residencia;	
	
	public SepDatos() {
		carrera			= "0";
		matricula 		= "0";
		nombre	 		= "-";
		curp 			= "0";
		grado 			= "0";
		nacimiento 		= "";
		nuevo 			= "";
		antecedente	  	= "";
		residencia		= "";	
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(String nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getNuevo() {
		return nuevo;
	}

	public void setNuevo(String nuevo) {
		this.nuevo = nuevo;
	}

	public String getAntecedente() {
		return antecedente;
	}

	public void setAntecedente(String antecedente) {
		this.antecedente = antecedente;
	}

	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}
	
}
