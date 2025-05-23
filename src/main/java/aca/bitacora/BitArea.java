package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitArea {
	
	private String areaId;
	private String areaNombre;
	private String responsable;
	private String acceso;
	
	public BitArea(){
		areaId 			= "";
		areaNombre 		= "";
		responsable 	= "";
		acceso			= "";
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

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		areaId 			= rs.getString("AREA_ID");
		areaNombre 		= rs.getString("AREA_NOMBRE");
		responsable 	= rs.getString("RESPONSABLE");
		acceso			= rs.getString("ACCESO");
	}
	
	public void mapeaRegId( Connection conn, String areaId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		
		try{
			ps = conn.prepareStatement(" SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE, ACCESO "
									 + " FROM ENOC.BIT_AREA WHERE AREA_ID = "+areaId); 
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
    
}
