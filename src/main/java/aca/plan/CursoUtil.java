// Clase Util para la tabla de Mapa_Plan
package aca.plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class CursoUtil{
	
	public boolean insertReg(Connection conn, MapaCurso curso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_CURSO" 
				+ "(PLAN_ID, CURSO_ID, NOMBRE_CURSO,CICLO,CREDITOS,HT,HP,HI,F_CREADA, NOTA_APROBATORIA,ESTADO,TIPOCURSO_ID,UNID,ON_LINE,CURSO_CLAVE,OBLIGATORIO,"
				+ " COMPLETO,HH,HFD,HSS,HAS,HORARIO,AREA_ID)"
				+ " VALUES(?, ?, ?,"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_NUMBER(?,'99.99'),"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_DATE(now(),'DD/MM/YYYY'),"
				+ " TO_NUMBER(?,'999'),"
				+ " ?,"
				+ " TO_NUMBER(?,'99'),"
				+ " ?, ?, ?, ?, ?,"
				+ " TO_NUMBER(?,'999'),"
				+ " TO_NUMBER(?,'999'),"
				+ " TO_NUMBER(?,'999'),"
			    + " TO_NUMBER(?,'999'),"
			    + " ?,"
			    + " TO_NUMBER(?,'999'))");			
			ps.setString(1, curso.getPlanId());
			ps.setString(2, curso.getCursoId());
			ps.setString(3, curso.getNombreCurso());
			ps.setString(4, curso.getCiclo());
			ps.setString(5, curso.getCreditos());
			ps.setString(6, curso.getHt());
			ps.setString(7, curso.getHp());
			ps.setString(8, curso.getHi());			
			ps.setString(9, curso.getNotaAprobatoria());
			ps.setString(10, curso.getEstado());
			ps.setString(11, curso.getTipoCursoId());
			ps.setString(12, curso.getUnid());
			ps.setString(13, curso.getOnLine());
			ps.setString(14, curso.getCursoClave());
			ps.setString(15, curso.getObligatorio());
			ps.setString(16, curso.getCompleto());
			ps.setString(17, curso.getHh());
			ps.setString(18, curso.getHfd());
			ps.setString(19, curso.getHss());
			ps.setString(20, curso.getHas());
			ps.setString(21, curso.getHorario());
			ps.setString(22, curso.getAreaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaCurso curso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_CURSO " + 
			 	 "SET PLAN_ID = ?, " +
				 "NOMBRE_CURSO = ?, " +
				 "CICLO = TO_NUMBER(?,'99'), " +
				 "CREDITOS = TO_NUMBER(?,'99.99'), " +
				 "HT = TO_NUMBER(?,'99'), " +
				 "HP = TO_NUMBER(?,'99'), " +
				 "HI = TO_NUMBER(?,'99'), " +
				 "NOTA_APROBATORIA = TO_NUMBER(?,'999'), " +
				 "ESTADO = ?, " +
				 "TIPOCURSO_ID = TO_NUMBER(?,'99'), " +
				 "UNID = ?, " +
				 "ON_LINE = ?, "+
				 "CURSO_CLAVE = ?, "+
				 "OBLIGATORIO = ?, "+
				 "COMPLETO = ?, "+
				 "HH = ?, "+
				 "HFD = ?, "+
				 "HSS = ?, "+
				 "HAS = ?, "+
				 "HORARIO = ?, "+
				 "AREA_ID = TO_NUMBER(?,'999') "+
				 "WHERE CURSO_ID = ? ");
			ps.setString(1, curso.getPlanId());			
			ps.setString(2, curso.getNombreCurso());
			ps.setString(3, curso.getCiclo());
			ps.setString(4, curso.getCreditos());
			ps.setString(5, curso.getHt());
			ps.setString(6, curso.getHp());
			ps.setString(7, curso.getHi());
			ps.setString(8, curso.getNotaAprobatoria());
			ps.setString(9, curso.getEstado());			
			ps.setString(10, curso.getTipoCursoId());
			ps.setString(11, curso.getUnid());
			ps.setString(12, curso.getOnLine());
			ps.setString(13, curso.getCursoClave());
			ps.setString(14, curso.getObligatorio());
			ps.setString(15, curso.getCompleto());
			ps.setString(16, curso.getHh());
			ps.setString(17, curso.getHfd());
			ps.setString(18, curso.getHss());
			ps.setString(19, curso.getHas());
			ps.setString(20, curso.getHorario());
			ps.setString(21, curso.getAreaId());
			ps.setString(22, curso.getCursoId());
						
			if (ps.executeUpdate()== 1){
				ok = true;				
				
			}else{
				ok = false;
				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ? ");
			ps.setString(1, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaCurso mapeaRegId( Connection conn, String cursoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaCurso curso = new MapaCurso();
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, " +
				"CICLO, CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
				"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, " +
				"NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
				"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
				"FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ? ");
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			if (rs.next()){
				curso.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return curso;
	}
	
	public boolean existeReg(Connection conn, String cursoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ? "); 
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Determina si el plan tiene componentes
	public static boolean esPlanDeComponentes(Connection conn, String planId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND TIPOCURSO_ID = 3"); 
			ps.setString(1,planId);
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|esPlanDeComponentes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int cuentaMaterias( Connection conn, String planId, String tipoCurso) throws SQLException, Exception{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		int nMaterias			= 0;		
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS MATERIAS FROM ENOC.MAPA_CURSO "+ 
			"WHERE PLAN_ID = ? AND TIPOCURSO_ID = TO_NUMBER(?,'99')");					
			ps.setString(1, planId);
			ps.setString(2,tipoCurso);		
			rs = ps.executeQuery();
			if (rs.next()){			
				nMaterias = rs.getInt("MATERIAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|cuantaMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nMaterias;
	}
	
	public static int totalMateriasObligatorias( Connection conn, String planId, String obligatorio) throws SQLException, Exception{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		int nMaterias			= 0;		
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS MATERIAS FROM ENOC.MAPA_CURSO "+ 
			"WHERE PLAN_ID = ? AND OBLIGATORIO = ?");					
			ps.setString(1, planId);
			ps.setString(2, obligatorio);
			rs = ps.executeQuery();
			if (rs.next()){			
				nMaterias = rs.getInt("MATERIAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|totalMateriasObligatorias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nMaterias;
	}
	
	public static int totalMateriasObligatoriasDos( Connection conn, String planId, String obligatorio) throws SQLException, Exception{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		int nMaterias			= 0;		
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS MATERIAS FROM ENOC.MAPA_CURSO "+ 
					"WHERE PLAN_ID = ? AND OBLIGATORIO = ? AND CREDITOS != 0");					
			ps.setString(1, planId);
			ps.setString(2, obligatorio);
			rs = ps.executeQuery();
			if (rs.next()){			
				nMaterias = rs.getInt("MATERIAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|totalMateriasObligatoriasDos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nMaterias;
	}

	public static String getMateria(Connection conn, String planId, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String nombre = ""; 
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND CURSO_ID = ?"); 
			ps.setString(1, planId);
			ps.setString(2, cursoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CURSO");
			else
				nombre = "0000000";
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getMateria(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String nombre = ""; 
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?");			 
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CURSO");
			else
				nombre = "0000000";
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static int getNumCredPlanObligatorio(Connection conn, String planId, String obligatorio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int nCred=0; 
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND OBLIGATORIO = ? ");
			ps.setString(1, planId);	
			ps.setString(2, obligatorio);	
			rs = ps.executeQuery();
			if (rs.next())
				nCred = rs.getInt("CREDITOS");		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNumCredPlanObligatorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCred;
	}
	
	public static int getNumCredPlan(Connection conn, String planId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int nCred=0; 
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			if (rs.next())
				nCred = rs.getInt("CREDITOS");		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNumCredPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCred;
	}
	
	public static int getNumMateriasCiclo(Connection conn, String planId, int ciclo, int tipo ) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		int numMat				= 0; 
		try{				
			ps = conn.prepareStatement("SELECT COUNT(CURSO_ID) AS NUMMAT FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND CICLO = ? AND TIPOCURSO_ID = ? ");
			ps.setString(1, planId);	
			ps.setInt(2, ciclo);
			ps.setInt(3, tipo);
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNumMateriasCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static String getTipoCurso(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String tipo = ""; 
		try{
			ps = conn.prepareStatement("SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?");	 
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			if (rs.next())
				tipo = rs.getString("TIPOCURSO_ID");
			else
				tipo = "0";
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getTipoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static int getCiclo(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int ciclo = 0; 
		try{
			ps = conn.prepareStatement("SELECT CICLO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?");			 
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			if (rs.next())
				ciclo = rs.getInt("CICLO");
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ciclo;
	}
	
	public static float getCreditosObliOpta(Connection conn, String planId, String ciclo) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		float creditos = 0; 
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) NUM_CREDITOS" +
					" FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('1','5','8')" + 
					" AND PLAN_ID = ? AND CICLO = ?");			
			ps.setString(1, planId);
			ps.setInt(2, Integer.parseInt(ciclo));
			rs = ps.executeQuery();
			
			if (rs.next())
				creditos = rs.getFloat("NUM_CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCreditosObliOpta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static float getCreditosCurso(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		float creditos = 0; 
		try{
			ps = conn.prepareStatement("SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?");
			ps.setString(1, cursoId);			
			rs = ps.executeQuery();
			
			if (rs.next())
				creditos = rs.getFloat("CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCreditosCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static float getCreditosElectivos(Connection conn, String planId, String ciclo) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		float creditos = 0; 
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) NUM_CREDITOS" +
					" FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('2','7')" + 
					" AND PLAN_ID = ? AND CICLO = ?");			
			ps.setString(1, planId);
			ps.setInt(2, Integer.parseInt(ciclo));
			rs = ps.executeQuery();
			
			if (rs.next())
				creditos = rs.getFloat("NUM_CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCreditosElectivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static String getFS(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int ht=0, hp=0; 
		try{
			ps = conn.prepareStatement("SELECT HT, HP FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?"); 
			ps.setString(1, cursoId);			
			rs = ps.executeQuery();
			if (rs.next()){
				ht = rs.getInt("HT");
				hp = rs.getInt("HP");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNumCredPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return String.valueOf(ht+hp);
	}
	
	public static String getHH(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int hh=0; 
		try{
			ps = conn.prepareStatement("SELECT HH FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?");
			ps.setString(1, cursoId);			
			rs = ps.executeQuery();
			if (rs.next()){
				hh = rs.getInt("HH");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNumCredPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return String.valueOf(hh);
	}
	
	public static int getCrElec(Connection conn, String planId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int creditos = 0; 
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) NUM_CREDITOS" +
					" FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('1','5')" + 
					" AND PLAN_ID = ?");			
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			
			if (rs.next())
				creditos = rs.getInt("NUM_CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCrElec|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static int getCrObli(Connection conn, String planId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		int creditos = 0; 
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) NUM_CREDITOS" +
					" FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('2','7')" + 
					" AND PLAN_ID = ?");			
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			
			if (rs.next())
				creditos = rs.getInt("NUM_CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCrObli|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public static String getPlanId(Connection conn, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String plan	= "X"; 
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?"); 
			ps.setString(1, cursoId);			
			rs = ps.executeQuery();
			if (rs.next()){
				plan = rs.getString("PLAN_ID");				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getPlanId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return plan;
	}	
		
	public ArrayList<MapaCurso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "+
				"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
				"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
				"FROM ENOC.MAPA_CURSO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<MapaCurso> getLista(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, " +
					"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, "+
					"NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
					"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
					"FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<MapaCurso> getMateriasReque(Connection conn, String planId, String cicloId, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, " +
					"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, "+
					"NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
					"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
					"FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' "+
					"AND CICLO ='"+cicloId+"' " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getMateriasReque|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<MapaCurso> getMatCompleto(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, " +
					"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, "+
					"NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
					"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
					"FROM ENOC.MAPA_CURSO WHERE COMPLETO = 'S' "+ orden;
					 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getMatCompleto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public ArrayList<MapaCurso> getMateriasElegibles(Connection conn, String planId, String tipoCurso, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, " +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
					" FROM ENOC.MAPA_CURSO" +
					" WHERE PLAN_ID = '"+planId+"'" +
					" AND TIPOCURSO_ID NOT IN ("+tipoCurso+") "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getMateriasElegibles|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	
	public TreeMap<String,MapaCurso> getTree(Connection conn, String planId, String orden ) throws SQLException{
		
		TreeMap<String, MapaCurso> treeCurso	= new TreeMap<String,MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO," +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
					" FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"'"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				treeCurso.put(curso.getCursoId(), curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getTree|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeCurso;
	}
	
	public TreeMap<String,MapaCurso> getTree(Connection conn, String planId, String tipo, String orden ) throws SQLException{
		
		TreeMap<String, MapaCurso> treeCurso	= new TreeMap<String,MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO," +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
					" FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"'" + 
					" AND TIPOCURSO_ID  IN ('"+tipo+"')"+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				treeCurso.put(curso.getCursoId(), curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getTree|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeCurso;
	}
	
	public ArrayList<MapaCurso> getListCarrera(Connection conn, String carreraId, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String s_comando	= "";
		
		try{
			s_comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "+
				"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
				"F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
				"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
				"FROM ENOC.MAPA_CURSO WHERE PLAN_ID IN "+ 
					"(SELECT PLAN_ID FROM ENOC.MAPA_PLAN WHERE CARRERA_ID = '"+carreraId+"') "+ 
				orden;
			
			rs = st.executeQuery(s_comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCurso;
	}
	public ArrayList<MapaCurso> getListPlan(Connection conn, String planId, String orden ) throws SQLException{
		
			ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String s_comando	= "";
		
			try{
				s_comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "+
					"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					"F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
					"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
					"FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' "+ orden; 
			
				rs = st.executeQuery(s_comando);
				while (rs.next()){
				
					MapaCurso curso = new MapaCurso();
					curso.mapeaReg(rs);
					lisCurso.add(curso);
				}
			
			}catch(Exception ex){
				System.out.println("Error - aca.plan.CursoUtil|getListPlan|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
		
			return lisCurso;
	}
	
	public ArrayList<MapaCurso> getListSemestre(Connection conn, String planId, int ciclo, String orden ) throws SQLException{
		
			ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String s_comando	= "";
		
			try{
				s_comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "+
					"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					"F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
					"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
					"FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' AND CICLO = '"+ciclo+"' "+ orden; 
			
				rs = st.executeQuery(s_comando);
				while (rs.next()){
				
					MapaCurso curso = new MapaCurso();
					curso.mapeaReg(rs);
					lisCurso.add(curso);
				}
			
			}catch(Exception ex){
				System.out.println("Error - aca.plan.CursoUtil|getListSemestre|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
		
			return lisCurso;
	}
	
	public ArrayList<MapaCurso> getListConvalida(Connection conn, String planId, String codigoPersonal, String orden ) throws SQLException{
		
			ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
		
			try{				
				comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
						" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
						" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
						" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
						" FROM ENOC.MAPA_CURSO"+ 
						" WHERE PLAN_ID = '"+planId+"'"+ 
						" AND CURSO_ID NOT IN"+
							"(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO"+ 
							" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+
						" AND TIPOCAL_ID IN('1','I'))"+
						" AND CURSO_ID NOT IN"+ 
							"(SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD"+
							" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
							" AND PROCESO_ID != 'X'" +
							" AND EST_MAT != 'N') "+orden;
			
				rs = st.executeQuery(comando);
				while (rs.next()){
				
					MapaCurso curso = new MapaCurso();
					curso.mapeaReg(rs);
					lisCurso.add(curso);
				}
			
			}catch(Exception ex){
				System.out.println("Error - aca.plan.CursoUtil|getListConvalida|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
		
			return lisCurso;
	}
	
	public ArrayList<MapaCurso> getListCursosPendientes(Connection conn, String codigoPersonal, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{				
			comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
					" FROM ENOC.MAPA_CURSO"+ 
					" WHERE PLAN_ID = '"+planId+"'"+					
					" AND CURSO_ID NOT IN"+
						"(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+" AND TIPOCAL_ID IN('1','I'))"+
					" AND TIPOCURSO_ID IN(1,2,3,4,5,7,8) "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListCursosPendientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisCurso;
	}
	
	public ArrayList<MapaCurso> getListCursosPendientessinRemedial(Connection conn, String codigoPersonal, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{				
			comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
					" FROM ENOC.MAPA_CURSO"+ 
					" WHERE PLAN_ID = '"+planId+"'"+					
					" AND CURSO_ID NOT IN"+
						"(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+" AND TIPOCAL_ID IN('1','I'))"+
					" AND TIPOCURSO_ID IN(1,2,3,5,7,8) "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListCursosPendientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisCurso;
	}
	
	public ArrayList<MapaCurso> getCursosNoInscritos(Connection conn, String codigoPersonal, String planId, String tipoCal, String orden ) throws SQLException{
		
		ArrayList<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{				
			comando = " SELECT * FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' "
					+ " AND CURSO_ID NOT IN ( "
					+ "               SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND TIPOCAL_ID IN ("+tipoCal+") ) "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.add(curso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCursosNoInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> getMapaCursos(Connection conn, String planId, String orden) throws SQLException{
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{				
			comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
					" FROM ENOC.MAPA_CURSO"+ 
					" WHERE PLAN_ID = '"+planId+"' "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.put(curso.getCursoId(), curso);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListCursosPendientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisCurso;
	}
	
	public static HashMap<String, MapaCurso> mapaCursosEnCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{				
			comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"'))";
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.put(curso.getCursoId(), curso);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListCursosPendientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> getAllMapaCursos(Connection conn, String orden) throws SQLException{
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID "+
					" FROM ENOC.MAPA_CURSO "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lisCurso.put(curso.getCursoId(), curso);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListCursosPendientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisCurso;
	}
	
	public HashMap<String, String> getMapaNombresCursosPorCondicion(Connection conn, String condiciones) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CURSO_ID, NOMBRE_CURSO FROM ENOC.MAPA_CURSO "+condiciones;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CURSO_ID"), rs.getString("NOMBRE_CURSO"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getMapaNombresCursosPorPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public String getNombreCurso(Connection conn, String cursoId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;		
		String s_comando		= "";
		String sNombre			= "x";
		
		try{
			s_comando = "SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = UPPER('"+cursoId+"')"; 
			
			rs = st.executeQuery(s_comando);
			if (rs.next()){
				sNombre = rs.getString("NOMBRE_CURSO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNombreCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return sNombre;
	}
	public String getPlanCurso(Connection conn, String cursoId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;		
		String s_comando		= "";
		String sNombre			= "x";
		
		try{
			s_comando = "SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = UPPER('"+cursoId+"')"; 
			
			rs = st.executeQuery(s_comando);
			if (rs.next()){
				sNombre = rs.getString("PLAN_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getPlanCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return sNombre;
	}	
	
	public boolean getMateriaInscrita(Connection conn, String cursoId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;		
		String s_comando		= "";
		boolean existe = false;		
		try{
			s_comando = "SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CURSO_ID = UPPER('"+cursoId+"')";
			
			rs = st.executeQuery(s_comando);
			if (rs.next()){
				existe = true;
			}else{
				existe = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getMateriaInscrita|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return existe;
	}
	
	public ArrayList<MapaCurso> getListMaterias(Connection conn, String cursoClave ) throws SQLException{
		
		ArrayList<MapaCurso> lis	= new ArrayList<MapaCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_CURSO WHERE CURSO_CLAVE = '"+cursoClave+"' ORDER BY PLAN_ID "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCurso curso = new MapaCurso();
				curso.mapeaReg(rs);
				lis.add(curso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getListMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> getNotasxCredtios(Connection conn, String periodoId ) throws SQLException{
		
		ArrayList<String> lis	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT " +
					" SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID, C.CARRERA_ID, SUM( CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END * B.CREDITOS) AS TOTAL " +
					" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,4)='"+periodoId+"'" +
					" AND A.CURSO_ID = B.CURSO_ID" +
					" AND C.PLAN_ID = B.PLAN_ID" +
					" AND TIPOCAL_ID IN('1','2','4')" +
					" AND B.TIPOCURSO_ID IN (1,2,7)" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID" +
					" ORDER BY 1,2 "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("CARGA_ID")+"%"+rs.getString("CARRERA_ID")+"%"+rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNotasxCredtios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> getTotalCredtios(Connection conn, String periodoId ) throws SQLException{
		
		ArrayList<String> lis	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT " +
					" SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID, C.CARRERA_ID, SUM(B.CREDITOS) AS TOTAL  " +
					" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,4)='"+periodoId+"'   " +
					" AND A.CURSO_ID = B.CURSO_ID" +
					" AND C.PLAN_ID = B.PLAN_ID" +
					" AND TIPOCAL_ID IN('1','2','4')" +
					" AND B.TIPOCURSO_ID IN (1,2,7)" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID, C.CARRERA_ID" +
					" ORDER BY 1,2 "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("CARGA_ID")+"%"+rs.getString("CARRERA_ID")+"%"+rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getTotalCredtios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> getNotasxCredtiosCarga(Connection conn, String cargaId ) throws SQLException{
		
		ArrayList<String> lis	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT"
					+ " SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID, ENOC.FACULTAD(C.CARRERA_ID) AS FACULTAD, C.CARRERA_ID,"
					+ " SUM( CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END * B.CREDITOS) AS TOTAL"
					+ " FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6)='"+cargaId+"'"
					+ " AND A.CURSO_ID = B.CURSO_ID"
					+ " AND C.PLAN_ID = B.PLAN_ID"
					+ " AND TIPOCAL_ID IN('1','2','4')"
					+ " AND B.TIPOCURSO_ID IN (1,2,7)"
					+ " GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID"
					+ " ORDER BY 2,3 "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("CARGA_ID")+"%"+rs.getString("FACULTAD")+"%"+rs.getString("CARRERA_ID")+"%"+rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getNotasxCredtios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> getTotalCredtiosCarga(Connection conn, String cargaId ) throws SQLException{
		
		ArrayList<String> lis	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT " +
					" SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID, ENOC.FACULTAD(C.CARRERA_ID) AS FACULTAD, C.CARRERA_ID, SUM(B.CREDITOS) AS TOTAL  " +
					" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6)='"+cargaId+"'   " +
					" AND A.CURSO_ID = B.CURSO_ID" +
					" AND C.PLAN_ID = B.PLAN_ID" +
					" AND TIPOCAL_ID IN('1','2','4')" +
					" AND B.TIPOCURSO_ID IN (1,2,7)" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID, C.CARRERA_ID" +
					" ORDER BY 2,3 "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("CARGA_ID")+"%"+rs.getString("FACULTAD")+"%"+rs.getString("CARRERA_ID")+"%"+rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getTotalCredtios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	/*
	 * Lista de los ciclos de un plan de estudios
	 * */
	public static ArrayList<String> lisCiclosPlan( Connection conn, String planId) throws SQLException{
		
		ArrayList<String> ciclos	= new ArrayList<String>();
		PreparedStatement ps 		= null;
		ResultSet rs 				= null;
		
		try{
			ps = conn.prepareStatement("SELECT DISTINCT(CICLO) AS CICLO FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? ORDER BY 1"); 
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			while (rs.next()){
				ciclos.add(rs.getString("CICLO"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|lisCiclosPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ciclos;
	}
	
	public HashMap<String, String> getCursoTerminadoObligatorio(Connection conn, String codigoPersonal, String planId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = " SELECT CICLO, COUNT(CURSO_ID) AS FALTANTES FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = '"+planId+"'"
					+ " AND OBLIGATORIO = 'S'"
					+ " AND CURSO_ID NOT IN "
					+ "		(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PLAN_ID = '"+planId+"' AND TIPOCAL_ID = '1')"
					+ " GROUP BY CICLO";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CICLO"), rs.getString("FALTANTES"));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|getCursoTerminadoObligatorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapCursos(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String comando 			= "SELECT CURSO_ID, NOMBRE_CURSO FROM ENOC.MAPA_CURSO";	
		try{
			ps = conn.prepareStatement(comando);			
			rs = ps.executeQuery();
			while (rs.next()){
				mapa.put(rs.getString("CURSO_ID"), rs.getString("NOMBRE_CURSO"));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|mapCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaCursos(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String comando 			= "SELECT PLAN_ID, COUNT(CURSO_ID) AS TOTAL FROM ENOC.MAPA_CURSO GROUP BY PLAN_ID";	
		try{
			ps = conn.prepareStatement(comando);			
			rs = ps.executeQuery();
			while (rs.next()){
				mapa.put(rs.getString("PLAN_ID"), rs.getString("TOTAL"));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CursoUtil|mapaCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
}