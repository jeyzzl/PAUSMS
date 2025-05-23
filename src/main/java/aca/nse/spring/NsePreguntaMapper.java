package aca.nse.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NsePreguntaMapper implements RowMapper<NsePregunta> {

	public NsePregunta mapRow(ResultSet rs, int rowNum) throws SQLException {

		NsePregunta objeto = new NsePregunta();
		
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setEncuestaId(rs.getString("ENCUESTA_ID"));
		objeto.setPregunta(rs.getString("PREGUNTA"));

		return objeto;
	}

}
