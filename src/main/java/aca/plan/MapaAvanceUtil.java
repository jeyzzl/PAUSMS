package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MapaAvanceUtil {
	
	public boolean insertReg(Connection conn, MapaAvance mapaAvance ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_AVANCE"+ 
				"(PLAN_ID, CICLO, TIPOCURSO_ID, CREDITOS ) "+
				"VALUES( ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99999.99'))");
			
			ps.setString(1, mapaAvance.getPlanId());
			ps.setString(2, mapaAvance.getCiclo());
			ps.setString(3, mapaAvance.getTipocursoId());
			ps.setString(4, mapaAvance.getCreditos());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaAvance mapaAvance ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_AVANCE " + 
			 	 "SET CREDITOS = TO_NUMBER(?,'99999.99') "+
				 "WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') AND TIPOCURSO_ID = TO_NUMBER(?,'99') ");
			
			ps.setString(1, mapaAvance.getCreditos());			
			ps.setString(2, mapaAvance.getPlanId());
			ps.setString(3, mapaAvance.getCiclo());
			ps.setString(4, mapaAvance.getTipocursoId());
			
			if (ps.executeUpdate()== 1){
				ok = true;								
			}else{
				ok = false;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String planId, String ciclo, String tipocursoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') AND TIPOCURSO_ID = TO_NUMBER(?,'99') ");
			
			ps.setString(1, planId);
			ps.setString(2, ciclo);
			ps.setString(3, tipocursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaAvance mapeaRegId( Connection conn, String planId, String ciclo, String tipocursoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaAvance mapaAvance = new MapaAvance();
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CICLO, TIPOCURSO_ID, CREDITOS FROM ENOC.MAPA_AVANCE"
					+ " WHERE PLAN_ID = '"+planId+"' AND CICLO = '"+ciclo+"' AND TIPOCURSO_ID = '"+tipocursoId+"' ");
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapaAvance.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaAvance;
	}
	
	public boolean existeReg(Connection conn, String planId, String ciclo, String tipocursoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') AND TIPOCURSO_ID = TO_NUMBER(?,'99') "); 
			
			ps.setString(1, planId);
			ps.setString(2, ciclo);
			ps.setString(3, tipocursoId);
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public static String getCreditosPlan(Connection conn, String planId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String creditos			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ?"); 
			
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			
			if (rs.next()){
				creditos = rs.getString("TOTAL");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getCreditosPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return creditos;
	}
	
	public static String getOptativos(Connection conn, String planId, String ciclo) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String creditos			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = '"+planId+"' AND CICLO = '"+ciclo+"' AND TIPOCURSO_ID='7'"); 
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				creditos = rs.getString("TOTAL");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getOptativos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return creditos;
	}
	
	public ArrayList<MapaAvance> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MapaAvance> list		= new ArrayList<MapaAvance>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_AVANCE "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaAvance obj = new MapaAvance();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return list;	
	}
	
	
	public ArrayList<MapaAvance> getListPlan(Connection conn, String planId, String orden) throws SQLException{
		
		ArrayList<MapaAvance> list		= new ArrayList<MapaAvance>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaAvance obj = new MapaAvance();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getListPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return list;	
	}
	
	public ArrayList<String[]> getListCreditosPorTipoCurso(Connection conn, String condiciones, String orden) throws SQLException{
		ArrayList<String[]> list		= new ArrayList<String[]>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT TIPOCURSO_ID, SUM(CREDITOS) AS CREDITOS FROM ENOC.MAPA_AVANCE " +
					(!condiciones.equals("")?condiciones:"") +
					" GROUP BY TIPOCURSO_ID " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				String[] arr = new String[2];
				arr[0] = rs.getString("TIPOCURSO_ID");
				arr[1] = rs.getFloat("CREDITOS")+"";
				list.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getListCreditosPorTipoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;	
	}
	
	public HashMap<String, MapaAvance> getMapAll(Connection conn, boolean keyPlanId, boolean keyCiclo, boolean keyTipoCursoId, String condicion) throws SQLException{
		
		HashMap<String, MapaAvance> map		= new HashMap<String, MapaAvance>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_AVANCE " + condicion;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaAvance obj = new MapaAvance();
				obj.mapeaReg(rs);
				String key = "";
				if(keyPlanId || (!keyPlanId&&!keyCiclo&&!keyTipoCursoId)) key += (obj.getPlanId()+",");
				if(keyCiclo) key += (obj.getCiclo()+",");
				if(keyTipoCursoId) key += (obj.getTipocursoId()+",");
				key = key.substring(0, key.length()-1);
				map.put(key, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;	
	}
	
	public HashMap<String, MapaAvance> getMapAll(Connection conn, String condicion) throws SQLException{
		
		HashMap<String, MapaAvance> map		= new HashMap<String, MapaAvance>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_AVANCE " + condicion;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaAvance obj = new MapaAvance();
				obj.mapeaReg(rs);				
				map.put(obj.getPlanId()+obj.getCiclo()+obj.getTipocursoId(), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;	
	}
	
	public HashMap<String, String> getMapCreditos(Connection conn, String planId) throws SQLException{
		
		HashMap<String, String> map		= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{			
			comando = " SELECT CICLO, SUM(CREDITOS) AS CREDITOS FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = TRIM('"+planId+"') GROUP BY CICLO ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("CICLO"), rs.getString("CREDITOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|getMapCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;	
	}
}