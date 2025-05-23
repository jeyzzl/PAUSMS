package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntAlumnoMapper implements RowMapper<IntAlumno> {
	public IntAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
		IntAlumno objeto = new IntAlumno();
		
		objeto.setDormitorioId(rs.getString("DORMITORIO_ID"));
		objeto.setCuartoId(rs.getString("CUARTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFechaInicio(rs.getDate("FECHA_INICIO"));
		objeto.setFechaFinal(rs.getDate("FECHA_FINAL"));
		
		return objeto;
	}

}
