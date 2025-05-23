package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatAcomodoMapper implements RowMapper<CatAcomodo> {
	public CatAcomodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatAcomodo objeto = new CatAcomodo();
		
		objeto.setAcomodoId(rs.getString("ACOMODO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}
}
