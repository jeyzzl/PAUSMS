// Clase para la tabla de Modulo
package aca.menu;

import java.sql.*;

public class Modulo  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String moduloId;
	private String nombreModulo;
	private String url;
	private String icono;
	private String menuId;
	
	// Constructor
	public Modulo(){		
		moduloId 		= "";
		nombreModulo	= "";
		url 			= "";
		icono			= "";
		menuId			= "";
	}
	
	

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getModuloId(){
		return moduloId;
	}
	
	public void setModuloId(String moduloId){
		this.moduloId = moduloId;
	}
	
	public String getNombreModulo(){
		return nombreModulo;
	}
	
	public void setNombreModulo(String nombreModulo){
		this.nombreModulo = nombreModulo;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getIcono(){
		return icono;
	}
	
	public void setIcono(String icono){
		this.icono = icono;
	}	
		
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MODULO(MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID) VALUES(?,?,?,?, TO_NUMBER(?,'99'))"); 
			ps.setString(1,moduloId);
			ps.setString(2,nombreModulo);
			ps.setString(3,url);
			ps.setString(4,icono);
			ps.setString(5,menuId);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Modulo|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO SET NOMBRE_MODULO = ?, URL= ?, ICONO= ?, MENU_ID = TO_NUMBER(?,'99') WHERE MODULO_ID = ?");			 
			ps.setString(1,nombreModulo);
			ps.setString(2,url);
			ps.setString(3,icono);
			ps.setString(4,menuId);
			ps.setString(5,moduloId);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Modulo|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MODULO WHERE MODULO_ID = ?"); 
			ps.setString(1,moduloId);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Modulo|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		moduloId 		= rs.getString("MODULO_ID");
		nombreModulo	= rs.getString("NOMBRE_MODULO");
		url 			= rs.getString("URL");
		icono			= rs.getString("ICONO");		
		menuId			= rs.getString("MENU_ID");
	}
	
	public void mapeaRegId(Connection con, String moduloId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO,MENU_ID FROM ENOC.MODULO WHERE MODULO_ID = ? "); 
			ps.setString(1,moduloId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Modulo|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.MODULO WHERE MODULO_ID = ? "); 
			ps.setString(1, moduloId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Modulo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String moduloNombre(Connection conn, String moduloId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String rolNombre 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_MODULO, MENU_ID FROM ENOC.MODULO WHERE MODULO_ID = ?"); 
			ps.setString(1,moduloId);
			rs = ps.executeQuery();
			if (rs.next()){
				rolNombre = rs.getString("NOMBRE_MODULO")+"@@"+rs.getString("MENU_ID");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Modulo|moduloNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return rolNombre;
	}

/*	
	public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "camina_con_dios");
			
			Modulo m = new Modulo();
			m.setModulo_id("01");
			m.setNombre("Modulo de Archivo");
			m.setUrl("Url");
			m.setIcono("Icono");
			
			//Pruebas hechas al Bean
			//m.insert_Reg(Conn);
			//m.mapea_Regid(Conn,"01");
			//m.update_Reg(Conn);
			//m.delete_Reg(Conn);
			if (m.existe_Reg(Conn))
				System.out.println("Existe..!");
			else
				System.out.println("No existe..!");
			
			
			System.out.println(m.getModulo_id()+"--"+m.getNombre()+"--"+m.getUrl());
			Conn.commit();
			Conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
*/
}