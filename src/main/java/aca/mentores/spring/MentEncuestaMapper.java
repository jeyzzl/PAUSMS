package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentEncuestaMapper implements RowMapper<MentEncuesta> {
	public MentEncuesta mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MentEncuesta objeto = new MentEncuesta();
		
		objeto.setEncuestaId(rs.getString("PERIODO_ID"));
		objeto.setNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}
