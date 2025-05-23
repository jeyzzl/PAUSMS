package aca.plan;

import java.sql.*;
import java.util.ArrayList;

public class OptativaUtil {
	
	public boolean insertReg(Connection conn, MapaOptativa mapaOptativa ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO MAPA_OPTATIVA"+
				"(CURSO_ID, OPTATIVA_ID ) VALUES( ?, ?)");	
			ps.setString(1, mapaOptativa.getCursoId());
			ps.setString(2, mapaOptativa.getOptativaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaOptativa mapaOptativa ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE MAPA_OPTATIVA " + 
					"SET OPTATIVA_ID = ?, WHERE CURSO_ID = ? ");
			ps.setString(1, mapaOptativa.getOptativaId());			
			ps.setString(2, mapaOptativa.getCursoId());
			
			if (ps.executeUpdate()== 1){
				ok = true;				
				
			}else{
				ok = false;
				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId, String optativaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM MAPA_OPTATIVA " +
				"WHERE CURSO_ID = ? AND OPTATIVA_ID =? ");
			ps.setString(1, cursoId);
			ps.setString(2, optativaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaOptativa mapeaRegId( Connection conn, String cursoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaOptativa mapaOptativa = new MapaOptativa();
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, OPTATIVA_ID " +
				"FROM MAPA_OPTATIVA WHERE CURSO_ID = ? ");
			ps.setString(1,cursoId);		
			rs = ps.executeQuery();
			if (rs.next()){
				mapaOptativa.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaOptativa;
	}	
	

	public boolean existeReg(Connection conn, String cursoId, String optativaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM MAPA_OPTATIVA WHERE CURSO_ID = ? AND OPTATIVA_ID= ?");
			ps.setString(1,cursoId);
			ps.setString(2,optativaId);
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void getOpta(Connection conn, String planId, String ciclo, String orden)throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		MapaOptativa mapaOptativa = new MapaOptativa();
		String query;
		query="SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' AND CICLO = '"+ciclo+"' "+orden; 
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next())
				mapaOptativa.setOptativa(rs.getString(1));
			
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|getOpta|:"+ex);			
		}finally{
			rs.close();
			ps.close();
		}
	}
	
	public ArrayList<MapaOptativa> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaOptativa> lisOptativa		= new ArrayList<MapaOptativa>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_ID, OPTATIVA_ID FROM MAPA_OPTATIVA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaOptativa optativa = new MapaOptativa();
				optativa.mapeaReg(rs);
				lisOptativa.add(optativa);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.optativaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisOptativa;
	}
	
	public ArrayList<MapaOptativa> getLista(Connection conn, String cursoId, String orden ) throws SQLException{
		
		ArrayList<MapaOptativa> lisOptativa	= new ArrayList<MapaOptativa>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_ID, OPTATIVA_ID FROM MAPA_OPTATIVA "+
				"WHERE CURSO_ID = '"+cursoId+"' " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaOptativa optativa = new MapaOptativa();
				optativa.mapeaReg(rs);
				lisOptativa.add(optativa);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.OptativaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisOptativa;
	}
}