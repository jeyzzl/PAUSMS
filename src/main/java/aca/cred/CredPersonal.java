package aca.cred;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredPersonal {
	private String eventoId;
	private String codigoPersonal;
	private String nombre;
	
	public CredPersonal(){
		eventoId 		= "";
		codigoPersonal = "";
		nombre 		= "";
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId			= rs.getString("EVENTO_ID");
		nombre 				= rs.getString("NOMBRE");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
	}
	
	public void mapeaRegId( Connection conn, String eventoId, String codigoPersonal) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"EVENTO_ID, CODIGO_PERSONAL, NOMBRE " +
				"FROM ENOC.CRED_PERSONAL WHERE EVENTO_ID = TO_NUMBER(?,999) AND CODIGO_PERSONAL = ?"); 
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
	public CredPersonal mapeaRegCodigoPersonal( Connection conn, String codigoPersonal, String eventoId) throws SQLException{
		
		CredPersonal per = new CredPersonal();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"EVENTO_ID, CODIGO_PERSONAL, NOMBRE " +
				"FROM ENOC.CRED_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND EVENTO_ID = '"+eventoId+"'"); 
			
			rs = ps.executeQuery();
			if (rs.next()){
				per.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|mapeaRegCodigoPersonal|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return per;
	}
	
}
