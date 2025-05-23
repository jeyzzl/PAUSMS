// Bean del Catalogo de Salones
package  aca.catalogo;

import java.sql.*;

public class CatSalon{
	private String edificioId;
	private String salonId;
	private String nombreSalon;
	private String numAlumnos;
	private String estado;
	
	public CatSalon(){
		edificioId 		= "";
		salonId 		= "";
		nombreSalon		= "-";
		numAlumnos		= "0";
		estado 			= "A";
	}
	
	public String getEdificioId(){
		return edificioId;
	}
	
	public void setEdificioId( String edificioId){
		this.edificioId = edificioId;
	}
	
	public String getSalonId(){
		return salonId;
	}
	
	public void setSalonId( String salonId){
		this.salonId = salonId;
	}
	
	public String getNombreSalon(){
		return nombreSalon;
	}
	
	public void setNombreSalon( String nombreSalon){
		this.nombreSalon = nombreSalon;
	}
	
	public String getNumAlumnos(){
		return numAlumnos;
	}
	
	public void setNumAlumnos( String numAlumnos){
		this.numAlumnos = numAlumnos;
	}	
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		edificioId 		= rs.getString("EDIFICIO_ID");
		salonId 		= rs.getString("SALON_ID");
		nombreSalon 	= rs.getString("NOMBRE_SALON");
		numAlumnos 		= rs.getString("NUM_ALUMNOS");
		estado 			= rs.getString("ESTADO");
	}

}