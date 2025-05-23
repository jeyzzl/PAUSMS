package aca.conva.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConvPeriodoMapper implements RowMapper<ConvPeriodo> {
	public ConvPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConvPeriodo periodo = new ConvPeriodo();
		
		periodo.setPeriodoId(rs.getString("PERIODO_ID"));
		periodo.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		periodo.setFechaIni(rs.getString("FECHA_INI"));
		periodo.setFechaFin(rs.getString("FECHA_FIN"));
		periodo.setCarrera(rs.getString("CARRERA"));
		
		return periodo;
	}
}
