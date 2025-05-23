package aca.menu.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ModuloMapper  implements RowMapper<Modulo> {
	public Modulo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Modulo objeto = new Modulo();
		
		objeto.setModuloId(rs.getString("MODULO_ID"));
		objeto.setNombreModulo(rs.getString("NOMBRE_MODULO"));
		objeto.setUrl(rs.getString("URL"));
		objeto.setIcono(rs.getString("ICONO"));
		objeto.setMenuId(rs.getString("MENU_ID"));
		objeto.setNombreIngles(rs.getString("NOMBRE_INGLES"));
		
		return objeto;
	}	
}