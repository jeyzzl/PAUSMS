package aca.menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MenuUtil{
		
	public ArrayList<Menu> getListAll(Connection Conn, String orden) throws SQLException{
		
		ArrayList<Menu> lisMenu			= new ArrayList<Menu>();
		Statement st 					= Conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT MENU_ID, MENU_NOMBRE FROM ENOC.MENU "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Menu menu = new Menu();
				menu.mapeaReg(rs);
				lisMenu.add(menu);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.MenuLista|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisMenu;
	}
	
	public ArrayList<Menu> getListUser(Connection Conn, String  codigoPersonal, String opciones ) throws SQLException{
		
		ArrayList<Menu> lisModulo 	= new ArrayList<Menu>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT MENU_ID, MENU_NOMBRE FROM ENOC.MENU "
					+ " WHERE MENU_ID IN "
					+ "		(SELECT MENU_ID FROM ENOC.MODULO"
					+ "		WHERE MODULO_ID IN "
					+ "			(SELECT MODULO_ID FROM ENOC.MODULO_OPCION "
					+ "			WHERE USUARIOS LIKE '%-"+codigoPersonal+"-%'"
					+ "			OR OPCION_ID IN ("+opciones+") )) ORDER BY ORDEN";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Menu menu = new Menu();
				menu.mapeaReg(rs);
				lisModulo.add(menu);							
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.MenuUtil|getListUser|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModulo;
	}	

}

