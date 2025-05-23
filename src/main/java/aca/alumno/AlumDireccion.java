package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumDireccion {
	private String codigoPersonal;
	private String calle;
	private String colonia;
	private String codPostal;
	private String apartado;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String fecha;
	private String estado;
	private String telefono;
	private String numero;
	
	
	public AlumDireccion(){
		codigoPersonal	= "";
		calle	        = "";
		colonia	    	= "";
		codPostal		= "";
		apartado		= "";
		paisId 			= "";
		estadoId		= "";
		ciudadId		= "";
		fecha   		= "";
		estado			= "";
		telefono		= "";
		numero			= "";
		
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	 * @return the codPostal
	 */
	public String getCodPostal() {
		return codPostal;
	}

	/**
	 * @param codPostal the codPostal to set
	 */
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	/**
	 * @return the apartado
	 */
	public String getApartado() {
		return apartado;
	}

	/**
	 * @param apartado the apartado to set
	 */
	public void setApartado(String apartado) {
		this.apartado = apartado;
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
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @param "conn" the connection of DB and "cargaId" the period
	*/
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		calle       	= rs.getString("CALLE");
		colonia     	= rs.getString("COLONIA");
		codPostal  		= rs.getString("COD_POSTAL");
		apartado	  	= rs.getString("APARTADO");
		paisId 		    = rs.getString("PAIS_ID");
		estadoId 	    = rs.getString("ESTADO_ID");
		ciudadId 		= rs.getString("CIUDAD_ID");
		fecha   		= rs.getString("FECHA");
		estado   		= rs.getString("ESTADO");
		telefono   		= rs.getString("TELEFONO");
		numero   		= rs.getString("NUMERO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, "+
				" CALLE, COLONIA, COD_POSTAL, APARTADO, " +
				" PAIS_ID, ESTADO_ID, CIUDAD_ID, " +
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, TELEFONO, NUMERO " +
				" FROM ENOC.ALUM_DIRECCION "+ 
				" WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}