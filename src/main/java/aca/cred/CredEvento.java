package aca.cred;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredEvento {
	private String eventoId;	
	private String eventoNombre;
	private String codigoInicial;	

	
	public CredEvento(){
		eventoId 				= "";
		eventoNombre  			= "";
		codigoInicial	  		= "";	
	}	
	
	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getEventoNombre() {
		return eventoNombre;
	}

	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	public String getCodigoInicial() {
		return codigoInicial;
	}

	public void setCodigoInicial(String codigoInicial) {
		this.codigoInicial = codigoInicial;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId 				= rs.getString("EVENTO_ID");
		eventoNombre			= rs.getString("EVENTO_NOMBRE");
		codigoInicial	 		= rs.getString("CODIGO_INICIAL");
	}
	
	public void mapeaRegId(Connection con, String eventoId) throws SQLException{		
			
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL FROM ENOC.CRED_EVENTO " +					
					"WHERE EVENTO_ID = TO_NUMBER(?,999) ");				
			ps.setString(1, eventoId);	
			rs = ps.executeQuery();			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}

}
	
	

  