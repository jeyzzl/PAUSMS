//Bean del Catalogo Cursos

package aca.plan;

import java.sql.*;
import java.util.ArrayList;

public class MapaOptativa {
	private String cursoId;
	private String optativaId;
	private ArrayList<String> opta;
		
	public MapaOptativa(){
		cursoId			= "";
		optativaId		= "";
		opta			= new ArrayList<String>();
	}
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}	
	
	public String getOptativaId(){
		return optativaId;
	}
	
	public void setOptativaId( String optativaId){
		this.optativaId = optativaId;
	}
	
	public void setOptativa (String opta){
		this.opta.add(opta);	
	}
	public ArrayList<String> getOptativa(){
		return opta;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		
		cursoId 		= rs.getString("CURSO_ID");
		optativaId 	= rs.getString("OPTATIVA_ID");	
	}
	
	public void mapeaRegId( Connection conn, String cursoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, OPTATIVA_ID " +
				"FROM MAPA_OPTATIVA WHERE CURSO_ID = ? ");
			ps.setString(1,cursoId);		
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}	
	
}