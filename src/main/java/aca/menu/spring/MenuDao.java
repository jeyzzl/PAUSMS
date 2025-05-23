package aca.menu.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MenuDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(Menu menu ) {

		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.MENU(MENU_ID, MENU_NOMBRE, NOMBRE_INGLES, ORDEN)" +
					" VALUES(?, ?, ?, TO_NUMBER(?,'99'))";
					
			Object[] parametros = new Object[] {menu.getMenuId(),menu.getMenuNombre(),menu.getNombreIngles()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(Menu menu) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.MENU SET MENU_NOMBRE = ?, NOMBRE_INGLES = ?, ORDEN = TO_NUMBER(?,'99') WHERE MENU_ID = ?";
			Object[] parametros = new Object[] {menu.getMenuNombre(),menu.getNombreIngles(),menu.getOrden(), menu.getMenuId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String menuId, String menuNombre ) {
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.MENU WHERE MENU_ID = ?";
			
			Object[] parametros = new Object[] {menuId,menuNombre};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public Menu mapeaRegId( String menuId) {
		Menu menu = new Menu();
		
		try{
			String comando = "SELECT MENU_ID, MENU_NOMBRE, NOMBRE_INGLES, ORDEN FROM ENOC.MENU WHERE MENU_ID = ?";
				
			Object[] parametros = new Object[] {menuId};
			menu = enocJdbc.queryForObject(comando, new MenuMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return menu;
	}
	
	public boolean existeReg(String menuId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENU WHERE MENU_ID = ?";
			
			Object[] parametros = new Object[] {menuId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public String menuNombre( String menuId) {
		String menuNombre 		= "-";
		
		try{
			String comando = "SELECT COUNT(MENU_NOMBRE) FROM ENOC.MENU WHERE MENU_ID = ?";			
			Object[] parametros = new Object[] {menuId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>= 1){
				comando = "SELECT MENU_NOMBRE FROM ENOC.MENU WHERE MENU_ID = ?";
				menuNombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|menuNombre|:"+ex);
		}		
		return menuNombre;
	}
	
	public String menuNombreIngles( String menuId) {
		String menuNombre 		= "-";
		
		try{
			String comando = "SELECT COUNT(NOMBRE_INGLES) FROM ENOC.MENU WHERE MENU_ID = ?";			
			Object[] parametros = new Object[] {menuId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>= 1){
				comando = "SELECT NOMBRE_INGLES FROM ENOC.MENU WHERE MENU_ID = ?";
				menuNombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|menuNombre|:"+ex);
		}		
		return menuNombre;
	}
		
	public List<Menu> getListAll( String orden) {
		List<Menu> lisMenu			= new ArrayList<Menu>();
		try{
			String comando = "SELECT MENU_ID, MENU_NOMBRE, NOMBRE_INGLES, ORDEN FROM ENOC.MENU "+orden;			
			lisMenu = enocJdbc.query(comando, new MenuMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|getListAll|:"+ex);
		}
		return lisMenu;
	}
	
	public List<Menu> lisPorEstados( String estados, String orden) {
		List<Menu> lisMenu			= new ArrayList<Menu>();
		try{
			String comando = "SELECT MENU_ID, MENU_NOMBRE, NOMBRE_INGLES, ORDEN FROM ENOC.MENU WHERE MENU_ID IN (SELECT MENU_ID FROM ENOC.MODULO WHERE MODULO_ID IN (SELECT DISTINCT(MODULO_ID) FROM ENOC.MODULO_OPCION WHERE ICONO IN ("+estados+"))) "+orden;			
			lisMenu = enocJdbc.query(comando, new MenuMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|getListAll|:"+ex);
		}
		return lisMenu;
	}
	
	public List<Menu> getListUser( String  codigoPersonal, String opciones ){
		List<Menu> lisModulo 	= new ArrayList<Menu>();		
		try{
			String comando = " SELECT MENU_ID, MENU_NOMBRE, NOMBRE_INGLES, ORDEN FROM ENOC.MENU WHERE MENU_ID IN "
					+ " 	(SELECT MENU_ID FROM ENOC.MODULO WHERE MODULO_ID IN "
					+ "			(SELECT MODULO_ID FROM ENOC.MODULO_OPCION WHERE OPCION_ID IN "
					+ "				(SELECT OPCION_ID FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ?)))"
					+ " ORDER BY ORDEN";			
			lisModulo = enocJdbc.query(comando, new MenuMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|getListUser|:"+ex);
		}		
		return lisModulo;
	}
	
	public HashMap<String, String> mapaMenus( ) {		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT MENU_ID AS LLAVE, MENU_NOMBRE AS VALOR FROM ENOC.MENU";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|mapaOpciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaMenusIngles( ) {		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT MENU_ID AS LLAVE, NOMBRE_INGLES AS VALOR FROM ENOC.MENU";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.MenuDao|mapaMenusIngles|:"+ex);
		}
		return mapa;
	}
}

