package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatDescuentoMapper implements RowMapper<CatDescuento> {
	public CatDescuento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatDescuento objeto = new CatDescuento();
		
		objeto.setDescuentoId(rs.getString("DESCUENTO_ID"));
		objeto.setDescuentoNombre(rs.getString("DESCUENTO_NOMBRE"));	
		objeto.setUsuarios(rs.getString("USUARIOS"));
		
		return objeto;
	}
}
