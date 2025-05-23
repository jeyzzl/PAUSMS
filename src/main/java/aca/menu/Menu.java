/**
 * 
 */
package aca.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class Menu{
	private String menuId;
	private String menuNombre;
	
	public Menu(){
		menuId			= "";
		menuNombre		= "";
	}
			


	public String getMenuId() {
		return menuId;
	}



	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}



	public String getMenuNombre() {
		return menuNombre;
	}



	public void setMenuNombre(String menuNombre) {
		this.menuNombre = menuNombre;
	}



	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MENU" +
					" (MENU_ID, MENU_NOMBRE)" +
					" VALUES(?, ?)");
			
			ps.setString(1, menuId);
			ps.setString(2, menuNombre);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Menu|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENU" +
					" SET MENU_NOMBRE = ?" +
					" WHERE MENU_ID = ?");
			
			ps.setString(1, menuId);
			ps.setString(2, menuNombre);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Menu|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENU WHERE MENU_ID = ?");
			
			ps.setString(1, menuId);
			ps.setString(2, menuNombre);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Menu|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		menuId			= rs.getString("MENU_ID");
		menuNombre		= rs.getString("MENU_NOMBRE");
	}
	
	public void mapeaRegId(Connection con, String menuId) throws SQLException{
		
		ResultSet rs = null;		
		PreparedStatement ps = null; 
		try{
			ps = con.prepareStatement("SELECT MENU_ID, MENU_NOMBRE FROM ENOC.MENU WHERE MENU_ID = ?");
				
			ps.setString(1, menuId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Menu|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENU WHERE MENU_ID = ?");
			
			ps.setString(1, menuId);
			ps.setString(2, menuNombre);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Menu|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String menuNombre(Connection conn, String menuId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String rolNombre 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT MENU_NOMBRE FROM ENOC.MENU WHERE MENU_ID = ?"); 
			ps.setString(1,menuId);
			rs = ps.executeQuery();
			if (rs.next()){
				rolNombre = rs.getString("MENU_NOMBRE");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Menu|menuNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return rolNombre;
	}

}