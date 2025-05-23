package aca.menu.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MenuMapper  implements RowMapper<Menu> {
	public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Menu objeto = new Menu();
		
		objeto.setMenuId(rs.getString("MENU_ID"));
		objeto.setMenuNombre(rs.getString("MENU_NOMBRE"));
		objeto.setNombreIngles(rs.getString("NOMBRE_INGLES"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}	
}