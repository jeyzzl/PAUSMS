package aca.menu.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConJspMapper  implements RowMapper<ConJsp> {
	public ConJsp mapRow(ResultSet rs, int arg1) throws SQLException {		
		ConJsp objeto = new ConJsp();		
		objeto.setRuta(rs.getString("RUTA"));
		objeto.setAbrir(rs.getString("ABRIR"));
		objeto.setCerrar(rs.getString("CERRAR"));		
		return objeto;
	}	
}