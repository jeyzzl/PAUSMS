package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaPronMapper implements RowMapper<CargaPron> {

	public CargaPron mapRow(ResultSet rs, int rowNum) throws SQLException {
		CargaPron objeto = new CargaPron();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		
		return objeto;
	}

}
