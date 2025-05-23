// Clase para la tabla Res

package aca.residencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RazonUtil {
	public ArrayList<ResRazon> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ResRazon> lisRazon	= new ArrayList<ResRazon>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResRazon razon = new ResRazon();
				razon.mapeaReg(rs);
				lisRazon.add(razon);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.RazonUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisRazon;
	}
	
	public ArrayList<ResRazon> getLista(Connection conn, String razon, String orden ) throws SQLException{
		
		ArrayList<ResRazon> lisRazon	= new ArrayList<ResRazon>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES "+ 
				"WHERE RAZON = '"+razon+"'"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResRazon razonId = new ResRazon();
				razonId.mapeaReg(rs);
				lisRazon.add(razonId);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.RazonUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisRazon;
	}
	
	public HashMap<String, String> getMapRazon(Connection conn) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES ";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("RAZON"), rs.getString("DESCRIPCION"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaEdadInternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	
	
	
}