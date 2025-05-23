// Clase Util para la tabla de Prerrequisitos
package aca.plan;

import java.sql.*;
import java.util.ArrayList;

public class PrerrequisitoUtil{
	
	public boolean insertReg(Connection conn, MapaCursoPre mapaCursoPre ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_CURSO_PRE"+ 
				"(CURSO_ID, CURSO_ID_PRE ) VALUES( ?, ? )");	
					
			ps.setString(1, mapaCursoPre.getCursoId());
			ps.setString(2, mapaCursoPre.getCursoIdPre());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, MapaCursoPre mapaCursoPre ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_PRE = ? ");
			ps.setString(1, mapaCursoPre.getCursoId());
			ps.setString(2, mapaCursoPre.getCursoIdPre());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaCursoPre mapeaRegId( Connection conn, String cursoId, String cursoIdPre) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaCursoPre mapaCursoPre = new MapaCursoPre();
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_PRE = ? ");
				ps.setString(1, cursoId);
				ps.setString(2, cursoIdPre);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaCursoPre.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaCursoPre;
	}
	
	public boolean existeReg(Connection conn, String cursoId, String cursoIdPre) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_PRE = ? ");
			ps.setString(1, cursoId);
			ps.setString(2, cursoIdPre);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int getNumPre(Connection conn, String planId) throws SQLException{
		ResultSet 			rs	= null;
		PreparedStatement	ps	= null;
		int nPrePlan =0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_ID) AS PRE FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE SUBSTR(CURSO_ID,1,8) = ?");
			ps.setString(1, planId);			
			
			rs = ps.executeQuery();
			if (rs.next())
				nPrePlan = rs.getInt("PRE");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|getNumPre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nPrePlan;
	}
		
	public ArrayList<MapaCursoPre> getLista(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCursoPre> lisPre		= new ArrayList<MapaCursoPre>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE "+ 
					"WHERE ENOC.CURSO_PLAN(CURSO_ID) = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoPre pre = new MapaCursoPre();
				pre.mapeaReg(rs);
				lisPre.add(pre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
			
	public ArrayList<MapaCursoPre> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaCursoPre> lisPre		= new ArrayList<MapaCursoPre>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoPre pre = new MapaCursoPre();
				pre.mapeaReg(rs);
				lisPre.add(pre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	//listor que regresa los prerrequisitos de una materia
	
	public ArrayList<MapaCursoPre> getListPrerrequisioMateria(Connection conn, String cursoId, String orden ) throws SQLException{
		
		ArrayList<MapaCursoPre> lisPre		= new ArrayList<MapaCursoPre>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = '"+cursoId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCursoPre pre = new MapaCursoPre();
				pre.mapeaReg(rs);
				lisPre.add(pre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|getListPrerrequisitoMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	// listor que regresa las materias de los semestres anteriores
	
	public ArrayList<MapaCurso> getListaPrerrequisito(Connection conn, String planId, int ciclo, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso		= new ArrayList<MapaCurso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, HT, HP, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, NOTA_APROBATORIA, ESTADO,"
					+ " TIPOCURSO_ID,UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE,HI,OBLIGATORIO,COMPLETO"
					+ " HH,HFD,HSS,HAS,HTOT,HORARIO,AREA_ID"
					+ " FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = '"+planId+"'"
					+ " AND CICLO < '"+ciclo+"' "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|getListaPrerrequisito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	// Funcion que regresa los prerrequisitos de una materia
	
	public String getPrerrequisitos(Connection conn, String cursoId) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String prerrequisitos	= "";
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = '"+cursoId+"' ORDER BY 1  "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
					prerrequisitos = rs.getString("CURSO_ID_PRE") + " " + prerrequisitos;			
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PrerrequisitoUtil|getPrerrequisitos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return prerrequisitos;
	}
}