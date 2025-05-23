package aca.rol.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RolMapper implements RowMapper<Rol>{
	
	public Rol mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Rol objeto = new Rol();
		
		objeto.setRolId(rs.getString("ROL_ID"));
		objeto.setRolNombre(rs.getString("ROL_NOMBRE"));
				
		return objeto;
	}
}