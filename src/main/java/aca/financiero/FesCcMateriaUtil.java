package aca.financiero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FesCcMateriaUtil {
	
	public ArrayList<FesCcMateria> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<FesCcMateria> lista 	= new ArrayList<FesCcMateria>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, CURSO_CARGA_ID, CURSO_ID, COSTO_CREDITO, COSTO_CURSO" +
	 				" FROM MATEO.FesCcMateria "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				FesCcMateria acceso = new FesCcMateria();
				acceso.mapeaReg(rs);
				lista.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateriaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public static HashMap<String, String> mapCostoCredito(Connection conn, String cargas, String estados) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, MAX(COALESCE(COSTO_CREDITO,0)) AS COSTO"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE"), rs.getString("COSTO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateriaUtil|mapCostoCredito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapCostoCreditoCarga(Connection conn, String cargas, String estados) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CARGA_ID, MAX(COALESCE(COSTO_CREDITO,0)) AS COSTO"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " GROUP BY MATRICULA, CARGA_ID";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID"), rs.getString("COSTO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateriaUtil|mapCostoCreditoCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapCreditosCarga(Connection conn, String cargas, String estados) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, SUM(COALESCE(CREDITOS,0)) AS TOTAL"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateriaUtil|mapCreditosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapCreditosCargaCero(Connection conn, String cargas) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CARGA_ID, BLOQUE, MATRICULA, SUM(CREDITOS) AS TOTAL FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID = '"+cargas+"' AND COSTO_CURSO < 1 "
					+ " GROUP BY CARGA_ID, BLOQUE, MATRICULA";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateriaUtil|mapCreditosCargaCero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapCostoMateria(Connection conn, String cargas) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CURSO_CARGA_ID, CURSO_ID, COALESCE(COSTO_CURSO,0) AS COSTO"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID IN("+cargas+")"
					+ " AND COSTO_CURSO > 0";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CURSO_CARGA_ID")+rs.getString("CURSO_ID"), rs.getString("COSTO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateriaUtil|mapCostoMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapMatEnCalculo(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, COALESCE(COUNT(MATRICULA),0) AS TOTAL FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MATRICULA IN (SELECT MATRICULA FROM MATEO.FES_CCOBRO WHERE MATRICULA = FES_CC_MATERIA.MATRICULA AND INSCRITO = 'S' AND CARGA_ID = '"+cargaId+"')"
					+ " GROUP BY MATRICULA";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("MATRICULA"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateriaUtil|mapMatEnCalculo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
}