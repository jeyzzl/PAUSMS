package aca.cultural.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompAsistenciaMapper implements RowMapper<CompAsistencia> {
	public CompAsistencia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CompAsistencia objeto = new CompAsistencia();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setHora(rs.getString("HORA"));

		return objeto;
	}
}