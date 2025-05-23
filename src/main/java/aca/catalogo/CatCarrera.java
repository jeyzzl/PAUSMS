// Bean del Catalogo de Carreras
package  aca.catalogo;

import java.sql.*;

public class CatCarrera{
	private String facultadId;
	private	String carreraId;
	private String nivelId;
	private String titulo;
	private String nombreCarrera;
	private String nombreCorto;
	private String ccostoId;
	private String codigoPersonal;
	
	public CatCarrera(){
		facultadId 	= "";
		carreraId		= "";
		nivelId		= "";
		titulo			= "";
		nombreCarrera	= "";
		nombreCorto	= "";
		ccostoId		= "";
		codigoPersonal	= "";
	}
	
	public String getFacultadId(){
		return facultadId;
	}
	
	public void setFacultadId( String facultadId){
		this.facultadId = facultadId;
	}
	
	public String getCarreraId(){
		return carreraId;
	}
	
	public void setCarreraId( String carreraId){
		this.carreraId = carreraId;
	}
	
	public String getNivelId(){
		return nivelId;
	}
	
	public void setNivelId( String nivelId){
		this.nivelId = nivelId;
	}
	
	public String getTitulo(){
		return titulo;
	}
		
	public void setTitulo( String titulo){
		this.titulo = titulo;
	}
	
	public String getNombreCarrera(){
		return nombreCarrera;
	}
	
	public void setNombreCarrera( String nombreCarrera){
		this.nombreCarrera = nombreCarrera;
	}
		
	public String getNombreCorto(){
		return nombreCorto;
	}

	public void setNombreCorto( String nombreCorto){
		this.nombreCorto = nombreCorto;
	}
	
	public String getCcostoId(){
		return ccostoId;
	}
	
	public void setCcostoId( String ccostoId){
		this.ccostoId = ccostoId;
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		facultadId 			= rs.getString("FACULTAD_ID");
		carreraId 			= rs.getString("CARRERA_ID");
		nivelId 			= rs.getString("NIVEL_ID");
		titulo	 			= rs.getString("TITULO");
		nombreCarrera 		= rs.getString("NOMBRE_CARRERA");
		nombreCorto		= rs.getString("NOMBRE_CORTO");
		ccostoId 			= rs.getString("CCOSTO_ID");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
	}	
}