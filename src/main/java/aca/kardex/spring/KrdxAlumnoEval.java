// Bean de la tabla KRDX_ALUMNO_EVAL
package  aca.kardex.spring;

public class KrdxAlumnoEval{
	private String codigoPersonal;
	private String cursoCargaId;
	private String evaluacionId;
	private String nota;
	private String evaluacionE42;
	
	public KrdxAlumnoEval(){
		codigoPersonal		= "";
		cursoCargaId		= "";
		evaluacionId		= "";
		nota				= "";
		evaluacionE42		= "";
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}	
	
	public String getEvaluacionId(){
		return evaluacionId;
	}
	
	public void setEvaluacionId( String evaluacionId){
		this.evaluacionId = evaluacionId;
	}
	
	public String getNota(){
		return nota;
	}
	
	public void setNota( String nota){
		this.nota = nota;
	}
	
	public String getEvaluacionE42() {
		return evaluacionE42;
	}

	public void setEvaluacionE42(String evaluacionE42) {
		this.evaluacionE42 = evaluacionE42;
	}
	
}