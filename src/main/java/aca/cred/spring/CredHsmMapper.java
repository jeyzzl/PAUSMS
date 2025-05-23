package aca.cred.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CredHsmMapper implements RowMapper<CredHsm> {
	public CredHsm mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CredHsm objeto = new CredHsm();
		
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setArea(rs.getString("AREA"));
		objeto.setFondo(rs.getString("FONDO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}