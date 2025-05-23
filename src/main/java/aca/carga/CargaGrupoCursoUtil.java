// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CargaGrupoCursoUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoCurso curso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_CURSO(CURSO_CARGA_ID, CURSO_ID, ORIGEN, GRUPO_HORARIO ) "+
				"VALUES( ?, ?, ?, ?)");
			ps.setString(1, curso.getCursoCargaId());
			ps.setString(2, curso.getCursoId());
			ps.setString(3, curso.getOrigen());
			ps.setString(4, curso.getGrupoHorario());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoCurso curso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_CURSO SET ORIGEN = ?, GRUPO_HORARIO = ? WHERE CURSO_CARGA_ID = ? AND CURSO_ID = ?");
			ps.setString(1, curso.getOrigen());
			ps.setString(2, curso.getGrupoHorario());
			ps.setString(3, curso.getCursoCargaId());			
			ps.setString(4, curso.getCursoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String cursoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ? AND CURSO_ID = ? ");
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteGrupo(Connection conn, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);			
			
			if (ps.executeUpdate() >= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|deleteGrupo|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoCurso mapeaRegId( Connection conn, String cursoCargaId, String cursoId ) throws SQLException{
		
		CargaGrupoCurso curso = new CargaGrupoCurso();
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
				curso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }		
		}
		
		return curso;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String cursoId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_CURSO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CURSO_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String cursoIdOrigen(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String cursoId			= "";
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND ORIGEN = 'O'");
			ps.setString(1, cursoCargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				cursoId = rs.getString("CURSO_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|cursoIdOrigen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cursoId;
	}
	
	public static String getPlanes(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String planes			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_PLAN(CURSO_ID) AS PLAN FROM CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);				
			rs = ps.executeQuery();
			while (rs.next()) {
				planes += ","+rs.getString("PLAN");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|cursoIdOrigen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return planes;
	}
	
		
	public ArrayList<CargaGrupoCurso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoCurso> lisCurso		= new ArrayList<CargaGrupoCurso>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CURSO_ID, ORIGEN, GRUPO_HORARIO "+
			"FROM ENOC.CARGA_GRUPO_CURSO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoCurso curso = new CargaGrupoCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CursosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<CargaGrupoCurso> getLista(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoCurso> lisCurso		= new ArrayList<CargaGrupoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CURSO_ID, ORIGEN, GRUPO_HORARIO "+
				"FROM ENOC.CARGA_GRUPO_CURSO "+ 
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoCurso curso = new CargaGrupoCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCursoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}

	
	public String obtenEstado(Connection conn, String cursoCargaId) throws SQLException{
		
		String estado="";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT ESTADO "+
				"FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				
				estado=rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCursoUtil|obtenEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	}
	
	public static ArrayList<String> getPlanes(Connection conn, String cargaId, String carreraId) throws SQLException{
		
		ArrayList<String> lista			= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando =  "SELECT DISTINCT(ENOC.CURSO_PLAN(CURSO_ID)) AS PLAN_ID " +
					" FROM ENOC.CARGA_GRUPO_CURSO " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' " +
					" AND (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ENOC.CURSO_PLAN(CURSO_ID)) = '"+carreraId+"' ";
			
			rs = st.executeQuery(comando);
			
			while (rs.next()){
				lista.add(rs.getString("PLAN_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCursoUtil|getPlanes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public static ArrayList<String> getPlanesAll(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<String> lista			= new ArrayList<String>();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando = "";
		
		try{
			comando =  "SELECT DISTINCT(ENOC.CURSO_PLAN(CURSO_ID)) AS PLAN_ID," +
					" (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA_ID" +
					" FROM ENOC.CARGA_GRUPO_CURSO " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			
			while(rs.next()){
				lista.add(rs.getString("CARRERA_ID")+"-}"+rs.getString("PLAN_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCursoUtil|getPlanesAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public ArrayList<CargaGrupoCurso> getMateriasSalon(Connection conn, String salonId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoCurso> lisCurso		= new ArrayList<CargaGrupoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_HORA WHERE SALON_ID = '"+salonId+"') "
					+ "AND ORIGEN = 'O'"+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoCurso curso = new CargaGrupoCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCursoUtil|getMateriasSalon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public HashMap<String, String> mapMateriasOrigenAlumno(Connection conn, String codigoPersonal ) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CURSO_CARGA_ID, CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')"; 
		
			rs = st.executeQuery(comando);
			while (rs.next()){			
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("CURSO_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CargaGrupoCursoUtil|mapMateriasOrigenAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapMateriasDelGrupo(Connection conn, String cursoCargaId ) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID ='"+cursoCargaId+"'"; 
		
			rs = st.executeQuery(comando);
			while (rs.next()){			
				mapa.put(rs.getString("CURSO_ID"), rs.getString("CURSO_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CargaGrupoCursoUtil|mapMateriasDelGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapCursosUsados(Connection conn, String planId ) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CURSO_ID, COUNT(*) AS TOTAL FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"') GROUP BY CURSO_ID"; 
		
			rs = st.executeQuery(comando);
			while (rs.next()){			
				mapa.put(rs.getString("CURSO_ID"), rs.getString("CURSO_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CargaGrupoCursoUtil|mapCursosUsados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
}