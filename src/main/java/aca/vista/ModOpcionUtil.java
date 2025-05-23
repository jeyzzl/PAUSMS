//Clase para la vista MOD_OPCION

package aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class ModOpcionUtil{
	
	public ArrayList<ModOpcion> getListAll (Connection conn, String orden) throws SQLException{
		
		ArrayList<ModOpcion> lisModOpcion 				= new ArrayList<ModOpcion>();
		Statement st	 					= conn.createStatement();
		ResultSet rs						= null;
		String s_command					= "";
		
		try {
			s_command = "SELECT (SELECT MENU_ID FROM ENOC.MODULO WHERE MODULO_ID = A.MODULO_ID) AS MENU_ID, MODULO_ID, OPCION_ID, NOMBRE_MODULO, NOMBRE_OPCION, "+
				"URL_MODULO, URL_OPCION, USUARIOS  FROM ENOC.MOD_OPCION A "+ orden; 
			rs = st.executeQuery (s_command);
			while (rs.next()){
				
				ModOpcion mod_opcion = new ModOpcion();
				mod_opcion.mapeaReg(rs);
				lisModOpcion.add(mod_opcion);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.ModOpcionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisModOpcion;
	}
	
	
	public ArrayList<ModOpcion> getListCat (Connection conn, String menu_Id, String orden) throws SQLException{
		
		ArrayList<ModOpcion> lisModOpcion 				= new ArrayList<ModOpcion>();
		Statement st	 					= conn.createStatement();
		ResultSet rs						= null;
		String s_command					= "";
		
		try {
			s_command = "SELECT (SELECT MENU_ID FROM ENOC.MODULO WHERE MODULO_ID = A.MODULO_ID) AS MENU_ID, MODULO_ID, OPCION_ID, NOMBRE_MODULO, NOMBRE_OPCION, "+
				"URL_MODULO, URL_OPCION, USUARIOS  FROM ENOC.MOD_OPCION A "+ 
				"WHERE MODULO_ID IN (SELECT MODULO_ID FROM ENOC.MODULO WHERE MENU_ID='"+menu_Id+"' ) "+orden; 
			rs = st.executeQuery (s_command);
			while (rs.next()){
				
				ModOpcion mod_opcion = new ModOpcion();
				mod_opcion.mapeaReg(rs);
				lisModOpcion.add(mod_opcion);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.ModOpcionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisModOpcion;
	}
	

	

}