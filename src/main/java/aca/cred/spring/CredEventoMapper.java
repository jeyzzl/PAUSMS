package aca.cred.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CredEventoMapper implements RowMapper<CredEvento> {
	public CredEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CredEvento objeto = new CredEvento();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setCodigoInicial(rs.getString("CODIGO_INICIAL"));
		
		return objeto;
	}
}