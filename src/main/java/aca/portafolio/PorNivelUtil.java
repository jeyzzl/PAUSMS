package aca.portafolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PorNivelUtil {
	
	public ArrayList<PorNivel> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorNivel> lisPeriodo	= new ArrayList<PorNivel>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT NIVEL_ID, NIVEL_NOMBRE, ARCHIVO, DOCUMENTO_ID "+
					"FROM DANIEL.POR_NIVEL "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorNivel periodo = new PorNivel();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
				
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorNivelUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisPeriodo;
	}
	
	public HashMap<String,PorNivel> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,PorNivel> map = new HashMap<String,PorNivel>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT NIVEL_ID, NIVEL_NOMBRE, ARCHIVO, DOCUMENTO_ID "+
					"FROM DANIEL.POR_NIVEL "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				PorNivel obj = new PorNivel();
				obj.mapeaReg(rs);
				llave = obj.getNivelId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorNivelUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
}