package aca.disciplina.spring;

public class CondReporte {
	private String idReporte;	
	private String nombre;
	private String tipo;
	
	public CondReporte(){
		idReporte 	= "0";
		nombre  	= "-";
		tipo	  	= "D";
	}
	
	public String getIdReporte(){
		return idReporte;
	}
	
	public void setIdReporte( String idReporte){
		this.idReporte = idReporte;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre( String nombre){
		this.nombre = nombre;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo( String tipo){
		this.tipo = tipo;
	}
	
}