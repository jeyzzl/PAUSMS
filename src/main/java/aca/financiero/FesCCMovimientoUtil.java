package aca.financiero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FesCCMovimientoUtil {
	public ArrayList<FesCCMovimiento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FesCCMovimiento> lisAcceso 	= new ArrayList<FesCCMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT MATRICULA,CARGA_ID, BLOQUE, TIPOMOV, DESCRIPCION,IMPORTE,NATURALEZA" +
					" FROM MATEO.FES_CC_MOVIMIENTO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FesCCMovimiento acceso = new FesCCMovimiento();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<FesCCMovimiento> getListNaturaleza(Connection conn, String matricula, String cargaId, String naturaleza, String orden ) throws SQLException{
		
		ArrayList<FesCCMovimiento> lisAcceso 	= new ArrayList<FesCCMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT MATRICULA,CARGA_ID, BLOQUE, TIPOMOV, DESCRIPCION, IMPORTE, NATURALEZA" +
					" FROM MATEO.FES_CC_MOVIMIENTO WHERE NATURALEZA = '"+naturaleza+"' AND MATRICULA = '"+matricula+"' " +
							" AND CARGA_ID = '"+cargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FesCCMovimiento acceso = new FesCCMovimiento();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}	

	public ArrayList<FesCCMovimiento> getMovimientos(Connection conn, String codigoPersonal, String cargaId, String bloque, String orden ) throws SQLException{
		
		ArrayList<FesCCMovimiento> lisAcceso 	= new ArrayList<FesCCMovimiento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT MATRICULA,CARGA_ID, BLOQUE, TIPOMOV, DESCRIPCION,IMPORTE,NATURALEZA" +
					" FROM MATEO.FES_CC_MOVIMIENTO " +
					" WHERE MATRICULA = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"' AND BLOQUE = '"+bloque+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FesCCMovimiento acceso = new FesCCMovimiento();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|getMovimientos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public ArrayList<String> getMaterias(Connection conn, String codigoPersonal, String cargaId, String bloque, String orden ) throws SQLException{
		
		ArrayList<String> materias 	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT CURSO_ID, NOMBRE_CURSO, COSTO_CURSO FROM MATEO.FES_CC_MATERIA " +
					" WHERE MATRICULA = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"' AND BLOQUE = '"+bloque+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				materias.add(rs.getString("CURSO_ID")+"&"+rs.getString("NOMBRE_CURSO")+"&"+rs.getString("COSTO_CURSO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|getMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return materias;
	}
	
	public static HashMap<String, String> mapSaldoInicial(Connection conn, String cargas, String movimientos, String estados) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, TIPOMOV, SUM(COALESCE((CASE NATURALEZA WHEN 'D' THEN -1 ELSE 1 END)*IMPORTE,0)) AS TOTAL"
					+ " FROM MATEO.FES_CC_MOVIMIENTO"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"										
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " AND TIPOMOV IN("+movimientos+")"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE, TIPOMOV";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE")+rs.getString("TIPOMOV"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|mapMovimientoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapMovimientoCarga(Connection conn, String cargas, String movimientos, String estados) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, TIPOMOV, SUM(COALESCE(IMPORTE,0)) AS TOTAL"
					+ " FROM MATEO.FES_CC_MOVIMIENTO"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"										
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " AND TIPOMOV IN("+movimientos+")"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE, TIPOMOV";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE")+rs.getString("TIPOMOV"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|mapMovimientoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapDeudaAlumno(Connection conn, String cargas, String movimientos, String estados) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, "
					+ " SUM( IMPORTE * (CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END)) AS TOTAL"
					+ " FROM MATEO.FES_CC_MOVIMIENTO"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"										
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " AND TIPOMOV NOT IN("+movimientos+")"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
			rs = st.executeQuery(comando);
			//System.out.println(comando);
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|mapDeudaAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}