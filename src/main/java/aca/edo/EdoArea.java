/**
 * 
 */
package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EdoArea {
	private String areaId;
	private String areaNombre;
	private String areaTitulo;
	
	public EdoArea(){
		areaId				= "";
		areaNombre			= "";
		areaTitulo 			= "";
	}
	
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaNombre() {
		return areaNombre;
	}

	public void setAreaNombre(String areaNombre) {
		this.areaNombre = areaNombre;
	}
	
	public String getAreaTitulo() {
		return areaTitulo;
	}

	public void setAreaTitulo(String areaTitulo) {
		this.areaTitulo = areaTitulo;
	}

	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		areaId			= rs.getString("AREA_ID");
		areaNombre		= rs.getString("AREA_NOMBRE");
		areaTitulo		= rs.getString("AREA_TITULO");
	}
	
	public void mapeaRegId(Connection con, String edoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT AREA_ID, AREA_NOMBRE, AREA_TITULO" +
					" FROM ENOC.EDO_AREA" + 
					" WHERE AREA_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, areaId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}	
}