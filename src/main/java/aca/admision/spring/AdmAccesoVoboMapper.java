package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmAccesoVoboMapper implements RowMapper<AdmAccesoVobo> {

	@Override
	public AdmAccesoVobo mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmAccesoVobo objeto = new AdmAccesoVobo();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		
		return objeto;
	}

}
