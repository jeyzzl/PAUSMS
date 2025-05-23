package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResPeriodoMapper implements RowMapper<ResPeriodo> {

	public ResPeriodo mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResPeriodo objeto = new ResPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
