package aca.encuesta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EncPeriodoMapper implements RowMapper<EncPeriodo> {
	public EncPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EncPeriodo objeto = new EncPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}
