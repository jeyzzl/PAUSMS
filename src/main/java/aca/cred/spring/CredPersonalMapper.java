package aca.cred.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CredPersonalMapper implements RowMapper<CredPersonal> {
	public CredPersonal mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CredPersonal objeto = new CredPersonal();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		
		return objeto;
	}
}