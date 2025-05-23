//Clase para la tabla de Alum_Academico
package aca.federacion;

import java.sql.*;
import java.util.HashMap;

public class FedVotoUtilM{
		
	public static HashMap<String, String> mapPresidente(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PRESIDENTE, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY PRESIDENTE ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("PRESIDENTE"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapPresidente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapDesarrollo(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DESARROLLO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY DESARROLLO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("DESARROLLO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapDesarrollo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapFinanciero(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FINANCIERO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY FINANCIERO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("FINANCIERO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapFinanciero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapEjecutivo(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EJECUTIVO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY EJECUTIVO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("EJECUTIVO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapEjecutivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapSecretario(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT SECRETARIO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY SECRETARIO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("SECRETARIO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapSecretario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

}