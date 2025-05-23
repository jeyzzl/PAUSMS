package aca.federacion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.*;

public class FedVotos {
	
	private String eventoId;
	private String candidatoId;
	private String codigoPersonal;
	private String fecha;
	private String voto;
	
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
	
	/**
	 * @return the voto
	 */
	public String getVoto() {
		return voto;
	}
	
	/**
	 * @param voto the voto to set
	 */
	public void setVoto(String voto) {
		this.voto = voto;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		eventoId			= rs.getString("EVENTO_ID");
 		candidatoId			= rs.getString("CANDIDATO_ID");
 		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
 		fecha		 		= rs.getString("FECHA");
 		voto				= rs.getString("VOTO");
 	}
	
	public void mapeaRegId( Connection conn, String eventoId, String candidatoId, String codigoPersonal) throws SQLException, IOException{
		
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement(" SELECT"+
	 								   " EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD,MM,YYYY') AS FECHA, VOTO "+	 			
	 								   " FROM ENOC.FED_VOTOS WHERE EVENTO_ID = ?, CANDIDATO_ID = ?, CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, eventoId);
	 		ps.setString(2, candidatoId);
	 		ps.setString(3, codigoPersonal);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotosUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		
 	} 

}
