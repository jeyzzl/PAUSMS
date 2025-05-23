package aca.admision;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmUbicacion{
	private String folio;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String calle;
	private String numero;
	private String codigoPostal;	
	private String telefono;
	private String colonia;
		
	public AdmUbicacion(){
		folio 			= "";
		paisId 			= "";
		estadoId 		= "";
		ciudadId		= "";
		calle 			= "";
		numero 			= "";
		codigoPostal 	= "";		
		telefono		= "";
		colonia			= "";
	}
	
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}

	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the paisId
	 */
	public String getPaisId() {
		return paisId;
	}

	/**
	 * @param paisId the paisId to set
	 */
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	/**
	 * @return the estadoId
	 */
	public String getEstadoId() {
		return estadoId;
	}

	/**
	 * @param estadoId the estadoId to set
	 */
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	/**
	 * @return the ciudadId
	 */
	public String getCiudadId() {
		return ciudadId;
	}

	/**
	 * @param ciudadId the ciudadId to set
	 */
	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		paisId			= rs.getString("PAIS_ID");
		estadoId 		= rs.getString("ESTADO_ID");
		ciudadId		= rs.getString("CIUDAD_ID");
		calle			= rs.getString("CALLE");
		numero	 		= rs.getString("NUMERO");
		codigoPostal	= rs.getString("CODIGO_POSTAL");
		telefono		= rs.getString("TELEFONO");
		colonia			= rs.getString("COLONIA");
	}
	
}