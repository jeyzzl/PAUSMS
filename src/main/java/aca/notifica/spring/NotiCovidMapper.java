package aca.notifica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NotiCovidMapper implements RowMapper<NotiCovid> {

	public NotiCovid mapRow(ResultSet rs, int rowNum) throws SQLException {

		NotiCovid objeto = new NotiCovid();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setRespuesta(rs.getString("RESPUESTA"));
		
		return objeto;
	}

}
