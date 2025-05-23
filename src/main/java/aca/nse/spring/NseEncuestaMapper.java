package aca.nse.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NseEncuestaMapper implements RowMapper<NseEncuesta> {

	public NseEncuesta mapRow(ResultSet rs, int rowNum) throws SQLException {

		NseEncuesta objeto = new NseEncuesta();
		
		objeto.setEncuestaId(rs.getString("ENCUESTA_ID"));
		objeto.setEncuestaNombre(rs.getString("ENCUESTA_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		
		return objeto;
	}

}
