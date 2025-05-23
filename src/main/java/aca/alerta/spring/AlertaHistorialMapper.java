package aca.alerta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlertaHistorialMapper implements RowMapper<AlertaHistorial> {
	@Override
	public AlertaHistorial mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlertaHistorial objeto = new AlertaHistorial();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPregunta(rs.getString("PREGUNTA"));
		objeto.setRespuesta(rs.getString("RESPUESTA"));
		objeto.setComentario1(rs.getString("COMENTARIO1"));
		objeto.setComentario2(rs.getString("COMENTARIO2"));
		
		return objeto;
	}

}
