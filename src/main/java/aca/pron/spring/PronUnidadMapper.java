package aca.pron.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PronUnidadMapper implements RowMapper<PronUnidad> {

	public PronUnidad mapRow(ResultSet rs, int rowNum) throws SQLException {
		PronUnidad objeto = new PronUnidad();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setAporte(rs.getString("APORTE"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}

}
