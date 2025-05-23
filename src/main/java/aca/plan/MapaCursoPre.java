// Bean del Catalogo Cursos Electivos
package  aca.plan;

import java.sql.*;

public class MapaCursoPre{
	
	private String cursoId;
	private String cursoIdPre;	
	
	public MapaCursoPre(){
		cursoId		= "";		
		cursoIdPre 	= "";				
	}
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}
	
	public String getCursoIdPre(){
		return cursoIdPre;
	}
	
	public void setCursoIdPre( String cursoIdPre){
		this.cursoIdPre = cursoIdPre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoId 	= rs.getString("CURSO_ID");
		cursoIdPre 	= rs.getString("CURSO_ID_PRE");		
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String cursoIdPre) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_PRE = ? ");
				ps.setString(1, cursoId);
				ps.setString(2, cursoIdPre);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}