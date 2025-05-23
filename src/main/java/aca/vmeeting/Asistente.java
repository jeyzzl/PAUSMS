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
public class Asistente {
	private int id;
	private String identificador;
	private int idSistema;
	private int idMeeting;
	private boolean moderador;
	private boolean enterado;
	
	public Asistente(){
		id				= 0;
		identificador	= "";
		idSistema		= 0;
		idMeeting		= 0;
		moderador		= false;
		enterado		= false;
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
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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

	/**
	 * @return the idMeeting
	 */
	public int getIdMeeting() {
		return idMeeting;
	}

	/**
	 * @param idMeeting the idMeeting to set
	 */
	public void setIdMeeting(int idMeeting) {
		this.idMeeting = idMeeting;
	}

	/**
	 * @return the moderador
	 */
	public boolean isModerador() {
		return moderador;
	}

	/**
	 * @param moderador the moderador to set
	 */
	public void setModerador(boolean moderador) {
		this.moderador = moderador;
	}

	/**
	 * @return the enterado
	 */
	public boolean isEnterado() {
		return enterado;
	}

	/**
	 * @param enterado the enterado to set
	 */
	public void setEnterado(boolean enterado) {
		this.enterado = enterado;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ASISTENTE"+ 
				"(ID, IDENTIFICADOR, ID_SISTEMA, ID_MEETING," +
				" MODERADOR, ENTERADO)"+
				" VALUES(?, ?, ?, ?," +
				" ?, ?)");			
			ps.setInt(1, id);
			ps.setString(2, identificador);
			ps.setInt(3, idSistema);
			ps.setInt(4, idMeeting);
			ps.setBoolean(5, moderador);
			ps.setBoolean(6, enterado);
			
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Asistente|insertReg|:"+ex);			
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
				" IDENTIFICADOR = ?," +
				" ID_SISTEMA = ?," +
				" ID_MEETING = ?," +
				" MODERADOR = ?," +
				" ENTERADO = ?" +
				" WHERE ID = ?");
				
			ps.setString(1, identificador);
			ps.setInt(2, idSistema);
			ps.setInt(3, idMeeting);
			ps.setBoolean(4, moderador);
			ps.setBoolean(5, enterado);
			ps.setInt(6, id);
			 			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false; 			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Asistente|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ASISTENTE"+ 
				" WHERE ID = ?");
			ps.setInt(1, id);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Asistente|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		id				= rs.getInt("ID");
		identificador	= rs.getString("IDENTIFICADOR");
		idSistema		= rs.getInt("ID_SISTEMA");
		idMeeting		= rs.getInt("ID_MEETING");
		moderador		= rs.getBoolean("MODERADOR");
		enterado		= rs.getBoolean("ENTERADO");
	}
	
	public void mapeaRegId( Connection conn, int id) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" ID, IDENTIFICADOR, ID_SISTEMA, ID_MEETING," +
	 			" MODERADOR, ENTERADO" +
	 			" FROM ASISTENTE WHERE ID = ?"); 
	 		ps.setInt(1, id);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Asistente|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT ID FROM ASISTENTE"+ 
				" WHERE ID = ?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Asistente|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(ID)+1 MAXIMO FROM ASISTENTE");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getInt("MAXIMO");
			if(maximo == 0)
				maximo = 1;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Asistente|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static boolean isModerator(Connection conn, String identificador, int idSistema, int idMeeting) throws SQLException{
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		
		try{
			ps = conn.prepareStatement("SELECT MODERADOR FROM ASISTENTE"+ 
				" WHERE IDENTIFICADOR = ?" +
				" AND ID_SISTEMA = ?" +
				" AND ID_MEETING = ?");
			ps.setString(1, identificador);
			ps.setInt(2, idSistema);
			ps.setInt(3, idMeeting);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = rs.getBoolean("MODERADOR");
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Asistente|isModerator|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}
