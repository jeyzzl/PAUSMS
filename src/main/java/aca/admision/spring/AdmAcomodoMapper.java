package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmAcomodoMapper implements RowMapper<AdmAcomodo> {
	@Override
	public AdmAcomodo mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmAcomodo objeto = new AdmAcomodo();
		
		objeto.setAcomodoId(rs.getString("ACOMODO_ID"));
		objeto.setAcomodoNombre(rs.getString("ACOMODO_NOMBRE"));
		objeto.setAcomodoTipo(rs.getString("ACOMODO_TIPO"));

		return objeto;
	}

}
