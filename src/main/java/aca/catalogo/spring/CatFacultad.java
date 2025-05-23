// Bean del Catalogo de Facultades
package  aca.catalogo.spring;

import java.sql.*;

public class CatFacultad{
	private String facultadId;
	private String areaId;	
	private String titulo;
	private String nombreFacultad;
	private String codigoPersonal;
	private String nombreCorto;
	private String invReferente;
	
	public CatFacultad(){
		areaId 			= "";
		facultadId		= "";
		titulo			= "";
		nombreFacultad	= "";
		codigoPersonal	= "";
		nombreCorto		= "";
		invReferente	= "0000000";
	}
	
	public String getInvReferente() {
		return invReferente;
	}

	public void setInvReferente(String invReferente) {
		this.invReferente = invReferente;
	}

	public String getAreaId(){
		return areaId;
	}
	
	public void setAreaId( String areaId){
		this.areaId = areaId;
	}
	
	public String getFacultadId(){
		return facultadId;
	}
	
	public void setFacultadId( String facultadId){
		this.facultadId = facultadId;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public void setTitulo( String titulo){
		this.titulo = titulo;
	}
	
	public String getNombreFacultad(){
		return nombreFacultad;
	}
	
	public void setNombreFacultad( String nombreFacultad){
		this.nombreFacultad = nombreFacultad;
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	

	public String getNombreCorto() {
		return nombreCorto;
	}
	
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		areaId 			= rs.getString("AREA_ID");
		facultadId 		= rs.getString("FACULTAD_ID");
		titulo	 		= rs.getString("TITULO");
		nombreFacultad 	= rs.getString("NOMBRE_FACULTAD");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		nombreCorto 	= rs.getString("NOMBRE_CORTO");
		invReferente 	= rs.getString("INV_REFERENTE");
	}
	
}