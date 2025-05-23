package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatTipoAlumnoMapper implements RowMapper<CatTipoAlumno> {
	public CatTipoAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatTipoAlumno objeto = new CatTipoAlumno();
		
		objeto.setTipoId(rs.getString("TIPO_ID"));
		objeto.setNombreTipo(rs.getString("NOMBRE_TIPO"));
		
		return objeto;
	}
}
