// Bean del Catalogo de Religiones
package  aca.catalogo;

import java.sql.*;

public class CatAvance{
	private String avanceId;	
	private String nombreAvance;
	
	public CatAvance(){
		avanceId 		= "";
		nombreAvance	= "";
	}

	public String getAvanceId() {
		return avanceId;
	}

	public void setAvanceId(String avanceId) {
		this.avanceId = avanceId;
	}

	public String getNombreAvance() {
		return nombreAvance;
	}

	public void setNombreAvance(String nombreAvance) {
		this.nombreAvance = nombreAvance;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		avanceId 		= rs.getString("AVANCE_ID");
		nombreAvance 	= rs.getString("NOMBRE_AVANCE");		
	}
		
}