/**
 * 
 */
package aca.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class ModuloAyuda {
	private String opcionId;
	private String ayudaId;
	private String ayuda;
	
	public ModuloAyuda(){
		opcionId 	= "";
		ayudaId		= "";
		ayuda		= "";
	}

	/**
	 * @return the ayuda
	 */
	public String getAyuda() {
		return ayuda;
	}

	/**
	 * @param ayuda the ayuda to set
	 */
	public void setAyuda(String ayuda) {
		this.ayuda = ayuda;
	}

	/**
	 * @return the ayudaId
	 */
	public String getAyudaId() {
		return ayudaId;
	}

	/**
	 * @param ayudaId the ayudaId to set
	 */
	public void setAyudaId(String ayudaId) {
		this.ayudaId = ayudaId;
	}

	/**
	 * @return the opcionId
	 */
	public String getOpcionId() {
		return opcionId;
	}

	/**
	 * @param opcionId the opcionId to set
	 */
	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		opcionId 		= rs.getString("OPCION_ID");		
		ayudaId			= rs.getString("AYUDA_ID");		
		ayuda 			= rs.getString("AYUDA");
	}
	
	public void mapeaRegId(Connection conn, String opcionId, String ayudaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT OPCION_ID, AYUDA_ID, AYUDA" +
					" FROM ENOC.MODULO_AYUDA" + 
					" WHERE OPCION_ID = ?" +
					" AND AYUDA_ID = ?");
			ps.setString(1, opcionId);
			ps.setString(2, ayudaId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloAyuda|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}