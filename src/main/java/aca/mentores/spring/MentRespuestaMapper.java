package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentRespuestaMapper implements RowMapper<MentRespuesta>{

	public MentRespuesta mapRow(ResultSet rs, int arg1) throws SQLException {
		MentRespuesta objeto = new MentRespuesta();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setImportancia(rs.getString("IMPORTANCIA"));
		objeto.setSatisfaccion(rs.getString("SATISFACCION"));
		objeto.setEncuestaId(rs.getString("ENCUESTA_ID"));

		return objeto;
	}

}
