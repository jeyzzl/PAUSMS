// Bean del Catalogo Cursos Equivalentes
package  aca.plan;

import java.sql.*;

public class MapaCursoEquiv{
	
	private String cursoId;
	private String cursoIdEquiv;	
	
	public MapaCursoEquiv(){
		cursoId				= "";		
		cursoIdEquiv 		= "";				
	}
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}
	
	public String getCursoIdEquiv(){
		return cursoIdEquiv;
	}
	
	public void setCursoIdEquiv( String cursoIdEquiv){
		this.cursoIdEquiv = cursoIdEquiv;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoId 			= rs.getString("CURSO_ID");
		cursoIdEquiv 		= rs.getString("CURSO_ID_EQUIV");		
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String cursoIdEquiv) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, CURSO_ID_EQUIV FROM ENOC.MAPA_CURSO_EQUIV "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_EQUIV = ? ");
				ps.setString(1, cursoId);
				ps.setString(2, cursoIdEquiv);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.EquivalenteUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}