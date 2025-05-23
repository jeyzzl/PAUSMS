// Clase Util para la tabla de Cat_Division
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtensionUtil{
		
	public ArrayList<CatExtension> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatExtension> lisExtension	= new ArrayList<CatExtension>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String s_comando		= "";
		
		try{
			s_comando = "SELECT EXTENSION_ID, NOMBRE_EXTENSION, "+
				"REFERENTE, DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL "+
				"FROM ENOC.CAT_EXTENSION "+ orden; 
			
			rs = st.executeQuery(s_comando);
			while (rs.next()){
				
				CatExtension extension = new CatExtension();
				extension.mapeaReg(rs);
				lisExtension.add(extension);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ExtensionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisExtension;
	}
	
	public HashMap<String,CatExtension> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatExtension> map = new HashMap<String,CatExtension>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT EXTENSION_ID, NOMBRE_EXTENSION, "+
				"REFERENTE, DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL "+
				"FROM ENOC.CAT_EXTENSION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatExtension obj = new CatExtension();
				obj.mapeaReg(rs);
				llave = obj.getExtensionId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ExtensionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}