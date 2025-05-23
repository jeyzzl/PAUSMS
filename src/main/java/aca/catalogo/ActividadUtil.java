package aca.catalogo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ActividadUtil {
	public ArrayList<CatActividad> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<CatActividad> lisAct = new ArrayList<CatActividad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ACTIVIDAD_ID, DESCRIPCION FROM ENOC.CAT_ACTIVIDAD "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatActividad actividad = new CatActividad();
				actividad.mapeaReg(rs);
				lisAct.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ActividadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAct;
	}
	
	public HashMap<String,CatActividad> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatActividad> map = new HashMap<String,CatActividad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT ACTIVIDAD_ID, DESCRIPCION FROM ENOC.CAT_ACTIVIDAD "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatActividad obj = new CatActividad();
				obj.mapeaReg(rs);
				llave = obj.getActividadId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ActividadUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}