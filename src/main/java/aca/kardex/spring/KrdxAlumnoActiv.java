// Bean de la tablas KRDX_CURSO_ACT 
package  aca.kardex.spring;

public class KrdxAlumnoActiv{
	private String codigoPersonal;
	private String cursoCargaId;
	private String actividadId;
	private String nota;
	private String actividadE42;
	private String evaluacionId;
	
	public KrdxAlumnoActiv(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		actividadId		= "";		
		nota			= "";
		actividadE42	= "";
		evaluacionId 	= "0"; 
	}

	public String getActividadId() {
		return actividadId;
	}	

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
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

	public String getNota() {
		return nota;
	}	

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getActividadE42() {
		return actividadE42;
	}

	public void setActividadE42(String actividadE42) {
		this.actividadE42 = actividadE42;
	}

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}	
}