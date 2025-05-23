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
public class SistemasIguales {
	private int idSistema;
	private int idGrupo;
	
	public SistemasIguales(){
		idSistema = 0;
		idGrupo = 0;
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
	 * @return the idGrupo
	 */
	public int getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO SISTEMAS_IGUALES"+ 
				"(ID_SISTEMA, ID_GRUPO)"+
				" VALUES(?, ?)");			
			ps.setInt(1, idSistema);
			ps.setInt(2, idGrupo);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemasIguales|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SISTEMAS_IGUALES"+ 
				" WHERE ID_SISTEMA = ? AND ID_GRUPO = ?");
			ps.setInt(1, idSistema);
			ps.setInt(1, idGrupo);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemasIguales|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		idSistema	= rs.getInt("ID_SISTEMA");
		idGrupo		= rs.getInt("ID_GRUPO");
	}
	
	public void mapeaRegId( Connection conn, int idSistema, int idGrupo) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" ID_SISTEMA, ID_GRUPO" +
	 			" FROM SISTEMAS_IGUALES WHERE ID_SISTEMA = ? AND ID_GRUPO = ?"); 
	 		ps.setInt(1, idSistema);
	 		ps.setInt(2, idGrupo);
	 		
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemasIguales|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT ID FROM SISTEMAS_GRUPOS"+ 
				" WHERE ID_SISTEMA = ? AND ID_GRUPO = ?");
			ps.setInt(1, idSistema);
			ps.setInt(2, idGrupo);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemasIguales|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}
