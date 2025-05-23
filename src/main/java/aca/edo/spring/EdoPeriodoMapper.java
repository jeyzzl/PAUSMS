package aca.edo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EdoPeriodoMapper implements RowMapper<EdoPeriodo> {

	public EdoPeriodo mapRow(ResultSet rs, int rowNum) throws SQLException {
		EdoPeriodo objeto = new EdoPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfFinal(rs.getString("F_FINAL"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
