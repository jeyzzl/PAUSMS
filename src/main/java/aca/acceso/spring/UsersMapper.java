package aca.acceso.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsersMapper implements RowMapper<AccesoPrivacidad>{
	@Override
	public AccesoPrivacidad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AccesoPrivacidad objeto = new AccesoPrivacidad();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}