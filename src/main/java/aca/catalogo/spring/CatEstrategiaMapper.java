package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatEstrategiaMapper implements RowMapper<CatEstrategia> {
	public CatEstrategia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatEstrategia objeto = new CatEstrategia();
		
		objeto.setEstrategiaId(rs.getString("ESTRATEGIA_ID"));
		objeto.setNombreEstrategia(rs.getString("NOMBRE_ESTRATEGIA"));
		
		return objeto;
	}
}
