// Bean del Catalogo de Paises
package  aca.catalogo;

import java.sql.*;

public class CatPais{
	private String paisId;	
	private String nombrePais;
	private String nacionalidad;
	private String interamerica;
	private String divisionId;
	
	public CatPais(){
		paisId 			= "";
		nombrePais 		= "";
		nacionalidad	= "";
		interamerica	= "";
		divisionId 		= "";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getNombrePais(){
		return nombrePais;
	}
	
	public void setNombrePais( String nombrePais){
		this.nombrePais = nombrePais;
	}
	
	public String getNacionalidad(){
		return nacionalidad;
	}
	
	public void setNacionalidad( String nacionalidad){
		this.nacionalidad = nacionalidad;
	}

	public String getInteramerica() {
		return interamerica;
	}

	public void setInteramerica(String interamerica) {
		this.interamerica = interamerica;
	}	

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 			= rs.getString("PAIS_ID");
		nombrePais 		= rs.getString("NOMBRE_PAIS");
		nacionalidad 	= rs.getString("NACIONALIDAD");
		interamerica 	= rs.getString("INTERAMERICA");
		divisionId		= rs.getString("DIVISION_ID");
	}	
}