package aca.investiga;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Karelly
 *
 */
public class InvReferente {
	private String codigoId;
	private String carreraId;
	

	public InvReferente(){
		codigoId	= "";
		carreraId	= "";
	}	

	public String getCodigoId() {
		return codigoId;
	}

	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoId		= rs.getString("CODIGO_ID");
		carreraId		= rs.getString("CARRERA_ID");
		
	}	

}