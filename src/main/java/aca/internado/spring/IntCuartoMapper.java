package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntCuartoMapper implements RowMapper<IntCuarto> {
	public IntCuarto mapRow(ResultSet rs, int rowNum) throws SQLException {
		IntCuarto objeto = new IntCuarto();
		
		objeto.setDormitorioId(rs.getString("DORMITORIO_ID"));
		objeto.setCuartoId(rs.getString("CUARTO_ID"));
		objeto.setPasillo(rs.getString("PASILLO"));
		objeto.setCupo(rs.getString("CUPO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
