package aca.rol.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RolOpcionMapper implements RowMapper<RolOpcion>{
	
	public RolOpcion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		RolOpcion objeto = new RolOpcion();
		
		objeto.setRolId(rs.getString("ROL_ID"));
		objeto.setOpcionId(rs.getString("OPCION_ID"));
					
		return objeto;
	}
}