package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatRecogidaMapper implements RowMapper<CatRecogida> {
	public CatRecogida mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatRecogida objeto = new CatRecogida();
		
		objeto.setRecogidaId(rs.getString("RECOGIDA_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		
		return objeto;
	}
}
