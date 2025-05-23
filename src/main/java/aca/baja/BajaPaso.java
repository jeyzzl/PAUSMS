/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class BajaPaso {
	private String pasoId;
	private String pasoNombre;
	
	public BajaPaso(){
		pasoId		= "";
		pasoNombre	= "";
	}

	/**
	 * @return the pasoId
	 */
	public String getPasoId() {
		return pasoId;
	}

	/**
	 * @param pasoId the pasoId to set
	 */
	public void setPasoId(String pasoId) {
		this.pasoId = pasoId;
	}

	/**
	 * @return the pasoNombre
	 */
	public String getPasoNombre() {
		return pasoNombre;
	}

	/**
	 * @param pasoNombre the pasoNombre to set
	 */
	public void setPasoNombre(String pasoNombre) {
		this.pasoNombre = pasoNombre;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		pasoId		= rs.getString("PASO_ID");
		pasoNombre	= rs.getString("PASO_NOMBRE");
	}
	
	public void mapeaRegId(Connection conn, String pasoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PASO_ID, PASO_NOMBRE" +
					" FROM ENOC.BAJA_PASO" + 
					" WHERE PASO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, pasoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaPasoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}