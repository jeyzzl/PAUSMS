package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentAccesoMapper implements RowMapper<MentAcceso>{

	public MentAcceso mapRow(ResultSet rs, int arg1) throws SQLException {
		MentAcceso objeto = new MentAcceso();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setAcceso(rs.getString("ACCESO"));

		return objeto;
	}

}
