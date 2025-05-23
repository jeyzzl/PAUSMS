package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecCompetencia {
	private String competenciaId;
	private String competenciaNombre;
	
	public BecCompetencia(){
		competenciaId			= "";
		competenciaNombre		= "";
	}

	public String getCompetenciaId() {
		return competenciaId;
	}

	public void setCompetenciaId(String competenciaId) {
		this.competenciaId = competenciaId;
	}
	
	public String getCompetenciaNombre() {
		return competenciaNombre;
	}
	
	public void setCompetenciaNombre(String competenciaNombre) {
		this.competenciaNombre = competenciaNombre;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		competenciaId			= rs.getString("COMPETENCIA_ID");
		competenciaNombre   	= rs.getString("COMPETENCIA_NOMBRE");
	}
	
	public void mapeaRegId(Connection conn, String competenciaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * FROM ENOC.BEC_COMPETENCIA WHERE COMPETENCIA_ID = ? "); 
			
			ps.setString(1,  competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecCompetencia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}