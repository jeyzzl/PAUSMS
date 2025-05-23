/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class HcaTipo {
	private String tipoId;
	private String tipoNombre;
	private String orden;
	
	public HcaTipo(){
		tipoId		= "";
		tipoNombre	= "";
		orden		= "";
	}

	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * @return the tipoId
	 */
	public String getTipoId() {
		return tipoId;
	}

	/**
	 * @param tipoId the tipoId to set
	 */
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	/**
	 * @return the tipoNombre
	 */
	public String getTipoNombre() {
		return tipoNombre;
	}

	/**
	 * @param tipoNombre the tipoNombre to set
	 */
	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		tipoId		= rs.getString("TIPO_ID");
		tipoNombre	= rs.getString("TIPO_NOMBRE");
		orden		= rs.getString("ORDEN");
	}
	
	public void mapeaRegId(Connection con, String tipoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT TIPO_ID, TIPO_NOMBRE, ORDEN FROM ENOC.HCA_TIPO" + 
					" WHERE TIPO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, tipoId);			
			rs = ps.executeQuery();
			
			if(rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaTipoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}