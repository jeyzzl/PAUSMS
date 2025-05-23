package aca.nse.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NseRespuestaPreguntaMapper implements RowMapper<NseRespuestaPregunta> {

	public NseRespuestaPregunta mapRow(ResultSet rs, int rowNum) throws SQLException {

		NseRespuestaPregunta objeto = new NseRespuestaPregunta();
		
		objeto.setRespuestaId(rs.getString("RESPUESTA_ID"));
		objeto.setEncuestaId(rs.getString("ENCUESTA_ID"));
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setRespuesta(rs.getString("RESPUESTA"));
		objeto.setPuntos(rs.getString("PUNTOS"));

		return objeto;
	}

}
