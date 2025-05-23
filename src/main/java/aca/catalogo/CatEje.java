package aca.catalogo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatEje{
	private String ejeId;	
	private String ejeNombre;
	private String descripcion;
	private String nivelId;
	private String orden;
	
	public CatEje(){
		ejeId 		= "";
		ejeNombre	= "";
		descripcion = "";
		nivelId	    = "";
		orden 		= "";
	}
	
	public String getEjeId() {
		return ejeId;
	}

	public void setEjeId(String ejeId) {
		this.ejeId = ejeId;
	}

	public String getEjeNombre() {
		return ejeNombre;
	}

	public void setEjeNombre(String ejeNombre) {
		this.ejeNombre = ejeNombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNivelId() {
		return nivelId;
	}

	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		ejeId 			= rs.getString("EJE_ID");
		ejeNombre 		= rs.getString("EJE_NOMBRE");
		descripcion		= rs.getString("DESCRIPCION");
		nivelId		 	= rs.getString("NIVEL_ID");
		orden	 		= rs.getString("ORDEN");
	}

}