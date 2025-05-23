package aca.alerta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlertaPeriodoMapper implements RowMapper<AlertaPeriodo> {

	@Override
	public AlertaPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		AlertaPeriodo objeto = new AlertaPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setModalidades(rs.getString("MODALIDADES"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setExcepto( rs.getString("EXCEPTO"));
		
		return objeto;
	}

}
