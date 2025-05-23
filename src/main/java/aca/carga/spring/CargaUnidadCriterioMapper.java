package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaUnidadCriterioMapper implements RowMapper<CargaUnidadCriterio>{
	
	public CargaUnidadCriterio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaUnidadCriterio objeto = new CargaUnidadCriterio();
		
		objeto.setCriterioId(rs.getString("CRITERIO_ID"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCriterioNombre(rs.getString("CRITERIO_NOMBRE"));

		return objeto;
	}
}