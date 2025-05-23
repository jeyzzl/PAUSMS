package aca.disciplina.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CondJuezMapper implements RowMapper<CondJuez>{
	
	public CondJuez mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CondJuez objeto = new CondJuez();
		
		objeto.setIdJuez(rs.getString("IDJUEZ"));
		objeto.setNombre(rs.getString("NOMBRE"));

		return objeto;
	}
}