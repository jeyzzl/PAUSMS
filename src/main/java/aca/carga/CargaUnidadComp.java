package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaUnidadComp {
	private String cursoCargaId;
	private String unidadId;	
	private String competenciaId;
	
	public CargaUnidadComp(){
		cursoCargaId   = "";
		unidadId 	   = "";
		competenciaId  = "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public String getCompetenciaId() {
		return competenciaId;
	}

	public void setCompetenciaId(String competenciaId) {
		this.competenciaId = competenciaId;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		unidadId 		= rs.getString("UNIDAD_ID");
		competenciaId	= rs.getString("COMPETENCIA_ID");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String unidadId, String competenciaId) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, UNIDAD_ID, COMPETENCIA_ID "+
				"FROM ENOC.CARGA_UNIDAD_COMP WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ? AND COMPETENCIA_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,unidadId);
			ps.setString(3,competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadComp|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}