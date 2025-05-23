// Bean del Catalogo de Areas
package  aca.catalogo;

import java.sql.*;

public class CatArea{
	private String areaId;
	private String nombreArea;
	private String codigoPersonal;
	private String tipoPromedio;
	
	public CatArea(){
		areaId 			= "";
		nombreArea		= "";
		codigoPersonal	= "";
		tipoPromedio	= "";
	}
	
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTipoPromedio() {
		return tipoPromedio;
	}

	public void setTipoPromedio(String tipoPromedio) {
		this.tipoPromedio = tipoPromedio;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		areaId 			= rs.getString("AREA_ID");
		nombreArea 		= rs.getString("NOMBRE_AREA");
		tipoPromedio 	= rs.getString("TIPO_PROMEDIO");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
	}
	
}