package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaEgresoUtil {
	
	public boolean insertReg(Connection conn, ExaEgreso exaEgreso ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EXA_EGRESO(EGRESO_ID, MATRICULA, CARRERAID, YEAR, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOANO, PLAN_ID)"+
				" VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'9'), ?, ? )");		
			ps.setString(1, exaEgreso.getEgresoId());
			ps.setString(2, exaEgreso.getMatricula());
			ps.setString(3, exaEgreso.getCarreraId());
			ps.setString(4, exaEgreso.getYear());
			ps.setString(5, exaEgreso.getFechaAct());
			ps.setString(6, exaEgreso.getEliminado());
			ps.setString(7, exaEgreso.getIdEgresadoAno());			
			ps.setString(8, exaEgreso.getPlanId());
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String egresoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_EGRESO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE EGRESO_ID = TO_NUMBER(?,'99999999')");
		
			ps.setString(1, egresoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public ExaEgreso mapeaRegIdEstudio( Connection conn, String estudioId) throws SQLException{
		ExaEgreso exaEgreso = new ExaEgreso();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EGRESO_ID, MATRICULA, CARRERAID, YEAR, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOANO, PLAN_ID"
					+ " FROM ENOC.EXA_EGRESO WHERE EGRESO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,estudioId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEgreso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEgreso;
	}
	
	public ExaEgreso mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaEgreso exaEgreso = new ExaEgreso();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EGRESO_ID, MATRICULA, CARRERAID, YEAR, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOANO, PLAN_ID"
					+ " FROM ENOC.EXA_EGRESO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEgreso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEgreso;
	}
	
	public boolean existeReg(Connection conn, String egresoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_EGRESO WHERE EGRESO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1, egresoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeAlumno(Connection conn, String matricula) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_EGRESO WHERE MATRICULA = ? AND ELIMINADO!=1"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|existeAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoRegAlumno(Connection conn, String matricula) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(EGRESO_ID)+1 AS MAXIMO FROM ENOC.EXA_EGRESO WHERE ELIMINADO !=1 AND MATRICULA = ?"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|maximoRegAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(EGRESO_ID)+1 AS MAXIMO FROM ENOC.EXA_EGRESO "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaEgreso> getEgresos(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaEgreso> list		= new ArrayList<ExaEgreso>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM EXA_EGRESO WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEgreso obj = new ExaEgreso();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|getEgresos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}	
	
	public ArrayList<ExaEgreso> getEgresos(Connection conn, String orden) throws SQLException{
		
		ArrayList<ExaEgreso> list		= new ArrayList<ExaEgreso>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM EXA_EGRESO WHERE ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEgreso obj = new ExaEgreso();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|getEgresos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<ExaEgreso> getEgresadosPorCarrera(Connection conn, String carreraId, String orden) throws SQLException{
		
		ArrayList<ExaEgreso> list				= new ArrayList<ExaEgreso>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM EXA_EGRESO WHERE CARRERAID = "+carreraId+" AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEgreso obj = new ExaEgreso();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|getEgresadosPorCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return list;
	}
	
	
	public ArrayList<String> getAnios(Connection conn, String orden) throws SQLException{
		
		ArrayList<String> list					= new ArrayList<String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT DISTINCT(YEAR) AS YEAR  FROM EXA_EGRESO " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				list.add(rs.getString("YEAR"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|getAnios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<String> getCarreras(Connection conn, String orden) throws SQLException{
		
		ArrayList<String> list					= new ArrayList<String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT DISTINCT(CARRERAID) AS CARRERAID FROM EXA_EGRESO " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				list.add(rs.getString("CARRERAID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgresoUtil|getCarreras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
