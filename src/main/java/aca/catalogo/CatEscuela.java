// Bean del Catalogo de Escuelas
package  aca.catalogo;

import java.sql.*;

public class CatEscuela{
	private String escuelaId;
	private String nombreEscuela;	
	private String paisId;
	private String estadoId;
	private String ciudadId;
	
	public CatEscuela(){
		escuelaId		= "";
		nombreEscuela	= "";
		paisId			= "";
		estadoId		= "";
		ciudadId		= "";
	}
	
	public String getEscuelaId(){
		return escuelaId;
	}
	
	public void setEscuelaId( String escuelaId){
		this.escuelaId = escuelaId;
	}	
	
	public String getNombreEscuela(){
		return nombreEscuela;
	}
	
	public void setNombreEscuela( String nombreEscuela){
		this.nombreEscuela = nombreEscuela;
	}	
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public void setEstadoId( String estadoId){
		this.estadoId = estadoId;
	}
	
	public String getCiudadId(){
		return ciudadId;
	}
	
	public void setCiudadId( String ciudadId){
		this.ciudadId = ciudadId;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		escuelaId 		= rs.getString("ESCUELA_ID");
		nombreEscuela 	= rs.getString("NOMBRE_ESCUELA");
		paisId		 	= rs.getString("PAIS_ID");
		estadoId		= rs.getString("ESTADO_ID");
		ciudadId	 	= rs.getString("CIUDAD_ID");
	}

}