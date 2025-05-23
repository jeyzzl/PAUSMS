package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmModCarreraMapper implements RowMapper<AdmModCarrera> {
	@Override
	public AdmModCarrera mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmModCarrera objeto = new AdmModCarrera();
		
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));		
		
		return objeto;
	}

}
