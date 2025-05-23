package aca.federacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FedVoto {
	private String eventoId;
	private String codigoPersonal;
	private String presidente;
	private String academico;
	private String desarrollo;
	private String financiero;
	private String ejecutivo;
	private String secretario;
	private String fecha;
	private String maestro;
	private String maestra;
	
	public FedVoto(){
		eventoId		= "";
		codigoPersonal	= "";
		presidente		= "";
		academico		= "";
		desarrollo		= "";
		financiero		= "";
		ejecutivo		= "";
		secretario		= "";
		fecha			= "";
		maestro 		= "";
		maestra			= "";
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPresidente() {
		return presidente;
	}

	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	public String getDesarrollo() {
		return desarrollo;
	}

	public void setDesarrollo(String desarrollo) {
		this.desarrollo = desarrollo;
	}

	public String getFinanciero() {
		return financiero;
	}

	public void setFinanciero(String financiero) {
		this.financiero = financiero;
	}

	public String getEjecutivo() {
		return ejecutivo;
	}

	public void setEjecutivo(String ejecutivo) {
		this.ejecutivo = ejecutivo;
	}

	public String getSecretario() {
		return secretario;
	}

	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId		= rs.getString("EVENTO_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		presidente		= rs.getString("PRESIDENTE");
		academico		= rs.getString("ACADEMICO");
		desarrollo		= rs.getString("DESARROLLO");
		financiero		= rs.getString("FINANCIERO");
		ejecutivo		= rs.getString("EJECUTIVO");
		secretario		= rs.getString("SECRETARIO");
		fecha			= rs.getString("FECHA");
		maestro			= rs.getString("MAESTRO");
		maestra			= rs.getString("MAESTRA");
	}
	
	public void mapeaRegId( Connection conn, String eventoId, String codigoPersonal ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FED_VOTO " + 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
				"AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
}