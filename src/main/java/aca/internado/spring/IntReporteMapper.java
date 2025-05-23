package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntReporteMapper implements RowMapper<IntReporte> {
	public IntReporte mapRow(ResultSet rs, int arg1) throws SQLException {
		
		IntReporte objeto = new IntReporte();
		
		objeto.setReporteId(rs.getString("REPORTE_ID"));
		objeto.setReporteNombre(rs.getString("REPORTE_NOMBRE"));
		
		return objeto;
	}
}