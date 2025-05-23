package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntDormitorioMapper implements RowMapper<IntDormitorio> {
	public IntDormitorio mapRow(ResultSet rs, int rowNum) throws SQLException {
		IntDormitorio objeto = new IntDormitorio();
		
		objeto.setDormitorioId(rs.getString("DORMITORIO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPreceptor(rs.getString("PRECEPTOR"));
		objeto.setSexo(rs.getString("SEXO"));
		
		return objeto;
	}

}
