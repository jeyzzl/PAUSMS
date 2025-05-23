package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentPreguntaMapper implements RowMapper<MentPregunta>{

	public MentPregunta mapRow(ResultSet rs, int arg1) throws SQLException {
		MentPregunta objeto = new MentPregunta();
		
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setEncuestaId(rs.getString("ENCUESTA_ID"));

		return objeto;
	}

}
