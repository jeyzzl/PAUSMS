package aca.catalogo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CatNotificacionUtil {

	public ArrayList<CatNotificacion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatNotificacion> lisNotificacion 		= new ArrayList<CatNotificacion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT NOTIFICACION_ID, NOTIFICACION_NOMBRE FROM ENOC.CAT_NOTIFICACION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatNotificacion notificacion = new CatNotificacion();
				notificacion.mapeaReg(rs);
				lisNotificacion.add(notificacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisNotificacion;
	}
	
	public HashMap<String,CatNotificacion> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatNotificacion> map = new HashMap<String,CatNotificacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT NOTIFICACION_ID, NOTIFICACION_NOMBRE FROM ENOC.CAT_NOTIFICACION  "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatNotificacion obj = new CatNotificacion();
				obj.mapeaReg(rs);
				llave = obj.getNotificacionId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}