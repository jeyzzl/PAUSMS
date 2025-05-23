package aca.admision;

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
}