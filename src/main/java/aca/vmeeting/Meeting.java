/**
 * 
 */
package aca.vmeeting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class Meeting {
	private int id;
	private String nombre;
	private String fInicio;
	private String fFinal;
	private String bbbMeetingId;
	private String bbbAttendeePw;
	private String bbbModeratorPw;
	private int bbbVoiceBridge;
	private boolean bbbRecord;
	private String identificadorOwner;
	private int idSistema;
	
	public Meeting(){
		id					= 0;
		nombre				= "";
		fInicio				= "";
		fFinal				= "";
		bbbMeetingId		= "";
		bbbAttendeePw		= "";
		bbbModeratorPw		= "";
		bbbVoiceBridge		= 0;
		bbbRecord			= false;
		identificadorOwner	= "";
		idSistema			= 0;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the fInicio
	 */
	public String getFInicio() {
		return fInicio;
	}

	/**
	 * @param inicio the fInicio to set
	 */
	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	/**
	 * @return the fFinal
	 */
	public String getFFinal() {
		return fFinal;
	}

	/**
	 * @param final1 the fFinal to set
	 */
	public void setFFinal(String final1) {
		fFinal = final1;
	}

	/**
	 * @return the bbbMeetingId
	 */
	public String getBbbMeetingId() {
		return bbbMeetingId;
	}

	/**
	 * @param bbbMeetingId the bbbMeetingId to set
	 */
	public void setBbbMeetingId(String bbbMeetingId) {
		this.bbbMeetingId = bbbMeetingId;
	}

	/**
	 * @return the bbbAtendeePw
	 */
	public String getBbbAttendeePw() {
		return bbbAttendeePw;
	}

	/**
	 * @param bbbAtendeePw the bbbAtendeePw to set
	 */
	public void setBbbAttendeePw(String bbbAttendeePw) {
		this.bbbAttendeePw = bbbAttendeePw;
	}

	/**
	 * @return the bbbModeratorPw
	 */
	public String getBbbModeratorPw() {
		return bbbModeratorPw;
	}

	/**
	 * @param bbbModeratorPw the bbbModeratorPw to set
	 */
	public void setBbbModeratorPw(String bbbModeratorPw) {
		this.bbbModeratorPw = bbbModeratorPw;
	}

	/**
	 * @return the bbbVoiceBridge
	 */
	public int getBbbVoiceBridge() {
		return bbbVoiceBridge;
	}

	/**
	 * @param bbbVoiceBridge the bbbVoiceBridge to set
	 */
	public void setBbbVoiceBridge(int bbbVoiceBridge) {
		this.bbbVoiceBridge = bbbVoiceBridge;
	}

	/**
	 * @return the bbbRecord
	 */
	public boolean isBbbRecord() {
		return bbbRecord;
	}

	/**
	 * @param bbbRecord the bbbRecord to set
	 */
	public void setBbbRecord(boolean bbbRecord) {
		this.bbbRecord = bbbRecord;
	}

	/**
	 * @return the identificadorOwner
	 */
	public String getIdentificadorOwner() {
		return identificadorOwner;
	}

	/**
	 * @param identificadorOwner the identificadorOwner to set
	 */
	public void setIdentificadorOwner(String identificadorOwner) {
		this.identificadorOwner = identificadorOwner;
	}

	/**
	 * @return the idSistema
	 */
	public int getIdSistema() {
		return idSistema;
	}

	/**
	 * @param idSistema the idSistema to set
	 */
	public void setIdSistema(int idSistema) {
		this.idSistema = idSistema;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO MEETING"+ 
				"(ID, NOMBRE, F_INICIO, F_FINAL," +
				" BBB_MEETING_ID, BBB_ATTENDEE_PW, BBB_MODERATOR_PW, BBB_VOICE_BRIDGE," +
				" BBB_RECORD, IDENTIFICADOR_OWNER, ID_SISTEMA)"+
				" VALUES(?, ?, TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI'), TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI')," +
				" ?, ?, ?, ?," +
				" ?, ?, ?)");			
			ps.setInt(1, id);
			ps.setString(2, nombre);
			ps.setString(3, fInicio);
			ps.setString(4, fFinal);
			ps.setString(5, bbbMeetingId);
			ps.setString(6, bbbAttendeePw);
			ps.setString(7, bbbModeratorPw);
			ps.setInt(8, bbbVoiceBridge);
			ps.setBoolean(9, bbbRecord);
			ps.setString(10, identificadorOwner);
			ps.setInt(11, idSistema);
			
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Meeting|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{ 		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE MEETING"+ 
				" SET"+		
				" NOMBRE = ?," +
				" F_INICIO = TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI')," +
				" F_FINAL = TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI')," +
				" BBB_MEETING_ID = ?," +
				" BBB_ATTENDEE_PW = ?," +
				" BBB_MODERATOR_PW = ?," +
				" BBB_VOICE_BRIDGE = ?," +
				" BBB_RECORD = ?," +
				" IDENTIFICADOR_OWNER = ?," +
				" ID_SISTEMA = ?" +
				" WHERE ID = ?");
				
			ps.setString(1, nombre);
			ps.setString(2, fInicio);
			ps.setString(3, fFinal);
			ps.setString(4, bbbMeetingId);
			ps.setString(5, bbbAttendeePw);
			ps.setString(6, bbbModeratorPw);
			ps.setInt(7, bbbVoiceBridge);
			ps.setBoolean(8, bbbRecord);
			ps.setString(9, identificadorOwner);
			ps.setInt(10, idSistema);
			ps.setInt(11, id);
			 			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false; 			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Meeting|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM MEETING"+ 
				" WHERE ID = ?");
			ps.setInt(1, id);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Meeting|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		id					= rs.getInt("ID");
		nombre				= rs.getString("NOMBRE");
		fInicio				= rs.getString("F_INICIO");
		fFinal				= rs.getString("F_FINAL");
		bbbMeetingId		= rs.getString("BBB_MEETING_ID");
		bbbAttendeePw		= rs.getString("BBB_ATTENDEE_PW");
		bbbModeratorPw		= rs.getString("BBB_MODERATOR_PW");
		bbbVoiceBridge		= rs.getInt("BBB_VOICE_BRIDGE");
		bbbRecord			= rs.getBoolean("BBB_RECORD");
		identificadorOwner	= rs.getString("IDENTIFICADOR_OWNER");
		idSistema			= rs.getInt("ID_SISTEMA");
	}
	
	public void mapeaRegId( Connection conn, int id) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" ID, NOMBRE, TO_CHAR(F_INICIO, 'DD/MM/YYYY HH24:MI') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY HH24:MI') AS F_FINAL," +
	 			" BBB_MEETING_ID, BBB_ATTENDEE_PW, BBB_MODERATOR_PW, BBB_VOICE_BRIDGE," +
	 			" BBB_RECORD, IDENTIFICADOR_OWNER, ID_SISTEMA" +
	 			" FROM MEETING WHERE ID = ?"); 
	 		ps.setInt(1, id);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Meeting|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		
		try{
			ps = conn.prepareStatement("SELECT ID FROM MEETING"+ 
				" WHERE ID = ?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Meeting|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public int maximoReg(Connection conn) throws SQLException{
		int maximo 				= 1;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ID)+1 MAXIMO FROM MEETING"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getInt("MAXIMO");
			if(maximo == 0) maximo = 1;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Meeting|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public void mapeaRegBbbMeetingId( Connection conn, String bbbMeetingId) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" ID, NOMBRE, TO_CHAR(F_INICIO, 'DD/MM/YYYY HH24:MI') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY HH24:MI') AS F_FINAL," +
	 			" BBB_MEETING_ID, BBB_ATTENDEE_PW, BBB_MODERATOR_PW, BBB_VOICE_BRIDGE," +
	 			" BBB_RECORD, IDENTIFICADOR_OWNER, ID_SISTEMA" +
	 			" FROM MEETING WHERE BBB_MEETING_ID = ?"); 
	 		ps.setString(1, bbbMeetingId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Meeting|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
	}
}
