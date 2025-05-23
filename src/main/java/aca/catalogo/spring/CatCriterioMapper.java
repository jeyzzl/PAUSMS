package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatCriterioMapper implements RowMapper<CatCriterio> {
	public CatCriterio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatCriterio criterio = new CatCriterio();
		
		criterio.setCriterioId(rs.getString("CRITERIO_ID"));
		criterio.setDescripcion(rs.getString("DESCRIPCION"));	
		
		return criterio;
	}
}
