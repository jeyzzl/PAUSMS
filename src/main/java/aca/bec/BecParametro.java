package aca.bec;


import java.sql.ResultSet;
import java.sql.SQLException;

public class BecParametro {
	
	private String prepaInicio;
	private String prepaFinal;
	private String pregradoInicio;
	private String pregradoFinal;
	
	public BecParametro(){
		prepaInicio = "";
		prepaFinal = "";
		pregradoInicio = "";
		pregradoFinal  = "";
	}
	
	public String getPrepaInicio() {
		return prepaInicio;
	}
	public void setPrepaInicio(String prepaInicio) {
		this.prepaInicio = prepaInicio;
	}
	public String getPrepaFinal() {
		return prepaFinal;
	}
	public void setPrepaFinal(String prepaFinal) {
		this.prepaFinal = prepaFinal;
	}
	public String getPregradoInicio() {
		return pregradoInicio;
	}
	public void setPregradoInicio(String pregradoInicio) {
		this.pregradoInicio = pregradoInicio;
	}
	public String getPregradoFinal() {
		return pregradoFinal;
	}
	public void setPregradoFinal(String pregradoFinal) {
		this.pregradoFinal = pregradoFinal;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		prepaInicio			= rs.getString("PREPA_INICIO");
		prepaFinal	 		= rs.getString("PREPA_FINAL");
		pregradoInicio 		= rs.getString("PREGRADO_INICIO");
		pregradoFinal 		= rs.getString("PREGRADO_FINAL");
	}

}
