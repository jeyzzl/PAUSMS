// Clase Util para la tabla de Mapa_Plan
package aca.plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class PlanUtil{		
	
	public boolean insertReg(Connection conn, MapaPlan plan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_PLAN"+ 
				"(PLAN_ID, CARRERA_ID, NOMBRE_PLAN, F_INICIO, F_FINAL, F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL," +
				"ESTADO, ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL)"+
				" VALUES( ?, ?, ?, "+
				" TO_DATE(?,'DD/MM/YYYY'),"+
				" TO_DATE(?,'DD/MM/YYYY'),"+
				" TO_DATE(?,'DD/MM/YYYY'),"+
				" TO_NUMBER(?,'999'),"+
				" TO_NUMBER(?,'99'),"+
				" TO_NUMBER(?,'99'), " +
				" ?, ?, ?, ?, ?," +
				" TO_NUMBER(?,'99'),"+				
				" TO_NUMBER(?,'99'),"+
				" ?,?,?,?,?,?)");
				
			ps.setString(1, plan.getPlanId());
			ps.setString(2, plan.getCarreraId());
			ps.setString(3, plan.getNombrePlan());
			ps.setString(4, plan.getFInicio());
			ps.setString(5, plan.getFFinal());
			ps.setString(6, plan.getFActualiza());
			ps.setString(7, plan.getNumCursos());
			ps.setString(8, plan.getMinimo());
			ps.setString(9, plan.getMaximo());
			ps.setString(10, plan.getCarreraSe());
			ps.setString(11, plan.getRvoe());
			ps.setString(12, plan.getOficial());
			ps.setString(13, plan.getEstado());
			ps.setString(14, plan.getEnLinea());
			ps.setString(15, plan.getCiclos());			
			ps.setString(16, plan.getNotaExtra());
			ps.setString(17, plan.getGeneral());
			ps.setString(18, plan.getPlanSE());
			ps.setString(19, plan.getNombrePlanMujer());
			ps.setString(20, plan.getClaveProfesiones());
			ps.setString(21, plan.getPrecio());
			ps.setString(22, plan.getRvoeInicial());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaPlan plan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_PLAN"
					+ " SET CARRERA_ID = ?, NOMBRE_PLAN = ?,"
					+ " F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " F_FINAL = TO_DATE(?,'DD/MM/YYYY'),"
					+ " F_ACTUALIZA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " NUM_CURSOS = TO_NUMBER(?,'999'),"
					+ " MINIMO = TO_NUMBER(?,'99'),"
					+ " MAXIMO = TO_NUMBER(?,'99'),"
					+ " CARRERA_SE = ?,"
					+ " RVOE = ?,"
					+ " OFICIAL = ?,"
					+ " ESTADO = ?,"
					+ " ENLINEA = ?,"
					+ " CICLOS = TO_NUMBER(?,'99'),"
					+ " NOTA_EXTRA = TO_NUMBER(?,'99'),"
					+ " GENERAL = ?,"
					+ " PLAN_SE = ?,"
					+ " NOMBRE_PLAN_MUJER = ?,"
					+ " CLAVE_PROFESIONES = ?,"
					+ " PRECIO = ?,"
					+ " RVOE_INICIAL = ?"
					+ " WHERE PLAN_ID = ?");
			
			ps.setString(1, plan.getCarreraId());
			ps.setString(2, plan.getNombrePlan());
			ps.setString(3, plan.getFInicio());
			ps.setString(4, plan.getFFinal());
			ps.setString(5, plan.getFActualiza());
			ps.setString(6, plan.getNumCursos());
			ps.setString(7, plan.getMinimo());
			ps.setString(8, plan.getMaximo());
			ps.setString(9, plan.getCarreraSe());
			ps.setString(10, plan.getRvoe());
			ps.setString(11, plan.getOficial());
			ps.setString(12, plan.getEstado());
			ps.setString(13, plan.getEnLinea());			
			ps.setString(14, plan.getCiclos());
			ps.setString(15, plan.getNotaExtra());
			ps.setString(16, plan.getGeneral());
			ps.setString(17, plan.getPlanSE());
			ps.setString(18, plan.getNombrePlanMujer());
			ps.setString(19, plan.getClaveProfesiones());
			ps.setString(20, plan.getPrecio());
			ps.setString(21, plan.getRvoeInicial());
			ps.setString(22, plan.getPlanId());
			if (ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;				
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String planId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?");
			ps.setString(1, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaPlan mapeaRegId( Connection conn, String planId ) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		MapaPlan plan 			= new MapaPlan();
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN,"
				+ " TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy') F_FINAL,"
				+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA,"
				+ " NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
				+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
				+ " FROM ENOC.MAPA_PLAN"
				+ " WHERE PLAN_ID = ? "); 
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				plan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return plan;
	}
	
	public boolean existeReg(Connection conn, String planId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_PLAN "+ 
				"WHERE PLAN_ID = ?");
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public static String getNombrePlanUtil(Connection conn, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String nombrePlan 		= "x";	
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_PLAN FROM ENOC.MAPA_PLAN "+ 
				"WHERE PLAN_ID = ?");
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombrePlan = rs.getString("NOMBRE_PLAN");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNombrePlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombrePlan;
	}
	
	public static int getNumCursos(Connection conn, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		int nCursos=0;	
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_ID) AS CURSOS FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?");
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				nCursos = rs.getInt("CURSOS");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNumCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCursos;
	}
	
	public static String getCarreraId(Connection conn, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String carrera			= "";	
		
		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID FROM ENOC.MAPA_PLAN "+ 
				"WHERE TRIM(PLAN_ID) = ?");
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				carrera = rs.getString("CARRERA_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}	
	
	public String getCarrera(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "x";
		
		try{			
			comando = "SELECT CARRERA_SE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = '"+planId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("CARRERA_SE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	
	
	public static String getCarreraSe(Connection conn, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String nombreCarrera	= "x";		
		try{
			ps = conn.prepareStatement("SELECT CARRERA_SE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?");
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			if (rs.next()) {
				nombreCarrera = rs.getString("CARRERA_SE");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getCarreraSe|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombreCarrera;
	}
	
	public static String getPlanOrigen(Connection conn, String cursoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String carrera			= "";	
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_PLAN FROM ENOC.MAPA_PLAN WHERE PLAN_ID = " + 
					"(SELECT DISTINCT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = " + 
					"(SELECT DISTINCT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO " + 
					"WHERE CURSO_ID = ?))");
			ps.setString(1, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				carrera = rs.getString("NOMBRE_PLAN");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getPlanOrigen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}	
	
	public static String getNumPlanes(Connection conn, String carreraId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String carrera			= "";	
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.MAPA_PLAN WHERE CARRERA_ID='"+carreraId+"' ");
			
			rs = ps.executeQuery();
			if (rs.next())
				carrera = rs.getString("CANTIDAD");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNumPlanes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	public static String getNumPlanes(Connection conn, String carreraId, String estado) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String carrera			= "";	
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.MAPA_PLAN WHERE CARRERA_ID='"+carreraId+"' AND ESTADO != '"+estado+"' ");
			
			rs = ps.executeQuery();
			if (rs.next())
				carrera = rs.getString("CANTIDAD");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNumPlanes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	public static String getNumPlanesAdmision(Connection conn, String carreraId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String carrera			= "";		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.MAPA_PLAN WHERE CARRERA_ID='"+carreraId+"' AND ESTADO='A'");			
			rs = ps.executeQuery();
			if (rs.next()) {
				carrera = rs.getString("CANTIDAD");			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNumPlanes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	
	public static String getNivelId(Connection conn, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String nivel			= "";	
		
		try{
			ps = conn.prepareStatement("SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?)");
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				nivel = rs.getString("NIVEL_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nivel;
	}
	
	
	public static String getNotaExtraPlan(Connection conn, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String notaExtraPlan 		= "x";	
		
		try{
			ps = conn.prepareStatement("SELECT NOTA_EXTRA FROM ENOC.MAPA_PLAN "+ 
				"WHERE PLAN_ID = ?");
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				notaExtraPlan = rs.getString("NOTA_EXTRA");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNotaExtraPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return notaExtraPlan;
	}
	
	public static String getRvoe(Connection conn, String planId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String notaExtraPlan 		= "x";	
		
		try{
			ps = conn.prepareStatement("SELECT RVOE FROM ENOC.MAPA_PLAN "+ 
				"WHERE PLAN_ID = ?");
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				notaExtraPlan = rs.getString("RVOE");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getRvoe|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return notaExtraPlan;
	}
	
	public ArrayList<MapaPlan> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;	
	}
	
	public ArrayList<MapaPlan> getListPlanActivo(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN WHERE ESTADO IN ('A','V') "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;	
	}
	
	public ArrayList<MapaPlan> getListPlanes(Connection conn, int year, String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ESTADO IN ('A', 'V')"
					+ " AND TO_NUMBER(TO_CHAR(F_INICIO, 'YYYY')) >= "+year+" "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;	
	}
	
	public ArrayList<MapaPlan> getLista(Connection conn, String carreraId, String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, F_INICIO, F_FINAL,"
					+ " F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE,"
					+ " OFICIAL, ESTADO, ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE,"
					+ " NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE CARRERA_ID = '"+carreraId+"'"
					+ " AND ESTADO IN ('A', 'V') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;
	}
	
	public ArrayList<MapaPlan> getListPlanFacAll(Connection conn, String facultad, String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = '"+facultad+"'"
					+ " AND ESTADO IN ('A', 'V','I') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getListPlanFacAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;
	}
	
	public ArrayList<MapaPlan> getListPlanFac(Connection conn, String facultad, String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = '"+facultad+"'"
					+ " AND ESTADO IN ('A', 'V') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getListPlanFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;
	}
	
	public ArrayList<MapaPlan> getPlanGeneral(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"	
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN WHERE GENERAL = 'S' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getPlanGeneral|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;
	}
	
	public ArrayList<MapaPlan> getListRvoe(Connection conn, String oficial, String estado,String orden ) throws SQLException{
		
		ArrayList<MapaPlan> lisPlan		= new ArrayList<MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(RVOE) AS RVOE FROM ENOC.MAPA_PLAN WHERE OFICIAL='"+oficial+"' AND ESTADO != '"+estado+"'"+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getListRvoe|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;
	}
	
	public HashMap<String, String> mapRvoeAlum(Connection conn, String cargaId, String orden) throws SQLException{
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
	
		try{
			comando = " SELECT RVOE, COUNT( CODIGO_PERSONAL) AS ALUMNOS"
					+ " FROM ENOC.ESTADISTICA E INNER JOIN ENOC.MAPA_PLAN M ON E.CARRERA_ID = M.CARRERA_ID AND E.PLAN_ID = M.PLAN_ID"
					+ " WHERE CARGA_ID = '"+cargaId+"' GROUP BY RVOE"+orden;
				
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("RVOE"), rs.getString("ALUMNOS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|mapRvoeAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public TreeMap<String, MapaPlan> getTreePlan(Connection conn, String orden ) throws SQLException{
		
		TreeMap<String,MapaPlan> treePlan		= new TreeMap<String,MapaPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ESTADO IN ('A', 'V') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaPlan plan = new MapaPlan();
				plan.mapeaReg(rs);
				treePlan.put(plan.getPlanId(),plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getTreePlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treePlan;
	}
	
	public static String getNombrePlan(Connection conn, String planId ) throws SQLException{		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String nombre			= "x";
		try{
			comando = "SELECT CARRERA_SE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = '"+planId+"'"; 
	
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("CARRERA_SE");
			}	
	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNombrePlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}

		return nombre;
	}	
	
	public static String getNombreVersion(Connection conn, String versionId ) throws SQLException{		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String nombre			= "x";
		try{
			comando = "SELECT VERSION_NOMBRE FROM ENOC.MAPA_VERSION WHERE PLAN_ID = '"+versionId+"'";	
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("VERSION_NOMBRE");
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|getNombreVersion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}

		return nombre;
	}
	
	public HashMap<String, String> mapCarreraSe(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = " SELECT PLAN_ID, CARRERA_SE FROM ENOC.MAPA_PLAN";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("PLAN_ID"), rs.getString("CARRERA_SE"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|mapCarreraPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapCarreraPlan(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID FROM ENOC.MAPA_PLAN";
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("PLAN_ID"), rs.getString("CARRERA_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|mapCarreraPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public boolean cotejado(Connection conn, String planId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String cotejado="";

		try{
			comando = "SELECT COALESCE(COTEJADO,'N') FROM ENOC.MAPA_PLAN WHERE PLAN_ID = '"+planId+"'"; 
	
			rs = st.executeQuery(comando);
			if (rs.next()){
				cotejado = rs.getString(1);
			}	
	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|cotejado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		if(cotejado.equals("S")) return true;
		else return false;
	}	
	
	public String getPrecio(Connection conn, String planId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String precio			= "N";

		try{
			comando = "SELECT COALESCE(PRECIO,'N') AS PRECIO FROM ENOC.MAPA_PLAN WHERE PLAN_ID = '"+planId+"'"; 
	
			rs = st.executeQuery(comando);
			if (rs.next()){
				precio = rs.getString("PRECIO");
			}	
	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|cotejado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return precio;		
	}
	
	public void cambiaCotejado(Connection conn, String planId,String cotejado) throws SQLException{
		
		Statement st 			= conn.createStatement();
		String comando			= "";

		try{
			comando = "UPDATE ENOC.MAPA_PLAN SET COTEJADO='"+cotejado+"' WHERE PLAN_ID = '"+planId+"'"; 
			st.executeUpdate(comando);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|cambiaCotejado|:"+ex);
		}finally{
			try { st.close(); } catch (Exception ignore) { }
		}
	}	
}