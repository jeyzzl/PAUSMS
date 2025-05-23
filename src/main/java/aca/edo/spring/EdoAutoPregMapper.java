package aca.edo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EdoAutoPregMapper implements RowMapper<EdoAutoPreg> {
	public EdoAutoPreg mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EdoAutoPreg objeto = new EdoAutoPreg();
		
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setEdoId(rs.getString("EDO_ID"));
		objeto.setPregunta(rs.getString("PREGUNTA"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}