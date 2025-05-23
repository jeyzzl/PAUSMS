package aca.disciplina.spring;

public class CondJuez {
	private String idJuez;	
	private String nombre;
	
	public CondJuez(){
		idJuez 		= "0";
		nombre  	= "-";
	}
	
	public String getIdJuez(){
		return idJuez;
	}
	
	public void setIdJuez( String idJuez){
		this.idJuez = idJuez;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre( String nombre){
		this.nombre = nombre;
	}	
	
}