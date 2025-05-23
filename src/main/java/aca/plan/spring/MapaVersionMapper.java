package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaVersionMapper implements RowMapper<MapaVersion>{

	public MapaVersion mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaVersion objeto = new MapaVersion();
		
		objeto.setVersionId(rs.getString("VERSION_ID"));
		objeto.setVersionNombre(rs.getString("VERSION_NOMBRE"));
		
		return objeto;
	}

}
