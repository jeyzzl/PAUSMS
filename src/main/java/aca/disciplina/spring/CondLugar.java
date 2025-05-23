package aca.disciplina.spring;

public class CondLugar {
	private String idLugar;	
	private String nombre;
	
	public CondLugar(){
		idLugar 		= "0";
		nombre  		= "-";
	}
	
	public String getIdLugar(){
		return idLugar;
	}
	
	public void setIdLugar( String idLugar){
		this.idLugar = idLugar;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre( String nombre){
		this.nombre = nombre;
	}
	
}