package aca.federacion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.*;

public class FedCandidato {
	
	private String eventoId;
	private String candidatoId;
	private String candidatoNombre;
	private String candidatos;
	private String orden;
	
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
	 * @return the candidatoId
	 */
	public String getCandidatoId() {
		return candidatoId;
	}
	
	/**
	 * @param candidatoId the candidatoId to set
	 */
	public void setCandidatoId(String candidatoId) {
		this.candidatoId = candidatoId;
	}
	
	/**
	 * @return the candidatoNombre
	 */
	public String getCandidatoNombre() {
		return candidatoNombre;
	}
	
	/**
	 * @param candidatoNombre the candidatoNombre to set
	 */
	public void setCandidatoNombre(String candidatoNombre) {
		this.candidatoNombre = candidatoNombre;
	}
	
	/**
	 * @return the candidatos
	 */
	public String getCandidatos() {
		return candidatos;
	}
	
	/**
	 * @param candidatos the candidatos to set
	 */
	public void setCandidatos(String candidatos) {
		this.candidatos = candidatos;
	}
	
	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}
	
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		eventoId			= rs.getString("EVENTO_ID");
 		candidatoId			= rs.getString("CANDIDATO_ID");
 		candidatoNombre		= rs.getString("CANDIDATO_NOMBRE");
 		candidatos	 		= rs.getString("CANDIDATOS");
 		orden				= rs.getString("ORDEN");
 	}
	
	public void mapeaRegId( Connection conn, String candidatoId ) throws SQLException, IOException{
		
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement(" SELECT"+
	 								   " EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, ORDEN "+	 			
	 								   " FROM ENOC.FED_CANDIDATO WHERE CANDIDATO_ID = ?"); 
	 		ps.setString(1, candidatoId);			
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedCandidatoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 	} 

}
