package aca.disciplina.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CondReporteMapper implements RowMapper<CondReporte>{
	
	public CondReporte mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CondReporte objeto = new CondReporte();
		
		objeto.setIdReporte(rs.getString("IDREPORTE"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));

		return objeto;
	}
}