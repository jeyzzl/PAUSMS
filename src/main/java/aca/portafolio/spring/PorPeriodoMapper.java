package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorPeriodoMapper implements RowMapper<PorPeriodo> {

	public PorPeriodo mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorPeriodo objeto = new PorPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
