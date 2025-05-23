package aca.edo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EdoMaestroPregMapper implements RowMapper<EdoMaestroPreg> {
	public EdoMaestroPreg mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EdoMaestroPreg objeto = new EdoMaestroPreg();
		
		objeto.setEdoId(rs.getString("EDO_ID"));
		objeto.setPreguntaId(rs.getString("PREGUNTA_ID"));
		objeto.setPregunta(rs.getString("PREGUNTA"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setComentario1(rs.getString("COMENTARIO1"));
		objeto.setComentario2(rs.getString("COMENTARIO2"));
		objeto.setComentario3(rs.getString("COMENTARIO3"));
		objeto.setComentario4(rs.getString("COMENTARIO4"));
		
		return objeto;
	}
}