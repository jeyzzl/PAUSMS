// Clase Util para la tabla de Cat_Area
package adm.catalogo;

import java.sql.*;
import java.util.ArrayList;

public class CiudadUtil{
	
	
	public ArrayList<CatCiudad> getLista(Connection conn, String paisId, String estadoId, String orden ) throws SQLException{
		
		ArrayList<CatCiudad> lisCiudad = new ArrayList<CatCiudad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD "+
				"FROM ENOC.CAT_CIUDAD "+
				"WHERE PAIS_ID = TO_NUMBER('"+paisId+"','999') "+
				"AND ESTADO_ID = TO_NUMBER('"+estadoId+"','999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCiudad ciudad = new CatCiudad();
				ciudad.mapeaReg(rs);
				lisCiudad.add(ciudad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCiudad;
	}
	
	public ArrayList<CatCiudad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatCiudad> lisCiudad = new ArrayList<CatCiudad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCiudad ciudad = new CatCiudad();
				ciudad.mapeaReg(rs);
				lisCiudad.add(ciudad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCiudad;
	}
	public String getCiudad(Connection Conn,String paisId,String estadoId, String ciudadId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			comando = "SELECT NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD where ciudad_id = "+ciudadId+" and estado_id ="+estadoId+" and pais_id = "+paisId;
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getCiudad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}		
}