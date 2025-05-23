// Bean del Catálogo de Grupos
package  aca.carga.spring;

public class CargaGrupoHorario{
	private String cursoCargaId;
	private String salonId;
	private String horario;
	private String tipo;
	private String validaCruce;
	
	public CargaGrupoHorario(){
		cursoCargaId	= "";
		salonId			= "";
		horario			= "";
		tipo			= "";
		validaCruce	= "";		
	}
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}	
	
	public String getSalonId(){
		return salonId;
	}
	
	public void setSalonId( String salonId){
		this.salonId = salonId;
	}	
	
	public String getHorario(){
		return horario;
	}
	
	public void setHorario( String horario){
		this.horario = horario;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo( String tipo){
		this.tipo = tipo;
	}
	
	public String getValidaCruce(){
		return validaCruce;
	}
	
	public void setValidaCruce( String validaCruce){
		this.validaCruce = validaCruce;
	}
	
}