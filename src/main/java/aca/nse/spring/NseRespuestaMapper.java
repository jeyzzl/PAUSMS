package aca.nse.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NseRespuestaMapper implements RowMapper<NseRespuesta> {

	public NseRespuesta mapRow(ResultSet rs, int rowNum) throws SQLException {

		NseRespuesta objeto = new NseRespuesta();
		
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setRespuesta(rs.getString("RESPUESTA"));
		objeto.setPuntos(rs.getString("PUNTOS"));
		objeto.setEncuestaId(rs.getString("ENCUESTA_ID"));

		return objeto;
	}

}
