package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaGrupoCompetencia {
	private String cursoCargaId;
	private String competenciaId;	
	private String descripcion;
	
	public CargaGrupoCompetencia(){
		cursoCargaId    = "";
		competenciaId   = "";
		descripcion     = "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCompetenciaId() {
		return competenciaId;
	}

	public void setCompetenciaId(String competenciaId) {
		this.competenciaId = competenciaId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		competenciaId   = rs.getString("COMPETENCIA_ID");
		descripcion		= rs.getString("DESCRIPCION");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String competenciaId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT COMPETENCIA_ID, DESCRIPCION, CURSO_CARGA_ID "+
				"FROM ENOC.CARGA_GRUPO_COMPETENCIA WHERE CURSO_CARGA_ID = ? AND COMPETENCIA_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,competenciaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCompetencia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}