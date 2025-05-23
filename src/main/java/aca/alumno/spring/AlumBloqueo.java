// Bean de datos academicos del alumno
package  aca.alumno.spring;

public class AlumBloqueo{
	private String matricula;
	private String cerrar;
	
	public AlumBloqueo(){
		matricula		= "0";
		cerrar			= "N";		
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCerrar() {
		return cerrar;
	}

	public void setCerrar(String cerrar) {
		this.cerrar = cerrar;
	}	
}