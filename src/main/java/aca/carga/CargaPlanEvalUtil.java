package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaPlanEvalUtil {
	
	
	public boolean insertReg(Connection conn, CargaPlanEval plan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" ENOC.CARGA_PLAN_EVAL(CURSO_CARGA_ID, EVALUACION_ID, EVALUACION_NOMBRE, FECHA, VALOR, ACTIVIDAD_ID)" +
				" VALUES( ?, TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99.99'),?)");
			ps.setString(1, plan.getCursoCargaId()); 
			ps.setString(2, plan.getEvaluacionId());
			ps.setString(3, plan.getEvaluacionNombre());
			ps.setString(4, plan.getFecha());
			ps.setString(5, plan.getValor());
			ps.setString(6, plan.getActividadId());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaPlanEval plan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_PLAN_EVAL "+ 
				" SET EVALUACION_NOMBRE = ?, " +
				" FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" VALOR = TO_NUMBER(?,'99.99')," +
				" ACTIVIDAD_ID = ?" +
				" WHERE CURSO_CARGA_ID = ? " +
				" AND EVALUACION_ID = TO_NUMBER(?,'99')");		
			
			ps.setString(1, plan.getEvaluacionNombre());
			ps.setString(2, plan.getFecha());
			ps.setString(3, plan.getValor());
			ps.setString(4, plan.getActividadId());
			ps.setString(5, plan.getCursoCargaId()); 
			ps.setString(6, plan.getEvaluacionId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateActividad(Connection conn, CargaPlanEval plan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_PLAN_EVAL"+ 
				" SET EVALUACION_NOMBRE = ?,"+
				" FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
				" VALOR = TO_NUMBER(?,'99.99')"+
				" WHERE CURSO_CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, plan.getEvaluacionNombre());
			ps.setString(2, plan.getFecha());
			ps.setString(3, plan.getValor());			
			ps.setString(4, plan.getCursoCargaId());
			ps.setString(5, plan.getActividadId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|updateActividad|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String evaluacionId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_PLAN_EVAL "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND EVALUACION_ID = TO_NUMBER(?,'99') ");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean deleteEvaluaciones(Connection conn, String cursoCargaId ) throws Exception{		
		
		PreparedStatement ps = null;
		boolean ok = false;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_PLAN_EVAL "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND ACTIVIDAD_ID NOT IN ('00','77') ");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate()>= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|deleteEvaluaciones|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaPlanEval mapeaRegId( Connection conn, String cursoCargaId, String evaluacionId) throws SQLException{
		
		CargaPlanEval plan = new CargaPlanEval();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, EVALUACION_ID," +
					" EVALUACION_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VALOR, ACTIVIDAD_ID " +
					" FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			rs = ps.executeQuery();
			if (rs.next()){
				plan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return plan;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String evaluacionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ?  AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeActividad(Connection conn, String cursoCargaId, String actividadId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		boolean 		ok 	= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ?  AND ACTIVIDAD_ID = ?");
			ps.setString(1,cursoCargaId);
			ps.setString(2,actividadId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|existeActividad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(EVALUACION_ID)+1 AS MAXIMO "+
				" FROM ENOC.CARGA_PLAN_EVAL WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static double getSumEvaluaciones(Connection conn, String cursoCargaId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		double suma				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(VALOR),0) AS SUMA FROM ENOC.CARGA_PLAN_EVAL" + 
					" WHERE CURSO_CARGA_ID = ?");
			
			ps.setString(1, cursoCargaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				suma = Double.parseDouble(rs.getString("SUMA"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|getSumEvaluaciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return suma;
	}
	
	public static int getNumEval(Connection conn, String cursoCargaId) throws SQLException{
		int totalEval		    = 0;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(EVALUACION_ID) AS EVALUACION FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ?");
			
			ps.setString(1, cursoCargaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				totalEval = rs.getInt("EVALUACION");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|getNumEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return totalEval;
	}	
	
	public ArrayList<CargaPlanEval> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaPlanEval> lisActividad = new ArrayList<CargaPlanEval>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, EVALUACION_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VALOR, ACTIVIDAD_ID " +
					" FROM ENOC.CARGA_PLAN_EVAL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaPlanEval act = new CargaPlanEval();
				act.mapeaReg(rs);
				lisActividad.add(act);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEvalUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActividad;
	}
	
	public ArrayList<CargaPlanEval> getListEvaluaciones(Connection conn, String cursoCargaId, String orden ) throws SQLException{
			
			ArrayList<CargaPlanEval> lisActividad = new ArrayList<CargaPlanEval>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, EVALUACION_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VALOR, ACTIVIDAD_ID" +
						" FROM ENOC.CARGA_PLAN_EVAL" + 
						" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CargaPlanEval act = new CargaPlanEval();
					act.mapeaReg(rs);
					lisActividad.add(act);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaPlanEvalUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisActividad;
	}

}