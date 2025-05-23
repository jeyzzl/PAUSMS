package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumAtuendoMapper implements RowMapper<AlumAtuendo> {
	@Override
	public AlumAtuendo mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlumAtuendo objeto = new AlumAtuendo();
		
		objeto.setAtuendoId(rs.getString("ATUENDO_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setPrecio(rs.getString("PRECIO"));
		
		return objeto;
	}

}
