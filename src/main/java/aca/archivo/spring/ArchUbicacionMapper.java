package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchUbicacionMapper implements RowMapper<ArchUbicacion>{
	@Override
	public ArchUbicacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchUbicacion objeto = new ArchUbicacion();
		
		objeto.setUbicacionId(rs.getString("UBICACION_ID"));
		objeto.setUbicacionNombre(rs.getString("UBICACION_NOMBRE"));
		
		return objeto;
	}
}