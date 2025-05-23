// Clase para la tabla de Alum_Plan
package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class PlanUtil{
	
	public boolean insertReg(Connection conn, AlumPlan alumPlan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_PLAN"
					+ " (CODIGO_PERSONAL, PLAN_ID,  F_INICIO, ESTADO, ESCUELA_ID, AVANCE_ID,"
					+ " PERMISO, EVENTO, F_GRADUACION, F_EGRESO, GRADO, CICLO, PRINCIPAL, ESCALA,"
					+ " PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP)"
					+ " VALUES( ?, ?,"
					+ " TO_DATE(?,'DD/MM/YYYY'),"
					+ " ?, TO_NUMBER( ?, '999'),"
					+ " TO_NUMBER( ?, '99'),"
					+ " ?,?, TO_DATE(?, 'DD/MM/YYYY'),"
					+ " TO_DATE(?, 'DD/MM/YYYY'),"
					+ " TO_NUMBER( ?, '99'),"
					+ " TO_NUMBER( ?, '99'),?,"
					+ " TO_NUMBER(?, '999'),"
					+ " TO_DATE(?, 'DD/MM/YYYY'), ?,"
					+ " TO_NUMBER( ?, '99'))");
			
			ps.setString(1, alumPlan.getCodigoPersonal());
			ps.setString(2, alumPlan.getPlanId());			
			ps.setString(3, alumPlan.getFInicio());
			ps.setString(4, alumPlan.getEstado());
			ps.setString(5, alumPlan.getEscuelaId());
			ps.setString(6, alumPlan.getAvanceId());
			ps.setString(7, alumPlan.getPermiso());
			ps.setString(8, alumPlan.getEvento());
			ps.setString(9, alumPlan.getFGraduacion());
			ps.setString(10, alumPlan.getFEgreso());
			ps.setString(11, alumPlan.getGrado());
			ps.setString(12, alumPlan.getCiclo());
			ps.setString(13, alumPlan.getPrincipal());
			ps.setString(14, alumPlan.getEscala());
			ps.setString(15, alumPlan.getPrimerMatricula());
			ps.setString(16, alumPlan.getActualizado());
			ps.setString(17, alumPlan.getCicloSep());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumPlan alumPlan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN"
					+ " SET F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " ESTADO = ?,"
					+ " ESCUELA_ID = TO_NUMBER(?,'999'),"
					+ " AVANCE_ID = TO_NUMBER(?,'999'),"
					+ " PERMISO = ?,"
					+ " EVENTO = ?,"
					+ " F_GRADUACION = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " F_EGRESO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " GRADO = TO_NUMBER(?, '99'),"
					+ " CICLO = TO_NUMBER(?, '99'),"
					+ " PRINCIPAL = ?,"
					+ " ESCALA = TO_NUMBER(?, '999'),"
					+ " PRIMER_MATRICULA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ACTUALIZADO = ?,"
					+ " CICLO_SEP = TO_NUMBER(?,'99')"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?");
			
			ps.setString(1, alumPlan.getFInicio());
			ps.setString(2, alumPlan.getEstado());
			ps.setString(3, alumPlan.getEscuelaId());
			ps.setString(4, alumPlan.getAvanceId());
			ps.setString(5, alumPlan.getPermiso());
			ps.setString(6, alumPlan.getEvento());
			ps.setString(7, alumPlan.getFGraduacion());
			ps.setString(8, alumPlan.getFEgreso());
			ps.setString(9, alumPlan.getGrado());
			ps.setString(10, alumPlan.getCiclo());
			ps.setString(11, alumPlan.getPrincipal());
			ps.setString(12, alumPlan.getEscala());
			ps.setString(13, alumPlan.getPrimerMatricula());
			ps.setString(14, alumPlan.getActualizado());
			ps.setString(15, alumPlan.getCicloSep());
			ps.setString(16, alumPlan.getCodigoPersonal());			
			ps.setString(17, alumPlan.getPlanId());	
			
			if (ps.executeUpdate() == 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updatePrincipal(Connection conn, String principal, String codigoPersonal, String planId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN "+				
				" SET PRINCIPAL = ?"+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?");		
			
			ps.setString(1, principal);			
			ps.setString(2, codigoPersonal);			
			ps.setString(3, planId);	
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updatePrincipal|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateEstado(Connection conn, String estado, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN SET ESTADO = ? WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, estado);			
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate() >= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updateEstado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateEstado(Connection conn, String estado, String codigoPersonal, String planId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN "+				
				" SET ESTADO = ?"+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?");
			
			ps.setString(1, estado);			
			ps.setString(2, codigoPersonal);			
			ps.setString(3, planId);	
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updateEstado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateEstadoPrincipal(Connection conn, String estado, String principal, String codigoPersonal, String planId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN "+				
				" SET ESTADO = ?, PRINCIPAL = ?"+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?");	
			
			ps.setString(1, estado);	
			ps.setString(2, principal);	
			ps.setString(3, codigoPersonal);			
			ps.setString(4, planId);	
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updateEstadoPrincipal|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean updateGrado(Connection conn, String codigoPersonal, String planId, int grado) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN "+ 
				"SET GRADO = ? "+								
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setInt(1, grado);
			ps.setString(2, codigoPersonal);
			ps.setString(3, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updateGrado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean updateCicloSep(Connection conn, String codigoPersonal, String planId, String cicloSep) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN"
					+ " SET CICLO_SEP = TO_NUMBER(?,'99')"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?");
			ps.setString(1, cicloSep);
			ps.setString(2, codigoPersonal);
			ps.setString(3, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updateCicloSep|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updatePrincipal(Connection conn, String codigoPersonal) throws Exception{
		PreparedStatement ps = null;
		boolean ok = false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN "+ 
				"SET PRINCIPAL = '0' "+					
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()>= 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updatePrincipal|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return ok;
	}
	
	public boolean updateFechaGraduacion(Connection conn, String fGraduacion, String codigoPersonal, String planId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN "+ 
				"SET F_GRADUACION = TO_DATE(?,'DD/MM/YYYY') "+							
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, fGraduacion);			
			ps.setString(2, codigoPersonal);			
			ps.setString(3, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|updateFechaGraduacion|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String planId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_PLAN "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumPlan mapeaRegId( Connection conn, String Codigo_personal, String Plan_id ) throws SQLException{
		
		AlumPlan alumPlan = new AlumPlan();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"
					+ " CODIGO_PERSONAL, PLAN_ID, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO, 'DD/MM/YYYY') AS F_EGRESO,"
					+ " GRADO, CICLO, PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA, 'DD/MM/YYYY') AS PRIMER_MATRICULA,"
					+ " ACTUALIZADO, CICLO_SEP"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?");
			ps.setString(1, Codigo_personal);
			ps.setString(2, Plan_id);
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				alumPlan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumPlan;
	}
	
	public AlumPlan mapeaRegId( Connection conn, String Codigo_personal) throws SQLException{
		
		AlumPlan plan = new AlumPlan();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL=? AND ESTADO='1'"); 
			ps.setString(1, Codigo_personal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				plan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return plan;
	}
	
	public AlumPlan mapeaRegIdE( Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		AlumPlan alumPlan = new AlumPlan();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL=? AND PLAN_ID='"+planId+"'"); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumPlan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapeaRegIdE|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumPlan;
	}

	public boolean existeReg(Connection conn, String codigoPersonal, String planId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PLAN "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public int tienePlan(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		int total 				=  0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);		
			
			rs = ps.executeQuery();
			if (rs.next())
				total = 0;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|tienePlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public boolean existeCodigoPersonal(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PLAN "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|existeCodigoPersonal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<String> getPlanesAlumno( Connection conn, String Codigo_personal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> planes=new ArrayList<String>();
		try{
			ps = conn.prepareStatement("SELECT TRIM(PLAN_ID) AS PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL=?");
			ps.setString(1, Codigo_personal);
			
			rs = ps.executeQuery();
			while (rs.next()){
				planes.add(rs.getString("PLAN_ID"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getPlanesAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return planes;
	}
	
	public static ArrayList<String> getPlanesAlumnoStatic(Connection conn, String Codigo_personal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> planes=new ArrayList<String>();
		try{
			ps = conn.prepareStatement("SELECT TRIM(PLAN_ID) AS PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL=?");
			ps.setString(1, Codigo_personal);
			
			rs = ps.executeQuery();
			while (rs.next()){
				planes.add(rs.getString("PLAN_ID"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getPlanesAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return planes;
	}
	
	public static String getAvanceId( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{		
			
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String avanceId 		= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(AVANCE_ID,0) AS AVANCE_ID "+
				"FROM ENOC.ALUM_PLAN " + 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){		
				avanceId = rs.getString("AVANCE_ID");				
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getAvanceId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return avanceId;
	}
	
	public static String getPlanActual( Connection conn, String codigoPersonal) throws SQLException, Exception{		
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String plan 			= "";		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND ESTADO='1'");
			ps.setString(1, codigoPersonal);				
			rs = ps.executeQuery();
			if (rs.next()){			
				plan = rs.getString("PLAN_ID");				
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getPlanActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return plan;
	}
	
	public static String getPermiso( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{		
		
	ResultSet 		rs		= null;
	PreparedStatement ps	= null;
	String permiso 		= "";		
	
		try{
			ps = conn.prepareStatement("SELECT PERMISO "+
				"FROM ENOC.ALUM_PLAN " + 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
		
			rs = ps.executeQuery();
			if (rs.next()){		
				permiso = rs.getString("PERMISO");				
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getPermiso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return permiso;
	}
	
	public static String getEvento( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{		
		
	ResultSet 		rs		= null;
	PreparedStatement ps	= null;
	String evento 			= "";		
	
		try{
			ps = conn.prepareStatement("SELECT EVENTO "+
				"FROM ENOC.ALUM_PLAN " + 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
		
			rs = ps.executeQuery();
			if (rs.next()){		
				evento = rs.getString("EVENTO");				
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return evento;
	}	
	
	public static String getCarreraId(Connection conn, String codigoPersonal) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String carreraId		= "x";
		
		try{
			ps = conn.prepareStatement("SELECT B.CARRERA_ID FROM ENOC.ALUM_PLAN A, ENOC.MAPA_PLAN B "
					+ " WHERE A.CODIGO_PERSONAL = ?"
					+ " AND A.ESTADO = '1'"
					+ " AND B.PLAN_ID = A.PLAN_ID");
			ps.setString(1, codigoPersonal);
			rs= ps.executeQuery();		
			
			if(rs.next()){
				carreraId = rs.getString("CARRERA_ID");	
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return carreraId;
	}
	
	public static String getCarreraIdPLAN(Connection conn, String planId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String carreraId		= "x";

		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ? ");
			ps.setString(1, planId);
			rs= ps.executeQuery();		
			
			if(rs.next()){
				carreraId = rs.getString("CARRERA_ID");	
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getCarreraIdPLAN|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return carreraId;
	}
	
	public static String getCarreraNivel(Connection conn, String carreraId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String nivelId		= "x";

		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID, NIVEL_ID FROM ENOC.CAT_CARRERA" + 
					" WHERE CARRERA_ID = ? ");
			ps.setString(1, carreraId);
			rs= ps.executeQuery();		
			
			if(rs.next()){
				nivelId = rs.getString("NIVEL_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getCarreraNivel|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nivelId;
	}
	
	public static String getFechaInicio( Connection conn, String codigoPersonal, String planId) throws SQLException, Exception{		
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String fecha 			= "";		
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO FROM ENOC.ALUM_PLAN " + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){		
				fecha = rs.getString("F_INICIO");
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getFechaInicio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return fecha;
	}
	
	public static boolean actualizaCicloAlumno(Connection conn, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps	= null;		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN"+ 
				" SET CICLO = ALUM_PLAN_CICLO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL = ? " +
				" AND ESTADO = '1'");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate() >= 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|actualizaCicloAlumno|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean actualizaCicloAlumnoPostgrado(Connection conn, String codigoPersonal) throws Exception{
		PreparedStatement ps	= null;
		boolean ok = false;				
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN"+
				" SET CICLO = ALUM_PLAN_CICLO_POSTGRADO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL = ?" + 
				" AND ESTADO = '1'");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate() >= 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|actualizaCicloAlumnoPostgrado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean actualizaCiclo(Connection conn, String cargas) throws Exception{
		boolean ok = false;
		PreparedStatement ps	= null;		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN"+ 
				" SET CICLO = ENOC.ALUM_PLAN_CICLO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))" + 
				" AND ENOC.NIVEL_PLAN(PLAN_ID) IN (1,2,5)");
			
			if (ps.executeUpdate() >= 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|actualizaCiclo|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean actualizaCicloPostgrado(Connection conn, String cargas) throws Exception{
		PreparedStatement ps	= null;
		boolean ok = false;				
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_PLAN"+			 
				" SET CICLO = ENOC.ALUM_PLAN_CICLO_POSTGRADO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))" + 
				" AND ENOC.NIVEL_PLAN(PLAN_ID) IN (3,4)");
			
			if (ps.executeUpdate() >= 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|actualizaCicloPostgrado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean actualizaGradoAlumno(Connection conn, String codigoPersonal) throws Exception{
		Statement st 		= conn.createStatement();		
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok 			= false;
		
		try{
			int grado 		= 0;			
			int error		= 0;		
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS FACULTAD, CICLO "+	
				" FROM ENOC.ALUM_PLAN"+ 
				" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
				" AND ESTADO = '1'";

			rs = st.executeQuery(comando);
			while (rs.next()){
				
				if (rs.getString("FACULTAD").equals("107")){
					if (rs.getInt("CICLO")>=1 && rs.getInt("CICLO") <= 3) {
						grado = 1;
					}else if (rs.getInt("CICLO")>=4 && rs.getInt("CICLO") <=6){
						grado = 2;
					}else{
						grado = 0;
					}
				}else{
					if (rs.getInt("CICLO")==1 || rs.getInt("CICLO") ==2) {
						grado = 1;
					}else if (rs.getInt("CICLO")==3 || rs.getInt("CICLO") ==4){
						grado = 2;
					}else if (rs.getInt("CICLO")==5 || rs.getInt("CICLO") ==6){
						grado = 3;
					}else if (rs.getInt("CICLO")==7 || rs.getInt("CICLO") ==8){
						grado = 4;
					}else if (rs.getInt("CICLO")==9 || rs.getInt("CICLO") ==10){
						grado = 5;
					}else if (rs.getInt("CICLO")==11 || rs.getInt("CICLO") ==12){
						grado = 6;
					}else{
						grado = 0;
					}					
				}				
				if (updateGrado(conn, rs.getString("CODIGO_PERSONAL"),rs.getString("PLAN_ID"), grado)==false){					
					error++;
				}					
			}			
			if (error==0){
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|actualizaGradoAlumno|:"+ex);		
		}finally{
			try { st.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean actualizaGrado(Connection conn, String cargas) throws Exception{
		Statement st 		= conn.createStatement();		
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok 			= false;
		
		try{
			int grado 		= 0;			
			int error		= 0;		
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS FACULTAD, CICLO "+		
				" FROM ENOC.ALUM_PLAN"+ 
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))"; 

			rs = st.executeQuery(comando);
			while (rs.next()){
				
				if (rs.getString("FACULTAD").equals("107")){
					if (rs.getInt("CICLO")>=1 && rs.getInt("CICLO") <= 3) {
						grado = 1;
					}else if (rs.getInt("CICLO")>=4 && rs.getInt("CICLO") <=6){
						grado = 2;
					}else{
						grado = 0;
					}
				}else{
					if (rs.getInt("CICLO")==1 || rs.getInt("CICLO") ==2) {
						grado = 1;
					}else if (rs.getInt("CICLO")==3 || rs.getInt("CICLO") ==4){
						grado = 2;
					}else if (rs.getInt("CICLO")==5 || rs.getInt("CICLO") ==6){
						grado = 3;
					}else if (rs.getInt("CICLO")==7 || rs.getInt("CICLO") ==8){
						grado = 4;
					}else if (rs.getInt("CICLO")==9 || rs.getInt("CICLO") ==10){
						grado = 5;
					}else if (rs.getInt("CICLO")==11 || rs.getInt("CICLO") ==12){
						grado = 6;
					}else{
						grado = 0;
					}					
				}				
				if (updateGrado(conn, rs.getString("CODIGO_PERSONAL"),rs.getString("PLAN_ID"), grado)==false){					
					error++;
				}					
			}			
			if (error==0){
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|actualizaGrado|:"+ex);		
		}finally{
			try { st.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getSem(Connection conn, String codigoPersonal, String planId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	ciclo			= "0"; 
		try{
			ps = conn.prepareStatement("SELECT CICLO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ciclo= rs.getString("CICLO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getSem|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ciclo;
	}
	
	public static String getCicloSep(Connection conn, String codigoPersonal, String planId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	ciclo			= "0"; 
		try{
			ps = conn.prepareStatement("SELECT CICLO_SEP FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ciclo= rs.getString("CICLO_SEP");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getCicloSep|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ciclo;
	}
	
	public static String getGrado(Connection conn, String codigoPersonal, String planId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	res				= ""; 
		try{
			ps = conn.prepareStatement("SELECT GRADO FROM ENOC.ALUM_PLAN "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				res= rs.getString("GRADO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getGrado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return res;
	}
	
	public static String getAlumPlanCiclo(Connection conn, String codigoPersonal, String planId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	ciclo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT ALUM_PLAN_CICLO(CODIGO_PERSONAL, PLAN_ID) AS CICLO FROM ENOC.ALUM_PLAN "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ciclo = rs.getString("CICLO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getAlumPlanCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ciclo;
	}
	
	public static String getAlumPromCreditos(Connection conn, String codigoPersonal, String planId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	promedio		= "0"; 
		try{
			ps = conn.prepareStatement("SELECT ENOC.ALUM_PROMEDIO_CREDITOS(CODIGO_PERSONAL, PLAN_ID) AS PROMEDIO  FROM ENOC.ALUM_PLAN "+ 
				"WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getAlumPromCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return promedio;
	}
	
	public static String getEscuelaOrigen(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String escuela			= "x";
		
		try{
			ps = conn.prepareStatement(" SELECT ESCUELA_ID FROM ENOC.ALUM_PLAN " + 
					" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			rs= ps.executeQuery();		
			
			if(rs.next()){
				escuela = rs.getString("ESCUELA_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getEscuelaOrigen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return escuela;
	}
	
	public static int getContPrimerIngreso(Connection conn, String year, String carreraId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		int total			= 0;
		
		try{
			ps = conn.prepareStatement(" SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ALUM_PLAN WHERE TO_CHAR(PRIMER_MATRICULA,'YYYY') = '"+year+"' " +
					" AND ENOC.CARRERA(PLAN_ID)='"+carreraId+"' AND PRIMER_MATRICULA IS NOT NULL" +
							" AND (SELECT MODALIDAD_ID FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ALUM_PLAN.CODIGO_PERSONAL)=1 ");
			
			rs= ps.executeQuery();		
			
			if(rs.next()){
				total = rs.getInt("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getContPrimerIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public static String getFInicio(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String 	fecha			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MIN(F_EVALUACION),NULL) AS INICIO " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PLAN_ID = '"+planId+"' AND CONVALIDACION = 'N'");
			
			rs= ps.executeQuery();		
			
			if(rs.next()){
				fecha = rs.getString("INICIO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getFInicio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return fecha;
	}
	
	public static String getPrimerMatricula(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String 	fecha			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MIN(FECHA),NULL) AS INSCRIPCION " +
					" FROM MATEO.FES_CCOBRO WHERE MATRICULA = '"+codigoPersonal+"' AND PLAN_ID = '"+planId+"' AND INSCRITO = 'S' ");
			
			rs= ps.executeQuery();		
			
			if(rs.next()){
				fecha = rs.getString("INSCRIPCION");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getPrimerMatricula|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return fecha;
	}
	
	public static String getNumMaterias(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String 	fecha			= "X";
		
		try{
			
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS CONT " +
					" FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL ='"+codigoPersonal+"' AND PLAN_ID = '"+planId+"' AND CONVALIDACION = 'N' ");
			
			rs= ps.executeQuery();		
			
			if(rs.next()){
				fecha = rs.getString("CONT");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getNumMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return fecha;
	}
		
	public ArrayList<AlumPlan> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumPlan> lisPlan	= new ArrayList<AlumPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, PLAN_ID,"
					+ " TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO,"
					+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO,"
					+ " PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP"
					+ " FROM ENOC.ALUM_PLAN "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPlan plan = new AlumPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPlan;
	}
	
	public ArrayList<AlumPlan> lisActualizarIngreso(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumPlan> lisPlan	= new ArrayList<AlumPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, PLAN_ID,"
					+ " TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO,"
					+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO,"
					+ " PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP"
					+ " FROM ENOC.ALUM_PLAN WHERE ACTUALIZADO = 'N' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPlan plan = new AlumPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|lisActualizarIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPlan;
	}
	
	public ArrayList<String> getLisPorMatriculayNivel(Connection conn, String codigoPersonal, String nivelId ) throws SQLException{
		
		ArrayList<String> lisPlan	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT DISTINCT ENOC.CARRERA(PLAN_ID) AS CARRERA FROM ENOC.ALUM_PLAN "
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " 
					+ " AND ENOC.NIVEL_PLAN(PLAN_ID) = '"+nivelId+"' "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				lisPlan.add(rs.getString("CARRERA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getLisPorMatriculayNivel|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPlan;
	}
	
	public ArrayList<AlumPlan> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<AlumPlan> lisPlan	= new ArrayList<AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					"PLAN_ID," +
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					"ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO," +
					"TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					"TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					"PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP " +
					"FROM ENOC.ALUM_PLAN " + 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+ orden;			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPlan plan = new AlumPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPlan;
	}
	
	public ArrayList<AlumPlan> getListaAlumnosPlan(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<AlumPlan> lisPlan	= new ArrayList<AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID," +
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					" ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP " +
					" FROM ENOC.ALUM_PLAN " +
					" WHERE PLAN_ID = '"+planId+"'" +
					" AND CODIGO_PERSONAL||PLAN_ID IN" +
					"	(SELECT MATRICULA||PLAN_ID FROM MATEO.FES_CCOBRO WHERE MATRICULA = ALUM_PLAN.CODIGO_PERSONAL AND INSCRITO = 'S') "+ orden;			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPlan plan = new AlumPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getListaAlumnosInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPlan;
	}
	
	public ArrayList<AlumPlan> getListYear(Connection conn, String year, String orden ) throws SQLException{
		
		ArrayList<AlumPlan> lisPlan	= new ArrayList<AlumPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID,	TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO," +
					" ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION, TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, " +
					" GRADO, CICLO, PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP" +
					" FROM ENOC.ALUM_PLAN A" + 
					" WHERE A.F_INICIO BETWEEN TO_DATE('01/07/"+year+"','DD/MM/YYYY') AND TO_DATE('30/06/"+(Integer.parseInt(year)+1)+"','DD/MM/YYYY')" +
					" AND A.CODIGO_PERSONAL||A.PLAN_ID = (SELECT DISTINCT(B.CODIGO_PERSONAL||B.PLAN_ID) FROM ENOC.ALUMNO_CURSO B" +
														" WHERE (B.NOTA > 0 OR 'S' IN (SELECT C.INSCRITO FROM MATEO.FES_CCOBRO C" +
																					 " WHERE C.MATRICULA = A.CODIGO_PERSONAL" +
																					 " AND C.PLAN_ID = A.PLAN_ID))" +
														" AND A.CODIGO_PERSONAL = B.CODIGO_PERSONAL" +
														" AND A.PLAN_ID = B.PLAN_ID) "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumPlan plan = new AlumPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getListYear|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisPlan;
	}
	
	public static HashMap<String,AlumPlan> getMapInscritos(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,AlumPlan> mapPlan = new HashMap<String,AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL,"+
				" PLAN_ID,"+
				" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO,"+
				" ESTADO,"+
				" ESCUELA_ID,"+
				" AVANCE_ID,"+
				" PERMISO,"+
				" EVENTO,"+
				" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
				" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
				" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP "+
				" FROM ENOC.ALUM_PLAN" +
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" +
				" AND ESTADO = '1' "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPlan alumPlan = new AlumPlan();
				alumPlan.mapeaReg(rs);
				llave = alumPlan.getCodigoPersonal()+alumPlan.getPlanId();
				mapPlan.put(llave, alumPlan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getMapInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static HashMap<String,AlumPlan> getMapPorCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		HashMap<String,AlumPlan> mapPlan = new HashMap<String,AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL," +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADO WHERE CARGA_ID = '"+cargaId+"') "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumPlan alumPlan = new AlumPlan();
				alumPlan.mapeaReg(rs);
				llave = alumPlan.getCodigoPersonal()+alumPlan.getPlanId();
				mapPlan.put(llave, alumPlan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getMapPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static HashMap<String,String> mapAlumCicloSepCarga(Connection conn, String cargaId ) throws SQLException{
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, PLAN_ID, CICLO_SEP" 
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN("+cargaId+") )";
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave = rs.getString("CODIGO_PERSONAL") + rs.getString("PLAN_ID");
				mapPlan.put(llave, rs.getString("CICLO_SEP"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumCicloSepCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}

	public int getMaximoCicloCargas(Connection conn, String cargas) throws SQLException{
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT MAX(CICLO) AS MAXIMO FROM ENOC.INSCRITOS A, ENOC.ALUM_PLAN B"
					+ " WHERE B.CODIGO_PERSONAL = A.CODIGO_PERSONAL"
					+ " AND A.CARGA_ID IN ("+cargas+")";
			rs = st.executeQuery(comando);
			if(rs.next()) return rs.getInt("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|getMaximoCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return 12;
	}
	
	public static HashMap<String,AlumPlan> mapAlumPlanActivo(Connection conn, String cargas ) throws SQLException{
		
		HashMap<String,AlumPlan> mapPlan = new HashMap<String,AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO = 'I')" +
					" AND ESTADO = '1' ";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPlan alumPlan = new AlumPlan();
				alumPlan.mapeaReg(rs);
				llave = alumPlan.getCodigoPersonal();				
				mapPlan.put(llave, alumPlan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumPlanActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static HashMap<String,AlumPlan> mapAlumPlanes(Connection conn, String cargas ) throws SQLException{
		
		HashMap<String,AlumPlan> mapPlan = new HashMap<String,AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO = 'I')";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPlan alumPlan = new AlumPlan();
				alumPlan.mapeaReg(rs);
				llave = alumPlan.getCodigoPersonal()+alumPlan.getPlanId();				
				mapPlan.put(llave, alumPlan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumPlanActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static HashMap<String,AlumPlan> mapAlumEnPlan(Connection conn, String planId, String estado ) throws SQLException{
		
		HashMap<String,AlumPlan> mapPlan = new HashMap<String,AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE PLAN_ID = '"+planId+"'" +
					" AND ESTADO IN("+estado+")";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPlan alumPlan = new AlumPlan();
				alumPlan.mapeaReg(rs);
				llave = alumPlan.getCodigoPersonal();
				mapPlan.put(llave, alumPlan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumEnPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public HashMap<String,AlumPlan> mapAlumIngreso(Connection conn, String matricula ) throws SQLException{
		
		HashMap<String,AlumPlan> mapPlan = new HashMap<String,AlumPlan>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL LIKE '"+matricula+"%'" +
					" AND ESTADO = '1' ";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumPlan alumPlan = new AlumPlan();
				alumPlan.mapeaReg(rs);
				llave = alumPlan.getCodigoPersonal();				
				mapPlan.put(llave, alumPlan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static HashMap<String,String> mapAlumPlanCreditos(Connection conn, String planId ) throws SQLException{
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		try{
			comando = "SELECT" +
					" CODIGO_PERSONAL, ALUM_CREDITOS_PLAN(CODIGO_PERSONAL, PLAN_ID) AS CREDITOS" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE PLAN_ID = '"+planId+"' " +
					" AND CODIGO_PERSONAL||PLAN_ID IN" +
					"  (SELECT MATRICULA||PLAN_ID FROM MATEO.FES_CCOBRO WHERE MATRICULA = ALUM_PLAN.CODIGO_PERSONAL AND INSCRITO = 'S')";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapPlan.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CREDITOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumPlanActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static HashMap<String,String> mapAlumUltimaInsc(Connection conn, String planId ) throws SQLException{
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		try{
			comando = "SELECT" +
					" CODIGO_PERSONAL, " +
					" (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO WHERE MATRICULA = AP.CODIGO_PERSONAL AND PLAN_ID = AP.PLAN_ID AND INSCRITO = 'S') AS FECHA" +
					" FROM ENOC.ALUM_PLAN AP" +
					" WHERE PLAN_ID = '"+planId+"' " +
					" AND CODIGO_PERSONAL||PLAN_ID IN" +
					"  (SELECT MATRICULA||PLAN_ID FROM MATEO.FES_CCOBRO WHERE MATRICULA = AP.CODIGO_PERSONAL AND INSCRITO = 'S')";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapPlan.put(rs.getString("CODIGO_PERSONAL"), rs.getString("FECHA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumUltimaInsc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static HashMap<String,String> mapAlumPlanActual(Connection conn, String periodoId ) throws SQLException{
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, PLAN_ID FROM ENOC.ALUM_PLAN "
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"'))"
					+ " AND ESTADO = '1'";					
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapPlan.put(rs.getString("CODIGO_PERSONAL"), rs.getString("PLAN_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapAlumPlanActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public HashMap<String,String> mapaPrimerMatricula(Connection conn, String cargaId ) throws SQLException{
		
		HashMap<String,String> mapPlan = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, PRIMER_MATRICULA FROM ALUM_PLAN WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ESTADISTICA WHERE CARGA_ID = '"+cargaId+"')";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapPlan.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PLAN_ID"), rs.getString("PRIMER_MATRICULA"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapaPrimerMatricula|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapPlan;
	}
	
	public static ArrayList<String> listAlumPlanEstudiado(Connection conn, String codigoPersonal) throws SQLException{
		
		ArrayList<String> listPlan 	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		try{
			comando = " SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL='"+codigoPersonal+"' AND PRINCIPAL!= '1'";					
			rs = st.executeQuery(comando);
			while (rs.next()){				
				listPlan.add(rs.getString("PLAN_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|listAlumPlanEstudiado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listPlan;
	}

}