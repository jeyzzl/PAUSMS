package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinPeriodoMapper implements RowMapper<FinPeriodo> {

	public FinPeriodo mapRow(ResultSet rs, int rowNum) throws SQLException {
		FinPeriodo objeto = new FinPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setCargas(rs.getString("CARGAS"));
		objeto.setModalidades(rs.getString("MODALIDADES"));
		objeto.setMensaje(rs.getString("MENSAJE"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		
		return objeto;
	}

}
