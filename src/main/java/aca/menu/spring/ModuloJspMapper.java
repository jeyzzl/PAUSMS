package aca.menu.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ModuloJspMapper  implements RowMapper<ModuloJsp> {
	public ModuloJsp mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		ModuloJsp objeto = new ModuloJsp();
		objeto.setId(rs.getInt("ID"));
		objeto.setRuta(rs.getString("RUTA"));
		objeto.setEnoc(rs.getString("ENOC"));
		objeto.setAtlas(rs.getString("ATLAS"));
		objeto.setRutaCorta(rs.getString("RUTA_CORTA"));
		return objeto;
	}	
}