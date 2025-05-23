// Clase para la tabla de Modulo
package aca.federacion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.*;

public class FedEvento{	
	private String eventoId;
	private String eventoNombre;
	private String eventoDescripcion;
	private String fechaIni;
	private String fechaFin;
	private String presidente;
	private String academico;
	private String desarrollo;
	private String financiero;
	private String ejecutivo;
	private String secretario;
	private String maestro;
	private String maestra;
	
	public FedEvento(){
		eventoId			= "";
		eventoNombre		= "";
		fechaIni			= "";
		fechaFin			= "";
		presidente			= "";
		academico			= "";
		desarrollo			= "";
		financiero			= "";
		ejecutivo			= "";
		secretario			= "";	
		eventoDescripcion	= "";
		maestro 			= "";
		maestra				= "";
	}
	
	
	/**
	 * @return the eventoId
	 */
	public String getEventoId() {
		return eventoId;
	}


	/**
	 * @param eventoId the eventoId to set
	 */
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	/**
	 * @return the eventoNombre
	 */
	public String getEventoNombre() {
		return eventoNombre;
	}

	/**
	 * @param eventoNombre the eventoNombre to set
	 */
	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	/**
	 * @return the fechaIni
	 */
	public String getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni the fechaIni to set
	 */
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	/**
	 * @return the presidente
	 */
	public String getPresidente() {
		return presidente;
	}

	/**
	 * @param presidente the presidente to set
	 */
	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	/**
	 * @return the desarrollo
	 */
	public String getDesarrollo() {
		return desarrollo;
	}

	/**
	 * @param desarrollo the desarrollo to set
	 */
	public void setDesarrollo(String desarrollo) {
		this.desarrollo = desarrollo;
	}

	/**
	 * @return the financiero
	 */
	public String getFinanciero() {
		return financiero;
	}

	/**
	 * @param financiero the financiero to set
	 */
	public void setFinanciero(String financiero) {
		this.financiero = financiero;
	}

	/**
	 * @return the ejecutivo
	 */
	public String getEjecutivo() {
		return ejecutivo;
	}

	/**
	 * @param ejecutivo the ejecutivo to set
	 */
	public void setEjecutivo(String ejecutivo) {
		this.ejecutivo = ejecutivo;
	}

	/**
	 * @return the secretario
	 */
	public String getSecretario() {
		return secretario;
	}

	/**
	 * @param secretario the secretario to set
	 */
	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}
	
	public String getEventoDescripcion() {
		return eventoDescripcion;
	}

	public void setEventoDescripcion(String eventoDescripcion) {
		this.eventoDescripcion = eventoDescripcion;
	}

	public String getMaestro() {
		return maestro;
	}
	
	public void setMaestro(String maestro) {
		this.maestro = maestro;
	}
	
	public String getMaestra() {
		return maestra;
	}
	
	public void setMaestra(String maestra) {
		this.maestra = maestra;
	}
		
	public String getAcademico() {
		return academico;
	}
	
	public void setAcademico(String academico) {
		this.academico = academico;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		eventoId			= rs.getString("EVENTO_ID");
 		eventoNombre		= rs.getString("EVENTO_NOMBRE");
 		eventoDescripcion	= rs.getString("EVENTO_DESCRIPCION");
 		fechaIni	 		= rs.getString("FECHA_INI");
 		fechaFin			= rs.getString("FECHA_FIN");
 		presidente			= rs.getString("PRESIDENTE");
 		academico			= rs.getString("ACADEMICO");
 		desarrollo			= rs.getString("DESARROLLO");
 		financiero 			= rs.getString("FINANCIERO");
 		ejecutivo 			= rs.getString("EJECUTIVO");
 		secretario 			= rs.getString("SECRETARIO");
 		maestro 			= rs.getString("MAESTRO");
 		maestra 			= rs.getString("MAESTRA");
 	}
	
	public void mapeaRegId( Connection conn, String eventoId ) throws SQLException, IOException{
		
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" EVENTO_ID, EVENTO_NOMBRE, EVENTO_DESCRIPCION, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN," +
	 			" PRESIDENTE, ACADEMICO, DESARROLLO, FINANCIERO, EJECUTIVO, SECRETARIO, MAESTRO, MAESTRA"+	 			
	 			" FROM ENOC.FED_EVENTO WHERE EVENTO_ID = ?"); 
	 		ps.setString(1, eventoId);			
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedEventoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 	} 
 	
}