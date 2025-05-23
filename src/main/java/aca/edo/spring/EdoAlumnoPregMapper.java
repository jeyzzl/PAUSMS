package aca.edo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EdoAlumnoPregMapper implements RowMapper<EdoAlumnoPreg> {
	public EdoAlumnoPreg mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EdoAlumnoPreg objeto = new EdoAlumnoPreg();
		
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setEdoId(rs.getString("EDO_ID"));
		objeto.setPregunta(rs.getString("PREGUNTA"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setSeccion(rs.getString("SECCION"));
		
		return objeto;
	}
}