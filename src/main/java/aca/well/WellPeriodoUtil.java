package aca.well;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class WellPeriodoUtil {

	public boolean insertReg(Connection conn, WellPeriodo wellPeriodo ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.WELL_PERIODO "
 					+ " (PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO) "
 					+ " VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?)");
 			ps.setString(1, wellPeriodo.getPeriodoId());
 			ps.setString(2, wellPeriodo.getPeriodoNombre());
 			ps.setString(3, wellPeriodo.getFechaIni());
 			ps.setString(4, wellPeriodo.getFechaFin());
 			ps.setString(5, wellPeriodo.getEstado());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellPeriodoUtil|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}	
	
	public boolean updateReg(Connection conn,WellPeriodo wellPeriodo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.WELL_PERIODO "
					+ " SET PERIODO_NOMBRE = ?,"
	 				+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'),"
	 				+ " FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'),"
	 				+ " ESTADO = ? "
	 				+ " WHERE PERIODO_ID = ? ");
		
	 			ps.setString(1, wellPeriodo.getPeriodoNombre());
	 			ps.setString(2, wellPeriodo.getFechaIni());
	 			ps.setString(3, wellPeriodo.getFechaFin());
	 			ps.setString(4, wellPeriodo.getEstado());
	 			ps.setString(5, wellPeriodo.getPeriodoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPeriodoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.WELL_PERIODO "
					+ " WHERE PERIODO_ID = ? ");
			ps.setString(1, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPeriodoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public WellPeriodo mapeaRegId( Connection conn, String periodoId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		WellPeriodo wellPeriodo = new WellPeriodo();
 		try{
	 		ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO"
	 				+ " FROM ENOC.WELL_PERIODO"
	 				+ " WHERE PERIODO_ID = ?"); 
	 		ps.setString(1, periodoId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			wellPeriodo.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPeriodoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return wellPeriodo;
 	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.WELL_PERIODO "
					+ " WHERE PERIODO_ID = ? ");
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPeriodoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<WellPeriodo> getListAll(Connection conn ) throws SQLException{
		
		ArrayList<WellPeriodo> listPeriodo 	= new ArrayList<WellPeriodo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT "
					+ " PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO "
					+ " FROM ENOC.WELL_PERIODO "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				WellPeriodo wellPeriodo = new WellPeriodo();
				wellPeriodo.mapeaReg(rs);
				listPeriodo.add(wellPeriodo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPeriodoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return listPeriodo;
	}
	
	public HashMap<String, String> mapPeriodosActivos(Connection conn) throws SQLException{
		
		HashMap<String, String> list		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE FROM ENOC.WELL_PERIODO WHERE ESTADO = 'A'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				list.put(rs.getString("PERIODO_ID"), rs.getString("PERIODO_NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPeriodoUtil|mapPeriodosActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return list;
	}
	
	public static String getPeriodoNombre(Connection conn, String periodoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	periodo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT PERIODO_NOMBRE FROM ENOC.WELL_PERIODO WHERE PERIODO_ID = '"+periodoId+"'"); 
						
			rs = ps.executeQuery();
			if (rs.next())	periodo = rs.getString("PERIODO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPeriodoUtil|getPeriodoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return periodo;
	}
	
}
