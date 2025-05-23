package aca.edo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EdoAlumnoRespMapper implements RowMapper<EdoAlumnoResp> {
	public EdoAlumnoResp mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EdoAlumnoResp objeto = new EdoAlumnoResp();
		
		objeto.setEdoId(rs.getString("EDO_ID"));
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCodigoMaestro(rs.getString("CODIGO_MAESTRO"));
		objeto.setRespuesta(rs.getString("RESPUESTA"));
		
		return objeto;
	}
}