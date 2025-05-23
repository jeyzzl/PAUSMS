package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecPrecioMapper implements RowMapper<BecPrecio> {
	
	@Override
	public BecPrecio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecPrecio objeto = new BecPrecio();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setPrecio(rs.getString("PRECIO"));	
		
		return objeto;
	}
}