// Bean del Catalogo de Bloques
package  aca.carga.spring;

public class CargaGrupoEvaluacion{
	private String cursoCargaId;
	private String evaluacionId;
	private String nombreEvaluacion;
	private String fecha;
	private String estrategiaId;	
	private String valor;
	private String tipo;
	private String evaluacionE42;
	private String enviar;
	
	public CargaGrupoEvaluacion(){
		cursoCargaId		= "";
		evaluacionId		= "0";
		nombreEvaluacion	= "";
		fecha				= aca.util.Fecha.getHoyReversa();
		estrategiaId		= "";
		valor				= "";
		tipo				= "";
		evaluacionE42		= "0";
		enviar				= "N";
	}
	
	public String getEvaluacionE42() {
		return evaluacionE42;
	}

	public void setEvaluacionE42(String evaluacionE42) {
		this.evaluacionE42 = evaluacionE42;
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
	
	public String getNombreEvaluacion(){
		return nombreEvaluacion;
	}
	
	public void setNombreEvaluacion( String nombreEvaluacion){
		this.nombreEvaluacion = nombreEvaluacion;
	}
	
	public String getFecha(){
		return fecha;
	}
	
	public void setFecha( String fecha){
		this.fecha = fecha;
	}
	
	public String getEstrategiaId(){
		return estrategiaId;
	}
	
	public void setEstrategiaId( String estrategiaId){
		this.estrategiaId = estrategiaId;
	}
	
	public String getValor(){
		return valor;
	}
	
	public void setValor( String valor){
		this.valor = valor;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo( String tipo){
		this.tipo = tipo;
	}

	public String getEnviar() {
		return enviar;
	}

	public void setEnviar(String enviar) {
		this.enviar = enviar;
	}
	
}