// Clase Util para la tabla de Prerrequisitos
package aca.plan;

import java.sql.*;
import java.util.ArrayList;

public class EquivalenteUtil{
	
	public boolean insertReg(Connection conn, MapaCursoEquiv mapaCursoEquiv ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_CURSO_EQUIV"+ 
				"(CURSO_ID, CURSO_ID_EQUIV ) VALUES( ?, ? )");	
					
			ps.setString(1, mapaCursoEquiv.getCursoId());
			ps.setString(2, mapaCursoEquiv.getCursoIdEquiv());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.EquivalenteUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, MapaCursoEquiv mapaCursoEquiv ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_CURSO_EQUIV "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_EQUIV = ? ");
			ps.setString(1, mapaCursoEquiv.getCursoId());
			ps.setString(2, mapaCursoEquiv.getCursoIdEquiv());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.EquivalenteUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaCursoEquiv mapeaRegId( Connection conn, String cursoId, String cursoIdEquiv) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaCursoEquiv mapaCursoEquiv = new MapaCursoEquiv();
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, CURSO_ID_EQUIV FROM ENOC.MAPA_CURSO_EQUIV "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_EQUIV = ? ");
				ps.setString(1, cursoId);
				ps.setString(2, cursoIdEquiv);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaCursoEquiv.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.EquivalenteUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaCursoEquiv;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String cursoIdEquiv) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_CURSO_EQUIV "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_EQUIV = ? ");
			ps.setString(1, cursoId);
			ps.setString(2, cursoIdEquiv);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.EquivalenteUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public ArrayList<MapaCursoEquiv> getLista(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCursoEquiv> lisEquivalente	= new ArrayList<MapaCursoEquiv>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando				= "";
		
		try{
			comando = "SELECT CURSO_ID, CURSO_ID_EQUIV FROM ENOC.MAPA_CURSO_EQUIV "+ 
					"WHERE ENOC.CURSO_PLAN(CURSO_ID) = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoEquiv equivalente = new MapaCursoEquiv();
				equivalente.mapeaReg(rs);
				lisEquivalente.add(equivalente);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.EquivalenteUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEquivalente;
	}
			
	public ArrayList<MapaCursoEquiv> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaCursoEquiv> lisEquivalente	= new ArrayList<MapaCursoEquiv>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando				= "";
		
		try{
			comando = "SELECT CURSO_ID, CURSO_ID_EQUIV FROM ENOC.MAPA_CURSO_EQUIV "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoEquiv equivalente = new MapaCursoEquiv();
				equivalente.mapeaReg(rs);
				lisEquivalente.add(equivalente);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.EquivalenteUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEquivalente;
	}
}