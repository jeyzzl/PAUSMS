package adm.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmAsesor {
	private String asesorId;	
	private String correo;
	private String chat;
	private String telefono;
	
	public AdmAsesor(){
		asesorId 	= "";
		correo		= "";
		chat		= "";
		telefono	= "";		
	}	


	/**
	 * @return the asesorId
	 */
	public String getAsesorId() {
		return asesorId;
	}


	/**
	 * @param asesorId the asesorId to set
	 */
	public void setAsesorId(String asesorId) {
		this.asesorId = asesorId;
	}


	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}


	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}


	/**
	 * @return the chat
	 */
	public String getChat() {
		return chat;
	}


	/**
	 * @param chat the chat to set
	 */
	public void setChat(String chat) {
		this.chat = chat;
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
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		asesorId		= rs.getString("ASESOR_ID");
		correo			= rs.getString("CORREO");
		chat  			= rs.getString("CHAT");
		telefono		= rs.getString("TELEFONO");		
	}
	
	public void mapeaRegId( Connection conn, String asesorId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT ASESOR_ID, CORREO,CHAT, TELEFONO" +
				" FROM SALOMON.ADM_ASESOR "+ 
				" WHERE ASESOR_ID = ?");
		
		ps.setString(1, asesorId);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ASESOR "+ 
					" WHERE ASESOR_ID = ?");
			ps.setString(1, asesorId);
						
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}