package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaGrupoAsistencia {

	private String cursoCargaGrupoId;
	private String folio;
	private String codigoPersonal;
	private String cursoId;
	private String estado;
	
	public CargaGrupoAsistencia() {
		cursoCargaGrupoId	= "";
		folio		 		= "";
		codigoPersonal		= "";
		cursoId				= "";
		estado				= "";
	}

	public String getCargaGrupoId() {
		return cursoCargaGrupoId;
	}

	public void setCargaGrupoId(String cargaGrupoId) {
		this.cursoCargaGrupoId = cargaGrupoId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaGrupoId 	= rs.getString("CURSO_CARGA_ID");
		folio 				= rs.getString("FOLIO");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		cursoId				= rs.getString("CURSO_ID");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaGrupoId, String folio, String codigoPersonal) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, FOLIO, CODIGO_PERSONAL, CURSO_ID, ESTADO FROM ENOC.CARGA_GRUPO_ASISTENCIA" +
					" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ?"); 
			ps.setString(1, cursoCargaGrupoId);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
}
