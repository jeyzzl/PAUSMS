// Bean del Catalogo de Materias de los grupos
package  aca.carga;

import java.sql.*;

public class CargaGrupoCurso{
	private String cursoCargaId;
	private String cursoId;
	private String origen;
	private String grupoHorario;
	
	public CargaGrupoCurso(){
		cursoCargaId	= "";
		cursoId			= "";
		origen			= "";
		grupoHorario	= "";
	}
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}	
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}	
	
	public String getOrigen(){
		return origen;
	}
	
	public void setOrigen( String origen){
		this.origen = origen;
	}
	
	public String getGrupoHorario() {
		return grupoHorario;
	}

	public void setGrupoHorario(String grupoHorario) {
		this.grupoHorario = grupoHorario;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		cursoId 		= rs.getString("CURSO_ID");
		origen	 		= rs.getString("ORIGEN");
		grupoHorario	= rs.getString("GRUPO_HORARIO");
	
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String cursoId ) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, CURSO_ID, ORIGEN, GRUPO_HORARIO "+
				"FROM ENOC.CARGA_GRUPO_CURSO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CURSO_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }		
		}		
	
	}

}