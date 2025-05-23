package aca.rotaciones.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RotInstitucionMapper implements RowMapper<RotInstitucion>{

	public RotInstitucion mapRow(ResultSet rs, int arg1) throws SQLException {
		RotInstitucion objeto = new RotInstitucion();
		
		objeto.setInstitucionId(rs.getString("INSTITUCION_ID"));
		objeto.setInstitucionNombre(rs.getString("INSTITUCION_NOMBRE"));

		return objeto;
	}
}

