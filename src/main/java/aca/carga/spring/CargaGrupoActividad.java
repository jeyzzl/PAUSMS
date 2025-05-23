
package  aca.carga.spring;

public class CargaGrupoActividad{
	private String actividadId;
	private String cursoCargaId;
	private String evaluacionId;
	private String nombre;
	private String valor;	
	private String fecha;
	private String actividadE42;
	private String agendadaE42;
	private String enviar;
	
	public CargaGrupoActividad(){
		actividadId		= "";
		cursoCargaId		= "";
		evaluacionId		= "";
		nombre				= "";
		valor				= "";
		fecha				= "";
		actividadE42		= "";
		agendadaE42			= "N";
		enviar 				= "N";
	}
	
	public String getActividadE42() {
		return actividadE42;
	}	

	public void setActividadE42(String actividadE42) {
		this.actividadE42 = actividadE42;
	}	

	public String getActividadId() {
		return actividadId;
	}
	
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}	
	
	public String getFecha() {
		return fecha;
	}	
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	
	public String getAgendadaE42() {
		return agendadaE42;
	}

	public void setAgendadaE42(String agendadaE42) {
		this.agendadaE42 = agendadaE42;
	}

	public String getEnviar() {
		return enviar;
	}

	public void setEnviar(String enviar) {
		this.enviar = enviar;
	}
	
}