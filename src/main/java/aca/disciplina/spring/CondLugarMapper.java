package aca.disciplina.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CondLugarMapper implements RowMapper<CondLugar>{
	
	public CondLugar mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CondLugar objeto = new CondLugar();
		
		objeto.setIdLugar(rs.getString("IDLUGAR"));
		objeto.setNombre(rs.getString("NOMBRE"));

		return objeto;
	}
}