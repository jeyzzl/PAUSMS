// Bean del Catalogo de Religiones
package  aca.catalogo;

import java.sql.*;

public class CatReligion{
	private String religionId;	
	private String nombreReligion;
	private String nombreCorto;
	
	public CatReligion(){
		religionId 			= "0";
		nombreReligion 		= "-";
		nombreCorto 		= "-";
	}
	
	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getNombreReligion() {
		return nombreReligion;
	}

	public void setNombreReligion(String nombreReligion) {
		this.nombreReligion = nombreReligion;
	}
	
	public String getNombreCorto() {
		return nombreCorto;
	}
	
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		religionId 		= rs.getString("RELIGION_ID");
		nombreReligion 	= rs.getString("NOMBRE_RELIGION");
		nombreCorto 	= rs.getString("NOMBRE_CORTO");
	}		
}