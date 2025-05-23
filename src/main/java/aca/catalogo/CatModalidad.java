// Bean del Catalogo de Modalidades
package  aca.catalogo;

import java.sql.*;

public class CatModalidad{
	private String modalidadId;
	private String nombreModalidad;
	private String enLinea;
	private String admisible;
		
	public CatModalidad(){
		modalidadId		= "";
		nombreModalidad	= "";
		enLinea			= "";
		admisible		= "";
	}
	
	public String getAdmisible() {
		return admisible;
	}

	public void setAdmisible(String admisible) {
		this.admisible = admisible;
	}

	public String getModalidadId(){
		return modalidadId;
	}
	
	public void setModalidadId( String modalidadId){
		this.modalidadId = modalidadId;
	}	
	
	public String getNombreModalidad(){
		return nombreModalidad;
	}
	
	public void setNombreModalidad( String nombreModalidad){
		this.nombreModalidad = nombreModalidad;
	}
	
	/**
	 * @return the enLinea
	 */
	public String getEnLinea() {
		return enLinea;
	}

	/**
	 * @param enLinea the enLinea to set
	 */
	public void setEnLinea(String enLinea) {
		this.enLinea = enLinea;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		modalidadId 		= rs.getString("MODALIDAD_ID");
		nombreModalidad 	= rs.getString("NOMBRE_MODALIDAD");
		enLinea 	        = rs.getString("ENLINEA");
		admisible 	        = rs.getString("ADMISIBLE");
	}	
}