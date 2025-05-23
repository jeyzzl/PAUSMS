package aca.catalogo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatCriterio {
	private String criterioId;	
	private String descripcion;
	
	public CatCriterio(){
		criterioId     	= "0";
		descripcion     = "-";
	}

	public String getCriterioId() {
		return criterioId;
	}

	public void setCriterioId(String criterioId) {
		this.criterioId = criterioId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		criterioId     = rs.getString("CRITERIO_ID");
		descripcion		= rs.getString("DESCRIPCION");
	}

}