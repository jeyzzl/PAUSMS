// Bean de Alum_Foto
package  aca.cultural.spring;

public class AnuariosEntregados{
	private String ejercicioId;
	private String matricula;
	private String nombre;
	private String fecha;
	private String usuario;
	private String carrera;
	private String semestre;
	
		
	public AnuariosEntregados(){
		ejercicioId		="";
		matricula		="";
		nombre			="";
		fecha			="";
		usuario 		="";
		carrera 		="";
		semestre 		="";
	}	
	
	public String getEjercicioId() {
		return ejercicioId;
	}

	public void setEjercicioId(String ejercicioId) {
		this.ejercicioId = ejercicioId;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
}