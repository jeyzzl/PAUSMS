// Clase Util para la tabla de Cat_Division
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InstitucionUtil{
		
	public ArrayList<CatInstitucion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatInstitucion> lisInstitucion	= new ArrayList<CatInstitucion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT INSTITUCION_ID, NOMBRE_INSTITUCION "+
				"FROM ENOC.CAT_INSTITUCION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatInstitucion institucion = new CatInstitucion();
				institucion.mapeaReg(rs);
				lisInstitucion.add(institucion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.InstitucionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInstitucion;
	}
		
	public HashMap<String,CatInstitucion> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatInstitucion> map = new HashMap<String,CatInstitucion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT INSTITUCION_ID, NOMBRE_INSTITUCION "+
				"FROM ENOC.CAT_INSTITUCION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatInstitucion obj = new CatInstitucion();
				obj.mapeaReg(rs);
				llave = obj.getInstitucionId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.InstitucionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}