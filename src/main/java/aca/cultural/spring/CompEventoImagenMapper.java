package aca.cultural.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompEventoImagenMapper implements RowMapper<CompEventoImagen> {
	public CompEventoImagen mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CompEventoImagen objeto = new CompEventoImagen();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setImagenId(rs.getString("IMAGEN_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setImagen(rs.getBytes("IMAGEN"));

		return objeto;
	}
}