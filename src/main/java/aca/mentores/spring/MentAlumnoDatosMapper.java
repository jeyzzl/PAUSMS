package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentAlumnoDatosMapper implements RowMapper<MentAlumnoDatos>{

	public MentAlumnoDatos mapRow(ResultSet rs, int arg1) throws SQLException {
		MentAlumnoDatos objeto = new MentAlumnoDatos();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setIglesia(rs.getString("IGLESIA"));
		objeto.setClaseEs(rs.getString("CLASE_ES"));

		return objeto;
	}

}
